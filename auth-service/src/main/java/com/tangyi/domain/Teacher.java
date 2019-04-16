package com.tangyi.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tangyi on 2017/2/26.
 */
public class Teacher implements Serializable {

    private static final long serialVersionUID = -1L;

    private String id;

    private String teacherName;

    private List<Grade> grades;

    private List<Student> students;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
