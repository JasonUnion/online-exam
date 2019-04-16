package com.tangyi.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tangyi on 2017/3/4.
 */
public class DtoGrade implements Serializable{

    private static final long serialVersionUID = -1L;

    private String label;

    private List<DtoClassName> children;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<DtoClassName> getChildren() {
        return children;
    }

    public void setChildren(List<DtoClassName> children) {
        this.children = children;
    }
}
