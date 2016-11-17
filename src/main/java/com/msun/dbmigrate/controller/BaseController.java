/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.dbmigrate.controller;

import static com.msun.dbmigrate.support.utils.SqlTemplate.map;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
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

    public static PandoraDataStore pandora            = PandoraDataStore.getInstance();

    @Autowired
    protected HttpServletRequest   request;
    @Autowired
    protected HttpSession          session;

    static Map<String, String>     tableMapByAccount  = map(new String[][] { { "accounts", "login" },
            { "profile", "login" }, { "character_elf_warehouse", "account_name" },
            { "character_shop_restrict", "account_name" }, { "character_vip_time", "account" },
            { "character_warehouse", "account_name" }, { "character_shop_consumption", "account_name" } });

    static Map<String, String>     tableMapByAccount2 = map(new String[][] { { "accounts", "login" },
            { "profile", "login" }, { "character_elf_warehouse", "account_name" },
            { "character_shop_restrict", "account_name" }, { "character_vip_time", "account" },
            { "character_shop_consumption", "account_name" } });

    static Map<String, String>     tableMapByChar     = map(new String[][] { { "character_config", "object_id" },
            { "character_maptime", "char_id" }, { "character_passivespells", "char_obj_id" },
            { "character_quests", "char_id" }, { "character_skills", "char_obj_id" },
            { "character_teleport", "char_id" }, { "pets", "item_obj_id" }, { "character_shop_warehouse", "char_id" } });

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

    public SqlTemplate genTemplate(DbMeta dbMeta) {
        return new SqlTemplate(dbMeta.getDbAddr(), dbMeta.getDbName(), dbMeta.getName(), dbMeta.getPasswdStr());
    }

    @SuppressWarnings("unchecked")
    public void optdb(String keyword, String where, Object value, SqlTemplate template, SqlTemplate ttemplate,
                      AtomicInteger counter, boolean optWarehouse) {
        for (Entry<String, String> entry : tableMapByAccount2.entrySet()) {
            if (!optWarehouse && StringUtils.endsWithIgnoreCase(entry.getKey(), "character_elf_warehouse")) continue;
            List<Map<String, Object>> list = template.select(entry.getKey(), entry.getValue(), keyword);
            for (Map<String, Object> map_ : list) {
                if (StringUtils.equalsIgnoreCase("id", entry.getValue())) {
                    map_.put("id", ttemplate.maxId(entry.getKey(), "id"));
                }
                // delete original data,ignore accounts
                if (!StringUtils.equalsIgnoreCase("accounts", entry.getKey())) {
                    template.delete(entry.getKey(), entry.getValue(), keyword);
                }
                counter.getAndAdd(ttemplate.insert(entry.getKey(), map_));
            }
        }

        Map<Object, Object> objMap = Maps.newHashMap();
        List<Map<String, Object>> charactersList = template.select("characters", where, value);
        // delete original data
        template.delete("characters", where, value);
        for (Map<String, Object> map : charactersList) {
            Object id = map.get("objid");
            Object tid = ttemplate.maxId("characters", "objid");
            objMap.put(tid, id);
            map.put("objid", tid);
            counter.getAndAdd(ttemplate.insert("characters", map));
        }

        int items_size = 0;
        for (Map<String, Object> map : charactersList) {
            Object targetid = map.get("objid");
            if (targetid == null) continue;
            Object id = objMap.get(targetid);
            if (id == null) continue;
            for (Entry<String, String> entry : tableMapByChar.entrySet()) {
                List<Map<String, Object>> list = template.select(entry.getKey(), entry.getValue(), id);
                for (Map<String, Object> _map : list) {
                    _map.put(entry.getValue(), targetid);
                    if (StringUtils.equalsIgnoreCase("id", entry.getValue())) {
                        _map.put("id", ttemplate.maxId(entry.getKey(), "id"));
                    }
                    // delete original data
                    template.delete(entry.getKey(), entry.getValue(), id);
                    counter.getAndAdd(ttemplate.insert(entry.getKey(), _map));
                }
            }

            Map<Object, Object> itemMap = Maps.newHashMap();
            List<Map<String, Object>> itemsList = template.select("character_items", "char_id", id);
            items_size += itemsList.size();
            // delete original data
            template.delete("character_items", "char_id", id);
            for (Map<String, Object> _map : itemsList) {
                Long id1 = ttemplate.maxId("character_items", "id");
                Long id2 = ttemplate.maxId("character_warehouse", "id");
                Object maxId = (id1 > id2) ? id1 : id2;
                itemMap.put(maxId, _map.get("id"));
                _map.put("char_id", targetid);
                _map.put("id", maxId);
                counter.getAndAdd(ttemplate.insert("character_items", _map));
            }

            for (Map<String, Object> _map : itemsList) {
                Object _item_id = _map.get("id");
                if (_item_id == null) continue;
                Object item_id = itemMap.get(_item_id);
                if (item_id == null) continue;
                List<Map<String, Object>> list = template.select("character_itemupdate", "item_id", item_id);
                // delete original data
                template.delete("character_itemupdate", "item_id", item_id);
                for (Map<String, Object> map_ : list) {
                    map_.put("item_id", _item_id);
                    counter.getAndAdd(ttemplate.insert("character_itemupdate", map_));
                }
            }
        }

        if (optWarehouse) {
            // character_warehouse特殊处理
            List<Map<String, Object>> list = template.select("character_warehouse", "account_name", keyword);
            int warehouse_size = list.size();
            // delete original data
            template.delete("character_warehouse", "account_name", keyword);
            Long id = 0l;
            for (Map<String, Object> map_ : list) {
                Long id1 = ttemplate.maxId("character_items", "id");
                Long id2 = ttemplate.maxId("character_warehouse", "id");
                // character_items和character_warehouse +2
                id = ((id1 > id2) ? id1 : id2) + items_size + warehouse_size + 2;
                map_.put("id", id);
                counter.getAndAdd(ttemplate.insert("character_warehouse", map_));
            }
            ttemplate.autoIncrement("accounts", id + 2);
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
