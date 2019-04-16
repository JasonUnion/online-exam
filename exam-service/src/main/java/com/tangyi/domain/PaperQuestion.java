package com.tangyi.domain;

/**
 * Created by tangyi on 2017/3/20.
 */
public class PaperQuestion {
    private Long id;

    private String paperId;

    private String questionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
}
