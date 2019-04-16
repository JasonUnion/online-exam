package com.tangyi.domain;

/**
 * 试卷答卷关系实体
 * Created by tangyi on 2017-04-17.
 */
public class PaperAnswerPaper {

    private Long id;

    private String paperId;

    private String answerPaperId;

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

    public String getAnswerPaperId() {
        return answerPaperId;
    }

    public void setAnswerPaperId(String answerPaperId) {
        this.answerPaperId = answerPaperId;
    }
}
