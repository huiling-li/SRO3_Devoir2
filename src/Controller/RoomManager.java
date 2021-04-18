/*因为有很多room所以需要聊天室管理：增加/删除 哈希表
第一个用户登录 什么都没有
只有创建 点击跳转到创建页面
*/
package Controller;

import Model.Room;
import Model.User;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

@WebServlet(name = "RoomManager", urlPatterns = {"/RoomManager"})
public class RoomManager extends HttpServlet {

//    public static Object getUser;
    private static Hashtable<Integer, Room> roomsTable = new Hashtable<Integer, Room>();

    public static Hashtable<Integer, Room> getRoomsTable() {
        return roomsTable;
    }


    public static String toStrings() {
        String allRooms=" ";
        Set<Integer> keys = roomsTable.keySet();
        Iterator<Integer> itr = keys.iterator();
        int index = 0;
        while (itr.hasNext()) {
            index = (int) itr.next();
            allRooms+=roomsTable.get(index).getTitre()+"   ";
            }
        return allRooms;
    }

    protected static Room getRoom(String room) {//titre找room
        Set<Integer> keys = roomsTable.keySet();//所有的键的set集合：所有序号
        Iterator<Integer> itr = keys.iterator();
        int index = 0;
        while (itr.hasNext()) {
            index = (int) itr.next();
            System.out.println(roomsTable.get(index).getTitre());
            if (roomsTable.get(index).getTitre().equalsIgnoreCase(room)) {
                break;
            }
        }
        return roomsTable.get(index);
    }

    public static User getUser(String str) {
        Set<Integer> keys = UserManager.getUsersTable().keySet();//所有的键的set集合：所有序号
//        HttpSession httpSession = request.getSession();
        //Obtaining iterator over set entries
        Iterator<Integer> itr = keys.iterator();
        int trouve = -1;
        int index = 0;
        while (itr.hasNext() && trouve < 0) {
            index = (int) itr.next();
            System.out.println(UserManager.getUsersTable().get(index).getLogin());
            if (UserManager.getUsersTable().get(index).getLogin().equalsIgnoreCase(str)) {
                trouve = index;
                break;
            }
        }
        if (trouve!=-1) return UserManager.getUsersTable().get(index);
        else return null;//没找到
    }

    protected String getTime() {
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(day);
    }
    protected void invitedsAddRoom(String user,Room room) {//给被邀请的加房
//        user = user.strip();
        User invited = getUser(user);
        invited.addRoomsInvited(room);
//        System.out.println(invited.getLogin());
    }

