package com.example.bookmall.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据库工具类 - 使用 DriverManager + properties
 * v0.1 版本不使用连接池
 */
public class DBUtil {
    private static String url;
    private static String user;
    private static String password;

    static {
        try {
            // 加载配置文件
            Properties props = new Properties();
            InputStream is = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
            if (is == null) {
                throw new RuntimeException("无法找到 db.properties 配置文件");
            }
            props.load(is);
            
            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");
            
            // 加载 MySQL 驱动（新版本 JDBC 可以自动注册，但显式加载更保险）
            Class.forName("com.mysql.cj.jdbc.Driver");
            
        } catch (IOException e) {
            throw new RuntimeException("加载数据库配置失败", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("加载 MySQL 驱动失败", e);
        }
    }

    /**
     * 获取数据库连接
     * @return Connection 对象
     * @throws SQLException 连接失败时抛出
     */
    // 修改后的 getConnection 方法
public static Connection getConnection() throws SQLException {
    Connection conn = DriverManager.getConnection(url, user, password);
    // 强制当前数据库会话使用 utf8mb4，解决 Java 驱动与服务器的协商问题
    try (java.sql.Statement stmt = conn.createStatement()) {
        stmt.execute("SET NAMES utf8mb4");
    }
    return conn;
}

    /**
     * 关闭连接
     * @param conn 要关闭的连接
     */
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
