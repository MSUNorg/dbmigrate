/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.dbmigrate.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.lamfire.json.JSON;
import com.lamfire.pandora.FireMap;
import com.lamfire.utils.Lists;
import com.msun.dbmigrate.cons.Definition;
import com.msun.dbmigrate.support.JsonResult;
import com.msun.dbmigrate.support.utils.DbMeta;
import com.msun.dbmigrate.support.utils.PandoraDataStore;

/**
 * @author zxc Aug 8, 2016 5:00:13 PM
 */
public class BaseController implements Definition {

    public static PandoraDataStore pandora = PandoraDataStore.getInstance();

    @Autowired
    protected HttpServletRequest   request;
    @Autowired
    protected HttpSession          session;

    public List<DbMeta> dbconf() {
        FireMap dao = pandora.getMap(DBCONF);
        List<String> keys = dao.keys();
        List<DbMeta> list = Lists.newLinkedList();
        for (String key : keys) {
            if (dao.get(key) != null) {
                DbMeta dbMeta = JSON.toJavaObject(JSON.fromBytes(dao.get(key)), DbMeta.class);
                list.add(dbMeta);
            }
        }
        return list;
    }

    public DbMeta dbconf(String id) {
        FireMap dao = pandora.getMap(DBCONF);
        if (dao.get(id) != null) return JSON.toJavaObject(JSON.fromBytes(dao.get(id)), DbMeta.class);
        return null;
    }

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
