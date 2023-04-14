package com.example.CourseRecommendation.controller.course;

import com.example.CourseRecommendation.config.MyConfig;
import com.example.CourseRecommendation.controller.message.Message;
import com.example.CourseRecommendation.entity.Course;
import com.example.CourseRecommendation.service.CourseService;
import com.example.CourseRecommendation.service.OpenCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private OpenCourseService openCourseService;
    @Autowired
    private CourseService courseService;

//    @GetMapping("/openCourse/saveAll")
//    public void saveAllOpenCourse() {
//        openCourseService.saveAllOpenCourse();
//    }
//
//    @GetMapping("/insertAllInfo")
//    public void insertAllCourseInfo(@RequestBody StudentInfo studentInfo) {
//        courseService.downloadAllCourseInfo(studentInfo.getStudent_id(), studentInfo.getStudent_pwd());
//        courseService.insertAllCourseInfo();
//    }
//
//    @DeleteMapping("/openCourse/delete")
//    public boolean deleteOpenCourse(@RequestParam String c_no) {
//        return openCourseService.removeById(c_no);
//    }

    @GetMapping("/find")
    public List<Course> findCourse(@RequestParam("course_name") String course_name) {
        return courseService.find(course_name);
    }

    @GetMapping("/detail")
    public Map<String,Object> getCourseDetail(@RequestParam("course_no") String course_no) {
        Message message = new Message();
        Map<String,Object> data = courseService.selectById(course_no);
        message.setMessage(data);
        return message;
    }

}
