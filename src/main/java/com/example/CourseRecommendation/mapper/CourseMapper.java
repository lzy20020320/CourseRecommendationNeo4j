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


    @Update("update course " +
            "set c_url = #{url} " +
            "where c_no = #{no}")
    boolean updateCourseUrl(@Param("no") String no, @Param("url") String url);

    @Update("update course " +
            "set c_th_url = #{th_url} " +
            "where c_no = #{no}")
    boolean updateCourseThUrl(@Param("no") String no, @Param("th_url") String th_url);

    @Select("select c_no from course")
    List<String> selectAllCno();

    @Select("select * from course where c_no = #{no} ")
    Map<String, Object> selectByNo(@Param("no") String no);


    @Select("select moment.c_no, c_name, c_url,c_th_url,c_aim,c_category,COUNT(m_recommended) as r_num " +
            "from course,moment " +
            "where m_recommended = 1 and " +
            "      course.c_no = moment.c_no " +
            "group by moment.c_no, c_name " +
            "order by r_num desc " +
            "limit 30")
    List<Map<String, Object>> selectHotCourse();


    @Select("select moment.c_no, c_name, c_url,c_th_url,c_aim,c_category,COUNT(m_recommended) as r_num " +
            "from course,moment " +
            "where m_recommended = 1 and " +
            "      course.c_no = moment.c_no and " +
            "      course.c_category = #{category} " +
            "group by moment.c_no, c_name " +
            "order by r_num desc " +
            "limit 30")
    List<Map<String, Object>> selectCategoryHotCourse(@Param("category") String category);


    @Select("select * from course")
    List<Map<String, Object>> selectAll();


    @Select("select * " +
            "from course " +
            "where c_name like concat('%',#{c_name},'%')")
    List<Map<String, Object>> findCourse(@Param("c_name") String c_name);

    @Select("select c_no ," +
            "            c_name," +
            "            c_credit," +
            "            c_category," +
            "            c_aim," +
            "            c_content," +
            "            c_reference," +
            "            c_prerequisite," +
            "            c_target_student," +
            "            c_url," +
            "            c_th_url " +
            "                    from (select course.c_no," +
            "                            c_name," +
            "                            c_credit," +
            "                            c_category," +
            "                            c_aim," +
            "                            c_content," +
            "                            c_reference," +
            "                            c_prerequisite," +
            "                            c_target_student," +
            "                            c_url," +
            "                            c_th_url," +
            "                            count(m_recommended) as r_num" +
            "                            from moment," +
            "                            course" +
            "                            where m_recommended = 1" +
            "                            and moment.c_no = course.c_no" +
            "                            and course.c_category =" +
            "                                    (select cn.category" +
            "                            from (select course.c_category as category, count(c_category) as num" +
            "                                    from lesson_plan," +
            "                                    course" +
            "                                    where u_student_id = (select user.u_student_id" +
            "                                    from user" +
            "                                    where u_id = #{u_id})" +
            "                            and lp_category = '通识课'" +
            "                            and lesson_plan.c_no = course.c_no" +
            "                            group by course.c_category " +
            "                            order by num desc" +
            "                            limit 1) as cn)" +
            "    group by c_no" +
            "    order by r_num desc) as `c.*rn` " +
            "limit 30 ")
    List<Map<String, Object>> recommendByCategory(@Param("u_id") String u_id);
}
