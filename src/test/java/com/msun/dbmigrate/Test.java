/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.dbmigrate;

import com.lamfire.utils.NumberUtils;
import com.msun.dbmigrate.support.utils.SqlTemplate;

/**
 * @author zxc Aug 15, 2016 5:27:51 PM
 */
public class Test {

    static SqlTemplate template = new SqlTemplate("120.24.228.30", "armani", "bet", "57c6a89c84ae6297514a1c37");

    public static void main(String[] args) {
        System.out.println(NumberUtils.format(50000, 2));
        template.autoIncrement("accounts", 100);
    }
}
