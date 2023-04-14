package com.example.CourseRecommendation.mapper;

import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserFollowMapper {

    @Insert("insert ignore into user_follow(u_following_id, u_follower_id)" +
            "value (#{following_id},#{follower_id})")
    boolean insertFollowUser(@Param("following_id") String following_id,
                             @Param("follower_id") String follower_id);

    @Delete("delete from user_follow " +
            "where u_following_id = #{following_id} and u_follower_id = #{follower_id}")
    boolean deleteFollowUser(@Param("following_id") String following_id,
                             @Param("follower_id") String follower_id);

    @Select("select user.*" +
            "from user_follow,user " +
            "where user_follow.u_following_id = user.u_id and u_follower_id = #{u_id}")
    List<Map<String, Object>> selectFollowUsersByUid(@Param("u_id") String u_id);


    @Select("select count(*)" +
            "from user_follow,user " +
            "where user_follow.u_following_id = user.u_id and u_follower_id = #{u_id}")
    Integer selectFollowUsersNumByUid(@Param("u_id") String u_id);
}
