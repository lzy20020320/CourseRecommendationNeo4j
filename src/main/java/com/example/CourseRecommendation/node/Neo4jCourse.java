package com.example.CourseRecommendation.node;

import com.example.CourseRecommendation.config.MyConfig;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import java.util.Map;
import java.util.Objects;

import static com.example.CourseRecommendation.config.MyConfig.COURSE_DEFAULT_URL;
import static com.example.CourseRecommendation.entity.Course.categoryC2E;

@Data
@Node("course")
public class Neo4jCourse {


//    public Neo4jCourse(Course course) {
//        this.no = course.getNo();
//        this.name = course.getName();
//        this.credit = String.valueOf(course.getCredit());
//        this.aim = course.getAim();
//        this.content = course.getContent();
//        this.reference = course.getReference();
//        this.prerequisite = course.getPrerequisite();
//        this.targetStudent = course.getTargetStudent();
//    }
//
//    public Neo4jCourse(Map<String,Object> course) {
//        this.no = course.get("c_no").toString();
//        this.name = course.get("c_name").toString();
//        this.credit = course.get("c_credit").toString();;
//        this.aim = course.get("c_aim").toString();
//        this.content = course.get("c_content").toString();
//        this.reference = course.get("c_reference").toString();
//        this.prerequisite = course.get("c_prerequisite").toString();
//        this.targetStudent = course.get("c_target_student").toString();
//    }

    @Id
    @GeneratedValue
    private Long id;
    @Property("no")
    private String no;
    @Property("name")
    private String name;
    @Property("credit")
    private String credit;
    @Property("aim")
    private String aim;
    @Property("content")
    private String content;
    @Property("reference")
    private String reference;
    @Property("prerequisite")
    private String prerequisite;
    @Property("target_student")
    private String targetStudent;

    @Property("c_url")
    private String url;


    @Property("c_th_url")
    private String th_url;
    @Property("c_category")
    private String category;

//    public void setUrl() {
//        if (Objects.equals(url, "0"))
//            url = MyConfig.ADDR +"/img/courseicon/"+categoryC2E(category)+"/default.jpg";
//        else
//            url =  MyConfig.ADDR +"/img/courseicon/"+categoryC2E(category)+"/"+ no + ".jpg";
//    }

    public static Neo4jCourse Map2Neo4jCourse(Map<String, Object> course) {
        Neo4jCourse neo4jCourse = new Neo4jCourse();
        if (course.get("c_no") != null)
            neo4jCourse.no = course.get("c_no").toString();
        if (course.get("c_name") != null)
            neo4jCourse.name = course.get("c_name").toString();
        if (course.get("c_credit") != null)
            neo4jCourse.credit = course.get("c_credit").toString();
        if (course.get("c_aim") != null)
            neo4jCourse.aim = course.get("c_aim").toString();
        if (course.get("c_content") != null)
            neo4jCourse.content = course.get("c_content").toString();
        if (course.get("c_reference") != null)
            neo4jCourse.reference = course.get("c_reference").toString();
        if (course.get("c_prerequisite") != null)
            neo4jCourse.prerequisite = course.get("c_prerequisite").toString();
        if (course.get("c_target_student") != null)
            neo4jCourse.targetStudent = course.get("c_target_student").toString();
        if (course.get("c_category") != null)
            neo4jCourse.category = course.get("c_category").toString();
        if (course.get("c_url") != null)
            neo4jCourse.url = course.get("c_url").toString();
        if (course.get("c_th_url") != null)
            neo4jCourse.th_url = course.get("c_th_url").toString();

        return neo4jCourse;
    }
}
