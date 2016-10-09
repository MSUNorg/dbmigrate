/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.dbmigrate.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.msun.dbmigrate.support.JsonResult;
import com.msun.dbmigrate.support.utils.DbMeta;
import com.msun.dbmigrate.support.utils.SqlTemplate;

/**
 * 数据迁移
 * 
 * @author zxc Sep 23, 2016 5:58:38 PM
 */
@Controller
public class DataMigrateController extends BaseController {

    @RequestMapping(value = "/migrateRole", method = RequestMethod.GET)
    public ModelAndView migrateRole(String keyword, String dbId) {
        List<Map<String, Object>> charactersList = null;
        if (StringUtils.isNotEmpty(keyword) && StringUtils.isNotEmpty(dbId)) {
            DbMeta dbMeta = dbconf(dbId);
            SqlTemplate template = genTemplate(dbMeta);
            charactersList = template.select("characters", "account_name", keyword);
        }
        return new ModelAndView("migrateRole")//
        .addObject("list", dbconf())//
        .addObject("charactersList", charactersList)//
        .addObject("dbId", dbId)//
        .addObject("keyword", keyword);
    }

    @RequestMapping(value = "/migrateRole", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult domigrateRole(String keyword, Long charId, String dbId, String targetId) {
        if (StringUtils.isEmpty(keyword) || StringUtils.isEmpty(dbId)) return fail("迁移失败,参数错误");
        DbMeta dbMeta = dbconf(dbId);
        DbMeta tdbMeta = dbconf(targetId);
        if (dbMeta == null || tdbMeta == null) return fail("迁移失败,数据库参数错误");

        SqlTemplate template = genTemplate(dbMeta);
        SqlTemplate ttemplate = genTemplate(tdbMeta);
        AtomicInteger counter = new AtomicInteger();

        List<Map<String, Object>> accounts = template.select("accounts", "login", keyword);
        if (accounts == null || accounts.size() == 0) return fail("迁移失败,用户信息不存在");

        try {
            optdb(keyword, "objid", charId, template, ttemplate, counter);
        } catch (Exception e) {
            _.error("optdb error!", e);
            return fail("迁移失败,数据库参数错误");
        }
        return ok("迁移成功", counter.get());
    }

    @RequestMapping(value = "/migrate", method = RequestMethod.GET)
    public ModelAndView migrate() {
        return new ModelAndView("migrate")//
        .addObject("list", dbconf());
    }

    @RequestMapping(value = "/migrate", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult domigrate(String keyword, String dbId, String targetId) {
        if (StringUtils.isEmpty(keyword) || StringUtils.isEmpty(dbId)) return fail("迁移失败,参数错误");
        DbMeta dbMeta = dbconf(dbId);
        DbMeta tdbMeta = dbconf(targetId);
        if (dbMeta == null || tdbMeta == null) return fail("迁移失败,数据库参数错误");

        SqlTemplate template = genTemplate(dbMeta);
        SqlTemplate ttemplate = genTemplate(tdbMeta);
        AtomicInteger counter = new AtomicInteger();

        List<Map<String, Object>> accounts = template.select("accounts", "login", keyword);
        if (accounts == null || accounts.size() == 0) return fail("迁移失败,用户信息不存在");

        try {
            optdb(keyword, "account_name", keyword, template, ttemplate, counter);
        } catch (Exception e) {
            _.error("optdb error!", e);
            return fail("迁移失败,数据库参数错误");
        }
        return ok("迁移成功", counter.get());
    }
}
