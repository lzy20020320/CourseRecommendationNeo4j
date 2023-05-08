package com.example.CourseRecommendation.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.CourseRecommendation.config.MyConfig;
import lombok.Data;

import java.util.Map;
import java.util.Objects;


@Data
@TableName("course")
public class Course {
    @TableId(value = "c_no")
    private String no;
    @TableField("c_name")
    private String name;
    @TableField("c_credit")
    private float credit;

    @TableField("c_url")
    private String url;

    @TableField("c_th_url")
    private String th_url;
    @TableField("c_category")
    private String category;
    @TableField("c_aim")
    private String aim;
    @TableField("c_content")
    private String content;
    @TableField("c_reference")
    private String reference;
    @TableField("c_prerequisite")
    private String prerequisite;
    @TableField("c_target_student")
    private String targetStudent;


//    public void setUrl() {
//        if (Objects.equals(url, "0"))
//            url = MyConfig.ADDR +"/img/courseicon/"+categoryC2E(category)+"/default.jpg";
//        else
//            url =  MyConfig.ADDR +"/img/courseicon/"+categoryC2E(category)+"/"+ no + ".jpg";
//    }
//
//    static public void setUrl(Map<String, Object> course) {
//        System.out.println(course.get("c_url").toString());
//        System.out.println(Objects.equals(course.get("c_url").toString(), "0"));
//        if (Objects.equals(course.get("c_url").toString(), "0"))
//            course.put("c_url", MyConfig.ADDR +"/img/courseicon/"+categoryC2E(course.get("c_category").toString())+"/default.jpg");
//        else
//            course.put("c_url", MyConfig.ADDR +"/img/courseicon/"+categoryC2E(course.get("c_category").toString())+"/"+ course.get("c_no").toString() + ".jpg");
//    }

    static public String categoryC2E(String category) {
        switch (category) {
            case "思想政治":
                return "politics";
            case "体育健康":
                return "sports";
            case "自然科学":
                return "science";
            case "创新实践":
                return "innovation";
            case "工程科学":
                return "engineering";
            case "历史文化":
                return "history";
            case "社会科学":
                return "society";
            case "综合":
                return "comprehensive";
            case "跨文化交流":
                return "intercultural";
            case "人文艺术":
                return "humanity";
            default:
                return "";

        }
    }

    static public String categoryE2C(String category) {
        switch (category) {
            case "politics":
                return "思想政治";
            case "sports":
                return "体育健康";
            case "science":
                return "自然科学";
            case "innovation":
                return "创新实践";
            case "engineering":
                return "工程科学";
            case "history":
                return "历史文化";
            case "society":
                return "社会科学";
            case "comprehensive":
                return "综合";
            case "intercultural":
                return "跨文化交流";
            case "humanity":
                return "人文艺术";
            default:
                return "";

        }
    }


    static public String category2Url(String category) {
        switch (category) {
            case "思想政治":
                return "https://i.328888.xyz/2023/04/20/iFaTlt.png";
            case "体育健康":
                return "https://i.328888.xyz/2023/04/20/iFQvOC.md.jpeg";
            case "自然科学":
                return "https://i.328888.xyz/2023/04/20/iF1ItZ.md.jpeg";
            case "创新实践":
                return "https://i.328888.xyz/2023/04/20/iFPxMx.md.jpeg";
            case "工程科学":
                return "https://i.328888.xyz/2023/04/20/iFKKsd.md.jpeg";
            case "历史文化":
                return "https://i.328888.xyz/2023/04/20/iFLn28.md.jpeg";
            case "社会科学":
                return "https://i.328888.xyz/2023/04/20/iFQAdE.md.jpeg";
            case "综合":
                return "https://i.328888.xyz/2023/04/20/iFKVjt.md.jpeg";
            case "跨文化交流":
                return "https://i.328888.xyz/2023/04/20/iFT24p.md.jpeg";
            case "人文艺术":
                return "https://i.328888.xyz/2023/04/20/iFMF1J.md.jpeg";
            default:
                return "";
        }
    }

    static public String category2ThUrl(String category) {
        switch (category) {
            case "思想政治":
                return "https://i.328888.xyz/2023/04/20/iFaTlt.th.png";
            case "体育健康":
                return "https://i.328888.xyz/2023/04/20/iFQvOC.th.jpeg";
            case "自然科学":
                return "https://i.328888.xyz/2023/04/20/iF1ItZ.th.jpeg";
            case "创新实践":
                return "https://i.328888.xyz/2023/04/20/iFPxMx.th.jpeg";
            case "工程科学":
                return "https://i.328888.xyz/2023/04/20/iFKKsd.th.jpeg";
            case "历史文化":
                return "https://i.328888.xyz/2023/04/20/iFLn28.th.jpeg";
            case "社会科学":
                return "https://i.328888.xyz/2023/04/20/iFQAdE.th.jpeg";
            case "综合":
                return "https://i.328888.xyz/2023/04/20/iFKVjt.th.jpeg";
            case "跨文化交流":
                return "https://i.328888.xyz/2023/04/20/iFT24p.th.jpeg";
            case "人文艺术":
                return "https://i.328888.xyz/2023/04/20/iFMF1J.th.jpeg";
            default:
                return "";
        }
    }


}
