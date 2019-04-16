package com.tangyi.service;

import com.tangyi.constant.RedisKey;
import com.tangyi.domain.Paper;
import com.tangyi.domain.Subject;
import com.tangyi.domain.SubjectPaper;
import com.tangyi.mapper.PaperMapper;
import com.tangyi.mapper.SubjectMapper;
import com.tangyi.mapper.SubjectPaperMapper;
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
public class PaperService {

    private static Logger logger = LoggerFactory.getLogger(PaperService.class);

    @Autowired
    PaperMapper paperMapper;

    @Autowired
    SubjectPaperMapper subjectPaperMapper;

    @Autowired
    SubjectMapper subjectMapper;

    @Autowired
    private CacheService cacheService;

    /**
     * 获取所有试卷
     * @return
     */
    public List<Paper> getPaperList() {
        return paperMapper.findAll();
    }

    /**
     * 获取所有正式试卷
     * @return
     */
    public List<Paper> getOfficialPaperList() {
        return paperMapper.findOfficial();
    }

    /**
     * 获取所有模拟试卷
     * @return
     */
    public List<Paper> getSimulatePaperList() {
        return paperMapper.findSimulate();
    }

    /**
     * 获取所有练习试卷
     * @return
     */
    public List<Paper> getPracticePaperList() {
        return paperMapper.findPractice();
    }

    /**
     * 获取已发布的正式试卷
     * @return
     */
    public List<Paper> getPublishedOfficialPaperList() {
        return paperMapper.findPublishedOfficial();
    }

    /**
     * 获取已发布的模拟试卷
     * @return
     */
    public List<Paper> getPublishedsimulatePaperList() {
        return paperMapper.findPublishedSimulate();
    }

    public Paper getPaperById(String id) {
        //获取缓存数据
        Paper paper = (Paper) cacheService.get(RedisKey.PAPER_ID + id);
        if(paper == null) {
            paper = paperMapper.findByPaperId(id);
            cacheService.set(RedisKey.PAPER_ID + id, paper);
        }
        return paper;
    }

    public Paper getPaperByName(String name) {
        //获取缓存数据
        Paper paper = (Paper) cacheService.get(RedisKey.PAPER_NAME + name);
        if(paper == null) {
            paper = paperMapper.findByPaperName(name);
            cacheService.set(RedisKey.PAPER_NAME + name, paper);
        }
        return paper;
    }

    /**
     * 模糊查询
     * @param name
     * @return
     */
    public List<Paper> getPaperFuzzy(String name) {
        return paperMapper.fuzzyFindByPaperName(name);
    }

    @Transactional(readOnly = false)
    public int savePaper(String id, Paper paper) {
        Subject subject = subjectMapper.findBySubjectId(id);
        //生产唯一id
        String paperId = IdGen.uuid();
        paper.setId(paperId);
        //设置所属试卷
        paper.setSubjectName(subject.getName());

        //设置发布状态，默认是否
        paper.setPublish("否");

        //默认参与人数0
        paper.setPeoples("0");

        paperMapper.insert(paper);
        SubjectPaper subjectPaper = new SubjectPaper();
        subjectPaper.setSubjectId(id);
        subjectPaper.setPaperId(paperId);
        return subjectPaperMapper.insert(subjectPaper);
    }

    @Transactional(readOnly = false)
    public void updatePaper(Paper paper) {
        Paper paper1 = paperMapper.findByPaperId(paper.getId());
        //修改数据
        if(paper.getName() != null) {
            paper1.setName(paper.getName());
        }
        if(paper.getCreated() != null) {
            paper1.setCreated(paper.getCreated());
        }
        if(paper.getStartTime() != null) {
            paper1.setStartTime(paper.getStartTime());
        }
        if(paper.getEndTime() != null) {
            paper1.setEndTime(paper.getEndTime());
        }
        if(paper.getSubjectName() != null) {
            paper1.setSubjectName(paper.getSubjectName());
        }
        if(paper.getPublish() != null) {
            paper1.setPublish(paper.getPublish());
        }
        if(paper.getPeoples() != null) {
            paper1.setPeoples(paper.getPeoples());
        }
        paperMapper.update(paper1);
        //删除缓存
        cacheService.delete(RedisKey.PAPER_ID + paper.getId());
    }

    @Transactional(readOnly = false)
    public void deletePaper(String id) {
        paperMapper.delete(id);
        //删除缓存
        cacheService.delete(RedisKey.PAPER_ID + id);
    }

}
