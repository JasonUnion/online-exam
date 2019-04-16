package com.tangyi.domain;

/**
 * Created by tangyi on 2017/1/8.
 */

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {

    private Long id;

    private String authority;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
