/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.dbmigrate.controller;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
    public ModelAndView migrateRole() {
        return new ModelAndView("migrateRole")//
        .addObject("list", dbconf());
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

        SqlTemplate template = new SqlTemplate(dbMeta.getDbAddr(), dbMeta.getDbName(), dbMeta.getName(),
                                               dbMeta.getPasswd());
        SqlTemplate ttemplate = new SqlTemplate(tdbMeta.getDbAddr(), tdbMeta.getDbName(), tdbMeta.getName(),
                                                tdbMeta.getPasswd());
        AtomicInteger counter = new AtomicInteger();

        List<Map<String, Object>> accounts = template.select("accounts", "login", keyword);
        if (accounts == null || accounts.size() == 0) return fail("迁移失败,用户信息不存在");

        for (Entry<String, String> entry : tableMapByAccount.entrySet()) {
            List<Map<String, Object>> list = template.select(entry.getKey(), entry.getValue(), keyword);
            counter.getAndAdd(ttemplate.insert(entry.getKey(), null, list));
        }
        List<Map<String, Object>> charactersList = template.select("characters", "account_name", keyword);
        for (Map<String, Object> map : charactersList) {
            Object id = map.get("objid");
            if (id == null) continue;
            for (Entry<String, String> entry : tableMapByChar.entrySet()) {
                List<Map<String, Object>> list = template.select(entry.getKey(), entry.getValue(), id);
                counter.getAndAdd(ttemplate.insert(entry.getKey(), null, list));
            }

            List<Map<String, Object>> itemsList = template.select("character_items", "char_id", id);
            for (Map<String, Object> _map : itemsList) {
                Object item_id = _map.get("id");
                if (item_id == null) continue;
                List<Map<String, Object>> list = template.select("character_itemupdate", "item_id", item_id);
                counter.getAndAdd(ttemplate.insert("character_itemupdate", null, list));
            }
        }
        return ok("迁移成功", counter.get());
    }
}
