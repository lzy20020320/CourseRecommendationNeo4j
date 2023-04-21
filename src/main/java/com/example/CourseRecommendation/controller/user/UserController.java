package com.example.CourseRecommendation.controller.user;

import com.example.CourseRecommendation.config.MyConfig;
import com.example.CourseRecommendation.controller.message.Message;
import com.example.CourseRecommendation.dao.UserRepository;
import com.example.CourseRecommendation.node.Neo4jCourse;
import com.example.CourseRecommendation.service.UserService;
import com.example.CourseRecommendation.utils.DownloadImg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/get_openid")
    public String getOpenid(@RequestParam("js_code") String js_code) {
        return userService.getOpenid(js_code);
    }

    @GetMapping("/login")
    public Map<String, Object> login(@RequestParam("openid") String openid,
                                     @RequestParam("user_pwd") String password) {
        userService.followUser(openid, openid);
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


    @GetMapping("/updateGender")
    public boolean updateGender(@RequestParam("openid") String u_id,
                                @RequestParam("gender") int gender) {
        return userService.updateGender(u_id, gender);
    }

    @GetMapping("/updateSchool")
    public boolean updateSchool(@RequestParam("openid") String u_id,
                                @RequestParam("school") String school) {
        return userService.updateSchool(u_id, school);
    }

    @GetMapping("/updateBirthday")
    public boolean updateBirthday(@RequestParam("openid") String u_id,
                                  @RequestParam("birthday") String birthday) {
        return userService.updateBirthday(u_id, birthday);
    }

    @GetMapping("/updateEmail")
    public boolean updateEmail(@RequestParam("openid") String u_id,
                               @RequestParam("email") String email) {
        return userService.updateEmail(u_id, email);
    }

    @GetMapping("/updateHobby")
    public boolean updateHobby(@RequestParam("openid") String u_id,
                               @RequestParam("hobby") String hobby) {
        return userService.updateHobby(u_id, hobby);
    }

    @GetMapping("/updateSignature")
    public boolean updateSignature(@RequestParam("openid") String u_id,
                                   @RequestParam("signature") String signature) {
        return userService.updateSignature(u_id, signature);
    }

    @GetMapping("/updateMajor")
    public boolean updateMajor(@RequestParam("openid") String u_id,
                               @RequestParam("major") int major) {
        return userService.updateMajor(u_id, major);
    }


    @GetMapping("/updateAvatar")
    public void updateAvatar(@RequestParam("openid") String u_id,
                             @RequestParam("url") String base64) {

        try {
            String t = String.valueOf((new Date().getTime()));
            DownloadImg.saveImageFromBase64(base64, MyConfig.RESOURCE_PATH + "static\\img\\avatar\\" + u_id + t + ".jpg");
            userService.updateAvatar(u_id, u_id + t);
        } catch (Exception ignore) {
            System.out.println("error");
        }
    }

    @GetMapping("/bindStudent")
    public String bindStudentId(@RequestParam("openid") String u_id,
                                @RequestParam("student_id") String student_id,
                                @RequestParam("student_pwd") String student_pwd) {
        userService.bindStudentId(student_id, u_id);
        return userService.insertAllLessonPlan(u_id, student_id, student_pwd);
    }


    @GetMapping("/lessonPlan/insert")
    public String insertLessonPlan(@RequestParam("openid") String u_id,
                                   @RequestParam("student_id") String student_id,
                                   @RequestParam("student_pwd") String student_pwd) {
        return userService.insertAllLessonPlan(u_id, student_id, student_pwd);
    }


    @GetMapping("/lessonPlan/get")
    public List<Map<String, Object>> getLessonPlan(@RequestParam("student_id") String student_id) {
        return userService.getLessonPlan(student_id);
    }

    @GetMapping("/lessonPlan/selected")
    public Map<String, Object> getSelectedLesson(@RequestParam("student_id") String student_id) {
        Message message = new Message();
        message.setMessage(userService.getSelectedLesson(student_id));
        return message;
    }

    @GetMapping("/recommendedCourse/get")
    public Map<String, Object> getRecommendedCourses(@RequestParam("openid") String openid) {
        Message message = new Message();
        message.setMessage(userService.getRecommendedCourses(openid));
        return message;

    }

    @GetMapping("/recommendedCourse/get_col")
    public Map<String, Object> getRecommendedCourseCol(@RequestParam("openid") String openid) {
        Message message = new Message();
        message.setMessage(userService.recommendCourseCol());
        return message;

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

//    @DeleteMapping("/followUser/delete")
//    public boolean deleteFollowUser(@RequestParam("followed_openid") String followed_id,
//                                    @RequestParam("follower_openid") String follower_id) {
//        return userService.deleteFollowUser(followed_id, follower_id);
//    }
//
//    @GetMapping("/followUser/get")
//    public List<Map<String, Object>> getFollowUsers(@RequestParam("follower_openid") String follower_id) {
//        return userService.getFollowUsers(follower_id);
//    }
//
//    @GetMapping("/followUser/getNum")
//    public Map<String, Object> getFollowUsersNum(@RequestParam("follower_openid") String follower_id) {
//        Message message = new Message();
//        message.setMessage(userService.getFollowUsersNum(follower_id));
//        return message;
//    }

    @GetMapping("/followCourse")
    public boolean followCourse(@RequestParam("followed_course_no") String following_cno,
                                @RequestParam("follower_openid") String follower_id) {
        return userService.followCourse(following_cno, follower_id);
    }

    @GetMapping("/followCourse/delete")
    public boolean deleteFollowCourse(@RequestParam("followed_course_no") String following_cno,
                                      @RequestParam("follower_openid") String follower_id) {
        System.out.println("test");
        return userService.deleteFollowCourse(following_cno, follower_id);
    }

    @GetMapping("/followCourse/get")
    public Map<String, Object> getFollowCourses(@RequestParam("follower_openid") String follower_id) {
        Message message = new Message();
        message.setMessage(userService.getFollowCourses(follower_id));
        return message;
    }

    @GetMapping("/followCourse/getNum")
    public Map<String, Object> getFollowCoursesNum(@RequestParam("follower_openid") String follower_id) {
        Message message = new Message();
        message.setMessage(userService.getFollowCoursesNum(follower_id));
        return message;
    }


}
