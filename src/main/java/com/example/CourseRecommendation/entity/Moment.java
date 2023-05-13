package com.example.CourseRecommendation.entity;


import com.example.CourseRecommendation.utils.momentRating.MomentRating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("moment")
public class Moment {


    @Id
    @Field("_id")
    private String _id;
    private String u_id;
    private String c_no;
    private String t_name;
    private String m_content;
    private int m_recommended;
    private String m_create_time;
    private int m_like_num;
    private int m_browse_num;


    @Transient
    private String u_url;

    @Transient
    private String time_diff;


    public void setRecommended(){
        m_recommended = Integer.parseInt(MomentRating.rating(m_content));
    }
}
