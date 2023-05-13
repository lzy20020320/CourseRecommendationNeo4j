package com.example.CourseRecommendation.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.CourseRecommendation.dao.UserRepository;
import com.example.CourseRecommendation.entity.Moment;
import com.example.CourseRecommendation.mapper.CourseFollowMapper;
import com.example.CourseRecommendation.mapper.MomentMapper;
import com.example.CourseRecommendation.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class MomentService {
//    @Autowired
//    MomentMapper momentMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    UserMapper userMapper;

    @Autowired
    CourseFollowMapper courseFollowMapper;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public void updateRelationUser2Course(String u_id, String c_no, boolean recommend) {
        if (recommend)
            userRepository.updateUserRecommendCourse(u_id, c_no);
        else
            userRepository.updateUserUnlikeCourse(u_id, c_no);
    }

    public void insert(Moment moment) {
        LocalDateTime now = LocalDateTime.now();
        moment.setM_create_time(now.format(formatter));
        mongoTemplate.insert(moment);
    }


    public List<Moment> getFollowMoments(String u_id, Integer pageNum, Integer pageSize) {
        int pageBegin = (pageNum - 1) * pageSize;
//        List<Map<String, Object>> moments = momentMapper.selectFollowMomentByUid(u_id, pageBegin, pageSize);
//        momentMapper.updateMomentBrowseNumByUid(u_id, pageBegin, pageSize);
        List<String> cnoList = courseFollowMapper.selectFollowCnoByUid(u_id);
        Query query = new Query(Criteria.where("c_no").in(cnoList));
        query.skip(pageBegin);
        query.limit(pageSize);
        Update update = new Update().inc("m_browse_num", 1);
        List<Moment> moments = mongoTemplate.find(query, Moment.class);
        for (Moment moment :moments){
            moment.setU_url(userMapper.selectUrlByUid(moment.getU_id()));
        }
        System.out.println(moments);
        mongoTemplate.updateMulti(query, update, Moment.class);


        return alterTimeDiff(moments);
    }

    public int getFollowMomentsTotalNum(String u_id) {
        List<String> cnoList = courseFollowMapper.selectFollowCnoByUid(u_id);
        Query query = new Query(Criteria.where("c_no").in(cnoList));
        return (int) mongoTemplate.count(query, Moment.class);
    }

    public List<Moment> getMyMoments(String u_id, Integer pageNum, Integer pageSize) {
        int pageBegin = (pageNum - 1) * pageSize;
//        List<Map<String, Object>> moments = momentMapper.selectFollowMomentByUid(u_id, pageBegin, pageSize);
//        momentMapper.updateMomentBrowseNumByUid(u_id, pageBegin, pageSize);
        Query query = new Query(Criteria.where("u_id").is(u_id));
        query.skip(pageBegin);
        query.limit(pageSize);
        Update update = new Update().inc("m_browse_num", 1);
        List<Moment> moments = mongoTemplate.find(query, Moment.class);
        for (Moment moment :moments){
            moment.setU_url(userMapper.selectUrlByUid(moment.getU_id()));
        }
        System.out.println(moments);
        mongoTemplate.updateMulti(query, update, Moment.class);
        return alterTimeDiff(moments);
    }


    public int getMyMomentsTotalNum(String u_id) {
        Query query = new Query(Criteria.where("u_id").is(u_id));
        return (int) mongoTemplate.count(query, Moment.class);
    }

    @Transactional
    public List<Moment> getCourseMoments(String c_no, Integer pageNum, Integer pageSize) {
        int pageBegin = (pageNum - 1) * pageSize;
//        List<Map<String, Object>> moments = momentMapper.selectFollowMomentByUid(u_id, pageBegin, pageSize);
//        momentMapper.updateMomentBrowseNumByUid(u_id, pageBegin, pageSize);
        Query query = new Query(Criteria.where("c_no").is(c_no));
        query.skip(pageBegin);
        query.limit(pageSize);
        Update update = new Update().inc("m_browse_num", 1);
        List<Moment> moments = mongoTemplate.find(query, Moment.class);
        for (Moment moment :moments){
            moment.setU_url(userMapper.selectUrlByUid(moment.getU_id()));
        }
        System.out.println(moments);
        mongoTemplate.updateMulti(query, update, Moment.class);
        return alterTimeDiff(moments);
    }

    public int getCourseMomentsTotalNum(String c_no) {
        Query query = new Query(Criteria.where("c_no").is(c_no));
        return (int) mongoTemplate.count(query, Moment.class);
    }

    private List<Moment> alterTimeDiff(List<Moment> moments) {
        for (Moment moment : moments) {
            System.out.println(moment);
            String create_time = moment.getM_create_time();
            LocalDateTime date = LocalDateTime.parse(create_time, formatter);
            LocalDateTime now = LocalDateTime.now();
            String time_diff = String.valueOf(ChronoUnit.DAYS.between(date,now));
            if (Objects.equals(time_diff, "0")) time_diff = "今天";
            else if (Objects.equals(time_diff, "1")) time_diff = "昨天";
            else time_diff = time_diff + "天前";
            moment.setTime_diff(time_diff);
        }
        return moments;
    }

    public void likeMoment(String m_id) {
        Query query = new Query(Criteria.where("_id").is(m_id));
        Update update = new Update();
        update.inc("m_like_num");
        mongoTemplate.updateFirst(query,update,"moment");
    }
}
