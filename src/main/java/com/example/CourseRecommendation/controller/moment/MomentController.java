package com.example.CourseRecommendation.controller.moment;

import com.example.CourseRecommendation.controller.message.Message;
import com.example.CourseRecommendation.entity.Moment;
import com.example.CourseRecommendation.entity.User;
import com.example.CourseRecommendation.service.MomentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/moment")
public class MomentController {
    @Autowired
    MomentService momentService;

    @GetMapping("/post")
    public boolean postMoment(@RequestParam("openid") String openid,
                              @RequestParam("course_no") String c_no,
                              @RequestParam("moment_content") String m_content) {
        Moment moment = new Moment();
        moment.setUId(openid);
        moment.setCNo(c_no);
        moment.setContent(m_content);
        moment.setRecommended();
        momentService.updateRelationUser2Course(openid,c_no,moment.isRecommended());
        return momentService.saveOrUpdate(moment);
    }

    @DeleteMapping("/delete")
    public boolean deleteMoment(@RequestParam int moment_id) {
        return momentService.removeById(moment_id);
    }

    @GetMapping("/my")
    public Map<String, Object> getMyMoments(@RequestParam("student_id") String u_id,
                                            @RequestParam("page_num") Integer pageNum,
                                            @RequestParam("page_size") Integer pageSize) {
        Message message = new Message();
        List<Map<String, Object>> myMoments = momentService.getMyMoments(u_id, pageNum, pageSize);
        for (Map<String, Object> moment : myMoments)
            moment.put("avatar", User.getUrl(u_id));
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
        List<Map<String, Object>> courseMoments = momentService.getCourseMoments(c_no, pageNum, pageSize);
        for (Map<String, Object> moment : courseMoments)
            moment.put("avatar", User.getUrl(moment.get("u_id").toString()));
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
        Message message = new Message();
        List<Map<String, Object>> followMoments = momentService.getFollowMoments(u_id, pageNum, pageSize);
        for (Map<String, Object> moment : followMoments)
            moment.put("avatar",moment.get("u_id").toString());
        Map<String, Object> data = new HashMap<>();
        data.put("total", momentService.getFollowMomentsTotalNum(u_id));
        data.put("moments", followMoments);
        message.setMessage(data);
        return message;
    }
}
