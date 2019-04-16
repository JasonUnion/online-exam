package com.tangyi.service;

import com.tangyi.domain.PaperAnswerPaper;
import com.tangyi.mapper.PaperAnswerPaperMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 试卷答卷关系服务层
 * Created by tangyi on 2017-04-17.
 */
@Service
@Transactional(readOnly = true)
public class PaperAnswerPaperService {

    @Autowired
    PaperAnswerPaperMapper mapper;

    public List<PaperAnswerPaper> getList() {
        return mapper.getList();
    }

    public List<PaperAnswerPaper> getByPaperId(String paperId) {
        return mapper.getByPaperId(paperId);
    }

    public PaperAnswerPaper getByAnswerPaperId(String answerPaperId) {
        return mapper.getByAnswerPaperId(answerPaperId);
    }

    @Transactional(readOnly = false)
    public int save(PaperAnswerPaper paperAnswerPaper) {
        return mapper.insert(paperAnswerPaper);
    }

    @Transactional(readOnly = false)
    public void delete(PaperAnswerPaper paperAnswerPaper) {
        mapper.delete(paperAnswerPaper);
    }
}
