package com;
import com.model.Users;
import com.dao.UserDao;
public class RunMain {
    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        Users users = new Users();
        users.setUname("chy");//单引号不行
        users.setUserId(1);
        users.setUpwd("123456");
        userDao.insertUsers(users);


    }
}
