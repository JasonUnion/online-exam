package com.tangyi.mapper;

import com.tangyi.domain.Grade;
import com.tangyi.domain.Student;
import com.tangyi.domain.Teacher;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * 老师Maper
 * Created by tangyi on 2017/2/26.
 */
@Mapper
public interface TeacherMapper {

    List<Grade> findGradeByName(String name);

    List<Student> findStudentByName(String name);

    @Select("SELECT * FROM TEACHER WHERE TEACHER_NAME=#{name}")
    Teacher findByName(@Param("name") String name);

    @Select("SELECT * FROM TEACHER")
    List<Teacher> findAll();

    @Select("SELECT * FROM TEACHER WHERE ID=#{id}")
    Teacher findById(@Param("id") String id);

    @Select("SELECT * FROM TEACHER WHERE TEACHER_NAME LIKE CONCAT(#{name}, '%')")
    List<Teacher> getTeacherFuzzy(String name);

    @Insert("INSERT INTO TEACHER(ID, TEACHER_NAME) VALUES(#{id}, #{teacherName})")
    int insert(Teacher teacher);

    @Update("UPDATE TEACHER SET TEACHER_NAME=#{teacherName} WHERE ID=#{id}")
    void update(Teacher teacher);

    @Delete("DELETE FROM TEACHER WHERE ID=#{id}")
    void delete(@Param("id") String id);

}
