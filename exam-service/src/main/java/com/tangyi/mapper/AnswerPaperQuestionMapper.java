package com.tangyi.mapper;

import com.tangyi.domain.AnswerPaper;
import com.tangyi.domain.AnswerPaperQuestion;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by tangyi on 2017/3/29.
 */
@Mapper
public interface AnswerPaperQuestionMapper {

    @Select("SELECT * FROM ANSWER_PAPER_QUESTION")
    List<AnswerPaperQuestion> findAll();

    /*@Select("SELECT * FROM ANSWER_PAPER WHERE PUBLISH='是' ORDER BY START_TIME DESC")
    List<AnswerPaper> findPublished();*/

    @Select("SELECT * FROM ANSWER_PAPER_QUESTION WHERE ANSWER_PAPER_ID = #{paperId}")
    List<AnswerPaperQuestion> findByPaperId(@Param("paperId") String paperId);

    @Select("SELECT * FROM ANSWER_PAPER_QUESTION WHERE ANSWER_QUESTION_ID = #{id}")
    List<AnswerPaperQuestion> findByQuestionId(@Param("id") String id);

    @Insert("INSERT INTO ANSWER_PAPER_QUESTION(ANSWER_PAPER_ID, ANSWER_QUESTION_ID) VALUES(#{answerPaperId},#{answerQuestionId})")
    int insert(AnswerPaperQuestion answerPaperQuestion);

    @Delete("DELETE FROM ANSWER_PAPER_QUESTION WHERE ID = #{id}")
    void delete(@Param("id") Long id);
}
