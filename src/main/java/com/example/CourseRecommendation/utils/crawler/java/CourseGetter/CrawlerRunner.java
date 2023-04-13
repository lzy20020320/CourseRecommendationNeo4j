package com.example.CourseRecommendation.utils.crawler.java.CourseGetter;

import java.io.*;

class CrawlerRunner {

    public static void PersonalCrawl(String name, String pwd) {
        try {
            String[] command = {"pipenv", "run", "python", "personalCrawler.py", "-u", name, "-p", pwd};
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.directory(new File("src\\main\\resources\\python"));
            Process process = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void AllCrawl() {
        String name, pwd;
        name = "";          //TODO
        pwd = "";
        try {
            String[] command = {"pipenv", "run", "python", "allCrawler.py", "-u", name, "-p", pwd};
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.directory(new File("src\\main\\resources\\python"));
            Process process = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void allCourseInfoCrawl(String name, String pwd) {
        try {
            String[] command = {"pipenv", "run", "python", "allCourseInfoCrawler.py", "-u", name, "-p", pwd};
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.directory(new File("src\\main\\resources\\python"));
            Process process = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
