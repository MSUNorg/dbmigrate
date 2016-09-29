/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.dbmigrate;

import com.msun.dbmigrate.support.utils.SqlTemplate;

/**
 * @author zxc Sep 29, 2016 1:46:40 PM
 */
public class TemplateTest {

    static SqlTemplate template = new SqlTemplate("192.168.43.155", "tuzi", "wxbet", "wxbet2016");

    public static void main(String[] args) {
        System.out.println(template.primaryKey("characters"));
    }
}
