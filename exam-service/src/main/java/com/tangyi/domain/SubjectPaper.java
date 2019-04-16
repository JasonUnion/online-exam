package com.tangyi.domain;

/**
 * Created by tangyi on 2017/3/20.
 */
public class SubjectPaper {
    private Long id;

    private String subjectId;

    private String paperId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }
}
