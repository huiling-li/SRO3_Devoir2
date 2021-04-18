//1.从新建用户页面来，看看符不符合规范
// 是UserManager的前一步 只是优化 不是必须的
//就是看看有没有漏填 不符合规范的 有没有重名啊什么的
//不符合就不能新建用户
package Controller;

        import java.io.IOException;
        import java.io.PrintWriter;
        import java.util.Hashtable;

        import Model.User;
        import jakarta.servlet.RequestDispatcher;
        import jakarta.servlet.ServletException;
        import jakarta.servlet.annotation.WebServlet;
        import jakarta.servlet.http.HttpServlet;
        import jakarta.servlet.http.HttpServletRequest;
        import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "Validation", urlPatterns = {"/Validation"})
public class Validation extends HttpServlet {

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
        boolean valid = true;//生效否
        //也是获取表单的值
        response.setContentType("text/html;charset=UTF-8");
        String firstName = request.getParameter("User first name");
        String lastName = request.getParameter("User familly name");
        String mail = request.getParameter("User email");
        String gender = request.getParameter("gender");
        String password = request.getParameter("User password");
        //1.不规范1：有一项没填 提示要填
        if (firstName == null || lastName == null || mail == null || password == null) {
            System.out.println("Champs non renseignés");
            //并跳回到新建用户页面
            RequestDispatcher rd = request.getRequestDispatcher("NouveauUtilisateur.html");
            rd.forward(request, response);
            valid = false;
            //此时都不valide呀
        }
        //2.不规范2：填了个屁也不行啊
        else if ("".equals(firstName) || "".equals(lastName) || "".equals(mail) || "".equals(password)) {
            System.out.println("Champs vides");
            RequestDispatcher rd = request.getRequestDispatcher("NouveauUtilisateur.html");
            rd.forward(request, response);
            valid = false;

        }





        //4.这好像是后面的事了：validator有值 就说明已经是第一次有重名的情况 在决定继不继续了
        if (request.getParameter("validator") != null) {// des doublons ont été détectés et l'utilisateur à valider son choix
            if ("oui".equals(request.getParameter("valider"))) {// on insėre les doublons
                valid = true;
            } else {
                valid = false;
                //不继续的话就还是回到前面咯
                RequestDispatcher rd = request.getRequestDispatcher("NouveauUtilisateur.html");//abandonner l'insertion
                rd.forward(request, response);
            }

            //3.这才是前传：validator==null还没经历重名抉择
            //这时候填的符合规范了 尝试给你新建一个用户 但却发现用户列表里已经有重名用户了
            //就让你抉择要不要覆盖了
        } else if (UserManager.getUsersTable().containsValue(new User(lastName, firstName))) {
            valid = false;
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet Validation</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Un utilisateur avec les m�mes nom et pr�nom existe d�j�. Voulez-vous l'enregistrer ?  </h1>");
                out.println("<form method='POST' action='Validation'>");
                out.println("Oui <input type='radio' name='valider' value='oui' /> ");
                out.println("Nom <input type='radio' name='valider' value='nom' />");
                //这些值因为之前都写过了就不用再写了 隐藏起来 等以后使用即可
                out.println("<input type='hidden' name='User first name' value='" + firstName + "'/>");
                out.println("<input type='hidden' name='User familly name' value='" + lastName + "'/>");
                out.println("<input type='hidden' name='User email' value='" + mail + "'/>");
                out.println("<input type='hidden' name='gender' value='" + gender + "'/>");
                out.println("<input type='hidden' name='User password' value='" + password + "' />");
                out.println("<br>");
                out.println("<input type ='submit' value='Envoyer' name='validator' />");
                //提交的动作就叫'validator' 因为是选oui 还是non 嘛
                out.println("</form>");
                out.println("</body>");
                out.println("</html>");
            }

        }
        if (valid) {//5.如果valide了就加到user管理器里
            RequestDispatcher rd = request.getRequestDispatcher("UserManager");
            rd.forward(request, response);
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

}