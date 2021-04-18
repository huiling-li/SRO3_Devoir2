package Model;

//面向对象就是这样 搞几个类出来
import Controller.RoomManager;
import Controller.UserManager;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

public class Room {
    private static int autoIncrement=0;
    private int id;
    private String titre;
    private String desc;
    private String date;

    public static Hashtable<Integer, User> getUsersInvited() {
        return usersInvited;
    }

    private String duree;//时间获取
    private User creator;//这俩值在用户输入的时候确定
    private static Hashtable<Integer, User> usersInvited= new Hashtable<Integer, User>();
    private static Hashtable<Integer,User> allUsers = new Hashtable<>();
    private String strInvitedUsers="";

    public static Hashtable<Integer, User> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers() {
        Room.allUsers = this.usersInvited;
        Room.allUsers.put(allUsers.size(),this.creator);
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", desc='" + desc + '\'' +
                ", date='" + date + '\'' +
                ", duree='" + duree + '\'' +
                ", creator=" + creator.getLogin() +
                ", invitedUsers='" + strInvitedUsers + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }
    public void modifierRoom(String titre, String desc, String duree,String[] users){
        setTitre(titre);
        setDesc(desc);
        setDuree(duree);
        usersInvited.clear();//清空，再设一次
        strInvitedUsers = "";
        for (String str :users){
            addUsersInvited(RoomManager.getUser(str));
        }

    }
    public Room(String titre, String desc, String date, String duree, User creator) {
        autoIncrement++;
        this.titre = titre;
        this.desc = desc;
        this.date = date;
        this.duree = duree;
        this.creator = creator;
        this.id=Room.autoIncrement;
        }
//静态方法怎么就不能设置值了呢
    public void addUsersInvited(User user) {
//        user=user.strip();
        this.strInvitedUsers += user.getLogin();//不能直接+=因为一开始是null
//        String[] inviteds = users.split(" ");
//        Set<Integer> keys = UserManager.getUsersTable().keySet();//所有的键的set集合：所有序号
        //Obtaining iterator over set entries
//        Iterator<Integer> itr = keys.iterator();
//        while (itr.hasNext()) {
//            int index = (int) itr.next();//2.遍历序号 从index找User 再找Login 看看等不等于输入的username
//            if(user.equalsIgnoreCase(UserManager.getUsersTable().get(index).getLogin())){
        usersInvited.put(usersInvited.size(),user);
//                break;
//            }
//
//        }
//        setAllUsers();//每次都加自己
//        return usersInvited;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return date;
    }

    public String getDuree() {
        return duree;
    }
    public void setDuree(String duree) {
        this.duree = duree;
    }


}

