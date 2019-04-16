package com.tangyi.mapper;

import com.tangyi.domain.Question;
import com.tangyi.domain.WrongQuestion;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by tangyi on 2017-04-07.
 */
@Mapper
public interface WrongQuestionMapper {


    @Select("SELECT * FROM WRONG_QUESTION")
    List<WrongQuestion> findAll();

    @Insert("INSERT INTO WRONG_QUESTION(ID, TYPE, CONTENT, SCORE, ANALYSIS, ANSWER, OPTION_A, OPTION_B, OPTION_C, OPTION_D,TITLE,PAPER_NAME,NUMBER,SIMPLE_TITLE,USERNAME, RIGHT_ANSWER) VALUES(#{id},#{type},#{content},#{score},#{analysis},#{answer},#{optionA},#{optionB},#{optionC},#{optionD},#{title},#{paperName},#{number},#{simpleTitle},#{username},#{rightAnswer})")
    int insert(WrongQuestion wrongQuestion);

    @Select("SELECT * FROM WRONG_QUESTION WHERE ID = #{id}")
    WrongQuestion findByQuestionId(@Param("id") String id);

    @Select("SELECT * FROM WRONG_QUESTION WHERE USERNAME=#{username}")
    List<WrongQuestion> findByUsername(@Param("username") String username);

    @Select("SELECT COUNT(1) FROM WRONG_QUESTION WHERE USERNAME=#{username}")
    Integer countByUsername(@Param("username") String username);

    @Select("SELECT * FROM WRONG_QUESTION WHERE ID IN (SELECT QUESTION_ID FROM PAPER_QUESTION WHERE PAPER_ID=#{paperId}) AND NUMBER=#{number}")
    WrongQuestion findByPaperIdAndQuestionNumber(@Param("paperId") String paperId, @Param("number") String number);

    @Select("SELECT ID,TYPE,CONTENT,SCORE,OPTION_A,OPTION_B,OPTION_C,OPTION_D,TITLE,PAPER_NAME,NUMBER,SIMPLE_TITLE FROM QUESTION WHERE ID  IN (SELECT QUESTION_ID FROM PAPER_QUESTION WHERE PAPER_ID=#{paperId}) ORDER BY NUMBER")
    List<WrongQuestion> findByPaperIdIgnoreAnswer(@Param("paperId") String paperId);

    //模糊查询
    @Select("SELECT * FROM WRONG_QUESTION WHERE TITLE LIKE CONCAT('%', #{questionName}, '%')")
    List<WrongQuestion> fuzzyFindByQuestionName(@Param("questionName") String questionName);

    @Update("UPDATE WRONG_QUESTION SET CONTENT=#{content}, TYPE=#{type}, SCORE=#{score},ANALYSIS=#{analysis},ANSWER=#{answer},OPTION_A=#{optionA},OPTION_B=#{optionB},OPTION_C=#{optionC},OPTION_D=#{optionD},TITLE=#{title},NUMBER=#{number},SIMPLE_TITLE=#{simpleTitle},USERNAME=#{username} WHERE ID=#{id}")
    void update(WrongQuestion wrongQuestion);

    @Delete("DELETE FROM WRONG_QUESTION WHERE ID = #{id}")
    void delete(@Param("id") String id);
}
