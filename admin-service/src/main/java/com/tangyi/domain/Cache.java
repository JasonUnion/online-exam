package com.tangyi.domain;

import java.io.Serializable;

/**
 * Created by tangyi on 2017/3/15.
 */
public class Cache implements Serializable{

    private static final long serialVersionUID = -1L;

    private String id;

    private String name;

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
}
