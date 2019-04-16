package com.tangyi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * MVC 配置
 * Created by tangyi on 2017/3/18.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/change-password").setViewName("changePassword");
/*        registry.addViewController("/portal-login").setViewName("portal-login");
        registry.addViewController("/portal-register").setViewName("portal-register");*/
    }
}
