package com.example.CourseRecommendation.utils;

import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtException;
import ai.onnxruntime.OrtSession;
import com.example.CourseRecommendation.config.MyConfig;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class QClassifier {


    final static List<String> vocabulary_list = initVocabulary();

    public final static List<String> answers = initAnswers();

    public static Map<String,Integer> userList = new HashMap<>();


    static public long classify(List<Term> seg) throws OrtException {
        String abstraction = abstractSentence(seg);
        List<Term> abstractSeg = sentenceSegment(abstraction);
        float[] vector = sentence2Vector(abstractSeg);
        OrtEnvironment env = OrtEnvironment.getEnvironment();
        OrtSession session = env.createSession(MyConfig.RESOURCE_PATH+"onnx/QClassifier.onnx", new OrtSession.SessionOptions());
        float[][] inputs = new float[][]{vector};

        OnnxTensor inputTensor = OnnxTensor.createTensor(env, inputs);
        Map<String, OnnxTensor> inputMap = new HashMap<>();
        inputMap.put("input", inputTensor);
        long[] longs;
        try (OrtSession.Result result = session.run(inputMap)) {
            OnnxTensor resultTensor = (OnnxTensor) result.get(0);
            longs = (long[]) resultTensor.getValue();
            return longs[0];
        } catch (OrtException ignore) {
            return 0;
        }
    }

    public static List<Term> sentenceSegment(String sentence) {
        List<Term> seg = new ArrayList<>();
        try {
            Segment segment = HanLP.newSegment();
            segment.enableCustomDictionary(true);
            seg = segment.seg(sentence);
        } catch (Exception ex) {
            System.out.println(ex.getClass() + "," + ex.getMessage());
        }
        return seg;
    }

    private static String abstractSentence(List<Term> seg) {
        String abstraction = "";
        for (Term term : seg) {
            if (Objects.equals(term.nature.toString(), "nm")
                    || Objects.equals(term.nature.toString(), "nz"))
                abstraction = abstraction + "nm";
            else if (Objects.equals(term.nature.toString(), "nr"))
                abstraction = abstraction + "nnt";
            else
                abstraction = abstraction + term.word;
        }
        return abstraction;
    }


    private static float[] sentence2Vector(List<Term> seg) {
        float[] input = new float[vocabulary_list.size()];
        List<String> word_list = new ArrayList<>();
        for (Term term : seg) word_list.add(term.word);
        for (String word : word_list) {
            int find = vocabulary_list.indexOf(word);
            if (find != -1)
                input[find] = 1;
        }
        return input;
    }

    private static List<String> initVocabulary() {
        List<String> wordsList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(MyConfig.RESOURCE_PATH+"txt/vocabulary.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(":");
                wordsList.add(words[1].trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordsList;
    }

    private static List<String> initAnswers() {
        List<String> answersList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(MyConfig.RESOURCE_PATH+"txt/answer.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(":");
                answersList.add(words[1].trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answersList;
    }

    // 0:cname的简介如下：content。
    public static String getAnswer0(String cname, String brief) {
        if (brief == null || brief.length() == 0 || cname.length() == 0)
            return "抱歉，暂无该课程的简介。";
        String answer = answers.get(0);
        answer = answer.replace("cname", cname);
        answer = answer.replace("content", brief);
        System.out.println(answer);
        return answer;
    }


    // 1:cname课程的类型是category。
    public static String getAnswer1(String cname, String category) {
        if (category == null || category.length() == 0 || cname.length() == 0)
            return "抱歉，未查询到该课程的类型。";
        String answer = answers.get(1);
        answer = answer.replace("cname", cname);
        answer = answer.replace("category", category);
        System.out.println(answer);
        return answer;
    }


    // 2:有tname老师教cname课程。
    public static String getAnswer2(String cname, List<String> tname_list) {
        if (tname_list == null || tname_list.size() == 0 || cname.length() == 0)
            return "抱歉，未查询到教授该课程的教师。";
        String answer = answers.get(2);
        String tnames = "";
        for (String tname : tname_list) {
            tnames = tnames + tname;
            tnames = tnames + ",";
        }
        if (!tnames.isEmpty())
            tnames = tnames.substring(0, tnames.length() - 1);
        answer = answer.replace("tname", tnames);
        answer = answer.replace("cname", cname);
        System.out.println(answer);
        return answer;
    }

    // 3:tname老师教授以下这些课程：cname。
    public static String getAnswer3(String tname, List<String> cname_list) {
        if (cname_list == null || cname_list.size() == 0 || tname.length() == 0)
            return "抱歉，未查询到该教师教授的课程。";
        String answer = answers.get(3);
        String cnames = "";
        for (String cname : cname_list) {
            cnames = cnames + cname;
            cnames = cnames + ",";
        }
        if (!cnames.isEmpty())
            cnames = cnames.substring(0, cnames.length() - 1);
        answer = answer.replace("tname", tname);
        answer = answer.replace("cname", cnames);
        System.out.println(answer);
        return answer;
    }


    // 4:tname老师教授以下这些类型的课程：category。
    public static String getAnswer4(String tname, List<String> category_list) {
        if (category_list == null || category_list.size() == 0 || tname.length() == 0)
            return "抱歉，未查询到该教师教授的课程类别。";
        String categorys = "";
        for (String category : category_list) {
            categorys = categorys + category;
            categorys = categorys + ",";
        }
        if (!categorys.isEmpty())
            categorys = categorys.substring(0, categorys.length() - 1);
        String answer = answers.get(4);
        answer = answer.replace("tname", tname);
        answer = answer.replace("category", categorys);
        System.out.println(answer);
        return answer;
    }


    // 5:tname老师教的课程总数为cnum。
    public static String getAnswer5(String tname, int cnum) {
        String answer = answers.get(5);
        answer = answer.replace("tname", tname);
        answer = answer.replace("cnum", String.valueOf(cnum));
        System.out.println(answer);
        return answer;
    }

    public static String getTeacherName(List<Term> terms) {
        for (Term term : terms)
            if (Objects.equals(term.nature.toString(), "nr"))
                return term.word;
        return "";
    }

    public static String getCourseName(List<Term> terms) {
        for (Term term : terms)
            if (Objects.equals(term.nature.toString(), "nm"))
                return term.word;
        return "";
    }

    public static String isFuzzyCourseName(String u_id,List<Term> terms,int QLabel){
        for (Term term : terms)
            if (Objects.equals(term.nature.toString(), "nz")){
                userList.put(u_id,QLabel);
                return term.word;
            }
        return "";
    }

}
