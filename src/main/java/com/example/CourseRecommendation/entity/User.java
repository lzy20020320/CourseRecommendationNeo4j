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

    @TableField("u_gender")
    Integer gender;


    @TableField("u_hobby")
    String hobby;

    @TableField("u_birthday")
    String birthday;

    @TableField("u_email")
    String email;

    @TableField("u_url")
    String url;

    @TableField("u_major")
    Integer major;

    @TableField("u_school")
    String school;

    @TableField("u_signature")
    String signature;

    @TableField("u_authenticated")
    String authenticated;


}