    protected void addRoom(HttpServletRequest request, HttpServletResponse response)//当然可以自定义方法 在生命周期函数内调用
        //post传输调用这个方法 只要搞几个对象进来干活即可
            throws ServletException, IOException {
        //1.获取信息（从validation传过来的 已经被同意了的 的信息） 有新用户输入嘛
//        response.setContentType("text/html;charset=UTF-8");
        String titre = request.getParameter("titre");
        String desc = request.getParameter("desc");
        String duree = request.getParameter("duree");
//        int ok = -1;
        String[] inviteds = request.getParameterValues("invit");
        for(Room room:RoomManager.getRoomsTable().values()){
            if(room.getTitre().equalsIgnoreCase(titre)){
//                request.getRequestDispatcher("Connexion").forward(request,response);//一定要写forward 不然不跳 表单提交 重新提交
                request.setAttribute("er","Echec :titre existe déjà, Veuillez changer");
                request.setAttribute("desc",desc);
                request.setAttribute("duree",duree);
                request.setAttribute("invits",inviteds);
                request.getRequestDispatcher("createRoom.jsp").forward(request,response);//顺序！！跳了就拿不到了
            }
        }
                //Obtaining iterator over set entries


        //2.添加一个用户（值和用户map对应 跟字典一样）被valide过就放心加吧

        HttpSession httpSession = request.getSession();
        User who = (User) httpSession.getAttribute("user");
        Room newRoom=new Room(titre, desc, getTime(), duree,who);
        this.roomsTable.put(this.roomsTable.size(),newRoom);
////
        for(String str: inviteds){
//                String str = "e";
            newRoom.addUsersInvited(getUser(str));
            getUser(str).addRoomsInvited(newRoom);//静态方法？？
            getUser(str).setStrRoomInvited();
        }
        newRoom.setStrInvitedUsers();
        who.addRoomsCreated(newRoom);


//        request.getRequestDispatcher("Connexion").forward(request,response);//一定要写forward 不然不跳

                //3.添加成功，说点废话
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {//表单传来新用户的数据 在这里加入UserTable 并显示
            /* TODO output your page here. You may use following sample code. */
            //遍历：table用itr 也可以这样for


            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Un nouveau room </title>");
            out.println("</head>");
            out.println("<body>");
            for(String str2: inviteds){
                out.println(str2);
//                out.println(getUser(str2).addRoomsInvited(newRoom));
            }
////            out.println("<br>");
            out.println(getUser("c"));
            out.println(getUser("d"));
////            out.println("<br>");
////            out.println(getUser(str));
//            out.println(who.getStrRoomCreated());
////            out.println("<br>");
//            out.println(who.getStrRoomInvited());
////            javax.servlet.http.HttpSession session = (javax.servlet.http.HttpSession) request.getSession();
            out.println("<h1> Un nouveau room est ajouté : </h1>");
//            out.println("自己： "+(User) httpSession.getAttribute("user"));
//            out.println("E: "+getUser("e"));
//            out.println("C: "+getUser("c"));
            out.println(roomsTable.get(roomsTable.size() - 1).toString());//toString应该是HsshTable自带方法
//            //会打印用户信息（自定义好的 想说的信息）
//            out.println("遍历被邀请的房间：怎么就不能"+((User) httpSession.getAttribute("user")).getRoomsInvited());
//            out.println("<br>");
//            out.println("遍历创建房间：怎么就不能"+((User)httpSession.getAttribute("user")).getRoomsCreated());

            out.println("<li><a href='Connexion'>Revient à l'accueil</a></li>");
            out.println("</body>");
            out.println("</html>");
//停一下再跳转？

        }

    }

    public void listRoom(HttpServletRequest request, HttpServletResponse response, Hashtable<Integer, Room> rooms)
    {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Un nouveau salon </title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1> Liste des salons : </h1>");
            out.println("<ol>");

            Set<Integer> keys = rooms.keySet();
            //Obtaining iterator over set entries
            Iterator<Integer> itr = keys.iterator();
            while (itr.hasNext()) {
                out.println("<li>");
                out.println(rooms.get(itr.next()).toString());
                out.println("</li>");
            }
            out.println("</ol>");
            out.println("</body>");
            out.println("</html>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //没有新输入用户 就循环下把原来的用户都输出即可
        //从地址栏输入 而不是从login发来表单信息
        //1.这里只能查看 要修改得先valide 表单提交来才行
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Un nouveau salon </title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1> Liste des salons : </h1>");
//            out.println("<ol>");
//
//            Set<Integer> keys = roomsTable.keySet();
//
//            //Obtaining iterator over set entries
//            Iterator<Integer> itr = keys.iterator();
//            while (itr.hasNext()) {
//                out.println("<li>");
//                out.println(roomsTable.get(itr.next()).toString());
//                out.println("</li>");
//            }
//            out.println("</ol>");
//            out.println("</body>");
//            out.println("</html>");
//        }
        listRoom(request,response,roomsTable);
        request.getRequestDispatcher("login2.jsp").forward(request,response);//注意request是哪里来的 这个是从createRoom跳转来的 只有一次
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        addRoom(request, response);
//        request.getRequestDispatcher("login2.jsp").forward(request,response);
    }


}