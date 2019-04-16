package com.tangyi.domain;

import java.io.Serializable;

/**
 * 课程实体类
 * Created by tangyi on 2017/3/16.
 */

public class Subject implements Serializable {

    private static final long serialVersionUID = -1L;

    private String id;

    private String name;

    private Integer sort;

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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
