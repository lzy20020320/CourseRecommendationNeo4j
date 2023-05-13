package com.example.CourseRecommendation;

import ai.onnxruntime.OrtException;
import com.example.CourseRecommendation.utils.QClassifier;
import com.hankcs.hanlp.HanLP;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.CourseRecommendation.utils.crawler.java.CourseGetter.CrawlerRunner.PersonalCrawl;

@SpringBootTest
class CourseRecommendationApplicationTests {

    @Test
    void contextLoads() throws OrtException {

    }

}
