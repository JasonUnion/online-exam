package com.tangyi.domain;

import java.io.Serializable;

/**
 * 联系人
 * Created by tangyi on 2017-04-16.
 */
public class Contact implements Serializable {

    private static final long serialVersionUID = -1L;

    private Long id;

    private String username;

    private String email;

    private String remark;

    private String created;

    /*查看状态*/
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
