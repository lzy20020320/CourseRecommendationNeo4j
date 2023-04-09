package com.example.CourseRecommendation.controller.home;

import com.example.CourseRecommendation.config.MyConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FloorDataMap extends HashMap<String, Object> {


    class ContentInfo extends HashMap<String, Object> {
        ContentInfo(String name, String image_src, String image_width, String navigator_url) {
            this.put("name", name);
            this.put("image_src", image_src);
            this.put("image_width", image_width);
            this.put("open_type", "navigate");
            this.put("navigator_url", navigator_url);
        }
    }

    HashMap<String, Object> floorTitle = new HashMap<>();
    List<ContentInfo> list = new ArrayList<>();

    public FloorDataMap(String name, String image_src) {
        floorTitle.put("name", name);
        floorTitle.put("image_src", image_src);
        this.put("floor_title", floorTitle);
        this.put("product_list", list);
    }
    public static List<FloorDataMap> FloorDataMapList() {
        List<FloorDataMap> floorDataMapList = new ArrayList<>();
        floorDataMapList.add(new FloorDataMap("时尚女装","https://api-hmugo-web.itheima.net/pyg/pic_floor01_title.png"));
        floorDataMapList.get(0).addContent(
                "优质服饰",
                "https://api-hmugo-web.itheima.net/pyg/pic_floor01_1@2x.png",
                "232",
                "/pages/goods_list?query=服饰"
        );
        floorDataMapList.get(0).addContent(
                "春季热门",
                "https://api-hmugo-web.itheima.net/pyg/pic_floor01_2@2x.png",
                "233",
                "/pages/goods_list?query=热"
        );
        floorDataMapList.get(0).addContent(
                "爆款清仓",
                "https://api-hmugo-web.itheima.net/pyg/pic_floor01_3@2x.png",
                "233",
                "/pages/goods_list?query=爆款"
        );
        floorDataMapList.get(0).addContent(
                "倒春寒",
                "https://api-hmugo-web.itheima.net/pyg/pic_floor01_4@2x.png",
                "233",
                "/pages/goods_list?query=春季"
        );
        floorDataMapList.get(0).addContent(
                "怦然心动",
                "https://api-hmugo-web.itheima.net/pyg/pic_floor01_5@2x.png",
                "233",
                "/pages/goods_list?query=心动"
        );
        floorDataMapList.add(new FloorDataMap("户外活动","https://api-hmugo-web.itheima.net/pyg/pic_floor02_title.png"));
        floorDataMapList.get(1).addContent(
                "勇往直前",
                "https://api-hmugo-web.itheima.net/pyg/pic_floor02_1@2x.png",
                "232",
                "/pages/goods_list?query=户外"
        );
        floorDataMapList.get(1).addContent(
                "户外登山包",
                "https://api-hmugo-web.itheima.net/pyg/pic_floor02_2@2x.png",
                "273",
                "/pages/goods_list?query=登山包"
        );
        floorDataMapList.get(1).addContent(
                "超强手套",
                "https://api-hmugo-web.itheima.net/pyg/pic_floor02_3@2x.png",
                "193",
                "/pages/goods_list?query=手套"
        );
        floorDataMapList.get(1).addContent(
                "户外运动鞋",
                "https://api-hmugo-web.itheima.net/pyg/pic_floor02_4@2x.png",
                "193",
                "/pages/goods_list?query=运动鞋"
        );
        floorDataMapList.get(1).addContent(
                "冲锋衣系列",
                "https://api-hmugo-web.itheima.net/pyg/pic_floor02_5@2x.png",
                "273",
                "/pages/goods_list?query=冲锋衣"
        );
        floorDataMapList.add(new FloorDataMap("箱包配饰","https://api-hmugo-web.itheima.net/pyg/pic_floor03_title.png"));
        floorDataMapList.get(2).addContent(
                "清新气质",
                "https://api-hmugo-web.itheima.net/pyg/pic_floor03_1@2x.png",
                "232",
                "/pages/goods_list?query=饰品"
        );
        floorDataMapList.get(2).addContent(
                "复古胸针",
                "https://api-hmugo-web.itheima.net/pyg/pic_floor03_2@2x.png",
                "263",
                "/pages/goods_list?query=胸针"
        );
        floorDataMapList.get(2).addContent(
                "韩版手链",
                "https://api-hmugo-web.itheima.net/pyg/pic_floor03_3@2x.png",
                "203",
                "/pages/goods_list?query=手链"
        );
        floorDataMapList.get(2).addContent(
                "水晶项链",
                "https://api-hmugo-web.itheima.net/pyg/pic_floor03_4@2x.png",
                "193",
                "/pages/goods_list?query=水晶项链"
        );
        floorDataMapList.get(2).addContent(
                "情侣表",
                "https://api-hmugo-web.itheima.net/pyg/pic_floor03_5@2x.png",
                "273",
                "/pages/goods_list?query=情侣表"
        );

        return floorDataMapList;
    }

    public void addContent(String name, String image_src, String image_width, String navigator_url) {
        list.add(new ContentInfo(name, image_src, image_width, navigator_url));
    }
}
