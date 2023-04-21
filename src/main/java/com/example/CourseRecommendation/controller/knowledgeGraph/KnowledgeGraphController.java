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
    public Map<String, Object> getKnowledgeGraph(@RequestParam("major") String major) {
        System.out.println(major+"\n\n\n\n");
        Message message = new Message();
        Map<String, Object> objectMap = JsonMessageGetter.readJsonFile(MyConfig.RESOURCE_PATH + "json/" + type2TypeName( Integer.parseInt(major)) + ".json");
        message.setMessage(objectMap);
        System.out.println(message);
        return message;
    }

    static public String type2TypeName(int type) {
        switch (type) {
            case 1:
                return "science";
            case 2:
                return "engineering";
            case 3:
                return "philosophy";
            case 4:
                return "economics";
            case 5:
                return "law";
            case 6:
                return "literature";
            case 7:
                return "history";
            case 8:
                return "management";
            case 9:
                return "art";
            default:
                return "total";
        }
    }
}
