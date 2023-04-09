package com.example.CourseRecommendation.controller.courselist;

import com.example.CourseRecommendation.controller.message.Message;
import com.example.CourseRecommendation.entity.CourseCategory;
import lombok.ToString;

import java.util.*;

@ToString
public class CourseListMessage extends Message {
    List<CatMap> messageList = new ArrayList<>();

    public CourseListMessage() {
        this.put("message", messageList);
        for (String categoryName : CourseCategory.CATEGORY_LIST) {
            this.addCategory(categoryName);
        }
    }

    private List<CatMap> getCourseList(String category) {
        int index = 0;
        for (int i = 0; i < CourseCategory.CATEGORY_LIST.size(); i++) {
            if (Objects.equals(category, CourseCategory.CATEGORY_LIST.get(i))) {
                index = i;
                break;
            }
        }
        CatMap catMap = messageList.get(index);
        return catMap.getMapList().get(0).getMapList();
    }


    private void addCategory(String category) {
        CatMap catMap = new CatMap("0", category, 0, "");
        catMap.childrenAdd(new CatMap("0", category, 1, ""));
        this.messageList.add(catMap);
    }

    public void addCourse(String name, String category, String no, String icon) {
        List<CatMap> courseList = this.getCourseList(category);
        courseList.add(new CatMap(no, name, 2, icon));
    }
}
