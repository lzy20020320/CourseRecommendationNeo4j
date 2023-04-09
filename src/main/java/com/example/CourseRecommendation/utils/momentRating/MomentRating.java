package com.example.CourseRecommendation.utils.momentRating;


public class MomentRating {

    public static String rating(String review) {
        HanLpClassifier.initClassifier(null, "src/main/resources/ser/nb-classifier-for-weibo.ser");
        System.out.println(HanLpClassifier.getClassification(review));
        return HanLpClassifier.getClassification(review);
    }
}

