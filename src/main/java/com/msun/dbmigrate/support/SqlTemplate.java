/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.dbmigrate.support;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.lamfire.utils.Lists;
import com.lamfire.utils.Maps;
import com.msun.dbmigrate.cons.Definition;

/**
 * @author zxc Sep 25, 2016 7:28:16 PM
 */
public class SqlTemplate implements Definition {

    public static Map<String, String> map(String[][] keyValues) {
        Map<String, String> data = Maps.newHashMap();
        for (int i = 0; i < keyValues.length; i++) {
            data.put((String) keyValues[i][0], keyValues[i][1]);
        }
        return data;
    }

    public static JdbcTemplate jdbc(String url, String name, String password) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://" + url + "?useUnicode=true&characterEncoding=UTF-8");
        dataSource.setUsername(name);
        dataSource.setPassword(password);
        return new JdbcTemplate(dataSource);
    }

    public static JdbcTemplate jdbc(String dbAddr, String dbName, String name, String password) {
        return jdbc(dbAddr + "/" + dbName, name, password);
    }

    public static boolean test(JdbcTemplate template) {
        try {
            template.queryForList("show tables");
            return true;
        } catch (Exception e) {
            _.error("template test error!", e);
            return false;
        }
    }

    public static List<Map<String, Object>> select(JdbcTemplate template, String tableName, String where, Object key) {
        try {
            List<Map<String, Object>> list = template.queryForList(genSelect(tableName, where), key);
            _.info("select " + tableName + ",data=" + list);
            return list;
        } catch (Exception e) {
            _.error("template select error!", e);
        }
        return Lists.newArrayList();
    }

    public static void insert(JdbcTemplate template, String tableName, List<Map<String, Object>> list) {
        for (final Map<String, Object> map : list) {
            try {
                template.update(genInsert(tableName, map), map.values().toArray(new Object[] {}));
            } catch (Exception e) {
                _.error("template insert error!", e);
            }
        }
    }

    public static List<String> allColumns(List<Map<String, Object>> list) {
        if (list == null || list.size() == 0) return Lists.newArrayList();
        return Lists.newLinkedList(list.get(0).keySet());
    }

    public static List<String> allColumns(Map<String, Object> map) {
        return Lists.newLinkedList(map.keySet());
    }

    public static String genSelect(String tableName, String where) {
        return "select * from " + tableName + " where " + where + "=?";
    }

    public static String genInsert(String tableName, Map<String, Object> map) {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ").append(tableName).append("(");
        List<String> allColumns = Lists.newLinkedList(map.keySet());
        int size = allColumns.size();
        for (int i = 0; i < size; i++) {
            sql.append(allColumns.get(i)).append(splitCommea(size, i));
        }

        sql.append(") values (");
        makeColumnQuestions(sql, allColumns);
        sql.append(")");
        return sql.toString().intern();
    }

    public static String splitCommea(int size, int i) {
        return (i + 1 < size) ? " , " : "";
    }

    public static void makeColumnQuestions(StringBuilder sql, List<String> columns) {
        int size = columns.size();
        for (int i = 0; i < size; i++) {
            sql.append("?").append(splitCommea(size, i));
        }
    }
}
