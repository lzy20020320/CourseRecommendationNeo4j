package com.example.CourseRecommendation.controller.courselist;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ToString
@Data
public class CatMap extends HashMap<String,Object> {

    private List<CatMap> mapList = new ArrayList<>();
    public CatMap() {
        this.put("cat_id","");
        this.put("cat_name","");
        this.put("cat_pid",0);
        this.put("cat_level",0);
        this.put("cat_deleted",false);
        this.put("cat_icon","");
        this.put("children",mapList);
    }

    public CatMap(String cat_id,String cat_name,int cat_level,String cat_icon) {
        this.put("cat_id",cat_id);
        this.put("cat_name",cat_name);
        this.put("cat_pid",0);
        this.put("cat_level",cat_level);
        this.put("cat_deleted",false);
        this.put("cat_icon",cat_icon);
        this.put("children",mapList);
    }

    public void childrenAdd(CatMap catMap){
        mapList.add(catMap);
    }
}
