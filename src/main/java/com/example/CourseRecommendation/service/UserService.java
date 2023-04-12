package com.example.CourseRecommendation.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.CourseRecommendation.config.MyConfig;
import com.example.CourseRecommendation.dao.CourseRepository;
import com.example.CourseRecommendation.dao.UserRepository;
import com.example.CourseRecommendation.entity.OpenCourse;
import com.example.CourseRecommendation.entity.User;
import com.example.CourseRecommendation.mapper.*;
import com.example.CourseRecommendation.entity.Course;
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
        List<Neo4jCourse> neo4jCourses = courseRepository.getRecommendedCourseByRecommendation(studentId);
        if (neo4jCourses.size() < min_len)
            neo4jCourses.addAll(courseRepository.getRecommendedCourseBySelection(studentId));
        if (neo4jCourses.size() < min_len) {
            List<Map<String, Object>> courses = courseMapper.recommendByCategory(studentId);
            for (Map<String, Object> course : courses) {
                neo4jCourses.add(Neo4jCourse.Map2Neo4jCourse(course));
            }
        }
        return neo4jCourses;
    }

    public Map<String, Object> login(String u_id) {
        userMapper.creatUser(u_id);
        Map<String, Object> userInfo = userMapper.selectById(u_id);
        userInfo.put("url", MyConfig.ADDR + "/img/courseicon/icon.jpg");
        return userInfo;
    }

    public boolean updateNickname(String u_id, String nickName) {
        return userMapper.updateNickname(u_id, nickName);
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

    public boolean updateAvatar(String uId) {
        return false;
    }
}
