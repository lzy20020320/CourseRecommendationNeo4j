package com.example.CourseRecommendation.utils;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

public class JsonMessageGetter {

    public static Map<String, Object> readJsonFile(String fileName) {
        Gson gson = new Gson();
        String json = "";
        try {
            File file = new File(fileName);
            Reader reader = new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8);
            int ch = 0;
            StringBuilder buffer = new StringBuilder();
            while ((ch = reader.read()) != -1) {
                buffer.append((char) ch);
            }
            reader.close();
            json = buffer.toString();
            return gson.fromJson(json, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
