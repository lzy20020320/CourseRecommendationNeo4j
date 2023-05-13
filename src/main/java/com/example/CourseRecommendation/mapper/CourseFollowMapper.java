package com.example.CourseRecommendation.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface CourseFollowMapper {
    @Insert("insert ignore into course_follow(c_no, u_follower_id)" +
            "value (#{c_no},#{u_id})")
    boolean insertFollowCourse(@Param("c_no") String c_no,
                               @Param("u_id") String u_id);

    @Delete("delete from course_follow " +
            "where c_no = #{c_no} and u_follower_id = #{u_id}")
    boolean deleteFollowCourse(@Param("c_no") String c_no,
                               @Param("u_id") String u_id);

    @Select("select course.* " +
            "from course_follow,course " +
            "where course_follow.c_no = course .c_no and u_follower_id = #{u_id}")
    List<Map<String, Object>> selectFollowCoursesByUid(@Param("u_id") String u_id);


    @Select("select course.c_no " +
            "from course_follow,course " +
            "where course_follow.c_no = course .c_no and u_follower_id = #{u_id}")
    List<String> selectFollowCnoByUid(@Param("u_id") String u_id);


    @Select("select count(*) " +
            "from course_follow,course " +
            "where course_follow.c_no = course .c_no and u_follower_id = #{u_id}")
    Integer selectFollowCoursesNumByUid(String followerId);

    @Select("select count(*) from course_follow where c_no = #{c_no} and u_follower_id = #{u_id}")
    int isFollowed(@Param("u_id") String u_id,
                   @Param("c_no") String c_no);
}
