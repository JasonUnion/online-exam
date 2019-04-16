package com.tangyi.service;

import com.tangyi.domain.SubjectPaper;
import com.tangyi.mapper.SubjectPaperMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 科目试卷服务层，提供根据科目id获取试卷id，根据试卷id获取科目id
 * Created by tangyi on 2017/3/20.
 */
@Service
@Transactional(readOnly = true)
public class SubjectPaperService {

    @Autowired
    SubjectPaperMapper subjectPaperMapper;

    /**
     * 根据科目id获取SubjectPaper
     * @param subjectPaper
     * @return
     */
    public List<SubjectPaper> findBySubjectId(SubjectPaper subjectPaper) {
        return subjectPaperMapper.findBySubjectId(subjectPaper);
    }

    /**
     * 根据试卷id获取SubjectPaper
     * @param subjectPaper
     * @return
     */
    public SubjectPaper findByPaperId(SubjectPaper subjectPaper) {
        return subjectPaperMapper.findByPaperId(subjectPaper);
    }

    /**
     * 保存
     * @param subjectPaper
     * @return
     */
    @Transactional(readOnly = false)
    public Integer saveSubjectPaper(SubjectPaper subjectPaper) {
        return subjectPaperMapper.insert(subjectPaper);
    }

    /**
     * 根据试卷id删除
     * @param subjectPaper
     */
    @Transactional(readOnly = false)
    public void deleteSubjectPaperByPaperId(SubjectPaper subjectPaper) {
        subjectPaperMapper.deleteByPaperId(subjectPaper);
    }

    /**
     * 根据科目id删除
     * @param subjectPaper
     */
    @Transactional(readOnly = false)
    public void deleteSubjectPaperBySubjectId(SubjectPaper subjectPaper) {
        subjectPaperMapper.deleteBySubjectId(subjectPaper);
    }
}
