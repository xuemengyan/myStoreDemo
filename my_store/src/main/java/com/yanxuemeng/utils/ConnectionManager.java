package com.yanxuemeng.utils;

import com.yanxuemeng.utils.C3P0Utils;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {
    // 定义一个集合,这个集合是存Connection对象
    // private static HashMap<Thread, Connection> map= new HashMap<>();
    private static ThreadLocal<Connection> tl = new ThreadLocal<>();

    public static Connection getConnection() throws SQLException {
        // 第一个调用该 方法,集合中内容是空的
        // Connection conn = map.get(Thread.currentThread());
        Connection conn = tl.get();

        if (conn == null) {
            // 如果集合中没有连接,则获取一个连接存入Map
            conn = C3P0Utils.getConnection();
            // map.put(Thread.currentThread(), conn);
            tl.set(conn);
        }
        return conn;
    }

    // 开启事务
    public static void begin() throws SQLException {
        ConnectionManager.getConnection().setAutoCommit(false);
    }

    // 提交事务
    public static void commit() throws SQLException {
        ConnectionManager.getConnection().commit();
    }

    // 回滚事务
    public static void rollback() throws SQLException {
        ConnectionManager.getConnection().rollback();
    }
    public static void close() throws SQLException {
        tl.remove();
        ConnectionManager.getConnection().setAutoCommit(true);
        ConnectionManager.getConnection().close();
    }
}
