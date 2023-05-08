package com.example.CourseRecommendation.service;

import com.example.CourseRecommendation.mapper.CourseMapper;
import com.example.CourseRecommendation.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    CourseMapper courseMapper;


    public Map<String,Object> getRandomQuestion(){
        Random rand = new Random();
        List<Map<String, Object>> hotCourses = courseMapper.selectHotCourse();
        Map<String, Object> randomCourse = hotCourses.get(rand.nextInt(hotCourses.size()));
        Map<String,Object> question = questionMapper.selectByCno(randomCourse.get("c_no").toString());
        return question;
    }


    public Map<String,Object> getRandomQuestion(String category){
        Random rand = new Random();
        List<Map<String, Object>> hotCourses = courseMapper.selectCategoryHotCourse(category);
        Map<String, Object> randomCourse = hotCourses.get(rand.nextInt(hotCourses.size()));
        Map<String,Object> question = questionMapper.selectByCno(randomCourse.get("c_no").toString());
        return question;
    }


    public Map<String,Object> getByCno(String c_no){
        Map<String,Object> question = questionMapper.selectByCno(c_no);
        return question;
    }
}
