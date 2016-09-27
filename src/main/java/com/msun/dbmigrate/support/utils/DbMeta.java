/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.dbmigrate.support.utils;

import com.lamfire.json.JSON;

/**
 * @author zxc Sep 26, 2016 12:11:43 PM
 */
public class DbMeta {

    private String id;      // 唯一id
    private String dbAddr;  // 数据库地址
    private String dbName;  // 数据库名称
    private String name;    // 数据库登录名
    private String passwd;  // 数据库密码
    private long   createAt; // 创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
