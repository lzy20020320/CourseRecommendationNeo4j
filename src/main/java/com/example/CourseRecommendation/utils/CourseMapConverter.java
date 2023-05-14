package com.example.CourseRecommendation.utils;

import com.example.CourseRecommendation.entity.CourseMap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseMapConverter {
    public static Map<String, Object> convertToMap(CourseMap course) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = course.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(course);
            if (value instanceof List<?> l) {
                List<Map<String, Object>> list = new ArrayList<>();
                for (Object o : l) {
                    if (o instanceof CourseMap c) {
                        list.add(convertToMap(c));
                    }
                }
                map.put(field.getName(), list);
            } else if (value instanceof Map<?, ?> m) {
                Map<String, Object> subMap = new HashMap<>();
                for (Map.Entry<?, ?> entry : m.entrySet()) {
                    subMap.put(entry.getKey().toString(), entry.getValue());
                }
                map.put(field.getName(), subMap);
            } else {
                map.put(field.getName(), value);
            }
        }
        return map;
    }
}

