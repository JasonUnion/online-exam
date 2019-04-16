package com.tangyi.service;

import com.tangyi.domain.PaperQuestion;
import com.tangyi.domain.SubjectPaper;
import com.tangyi.mapper.PaperQuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by tangyi on 2017/3/20.
 */
@Service
@Transactional(readOnly = true)
public class PaperQuestionService {

    @Autowired
    PaperQuestionMapper paperQuestionMapper;

    /**
     * 根据试卷id查找PaperQuestion
     * @param paperQuestion
     * @return
     */
    public PaperQuestion findByPaperId(PaperQuestion paperQuestion) {
        return paperQuestionMapper.findByPaperId(paperQuestion);
    }

    /**
     * 根据题目id查找PaperQuestion
     * @param paperQuestion
     * @return
     */
    public PaperQuestion findByQuestionId(PaperQuestion paperQuestion) {
        return paperQuestionMapper.findByQuestionId(paperQuestion);
    }

    /**
     * 保存
     * @param paperQuestion
     * @return
     */
    @Transactional(readOnly = false)
    public Integer savePaperQuestion(PaperQuestion paperQuestion) {
        return paperQuestionMapper.insert(paperQuestion);
    }

    /**
     * 根据试卷id删除
     * @param paperQuestion
     */
    @Transactional(readOnly = false)
    public void deletePaperQuestionByPaperId(PaperQuestion paperQuestion) {
        paperQuestionMapper.deleteByPaperId(paperQuestion);
    }

    /**
     * 根据科目id删除
     * @param paperQuestion
     */
    @Transactional(readOnly = false)
    public void deletePaperQuestionByQuestionId(PaperQuestion paperQuestion) {
        paperQuestionMapper.deleteByQuestionId(paperQuestion);
    }
}
