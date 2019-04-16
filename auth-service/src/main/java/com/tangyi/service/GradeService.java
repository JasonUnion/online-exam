package com.tangyi.service;

import com.tangyi.constant.RedisKey;
import com.tangyi.domain.ClassName;
import com.tangyi.domain.Grade;
import com.tangyi.domain.GradeClassName;
import com.tangyi.domain.Student;
import com.tangyi.dto.DtoClassName;
import com.tangyi.dto.DtoGrade;
import com.tangyi.mapper.ClassNameMapper;
import com.tangyi.mapper.GradeClassNameMapper;
import com.tangyi.mapper.GradeClassNameStudentMapper;
import com.tangyi.mapper.GradeMapper;
import com.tangyi.utils.IdGen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangyi on 2017/2/26.
 */
@Service
@Transactional(readOnly = true)
public class GradeService {

    private static Logger logger = LoggerFactory.getLogger(GradeService.class);

    @Autowired
    GradeMapper gradeMapper;

    @Autowired
    ClassNameMapper classNameMapper;

    @Autowired
    GradeClassNameMapper gradeClassNameMapper;

    @Autowired
    GradeClassNameStudentMapper gradeClassNameStudentMapper;

    @Autowired
    private CacheService cacheService;

    public List<Grade> getGradeList() {
        return gradeMapper.findAll();
    }

    /*构造班级树列表*/
    public List<DtoGrade> getGradeTreeList() {

        List<Grade> grades = gradeMapper.findAll();

        List<DtoGrade> dtoGrades = new ArrayList<DtoGrade>();
        List<DtoClassName> dtoClassNames = null;
        DtoGrade dtoGrade = null;
        DtoClassName dtoClassName = null;
        for (Grade grade : grades) {
            dtoGrade = new DtoGrade();
            dtoGrade.setLabel(grade.getGradeName());
            dtoClassNames = new ArrayList<DtoClassName>();
            for (ClassName className : grade.getClassNames()) {
                dtoClassName = new DtoClassName();
                if(className.getClassName() == null || className.getClassName() == "") {
                    dtoClassNames = null;
                    break;
                }
                dtoClassName.setLabel(className.getClassName());
                dtoClassNames.add(dtoClassName);
            }
            dtoGrade.setChildren(dtoClassNames);


            dtoGrades.add(dtoGrade);

        }

        return dtoGrades;
    }

    public Student findStudentByName(String name) {
        return gradeMapper.findStudentByName(name);
    }

    public Grade findClassNameByName(String name) {
        return gradeMapper.findClassNameByName(name);
    }

    /**
     * 根据名字模糊查询
     * @param name
     * @return
     */
    public List<Grade> getGradeFuzzy(String name) {
        return gradeMapper.getGradeFuzzy(name);
    }

    /**
     * 根据id获取班级
     * @param id
     * @return
     */
    public Grade getGradeById(String id) {
        //获取缓存数据
        Grade grade = (Grade) cacheService.get(RedisKey.GRADE_ID + id);
        if(grade == null) {
            grade = gradeMapper.findById(id);
            cacheService.set(RedisKey.GRADE_ID + id, grade);
        }
        return grade;
    }

    /**
     * 根据名称获取班级
     * @param name
     * @return
     */
    public Grade getGradeByName(String name) {
        //获取缓存数据
        Grade grade = (Grade) cacheService.get(RedisKey.GRADE_NAME + name);
        if(grade == null) {
            grade = gradeMapper.findByName(name);
            cacheService.set(RedisKey.GRADE_NAME + name, grade);
        }
        return grade;
    }

    /**
     * 统计某个年级的班级数
     * @param id
     * @return
     */
    public Integer countClassName(String id) {
        return gradeMapper.countClassName(id);
    }

    @Transactional(readOnly = false)
    public Integer saveGrade(Grade grade) {
        grade.setId(IdGen.uuid());
        return gradeMapper.insert(grade);
    }

    /**
     * 更新班级信息
     * @param grade
     */
    @Transactional(readOnly = false)
    public void updateGrade(Grade grade) {
        Integer count = gradeMapper.countClassName(grade.getId());
        List<ClassName> classNamesInDb = classNameMapper.findAll();
        List<ClassName> classNames = grade.getClassNames();
        ClassName newClassName = null;
        if(classNames.size() > count) {
            //表示有新增班级

            //获取新增的班级名称
            for(int i = count; i < classNames.size(); i++) {
                boolean isExist = false;
                ClassName className = null;
                for(int j = 0; j < classNamesInDb.size(); j++) {
                    if(classNames.get(i).getClassName() != null && classNames.get(i).getClassName().equals(classNamesInDb.get(j).getClassName())) {
                        isExist = true;
                        className = classNamesInDb.get(j);//当前班级名称
                        break;
                    }
                }

                String classId = null;
                /**
                 * 1.数据库没有这个班级名称
                 * 2.班级名称不为空
                 */
                if(!isExist) {
                    if(classNames.get(i).getClassName() != null && !classNames.get(i).getClassName().equals("")) {
                        newClassName = classNames.get(i);
                        classId = IdGen.uuid();
                        newClassName.setId(classId);
                        classNameMapper.insert(newClassName);
                        GradeClassName gradeClassName = new GradeClassName();
                        gradeClassName.setGradeId(grade.getId());
                        gradeClassName.setClassnameId(classId);
                        gradeClassNameMapper.insert(gradeClassName);
                    }
                }else {
                    //数据库有这个班级名称
                    GradeClassName gradeClassName = new GradeClassName();
                    gradeClassName.setGradeId(grade.getId());
                    if(className != null) {
                        gradeClassName.setClassnameId(className.getId());
                    }
                    gradeClassNameMapper.insert(gradeClassName);
                }


            }

        }
        //更新数据
        Grade grade1 = gradeMapper.findById(grade.getId());
        if(grade.getGradeName() != null) {
            grade1.setGradeName(grade.getGradeName());
        }
        if(grade.getSort() != null) {
            grade1.setSort(grade.getSort());
        }
        gradeMapper.update(grade1);
        //清理缓存
        cacheService.del(RedisKey.GRADE_ID + grade.getId());
    }

    @Transactional(readOnly = false)
    public void deleteGrade(String id) throws RuntimeException {
        if(gradeClassNameStudentMapper.findByGradeId(id).size() > 0) {
            throw new RuntimeException("班级还有学生，不能删除");
        }
        gradeMapper.delete(id);
        gradeClassNameMapper.delete(id);
        //清理缓存
        cacheService.del(RedisKey.GRADE_ID + id);
    }
}

