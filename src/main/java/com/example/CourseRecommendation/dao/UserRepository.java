package com.example.CourseRecommendation.dao;

import com.example.CourseRecommendation.node.User;
import org.springframework.data.repository.query.Param;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface UserRepository extends Neo4jRepository<User, Long> {
    @Query("merge (:user{openid: $openid})")
    void createUser(@Param(value = "openid") String id);

    @Query("match (u:user),(c:course) where u.openid = $u_id  and c.no= $c_no merge (u)-[r:RECOMMEND]->(c) return r")
    void updateUserRecommendCourse(@Param("u_id") String u_id,
                                   @Param("c_no") String c_no);

    @Query("match (u:user),(c:course) where u.openid = $u_id  and c.no= $c_no merge (u)-[r:UNLIKE]->(c) return r")
    void updateUserUnlikeCourse(@Param("u_id") String u_id,
                                @Param("c_no") String c_no);

}
