package com.example.CourseRecommendation.controller.courselist;

import com.example.CourseRecommendation.config.MyConfig;
import com.example.CourseRecommendation.entity.Course;
import com.example.CourseRecommendation.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/courselist")
public class CourseListController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/categories")
    public Map<String, Object> getCategories(){
        CourseListMessage courseListMessage = new CourseListMessage();
        List<Course> courses = courseService.selectAll();
        for (Course course :courses)
            courseListMessage.addCourse(course.getName(),course.getCategory(),course.getNo(), course.getUrl());
        return courseListMessage;
    }
}
