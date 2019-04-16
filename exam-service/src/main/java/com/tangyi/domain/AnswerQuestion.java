package com.tangyi.domain;

/**
 * 答卷题目实体类
 * Created by tangyi on 2017/3/29.
 */
public class AnswerQuestion {

    private String id;

    /*题目题干*/
    private String title;

    /*题目类型：1.选择，2.简答*/
    private String type;

    /*题目序号*/
    private Integer number;

    /*选项A*/
    private String optionA;

    /*选项B*/
    private String optionB;

    /*选项C*/
    private String optionC;

    /*选项D*/
    private String optionD;

    /*简答题*/
    private String content;

    /*题目分值*/
    private String score;

    /*题目解析*/
    private String analysis;

    /*题目答案*/
    private String answer;

    /*批改分数*/
    private String markScore;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getMarkScore() {
        return markScore;
    }

    public void setMarkScore(String markScore) {
        this.markScore = markScore;
    }
}
