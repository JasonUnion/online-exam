package com.tangyi.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by tangyi on 2017/2/6.
 */

public class Paper implements Serializable{

    private static final long serialVersionUID = -1L;

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

    /*正式考试或模拟考试, official or simulate */
    private String type;

    /*备注*/
    private String remark;

    /*图标*/
    private String avatar;

    /*参与人数*/
    private String peoples;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPeoples() {
        return peoples;
    }

    public void setPeoples(String peoples) {
        this.peoples = peoples;
    }
}
