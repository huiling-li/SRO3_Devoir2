
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

        import Model.User;
        import jakarta.servlet.ServletException;
        import jakarta.servlet.annotation.WebServlet;
        import jakarta.servlet.http.HttpServlet;
        import jakarta.servlet.http.HttpServletRequest;
        import jakarta.servlet.http.HttpServletResponse;
        import jakarta.servlet.http.HttpSession;

        import java.io.IOException;
        import java.io.PrintWriter;
        import java.util.Iterator;
        import java.util.Set;


/**
 *
 * @author lounis
 */
@WebServlet(name = "Connexion", urlPatterns = {"/Connexion"})
public class Connexion extends HttpServlet {

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
        // Vérifier si le login existe
        Set<Integer> keys = UserManager.getUsersTable().keySet();//所有的键的set集合：所有序号

        //Obtaining iterator over set entries
        Iterator<Integer> itr = keys.iterator();
        int trouve = -1;
        while (itr.hasNext() && trouve < 0) {
            int index = (int) itr.next();//遍历序号 从index找User 再找Login 看看等不等于输入的username
            if (UserManager.getUsersTable().get(index).getLogin().equalsIgnoreCase(request.getParameter("username"))) {
                trouve = index;
                break;
            }
        }
        if (trouve < 0) {//循环过后还是没找到 那就说：失败
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet Connexion</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Echec </h1>");
                out.println("</body>");
                out.println("</html>");
            }
            //再看密码对不对 如果不对就说密码错了
        } else if (!UserManager.getUsersTable().get(trouve).getPwd().equals(request.getParameter("password"))) {
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet Connexion</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Echec :mot de passe érroné </h1>");
                out.println("</body>");
                out.println("</html>");

            }
        } else {//登录成功 下一步：判断你以什么身份进入
            HttpSession session = request.getSession();
            session.setAttribute("login", UserManager.getUsersTable().get(trouve).getLogin());
            //在会话里存储一个用户名变量
            String role = UserManager.getUsersTable().get(trouve).getRole();
            session.setAttribute("role", role);
            //在会话里存储一个身份变量
            response.setContentType("text/html;charset=UTF-8");
            if ("admin".equals(role)) {//如果你是管理员 就有权看所有用户
                try (PrintWriter out = response.getWriter()) {
                    out.println("<!DOCTYPE html>");
                    out.println("<html><head><title>Navigation Administrateur</title></head>");
                    out.println("<body>");
                    out.println("<h1>Hello " + session.getAttribute("login") + "</h1>");
                    out.println("<nav> <ul>");
                    out.println(" <li>Connected</li>");
                    out.println("<li><a href='NouveauUtilisateur.html'>Créer un nouveau utilisateur</a></li>");
                    out.println(" <li><a href='UserManager'>Afficher la liste des utilisateurs</a></li>");
                    out.println(" <li><a href='Deconnexion'>Déconnecter</a></li>");
                    out.println("</ul>");
                    out.println("</nav>");
                    out.println("</body>");
                    out.println("</html>");
                }

            } else {//登录成功 但是以普通人身份进入
                try (PrintWriter out = response.getWriter()) {
                    /* TODO output your page here. You may use following sample code. */
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Servlet Connexion</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1>Succes : utilisateur non admin </h1>");
                    out.println("</body>");
                    out.println("</html>");
                }

            }

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
        processRequest(request, response);
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

    @Override
    public void init() throws ServletException {
        super.init();
        //Créer le premier utilisateur de l'application pour pouvoir connecter et ajouter d'autres utilisateurs
        int id = UserManager.getUsersTable().size();
        UserManager.getUsersTable().put(id, new User("utc", "utc", "lounis@utc", id, "Mr", "lounisah"));
        UserManager.getUsersTable().get(id).setRole("admin");
    }

}