package com.tangyi.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by tangyi on 2017/3/25.
 */
@Mapper
public interface TeacherStudentMapper {

    @Insert("INSERT INTO TEACHER_STUDENT(TEACHER_ID,STUDENT_ID) VALUES (#{teacherId},#{studentId})")
    int insert(@Param("teacherId") String teacherId, @Param("studentId") String studentId);

    @Delete("DELETE FROM TEACHER_STUDENT WHERE STUDENT_ID=#{studentId}")
    int deleteByStudentId(@Param("studentId") String studentId);
}
