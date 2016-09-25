/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.dbmigrate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

/**
 * @author zxc Aug 28, 2015 11:46:11 AM
 */
@SpringBootApplication
public class DbmigrateLauncher {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(DbmigrateLauncher.class, args);
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {

        return new EmbeddedServletContainerCustomizer() {

            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                container.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, "/404.html"),
                                        new ErrorPage(HttpStatus.NOT_FOUND, "/404.html"),
                                        new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/404.html"));
            }
        };
    }
}
