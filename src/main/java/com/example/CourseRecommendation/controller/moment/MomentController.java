package com.example.CourseRecommendation.controller.moment;

import com.example.CourseRecommendation.controller.message.Message;
import com.example.CourseRecommendation.entity.Moment;
import com.example.CourseRecommendation.entity.User;
import com.example.CourseRecommendation.service.MomentService;
import com.example.CourseRecommendation.utils.sensi.SensitiveFilter;
import com.google.common.util.concurrent.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/moment")
public class MomentController {
    @Autowired
    MomentService momentService;

    private SensitiveFilter filter = SensitiveFilter.DEFAULT;

    private final RateLimiter limiter = RateLimiter.create(2.0);


    @GetMapping("/post")
    public Message postMoment(@RequestParam("openid") String openid,
                              @RequestParam("course_no") String c_no,
                              @RequestParam("moment_content") String m_content) {
        Message message = new Message();

        boolean tryAcquire = limiter.tryAcquire(500, TimeUnit.MILLISECONDS);
        if(!tryAcquire){
            message.setMeta("TOOFREQUENT",200);
        }
        else {
            String filted = filter.filter(m_content, '*');
            // 如果未过滤，则返回输入的String引用
            if (m_content != filted) {
                message.setMeta("INVALID", 200);
            } else {
                Moment moment = new Moment();
                moment.setU_id(openid);
                moment.setC_no(c_no);
                moment.setM_content(m_content);
                moment.setM_like_num(0);
                moment.setM_browse_num(0);
                moment.setRecommended();
                momentService.updateRelationUser2Course(openid, c_no, moment.getM_recommended() == 1);
                momentService.insert(moment);
            }
        }
        return message;
    }

//    @DeleteMapping("/delete")
//    public boolean deleteMoment(@RequestParam int moment_id) {
//        return momentService.removeById(moment_id);
//    }

    @GetMapping("/my")
    public Map<String, Object> getMyMoments(@RequestParam("openid") String u_id,
                                            @RequestParam("page_num") Integer pageNum,
                                            @RequestParam("page_size") Integer pageSize) {
        Message message = new Message();
        List<Moment> myMoments = momentService.getMyMoments(u_id, pageNum, pageSize);
        Map<String, Object> data = new HashMap<>();
        data.put("total", momentService.getMyMomentsTotalNum(u_id));
        data.put("moments", myMoments);
        message.setMessage(data);
        return message;
    }

    @GetMapping("/course")
    public Map<String, Object> getCourseMoments(@RequestParam("course_no") String c_no,
                                                @RequestParam("page_num") Integer pageNum,
                                                @RequestParam("page_size") Integer pageSize) {
        Message message = new Message();
        List<Moment> courseMoments = momentService.getCourseMoments(c_no, pageNum, pageSize);
        Map<String, Object> data = new HashMap<>();
        data.put("total", momentService.getCourseMomentsTotalNum(c_no));
        data.put("moments", courseMoments);
        message.setMessage(data);
        return message;
    }

    @GetMapping("/search")
    public Map<String, Object> getFollowMoments(@RequestParam("openid") String u_id,
                                                @RequestParam("page_num") Integer pageNum,
                                                @RequestParam("page_size") Integer pageSize) {
        System.out.println("search");
        Message message = new Message();
        List<Moment> followMoments = momentService.getFollowMoments(u_id, pageNum, pageSize);
        Map<String, Object> data = new HashMap<>();
        data.put("total", momentService.getFollowMomentsTotalNum(u_id));
        data.put("moments", followMoments);
        message.setMessage(data);
        return message;
    }

    @GetMapping("/like")
    public void likeMoment(@RequestParam("m_id") String m_id) {
        momentService.likeMoment(m_id);
    }
}
