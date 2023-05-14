package com.example.CourseRecommendation.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;
@Document("course")
public class CourseMap {
    @Id
    private String id;
    private String name;
    private List<CourseMap> children;
    private Map<String, String> itemStyle;
}
