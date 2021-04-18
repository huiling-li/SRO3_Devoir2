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


    protected void supprimerRoom(HttpServletRequest request, HttpServletResponse response)//当然可以自定义方法 在生命周期函数内调用
        //post传输调用这个方法 只要搞几个对象进来干活即可
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        response.setContentType("text/html;charset=UTF-8");
        String suppr = request.getParameter("suppr");
        Room room1 = RoomManager.getRoom(suppr);

        //3.添加成功，说点废话
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {//表单传来新用户的数据 在这里加入UserTable 并显示
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
            out.println(RoomManager.getRoomsTable().remove(room1.getId()-1));//看结果 别忘了减1 打印出来看一下
            //user那边也要剪掉
            //不刷新？？
            User user = (User)session.getAttribute("user");
            user.supprimerRoomCreated(room1);
            Set<Integer>key = room1.getUsersInvited().keySet();
            Iterator<Integer> itr = key.iterator();
            while (itr.hasNext()){
                int index = itr.next();
                room1.getUsersInvited().get(index).supprimerRoomInvited(room1);
            }

//            RoomManager.getRoomsTable().clear();
            out.println("<h1> Les salons qui restent : </h1>");
            out.println(RoomManager.getRoomsTable().keySet()+RoomManager.toStrings());  //    不是静态就不能调用？？
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