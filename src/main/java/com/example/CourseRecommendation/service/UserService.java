package com.example.CourseRecommendation.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.CourseRecommendation.config.MyConfig;
import com.example.CourseRecommendation.controller.message.Message;
import com.example.CourseRecommendation.dao.CourseRepository;
import com.example.CourseRecommendation.entity.User;
import com.example.CourseRecommendation.mapper.*;
import com.example.CourseRecommendation.node.Neo4jCourse;
import com.example.CourseRecommendation.utils.CourseClassifier;
import com.example.CourseRecommendation.utils.crawler.java.CourseGetter.CourseGetter;
import com.example.CourseRecommendation.utils.crawler.java.CourseGetter.SelectedCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Autowired
    UserMapper userMapper;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    CourseFollowMapper courseFollowMapper;

    @Autowired
    UserFollowMapper userFollowMapper;

    @Autowired
    CourseRepository courseRepository;


    public boolean bindStudentId(String student_id, String user_id) {
        return userMapper.updateStudentId(student_id, user_id);
    }


    public boolean insertAllLessonPlan(String u_id, String student_id, String student_pwd) {
        List<SelectedCourse> selectedCourses = CourseGetter.UpdatePersonalCourses(student_id, student_pwd);
        for (SelectedCourse selectedCourse : selectedCourses) {
            if (courseMapper.updateCourse(selectedCourse.getCourseId(),
                    selectedCourse.getCourseName(), selectedCourse.getCredits()) > 0) {
                try {
                    String category = CourseClassifier.courseClassify(selectedCourse.getCourseName());
                    courseMapper.updateCourseCategory(selectedCourse.getCourseId(), category);
                    // 更新neo4j
                    courseRepository.createCourse(selectedCourse.getCourseId(),
                            selectedCourse.getCourseName(), Float.toString(selectedCourse.getCredits()));
                    courseRepository.classifyCourse(selectedCourse.getCourseId(), category);
                } catch (Exception ignored) {
                }
            }
            courseRepository.selectCourse(u_id, selectedCourse.getCourseId(), selectedCourse.getCourseKind());
            userMapper.insertLessonPlan(student_id, selectedCourse.getCourseId(),
                    selectedCourse.getCourseKind(), selectedCourse.getSemester(), selectedCourse.isSelect());
        }
        return true;
    }


    public List<Map<String, Object>> getLessonPlan(String student_id) {
        return userMapper.selectLessonPlanBySid(student_id);
    }

    public List<Map<String, Object>> getSelectedLesson(String student_id) {
        return userMapper.selectSelectedLessonPlanBySid(student_id);
    }

    public List<Neo4jCourse> getRecommendedCourses(String studentId) {
        int min_len = 100;
        List<Neo4jCourse> recommendedCourses = new ArrayList<>();
        List<String> courseNames = new ArrayList<>();
        List<Neo4jCourse> recommendedCourseByRecommendation = courseRepository.getRecommendedCourseByRecommendation(studentId);

        for (Neo4jCourse course : recommendedCourseByRecommendation) {
            course.setUrl();
            recommendedCourses.add(course);
            courseNames.add(course.getName());
        }

        if (recommendedCourses.size() < min_len) {
            List<Neo4jCourse> recommendedCourseBySelection = courseRepository.getRecommendedCourseBySelection(studentId);
            for (Neo4jCourse course : recommendedCourseBySelection) {
                if (!courseNames.contains(course.getName())) {
                    course.setUrl();
                    recommendedCourses.add(course);
                    courseNames.add(course.getName());
                }
            }
        }

        if (recommendedCourses.size() < min_len) {
            List<Map<String, Object>> courses = courseMapper.recommendByCategory(studentId);
            for (Map<String, Object> course : courses) {
                Neo4jCourse neo4jCourse = Neo4jCourse.Map2Neo4jCourse(course);
                if (!courseNames.contains(neo4jCourse.getName())) {
                    neo4jCourse.setUrl();
                    recommendedCourses.add(neo4jCourse);
                    courseNames.add(neo4jCourse.getName());
                }
            }
        }

        return recommendedCourses;
    }

    public Map<String, Object> login(String u_id) {
        Message message = new Message();
        userMapper.createWxUser(u_id);
        Map<String, Object> userInfo = userMapper.selectById(u_id);
        message.setMessage(userInfo);
        return message;
    }


    public Map<String, Object> login(String u_id, String u_pwd) {
        Message message = new Message();
        Map<String, Object> userInfo = userMapper.selectByIdAndPwd(u_id, u_pwd);
        if (userInfo == null)
            message.setMeta("FAIL", 200);
        else {
            message.setMeta("SUCCESS", 200);
            message.setMessage(userInfo);
        }
        return message;
    }

    public Map<String, Object> sign(String u_id, String u_pwd) {
        Message message = new Message();
        if (u_id.length() == 11 && userMapper.createUser(u_id, u_pwd) != 0)
            message.setMeta("SUCCESS", 200);
        else
            message.setMeta("FAIL", 200);
        return message;
    }


    public boolean updateNickname(String u_id, String nickName) {
        return userMapper.updateNickname(u_id, nickName);
    }


    public boolean updateAvatar(String u_id,String url) {
        String url_temp = MyConfig.ADDR+"/img/avatar/"+url+".jpg";
        return userMapper.updateAvatar(u_id, url_temp);
    }


    public boolean followCourse(String c_no, String u_id) {
        return courseFollowMapper.insertFollowCourse(c_no, u_id);
    }

    public boolean deleteFollowCourse(String c_no, String u_id) {
        return courseFollowMapper.deleteFollowCourse(c_no, u_id);
    }

    public List<Map<String, Object>> getFollowCourses(String u_id) {
        return courseFollowMapper.selectFollowCoursesByUid(u_id);
    }

    public boolean followUser(String following, String follower) {
        return userFollowMapper.insertFollowUser(following, follower);
    }

    public boolean deleteFollowUser(String following, String follower) {
        return userFollowMapper.deleteFollowUser(following, follower);
    }

    public List<Map<String, Object>> getFollowUsers(String u_id) {
        return userFollowMapper.selectFollowUsersByUid(u_id);
    }


    public Integer getFollowUsersNum(String u_id) {

        return userFollowMapper.selectFollowUsersNumByUid(u_id);
    }

    public Integer getFollowCoursesNum(String followerId) {
        return courseFollowMapper.selectFollowCoursesNumByUid(followerId);
    }
}
