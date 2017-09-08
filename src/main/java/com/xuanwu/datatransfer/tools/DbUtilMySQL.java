package com.xuanwu.datatransfer.tools;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * MySQL数据库工具，单例，持久连接
 *
 * @author Bob
 */
public class DbUtilMySQL {
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private static String DBClassName = null;
    private static String DBUrl = null;
    private static String DBName = null;
    private static String DBUser = null;
    private static String DBPassword = null;

    private static DbUtilMySQL instance = null;

    private static final Logger logger = LoggerFactory.getLogger(DbUtilMySQL.class);

    /**
     * 私有的构造
     */
    private DbUtilMySQL() {
        loadConfig();
    }

    /**
     * 获取实例，线程安全
     *
     * @return
     */
    public static synchronized DbUtilMySQL getInstance() {

        if (instance == null) {
            instance = new DbUtilMySQL();
        }
        return instance;
    }

    /**
     * 从配置文件加载设置数据库信息
     */
    private void loadConfig() {
        try {
            DBClassName = "com.mysql.jdbc.Driver";
            DBUrl =null;
            DBName = null;
            DBUser = null;
            DBPassword = null;

            Class.forName(DBClassName);
        } catch (Exception e) {

            logger.error(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * 获取连接，线程安全
     *
     * @return
     * @throws SQLException
     */
    public synchronized Connection getConnection() throws SQLException {
        String user = "";
        String password = "";
        try {
            DESPlus des = new DESPlus();
            user = des.decrypt(DBUser);
            password = des.decrypt(DBPassword);
        } catch (Exception e) {
            logger.error(PropertyUtil.getProperty("ds.ui.database.to.err.decode") + e.toString());
            e.printStackTrace();
        }

        // 当connection失效时重新获取
        if (connection == null || connection.isValid(10) == false) {
            // "jdbc:mysql://localhost/pxp2p_branch"
            connection = DriverManager.getConnection(
                    "jdbc:mysql://" + DBUrl + "/" + DBName + "?useUnicode=true&characterEncoding=utf8", user, password);
            // 把事务提交方式改为手工提交
            connection.setAutoCommit(false);
        }

        if (connection == null) {
            logger.error("Can not load MySQL jdbc and get connection.");
        }
        return connection;
    }

    /**
     * 测试连接，线程安全 参数从配置文件获取
     *
     * @return
     * @throws SQLException
     */
    public synchronized Connection testConnection() throws SQLException {
        loadConfig();
        // "jdbc:mysql://localhost/pxp2p_branch"
        String user = "";
        String password = "";
        try {
            DESPlus des = new DESPlus();
            user = des.decrypt(DBUser);
            password = des.decrypt(DBPassword);
        } catch (Exception e) {
            logger.error(PropertyUtil.getProperty("ds.ui.database.to.err.decode") + e.toString());
            e.printStackTrace();
        }
        connection = DriverManager.getConnection("jdbc:mysql://" + DBUrl + "/" + DBName, user, password);
        // 把事务提交方式改为手工提交
        connection.setAutoCommit(false);

        if (connection == null) {
            logger.error("Can not load MySQL jdbc and get connection.");
        }
        return connection;
    }

    /**
     * 测试连接，线程安全 参数从入参获取
     *
     * @return
     * @throws SQLException
     */
    public synchronized Connection testConnection(String DBUrl, String DBName, String DBUser, String DBPassword)
            throws SQLException {
        loadConfig();
        // "jdbc:mysql://localhost/pxp2p_branch"
        connection = DriverManager.getConnection("jdbc:mysql://" + DBUrl + "/" + DBName, DBUser, DBPassword);
        // 把事务提交方式改为手工提交
        connection.setAutoCommit(false);

        if (connection == null) {
            logger.error("Can not load MySQL jdbc and get connection.");
        }
        return connection;
    }

    /**
     * 获取数据库声明，私有，线程安全
     *
     * @throws SQLException
     */
    private synchronized void getStatement() throws SQLException {
        getConnection();
        // 仅当statement失效时才重新创建
        if (statement == null || statement.isClosed() == true) {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        }
    }

    /**
     * 关闭（结果集、声明、连接），线程安全
     *
     * @throws SQLException
     */
    public synchronized void close() throws SQLException {
        if (resultSet != null) {
            resultSet.close();
            resultSet = null;
        }
        if (statement != null) {
            statement.close();
            statement = null;
        }
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

    /**
     * 执行查询，线程安全
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    public synchronized ResultSet executeQuery(String sql) throws SQLException {
        getStatement();
        if (resultSet != null && resultSet.isClosed() == false) {
            resultSet.close();
        }
        resultSet = null;
        resultSet = statement.executeQuery(sql);
        return resultSet;
    }

    /**
     * 执行更新，线程安全
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    public synchronized int executeUpdate(String sql) throws SQLException {
        int result = 0;
        getStatement();
        result = statement.executeUpdate(sql);
        return result;
    }

}
