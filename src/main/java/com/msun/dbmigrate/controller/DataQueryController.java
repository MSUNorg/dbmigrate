/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.dbmigrate.controller;

import static com.msun.dbmigrate.support.SqlTemplate.allColumns;
import static com.msun.dbmigrate.support.SqlTemplate.jdbc;
import static com.msun.dbmigrate.support.SqlTemplate.map;
import static com.msun.dbmigrate.support.SqlTemplate.select;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lamfire.utils.Lists;
import com.lamfire.utils.Maps;
import com.msun.dbmigrate.support.utils.DbMeta;

/**
 * 数据查询
 * 
 * @author zxc Sep 23, 2016 5:58:09 PM
 */
@Controller
@RequestMapping(value = "/query")
public class DataQueryController extends BaseController {

    static Map<String, String> tableMapByAccount = map(new String[][] { { "accounts", "login" },
            { "profile", "login" }, { "characters", "account_name" }, { "character_elf_warehouse", "account_name" },
            { "character_shop_restrict", "account_name" }, { "character_vip_time", "account" },
            { "character_warehouse", "account_name" }, { "character_shop_consumption", "account_name" } });
    static Map<String, String> tableMapByChar    = map(new String[][] { { "character_items", "char_id" },
            { "character_config", "object_id" }, { "character_maptime", "char_id" },
            { "character_passivespells", "char_obj_id" }, { "character_quests", "char_id" },
            { "character_skills", "char_obj_id" }, { "character_teleport", "char_id" }, { "pets", "item_obj_id" },
            { "character_shop_warehouse", "char_id" } });

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(String keyword, String dbId) {
        ModelAndView mv = new ModelAndView("query")//
        .addObject("dbId", dbId)//
        .addObject("keyword", keyword)//
        .addObject("list", dbconf());
        if (StringUtils.isEmpty(keyword) || StringUtils.isEmpty(dbId)) return mv;
        DbMeta dbMeta = dbconf(dbId);
        if (dbMeta == null) return mv;

        JdbcTemplate template = jdbc(dbMeta.getDbAddr(), dbMeta.getDbName(), dbMeta.getName(), dbMeta.getPasswd());
        List<Map<String, Object>> tableList = template.queryForList("show tables");
        List<Object> tables = Lists.newLinkedList();
        for (Map<String, Object> map : tableList) {
            tables.addAll(map.values());
        }
        mv.addObject("tableList", tables);
        Map<String, Object> dataMap = Maps.newHashMap();
        mv.addObject("dataMap", dataMap);

        for (Entry<String, String> entry : tableMapByAccount.entrySet()) {
            List<Map<String, Object>> list = select(template, entry.getKey(), entry.getValue(), keyword);
            dataMap.put(entry.getKey(), list);
            dataMap.put(entry.getKey() + "_th", allColumns(list));
        }

        List<Map<String, Object>> charactersList = select(template, "characters", "account_name", keyword);
        for (Map<String, Object> map : charactersList) {
            Object id = map.get("objid");
            if (id == null) continue;
            for (Entry<String, String> entry : tableMapByChar.entrySet()) {
                List<Map<String, Object>> list = select(template, entry.getKey(), entry.getValue(), id);
                dataMap.put(entry.getKey(), list);
                dataMap.put(entry.getKey() + "_th", allColumns(list));
            }

            List<Map<String, Object>> itemupdateList = Lists.newLinkedList();
            List<Map<String, Object>> itemsList = select(template, "character_items", "char_id", id);
            for (Map<String, Object> _map : itemsList) {
                Object item_id = _map.get("id");
                if (item_id == null) continue;
                List<Map<String, Object>> list = select(template, "character_itemupdate", "item_id", item_id);
                itemupdateList.addAll(list);
            }
            dataMap.put("character_itemupdate", itemupdateList);
            dataMap.put("character_itemupdate_th", allColumns(itemupdateList));
        }
        return mv;
    }
}
