package com.tangyi.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 年级
 * Created by tangyi on 2017/3/6.
 */

public class Grade implements Serializable {

    private static final long serialVersionUID = -1L;

    private String id;

    private String gradeName;

    private List<Student> students;

    private List<Teacher> teachers;

    private List<ClassName> classNames;

    /*排序*/
    private Integer sort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public List<ClassName> getClassNames() {
        return classNames;
    }

    public void setClassNames(List<ClassName> classNames) {
        this.classNames = classNames;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
