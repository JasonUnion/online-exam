package com.tangyi.mapper;

import com.tangyi.domain.Authority;
import com.tangyi.domain.ClassName;
import com.tangyi.domain.Grade;
import com.tangyi.domain.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 班级Mapper
 * Created by tangyi on 2017/2/26.
 */
@Mapper
public interface GradeMapper {

    Student findStudentByName(String name);

    Grade findClassNameByName(String name);

    //List<Grade> findClassNameList();

    //@Select("SELECT * FROM GRADE")
    List<Grade> findAll();

    @Select("SELECT * FROM GRADE WHERE GRADE_NAME=#{name}")
    Grade findByName(@Param("name") String name);

    @Select("SELECT * FROM GRADE WHERE GRADE_NAME LIKE CONCAT(#{name}, '%')")
    List<Grade> getGradeFuzzy(@Param("name") String name);

    @Select("SELECT COUNT(*) FROM GRADE g INNER JOIN GRADE_CLASSNAME gc ON g.ID= gc.GRADE_ID " +
            "INNER JOIN CLASSNAME c on gc.CLASSNAME_ID=c.ID WHERE g.ID= #{id}")
    Integer countClassName(@Param("id") String id);

    @Insert("INSERT INTO GRADE(ID,GRADE_NAME,SORT) VALUES (#{id},#{gradeName},#{sort})")
    int insert(Grade grade);

    @Update("UPDATE GRADE SET GRADE_NAME=#{gradeName}, SORT=#{sort} WHERE ID=#{id}")
    void update(Grade grade);

    Grade findById(@Param("id") String id);

    @Delete("DELETE FROM GRADE WHERE ID=#{id}")
    void delete(@Param("id") String id);

}
