package com.tangyi.domain;

import java.io.Serializable;

/**
 * Created by tangyi on 2017/3/2.
 */

public class Student implements Serializable {

    private static final long serialVersionUID = -1L;

    private String id;

    //考号
    private String examNumber;

    //名字
    private String studentName;

    //生日
    private String birthday;

    //邮箱
    private String email;

    //性别
    private String gender;

    //班级名称
    private String className;

    //年级名称
    private String gradeName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExamNumber() {
        return examNumber;
    }

    public void setExamNumber(String examNumber) {
        this.examNumber = examNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }
}
