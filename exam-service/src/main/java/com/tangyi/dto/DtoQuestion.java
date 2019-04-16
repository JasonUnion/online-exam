package com.tangyi.dto;

/**
 * 题目dto
 * Created by tangyi on 2017/3/28.
 */
public class DtoQuestion {

    private String id;

    /*题目题干*/
    private String title;

    /*简化的题目，用于方便显示*/
    private String simpleTitle;

    /*题目类型：1.选择，2.简答*/
    private String type;

    /*题目序号*/
    private String number;

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

    /*所属试卷*/
    private String paperName;

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

    public String getSimpleTitle() {
        return simpleTitle;
    }

    public void setSimpleTitle(String simpleTitle) {
        this.simpleTitle = simpleTitle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
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

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }
}
