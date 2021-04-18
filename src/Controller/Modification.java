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

@WebServlet(name = "Modification", urlPatterns = {"/Modification"})
public class Modification extends HttpServlet {


    protected void modifierRoom(HttpServletRequest request, HttpServletResponse response)//当然可以自定义方法 在生命周期函数内调用
        //post传输调用这个方法 只要搞几个对象进来干活即可
            throws ServletException, IOException {
        int ok = -1;

        HttpSession session = request.getSession();

        response.setContentType("text/html;charset=UTF-8");

        if(((User)session.getAttribute("user")).getRoomsCreated().isEmpty()==true){
            request.setAttribute("er","Aucun salon disponible à modifier, Veuillez en créer un");
            request.getRequestDispatcher("Connexion").forward(request,response);//顺序！！跳了就拿不到了
        }
        String modif = request.getParameter("modif");//第二次再选就没有 只是第一次的？
        String titre = request.getParameter("titre");
        String desc = request.getParameter("desc");
        String duree = request.getParameter("duree");
        String[] inviteds = request.getParameterValues("invit");
//        String TypeName = request.getParameter("TypeName");
//        System.out.println(TypeName);
        //2.添加一个用户（值和用户map对应 跟字典一样）被valide过就放心加
//        User who = RoomManager.getUser(request, response);
        Room roomModif = RoomManager.getRoom(modif);
//        Room room = (Room) session.getAttribute("room");
//        Room room1 = RoomManager.getRoom(modif);
        for(Room room:RoomManager.getRoomsTable().values()){
            if(room.getTitre().equalsIgnoreCase(titre)){
//                request.getRequestDispatcher("Connexion").forward(request,response);//一定要写forward 不然不跳 表单提交 重新提交
                request.setAttribute("err","Echec :titre existe déjà, Veuillez changer");
                request.setAttribute("desc",desc);
                request.setAttribute("duree",duree);
                request.setAttribute("invits",inviteds);
                request.getRequestDispatcher("modif.jsp").forward(request,response);//顺序！！跳了就拿不到了
            }
        }
        if(modif==null){
            request.setAttribute("desc",desc);
            request.setAttribute("duree",duree);
            request.setAttribute("invits",inviteds);
            request.setAttribute("err","Echec :Aucun salon choisi, Veuillez choisir");
            request.getRequestDispatcher("modif.jsp").forward(request,response);//顺序！！跳了就拿不到了
        }
        else {
        //3.添加成功，说点废话
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {//表单传来新用户的数据 在这里加入UserTable 并显示
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Le salon a été modifié </title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1> Le salon avant : </h1>");
//            out.println(TypeName);
            out.println(roomModif.toString());

            Set<Integer> key = roomModif.getUsersInvited().keySet();//之前邀请的删掉
            Iterator<Integer> itr = key.iterator();
            while (itr.hasNext()) {
                roomModif.getUsersInvited().get(itr.next()).supprimerRoomInvited(roomModif);
                roomModif.getUsersInvited().get(itr.next()).setStrRoomInvited();
            }

            roomModif.modifierRoom(titre, desc, duree, inviteds);//这下总算改完了吧
//            Set<Integer> keys = UserManager.getUsersTable().keySet();//更新信息
//            //Obtaining iterator over set entries
//            Iterator<Integer> itr = keys.iterator();
//            while(itr.hasNext()){
//                UserManager.getUsersTable().get(itr.next()).setStrRoomInvited();
//                UserManager.getUsersTable().get(itr.next()).setStrRoomCreated();//更新一下
//            }
//

            response.setContentType("text/html;charset=UTF-8");
            User user = (User) session.getAttribute("user");
//            user.supprimerRoomCreated(room1);
//            Set<Integer>key = roomModif.getUsersInvited().keySet();
//            Iterator<Integer> itr = key.iterator();
//            while (itr.hasNext()){
//                int index = itr.next();
//                room1.getUsersInvited().get(index).supprimerRoomInvited(room1);
//            }

            out.println("<h1> Le salon a été modifié comme suivant : </h1>");
            out.println(roomModif.toString());//toString应该是HsshTable自带方法
            out.println("<li><a href='Connexion'>Revient à l'accueil</a></li>");
            //会打印用户信息（自定义好的 想说的信息）
            out.println("</body>");
            out.println("</html>");

        }
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
        modifierRoom(request, response);
    }


}