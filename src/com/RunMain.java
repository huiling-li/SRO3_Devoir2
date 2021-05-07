package com;
import com.model.Users;
import com.dao.UserDao_mysql;
public class RunMain {
    public static void main(String[] args) {
        UserDao_mysql userDaoMysql = new UserDao_mysql();
        Users users = new Users();
        users.setUname("chy");//单引号不行
        users.setUserId(1);
        users.setUpwd("123456");
        userDaoMysql.insertUsers(users);


    }
}
