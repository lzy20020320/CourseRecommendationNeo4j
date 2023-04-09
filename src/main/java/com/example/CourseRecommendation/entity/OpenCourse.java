package com.example.CourseRecommendation.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "open_course")
public class OpenCourse {
    @TableId(value = "o_id",type = IdType.AUTO)
    private Integer id;
    @TableField("c_no")
    private String no;
    @TableField("t_name")
    private String teacherName;
    @TableField("t_id")
    private String teacherId;
    @TableField("o_campus")
    private String campus;
    @TableField("o_time")
    private String time;
}
