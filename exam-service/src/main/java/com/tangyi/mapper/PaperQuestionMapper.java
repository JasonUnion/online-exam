package com.tangyi.mapper;

import com.tangyi.domain.Paper;
import com.tangyi.domain.PaperQuestion;
import com.tangyi.domain.SubjectPaper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by tangyi on 2017/3/20.
 */
@Mapper
public interface PaperQuestionMapper {

    @Select("SELECT * FROM PAPER_QUESTION WHERE PAPER_ID=#{paperId}")
    PaperQuestion findByPaperId(PaperQuestion paperQuestion);

    @Select("SELECT * FROM PAPER_QUESTION WHERE QUESTION_ID=#{questionId}")
    PaperQuestion findByQuestionId(PaperQuestion paperQuestion);

    @Insert("INSERT INTO PAPER_QUESTION(PAPER_ID, QUESTION_ID) VALUES(#{paperId}, #{questionId})")
    int insert(PaperQuestion paperQuestion);

    @Delete("DELETE FROM PAPER_QUESTION WHERE PAPER_ID=#{paperId}")
    void deleteByPaperId(PaperQuestion paperQuestion);

    @Delete("DELETE FROM PAPER_QUESTION WHERE QUESTION_ID=#{questionId}")
    void deleteByQuestionId(PaperQuestion paperQuestion);
}
