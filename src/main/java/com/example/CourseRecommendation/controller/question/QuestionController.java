package com.example.CourseRecommendation.controller.question;

import com.example.CourseRecommendation.controller.message.Message;
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
    public Map<String, Object> getRandomQuestion(@RequestParam("openid") String u_id) {
        Message message = new Message();
        message.setMessage(questionService.getRandomQuestion());
        return message;
    }
}
