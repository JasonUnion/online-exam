package com.tangyi.service;

import com.tangyi.constant.RedisKey;
import com.tangyi.domain.Grade;
import com.tangyi.domain.Student;
import com.tangyi.domain.Teacher;
import com.tangyi.domain.UserAuthority;
import com.tangyi.mapper.TeacherMapper;
import com.tangyi.mapper.UserAuthorityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 老师服务层
 * Created by tangyi on 2017/2/26.
 */
@Service
@Transactional(readOnly = true)
public class TeacherService {

    private static Logger logger = LoggerFactory.getLogger(TeacherService.class);

    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    UserAuthorityMapper authorityMapper;

    @Autowired
    private CacheService cacheService;

    public List<Teacher> getTeacherList() {
        return teacherMapper.findAll();
    }

    /**
     * 根据id获取信息
     * @param id
     * @return
     */
    public Teacher getTeacherById(String id) {
        //获取缓存数据
        Teacher teacher = (Teacher) cacheService.get(RedisKey.TEACHER_ID + id);
        if(teacher == null) {
            teacher = teacherMapper.findById(id);
            cacheService.opsForValue().set(RedisKey.TEACHER_ID + id, teacher);
        }
        return teacher;
    }

    /**
     * 根据名字查找，精确匹配
     * @param name
     * @return
     */
    public Teacher getTeacherByName(String name) {
        //获取缓存数据
        Teacher teacher = (Teacher) cacheService.get(RedisKey.TEACHER_NAME + name);
        if(teacher == null) {
            teacher = teacherMapper.findByName(name);
            cacheService.set(RedisKey.TEACHER_NAME + name, teacher);
        }
        return teacher;
    }

    /**
     * 根据名字获取信息，模糊查询
     * @param name
     * @return
     */
    public List<Teacher> getTeacherFuzzy(String name) {
        return teacherMapper.getTeacherFuzzy(name);
    }

    public List<Grade> findGradeByName(String name) {
        return teacherMapper.findGradeByName(name);
    }

    public List<Student> findStudentByName(String name) {
        return teacherMapper.findStudentByName(name);
    }

    /**
     * 新增教师
     * @param teacher
     * @return
     */
    @Transactional(readOnly = false)
    public Integer saveTeacher(Teacher teacher) {
        /**
         * 1.保存老师信息
         * 2.分配权限：老师
         */
        UserAuthority authority = new UserAuthority();
        authority.setUserId(teacher.getId());
        authority.setAuthorityId("1");
        authorityMapper.insert(authority);
        return teacherMapper.insert(teacher);
    }

    /**
     * 更新教师信息
     * @param teacher
     */
    @Transactional(readOnly = false)
    public void updateTeacher(Teacher teacher) {
        Teacher teacher1 = teacherMapper.findById(teacher.getId());
        if(teacher.getTeacherName() != null) {
            teacher1.setTeacherName(teacher.getTeacherName());
        }
        teacherMapper.update(teacher1);
        //清理缓存
        cacheService.del(RedisKey.TEACHER_ID + teacher.getId());
    }

    @Transactional(readOnly = false)
    public void deleteTeacher(String id) {
        teacherMapper.delete(id);
        //清理缓存
        cacheService.del(RedisKey.TEACHER_ID + id);
    }
}

