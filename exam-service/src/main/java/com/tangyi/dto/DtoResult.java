package com.tangyi.dto;

import com.tangyi.domain.AnswerQuestion;
import com.tangyi.domain.Question;

import java.util.List;
import java.util.Map;

/**
 * 考试结果DTO类
 * Created by tangyi on 2017/4/6.
 */
public class DtoResult {

    /*分数*/
    private Integer score;

    /*正确题目数*/
    private Double right;

    /*错误题目数*/
    private Double wrong;

    /*正确率*/
    private Double correctRate;

    /*题目信息*/
    private List<DtoRightAndWrong> results;

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Double getRight() {
        return right;
    }

    public void setRight(Double right) {
        this.right = right;
    }

    public Double getWrong() {
        return wrong;
    }

    public void setWrong(Double wrong) {
        this.wrong = wrong;
    }

    public Double getCorrectRate() {
        return correctRate;
    }

    public void setCorrectRate(Double correctRate) {
        this.correctRate = correctRate;
    }

    public List<DtoRightAndWrong> getResults() {
        return results;
    }

    public void setResults(List<DtoRightAndWrong> results) {
        this.results = results;
    }
}
