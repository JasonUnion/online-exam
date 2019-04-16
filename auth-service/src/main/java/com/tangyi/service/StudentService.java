package com.tangyi.service;

import com.tangyi.constant.RedisKey;
import com.tangyi.domain.ClassName;
import com.tangyi.domain.Grade;
import com.tangyi.domain.MapperUser;
import com.tangyi.domain.Student;
import com.tangyi.dto.DtoStudent;
import com.tangyi.mapper.GradeClassNameStudentMapper;
import com.tangyi.mapper.StudentClassNameMapper;
import com.tangyi.mapper.StudentMapper;
import com.tangyi.mapper.TeacherStudentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tangyi on 2017/2/26.
 */

@Service
@Transactional(readOnly = true)
public class StudentService {

    private static Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    GradeService gradeService;

    @Autowired
    GradeClassNameStudentMapper gradeStudentMapper;

    @Autowired
    private CacheService cacheService;

    @Autowired
    TeacherStudentMapper teacherStudentMapper;

    @Autowired
    StudentClassNameMapper studentClassNameMapper;

    @Autowired
    GradeClassNameStudentMapper gradeClassNameStudentMapper;

    @Autowired
    ClassNameService classNameService;

    public List<Student> getStudentList() {
        return studentMapper.findAll();
    }

    /**
     * 根据老师名字获取学生列表
     * @param name
     * @return
     */
    public List<Student> findStudentListByTeacher(String name) {
        return studentMapper.findStudentListByTeacher(name);
    }

    public Student getStudent(String id) {
        //获取缓存数据
        Student student = (Student) cacheService.get(RedisKey.STUDENT_ID + id);
        if(student == null) {
            student = studentMapper.findById(id);
            cacheService.set(RedisKey.STUDENT_ID + id, student);
        }
        return student;
    }

    /**
     * 获取学生信息和班级信息
     * @param id
     * @return
     */
    public Student getStudentAndGrade(String id) {
        //获取缓存数据
        Student student = (Student) cacheService.get(RedisKey.STUDENT_ID + id);
        if(student == null) {
            student = studentMapper.findStudentAndGradeById(id);
            cacheService.set(RedisKey.STUDENT_ID + id, student);
        }
        return student;
    }

    /**
     * 模糊查询
     * @param studentName
     * @return
     */
    public List<Student> getStudentFuzzy(String studentName, String teacherName) {
        return studentMapper.getStudentFuzzy(studentName, teacherName);
    }

    /**
     * 保存学生信息
     * @param dtoStudent
     * @param user
     * @return
     */
    @Transactional(readOnly = false)
    public Integer saveStudent(DtoStudent dtoStudent, MapperUser user, String teacherId) throws Exception {
        Student student = new Student();
        student.setId(user.getId());
        student.setStudentName(dtoStudent.getStudentName());
        student.setExamNumber(dtoStudent.getExamNumber());
        student.setClassName(dtoStudent.getClassName());

        student.setBirthday(user.getBirthday());
        student.setEmail(user.getEmail());
        student.setGender(user.getGender());

        studentMapper.insert(student);

        //保存老师学生信息
        teacherStudentMapper.insert(teacherId, student.getId());

        //TODO
        //先根据grade取出gradeId,再保存进grade_student表
        //调用服务层会先从缓存里读取数据，减轻数据库压力
        Grade grade = gradeService.getGradeByName(dtoStudent.getGrade());
        if(grade != null) {

            //调用服务层会先从缓存里读取数据，减轻数据库压力
            ClassName className = classNameService.getByName(dtoStudent.getClassName());

            if(className != null) {
                //保存学生班级信息
                studentClassNameMapper.insert(student.getId(), className.getId());
                gradeStudentMapper.insert(grade.getId(), className.getId(), student.getId());
            }else {
                logger.error("保存学生班级信息失败！");

                /*spring 声明式事务默认对RuntimeException回滚， 抛出Exception 不回滚*/
                throw new RuntimeException();
            }
        }else {
            logger.error("年级不存在！");
            throw new RuntimeException();
        }
        //保存学生年级信息
        return 1;
    }

    /**
     * 更新学生信息
     * @param student
     */
    @Transactional(readOnly = false)
    public void updateStudent(Student student) {
        Student student1 = studentMapper.findById(student.getId());
        if(student.getExamNumber() != null) {
            student1.setExamNumber(student.getExamNumber());
        }
        if(student.getStudentName() != null) {
            student1.setStudentName(student.getStudentName());
        }
        if(student.getBirthday() != null) {
            student1.setBirthday(student.getBirthday());
        }
        if(student.getEmail() != null) {
            student1.setEmail(student.getEmail());
        }
        if(student.getGender() != null) {
            student1.setGender(student.getGender());
        }
        if(student.getClassName() != null) {
            student1.setClassName(student.getClassName());
        }
        studentMapper.update(student1);
        //删除缓存数据
        cacheService.del(RedisKey.STUDENT_ID + student.getId());
    }

    @Transactional(readOnly = false)
    public void deleteStudent(String id) {
        studentMapper.delete(id);

        teacherStudentMapper.deleteByStudentId(id);

        studentClassNameMapper.deleteByStudentId(id);

        gradeClassNameStudentMapper.deleteByStudentId(id);
        //删除缓存数据
        cacheService.del(RedisKey.STUDENT_ID + id);
    }
}
