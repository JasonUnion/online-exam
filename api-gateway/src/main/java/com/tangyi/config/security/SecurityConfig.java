package com.tangyi.config.security;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by tangyi on 2017/1/18.
 */
@Configuration
@EnableOAuth2Sso
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .logout().and()
                .authorizeRequests()
                .antMatchers("/", "/login","/auth/**",
                        "/bower_components/**","/components/**","/img/**","/lib/**","/app.*")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }
}
