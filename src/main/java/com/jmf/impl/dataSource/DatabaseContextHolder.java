package com.jmf.impl.dataSource;

/**
 * Description:
 * Author:        LiuZhuang
 * Create Date:   2019/5/20 15:36
 */
public class DatabaseContextHolder {

    private static final ThreadLocal<DatabaseType> contextHolder = new ThreadLocal<>();


    public static void setDatabaseType(DatabaseType type) {
        contextHolder.set(type);
    }

    public static DatabaseType getDatabaseType() {
        return contextHolder.get();
    }
}
