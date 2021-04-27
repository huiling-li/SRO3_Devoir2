package com.servlet;

import Model.User;
import Model.user_essai;
import com.utils.DBUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uname = request.getParameter("uname");
        String upwd = request.getParameter("upwd");
        //设置响应类型
        response.setContentType("text/html;charset=UTF-8");
        //得到输出字符流
        PrintWriter out = response.getWriter();
//        String uname = request.getParameter("uname");
        if(isEmpty(uname)||isEmpty(upwd)){
            out.write("<h2>username et password ne peuvent pas être vides!</h2>");
            return;
        }
        user_essai user = findUserByUnameAndUpwd(uname,upwd);
        if (user!=null){
            out.write("<h2>bienvenue"+uname+"+</h2>");//可以这么拼的
        }else {
            out.write("<h2>username or password pas vrai</h2>");
        }
    }

    /**
     * ！！！数据库查询
     * @param uname
     * @param upwd
     * @return
     */
    private user_essai findUserByUnameAndUpwd(String uname,String upwd){
        user_essai user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtils.getConnection();
            String sql = "select * from sys where uname = ? and upwd = ?";
            //预编译sql语句
            preparedStatement = connection.prepareStatement(sql);
            //设置参数，下标从1开始
            preparedStatement.setString(1,uname);
            preparedStatement.setString(2,upwd);
            //执行查询,返回resultSet 结果集
            resultSet = preparedStatement.executeQuery();//不需要传sql了
            while (resultSet.next()){//最多只会循环1次，if/while都行
                //如果拿到了，接设置到user对象里
                user = new user_essai();
                user.setUserId(resultSet.getInt("userId"));
                user.setUname(resultSet.getString("uname"));
                user.setUpwd(resultSet.getString(upwd));//这样也可以 反正是一样的


            }


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭资源
            DBUtils.close(resultSet,preparedStatement,connection);
        }
        return user;
    }
    public boolean isEmpty(String str){
        if(str == null||"".equals(str.trim())){
            return true;
        }else return false;
    }
}
