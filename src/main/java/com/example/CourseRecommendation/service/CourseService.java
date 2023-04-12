package com.example.CourseRecommendation.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.CourseRecommendation.entity.Course;
import com.example.CourseRecommendation.mapper.CourseMapper;
import com.example.CourseRecommendation.utils.crawler.java.CourseGetter.CourseGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CourseService extends ServiceImpl<CourseMapper, Course> {

    @Autowired
    CourseMapper courseMapper;

    public List<Course> find(String course_name) {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.like("c_name", course_name);
        return courseMapper.selectList(wrapper);
    }

    public List<Course> selectAll() {
        return courseMapper.selectList(null);
    }
    public List<Course> selectByCategory(String category) {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("c_category", category);
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

    public Map<String,Object> selectById(String no){
        return courseMapper.selectByNo(no);
    }


}
