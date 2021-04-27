package com.dao;
import com.model.Users;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDao {
    public void insertUsers(Users users){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;//要在外面 生命周期
        PreparedStatement ps = null;
        try {
        //所有对象都来自sql包下（有好多同名的）选
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys","root","74738205");
        String sql="insert into chy(userId,uname,upwd)values(?,?,?)";
        //sql容器 装载sql语句
        ps = connection.prepareStatement(sql);
        //为？（占位符）赋值 可选操作步骤
        ps.setInt(1,users.getUserId());
        ps.setString(2,users.getUname());
        ps.setString(3,users.getUpwd());
        ps.execute();}
        catch (SQLException e)
        {
            e.printStackTrace();
        }finally {
        if (ps!=null){
            try {
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (connection!=null){
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        }

    }
}
















