package com.example.CourseRecommendation.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.CourseRecommendation.config.MyConfig;
import lombok.Data;

@Data
@TableName("user")
public class User {

    @TableId("u_id")
    String u_id;

    @TableField("u_student_id")
    String student_id;

    @TableField("u_nickname")
    String nickname;

    @TableField("u_college")
    String college;

    @TableField("u_url")
    String url;

}
