package com.example.CourseRecommendation.controller.query;

import com.example.CourseRecommendation.controller.message.Message;
import com.example.CourseRecommendation.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/query")
public class QueryController {

    @Autowired
    QueryService queryService;

    @GetMapping("/answer")
    public Map<String, Object> answer(@RequestParam("openid") String u_id,
                                      @RequestParam("question") String question) {
        Message message = new Message();
        message.setMessage(queryService.answer(u_id,question));
        System.out.println(message);
        return message;
    }
}
