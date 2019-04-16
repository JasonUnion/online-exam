package com.tangyi.mapper;

import com.tangyi.domain.Paper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by tangyi on 2017/3/20.
 */
@Mapper
public interface PaperMapper {

    @Select("SELECT * FROM PAPER")
    List<Paper> findAll();

    @Select("SELECT * FROM PAPER WHERE PUBLISH='是' AND TYPE='official' ORDER BY START_TIME")
    List<Paper> findPublishedOfficial();

    @Select("SELECT * FROM PAPER WHERE PUBLISH='是' AND TYPE='simulate' ORDER BY START_TIME")
    List<Paper> findPublishedSimulate();

    @Select("SELECT * FROM PAPER WHERE TYPE='official' ORDER BY START_TIME")
    List<Paper> findOfficial();

    @Select("SELECT * FROM PAPER WHERE TYPE='simulate' ORDER BY START_TIME")
    List<Paper> findSimulate();

    @Select("SELECT * FROM PAPER WHERE TYPE='practice' ORDER BY START_TIME")
    List<Paper> findPractice();

    @Select("SELECT * FROM PAPER WHERE NAME = #{name}")
    Paper findByPaperName(@Param("name") String name);

    @Select("SELECT * FROM PAPER WHERE ID = #{id}")
    Paper findByPaperId(@Param("id") String id);

    //模糊查询
    @Select("SELECT * FROM PAPER WHERE NAME LIKE CONCAT('%', #{paperName}, '%')")
    List<Paper> fuzzyFindByPaperName(@Param("paperName") String paperName);

    @Insert("INSERT INTO PAPER(ID,NAME, CREATED, START_TIME, END_TIME, SUBJECT_NAME,PUBLISH,TYPE,REMARK,AVATAR,PEOPLES) VALUES(#{id},#{name},#{created},#{startTime},#{endTime},#{subjectName},#{publish},#{type},#{remark},#{avatar},#{peoples})")
    int insert(Paper paper);

    @Update("UPDATE PAPER SET NAME=#{name}, START_TIME=#{startTime},END_TIME=#{endTime},SUBJECT_NAME=#{subjectName},PUBLISH=#{publish},TYPE=#{type},REMARK=#{remark},AVATAR=#{avatar},PEOPLES=#{peoples} WHERE ID=#{id}")
    void update(Paper paper);

    @Delete("DELETE FROM SUBJECT WHERE ID = #{id}")
    void delete(@Param("id") String id);
}
