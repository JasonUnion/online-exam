package com.tangyi.service;

import com.tangyi.constant.RedisKey;
import com.tangyi.domain.WrongQuestion;
import com.tangyi.mapper.PaperMapper;
import com.tangyi.mapper.PaperQuestionMapper;
import com.tangyi.mapper.WrongQuestionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tangyi on 2017/4/7.
 */
@Service
@Transactional(readOnly = true)
public class WrongQuestionService {

    private static Logger logger = LoggerFactory.getLogger(WrongQuestionService.class);

    @Autowired
    WrongQuestionMapper wrongQuestionMapper;

    @Autowired
    PaperQuestionMapper paperQuestionMapper;

    @Autowired
    PaperMapper paperMapper;

    @Autowired
    private CacheService cacheService;

    public List<WrongQuestion> getWrongQuestionList() {
        return wrongQuestionMapper.findAll();
    }

    public List<WrongQuestion> getWrongQuestionListByUsername(String username) {
        return wrongQuestionMapper.findByUsername(username);
    }

    public WrongQuestion getWrongQuestion(String questionId) {
        //获取缓存数据
        WrongQuestion wrongQuestion = (WrongQuestion) cacheService.get(RedisKey.WRONG_QUESTION_ID + questionId);
        if(wrongQuestion == null) {
            wrongQuestion = wrongQuestionMapper.findByQuestionId(questionId);
            cacheService.set(RedisKey.WRONG_QUESTION_ID + questionId, wrongQuestion);
        }
        return wrongQuestion;
    }

    public WrongQuestion getWrongQuestionByPaperIdAndQuestionNumber(String paperId, String number) {
        return wrongQuestionMapper.findByPaperIdAndQuestionNumber(paperId, number);
    }


    /**
     * 根据试卷id获取题目数量
     * @param username
     * @return
     */
    public Integer countByUsername(String username) {
        return wrongQuestionMapper.countByUsername(username);
    }


    /**
     * 模糊查询
     * @param name
     * @return
     */
    public List<WrongQuestion> getQuestionFuzzy(String name) {
        return wrongQuestionMapper.fuzzyFindByQuestionName(name);
    }

    @Transactional(readOnly = false)
    public int saveQuestion(WrongQuestion wrongQuestion) {
        return wrongQuestionMapper.insert(wrongQuestion);
    }

    /**
     * 更新题目
     * @param wrongQuestion
     */
    @Transactional(readOnly = false)
    public void updateQuestion(WrongQuestion wrongQuestion) {
        //先将数据查询出来，在内存修改好，再更新到数据库
        WrongQuestion wrongQuestion1 = wrongQuestionMapper.findByQuestionId(wrongQuestion.getId());
        if(wrongQuestion.getTitle() != null) {
            wrongQuestion1.setTitle(wrongQuestion.getTitle());
        }
        if(wrongQuestion.getSimpleTitle() != null) {
            wrongQuestion1.setSimpleTitle(wrongQuestion.getSimpleTitle());
        }
        if(wrongQuestion.getTitle() != null) {
            wrongQuestion1.setType(wrongQuestion.getType());
        }
        if(wrongQuestion.getNumber() != null) {
            wrongQuestion1.setNumber(wrongQuestion.getNumber());
        }
        if(wrongQuestion.getOptionA() != null) {
            wrongQuestion1.setOptionA(wrongQuestion.getOptionA());
        }
        if(wrongQuestion.getOptionB() != null) {
            wrongQuestion1.setOptionB(wrongQuestion.getOptionB());
        }
        if(wrongQuestion.getOptionC() != null) {
            wrongQuestion1.setOptionC(wrongQuestion.getOptionC());
        }
        if(wrongQuestion.getOptionD() != null) {
            wrongQuestion1.setOptionD(wrongQuestion.getOptionD());
        }
        if(wrongQuestion.getContent() != null) {
            wrongQuestion1.setContent(wrongQuestion.getContent());
        }
        if(wrongQuestion.getScore() != null) {
            wrongQuestion1.setScore(wrongQuestion.getScore());
        }
        if(wrongQuestion.getAnalysis() != null) {
            wrongQuestion1.setAnalysis(wrongQuestion.getAnalysis());
        }
        if(wrongQuestion.getAnswer() != null) {
            wrongQuestion1.setAnswer(wrongQuestion.getAnswer());
        }
        if(wrongQuestion.getPaperName() != null) {
            wrongQuestion1.setPaperName(wrongQuestion.getPaperName());
        }
        wrongQuestionMapper.update(wrongQuestion1);
        //清理缓存
        cacheService.delete(RedisKey.WRONG_QUESTION_ID + wrongQuestion.getId());
    }

    @Transactional(readOnly = false)
    public void deleteQuestion(String questionId) {
        wrongQuestionMapper.delete(questionId);
        //清理缓存
        cacheService.delete(RedisKey.WRONG_QUESTION_ID + questionId);
    }
}
