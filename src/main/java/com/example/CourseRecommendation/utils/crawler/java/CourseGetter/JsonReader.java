package com.example.CourseRecommendation.utils.crawler.java.CourseGetter;

import com.example.CourseRecommendation.config.MyConfig;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


class JsonReader {

    public static List<SelectedCourse> GetPersonalCourses(String name) {
        JSONParser parser = new JSONParser();
        List<SelectedCourse> cours = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(MyConfig.RESOURCE_PATH+"python\\interval-crawler-task-result\\terms\\" + name + ".json");
            Object obj = parser.parse(fileReader);
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray coursesArray = (JSONArray) jsonObject.get("cours");
            for (Object courseObj : coursesArray) {
                JSONObject courseJson = (JSONObject) courseObj;
                SelectedCourse selectedCourse = new SelectedCourse();
                selectedCourse.setCourseKind((String) courseJson.get("category"));
                selectedCourse.setCourseId((String) courseJson.get("course_number"));
                selectedCourse.setCourseName((String) courseJson.get("course_name"));
                selectedCourse.setCredits(Float.parseFloat((String) courseJson.get("credit")));
                selectedCourse.setSelect((Boolean) courseJson.get("select"));
                selectedCourse.setSemester((String) courseJson.get("semester"));
                cours.add(selectedCourse);
            }
            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (SelectedCourse selectedCourse : cours) System.out.println(selectedCourse.toString());
        return cours;
    }

    public static List<CourseRaw> GetAllCourses() {
        JSONParser parser = new JSONParser();
        List<CourseRaw> cours = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(MyConfig.RESOURCE_PATH+"python\\interval-crawler-task-result\\terms\\" + "21001" + ".json");
            Object obj = parser.parse(fileReader);
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray coursesArray = (JSONArray) jsonObject.get("cours");

            for (Object courseObj : coursesArray) {
                // Create a new Course object
                CourseRaw course = new CourseRaw();
                JSONObject courseJson = (JSONObject) courseObj;
                // Set the properties of the Course object using data from the JSON object
                course.setCampus((String) courseJson.get("campus"));
                course.setCapacity(Integer.parseInt((String) courseJson.get("capacity")));
                course.setClassTime((String) courseJson.get("classTime"));
                course.setCourseId((String) courseJson.get("courseId"));
                course.setCourseName((String) courseJson.get("courseName"));
                course.setCredit(Float.parseFloat((String) courseJson.get("credit")));


                course.setNumber((String) courseJson.get("number"));
                course.setPosition((String) courseJson.get("position"));
                course.setTeacherId((String) courseJson.get("teacherId"));
                course.setTeacherName((String) courseJson.get("teacherName"));
                course.setTeacherTitle((String) courseJson.get("teacherTitle"));
                cours.add(course);
                // Do something with the Course object
            }
            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (CourseRaw course : cours) System.out.println(course.toString());
        return cours;
    }

    public static CourseInfo GetCourseInfo(String course_no) {
        JSONParser parser = new JSONParser();
        CourseInfo courseInfo = new CourseInfo();
        try {
            FileReader fileReader = new FileReader(MyConfig.RESOURCE_PATH+"python\\interval-crawler-task-result\\courseInfo\\" + course_no + ".json");
            Object obj = parser.parse(fileReader);
            JSONObject jsonObject = (JSONObject) obj;
            Object courseObj = jsonObject.get("courseInfo");
            JSONObject courseJson = (JSONObject) courseObj;

            courseInfo.setNo((String) courseJson.get("no"));
            courseInfo.setAim((String) courseJson.get("aim"));
            courseInfo.setContent((String) courseJson.get("content"));
            courseInfo.setReference((String) courseJson.get("reference"));
            courseInfo.setRequisites((String) courseJson.get("requisites"));
            courseInfo.setTarget((String) courseJson.get("target"));


            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(courseInfo);
        return courseInfo;
    }

}
