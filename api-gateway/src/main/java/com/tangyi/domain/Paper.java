package com.tangyi.domain;

import java.util.List;

/**
 * Created by tangyi on 2017/2/6.
 */

public class Paper {

    /*试卷id*/
    private String id;

    /*试卷名称*/
    private String name;

    /*创建时间*/
    private String created;

    /*开始时间*/
    private String startTime;

    /*结束时间*/
    private String endTime;

    /*课程名称*/
    private String subjectName;

    /*是否发布*/
    private String publish;

    /*题目*/
    private List<Question> questions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
