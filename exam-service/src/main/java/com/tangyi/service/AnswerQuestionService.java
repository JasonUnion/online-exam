package com.tangyi.service;

import com.tangyi.constant.RedisKey;
import com.tangyi.domain.AnswerQuestion;
import com.tangyi.mapper.AnswerQuestionMapper;
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
public class AnswerQuestionService {

    private static Logger logger = LoggerFactory.getLogger(AnswerQuestionService.class);

    @Autowired
    AnswerQuestionMapper answerQuestionMapper;

    @Autowired
    private CacheService cacheService;

    public List<AnswerQuestion> getAnswerQuestionList() {
        return answerQuestionMapper.findAll();
    }

    public AnswerQuestion getAnswerQuestionById(String id) {
        //获取缓存数据
        AnswerQuestion answerQuestion = (AnswerQuestion) cacheService.get(RedisKey.ANSWER_QUESTION_ID + id);
        if(answerQuestion == null) {
            answerQuestion = answerQuestionMapper.findByAnswerQuestionId(id);
            cacheService.set(RedisKey.ANSWER_QUESTION_ID + id, answerQuestion);
        }
        return answerQuestion;
    }

    public AnswerQuestion getAnswerQuestionByName(String name) {
        //获取缓存数据
        AnswerQuestion answerQuestion = (AnswerQuestion) cacheService.get(RedisKey.ANSWER_QUESTION_NAME + name);
        if(answerQuestion == null) {
            answerQuestion = answerQuestionMapper.findByAnswerQuestionTitle(name);
            cacheService.set(RedisKey.ANSWER_QUESTION_NAME + name, answerQuestion);
        }
        return answerQuestion;
    }

    public AnswerQuestion getAnswerQuestionByPaperIdAndQuestionNumber(String paperId, Integer number) {
        return answerQuestionMapper.findByPaperIdAndQuestionNumber(paperId, number);
    }

    public List<AnswerQuestion> findByAnswerPaperId(String id) {
        return answerQuestionMapper.findByAnswerPaperId(id);
    }

    @Transactional(readOnly = false)
    public int saveAnswerQuestion(AnswerQuestion answerQuestion) {
        return answerQuestionMapper.insert(answerQuestion);
    }

    @Transactional(readOnly = false)
    public void updateAnswerQuestion(AnswerQuestion answerQuestion) {
        AnswerQuestion answerQuestion1 = answerQuestionMapper.findByAnswerQuestionId(answerQuestion.getId());
        // TODO: 2017/3/29
        //修改数据
        if(answerQuestion.getAnswer() != null) {
            answerQuestion1.setAnswer(answerQuestion.getAnswer());
        }
        if(answerQuestion.getMarkScore() != null) {
            answerQuestion1.setMarkScore(answerQuestion.getMarkScore());
        }
        answerQuestionMapper.update(answerQuestion1);
        //删除缓存
        cacheService.delete(RedisKey.ANSWER_QUESTION_ID + answerQuestion1.getId());
    }

    @Transactional(readOnly = false)
    public void deletePaper(String id) {
        answerQuestionMapper.delete(id);
        //删除缓存
        cacheService.delete(RedisKey.ANSWER_QUESTION_ID + id);
    }
}
