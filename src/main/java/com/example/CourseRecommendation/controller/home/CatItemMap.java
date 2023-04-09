package com.example.CourseRecommendation.controller.home;

import com.example.CourseRecommendation.config.MyConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CatItemMap extends HashMap<String, Object> {

    private CatItemMap(String name, String image_src, String navigator_url) {
        this.put("name", name);
        this.put("image_src", image_src);
        this.put("open_type", "switchTab");
        this.put("navigator_url", navigator_url);
    }

    public static List<CatItemMap> CatItemMapList() {
        List<String> nameList = Arrays.asList(
                "随机推荐",
                "热门课程",
                "课单",
                "一键生成"
        );

        List<String> srcList = Arrays.asList(
                MyConfig.ADDR + "/img/catitem/catitem1.png",
                MyConfig.ADDR + "/img/catitem/catitem2.png",
                MyConfig.ADDR + "/img/catitem/catitem3.png",
                MyConfig.ADDR + "/img/catitem/catitem4.png");

        List<String> urlList = Arrays.asList(
                "",
                "",
                "",
                ""
        );

        List<CatItemMap> catItemMapArrayList = new ArrayList<>();
        for (int i = 0; i < 4; i++)
            catItemMapArrayList.add(new CatItemMap(nameList.get(i), srcList.get(i), urlList.get(i)));
        return catItemMapArrayList;
    }
}
