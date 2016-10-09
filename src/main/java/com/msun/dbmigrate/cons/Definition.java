/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.dbmigrate.cons;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zxc Apr 28, 2016 11:46:14 AM
 */
public interface Definition {

    static final Logger         _                      = LoggerFactory.getLogger(Definition.class);

    public final static Integer PAGE_SIZE              = 30;
    public final static String  _PAGE_SIZE_            = "30";

    public final static String  NAV                    = "nav";

    public static final String  OPENID_SESSION_KEY     = "OPENID_SESSION_KEY";

    public final static String  SESSION_REGISTER_CODE  = "SESSION_REGISTER_PHONE_CODE";
    public final static String  SESSION_RESET_PWD_CODE = "SESSION_RESET_PASSWORD_PHONE_CODE";

    public final static long    PHONE_CODE_TIMEOUT     = 60000;

    public final static String  CUR_USER               = "cur_user";
    public final static String  DBCONF                 = "DBCONF";

    DateFormat                  f                      = new SimpleDateFormat("yyyy-MM-dd HH:mm");
}
