package com.tangyi.mapper;

import com.tangyi.domain.AnswerPaper;
import com.tangyi.domain.AnswerQuestion;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by tangyi on 2017/3/29.
 */
@Mapper
public interface AnswerQuestionMapper {

    @Select("SELECT * FROM ANSWER_QUESTION")
    List<AnswerQuestion> findAll();

    /*@Select("SELECT * FROM ANSWER_PAPER WHERE PUBLISH='是' ORDER BY START_TIME DESC")
    List<AnswerPaper> findPublished();*/

    @Select("SELECT * FROM ANSWER_QUESTION WHERE TITLE = #{title}")
    AnswerQuestion findByAnswerQuestionTitle(@Param("title") String title);

    @Select("SELECT * FROM ANSWER_QUESTION WHERE ID = #{id}")
    AnswerQuestion findByAnswerQuestionId(@Param("id") String id);

    @Select("SELECT * FROM ANSWER_QUESTION WHERE ID IN (SELECT ANSWER_QUESTION_ID FROM ANSWER_PAPER_QUESTION WHERE ANSWER_PAPER_ID=#{id})")
    List<AnswerQuestion> findByAnswerPaperId(@Param("id") String id);

    @Select("SELECT  * FROM ANSWER_QUESTION WHERE ID IN (SELECT ANSWER_QUESTION_ID FROM ANSWER_PAPER_QUESTION " +
            "WHERE ANSWER_PAPER_ID=#{paperId}) AND NUMBER=#{number}")
    AnswerQuestion findByPaperIdAndQuestionNumber(@Param("paperId") String paperId, @Param("number") Integer number);

    //模糊查询
    @Select("SELECT * FROM ANSWER_QUESTION WHERE TITLE LIKE CONCAT('%', #{title}, '%')")
    List<AnswerQuestion> fuzzyFindByAnswerQuestionTitle(@Param("title") String title);

    @Insert("INSERT INTO ANSWER_QUESTION(ID, TITLE, TYPE, OPTION_A, OPTION_B, OPTION_C, OPTION_D,CONTENT, SCORE,ANSWER,ANALYSIS,NUMBER,MARK_SCORE) " +
            "VALUES(#{id},#{title},#{type},#{optionA},#{optionB},#{optionC},#{optionD},#{content},#{score},#{answer},#{analysis},#{number},#{markScore})")
    int insert(AnswerQuestion answerQuestion);

    @Update("UPDATE ANSWER_QUESTION SET TITLE=#{title}, TYPE=#{type},OPTION_A=#{optionA},OPTION_B=#{optionB},OPTION_C=#{optionC},OPTION_D=#{optionD},content=#{content} " +
            ",SCORE=#{score},ANSWER=#{answer},ANALYSIS=#{analysis},NUMBER=#{number},MARK_SCORE=#{markScore} WHERE ID=#{id}")
    void update(AnswerQuestion answerQuestion);

    @Delete("DELETE FROM ANSWER_QUESTION WHERE ID = #{id}")
    void delete(@Param("id") String id);
}
