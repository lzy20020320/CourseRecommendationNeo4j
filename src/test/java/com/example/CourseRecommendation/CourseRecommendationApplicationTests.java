package com.example.CourseRecommendation;

import ai.onnxruntime.OrtException;
import com.example.CourseRecommendation.utils.QClassifier;
import com.hankcs.hanlp.HanLP;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class CourseRecommendationApplicationTests {

    @Test
    void contextLoads() throws OrtException {
//        System.out.println(HanLP.("王玉超老师上哪些课？"));;
        System.out.println(new Date().getTime());
    }

}
