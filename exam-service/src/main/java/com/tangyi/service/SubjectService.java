package com.tangyi.service;

import com.tangyi.constant.RedisKey;
import com.tangyi.domain.Subject;
import com.tangyi.domain.SubjectPaper;
import com.tangyi.mapper.SubjectMapper;
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
public class SubjectService {

    private static Logger logger = LoggerFactory.getLogger(SubjectService.class);

    @Autowired
    private CacheService cacheService;

    @Autowired
    SubjectMapper subjectMapper;

    @Autowired
    SubjectPaperService subjectPaperService;

    public List<Subject> getSubjectList() {
        return subjectMapper.findAll();
    }

    /**
     * 根据名字模糊查询
     * @param name
     * @return
     */
    public List<Subject> getSubjectFuzzy(String name) {
        return subjectMapper.getSubjectFuzzy(name);
    }

    public Subject getSubjectById(String subjectId) {
        //获取缓存数据
        Subject subject = (Subject) cacheService.get(RedisKey.SUBJECT_ID + subjectId);

        if(subject == null) {
            subject = subjectMapper.findBySubjectId(subjectId);
            cacheService.set(RedisKey.SUBJECT_ID + subjectId, subject);
        }
        return subject;
    }

    /**
     * 根据科目名称获取科目对象
     * @param name
     * @return
     */
    public Subject getSubjectByName(String name) {

        //获取缓存数据
        Subject subject = (Subject) cacheService.get(RedisKey.SUBJECT_NAME + name);

        if(subject == null) {
            subject = new Subject();
            subject.setName(name);
            subject = subjectMapper.findBySubjectName(subject);
            //存入缓存
            cacheService.set(RedisKey.SUBJECT_NAME + name, subject);
        }
        return subject;
    }

    /**
     * 保存，保存前生成唯一uuid
     * @param subject
     * @return
     */
    @Transactional(readOnly = false)
    public Integer saveSubject(Subject subject) {
        subject.setId(IdGen.uuid());
        return subjectMapper.insert(subject);
    }

    @Transactional(readOnly = false)
    public void updateSubject(Subject subject) {
        //先查询，在内存修改数据，然后写入数据库
        Subject subject1 = subjectMapper.findBySubjectId(subject.getId());
        if(subject.getName() != null) {
            subject1.setName(subject.getName());
        }
        if(subject.getSort() != null) {
            subject1.setSort(subject.getSort());
        }
        subjectMapper.update(subject1);
        //删除缓存
        cacheService.delete(RedisKey.SUBJECT_ID + subject.getId());
    }

    @Transactional(readOnly = false)
    public void deleteSubject(String subjectId) throws RuntimeException {
        SubjectPaper subjectPaper = new SubjectPaper();
        subjectPaper.setSubjectId(subjectId);
        if(subjectPaperService.findBySubjectId(subjectPaper).size() > 0) {
            throw new RuntimeException("该课程包含有考试，不能删除");
        }
        subjectMapper.delete(subjectId);
        //删除缓存
        cacheService.del(RedisKey.SUBJECT_ID + subjectId);
    }
}
