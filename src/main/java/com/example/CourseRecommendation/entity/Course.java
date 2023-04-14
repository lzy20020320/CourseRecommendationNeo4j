package com.example.CourseRecommendation.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.CourseRecommendation.config.MyConfig;
import lombok.Data;

@Data
@TableName("course")
public class Course {
    @TableId(value = "c_no")
    private String no;
    @TableField("c_name")
    private String name;
    @TableField("c_credit")
    private float credit;
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

    @TableField("c_url")
    private String url;

}
