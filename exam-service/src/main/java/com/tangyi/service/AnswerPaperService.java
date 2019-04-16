package com.tangyi.service;

import com.tangyi.constant.RedisKey;
import com.tangyi.domain.AnswerPaper;
import com.tangyi.domain.PaperAnalysis;
import com.tangyi.mapper.AnswerPaperMapper;
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
public class AnswerPaperService {

    private static Logger logger = LoggerFactory.getLogger(AnswerPaperService.class);

    @Autowired
    AnswerPaperMapper answerPaperMapper;

    @Autowired
    private CacheService cacheService;

    public List<AnswerPaper> getAnswerPaperList() {
        return answerPaperMapper.findAll();
    }

    public List<AnswerPaper> getAnswerPaperListByAnswerUser(String username) {
        return answerPaperMapper.findByAnswerUser(username);
    }

    /**
     * 根据用户名和考试类型查找：正式或模拟
     * @param username
     * @param type
     * @return
     */
    public List<AnswerPaper> getAnswerPaperListByAnswerUserAndType(String username, String type) {
        return type.equals("official") ? answerPaperMapper.findByAnswerUserAndTypeOfficial(username) :
                answerPaperMapper.findByAnswerUserAndTypeSimulate(username);
    }

    /**
     * 根据用户获取正式考试
     * @param username
     * @return
     */
    public List<AnswerPaper> getOfficialAnswerPaperListByAnswerUser(String username) {
        return answerPaperMapper.findByAnswerUserAndTypeOfficial(username);
    }

    /**
     * 根据用户和答卷名称获取答卷
     * @param username
     * @param paperName
     * @return
     */
    public AnswerPaper findByAnswerUserAndPaperName(String username, String paperName) {
        return answerPaperMapper.findByAnswerUserAndPaperName(username, paperName);
    }

    /**
     * 根据用户获取模拟考试
     * @param username
     * @return
     */
    public List<AnswerPaper> getSimulateAnswerPaperListByAnswerUser(String username) {
        return answerPaperMapper.findByAnswerUserAndTypeSimulate(username);
    }

    public AnswerPaper getAnswerPaperById(String id) {
        //获取缓存数据
        AnswerPaper answerPaper = (AnswerPaper) cacheService.get(RedisKey.ANSWER_PAPER_ID + id);
        if(answerPaper == null) {
            answerPaper = answerPaperMapper.findByAnswerPaperId(id);
            cacheService.set(RedisKey.ANSWER_PAPER_ID + id, answerPaper);
        }
        return answerPaper;
    }

    public AnswerPaper getAnswerPaperByName(String name) {
        //获取缓存数据
        AnswerPaper answerPaper = (AnswerPaper) cacheService.get(RedisKey.ANSWER_PAPER_NAME + name);
        if(answerPaper == null) {
            answerPaper = answerPaperMapper.findByPaperName(name);
            cacheService.set(RedisKey.ANSWER_PAPER_NAME + name, answerPaper);
        }
        return answerPaper;
    }

    public AnswerPaper getAnswerPaperByNameAndUser(String name, String username) {
        return answerPaperMapper.findByPaperNameAndUser(name, username);
    }

    /**
     * 获取未批改或已批改的答卷数量，
     * @param check true:已批改，false：未批改
     * @return
     */
    public Integer countCheck(String check) {
        return answerPaperMapper.countCheck(check);
    }


    /**
     * 模糊查询
     * @param name
     * @return
     */
    public List<AnswerPaper> getAnswerPaperFuzzy(String name) {
        return answerPaperMapper.fuzzyFindByAnswerPaperName(name);
    }

    @Transactional(readOnly = false)
    public int saveAnswerPaper(AnswerPaper answerPaper) {
        return answerPaperMapper.insert(answerPaper);
    }

    @Transactional(readOnly = false)
    public void updatePaper(AnswerPaper answerPaper) {
        AnswerPaper answerPaper1 = answerPaperMapper.findByAnswerPaperId(answerPaper.getId());
        // TODO: 2017/3/29
        //修改数据

        if(answerPaper.getChecked() != null) {
            answerPaper1.setChecked(answerPaper.getChecked());
        }

        if(answerPaper.getScore() != null) {
            answerPaper1.setScore(answerPaper.getScore());
        }

        answerPaperMapper.update(answerPaper1);
        //删除缓存
        cacheService.delete(RedisKey.ANSWER_PAPER_ID + answerPaper1.getId());
    }

    @Transactional(readOnly = false)
    public void deletePaper(String id) {
        answerPaperMapper.delete(id);
        //删除缓存
        cacheService.delete(RedisKey.ANSWER_PAPER_ID + id);
    }

    /**
     * 分析答卷
     * @return
     */
    public List<PaperAnalysis> analysisPaper() {
        return answerPaperMapper.analysisPaper();
    }
}
