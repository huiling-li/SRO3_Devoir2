/*
页面的作用？类的作用？
数据共享/存储：sessionAttribute
用户登录后跳转到首页：（可以加查找聊天室功能？）
显示自己创建的聊天室：显示聊天室链接（一些信息/按钮跳转）+ 被邀请的聊天室
1.创建自己聊天室：跳转到表单提交页面 时间自动生成 邀请的人从usermanager里选
2.自己创建的聊天室有修改/删除按钮：显示列表，按钮删除（确定页面？）
点链接跳转到新标签页：聊天界面 储存用户 消息记录 ...
来得迟就看不到信息？


妈的其实跟c++没什么两样 都是多搞几个对象就行了
编程应该是有一点点思路即可进行下去的 不用现成的都给你弄好
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.RoomManager;
import Controller.UserManager;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;


public class User {
    private String lastName;
    private String firstName;
    private String login;
    private String gender;
    private int id;
    private String role;
    private static int autoIncrement=0;//只是为了id自动加1
    private Hashtable<Integer, Room> roomsCreated= new Hashtable<Integer, Room>();
    private Hashtable<Integer, Room> roomsInvited= new Hashtable<Integer, Room>();
    private String strsRoomInvited = "";
    private String strsRoomCreated = "";
    private String strRoomInvited = "";
    private String strRoomCreated = "";

//    public Room getCreate() {

    public String getStrsRoomCreated() {
        return strsRoomCreated;
    }
//        return this.create;

    public String getStrsRoomInvited() {
        return strsRoomInvited;
    }
//    }
//
//    public void setCreate(Room create) {
//        this.create = create;
//    }

    public Hashtable<Integer, Room> getRoomsCreated() {
        return this.roomsCreated;
    }
//    public void modifierRoomInvited(Room room) {
//        Set<Integer> keys = this.roomsInvited.keySet();//所有的键的set集合：所有序号
//        //Obtaining iterator over set entries
//        Iterator<Integer> itr = keys.iterator();
//        while (itr.hasNext()) {
//            int index = (int) itr.next();//2.遍历序号 从index找User 再找Login 看看等不等于输入的username
//            if (roomsInvited.get(index).equals(room)){
//                roomsInvited.remove(index);
//                break;
//            }
//        }
//    }
    public void supprimerRoomInvited(Room room) {
        Set<Integer> keys = this.roomsInvited.keySet();//所有的键的set集合：所有序号
        //Obtaining iterator over set entries
        Iterator<Integer> itr = keys.iterator();
        while (itr.hasNext()) {
            int index = (int) itr.next();//2.遍历序号 从index找User 再找Login 看看等不等于输入的username
            if (roomsInvited.get(index).equals(room)){
                roomsInvited.remove(index);
                break;
            }
        }
    }
    public void supprimerRoomCreated(Room room) {
        Set<Integer> keys = this.roomsCreated.keySet();//所有的键的set集合：所有序号
        //Obtaining iterator over set entries
        Iterator<Integer> itr = keys.iterator();
        while (itr.hasNext()) {
            int index = (int) itr.next();//2.遍历序号 从index找User 再找Login 看看等不等于输入的username
            if (roomsCreated.get(index).equals(room)){
                roomsCreated.remove(index);
                break;
            }
        }
    }

    public  Hashtable<Integer, Room> getRoomsInvited() {
        return this.roomsInvited;
    }



    public void addRoomsCreated(Room newroom) {
//roomsCreated.put()
        this.roomsCreated.put(this.roomsCreated.size(),newroom);
//        this.addStrRoomCreated(newroom);
//        this.addStrsRoomCreated(newroom);
        this.setStrRoomCreated();
    }

    public void addRoomsInvited(Room newroom)
    {
        this.roomsInvited.put(this.roomsInvited.size(),newroom);
//        this.addStrRoomInvited(newroom);
//        this.addStrsRoomInvited(newroom);
        this.setStrRoomInvited();
    }


    public static int getAutoIncrement() {
        return autoIncrement;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User(String lastName, String firstName, String login, int key, String gender, String pwd) {
        autoIncrement++;
        this.lastName = lastName;
        this.firstName = firstName;
        this.login = login;
        this.gender = gender;
        this.pwd = pwd;
        this.id=User.autoIncrement;
//        this.
    }

    public int getId() {
        return id;
    }


    public void setLogin(String login) {
        this.login = login;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getLogin() {
        return login;
    }

    public String getPwd() {
        return pwd;
    }
    private String pwd;

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void finalize(){
        autoIncrement--;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.lastName);//得到名字的hash值后加盐？
        hash = 97 * hash + Objects.hashCode(this.firstName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;//final值不可变
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        return true;
    }



    public User(String lastName, String firstName){
        this.lastName = lastName;
        this.firstName = firstName;//主键就是名字
    }
    public void addStrsRoomCreated(Room newroom) {
//        Set<Integer> keys = this.roomsInvited.keySet();//所有的键的set集合：所有序号
//        //Obtaining iterator over set entries
//        Iterator<Integer> itr = keys.iterator();
//        while (itr.hasNext()) {
//            int index = (int) itr.next();//2.遍历序号 从index找User 再找Login 看看等不等于输入的username
        this.strsRoomCreated += newroom+"\n";

//        return this.strsRoomCreated;
    }
    public void addStrsRoomInvited(Room newroom) {
//        Set<Integer> keys = this.roomsInvited.keySet();//所有的键的set集合：所有序号
//        //Obtaining iterator over set entries
//        Iterator<Integer> itr = keys.iterator();
//        while (itr.hasNext()) {
//            int index = (int) itr.next();//2.遍历序号 从index找User 再找Login 看看等不等于输入的username
        this.strsRoomInvited += newroom + "\n";

//        return this.strsRoomInvited;
    }
    public String addStrRoomInvited(Room newroom) {
//        Set<Integer> keys = this.roomsInvited.keySet();//所有的键的set集合：所有序号
//        //Obtaining iterator over set entries
//        Iterator<Integer> itr = keys.iterator();
//        while (itr.hasNext()) {
//            int index = (int) itr.next();//2.遍历序号 从index找User 再找Login 看看等不等于输入的username
            this.strRoomInvited += newroom.getTitre();

        return this.strRoomInvited;
    }
    public void addStrRoomCreated(Room newroom) {
//        Set<Integer> keys = this.roomsCreated.keySet();//所有的键的set集合：所有序号
//        //Obtaining iterator over set entries
//        Iterator<Integer> itr = keys.iterator();
//        while (itr.hasNext()) {
//            int index = itr.next();//2.遍历序号 从index找User 再找Login 看看等不等于输入的username
            this.strRoomCreated += newroom.getTitre();

    }
    public String getStrRoomCreated() {//不能每次要的时候都加一遍

        return this.strRoomCreated;
    }
    public String getStrRoomInvited() {//不能每次要的时候都加一遍

        return this.strRoomInvited;
    }
    public void setStrRoomCreated() {
        strRoomCreated = "";
        if(roomsCreated.isEmpty()==false) {
            Set<Integer> keys = this.getRoomsCreated().keySet();//所有的键的set集合：所有序号
            //Obtaining iterator over set entries
            Iterator<Integer> itr = keys.iterator();
            while (itr.hasNext()) {
                int index = (int) itr.next();//2.遍历序号 从index找User 再找Login 看看等不等于输入的username
                this.strRoomCreated += this.getRoomsCreated().get(index).getTitre()+" ";

            }
        }
    }
    public void setStrRoomInvited() {//可以更新 一开始一条条加的不行
        strRoomInvited = "";
        if (roomsInvited.isEmpty()==false) {//为空执行可能会报错
            Set<Integer> keys = this.roomsInvited.keySet();//所有的键的set集合：所有序号
            //Obtaining iterator over set entries
            Iterator<Integer> itr = keys.iterator();
            while (itr.hasNext()) {
                int index = (int) itr.next();//2.遍历序号 从index找User 再找Login 看看等不等于输入的username
                this.strRoomInvited += this.roomsInvited.get(index).getTitre()+" ";
            }
        }
    }

    public void setStrsRoomCreated() {
        strsRoomCreated = "";
        Set<Integer> keys = this.getRoomsCreated().keySet();//所有的键的set集合：所有序号
        //Obtaining iterator over set entries
        Iterator<Integer> itr = keys.iterator();
        while (itr.hasNext()) {
            int index = (int) itr.next();//2.遍历序号 从index找User 再找Login 看看等不等于输入的username
            this.strsRoomCreated += this.getRoomsCreated().get(index);

        }
    }
    public void setStrsRoomInvited() {//可以更新 一开始一条条加的不行
        strsRoomInvited = "";
        Set<Integer> keys = this.roomsInvited.keySet();//所有的键的set集合：所有序号
        //Obtaining iterator over set entries
        Iterator<Integer> itr = keys.iterator();
        while (itr.hasNext()) {
            int index = (int) itr.next();//2.遍历序号 从index找User 再找Login 看看等不等于输入的username
            this.strsRoomInvited += this.roomsInvited.get(index);
        }
    }

    @Override
    public String toString() {

        return "User{" + "lastName=" + lastName + ", firstName=" + firstName + ""
                + ", login=" + login  + ", gender=" + gender + ","
                + " pwd=" + pwd + " invitedroom ="+getStrRoomInvited()+" createdRoom ="+getStrRoomCreated()+'}';
    }



}