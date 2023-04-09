package com.example.CourseRecommendation.controller.home;

import com.example.CourseRecommendation.config.MyConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SwiperDataMap extends HashMap<String, Object> {

    public SwiperDataMap(String no, String image_src, String navigator_url) {
        this.put("goods_id", no);
        this.put("image_src", image_src);
        this.put("open_type", "navigate");
        this.put("navigator_url", navigator_url);
    }

    public static List<SwiperDataMap> SwiperDataMapList() {
        List<String> srcList = Arrays.asList(
                MyConfig.ADDR + "/img/banner/banner1.png",
                MyConfig.ADDR + "/img/banner/banner2.png",
                MyConfig.ADDR + "/img/banner/banner3.png",
                MyConfig.ADDR + "/img/banner/banner4.png");

        List<String> noList = Arrays.asList(
                "00816394",
                "0081A004",
                "00853001",
                "0100Y016"
        );

        List<String> urlList = Arrays.asList(
                "",
                "",
                "",
                ""
        );

        List<SwiperDataMap> swiperDataMapList = new ArrayList<>();
        for (int i = 0; i < 4; i++)
            swiperDataMapList.add(new SwiperDataMap(noList.get(i), srcList.get(i), urlList.get(i)));
        return swiperDataMapList;
    }
}
