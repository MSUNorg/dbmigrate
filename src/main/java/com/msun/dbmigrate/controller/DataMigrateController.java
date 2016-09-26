/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.dbmigrate.controller;

import static com.msun.dbmigrate.support.utils.SqlTemplate.insert;
import static com.msun.dbmigrate.support.utils.SqlTemplate.jdbc;
import static com.msun.dbmigrate.support.utils.SqlTemplate.select;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.msun.dbmigrate.support.JsonResult;
import com.msun.dbmigrate.support.utils.DbMeta;

/**
 * 数据迁移
 * 
 * @author zxc Sep 23, 2016 5:58:38 PM
 */
@Controller
@RequestMapping(value = "/migrate")
public class DataMigrateController extends BaseController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("migrate")//
        .addObject("list", dbconf());
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public JsonResult domigrate(String keyword, String dbId, String targetId) {
        if (StringUtils.isEmpty(keyword) || StringUtils.isEmpty(dbId)) return fail("迁移失败,参数错误");
        DbMeta dbMeta = dbconf(dbId);
        DbMeta tdbMeta = dbconf(targetId);
        if (dbMeta == null || tdbMeta == null) return fail("迁移失败,数据库参数错误");

        JdbcTemplate template = jdbc(dbMeta.getDbAddr(), dbMeta.getDbName(), dbMeta.getName(), dbMeta.getPasswd());
        JdbcTemplate ttemplate = jdbc(tdbMeta.getDbAddr(), tdbMeta.getDbName(), tdbMeta.getName(), tdbMeta.getPasswd());
        AtomicInteger counter = new AtomicInteger();

        for (Entry<String, String> entry : tableMapByAccount.entrySet()) {
            List<Map<String, Object>> list = select(template, entry.getKey(), entry.getValue(), keyword);
            counter.getAndAdd(insert(ttemplate, entry.getKey(), list));
        }
        List<Map<String, Object>> charactersList = select(template, "characters", "account_name", keyword);
        for (Map<String, Object> map : charactersList) {
            Object id = map.get("objid");
            if (id == null) continue;
            for (Entry<String, String> entry : tableMapByChar.entrySet()) {
                List<Map<String, Object>> list = select(template, entry.getKey(), entry.getValue(), id);
                counter.getAndAdd(insert(ttemplate, entry.getKey(), list));
            }

            List<Map<String, Object>> itemsList = select(template, "character_items", "char_id", id);
            for (Map<String, Object> _map : itemsList) {
                Object item_id = _map.get("id");
                if (item_id == null) continue;
                List<Map<String, Object>> list = select(template, "character_itemupdate", "item_id", item_id);
                counter.getAndAdd(insert(ttemplate, "character_itemupdate", list));
            }
        }
        return ok("迁移成功", counter.get());
    }
}
