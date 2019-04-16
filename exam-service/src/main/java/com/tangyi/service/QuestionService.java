package com.tangyi.service;

import com.tangyi.constant.RedisKey;
import com.tangyi.domain.Paper;
import com.tangyi.domain.PaperQuestion;
import com.tangyi.domain.Question;
import com.tangyi.mapper.PaperMapper;
import com.tangyi.mapper.PaperQuestionMapper;
import com.tangyi.mapper.QuestionMapper;
import com.tangyi.utils.IdGen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tangyi on 2017/3/20.
 */
@Service
@Transactional(readOnly = true)
public class QuestionService {

    private static Logger logger = LoggerFactory.getLogger(QuestionService.class);

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    PaperQuestionMapper paperQuestionMapper;

    @Autowired
    PaperMapper paperMapper;

    @Autowired
    private CacheService cacheService;

    public List<Question> getQuestionList() {
        return questionMapper.findAll();
    }

    public List<Question> getQuestionListByPaper(String paperId) {
        return questionMapper.findByPaperId(paperId);
    }

    public Question getQuestion(String questionId) {

        //获取缓存数据
        Question question = (Question) cacheService.get(RedisKey.QUESTION_ID + questionId);
        if(question == null) {
            question = questionMapper.findByQuestionId(questionId);
            cacheService.set(RedisKey.QUESTION_ID + questionId, question);
        }
        return question;
    }

    public Question getQuestionByPaperIdAndQuestionNumber(String paperId, Integer number) {
        return questionMapper.findByPaperIdAndQuestionNumber(paperId, number);
    }

    /**
     * 根据试卷获取题目
     * @param questionId
     * @return
     */
    public List<Question> getQuestionByPaperId(String questionId) {
        return questionMapper.findByPaperId(questionId);
    }

    /**
     * 根据试卷id获取题目数量
     * @param paperId
     * @return
     */
    public Integer countByPaperId(String paperId) {
        return questionMapper.countByPaperId(paperId);
    }

    /**
     * 根据试卷获取题目,不返回答案
     * @param questionId
     * @return
     */
    public List<Question> getQuestionByPaperIdIgnoreAnswer(String questionId) {
        return questionMapper.findByPaperIdIgnoreAnswer(questionId);
    }

    /**
     * 模糊查询
     * @param name
     * @return
     */
    public List<Question> getQuestionFuzzy(String name) {
        return questionMapper.fuzzyFindByQuestionName(name);
    }

    @Transactional(readOnly = false)
    public int saveQuestion(String id, Question question) {
        //生成唯一id
        String questionId = IdGen.uuid();
        question.setId(questionId);
        if(question.getTitle().length() > 15) {
            question.setSimpleTitle(question.getTitle().substring(0, 15) + "...");
        }else {
            question.setSimpleTitle(question.getTitle());
        }

        Paper paper = paperMapper.findByPaperId(id);
        if(paper != null) {
            question.setPaperName(paper.getName());
        }
        questionMapper.insert(question);
        PaperQuestion paperQuestion = new PaperQuestion();
        paperQuestion.setPaperId(id);
        paperQuestion.setQuestionId(questionId);
        return paperQuestionMapper.insert(paperQuestion);
    }

    /**
     * 更新题目
     * @param question
     */
    @Transactional(readOnly = false)
    public void updateQuestion(Question question) {
        //先将数据查询出来，在内存修改好，再更新到数据库
        Question question1 = questionMapper.findByQuestionId(question.getId());
        if(question.getTitle() != null) {
            question1.setTitle(question.getTitle());
        }
        if(question.getSimpleTitle() != null) {
            question1.setSimpleTitle(question.getSimpleTitle());
        }
        if(question.getTitle() != null) {
            question1.setType(question.getType());
        }
        if(question.getNumber() != null) {
            question1.setNumber(question.getNumber());
        }
        if(question.getOptionA() != null) {
            question1.setOptionA(question.getOptionA());
        }
        if(question.getOptionB() != null) {
            question1.setOptionB(question.getOptionB());
        }
        if(question.getOptionC() != null) {
            question1.setOptionC(question.getOptionC());
        }
        if(question.getOptionD() != null) {
            question1.setOptionD(question.getOptionD());
        }
        if(question.getContent() != null) {
            question1.setContent(question.getContent());
        }
        if(question.getScore() != null) {
            question1.setScore(question.getScore());
        }
        if(question.getAnalysis() != null) {
            question1.setAnalysis(question.getAnalysis());
        }
        if(question.getAnswer() != null) {
            question1.setAnswer(question.getAnswer());
        }
        if(question.getPaperName() != null) {
            question1.setPaperName(question.getPaperName());
        }
        questionMapper.update(question1);
        //清理缓存
        cacheService.del(RedisKey.QUESTION_ID + question.getId());
    }

    @Transactional(readOnly = false)
    public void deleteQuestion(String questionId) {
        questionMapper.delete(questionId);
        PaperQuestion paperQuestion = new PaperQuestion();
        paperQuestion.setQuestionId(questionId);
        paperQuestionMapper.deleteByQuestionId(paperQuestion);
        //清理缓存
        cacheService.del(RedisKey.QUESTION_ID + questionId);
    }
}
