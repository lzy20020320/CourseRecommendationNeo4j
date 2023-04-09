package com.example.CourseRecommendation.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.CourseRecommendation.entity.OpenCourse;
import com.example.CourseRecommendation.entity.User;
import com.example.CourseRecommendation.mapper.*;
import com.example.CourseRecommendation.utils.CourseClassifier;
import com.example.CourseRecommendation.utils.crawler.java.CourseGetter.CourseGetter;
import com.example.CourseRecommendation.utils.crawler.java.CourseGetter.SelectedCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public boolean insertAllLessonPlan(String student_id, String student_pwd) {
        List<SelectedCourse> selectedCourses = CourseGetter.UpdatePersonalCourses(student_id, student_pwd);
        for (SelectedCourse selectedCourse : selectedCourses) {
            if (courseMapper.updateCourse(selectedCourse.getCourseId(),
                    selectedCourse.getCourseName(), selectedCourse.getCredits()) > 0) {
                try {
                    String category = CourseClassifier.courseClassify(selectedCourse.getCourseName());
                    courseMapper.updateCourseCategory(selectedCourse.getCourseId(), category);
                } catch (Exception ignored) {
                }
            }
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

    public List<OpenCourse> getRecommendedCourses(String studentId) {
        return null;
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
}
