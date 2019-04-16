package com.tangyi.mapper;

import com.tangyi.domain.GradeClassNameStudent;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by tangyi on 2017/3/4.
 */
@Mapper
public interface GradeClassNameStudentMapper {

    @Insert("INSERT INTO GRADE_CLASSNAME_STUDENT(GRADE_ID,CLASSNAME_ID,STUDENT_ID) VALUES (#{gradeId},#{classnameId},#{studentId})")
    int insert(@Param("gradeId") String gradeId, @Param("classnameId") String classnameId, @Param("studentId") String studentId);

    @Select("SELECT * FROM GRADE_CLASSNAME_STUDENT WHERE GRADE_ID=#{gradeId}")
    List<GradeClassNameStudent> findByGradeId(@Param("gradeId") String gradeId);

    @Select("SELECT * FROM GRADE_CLASSNAME_STUDENT WHERE STUDENT_ID=#{studentId}")
    List<GradeClassNameStudent> findByStudentId(@Param("studentId") String studentId);

    @Delete("DELETE FROM GRADE_CLASSNAME_STUDENT WHERE STUDENT_ID=#{studentId}")
    int deleteByStudentId(@Param("studentId") String studentId);

    @Delete("DELETE FROM GRADE_CLASSNAME_STUDENT WHERE GRADE_ID=#{gradeId}")
    int deleteByGradeId(@Param("gradeId") String gradeId);
}
