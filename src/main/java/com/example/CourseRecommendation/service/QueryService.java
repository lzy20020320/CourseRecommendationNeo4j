package com.example.CourseRecommendation.service;

import com.example.CourseRecommendation.dao.QueryRepository;
import com.example.CourseRecommendation.utils.QClassifier;
import com.hankcs.hanlp.seg.common.Term;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.CourseRecommendation.utils.QClassifier.*;


@Service
public class QueryService {

    @Autowired
    QueryRepository queryRepository;

    public String answer(String u_id, String sentence) {
        List<Term> terms = sentenceSegment(sentence);
        String answer = "";
        System.out.println(terms);
        int question_label = -1;
        try {
            question_label = (int) classify(terms);
            System.out.println(question_label);
        } catch (Exception ignore) {
        }

        if (userList.containsKey(u_id)) {
            question_label = userList.get(u_id);
            userList.remove(u_id);
            System.out.println("containsKey\n");
        } else {
            String fuzzyCourseName = isFuzzyCourseName(u_id, terms, question_label);
            if (!Objects.equals(fuzzyCourseName, "")) {
                answer = "请问您是指以下课程中的哪个？\n";
                List<String> courses = queryRepository.getFuzzyCourse(fuzzyCourseName);
                for (String courseName : courses)
                    answer = answer + courseName + ',';
                answer = answer + "请输入准确的课程名。";
                return answer;
            }
        }


        answer = QClassifier.answers.get(question_label);
        switch (question_label) {
            case 0: {
                String brief = "";
                String cname = getCourseName(terms);
                brief = queryRepository.getCourseDetail(cname);
                answer = getAnswer0(cname, brief);
                break;
            }
            case 1: {
                String cname = getCourseName(terms);
                String category = "";
                category = queryRepository.getCourseCategoryByCName(cname);
                answer = getAnswer1(cname, category);
                break;
            }
            case 2: {
                List<String> tnames = new ArrayList<>();
                String cname = getCourseName(terms);
                tnames = queryRepository.getTNameByCourseName(cname);
                answer = getAnswer2(cname, tnames);
                break;
            }
            case 3: {
                List<String> cnames = new ArrayList<>();
                String tname = getTeacherName(terms);
                cnames = queryRepository.getCourseNameByTName(tname);
                answer = getAnswer3(tname, cnames);
                break;
            }
            case 4: {
                List<String> categoryNames = new ArrayList<>();
                String tname = getTeacherName(terms);
                categoryNames = queryRepository.getCategoryNameByTName(tname);
                answer = getAnswer4(tname, categoryNames);
                break;
            }
            case 5: {
                String tname = getTeacherName(terms);
                answer = getAnswer5(tname, queryRepository.getCourseNumByTName(tname));
                break;
            }
        }
        return answer;
    }

}
