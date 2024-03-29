package com.example.CourseRecommendation.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.CourseRecommendation.config.MyConfig;
import com.example.CourseRecommendation.controller.message.Message;
import com.example.CourseRecommendation.dao.CourseRepository;
import com.example.CourseRecommendation.dao.UserRepository;
import com.example.CourseRecommendation.entity.Course;
import com.example.CourseRecommendation.entity.User;
import com.example.CourseRecommendation.mapper.*;
import com.example.CourseRecommendation.node.Neo4jCourse;
import com.example.CourseRecommendation.utils.CourseClassifier;
import com.example.CourseRecommendation.utils.JsonMessageGetter;
import com.example.CourseRecommendation.utils.crawler.java.CourseGetter.CourseGetter;
import com.example.CourseRecommendation.utils.crawler.java.CourseGetter.SelectedCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import java.util.*;

import static com.example.CourseRecommendation.controller.knowledgeGraph.KnowledgeGraphController.type2TypeName;


@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Autowired
    UserMapper userMapper;
    @Autowired
    CourseMapper courseMapper;

    @Autowired
    MomentMapper momentMapper;
    @Autowired
    CourseFollowMapper courseFollowMapper;

    @Autowired
    UserFollowMapper userFollowMapper;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserRepository userRepository;

    public String getOpenid(String temporaryId) {
        RestTemplate template = new RestTemplate();
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wx07d4ba749ffe08ce&secret=3a51553e895d1a87d69f3d4e766daade&js_code={code}&grant_type=authorization_code";

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("code", temporaryId);

        String result = template.getForObject(url, String.class, paramMap);
        if (result != null && result.length() > 0) {
            result = result.substring(0, result.length() - 1);
        }
        assert result != null;
        result = result.replaceAll(",\"session_key\":\"[^\"]*\"|\"session_key\":\"[^\"]*\",", "");
        Map<String, Object> objectMap = JsonMessageGetter.readJsonFile(MyConfig.RESOURCE_PATH + "myconfig.json");

        String version;
        if (objectMap != null && objectMap.containsKey("version"))
            version = objectMap.get("version").toString();
        else
            version = "1";
        result = result + ",\"version\":\"" + version + "\"}";
        return result;
    }


    public boolean bindStudentId(String student_id, String user_id) {
        return userMapper.updateStudentId(student_id, user_id);
    }


    public String insertAllLessonPlan(String u_id, String student_id, String student_pwd) {
        List<SelectedCourse> selectedCourses = CourseGetter.UpdatePersonalCourses(student_id, student_pwd);
        if (selectedCourses.size() == 0) return "未认证";
        userMapper.updateAuthenticated(u_id, "已认证");
        for (SelectedCourse selectedCourse : selectedCourses) {
            if (courseMapper.updateCourse(selectedCourse.getCourseId(),
                    selectedCourse.getCourseName(), selectedCourse.getCredits()) > 0) {
                try {
                    String category = CourseClassifier.courseClassify(selectedCourse.getCourseName());
                    courseMapper.updateCourseCategory(selectedCourse.getCourseId(), category);
                    courseMapper.updateCourseUrl(selectedCourse.getCourseId(), Course.category2Url(category));
                    courseMapper.updateCourseThUrl(selectedCourse.getCourseId(), Course.category2ThUrl(category));

                    // 更新neo4j
                    courseRepository.createCourse(selectedCourse.getCourseId(), selectedCourse.getCourseName(),
                            Float.toString(selectedCourse.getCredits()), category,
                            Course.category2Url(category), Course.category2ThUrl(category));
                    courseRepository.classifyCourse(selectedCourse.getCourseId(), category);

                } catch (Exception ignored) {
                }
            }
            courseRepository.selectCourse(u_id, selectedCourse.getCourseId(), selectedCourse.getCourseKind());
            userMapper.insertLessonPlan(student_id, selectedCourse.getCourseId(),
                    selectedCourse.getCourseKind(), selectedCourse.getSemester(), selectedCourse.isSelect());
        }
        return "已认证";
    }


    public List<Map<String, Object>> getLessonPlan(String student_id) {
        //        for (Map<String, Object> course : courses)
//            Course.setUrl(course);
        return userMapper.selectLessonPlanBySid(student_id);
    }

    public List<Map<String, Object>> getSelectedLesson(String student_id) {
        //        for (Map<String, Object> course : courses)
//            Course.setUrl(course);
        return userMapper.selectSelectedLessonPlanBySid(student_id);
    }

    public List<Neo4jCourse> getRecommendedCourses(String studentId) {
        int min_len = 20;
        List<Neo4jCourse> recommendedCourses = new ArrayList<>();
        List<String> courseNames = new ArrayList<>();
        List<Neo4jCourse> recommendedCourseByRecommendation = courseRepository.getRecommendedCourseByRecommendation(studentId);
        for (Neo4jCourse course : recommendedCourseByRecommendation) {
            if (recommendedCourses.size() < min_len) {
//                course.setUrl();
                recommendedCourses.add(course);
                courseNames.add(course.getName());
            }
        }

        if (recommendedCourses.size() < min_len) {
            List<Neo4jCourse> recommendedCourseBySelection = courseRepository.getRecommendedCourseBySelection(studentId);
            for (Neo4jCourse course : recommendedCourseBySelection) {
                if (!courseNames.contains(course.getName()) && recommendedCourses.size() < min_len) {
//                    course.setUrl();
                    recommendedCourses.add(course);
                    courseNames.add(course.getName());
                }
            }
        }

        if (recommendedCourses.size() < min_len) {
            List<Map<String, Object>> courses = courseMapper.recommendByCategory(studentId);
            for (Map<String, Object> course : courses) {
                Neo4jCourse neo4jCourse = Neo4jCourse.Map2Neo4jCourse(course);
                if (!courseNames.contains(neo4jCourse.getName()) && recommendedCourses.size() < min_len) {
//                    neo4jCourse.setUrl();
                    recommendedCourses.add(neo4jCourse);
                    courseNames.add(neo4jCourse.getName());
                }
            }
        }

        if (recommendedCourses.size() < min_len) {
            List<Map<String, Object>> courses = courseMapper.selectHotCourse();
            for (Map<String, Object> course : courses) {
                Neo4jCourse neo4jCourse = Neo4jCourse.Map2Neo4jCourse(course);
                if (!courseNames.contains(neo4jCourse.getName()) && recommendedCourses.size() < min_len) {
//                    neo4jCourse.setUrl();
                    recommendedCourses.add(neo4jCourse);
                    courseNames.add(neo4jCourse.getName());
                }
            }
        }

        return recommendedCourses;
    }

    public List<Map<String, Object>> hotCourseCol(int size) {
        List<Map<String, Object>> courses = courseMapper.selectHotCourse();
        Collections.shuffle(courses);
        return courses.subList(0, size);
    }

    public Map<String, Object> login(String u_id) {
        Message message = new Message();
        if (Objects.equals(u_id, "undefined")) {
            message.setMeta("FAIL", 200);
            return message;
        }
        userMapper.createWxUser(u_id);
        userRepository.createUser(u_id);
        Map<String, Object> userInfo = userMapper.selectById(u_id);
        userInfo.put("moment_num", momentMapper.selectMyMomentTotalNum(u_id));
        userInfo.put("course_num", userMapper.selectSelectedLessonPlanNumByUid(u_id));
        userInfo.put("follow_num", courseFollowMapper.selectFollowCoursesNumByUid(u_id));
        userInfo.put("item", "https://ruikezhixuan.cpolar.top/img/itemImg/item" + userInfo.get("u_item") + ".png");
        Map<String, Object> objectMap = JsonMessageGetter.readJsonFile(
                MyConfig.RESOURCE_PATH + "json/" +
                        type2TypeName(Integer.parseInt(userInfo.get("u_major").toString())) + ".json");
        userInfo.put("graph", objectMap);

        message.setMeta("SUCCESS", 200);
        message.setMessage(userInfo);
        message.setMessage(userInfo);
        return message;
    }


    public Map<String, Object> login(String u_id, String u_pwd) {
        Message message = new Message();
        if (Objects.equals(u_id, "undefined")) {
            message.setMeta("FAIL", 200);
            return message;
        }
        Map<String, Object> userInfo = userMapper.selectByIdAndPwd(u_id, u_pwd);
        if (userInfo == null)
            message.setMeta("FAIL", 200);
        else {
            userInfo.put("moment_num", momentMapper.selectMyMomentTotalNum(u_id));
            userInfo.put("course_num", userMapper.selectSelectedLessonPlanNumByUid(u_id));
            userInfo.put("follow_num", courseFollowMapper.selectFollowCoursesNumByUid(u_id));
            userInfo.put("item", "https://ruikezhixuan.cpolar.top/img/itemImg/item" + userInfo.get("u_item") + ".png");

            Map<String, Object> objectMap = JsonMessageGetter.readJsonFile(
                    MyConfig.RESOURCE_PATH + "json/" +
                            type2TypeName(Integer.parseInt(userInfo.get("u_major").toString())) + ".json");
            userInfo.put("graph", objectMap);
            this.followUser(u_id, u_id);
            message.setMeta("SUCCESS", 200);
            message.setMessage(userInfo);
        }
        return message;
    }

    public Map<String, Object> sign(String u_id, String u_pwd) {
        Message message = new Message();
        if (u_id.length() == 11 && userMapper.createUser(u_id, u_pwd) != 0) {
            userRepository.createUser(u_id);
            message.setMeta("SUCCESS", 200);
        } else
            message.setMeta("FAIL", 200);
        return message;
    }

    public boolean updateNickname(String u_id, String nickName) {
        return userMapper.updateNickname(u_id, nickName);
    }

    public boolean updateBirthday(String u_id, String birthday) {
        return userMapper.updateBirthday(u_id, birthday);
    }

    public boolean updateSchool(String u_id, String school) {
        return userMapper.updateSchool(u_id, school);
    }

    public boolean updateEmail(String u_id, String email) {
        return userMapper.updateEmail(u_id, email);
    }

    public boolean updateHobby(String u_id, String hobby) {
        return userMapper.updateHobby(u_id, hobby);
    }

    public boolean updateSignature(String u_id, String signature) {
        return userMapper.updateSignature(u_id, signature);
    }

    public boolean updateItem(String u_id, String item) {
        return userMapper.updateItem(u_id, item);
    }

    public boolean updateGender(String u_id, int gender) {
        return userMapper.updateGender(u_id, gender);
    }

    public boolean updateMajor(String u_id, int major) {
        return userMapper.updateMajor(u_id, major);
    }


    public boolean updateAvatar(String u_id, String url) {
        String url_temp = MyConfig.ADDR + "/img/avatar/" + url + ".jpg";
        return userMapper.updateAvatar(u_id, url_temp);
    }


    public boolean followCourse(String c_no, String u_id) {
        return courseFollowMapper.insertFollowCourse(c_no, u_id);
    }

    public boolean deleteFollowCourse(String c_no, String u_id) {
        return courseFollowMapper.deleteFollowCourse(c_no, u_id);
    }

    public List<Map<String, Object>> getFollowCourses(String u_id) {
        //        for (Map<String, Object> course : courses)
//            Course.setUrl(course);
        return courseFollowMapper.selectFollowCoursesByUid(u_id);
    }

    public boolean followUser(String following, String follower) {
        return userFollowMapper.insertFollowUser(following, follower);
    }


//    public boolean deleteFollowUser(String following, String follower) {
//        return userFollowMapper.deleteFollowUser(following, follower);
//    }
//
//    public List<Map<String, Object>> getFollowUsers(String u_id) {
//        return userFollowMapper.selectFollowUsersByUid(u_id);
//    }
//
//
//    public Integer getFollowUsersNum(String u_id) {
//
//        return userFollowMapper.selectFollowUsersNumByUid(u_id);
//    }

    public Integer getFollowCoursesNum(String followerId) {
        return courseFollowMapper.selectFollowCoursesNumByUid(followerId);
    }
}
