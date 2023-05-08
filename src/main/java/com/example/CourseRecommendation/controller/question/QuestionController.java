package com.example.CourseRecommendation.controller.question;

import com.example.CourseRecommendation.controller.message.Message;
import com.example.CourseRecommendation.entity.Course;
import com.example.CourseRecommendation.service.QueryService;
import com.example.CourseRecommendation.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/random")
    public Map<String, Object> getRandomQuestion() {
        Message message = new Message();
        Map<String, Object> question = null;
        while (question == null) {
            question = questionService.getRandomQuestion();
        }
        message.setMessage(question);
        return message;
    }


    @GetMapping("/category/random")
    public Map<String, Object> getMajorRandomQuestion(@RequestParam("category") String category) {
        Message message = new Message();
        Map<String, Object> question = null;
        String category_c = Course.categoryE2C(category);
        while (question == null) {
            question = questionService.getRandomQuestion(category_c);
        }
        message.setMessage(question);
        return message;
    }

}
