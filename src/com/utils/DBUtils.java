package com.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
public class DBUtils {

    private static Properties properties = null;

    static {
        InputStream in = DBUtils.class.getClassLoader().getResourceAsStream("db.properties");
        properties = new Properties();
        try {
            properties.load(in);//配置对象就有这个方法
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**然后回车就出来这个注释
     *
     * @return
     * 工具类：静态方法
     */
    public static Connection getConnection(){//Connection对象：可以搞来用
      Connection connection = null;
      //得到
      String dbUrl = properties.getProperty("dbUrl");
      String jdbcName = properties.getProperty("jdbcName");
      String dbName = properties.getProperty("dbName");
      String dbPwd = properties.getProperty("dbPwd");
        try {
            //加载驱动 driver
            Class.forName(jdbcName);
            //得到数据库连接
            connection = DriverManager.getConnection(dbUrl,dbName,dbPwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void close(ResultSet resultSet, PreparedStatement preparedStatement,Connection connection){
        //判断资源是否为空，不为空关闭资源
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null){
                preparedStatement.close();
            }
            if (connection != null){
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 测一下
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(getConnection());
    }
}









