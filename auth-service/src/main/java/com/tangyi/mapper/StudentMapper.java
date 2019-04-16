package com.tangyi.mapper;

import com.tangyi.domain.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by tangyi on 2017/2/26.
 */
@Mapper
public interface StudentMapper {

    @Select("SELECT * FROM STUDENT WHERE STUDENT_NAME=#{name}")
    Student findByName(@Param("name") String name);

    @Select("SELECT * FROM STUDENT")
    List<Student> findAll();

    /**
     * 调整sql
     * @param name
     * @return
     */
    // TODO: 2017-04-09
    /*@Select("SELECT * FROM STUDENT WHERE ID IN( SELECT STUDENT_ID FROM TEACHER_STUDENT WHERE " +
            "TEACHER_ID=(SELECT ID FROM TEACHER WHERE TEACHER_NAME=#{name}))")*/
    /*@Select("SELECT s.id,s.exam_number,s.student_name,s.gender,s.email,g.grade_name,c.class_name FROM STUDENT s " +
            "LEFT JOIN GRADE_CLASSNAME_STUDENT gs on s.id = gs.student_id LEFT JOIN GRADE g on gs.grade_id = g.id " +
            "LEFT JOIN STUDENT_CLASSNAME sc ON s.id = sc.student_id " +
            "LEFT JOIN CLASSNAME c on sc.classname_id = c.id WHERE s.ID IN " +
            "( SELECT STUDENT_ID FROM TEACHER_STUDENT WHERE " +
            "TEACHER_ID=(SELECT ID FROM TEACHER WHERE TEACHER_NAME=#{name})) GROUP BY s.student_name")*/
    @Select("SELECT s.id,s.exam_number,s.student_name,s.gender,s.email,g.grade_name,c.class_name FROM STUDENT s " +
            "LEFT JOIN GRADE_CLASSNAME_STUDENT gs on s.id = gs.student_id LEFT JOIN GRADE g on " +
            "gs.grade_id = g.id LEFT JOIN CLASSNAME c on gs.classname_id = c.id WHERE s.ID IN " +
            "(SELECT STUDENT_ID FROM TEACHER_STUDENT WHERE TEACHER_ID=(SELECT ID FROM TEACHER WHERE " +
            "TEACHER_NAME=#{name})) GROUP BY s.student_name")
    List<Student> findStudentListByTeacher(String name);

    @Select("SELECT * FROM STUDENT WHERE ID=#{id}")
    Student findById(@Param("id") String id);

    @Select("SELECT s.id,s.exam_number,s.student_name,s.gender,s.email,g.grade_name,c.class_name FROM STUDENT s " +
            "LEFT JOIN GRADE_CLASSNAME_STUDENT gs on s.id = gs.student_id LEFT JOIN GRADE g on " +
            "gs.grade_id = g.id LEFT JOIN CLASSNAME c on gs.classname_id = c.id WHERE s.ID=#{id}")
    Student findStudentAndGradeById(@Param("id") String id);

    //模糊查询
    @Select("SELECT * FROM STUDENT WHERE ID IN( SELECT STUDENT_ID FROM TEACHER_STUDENT WHERE " +
            "TEACHER_ID=(SELECT ID FROM TEACHER WHERE TEACHER_NAME=#{teacherName})) AND STUDENT_NAME LIKE CONCAT('%', #{studentName}, '%')")
    List<Student> getStudentFuzzy(@Param("studentName") String studentName, @Param("teacherName") String teacherName);

    @Insert("INSERT INTO STUDENT(ID,EXAM_NUMBER,STUDENT_NAME,BIRTHDAY,EMAIL,GENDER,CLASS_NAME) VALUES " +
            "(#{id},#{examNumber},#{studentName},#{birthday},#{email},#{gender},#{className})")
    int insert(Student student);

    @Update("UPDATE STUDENT SET EXAM_NUMBER=#{examNumber},STUDENT_NAME=#{studentName},BIRTHDAY=#{birthday}," +
            "EMAIL=#{email},GENDER=#{gender},CLASS_NAME=#{className} WHERE ID=#{id}")
    void update(Student student);

    @Delete("DELETE FROM STUDENT WHERE ID=#{id}")
    void delete(@Param("id") String id);

}
