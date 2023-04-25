package com.example.CourseRecommendation.dao;

import com.example.CourseRecommendation.node.Neo4jCourse;
import org.springframework.data.repository.query.Param;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface CourseRepository extends Neo4jRepository<Neo4jCourse, Long> {


    //    @Query("MATCH(c:course) WHERE c.no=$cno RETURN *")
//    Neo4jCourse getCourseInfoByCourseNo(@Param("cno") String cno);
//
//    @Query("MATCH(n:course) WHERE n.name =~('.*' + $cname + '.*') RETURN *")
//    List<Neo4jCourse> findCoursesByName(@Param("cname") String $cname);
//
//    @Query("match(:course{no: $cno})-[r:BELONGS_TO]->(c:category) return c.name")
//    String getCategoryByCno(@Param("cno") String cno);
//
    @Query("merge (:course{no: $no,name:$name,credit:$credit,aim:'暂无简介',c_category:$category,c_url:'0',prerequisite:'无',reference:'暂无参考教材',target_student:'全体学生',content:'暂无简介'})")
    void createCourse(@Param(value = "no") String courseId,
                      @Param(value = "name") String courseName,
                      @Param(value = "credit") String credits,
                      @Param(value = "$category") String $category);

    //
    @Query("match (co:course),(ca:category) where co.no=$no and ca.name=$name merge (co)-[:BELONGS_TO]->(ca)")
    void classifyCourse(@Param(value = "no") String courseId,
                        @Param(value = "name") String categoryName);

    //
//    @Query("match (c:course),(s:student) where c.no=$courseId and s.student_id=$studentId create (s)-[r:SELECT{category:$category,term:$term,select:$select}]->(c)")
//    void insertLessonPlan(@Param("studentId") String studentId, @Param("courseId") String courseId, @Param("category") String courseKind, @Param("term") String semester, @Param("select") boolean select);
    @Query("match(u:user{openid:$openid}),(c:course{no:$no}) merge(u)-[r:SELECT{category:$name}]->(c)")
    void selectCourse(@Param("openid") String openid,
                      @Param(value = "no") String courseId,
                      @Param(value = "name") String categoryName);


    @Query("MATCH (i:user{openid:$openid}),(other:user) " +
            "where other.openid <> $openid " +
            "WITH i,other " +
            "OPTIONAL MATCH common=(i)-[:RECOMMEND]->(:course)<-[:RECOMMEND]-(other) " +
            "WITH i, other, COUNT(common) as intersection " +
            "OPTIONAL MATCH (i)-[:RECOMMEND]->(myFavour:course) " +
            "WITH i, other, intersection, collect(myFavour) as myFavour,count(myFavour) as myFavourNum  " +
            "OPTIONAL MATCH (other)-[:RECOMMEND]->(otherFavour:course)  " +
            "WHERE NOT otherFavour IN myFavour  " +
            "WITH i, other, intersection, count(otherFavour) AS recommendationNum,myFavourNum,otherFavour as recommendation  " +
            "where intersection/toFloat(recommendationNum+myFavourNum) > 0.2  " +
            "with recommendation,count(recommendation) as r_num " +
            "return recommendation as course " +
            "order by r_num desc limit 5")
    List<Neo4jCourse> getRecommendedCourseByRecommendation(@Param(value = "openid") String openid);


    @Query("MATCH (i:user{openid:$openid}),(other:user) " +
            "where other.openid <> $openid " +
            "WITH i,other " +
            "OPTIONAL MATCH common=(i)-[:SELECT{category:'通识课'}]->(:course)<-[:SELECT{category:'通识课'}]-(other) " +
            "WITH i, other, COUNT(common) as intersection " +
            "OPTIONAL MATCH (i)-[:SELECT{category:'通识课'}]->(myFavour:course) " +
            "WITH i, other, intersection, collect(myFavour) as myFavour,count(myFavour) as myFavourNum  " +
            "OPTIONAL MATCH (other)-[:SELECT{category:'通识课'}]->(otherFavour:course)  " +
            "WHERE NOT otherFavour IN myFavour  " +
            "WITH i, other, intersection, count(otherFavour) AS recommendationNum,myFavourNum,otherFavour as recommendation  " +
            "where intersection/toFloat(recommendationNum+myFavourNum) > 0.2  " +
            "with recommendation,count(recommendation) as r_num " +
            "return recommendation as course " +
            "order by r_num desc limit 5")
    List<Neo4jCourse> getRecommendedCourseBySelection(@Param(value = "openid") String openid);
}
