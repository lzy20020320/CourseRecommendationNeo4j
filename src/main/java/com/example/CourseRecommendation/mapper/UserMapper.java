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


    @Select("select u_id, u_student_id, u_nickname, u_college " +
            "from user where u_id=#{u_id}")
    Map<String, Object> selectById(@Param("u_id") String u_id);



    @Select("select u_id, u_student_id, u_nickname, u_college from user where u_id=#{u_id} and u_pwd = #{u_pwd}")
    Map<String, Object> selectByIdAndPwd(@Param("u_id") String u_id,@Param("u_pwd") String u_pwd);

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

    @Select("select lesson_plan.c_no,c_name,lp_category,lp_select " +
            "from lesson_plan,course " +
            "where lesson_plan.c_no = course.c_no and u_student_id = #{u_student_id} and lp_select = 1")
    List<Map<String, Object>> selectSelectedLessonPlanBySid(@Param("u_student_id") String student_id);


    @Update("update user " +
            "set u_student_id = #{nickname} " +
            "where u_id=#{u_id}")
    boolean updateNickname(@Param("u_id") String uId,
                           @Param("nickname") String nickName);
}
