package com.example.CourseRecommendation.controller.user;

import com.example.CourseRecommendation.entity.OpenCourse;
import com.example.CourseRecommendation.entity.StudentInfo;
import com.example.CourseRecommendation.service.CourseService;
import com.example.CourseRecommendation.service.UserService;
import com.example.CourseRecommendation.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/lessonPlan/insert")
    public boolean insertLessonPlan(@RequestBody StudentInfo studentInfo) {
        return userService.insertAllLessonPlan(studentInfo.getStudent_id(), studentInfo.getStudent_pwd());
    }


    @GetMapping("/lessonPlan/get")
    public List<Map<String, Object>> getLessonPlan(@RequestParam String student_id) {
        return userService.getLessonPlan(student_id);
    }

    @GetMapping("/lessonPlan/selected")
    public List<Map<String, Object>> getSelectedLesson(@RequestParam String student_id) {
        return userService.getSelectedLesson(student_id);
    }

    @GetMapping("/recommendedCourse/get")
    public List<OpenCourse> getRecommendedCourses(@RequestParam String student_id) {
        return userService.getRecommendedCourses(student_id);
    }

    @PostMapping("/login")
    public boolean login() {
        return false;
    }

    @PostMapping("/sign")
    public boolean sign() {
        return false;
    }

    @DeleteMapping("/delete")
    public boolean deleteUser(@RequestParam String u_id) {
        return userService.removeById(u_id);
    }


    @GetMapping("/followUser")
    public boolean followUser(@RequestParam String following_id, @RequestParam String follower_id) {
        return userService.followUser(following_id, follower_id);
    }

    @DeleteMapping("/followUser/delete")
    public boolean deleteFollowUser(@RequestParam String following_id, @RequestParam String follower_id) {
        return userService.deleteFollowUser(following_id, follower_id);
    }

    @GetMapping("/followUser/get")
    public List<Map<String, Object>> getFollowUsers(@RequestParam String follower_id) {
        return userService.getFollowUsers(follower_id);
    }

    @GetMapping("/followCourse")
    public boolean followCourse(@RequestParam String following_cno, @RequestParam String follower_id) {
        return userService.followCourse(following_cno, follower_id);
    }

    @DeleteMapping("/followCourse/delete")
    public boolean deleteFollowCourse(@RequestParam String following_cno, @RequestParam String follower_id) {
        return userService.deleteFollowCourse(following_cno, follower_id);
    }

    @GetMapping("/followCourse/get")
    public List<Map<String, Object>> getFollowCourses(@RequestParam String follower_id) {
        return userService.getFollowCourses(follower_id);
    }


//    @GetMapping("/img")    // Test
//    public ResponseEntity<byte[]> getImg() {
//        InputStream inputStream = HttpUtils.getInputStream("https://i1.hdslb.com/bfs/face/6313ddfd03395ade78773a2f064ba320cdadb8b3.jpg@120w_120h_1c");
//        byte[] bytesByStream = HttpUtils.getBytesByStream(inputStream);
//        final HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_PNG);
//        return new ResponseEntity<>(bytesByStream, headers, HttpStatus.OK);
//    }
}
