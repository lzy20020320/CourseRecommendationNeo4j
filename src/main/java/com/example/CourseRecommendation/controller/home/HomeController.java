package com.example.CourseRecommendation.controller.home;

import com.example.CourseRecommendation.controller.message.Message;
import com.example.CourseRecommendation.utils.JsonMessageGetter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/home")
public class HomeController {

    @GetMapping("/swiperdata")
    public Map<String, Object> getSwiperData() {
        Message message = new Message();
        message.setMessage(SwiperDataMap.SwiperDataMapList());
        return message;
    }

    @GetMapping("/catitems")
    public Map<String, Object> getCatItems() {
        Message message = new Message();
        message.setMessage(CatItemMap.CatItemMapList());
        return message;
    }

    @GetMapping("/floordata")
    public Map<String, Object> getFloorData() {
        Message message = new Message();
        message.setMessage(FloorDataMap.FloorDataMapList());
        return message;
    }
}
