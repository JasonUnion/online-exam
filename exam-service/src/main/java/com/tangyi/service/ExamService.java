package com.tangyi.service;

import com.tangyi.domain.*;
import com.tangyi.mapper.AnswerPaperMapper;
import com.tangyi.mapper.AnswerPaperQuestionMapper;
import com.tangyi.mapper.AnswerQuestionMapper;
import com.tangyi.utils.IdGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tangyi on 2017/3/29.
 */
@Service
@Transactional(readOnly = true)
public class ExamService {

    @Autowired
    AnswerPaperMapper answerPaperMapper;

    @Autowired
    AnswerQuestionMapper answerQuestionMapper;

    @Autowired
    AnswerPaperQuestionMapper answerPaperQuestionMapper;

    @Transactional(readOnly = false)
    public int saveAnswerPaper(Paper paper, String username) {

        AnswerPaper answerPaper = new AnswerPaper();
        int count = 0;
        //答卷id
        String answerPaperId = IdGen.uuid();
        answerPaper.setId(answerPaperId);
        answerPaper.setAnswerTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        answerPaper.setPaperName(paper.getName());
        answerPaper.setAnswerUser(username);
        answerPaper.setChecked("false");
        //保存答卷
        count += answerPaperMapper.insert(answerPaper);

        AnswerQuestion answerQuestion = null;
        AnswerPaperQuestion answerPaperQuestion = null;
        String answerQuestionId = null;

        for(Question question : paper.getQuestions()) {
            answerQuestion = new AnswerQuestion();
            answerQuestionId = IdGen.uuid();
            //初始化信息
            answerQuestion.setId(answerQuestionId);
            answerQuestion.setTitle(question.getTitle());
            answerQuestion.setType(question.getType());
            answerQuestion.setNumber(question.getNumber());
            answerQuestion.setOptionA(question.getOptionA());
            answerQuestion.setOptionB(question.getOptionB());
            answerQuestion.setOptionC(question.getOptionC());
            answerQuestion.setOptionD(question.getOptionD());
            answerQuestion.setContent(question.getContent());
            answerQuestion.setScore(question.getScore());
            answerQuestion.setAnalysis(question.getAnalysis());
            answerQuestion.setAnswer(question.getAnswer());
            //保存
            count += answerQuestionMapper.insert(answerQuestion);

            //保存关系
            answerPaperQuestion = new AnswerPaperQuestion();
            answerPaperQuestion.setAnswerPaperId(answerPaperId);
            answerPaperQuestion.setAnswerQuestionId(answerQuestionId);
            count += answerPaperQuestionMapper.insert(answerPaperQuestion);
        }
        return count;
    }

    @Transactional(readOnly = false)
    public void updateAnswerPaper(AnswerPaper answerPaper) {
        AnswerPaper answerPaper1 = answerPaperMapper.findByAnswerPaperId(answerPaper.getId());
        if(answerPaper1 != null) {
            if(answerPaper.getAnswerTime() != null) {
                answerPaper1.setAnswerTime(answerPaper.getAnswerTime());
            }
            if(answerPaper.getFinished() != null) {
                answerPaper1.setFinished(answerPaper.getFinished());
            }
            if(answerPaper.getType() != null) {
                answerPaper1.setType(answerPaper.getType());
            }

            answerPaperMapper.update(answerPaper1);
        }
    }
}
