package com.tangyi.service;

import com.tangyi.domain.AnswerPaperQuestion;
import com.tangyi.domain.AnswerQuestion;
import com.tangyi.mapper.AnswerPaperQuestionMapper;
import com.tangyi.mapper.AnswerQuestionMapper;
import com.tangyi.utils.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tangyi on 2017/3/29.
 */
@Service
@Transactional(readOnly = true)
public class AnswerPaperQuestionService {

    private static Logger logger = LoggerFactory.getLogger(AnswerPaperQuestionService.class);

    @Autowired
    AnswerPaperQuestionMapper answerPaperQuestionMapper;


    public List<AnswerPaperQuestion> getByPaperId(String id) {
        return answerPaperQuestionMapper.findByPaperId(id);
    }

    public List<AnswerPaperQuestion> getByQuestionId(String id) {
        return answerPaperQuestionMapper.findByQuestionId(id);
    }

    @Transactional(readOnly = false)
    public int saveAnswerPaperQuestion(AnswerPaperQuestion question) {
        return answerPaperQuestionMapper.insert(question);
    }
}
