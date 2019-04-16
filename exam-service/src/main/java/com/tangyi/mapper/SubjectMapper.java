package com.tangyi.mapper;

import com.tangyi.domain.Subject;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * 课程mapper
 * Created by tangyi on 2017/3/20.
 */
@Mapper
public interface SubjectMapper {

    @Insert("INSERT INTO SUBJECT(ID,NAME,SORT) VALUES(#{id},#{name},#{sort})")
    int insert(Subject subject);

    @Select("SELECT * FROM SUBJECT ORDER BY SORT DESC")
    List<Subject> findAll();

    @Select("SELECT * FROM SUBJECT WHERE ID = #{id}")
    Subject findBySubjectId(@Param("id") String id);

    @Select("SELECT * FROM SUBJECT WHERE NAME = #{name}")
    Subject findBySubjectName(Subject subject);

    @Select("SELECT * FROM SUBJECT WHERE NAME LIKE CONCAT('%', #{name}, '%')")
    List<Subject> getSubjectFuzzy(String name);

    @Update("UPDATE SUBJECT SET NAME=#{name},SORT=#{sort} WHERE ID=#{id}")
    void update(Subject subject);

    @Delete("DELETE FROM SUBJECT WHERE ID = #{id}")
    void delete(@Param("id") String id);

}
