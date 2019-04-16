package com.tangyi.dto;

import com.tangyi.domain.AnswerQuestion;
import com.tangyi.domain.Question;

/**
 * Created by tangyi on 2017-04-07.
 */
public class DtoRightAndWrong {

    private Question question;

    private AnswerQuestion answerQuestion;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public AnswerQuestion getAnswerQuestion() {
        return answerQuestion;
    }

    public void setAnswerQuestion(AnswerQuestion answerQuestion) {
        this.answerQuestion = answerQuestion;
    }
}
