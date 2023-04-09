package com.example.CourseRecommendation.controller.query;

import com.example.CourseRecommendation.config.MyConfig;
import com.example.CourseRecommendation.controller.message.Message;
import com.example.CourseRecommendation.dao.CourseRepository;
import com.example.CourseRecommendation.service.QueryService;
import com.example.CourseRecommendation.service.UserService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/query")
public class QueryController {

    @Autowired
    QueryService queryService;

    @GetMapping("/answer")
    public Map<String, Object> answer(@RequestParam String question) {
        Message message = new Message();
        message.setMessage(queryService.answer(question));
        return message;
    }
}
