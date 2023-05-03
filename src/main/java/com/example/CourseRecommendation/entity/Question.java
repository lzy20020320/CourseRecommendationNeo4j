package com.example.CourseRecommendation.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("question")
public class Question {
    @TableId(value = "q_id",type = IdType.AUTO)
    private Integer q_id;
    @TableField(value = "q_content")
    private String q_content;
    @TableField(value = "c_no")
    private String c_no;
}
