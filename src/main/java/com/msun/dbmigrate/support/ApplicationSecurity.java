/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.dbmigrate.support;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

import com.lamfire.json.JSON;
import com.lamfire.json.JSONArray;
import com.msun.dbmigrate.cons.Definition;

/**
 * @author zxc Sep 23, 2016 3:06:25 PM
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurity extends WebSecurityConfigurerAdapter implements Definition {

    @Value("${public.url}")
    private String[] urls;
    @Value("${userSet}")
    private String   userSet;

    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandlerImpl() {
        return new AccessDeniedHandler() {

            public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException ex)
                                                                                                         throws IOException,
                                                                                                         ServletException {
                boolean isAjax = isAjaxRequest(req);
                if (isAjax) {
                    JsonResult result = JsonResult.fail(exceptionDetail(ex));
                    res.setHeader("Content-Type", "application/json;charset=UTF-8");
                    res.getWriter().print(result.toString());
                } else if (!res.isCommitted()) {
                    req.setAttribute(WebAttributes.ACCESS_DENIED_403, ex);
                    res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    RequestDispatcher dispatcher = req.getRequestDispatcher("/401.html");
                    dispatcher.forward(req, res);
                }
            }
        };
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        return header != null && "XMLHttpRequest".equalsIgnoreCase(header);
    }

    public String exceptionDetail(Throwable ex) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream pout = new PrintStream(out);
        ex.printStackTrace(pout);
        String ret = out.toString();
        pout.close();
        try {
            out.close();
        } catch (Exception e) {
            _.error("exceptionDetail error!", e);
        }
        return ret;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()//
        .authorizeRequests()//
        .antMatchers(urls).permitAll()//
        .anyRequest().authenticated()//
        .and().formLogin().defaultSuccessUrl("/").loginPage("/login").failureUrl("/login?error").permitAll()//
        .and().logout().deleteCookies("JSESSIONID").permitAll()//
        .and().rememberMe().key("11a63e5e-ccd7-452c-856e-025b08f1211e").tokenValiditySeconds(2592000)//
        .and().exceptionHandling().accessDeniedHandler(accessDeniedHandlerImpl())//
        .and().sessionManagement().maximumSessions(1).expiredUrl("/login");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        JSONArray userArray = JSONArray.fromJSONString(userSet);
        for (Object json : userArray.asList()) {
            JSON user = (JSON) json;
            auth.inMemoryAuthentication()//
            .withUser(user.getString("name"))//
            .password(user.getString("passwd"))//
            .roles(user.getJSONArray("role").toArray(new String[] {}));
        }
    }
}
