package com.example.CourseRecommendation.utils.momentRating;


import com.example.CourseRecommendation.config.MyConfig;

public class MomentRating {

    public static String rating(String review) {
        HanLpClassifier.initClassifier(null, MyConfig.RESOURCE_PATH+"ser/nb-classifier-for-weibo.ser");
        System.out.println(HanLpClassifier.getClassification(review));
        return HanLpClassifier.getClassification(review);
    }
}

