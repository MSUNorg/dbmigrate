/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.dbmigrate;

import static com.msun.dbmigrate.support.utils.SqlTemplate.map;

import java.util.Map;

import com.msun.dbmigrate.support.utils.SqlTemplate;

/**
 * @author zxc Sep 29, 2016 1:46:40 PM
 */
public class TemplateTest {

    static Map<String, String> tableMapByAccount = map(new String[][] { { "accounts", "login" },
            { "profile", "login" }, { "characters", "account_name" }, { "character_elf_warehouse", "account_name" },
            { "character_shop_restrict", "account_name" }, { "character_vip_time", "account" },
            { "character_warehouse", "account_name" }, { "character_shop_consumption", "account_name" } });
    static Map<String, String> tableMapByChar    = map(new String[][] { { "character_items", "char_id" },
            { "character_config", "object_id" }, { "character_maptime", "char_id" },
            { "character_passivespells", "char_obj_id" }, { "character_quests", "char_id" },
            { "character_skills", "char_obj_id" }, { "character_teleport", "char_id" }, { "pets", "item_obj_id" },
            { "character_shop_warehouse", "char_id" } });

    static SqlTemplate         template          = new SqlTemplate("192.168.43.155", "tuzi", "wxbet", "wxbet2016");

    public static void main(String[] args) {
        for (String table : tableMapByAccount.keySet()) {
            System.out.println(template.primaryKey(table));
            System.out.println(template.maxId(table, template.primaryKey(table)));
        }
        for (String table : tableMapByChar.keySet()) {
            System.out.println(template.primaryKey(table));
            System.out.println(template.maxId(table, template.primaryKey(table)));
        }
    }
}
