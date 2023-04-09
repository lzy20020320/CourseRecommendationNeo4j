package com.example.CourseRecommendation.controller.moment;

import com.example.CourseRecommendation.config.MyConfig;
import com.example.CourseRecommendation.controller.message.Message;
import com.example.CourseRecommendation.entity.Moment;
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
    public boolean postMoment(@RequestParam String u_id,@RequestParam String c_no,@RequestParam String m_content) {
        Moment moment = new Moment();
        moment.setUId(u_id);
        moment.setCNo(c_no);
        moment.setContent(m_content);
        moment.setRecommended();
        return momentService.saveOrUpdate(moment);
    }

    @DeleteMapping("/delete")
    public boolean deleteMoment(@RequestParam int moment_id) {
        return momentService.removeById(moment_id);
    }

    @GetMapping("/my")
    public Map<String, Object> getMyMoments(@RequestParam String u_id,
                                            @RequestParam("pagenum") Integer pageNum,
                                            @RequestParam("pagesize") Integer pageSize) {
        Message message = new Message();
        List<Map<String, Object>> myMoments = momentService.getMyMoments(u_id, pageNum, pageSize);
        for (Map<String, Object> moment : myMoments)
            moment.put("avatar", MyConfig.ADDR+"/img/courseicon/icon.jpg");
        Map<String, Object> data = new HashMap<>();
        data.put("total", momentService.getMyMomentsTotalNum(u_id));
        data.put("moments", myMoments);
        message.setMessage(data);
        return message;
    }

    @GetMapping("/course")
    public Map<String, Object> getCourseMoments(@RequestParam String c_no,
                                                @RequestParam("pagenum") Integer pageNum,
                                                @RequestParam("pagesize") Integer pageSize) {
        Message message = new Message();
        List<Map<String, Object>> courseMoments = momentService.getCourseMoments(c_no, pageNum, pageSize);
        for (Map<String, Object> moment : courseMoments)
            moment.put("avatar", MyConfig.ADDR+"/img/courseicon/icon.jpg");
        Map<String, Object> data = new HashMap<>();
        data.put("total", momentService.getCourseMomentsTotalNum(c_no));
        data.put("moments", courseMoments);
        message.setMessage(data);
        return message;
    }

    @GetMapping("/search")
    public Map<String, Object> getFollowMoments(@RequestParam String u_id,
                                                @RequestParam("pagenum") Integer pageNum,
                                                @RequestParam("pagesize") Integer pageSize) {
        Message message = new Message();
        List<Map<String, Object>> followMoments = momentService.getFollowMoments(u_id, pageNum, pageSize);
        for (Map<String, Object> moment : followMoments)http://192.168.137.1:8888
            moment.put("avatar", MyConfig.ADDR+"/img/courseicon/icon.jpg");
        Map<String, Object> data = new HashMap<>();
        data.put("total", momentService.getFollowMomentsTotalNum(u_id));
        data.put("moments", followMoments);
        message.setMessage(data);
        return message;
    }
}
