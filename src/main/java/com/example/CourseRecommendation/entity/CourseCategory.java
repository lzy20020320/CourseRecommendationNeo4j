package com.example.CourseRecommendation.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CourseCategory {
    final static public String ENGINEERING_SCIENCE = "工程科学";
    final static public String CROSS_CULTURAL_EXCHANGE = "跨文化交流";
    final static public String HISTORICAL_AND_CULTURAL = "历史文化";
    final static public String HUMANITIES_AND_ARTS = "人文艺术";
    final static public String SOCIAL_SCIENCES = "社会科学";
    final static public String PRACTICAL_INNOVATION = "实践创新";
    final static public String IDEOLOGY_AND_POLITICS = "思想政治";
    final static public String SPORTS_AND_HEALTH = "体育健康";
    final static public String NATURAL_SCIENCE = "自然科学";
    final static public String SYNTHESIZE = "综合";

    final static public List<String> CATEGORY_LIST = new ArrayList<>(Arrays.asList(
            "工程科学", "跨文化交流", "历史文化", "人文艺术", "社会科学", "实践创新", "思想政治", "体育健康", "自然科学", "综合"));
}
