package com.tangyi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Created by tangyi on 2017/3/15.
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired(required = false)
    private AuthorizationServerEndpointsConfiguration endpoints;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.httpBasic().disable();

        http.requestMatchers().antMatchers("/v1/**", "/swagger**", "/v2/**", "/druid/**")
                .and().authorizeRequests()
                .antMatchers("/v1/authenticated").authenticated()
                .antMatchers("/v1/users/register/**", "/v1/users/change-password/**", "/swagger**", "/v2/**", "/druid/**").permitAll()
                .antMatchers("/v1/activate").permitAll();

    }
}
