package com.example.CourseRecommendation.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.CourseRecommendation.entity.OpenCourse;
import com.example.CourseRecommendation.mapper.CourseMapper;
import com.example.CourseRecommendation.mapper.OpenCourseMapper;
import com.example.CourseRecommendation.utils.crawler.java.CourseGetter.CourseGetter;
import com.example.CourseRecommendation.utils.crawler.java.CourseGetter.CourseRaw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpenCourseService extends ServiceImpl<OpenCourseMapper, OpenCourse> {

    @Autowired
    CourseMapper courseMapper;
    @Autowired
    CourseService courseService;

    public void saveAllOpenCourse() {
        List<CourseRaw> courses = CourseGetter.GetAllCourses();
        for (CourseRaw courseRaw : courses) {
            OpenCourse openCourse = new OpenCourse();
            openCourse.setNo(courseRaw.getCourseId());
            openCourse.setTeacherName(courseRaw.getTeacherName());
            openCourse.setTeacherId(courseRaw.getTeacherId());
            openCourse.setCampus(courseRaw.getCampus());
            openCourse.setTime(courseRaw.getClassTime());
            courseMapper.updateCourse(courseRaw.getCourseId(), courseRaw.getCourseName(), courseRaw.getCredit());
            saveOrUpdate(openCourse);
        }
    }

}
