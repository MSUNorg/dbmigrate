/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.dbmigrate.support.utils;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.lamfire.utils.Lists;
import com.lamfire.utils.Maps;
import com.msun.dbmigrate.cons.Definition;

/**
 * @author zxc Sep 25, 2016 7:28:16 PM
 */
public class SqlTemplate extends JdbcTemplate implements Definition {

    private String dbAddr;
    private String dbName;
    private String name;
    private String password;

    public SqlTemplate(String dbAddr, String dbName, String name, String password) {
        super(jdbc(dbAddr, dbName, name, password));
        this.dbAddr = dbAddr;
        this.dbName = dbName;
        this.name = name;
        this.password = password;
    }

    public boolean test() {
        try {
            queryForList("show tables");
            return true;
        } catch (Exception e) {
            _.error("template test error!", e);
            return false;
        }
    }

    public String primaryKey(String tableName) {
        try {
            Map<String, Object> map = queryForMap("select * from INFORMATION_SCHEMA.KEY_COLUMN_USAGE where TABLE_NAME = ? and TABLE_SCHEMA=?",
                                                  tableName, dbName);
            return (String) map.get("COLUMN_NAME");
        } catch (Exception e) {
            _.error("template test error!", e);
            return "";
        }
    }

    @SuppressWarnings("deprecation")
    public Long maxId(String tableName) {
        try {
            return queryForLong("select max(id)+1 from " + tableName);
        } catch (Exception e) {
            _.error("template test error!", e);
            return Long.MAX_VALUE;
        }
    }

    public List<Map<String, Object>> select(String tableName, String where, Object key) {
        try {
            List<Map<String, Object>> list = queryForList(genSelect(tableName, where), key);
            _.info("select " + tableName + ",data=" + list);
            return list;
        } catch (Exception e) {
            _.error("template select error!", e);
        }
        return Lists.newArrayList();
    }

    public int insert(String tableName, List<Map<String, Object>> list) {
        return insert(tableName, null, list);
    }

    public int insert(String tableName, String primaryKey, List<Map<String, Object>> list) {
        int count = 0;
        for (final Map<String, Object> map : list) {
            try {
                List<String> allColumns = allColumns(map, primaryKey);
                if (StringUtils.isNotEmpty(primaryKey) && map.containsKey(primaryKey)) map.remove(primaryKey);
                count += update(genInsert(tableName, allColumns), map.values().toArray(new Object[] {}));
            } catch (Exception e) {
                _.error("template insert error!", e);
            }
        }
        return count;
    }

    public String getDbAddr() {
        return dbAddr;
    }

    public void setDbAddr(String dbAddr) {
        this.dbAddr = dbAddr;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // **********************************************************************************************//
    private static javax.sql.DataSource jdbc(String url, String name, String password) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://" + url + "?useUnicode=true&characterEncoding=UTF-8");
        dataSource.setUsername(name);
        dataSource.setPassword(password);
        return dataSource;
    }

    private static javax.sql.DataSource jdbc(String dbAddr, String dbName, String name, String password) {
        return jdbc(dbAddr + "/" + dbName, name, password);
    }

    public static List<String> allColumns(List<Map<String, Object>> list) {
        if (list == null || list.size() == 0) return Lists.newArrayList();
        return Lists.newLinkedList(list.get(0).keySet());
    }

    public static List<String> allColumns(Map<String, Object> map, String primaryKey) {
        List<String> allColumns = Lists.newLinkedList(map.keySet());
        List<String> columnList = Lists.newLinkedList();
        for (String column : allColumns) {
            if (StringUtils.isEmpty(primaryKey) || !StringUtils.equalsIgnoreCase(primaryKey, column)) columnList.add(column);
        }
        return columnList;
    }

    public static String genSelect(String tableName, String where) {
        return "select * from " + tableName + " where " + where + "=?";
    }

    public static String genInsert(String tableName, List<String> allColumns) {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ").append(tableName).append("(");
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

    public static Map<String, String> map(String[][] keyValues) {
        Map<String, String> data = Maps.newHashMap();
        for (int i = 0; i < keyValues.length; i++) {
            data.put((String) keyValues[i][0], keyValues[i][1]);
        }
        return data;
    }
}
