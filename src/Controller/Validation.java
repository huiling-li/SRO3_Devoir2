//1.vient de Nouveau Utilisateur pour valider l'ajout d'un nouveau Utilisateur

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
        boolean valid = true;//indique est-ce qu'il est valide ou pas
        //rcupere les valeurs transfert d'un formulaire
        response.setContentType("text/html;charset=UTF-8");
        String firstName = request.getParameter("User first name");
        String lastName = request.getParameter("User familly name");
        String mail = request.getParameter("User email");
        String gender = request.getParameter("gender");
        String password = request.getParameter("User password");
        //1.anomalie1：s'il y a un champ pas saisi
        if (firstName == null || lastName == null || mail == null || password == null) {
            System.out.println("Champs non renseignés");
            //on redirige à la page NouveauUtilisateur
            RequestDispatcher rd = request.getRequestDispatcher("NouveauUtilisateur.html");
            rd.forward(request, response);
            valid = false;

        }
        //2.anomalie2：on a saisi rien
        else if ("".equals(firstName) || "".equals(lastName) || "".equals(mail) || "".equals(password)) {
            System.out.println("Champs vides");
            RequestDispatcher rd = request.getRequestDispatcher("NouveauUtilisateur.html");
            rd.forward(request, response);
            valid = false;

        }
        //4.
        if (request.getParameter("validator") != null) {// des doublons ont été détectés et l'utilisateur à valider son choix
            if ("oui".equals(request.getParameter("valider"))) {// on insėre les doublons
                valid = true;
            } else {
                valid = false;
                //on redirige à la page NouveauUtilisateur
                RequestDispatcher rd = request.getRequestDispatcher("NouveauUtilisateur.html");//abandonner l'insertion
                rd.forward(request, response);
            }

            //3.
            //il y a pas d'anomalie, mais il y a un compte existant qui porte le meme nom et prenom
            //vous choisissez est-ce que vous voulez le recouvrir ou non
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
                out.println("<h1>Un utilisateur avec les m�mes nom et pr�nom existe deja. Voulez-vous l'enregistrer ?  </h1>");
                out.println("<form method='POST' action='Validation'>");
                out.println("Oui <input type='radio' name='valider' value='oui' /> ");
                out.println("Nom <input type='radio' name='valider' value='nom' />");
                //masquer et stocker les valeurs pour l'utilisation ultérieur
                out.println("<input type='hidden' name='User first name' value='" + firstName + "'/>");
                out.println("<input type='hidden' name='User familly name' value='" + lastName + "'/>");
                out.println("<input type='hidden' name='User email' value='" + mail + "'/>");
                out.println("<input type='hidden' name='gender' value='" + gender + "'/>");
                out.println("<input type='hidden' name='User password' value='" + password + "' />");
                out.println("<br>");
                out.println("<input type ='submit' value='Envoyer' name='validator' />");
                out.println("</form>");
                out.println("</body>");
                out.println("</html>");
            }

        }
        if (valid) {//5.une fois valide,onl'ajoute à UserManager
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