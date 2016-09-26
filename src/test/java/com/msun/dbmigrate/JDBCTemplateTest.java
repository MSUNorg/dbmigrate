/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.dbmigrate;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.jdbc.core.JdbcTemplate;

import com.msun.dbmigrate.support.utils.SqlTemplate;

/**
 * <pre>
 * #account_name
 * select * from accounts where login='miaonina';
 * select * from profile where login='miaonina';
 * select * from characters where account_name='miaonina';
 * select * from character_elf_warehouse where account_name='miaonina';
 * select * from character_shop_restrict where account_name='miaonina';
 * select * from character_vip_time where account='miaonina';
 * select * from character_warehouse where account_name='miaonina';
 * select * from character_shop_consumption where account_name='miaonina';
 * 
 * #characters objid
 * select * from characters_config where object_id=?;
 * select * from character_items where char_id=?;
 * select * from character_maptime where char_id=?;
 * select * from character_passivespells where char_id=?;
 * select * from character_quests where char_id=?;
 * select * from character_skills where char_obj_id=?;
 * select * from character_teleport where char_id=?;
 * select * from pets where item_obj_id=?;
 * select * from character_shop_warehouse where char_id=?;
 * 
 * #character_items id
 * select * from character_itemupdate where item_id=?;
 * </pre>
 * 
 * @author zxc Sep 25, 2016 1:08:32 PM
 */
public class JDBCTemplateTest {

    static JdbcTemplate template  = SqlTemplate.jdbc("192.168.43.155/tuzi", "wxbet", "wxbet2016");
    static JdbcTemplate template2 = SqlTemplate.jdbc("192.168.43.155/tuzi2", "wxbet", "wxbet2016");

    public static void main(String[] args) {
        Map<String, String> tableMap = SqlTemplate.map(new String[][] { { "accounts", "login" },
                { "profile", "login" }, { "characters", "account_name" },
                { "character_elf_warehouse", "account_name" }, { "character_shop_restrict", "account_name" },
                { "character_vip_time", "account" }, { "character_warehouse", "account_name" },
                { "character_shop_consumption", "account_name" } });

        System.out.println(template.queryForList("show tables"));
        // query by name
        String key = "11aa22";
        for (Entry<String, String> entry : tableMap.entrySet()) {
            List<Map<String, Object>> list = SqlTemplate.select(template, entry.getKey(), entry.getValue(), key);
            SqlTemplate.insert(template2, entry.getKey(), list);
        }

        // characters objid
        List<Map<String, Object>> charactersList = SqlTemplate.select(template, "characters", "account_name", key);
        for (Map<String, Object> map : charactersList) {
            optCharId(map.get("objid"));
        }
    }

    static void optCharId(Object id) {
        if (id == null) return;
        Map<String, String> tableMap = SqlTemplate.map(new String[][] { { "character_items", "char_id" },
                { "character_config", "object_id" }, { "character_maptime", "char_id" },
                { "character_passivespells", "char_obj_id" }, { "character_quests", "char_id" },
                { "character_skills", "char_obj_id" }, { "character_teleport", "char_id" }, { "pets", "item_obj_id" },
                { "character_shop_warehouse", "char_id" } });
        for (Entry<String, String> entry : tableMap.entrySet()) {
            List<Map<String, Object>> list = SqlTemplate.select(template, entry.getKey(), entry.getValue(), id);
            SqlTemplate.insert(template2, entry.getKey(), list);
        }

        List<Map<String, Object>> itemsList = SqlTemplate.select(template, "character_items", "char_id", id);
        for (Map<String, Object> map : itemsList) {
            Object item_id = map.get("id");
            List<Map<String, Object>> list = SqlTemplate.select(template, "character_itemupdate", "item_id", item_id);
            SqlTemplate.insert(template2, "character_itemupdate", list);
        }
    }
}
