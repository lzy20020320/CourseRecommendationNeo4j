package com.example.CourseRecommendation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.CourseRecommendation.entity.Course;
import com.example.CourseRecommendation.utils.crawler.java.CourseGetter.CourseInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {
    @Insert("insert ignore into course(c_no, c_name, c_credit)" +
            "value (#{c_no},#{c_name},#{c_credit})")
    int updateCourse(@Param("c_no") String no, @Param("c_name") String name, @Param("c_credit") float credit);

    @Update("update course " +
            "set c_aim = #{aim}, c_content = #{content}, c_reference = #{reference},c_prerequisite = #{requisites},c_target_student = #{target} " +
            "where c_no = #{no}")
    boolean updateCourseInfo(CourseInfo courseInfo);

    @Update("update course " +
            "set c_category = #{category} " +
            "where c_no = #{no}")
    boolean updateCourseCategory(@Param("no") String no, @Param("category") String category);

    @Select("select c_no from course")
    List<String> selectAllCno();

    @Select("select * from course where c_no = #{no} ")
    Map<String,Object> selectByNo(@Param("no") String no);
}
