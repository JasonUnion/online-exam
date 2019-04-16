package com.tangyi.mapper;

import com.tangyi.domain.SubjectPaper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 科目和试卷对应关系
 * Created by tangyi on 2017/3/20.
 */
@Mapper
public interface SubjectPaperMapper {

    @Select("SELECT * FROM SUBJECT_PAPER WHERE SUBJECT_ID=#{subjectId}")
    List<SubjectPaper> findBySubjectId(SubjectPaper subjectPaper);

    @Select("SELECT * FROM SUBJECT_PAPER WHERE PAPER_ID=#{paperId}")
    SubjectPaper findByPaperId(SubjectPaper subjectPaper);

    @Insert("INSERT INTO SUBJECT_PAPER(SUBJECT_ID,PAPER_ID) VALUES(#{subjectId},#{paperId})")
    int insert(SubjectPaper subjectPaper);

    @Delete("DELETE FROM SUBJECT_PAPER WHERE PAPER_ID=#{paperId}")
    void deleteByPaperId(SubjectPaper subjectPaper);

    @Delete("DELETE FROM SUBJECT_PAPER WHERE SUBJECT_ID=#{subjectId}")
    void deleteBySubjectId(SubjectPaper subjectPaper);
}
