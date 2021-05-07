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
import javax.websocket.OnOpen;
import javax.websocket.Session;
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

    protected static Room getRoom(String room) {//cherche le salon selon le titre
        Set<Integer> keys = roomsTable.keySet();
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
        Set<Integer> keys = UserManager.getUsersTable().keySet();
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
        else return null;//pas trouve
    }

    protected String getTime() {
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(day);
    }
    protected void invitedsAddRoom(String user,Room room) {//ajoute le salon invité pour les invités
//        user = user.strip();
        User invited = getUser(user);
        invited.addRoomsInvited(room);
//        System.out.println(invited.getLogin());
    }

    protected void addRoom(HttpServletRequest request, HttpServletResponse response)
        //appelé par doPost
            throws ServletException, IOException {
        //1.récupère les infos transfertes
//        response.setContentType("text/html;charset=UTF-8");
        String titre = request.getParameter("titre");
        String desc = request.getParameter("desc");
        String duree = request.getParameter("duree");
//        int ok = -1;
        String[] inviteds = request.getParameterValues("invit");
        for(Room room:RoomManager.getRoomsTable().values()){
            if(room.getTitre().equalsIgnoreCase(titre)){
                request.setAttribute("er","Echec :titre existe déjà, Veuillez changer");
                request.setAttribute("desc",desc);
                request.setAttribute("duree",duree);
                request.setAttribute("invits",inviteds);
                request.getRequestDispatcher("createRoom.jsp").forward(request,response);
            }
        }

        //2.ajoute un user valiede
        HttpSession httpSession = request.getSession();
        User who = (User) httpSession.getAttribute("user");
        Room newRoom=new Room(titre, desc, getTime(), duree,who);
        this.roomsTable.put(this.roomsTable.size(),newRoom);
////
        for(String str: inviteds){
//                String str = "e";
            newRoom.addUsersInvited(getUser(str));
            getUser(str).addRoomsInvited(newRoom);
            getUser(str).setStrRoomInvited();
        }
        newRoom.setStrInvitedUsers();
        who.addRoomsCreated(newRoom);


                //3.réusssi à 'ajouter, affiche les infos
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Un nouveau room </title>");
            out.println("</head>");
            out.println("<body>");
//            for(String str2: inviteds){
//                out.println(str2);
//                out.println(getUser(str2).addRoomsInvited(newRoom));
//            }
////            out.println("<br>");
//            out.println(getUser("c"));
//            out.println(getUser("d"));
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
            out.println(roomsTable.get(roomsTable.size() - 1));//toString应该是HsshTable自带方法
//            out.println("遍历被邀请的房间：怎么就不能"+((User) httpSession.getAttribute("user")).getRoomsInvited());
//            out.println("<br>");
//            out.println("遍历创建房间：怎么就不能"+((User)httpSession.getAttribute("user")).getRoomsCreated());

            out.println("<li><a href='Connexion'>Revient à l'accueil</a></li>");
            out.println("</body>");
            out.println("</html>");
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
        request.getRequestDispatcher("login2.jsp").forward(request,response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        addRoom(request, response);
//        request.getRequestDispatcher("login2.jsp").forward(request,response);
    }


}