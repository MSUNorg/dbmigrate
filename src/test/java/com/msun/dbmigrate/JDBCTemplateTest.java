/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.dbmigrate;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

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
 * 
 * #character_items id
 * select * from character_itemupdate where item_id=?;
 * </pre>
 * 
 * @author zxc Sep 25, 2016 1:08:32 PM
 */
public class JDBCTemplateTest {

    public static void main(String[] args) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.43.155/tuzi?useUnicode=true&characterEncoding=UTF-8");
        dataSource.setUsername("wxbet");
        dataSource.setPassword("wxbet2016");
        JdbcTemplate template = new JdbcTemplate(dataSource);

        System.out.println(template.queryForList("show tables"));

        List<?> list = template.queryForList("select * from accounts where login=?", "miaonina");
        System.out.println(list);

        System.out.println(template.queryForList("select * from profile where login=?", "miaonina"));
        System.out.println(template.queryForList("select * from characters where account_name=?", "miaonina"));
        System.out.println(template.queryForList("select * from character_elf_warehouse where account_name=?",
                                                 "miaonina"));
        System.out.println(template.queryForList("select * from character_shop_restrict where account_name=?",
                                                 "miaonina"));
        System.out.println(template.queryForList("select * from character_vip_time where account=?", "miaonina"));
        System.out.println(template.queryForList("select * from character_warehouse where account_name=?", "miaonina"));
        System.out.println(template.queryForList("select * from character_shop_consumption where account_name=?",
                                                 "miaonina"));

    }
}
