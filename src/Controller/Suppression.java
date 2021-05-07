/*因为有很多room所以需要聊天室管理：增加/删除 哈希表
第一个用户登录 什么都没有
只有创建 点击跳转到创建页面
*/
package Controller;

import Model.Room;
import Model.User;
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

@WebServlet(name = "Suppression", urlPatterns = {"/Suppression"})
public class Suppression extends HttpServlet {


    protected void supprimerRoom(HttpServletRequest request, HttpServletResponse response)

            throws ServletException, IOException {

        HttpSession session = request.getSession();
        response.setContentType("text/html;charset=UTF-8");
        String suppr = request.getParameter("suppr");
        Room room1 = RoomManager.getRoom(suppr);

        //3.affiche le message
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<h2>Le salon a été supprimé </h2>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1> Les salons avant : </h1>");
            out.println(RoomManager.toStrings());
//            out.println(room1);
//            out.println(RoomManager.getRoomsTable().get(room1.getId()));
            Set<Integer>key1 = RoomManager.getRoomsTable().keySet();//supprimer les salons invités pour les invités
            Iterator<Integer> itr1 = key1.iterator();
            while (itr1.hasNext()){
                int index = itr1.next();
                if (RoomManager.getRoomsTable().get(index).getTitre().equalsIgnoreCase(room1.getTitre()))
                    RoomManager.getRoomsTable().remove(index);
            }
//            out.println(RoomManager.getRoomsTable().remove(room1.getId()-1));
            User user = (User)session.getAttribute("user");
            user.supprimerRoomCreated(room1);
            user.setStrRoomCreated();//mise à jour les salons crees

            Set<Integer>key = room1.getUsersInvited().keySet();
            Iterator<Integer> itr = key.iterator();
            while (itr.hasNext()){
                int index = itr.next();
                room1.getUsersInvited().get(index).supprimerRoomInvited(room1);
                room1.getUsersInvited().get(index).setStrRoomInvited();
            }

//            RoomManager.getRoomsTable().clear();
            out.println("<h1> Les salons qui restent : </h1>");
            out.println(RoomManager.toStrings());
            out.println("<li><a href='Connexion'>Revient à l'accueil</a></li>");
            out.println("</body>");
            out.println("</html>");
        }
    }

//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        modifierRoom(request, response);
//    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        supprimerRoom(request, response);
    }


}