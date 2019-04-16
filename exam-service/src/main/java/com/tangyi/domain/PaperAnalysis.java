package com.tangyi.domain;

/**
 * 答卷分析实体
 * Created by tangyi on 2017-04-09.
 */
public class PaperAnalysis {

    /*答卷名称*/
    private String paperName;

    /*平均分*/
    private double averageScore;

    /*最高分*/
    private double maxScore;

    /*最低分*/
    private double minScore;

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(double maxScore) {
        this.maxScore = maxScore;
    }

    public double getMinScore() {
        return minScore;
    }

    public void setMinScore(double minScore) {
        this.minScore = minScore;
    }
}
