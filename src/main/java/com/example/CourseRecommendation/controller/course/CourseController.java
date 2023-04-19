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
import java.util.Random;

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
    public Map<String, Object> getCourseDetail(@RequestParam("openid") String openid,
                                               @RequestParam("course_no") String course_no) {
        Message message = new Message();
        Map<String, Object> data = courseService.selectById(course_no);
        data.put("followed",courseService.isFollowed(course_no,openid));
        message.setMessage(data);
        return message;
    }

    @GetMapping("/random")
    public Map<String, Object> getRandomCourse() {
        Message message = new Message();
        Random rand = new Random();
        List<Map<String, Object>> hotCourses = courseService.selectHotCourse();
        Map<String, Object> randomCourse = hotCourses.get(rand.nextInt(hotCourses.size()));
        message.setMessage(randomCourse.get("c_no"));
        return message;
    }

    @GetMapping("/hot")
    public Map<String, Object> getHotCourse() {
        Message message = new Message();
        message.setMessage(courseService.selectHotCourse());
        return message;
    }

}
