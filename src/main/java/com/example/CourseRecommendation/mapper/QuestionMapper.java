package com.example.CourseRecommendation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.CourseRecommendation.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface QuestionMapper extends BaseMapper<Question> {
    @Select("select *" +
            "from question " +
            "where c_no = #{c_no}")
    Map<String,Object> selectByCno(@Param("c_no") String c_no);
}
