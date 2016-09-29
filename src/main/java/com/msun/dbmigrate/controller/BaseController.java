/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.dbmigrate.controller;

import static com.msun.dbmigrate.support.utils.SqlTemplate.map;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.lamfire.json.JSON;
import com.lamfire.pandora.FireMap;
import com.lamfire.utils.Lists;
import com.lamfire.utils.Maps;
import com.msun.dbmigrate.cons.Definition;
import com.msun.dbmigrate.support.JsonResult;
import com.msun.dbmigrate.support.utils.DbMeta;
import com.msun.dbmigrate.support.utils.PandoraDataStore;
import com.msun.dbmigrate.support.utils.SqlTemplate;

/**
 * @author zxc Aug 8, 2016 5:00:13 PM
 */
public class BaseController implements Definition {

    public static PandoraDataStore pandora           = PandoraDataStore.getInstance();

    @Autowired
    protected HttpServletRequest   request;
    @Autowired
    protected HttpSession          session;

    static Map<String, String>     tableMapByAccount = map(new String[][] { { "accounts", "login" },
            { "profile", "login" }, { "character_elf_warehouse", "account_name" },
            { "character_shop_restrict", "account_name" }, { "character_vip_time", "account" },
            { "character_warehouse", "account_name" }, { "character_shop_consumption", "account_name" } });
    static Map<String, String>     tableMapByChar    = map(new String[][] { { "character_items", "char_id" },
            { "character_config", "object_id" }, { "character_maptime", "char_id" },
            { "character_passivespells", "char_obj_id" }, { "character_quests", "char_id" },
            { "character_skills", "char_obj_id" }, { "character_teleport", "char_id" }, { "pets", "item_obj_id" },
            { "character_shop_warehouse", "char_id" } });

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

    public void optdb(String keyword, String where, Object value, SqlTemplate template, SqlTemplate ttemplate,
                      AtomicInteger counter) {
        for (Entry<String, String> entry : tableMapByAccount.entrySet()) {
            List<Map<String, Object>> list = template.select(entry.getKey(), entry.getValue(), keyword);
            counter.getAndAdd(ttemplate.insert(entry.getKey(), list));
        }

        Map<Object, Object> objMap = Maps.newHashMap();
        List<Map<String, Object>> charactersList = template.select("characters", where, value);
        for (Map<String, Object> map : charactersList) {
            Object id = map.get("objid");
            Object tid = ttemplate.maxId("characters", "objid");
            objMap.put(id, tid);
            map.put("objid", tid);
        }
        counter.getAndAdd(ttemplate.insert("characters", charactersList));

        for (Map<String, Object> map : charactersList) {
            Object id = map.get("objid");
            if (id == null) continue;
            Object targetid = objMap.get(id);
            if (targetid == null) continue;
            for (Entry<String, String> entry : tableMapByChar.entrySet()) {
                List<Map<String, Object>> list = template.select(entry.getKey(), entry.getValue(), id);
                for (Map<String, Object> _map : list) {
                    _map.put(entry.getValue(), targetid);
                }
                counter.getAndAdd(ttemplate.insert(entry.getKey(), list));
            }

            List<Map<String, Object>> itemsList = template.select("character_items", "char_id", id);
            for (Map<String, Object> _map : itemsList) {
                Object item_id = _map.get("id");
                if (item_id == null) continue;
                List<Map<String, Object>> list = template.select("character_itemupdate", "item_id", item_id);
                counter.getAndAdd(ttemplate.insert("character_itemupdate", list));
            }
        }
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
