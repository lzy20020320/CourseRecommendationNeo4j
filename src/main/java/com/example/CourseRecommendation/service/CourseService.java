package com.example.CourseRecommendation.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.CourseRecommendation.entity.Course;
import com.example.CourseRecommendation.mapper.CourseFollowMapper;
import com.example.CourseRecommendation.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CourseService extends ServiceImpl<CourseMapper, Course> {

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    CourseFollowMapper courseFollowMapper;

    public List<Map<String,Object>> find(String course_name) {
        return courseMapper.findCourse(course_name);
    }

    public List<Map<String,Object>> selectAll() {
        //        for (Map<String,Object> course : courses)
//            Course.setUrl(course);
        return courseMapper.selectAll();
    }

    public List<Course> selectByCategory(String category) {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("c_category", category);
        //        for (Course course : courses)
//            course.setUrl();
        return courseMapper.selectList(wrapper);
    }

//    public void downloadAllCourseInfo(String user_name, String pwd) {
//        CourseGetter.saveAllCourseInfo(user_name,pwd);
//    }
//
//    public void insertAllCourseInfo() {
//        List<String> cnoList = courseMapper.selectAllCno();
//        for(String cno :cnoList)
//            courseMapper.updateCourseInfo(CourseGetter.getCourseInfo(cno));
//    }

    public List<Map<String, Object>> selectHotCourse() {
        //        for (Map<String, Object> course : courses)
//            Course.setUrl(course);
        return courseMapper.selectHotCourse();
    }

    public Map<String, Object> selectById(String no) {
        //        Course.setUrl(course);
        return courseMapper.selectByNo(no);
    }

    public int isFollowed(String c_no, String u_id) {
        if (courseFollowMapper.isFollowed(u_id, c_no) > 0)
            return 1;
        else return 0;
    }


}
