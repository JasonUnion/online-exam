package com.tangyi.mapper;

import com.tangyi.domain.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tangyi on 2017/3/20.
 */
@Mapper
public interface QuestionMapper {

    @Select("SELECT * FROM QUESTION")
    List<Question> findAll();

    @Insert("INSERT INTO QUESTION(ID, TYPE, CONTENT, SCORE, ANALYSIS, ANSWER, OPTION_A, OPTION_B, OPTION_C, OPTION_D,TITLE,PAPER_NAME,NUMBER,SIMPLE_TITLE) VALUES(#{id},#{type},#{content},#{score},#{analysis},#{answer},#{optionA},#{optionB},#{optionC},#{optionD},#{title},#{paperName},#{number},#{simpleTitle})")
    int insert(Question question);

    @Select("SELECT * FROM QUESTION WHERE ID = #{id}")
    Question findByQuestionId(@Param("id") String id);

    @Select("SELECT * FROM QUESTION WHERE ID  IN (SELECT QUESTION_ID FROM PAPER_QUESTION WHERE PAPER_ID=#{paperId}) ORDER BY NUMBER")
    List<Question> findByPaperId(@Param("paperId") String paperId);

    @Select("SELECT COUNT(1) FROM QUESTION WHERE ID  IN (SELECT QUESTION_ID FROM PAPER_QUESTION WHERE PAPER_ID=#{paperId})")
    Integer countByPaperId(@Param("paperId") String paperId);

    @Select("SELECT * FROM QUESTION WHERE ID IN (SELECT QUESTION_ID FROM PAPER_QUESTION WHERE PAPER_ID=#{paperId}) AND NUMBER=#{number}")
    Question findByPaperIdAndQuestionNumber(@Param("paperId") String paperId, @Param("number") Integer number);

    @Select("SELECT ID,TYPE,CONTENT,SCORE,OPTION_A,OPTION_B,OPTION_C,OPTION_D,TITLE,PAPER_NAME,NUMBER,SIMPLE_TITLE FROM QUESTION WHERE ID  IN (SELECT QUESTION_ID FROM PAPER_QUESTION WHERE PAPER_ID=#{paperId}) ORDER BY NUMBER")
    List<Question> findByPaperIdIgnoreAnswer(@Param("paperId") String paperId);

    //模糊查询
    @Select("SELECT * FROM QUESTION WHERE TITLE LIKE CONCAT('%', #{questionName}, '%')")
    List<Question> fuzzyFindByQuestionName(@Param("questionName") String questionName);

    @Update("UPDATE QUESTION SET CONTENT=#{content}, TYPE=#{type}, SCORE=#{score},ANALYSIS=#{analysis},ANSWER=#{answer},OPTION_A=#{optionA},OPTION_B=#{optionB},OPTION_C=#{optionC},OPTION_D=#{optionD},TITLE=#{title},NUMBER=#{number},SIMPLE_TITLE=#{simpleTitle} WHERE ID=#{id}")
    void update(Question question);

    @Delete("DELETE FROM QUESTION WHERE ID = #{id}")
    void delete(@Param("id") String id);
}
