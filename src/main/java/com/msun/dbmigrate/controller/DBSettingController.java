/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.dbmigrate.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.lamfire.code.PUID;
import com.lamfire.json.JSON;
import com.lamfire.pandora.FireMap;
import com.lamfire.utils.Lists;
import com.msun.dbmigrate.support.JsonResult;
import com.msun.dbmigrate.support.utils.DbMeta;

/**
 * 数据库配置
 * 
 * @author zxc Sep 26, 2016 10:06:24 AM
 */
@Controller
@RequestMapping(value = "/setting")
public class DBSettingController extends BaseController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list() {
        FireMap dao = pandora.getMap(DBCONF);
        List<String> keys = dao.keys();
        List<DbMeta> list = Lists.newLinkedList();
        for (String key : keys) {
            if (dao.get(key) != null) {
                DbMeta dbMeta = JSON.toJavaObject(JSON.fromBytes(dao.get(key)), DbMeta.class);
                list.add(dbMeta);
            }
        }
        return new ModelAndView("setting")//
        .addObject("list", list);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable(value = "id") String id) {
        FireMap dao = pandora.getMap(DBCONF);
        DbMeta dbMeta = new DbMeta();
        if (dao.get(id) != null) dbMeta = JSON.toJavaObject(JSON.fromBytes(dao.get(id)), DbMeta.class);
        return new ModelAndView("settingEdit")//
        .addObject("dbMeta", dbMeta);
    }

    @RequestMapping(value = "/del/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult del(@PathVariable(value = "id") String id) {
        FireMap dao = pandora.getMap(DBCONF);
        if (dao.get(id) != null) dao.remove(id);
        return ok("删除成功");
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(DbMeta dbMeta) {
        if (dbMeta == null) return fail("参数错误");
        String dbAddr = StringUtils.trim(dbMeta.getDbAddr());
        String dbName = StringUtils.trim(dbMeta.getDbName());
        String name = StringUtils.trim(dbMeta.getName());
        String passwd = StringUtils.trim(dbMeta.getPasswd());
        if (StringUtils.isEmpty(dbAddr) || StringUtils.isEmpty(dbName) || StringUtils.isEmpty(name)
            || StringUtils.isEmpty(passwd)) return fail("参数为空");

        FireMap dao = pandora.getMap(DBCONF);
        String id = PUID.makeAsString();
        dbMeta.setId(id);
        dbMeta.setDbAddr(dbAddr);
        dbMeta.setDbName(dbName);
        dbMeta.setName(name);
        dbMeta.setPasswd(passwd);
        dbMeta.setCreateAt(System.currentTimeMillis());
        dao.put(id, JSON.toJSONString(dbMeta).getBytes());
        return ok("成功");
    }
}
