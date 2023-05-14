package com.example.CourseRecommendation.controller.knowledgeGraph;

import com.example.CourseRecommendation.controller.message.Message;
import com.example.CourseRecommendation.entity.CourseMap;
import com.example.CourseRecommendation.utils.CourseMapConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/knowledge_graph")
public class KnowledgeGraphController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping("/data")
    public Map<String, Object> getKnowledgeGraph(@RequestParam("major") String major) throws IllegalAccessException {
        System.out.println(major+"\n\n\n\n");
        Message message = new Message();
        Query query = new Query();
        Criteria nameCriteria=Criteria.where("name").is(type2TypeName( Integer.parseInt(major)));
        query.addCriteria(nameCriteria);
        Map<String, Object> objectMap = CourseMapConverter.convertToMap(Objects.requireNonNull(mongoTemplate.findOne(query, CourseMap.class)));
        message.setMessage(objectMap);
        System.out.println(message);
        return message;
    }

    static public String type2TypeName(int type) {
        return switch (type) {
            case 1 -> "理学";
            case 2 -> "工学";
            case 3 -> "哲学";
            case 4 -> "经济学";
            case 5 -> "法学";
            case 6 -> "文学";
            case 7 -> "历史学";
            case 8 -> "管理学";
            case 9 -> "艺术学";
            default -> "学科";
        };
    }
}
