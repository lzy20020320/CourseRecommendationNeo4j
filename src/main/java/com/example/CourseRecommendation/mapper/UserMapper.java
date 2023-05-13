package com.example.CourseRecommendation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.CourseRecommendation.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper extends BaseMapper<User> {


    @Insert("insert ignore into course_recommendation.user(u_id) " +
            "VALUES (#{u_id})")
    int createWxUser(@Param("u_id") String u_id);


    @Insert("insert ignore into course_recommendation.user(u_id,u_pwd) " +
            "VALUES (#{u_id},#{u_pwd})")
    int createUser(@Param("u_id") String u_id, @Param("u_pwd") String u_pwd);


    @Select("select u_id, u_student_id, u_nickname, u_gender, u_hobby, u_birthday, u_email, u_url, u_major, u_school, u_signature, u_authenticated,u_item " +
            "from user where u_id=#{u_id}")
    Map<String, Object> selectById(@Param("u_id") String u_id);


    @Select("select u_id, u_student_id, u_nickname, u_gender, u_hobby, u_birthday, u_email, u_url, u_major, u_school, u_signature, u_authenticated,u_item " +
            "from user where u_id=#{u_id} and u_pwd = #{u_pwd}")
    Map<String, Object> selectByIdAndPwd(@Param("u_id") String u_id, @Param("u_pwd") String u_pwd);

    @Update("update user " +
            "set u_student_id = #{student_id} " +
            "where u_id=#{u_id}")
    boolean updateStudentId(@Param("student_id") String student_id,
                            @Param("u_id") String u_id);


    @Insert("insert ignore into course_recommendation.lesson_plan(u_student_id, c_no,lp_category, lp_select_term,lp_select) " +
            "VALUES (#{u_student_id},#{c_no},#{lp_category},#{lp_select_term},#{lp_select})")
    boolean insertLessonPlan(@Param("u_student_id") String student_id,
                             @Param("c_no") String c_no,
                             @Param("lp_category") String category,
                             @Param("lp_select_term") String select_term,
                             @Param("lp_select") boolean select);

    @Select("select lesson_plan.c_no,c_name,lp_category,lp_select " +
            "from lesson_plan,course " +
            "where lesson_plan.c_no = course.c_no and u_student_id = #{u_student_id}")
    List<Map<String, Object>> selectLessonPlanBySid(@Param("u_student_id") String student_id);

    @Select("select course.c_no, c_name, c_credit, c_url,c_th_url, c_category, c_aim, c_content, c_reference, c_prerequisite, c_target_student " +
            "from lesson_plan,course " +
            "where lesson_plan.c_no = course.c_no and u_student_id = #{u_student_id} and lp_select = 1")
    List<Map<String, Object>> selectSelectedLessonPlanBySid(@Param("u_student_id") String student_id);


    @Select("select count(*) " +
            "from lesson_plan,course,user " +
            "where lesson_plan.c_no = course.c_no " +
            "and user.u_id = #{u_id} " +
            "and lp_select = 1 " +
            "and user.u_student_id = lesson_plan.u_student_id")
    int selectSelectedLessonPlanNumByUid(@Param("u_id") String u_id);



    @Select("select u_url " +
            "from user " +
            "where user.u_id = #{u_id} ")
    String selectUrlByUid(@Param("u_id") String u_id);


    @Update("update user " +
            "set u_nickname = #{nickname} " +
            "where u_id=#{u_id}")
    boolean updateNickname(@Param("u_id") String uId,
                           @Param("nickname") String nickName);

    @Update("update user " +
            "set u_school = #{school} " +
            "where u_id=#{u_id}")
    boolean updateSchool(@Param("u_id") String uId,
                         @Param("school") String school);


    @Update("update user " +
            "set u_email = #{email} " +
            "where u_id=#{u_id}")
    boolean updateEmail(@Param("u_id") String uId,
                        @Param("email") String email);

    @Update("update user " +
            "set u_hobby = #{hobby} " +
            "where u_id=#{u_id}")
    boolean updateHobby(@Param("u_id") String uId,
                        @Param("hobby") String hobby);


    @Update("update user " +
            "set u_signature = #{signature} " +
            "where u_id=#{u_id}")
    boolean updateSignature(@Param("u_id") String uId,
                            @Param("signature") String signature);


    @Update("update user " +
            "set u_item = #{u_item} " +
            "where u_id=#{u_id}")
    boolean updateItem(@Param("u_id") String uId,
                                @Param("u_item") String item);

    @Update("update user " +
            "set u_authenticated = #{authenticated} " +
            "where u_id=#{u_id}")
    boolean updateAuthenticated(@Param("u_id") String uId,
                                @Param("authenticated") String authenticated);

    @Update("update user " +
            "set u_birthday = #{birthday} " +
            "where u_id=#{u_id}")
    boolean updateBirthday(@Param("u_id") String uId,
                           @Param("birthday") String birthday);

    @Update("update user " +
            "set u_gender = #{gender} " +
            "where u_id=#{u_id}")
    boolean updateGender(@Param("u_id") String uId,
                         @Param("gender") int gender);


    @Update("update user " +
            "set u_major = #{major} " +
            "where u_id=#{u_id}")
    boolean updateMajor(@Param("u_id") String uId,
                        @Param("major") int major);


    @Update("update user " +
            "set u_url = #{url} " +
            "where u_id=#{u_id}")
    boolean updateAvatar(@Param("u_id") String uId,
                         @Param("url") String url);
}
