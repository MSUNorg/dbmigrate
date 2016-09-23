/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.dbmigrate.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.lamfire.json.JSON;
import com.msun.dbmigrate.cons.Definition;
import com.msun.dbmigrate.support.JsonResult;

/**
 * @author zxc Aug 8, 2016 5:00:13 PM
 */
public class BaseController implements Definition {

    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpSession        session;

    public JsonResult ok(String msg) {
        return JsonResult.successMsg(msg);
    }

    public JsonResult ok(String msg, Object[][] keyValues) {
        JSON data = new JSON();
        for (int i = 0; i < keyValues.length; i++) {
            data.put((String) keyValues[i][0], keyValues[i][1]);
        }
        return JsonResult.success(data, msg);
    }

    public JsonResult ok(String msg, Object data) {
        return JsonResult.success(data, msg);
    }

    public JsonResult fail(String msg) {
        return JsonResult.failMsg(msg);
    }
}
