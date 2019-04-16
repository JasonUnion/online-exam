package com.tangyi.domain;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * Created by tangyi on 2017/2/15.
 */

public class UserAuthority implements Serializable {

    private static final long serialVersionUID = -1L;

    private Long id;

    private String userId;

    private String authorityId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(String authorityId) {
        this.authorityId = authorityId;
    }
}
