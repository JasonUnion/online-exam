package com.tangyi.config;

import com.tangyi.web.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.csrf.MissingCsrfTokenException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Spring security 配置
 * Created by tangyi on 2017/3/18.
 */
@Configuration
@Order(-20)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .formLogin().loginPage("/login").permitAll()
                .and().logout().permitAll()
                .and().httpBasic().disable()
                .csrf().disable()
                .requestMatchers()
                .antMatchers("/login", "/register/**", "/change-password", "/oauth/authorize", "/oauth/confirm_access","/auth/**")
                .antMatchers("/fonts/**", "/js/**", "/css/**", "/img/**", "swagger**", "/druid/**")
                .and()
                .authorizeRequests()
                .antMatchers("/register/**", "/change-password", "/fonts/**", "/js/**", "/css/**", "/img/**", "swagger**", "/druid/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .rememberMe()
                .tokenValiditySeconds(31536000)
                .and().exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler());//指定403错误处理方法
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			/*auth.inMemoryAuthentication()
					.withUser("reader")
					.password("reader")
					.authorities("ROLE_READER")
					.and()
					.withUser("writer")
					.password("writer")
					.authorities("ROLE_READER", "ROLE_WRITER")
					.and()
					.withUser("guest")
					.password("guest")
					.authorities("ROLE_GUEST");*/
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    //403错误处理
    @Bean
    AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
                    throws IOException, ServletException {
                logger.warn("403错误：", accessDeniedException);
            }
        };
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }
}
