package com.example.CourseRecommendation.controller.message;

import java.util.HashMap;
import java.util.Map;

public class Message extends HashMap<String, Object> {
    Map<String, Object> statusMap = new HashMap<>();

    public Message() {
        this.put("message", "");
        statusMap.put("msg", "获取成功");
        statusMap.put("status", 200);
        this.put("meta", statusMap);
    }

    public void setMessage(Object message) {
        this.put("message",message);
    }


    public void setMeta(String msg, int status) {
        statusMap.put("msg", msg);
        statusMap.put("status", status);
    }
}
