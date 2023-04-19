package com.example.CourseRecommendation.controller.knowledgeGraph;

import com.example.CourseRecommendation.config.MyConfig;
import com.example.CourseRecommendation.controller.message.Message;
import com.example.CourseRecommendation.utils.JsonMessageGetter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/knowledge_graph")
public class KnowledgeGraphController {


    @GetMapping("/data")
    public Map<String, Object> getKnowledgeGraph(@RequestParam("type") int type) {
        Message message = new Message();
        Map<String, Object> objectMap = JsonMessageGetter.readJsonFile(MyConfig.RESOURCE_PATH + "json/" + type2TypeName(type) + ".json");
//        Map<String, Object> objectMap = JsonMessageGetter.readJsonFile("json/knowledgeGraph.json");
        message.setMessage(objectMap);
        return message;
    }

    static private String type2TypeName(int type) {
        switch (type) {
            case 0:
                return "science";
            case 1:
                return "engineering";
            default:
                return "total";
        }
    }
}
