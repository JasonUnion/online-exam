package com.tangyi.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

/**
 * 安全工具类，提供获取当前用户信息的方法
 * Created by tangyi on 2017/3/29.
 */
public class SecurityUtils {

    public static  String getUserInfo() {
        OAuth2Authentication oAuth2Authentication =
                (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
        Authentication userAuthentication = oAuth2Authentication.getUserAuthentication();
        return userAuthentication.getName();
    }
}
