/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.dbmigrate.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.lamfire.json.JSON;
import com.lamfire.utils.DateFormatUtils;

/**
 * @author zxc Aug 4, 2016 9:09:40 PM
 */
@Controller
public class IndexController extends BaseController {

    @RequestMapping(value = { "/", "/index" })
    public ModelAndView index() {
        return new ModelAndView("index")//
        .addObject("serverAt", System.currentTimeMillis());
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(HttpServletRequest request) {
        if (request.getRemoteUser() != null) return new ModelAndView(new RedirectView("/", true, false));
        return new ModelAndView("login");
    }

    // 健康检查
    @Bean
    public ServletRegistrationBean healthAction() {
        return new ServletRegistrationBean(new HttpServlet() {

            private static final long serialVersionUID = -33776623249934740L;

            public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                                                                       IOException {
                JSON json = new JSON();
                json.put("status", 200);
                json.put("time", DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss:SSS"));
                response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                out.println(json.toJSONString());
                out.flush();
                out.close();
            }

            public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                                                                        IOException {
                doGet(request, response);
            }
        }, "/health");
    }
}
