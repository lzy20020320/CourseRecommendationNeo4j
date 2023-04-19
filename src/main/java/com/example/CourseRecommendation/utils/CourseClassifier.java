package com.example.CourseRecommendation.utils;

import ai.onnxruntime.*;
import com.example.CourseRecommendation.config.MyConfig;


import java.util.HashMap;
import java.util.Map;

public class CourseClassifier {
    public static String courseClassify(String courseName) throws OrtException {
        OrtEnvironment env = OrtEnvironment.getEnvironment();
        OrtSession session = env.createSession(MyConfig.RESOURCE_PATH+"onnx/course_clf.onnx", new OrtSession.SessionOptions());
        String[][] inputString = new String[1][1];
        inputString[0][0] = courseName;
        OnnxTensor inputTensor = OnnxTensor.createTensor(env, inputString);
        Map<String, OnnxTensor> inputMap = new HashMap<>();
        inputMap.put("input", inputTensor);
        try (OrtSession.Result result = session.run(inputMap)) {
            OnnxTensor resultTensor = (OnnxTensor) result.get(0);
            String[] strings = (String[]) resultTensor.getValue();
            return strings[0];
        }
    }
}




