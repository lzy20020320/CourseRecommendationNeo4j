package com.example.CourseRecommendation.service;

import com.example.CourseRecommendation.dao.CourseRepository;
import com.example.CourseRecommendation.dao.QueryRepository;
import com.example.CourseRecommendation.mapper.CourseMapper;
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

    public String answer(String sentence) {
        List<Term> terms = sentenceSegment(sentence);
        String answer = "";
        System.out.println(terms);
        int question_label = -1;
        try {
            question_label = (int) classify(terms);
            answer = QClassifier.answers.get(question_label);
            System.out.println(question_label);
        } catch (Exception ignore) {
        }
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
                int cnum = 0;
                String tname = getTeacherName(terms);
                cnum = queryRepository.getCourseNumByTName(tname);
                answer = getAnswer5(tname, cnum);
                break;
            }
        }
        return answer;
    }

}
