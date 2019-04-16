package com.tangyi.domain;

import java.util.List;

/**
 * 答卷实体类
 * Created by tangyi on 2017/3/29.
 */
public class AnswerPaper {
    /*答卷id*/
    private String id;

    /*答卷名称*/
    private String paperName;

    /*提交时间*/
    private String answerTime;

    /*答卷人*/
    private String answerUser;

    /*是否改完卷*/
    private String checked;

    /*是否答完卷*/
    private String finished;

    /*答卷成绩*/
    private String score;

    /*正式考试或模拟考试, official or simulate */
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public String getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(String answerTime) {
        this.answerTime = answerTime;
    }

    public String getAnswerUser() {
        return answerUser;
    }

    public void setAnswerUser(String answerUser) {
        this.answerUser = answerUser;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getFinished() {
        return finished;
    }

    public void setFinished(String finished) {
        this.finished = finished;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
