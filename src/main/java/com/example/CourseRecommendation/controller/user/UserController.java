package com.example.CourseRecommendation.controller.user;

import com.example.CourseRecommendation.controller.message.Message;
import com.example.CourseRecommendation.dao.UserRepository;
import com.example.CourseRecommendation.entity.OpenCourse;
import com.example.CourseRecommendation.entity.StudentInfo;
import com.example.CourseRecommendation.entity.Course;
import com.example.CourseRecommendation.entity.User;
import com.example.CourseRecommendation.node.Neo4jCourse;
import com.example.CourseRecommendation.service.CourseService;
import com.example.CourseRecommendation.service.UserService;
import com.example.CourseRecommendation.utils.DownloadImg;
import com.example.CourseRecommendation.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;


    @GetMapping("/login")
    public Map<String, Object> login(@RequestParam("openid") String openid,
                                     @RequestParam("user_pwd") String password) {
        if (openid.length() != 11)
            return userService.login(openid);
        else
            return userService.login(openid, password);
    }

    @GetMapping("/sign")
    public Map<String, Object> sign(@RequestParam("openid") String openid,
                                    @RequestParam("user_pwd") String password) {
        return userService.sign(openid, password);
    }

    @GetMapping("/updateNickname")
    public boolean updateNickname(@RequestParam("openid") String u_id,
                                  @RequestParam("nickname") String nickname) {
        return userService.updateNickname(u_id, nickname);
    }


    @GetMapping("/updateAvatar")
    public void updateAvatar(@RequestParam("openid") String u_id,
                             @RequestParam("url") String base64) {
//        InputStream inputStream = HttpUtils.getInputStream(url);
//        byte[] bytesByStream = HttpUtils.getBytesByStream(inputStream);
//        final HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_PNG);
//        return new ResponseEntity<>(bytesByStream, headers, HttpStatus.OK);
        try {
            String t = String.valueOf((new Date().getTime()));
            DownloadImg.saveImageFromBase64(base64, "src\\main\\resources\\static\\img\\avatar\\" + u_id + t + ".jpg");
            userService.updateAvatar(u_id, u_id + t);
        } catch (Exception ignore) {
            System.out.println("error");
        }
    }

    @GetMapping("/bindStudent")
    public boolean bindStudentId(@RequestParam("openid") String u_id,
                                 @RequestParam("student_id") String student_id,
                                 @RequestParam("student_pwd") String student_pwd) {
        userService.bindStudentId(u_id, student_id);
        return userService.insertAllLessonPlan(u_id, student_id, student_pwd);
    }


    @GetMapping("/lessonPlan/insert")
    public boolean insertLessonPlan(@RequestParam("openid") String u_id,
                                    @RequestParam("student_id") String student_id,
                                    @RequestParam("student_pwd") String student_pwd) {
        return userService.insertAllLessonPlan(u_id, student_id, student_pwd);
    }


    @GetMapping("/lessonPlan/get")
    public List<Map<String, Object>> getLessonPlan(@RequestParam("student_id") String student_id) {
        return userService.getLessonPlan(student_id);
    }

    @GetMapping("/lessonPlan/selected")
    public List<Map<String, Object>> getSelectedLesson(@RequestParam("student_id") String student_id) {
        return userService.getSelectedLesson(student_id);
    }

    @GetMapping("/recommendedCourse/get")
    public List<Neo4jCourse> getRecommendedCourses(@RequestParam("openid") String openid) {
        return userService.getRecommendedCourses(openid);

    }


//    @DeleteMapping("/delete")
//    public boolean deleteUser(@RequestParam String u_id) {
//        return userService.removeById(u_id);
//    }


    @GetMapping("/followUser")
    public boolean followUser(@RequestParam("followed_openid") String followed_id,
                              @RequestParam("follower_openid") String follower_id) {
        return userService.followUser(followed_id, follower_id);
    }

    @DeleteMapping("/followUser/delete")
    public boolean deleteFollowUser(@RequestParam("followed_openid") String followed_id,
                                    @RequestParam("follower_openid") String follower_id) {
        return userService.deleteFollowUser(followed_id, follower_id);
    }

    @GetMapping("/followUser/get")
    public List<Map<String, Object>> getFollowUsers(@RequestParam("follower_openid") String follower_id) {
        return userService.getFollowUsers(follower_id);
    }

    @GetMapping("/followUser/getNum")
    public Map<String, Object> getFollowUsersNum(@RequestParam("follower_openid") String follower_id) {
        Message message = new Message();
        message.setMessage(userService.getFollowUsersNum(follower_id));
        return message;
    }

    @GetMapping("/followCourse")
    public boolean followCourse(@RequestParam("followed_course_no") String following_cno,
                                @RequestParam("follower_openid") String follower_id) {
        return userService.followCourse(following_cno, follower_id);
    }

    @DeleteMapping("/followCourse/delete")
    public boolean deleteFollowCourse(@RequestParam("followed_course_no") String following_cno,
                                      @RequestParam("follower_openid") String follower_id) {
        return userService.deleteFollowCourse(following_cno, follower_id);
    }

    @GetMapping("/followCourse/get")
    public List<Map<String, Object>> getFollowCourses(@RequestParam("follower_openid") String follower_id) {
        return userService.getFollowCourses(follower_id);
    }

    @GetMapping("/followCourse/getNum")
    public Map<String, Object> getFollowCoursesNum(@RequestParam("follower_openid") String follower_id) {
        Message message = new Message();
        message.setMessage(userService.getFollowCoursesNum(follower_id));
        return message;
    }


}
