
/*
3.已经有些用户 现在要登录
attention：一定从connexion.html跳转来
你从地址栏直接进来肯定echec啦（不输入用户名密码就想进来啊？呵呵）
第一个登录的用户必须是lounis(管理员) 然后才能继续

用户名是邮箱！！！！
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 从不同地方跳转来 处理方式能不能不一样？
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
        import java.util.Hashtable;
        import java.util.Iterator;
        import java.util.Set;


@WebServlet(name = "Connexion", urlPatterns = {"/Connexion"})
public class Connexion extends HttpServlet {

    public void listRoom(HttpServletRequest request, HttpServletResponse response, Hashtable<Integer, Room> rooms,Hashtable<Integer, Room> roomsInvited)
    {
        HttpSession session = request.getSession();
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Un nouveau salon </title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Hello " + session.getAttribute("login") + "</h1>");
            ((User)session.getAttribute("user")).setStrRoomCreated();
            ((User)session.getAttribute("user")).setStrRoomInvited();
            out.println(((User)session.getAttribute("user")));
            out.println("<nav> <ul>");
//            out.println(" <li>Connected</li>");
            out.println("<li><a href='createRoom.jsp'>Créer un nouveau salon</a></li>");
            out.println(" <li><a href='UserManager'>Afficher la liste des utilisateurs</a></li>");//点击会跳转
            out.println(" <li><a href='RoomManager'>Afficher la liste des salons</a></li>");
            out.println(" <li><a href='modif.jsp'>Modifier vos salons crées</a></li>");
            if(request.getAttribute("er")!=null)
                out.println(request.getAttribute("er"));
            out.println(" <li><a href='supprimer.jsp'>Supprimer vos salons crées</a></li>");
            out.println(" <li><a href='Deconnexion'>Déconnecter</a></li>");


            out.println("</ul>");
            out.println("</nav>");
            out.println("<h1> Liste des salons que vous avez crées : </h1>");


            Set<Integer> keys = rooms.keySet();

            //Obtaining iterator over set entries
            Iterator<Integer> itr = keys.iterator();
            Set<Integer> keys2 = ((User) session.getAttribute("user")).getRoomsInvited().keySet();
            Iterator<Integer> itr2 = keys2.iterator();
//            String allSalonModif = "";
//            out.println(" <script type=>");
            while (itr.hasNext()) {

//                session.setAttribute("user",RoomManager.getUser(request,response));
                //html 超链接 传参数 就可以知道是哪个了
//                String url="jjj";
//                String Url="Selection?room=<%=r%>";
                out.println("<ol>");
                Room r = rooms.get(itr.next());
                out.println(r.toString());
//                out.println(" <li><a href='Selection?room="+rooms.get(itr.next()).getTitre()+"'>Veuillez choisir une opération</a></li>");

//            ((User) session.getAttribute("user")).setStrsRoomCreated(response);
//
//                out.println(((User)session.getAttribute("user")).getStrsRoomCreated());
//                out.println("遍历创建房间：怎么就不能"+((User)session.getAttribute("user")).getRoomsCreated());
//                out.println(r.toString());
                out.println("</ol>");
//                allSalonModif = allSalonModif + r.getTitre() + "\n";
//                session.setAttribute("room",r);
            }
//                out.println("</a></li>");
//            session.setAttribute("modif",allSalonModif);//要更新

            out.println("<h1> Liste des salons où vous êtes invité  : </h1>");
            out.println("<ol>");
            ((User) session.getAttribute("user")).setStrsRoomInvited();
              out.println(((User) session.getAttribute("user")).getStrsRoomInvited());
//              out.println("遍历被邀请的房间：怎么就不能"+((User) session.getAttribute("user")).getRoomsInvited());
            while (itr2.hasNext()) {
                Room ri = roomsInvited.get(itr2.next());
                out.println(ri.toString());
            }
            out.println("</ol><br>");
            out.println("</body>");
            out.println("</html>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//这次从地址栏和post表单进来都可以了
        // Vérifier si le login existe1.取出所有序号
        Set<Integer> keys = UserManager.getUsersTable().keySet();//所有的键的set集合：所有序号
        //Obtaining iterator over set entries
        Iterator<Integer> itr = keys.iterator();
        int trouve = -1;
        while (itr.hasNext() && trouve < 0) {
            int index = (int) itr.next();//2.遍历序号 从index找User 再找Login 看看等不等于输入的username
            System.out.println(UserManager.getUsersTable().get(index).getLogin());
            if (UserManager.getUsersTable().get(index).getLogin().equalsIgnoreCase(request.getParameter("username"))) {
                trouve = index;
                break;
            }
        }
        if (trouve < 0) {//3.循环过后还是没找到 那就说：失败
            response.setContentType("text/html;charset=UTF-8");
            request.setAttribute("uname",request.getParameter("username"));
            request.setAttribute("upwd",request.getParameter("password"));
            request.setAttribute("msg","Echec :Username introuvable!");
            request.getRequestDispatcher("login2.jsp").forward(request,response);
//            try (PrintWriter out = response.getWriter()) {
//                /* TODO output your page here. You may use following sample code. */
//                out.println("<!DOCTYPE html>");
//                out.println("<html>");
//                out.println("<head>");
//                out.println("<title>Servlet Connexion</title>");
//                out.println("</head>");
//                out.println("<body>");
//                out.println("<h1>Echec </h1>");
//                out.println("</body>");
//                out.println("</html>");
//            }
            //4.再看密码对不对 如果不对就说密码错了
        } else if (!UserManager.getUsersTable().get(trouve).getPwd().equals(request.getParameter("password"))) {
            response.setContentType("text/html;charset=UTF-8");
            request.setAttribute("uname",request.getParameter("username"));
            request.setAttribute("msg","Echec :mot de passe érroné");
            request.getRequestDispatcher("login2.jsp").forward(request,response);
//            try (PrintWriter out = response.getWriter()) {
//                /* TODO output your page here. You may use following sample code. */
//                out.println("<!DOCTYPE html>");
//                out.println("<html>");
//                out.println("<head>");
//                out.println("<title>Servlet Connexion</title>");
//                out.println("</head>");
//                out.println("<body>");
//                out.println("<h1>Echec :mot de passe érroné </h1>");
//                out.println("</body>");
//                out.println("</html>");
//
//            }
        } else {//5.登录成功 下一步：判断你以什么身份进入
            HttpSession session = request.getSession();//唯一的值
            session.removeAttribute("login");
            session.setAttribute("login", UserManager.getUsersTable().get(trouve).getLogin());
            User user = RoomManager.getUser((String) session.getAttribute("login"));
            session.removeAttribute("user");
            session.setAttribute("user",user);
            listRoom(request,response,((User)session.getAttribute("user")).getRoomsCreated(),((User)session.getAttribute("user")).getRoomsInvited());
//多加一层括号
            //在会话里存储一个用户名变量
//            String role = UserManager.getUsersTable().get(trouve).getRole();
//            session.setAttribute("role", role);
            //在会话里存储一个身份变量
//            response.setContentType("text/html;charset=UTF-8");
//            if ("admin".equals(role)) {//如果你是管理员 就有权看所有用户 说点拍马屁的话
//                try (PrintWriter out = response.getWriter()) {
//                    out.println("<!DOCTYPE html>");
//                    out.println("<html><head><title>Navigation Administrateur</title></head>");
//                    out.println("<body>");
//                    out.println("<h1>Hello " + session.getAttribute("login") + "</h1>");
//                    out.println("<nav> <ul>");
//                    out.println(" <li>Connected</li>");
//                    out.println("<li><a href='CreateRoom.html'>Créer un nouveau salon</a></li>");
//                    out.println(" <li><a href='UserManager'>Afficher la liste des utilisateurs</a></li>");//点击会跳转
//                    out.println(" <li><a href='Deconnexion'>Déconnecter</a></li>");
//                    out.println(" <li><a href='RoomManager'>RoomManager</a></li>");
//                    out.println(" <li><a href='modifier'>Modifier vos salons crées/a></li>");
//                    out.println(" <li><a href='supprimer'>Supprimer vos salons crées/a></li>");
//                    out.println("</ul>");
//                    out.println("</nav>");
//                    out.println("</body>");
//                    out.println("</html>");
//                    listRoom(request,response,RoomManager.getUser(request,response).getRoomsCreated());
//                    listRoom(request,response,RoomManager.getUser(request,response).getRoomsInvited());
//                }
            //登录成功 但是以普通人身份进入
//                try (PrintWriter out = response.getWriter()) {
//                    /* TODO output your page here. You may use following sample code. */
//                    out.println("<!DOCTYPE html>");
//                    out.println("<html>");
//                    out.println("<head>");
//                    out.println("<title>Servlet Connexion</title>");
//                    out.println("</head>");
//                    out.println("<body>");
//                    out.println("<h1>Succes : utilisateur non admin </h1>");
//                    out.println("</body>");
//                    out.println("</html>");
//                }



        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        listRoom(request,response,((User)session.getAttribute("user")).getRoomsCreated(),((User)session.getAttribute("user")).getRoomsInvited());
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override//要自己建第一个用户 后面才好继续
    public void init() throws ServletException {
        super.init();
        //Créer le premier utilisateur de l'application pour pouvoir connecter et ajouter d'autres utilisateurs
        int id = UserManager.getUsersTable().size();
        UserManager.getUsersTable().put(id, new User("utc", "utc", "a", id, "Mr", "aaaaaaaa"));
        UserManager.getUsersTable().put(id+1, new User("utc1", "utc1", "b", id+1, "Mr", "bbbbbbbb"));
        UserManager.getUsersTable().put(id+2, new User("utc2", "utc2", "c", id+2, "Mr", "cccccccc"));
        UserManager.getUsersTable().put(id+3, new User("utc3", "utc3", "d", id+3, "Mr", "aaaaaaaa"));
        UserManager.getUsersTable().put(id+4, new User("utc4", "ut4", "e", id+4, "Mr", "aaaaaaaa"));
        UserManager.getUsersTable().get(id).setRole("admin");
        UserManager.getUsersTable().get(id+1).setRole("admin");
        UserManager.getUsersTable().get(id+2).setRole("admin");
        UserManager.getUsersTable().get(id+3).setRole("admin");
        UserManager.getUsersTable().get(id+4).setRole("admin");

    }

}