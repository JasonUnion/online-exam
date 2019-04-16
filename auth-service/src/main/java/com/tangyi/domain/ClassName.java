package com.tangyi.domain;

import java.io.Serializable;

/**
 * 班级名称
 * Created by tangyi on 2017/3/1.
 */
public class ClassName implements Serializable{

    private static final long serialVersionUID = -1L;

    private String id;

    private String className;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
