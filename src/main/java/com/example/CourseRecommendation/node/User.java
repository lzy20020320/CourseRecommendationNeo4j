package com.example.CourseRecommendation.node;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Data
@Node("user")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Property("openid")
    private String openid;

}
