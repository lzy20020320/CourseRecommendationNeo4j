package com.example.CourseRecommendation.utils.crawler.java.CourseGetter;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CourseGetter {

    public static List<SelectedCourse> UpdatePersonalCourses(String name, String pwd) {
        CrawlerRunner.PersonalCrawl(name, pwd);
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (Exception ignore) {
        }

        return JsonReader.GetPersonalCourses(name);
    }

    public static List<SelectedCourse> GetPersonalCourses(String name) {
        return JsonReader.GetPersonalCourses(name);
    }

    public static List<CourseRaw> GetAllCourses() {
        return JsonReader.GetAllCourses();
    }

    public static List<CourseRaw> UpdateAllCourses() {
        CrawlerRunner.AllCrawl();
        return JsonReader.GetAllCourses();
    }

    public static void saveAllCourseInfo(String name, String pwd) {
        CrawlerRunner.allCourseInfoCrawl(name, pwd);
    }

    public static CourseInfo getCourseInfo(String cno) {
        return JsonReader.GetCourseInfo(cno);
    }

}
