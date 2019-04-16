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
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.logout().invalidateHttpSession(true).logoutUrl("/logout").logoutSuccessUrl("/")
                .and().antMatcher("/**").authorizeRequests()
                .antMatchers("/login","/auth/**","/assets/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .rememberMe()
                .tokenValiditySeconds(31536000).and().csrf().disable();
    }
}
