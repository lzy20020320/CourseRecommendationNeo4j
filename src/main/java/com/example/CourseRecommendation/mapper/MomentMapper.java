package com.example.CourseRecommendation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.CourseRecommendation.entity.Moment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface MomentMapper extends BaseMapper<Moment> {
    @Select("select m_id,moment.u_id,u_nickname,course.c_no,c_name,m_content,t_name," +
            "m_recommended,TIMESTAMPDIFF(DAY,DATE_FORMAT(m_create_time, '%Y-%m-%d'),DATE_FORMAT(NOW(), '%Y-%m-%d')) as time_diff " +
            "from moment,course,user " +
            "where moment.c_no = course.c_no and moment.u_id = user.u_id and " +
            "moment.u_id in (select user_follow.u_following_id from user_follow where user_follow.u_follower_id = #{u_id}) or " +
            "moment.c_no in (select course_follow.c_no from course_follow where course_follow.u_follower_id = #{u_id}) " +
            "order by m_create_time desc " +
            "limit #{pageBegin},#{pageSize}")
    List<Map<String, Object>> selectFollowMomentByUid(@Param("u_id") String u_id,
                                                      @Param("pageBegin")Integer pageBegin,
                                                      @Param("pageSize")Integer pageSize);;


    @Select("select count(*) " +
            "from moment " +
            "where " +
            "moment.u_id in (select user_follow.u_following_id from user_follow where user_follow.u_follower_id = #{u_id}) or " +
            "moment.c_no in (select course_follow.c_no from course_follow where course_follow.u_follower_id = #{u_id}) ")
    Integer selectFollowMomentNum(@Param("u_id") String u_id);


    @Select("select m_id,moment.u_id,u_nickname,course.c_no,c_name,m_content,t_name," +
            "m_recommended,TIMESTAMPDIFF(DAY,DATE_FORMAT(m_create_time, '%Y-%m-%d'),DATE_FORMAT(NOW(), '%Y-%m-%d')) as time_diff " +
            "from moment,course,user " +
            "where moment.u_id = #{u_id} and moment.c_no = course.c_no and moment.u_id = user.u_id " +
            "order by m_create_time desc " +
            "limit #{pageBegin},#{pageSize}")
    List<Map<String, Object>> selectMomentsByUid(@Param("u_id") String u_id,
                                                 @Param("pageBegin")Integer pageBegin,
                                                 @Param("pageSize")Integer pageSize);;

    @Select("select count(*) "+
            "from moment,course,user " +
            "where moment.u_id = #{u_id} and moment.c_no = course.c_no and moment.u_id = user.u_id " +
            "order by m_create_time desc")
    int selectMyMomentTotalNum(@Param("u_id") String u_id);

    @Select("select m_id,moment.u_id,u_nickname,course.c_no,c_name,m_content,t_name," +
            "m_recommended,TIMESTAMPDIFF(DAY,DATE_FORMAT(m_create_time, '%Y-%m-%d'),DATE_FORMAT(NOW(), '%Y-%m-%d')) as time_diff " +
            "from moment,course,user " +
            "where moment.c_no = #{c_no} and moment.c_no = course.c_no and moment.u_id = user.u_id " +
            "order by m_create_time desc " +
            "limit #{pageBegin},#{pageSize}")
    List<Map<String, Object>> selectMomentsByCno(@Param("c_no") String c_no,
                                                 @Param("pageBegin")Integer pageBegin,
                                                 @Param("pageSize")Integer pageSize);

    @Select("select count(*) " +
            "from moment,course,user " +
            "where moment.c_no = #{c_no} and moment.c_no = course.c_no and moment.u_id = user.u_id " +
            "order by m_create_time desc")
    int selectCourseMomentTotalNum(@Param("c_no") String c_no);
}
