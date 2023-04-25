package com.example.CourseRecommendation.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.CourseRecommendation.dao.UserRepository;
import com.example.CourseRecommendation.entity.Moment;
import com.example.CourseRecommendation.mapper.MomentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class MomentService extends ServiceImpl<MomentMapper, Moment> {
    @Autowired
    MomentMapper momentMapper;

    @Autowired
    UserRepository userRepository;


    public void updateRelationUser2Course(String u_id, String c_no, boolean recommend) {
        if (recommend)
            userRepository.updateUserRecommendCourse(u_id, c_no);
        else
            userRepository.updateUserUnlikeCourse(u_id, c_no);
    }

    @Transactional
    public List<Map<String, Object>> getFollowMoments(String u_id, Integer pageNum, Integer pageSize) {
        Integer pageBegin = (pageNum - 1) * pageSize;
        List<Map<String, Object>> moments = momentMapper.selectFollowMomentByUid(u_id, pageBegin, pageSize);
        momentMapper.updateMomentBrowseNumByUid(u_id, pageBegin, pageSize);
        return alterTimeDiff(moments);
    }

    public int getFollowMomentsTotalNum(String u_id) {
        return momentMapper.selectFollowMomentNum(u_id);
    }

    public List<Map<String, Object>> getMyMoments(String u_id, Integer pageNum, Integer pageSize) {
        Integer pageBegin = (pageNum - 1) * pageSize;
        List<Map<String, Object>> moments = momentMapper.selectMomentsByUid(u_id, pageBegin, pageSize);
        return alterTimeDiff(moments);
    }


    public int getMyMomentsTotalNum(String u_id) {
        return momentMapper.selectMyMomentTotalNum(u_id);
    }
    @Transactional
    public List<Map<String, Object>> getCourseMoments(String c_no, Integer pageNum, Integer pageSize) {
        Integer pageBegin = (pageNum - 1) * pageSize;
        List<Map<String, Object>> moments = momentMapper.selectMomentsByCno(c_no, pageBegin, pageSize);
        momentMapper.updateMomentsByCnoBrowseNum(c_no, pageBegin, pageSize);
        return alterTimeDiff(moments);
    }

    public int getCourseMomentsTotalNum(String c_no) {
        return momentMapper.selectCourseMomentTotalNum(c_no);
    }

    private List<Map<String, Object>> alterTimeDiff(List<Map<String, Object>> moments) {
        for (Map<String, Object> moment : moments) {
            System.out.println(moment);
            String time_diff = moment.get("time_diff").toString();
            if (Objects.equals(time_diff, "0")) time_diff = "今天";
            else if (Objects.equals(time_diff, "1")) time_diff = "昨天";
            else time_diff = time_diff + "天前";
            moment.put("time_diff", time_diff);
        }
        return moments;
    }

    public boolean likeMoment(String m_id){
        return momentMapper.likeMoment(m_id);
    }
}
