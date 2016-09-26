/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.dbmigrate.support;

import java.util.Properties;

import com.lamfire.pandora.*;
import com.lamfire.utils.PropertiesUtils;

/**
 * @author zxc Sep 26, 2016 11:39:24 AM
 */
public class PandoraDataStore {

    private final Pandora           pandora;
    private static final Properties pro = PropertiesUtils.load("level-db-data.properties", PandoraDataStore.class);
    private static String           storeDir;
    private static String           name;

    static {
        storeDir = pro.getProperty("store.dir");
        name = pro.getProperty("data.name");
    }

    private PandoraDataStore() {
        PandoraMaker maker = new PandoraMaker(storeDir, name);
        maker.createIfMissing(true);
        pandora = maker.make();
    }

    private static class SingletonClassInstance {

        private static final PandoraDataStore instance = new PandoraDataStore();
    }

    public static PandoraDataStore getInstance() {
        return SingletonClassInstance.instance;
    }

    public FireIncrement getIncrement(String name) {
        return pandora.getFireIncrement(name);
    }

    public FireQueue getQueue(String name) {
        return pandora.getFireQueue(name);
    }

    public FireMap getMap(String name) {
        return pandora.getFireMap(name);
    }

    public FireList getList(String name) {
        return pandora.getFireList(name);
    }

    public FireRank getRank(String name) {
        return pandora.getFireRank(name);
    }

    public FireSet getSet(String name) {
        return pandora.getFireSet(name);
    }
}
