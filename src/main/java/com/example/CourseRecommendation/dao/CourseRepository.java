package com.example.CourseRecommendation.dao;

import com.example.CourseRecommendation.node.Course;
import org.springframework.data.repository.query.Param;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface CourseRepository extends Neo4jRepository<Course, Long> {
    @Query("MATCH(n:course) WHERE n.name =~('.*' + $cname + '.*') RETURN n.aim LIMIT 1")
    String getCourseDetail(@Param(value = "cname") String cname);

    @Query("match (n:teacher{name: $tname})-[r:TEACHES]->(c:course) return c.name")
    List<String> getCourseNameByTName(@Param(value = "tname") String tname);

    @Query("match (n:teacher{name: $tname})-[r:TEACHES]->(c:course)-[r2:BELONGS_TO]->(ca:category) " +
            "return distinct ca.name")
    List<String> getCategoryNameByTName(@Param(value = "tname") String tname);

    @Query("match (t:teacher)-[r:TEACHES]->(c:course{name: $cname}) return distinct t.name")
    List<String> getTNameByCourseName(@Param(value = "cname") String cname);

    @Query("match (n:teacher{name: $tname})-[r:TEACHES]->(c:course) return count(*)")
    int getCourseNumByTName(@Param(value = "tname") String tname);

    @Query("match (c:course)-[r:BELONGS_TO]->(ca:category) where c.name =~('.*' + $cname + '.*') return ca.name limit 1")
    String getCourseCategoryByCName(@Param(value = "cname") String cname);
}
