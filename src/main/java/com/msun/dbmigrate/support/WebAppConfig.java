/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.dbmigrate.support;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author zxc Feb 22, 2016 8:36:05 PM
 */
@EnableAutoConfiguration
@ComponentScan("com.msun")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebAppConfig extends WebMvcConfigurerAdapter {

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebAppConfig.class);
    }
}
