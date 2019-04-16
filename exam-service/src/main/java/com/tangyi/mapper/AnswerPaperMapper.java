package com.tangyi.mapper;

import com.tangyi.domain.AnswerPaper;
import com.tangyi.domain.PaperAnalysis;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by tangyi on 2017/3/29.
 */
@Mapper
public interface AnswerPaperMapper {
    @Select("SELECT * FROM ANSWER_PAPER")
    List<AnswerPaper> findAll();

    @Select("SELECT * FROM ANSWER_PAPER WHERE ANSWER_USER = #{username}")
    List<AnswerPaper> findByAnswerUser(String username);

    @Select("SELECT * FROM ANSWER_PAPER WHERE ANSWER_USER = #{username} AND TYPE='official'")
    List<AnswerPaper> findByAnswerUserAndTypeOfficial(String username);

    @Select("SELECT * FROM ANSWER_PAPER WHERE ANSWER_USER = #{username} AND TYPE='simulate'")
    List<AnswerPaper> findByAnswerUserAndTypeSimulate(String username);

    @Select("SELECT * FROM ANSWER_PAPER WHERE ANSWER_USER = #{username} AND PAPER_NAME=#{paperName}")
    AnswerPaper findByAnswerUserAndPaperName(@Param("username") String username, @Param("paperName") String paperName);

    /*@Select("SELECT * FROM ANSWER_PAPER WHERE PUBLISH='是' ORDER BY START_TIME DESC")
    List<AnswerPaper> findPublished();*/

    @Select("SELECT * FROM ANSWER_PAPER WHERE PAPER_NAME = #{name}")
    AnswerPaper findByPaperName(@Param("name") String name);

    @Select("SELECT * FROM ANSWER_PAPER WHERE PAPER_NAME = #{name} AND ANSWER_USER=#{username}")
    AnswerPaper findByPaperNameAndUser(@Param("name") String name, @Param("username") String username);

    @Select("SELECT * FROM ANSWER_PAPER WHERE ID = #{id}")
    AnswerPaper findByAnswerPaperId(@Param("id") String id);

    @Select("SELECT COUNT(1) FROM ANSWER_PAPER WHERE CHECKED = #{check}")
    Integer countCheck(@Param("check") String check);

    //模糊查询
    @Select("SELECT * FROM ANSWER_PAPER WHERE PAPER_NAME LIKE CONCAT('%', #{paperName}, '%')")
    List<AnswerPaper> fuzzyFindByAnswerPaperName(@Param("paperName") String paperName);

    @Insert("INSERT INTO ANSWER_PAPER(ID,PAPER_NAME, ANSWER_TIME, ANSWER_USER, CHECKED, FINISHED, SCORE, TYPE) VALUES(#{id},#{paperName},#{answerTime},#{answerUser},#{checked},#{finished},#{score},#{type})")
    int insert(AnswerPaper answerPaper);

    @Update("UPDATE ANSWER_PAPER SET PAPER_NAME=#{paperName}, ANSWER_TIME=#{answerTime},ANSWER_USER=#{answerUser},CHECKED=#{checked},FINISHED=#{finished},SCORE=#{score},TYPE=#{type} WHERE ID=#{id}")
    void update(AnswerPaper answerPaper);

    @Delete("DELETE FROM ANSWER_PAPER WHERE ID = #{id}")
    void delete(@Param("id") String id);


    @Select("SELECT PAPER_NAME, MAX(SCORE) AS MAX_SCORE, MIN(SCORE) AS MIN_SCORE, SUM(SCORE)/COUNT(1) AS " +
            "AVERAGE_SCORE FROM ANSWER_PAPER WHERE CHECKED='true' AND FINISHED='true' GROUP BY PAPER_NAME;")
    List<PaperAnalysis> analysisPaper();


}
