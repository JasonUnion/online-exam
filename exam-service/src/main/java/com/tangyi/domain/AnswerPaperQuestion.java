package com.tangyi.domain;

/**
 * 答卷和答卷题目对应关系实体类
 * Created by tangyi on 2017/3/29.
 */
public class AnswerPaperQuestion {
    private Long id;

    private String answerPaperId;

    private String answerQuestionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswerPaperId() {
        return answerPaperId;
    }

    public void setAnswerPaperId(String answerPaperId) {
        this.answerPaperId = answerPaperId;
    }

    public String getAnswerQuestionId() {
        return answerQuestionId;
    }

    public void setAnswerQuestionId(String answerQuestionId) {
        this.answerQuestionId = answerQuestionId;
    }
}
