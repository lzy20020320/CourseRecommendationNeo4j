package com.example.CourseRecommendation.node;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import java.io.Serializable;

@Data
@Node("course")
public class Course implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @Property("no")
    private String no;
    @Property("name")
    private String name;
    @Property("credit")
    private float credit;
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
}
