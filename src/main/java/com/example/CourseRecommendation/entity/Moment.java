package com.example.CourseRecommendation.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.CourseRecommendation.utils.momentRating.MomentRating;
import lombok.Data;

import java.util.Objects;

@Data
@TableName("moment")
public class Moment {
    @TableId(value = "m_id",type = IdType.AUTO)
    private Integer id;
    @TableField(value = "u_id")
    private String uId;
    @TableField(value = "c_no")
    private String cNo;

    @TableField(value = "t_name")
    private String tName;
    @TableField(value = "m_content")
    private String content;
    @TableField(value = "m_create_time")
    private String time;
    @TableField(value = "m_recommended")
    private boolean recommended;

    public void setRecommended(){
        recommended = Objects.equals(MomentRating.rating(content), "1");
    }
}
