package com.tangyi.mapper;

import com.tangyi.domain.PaperAnswerPaper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by tangyi on 2017-04-17.
 */
@Mapper
public interface PaperAnswerPaperMapper {

    @Select("SELECT * FROM PAPER_ANSWER_PAPER")
    List<PaperAnswerPaper> getList();

    @Select("SELECT * FROM PAPER_ANSWER_PAPER WHERE ANSWER_PAPER_ID=#{answerPaperId}")
    PaperAnswerPaper getByAnswerPaperId(String answerPaperId);

    @Select("SELECT * FROM PAPER_ANSWER_PAPER WHERE PAPER_ID=#{paperId}")
    List<PaperAnswerPaper> getByPaperId(String paperId);

    @Insert("INSERT INTO PAPER_ANSWER_PAPER(PAPER_ID, ANSWER_PAPER_ID) VALUES(#{paperId}, #{answerPaperId})")
    int insert(PaperAnswerPaper paperAnswerPaper);

    @Delete("DELETE FROM PAPER_ANSWER_PAPER WHERE ID=#{id}")
    void delete(PaperAnswerPaper paperAnswerPaper);
}
