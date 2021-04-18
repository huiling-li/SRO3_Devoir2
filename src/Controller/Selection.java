package Controller;

import Model.User;
import jakarta.servlet.RequestDispatcher;
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
@WebServlet(name = "Selection", urlPatterns = {"/Selection"})
public class Selection extends HttpServlet {

    public void show(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
//        session.setAttribute("room",);在上一步记录是哪个房间
        response.setContentType("text/html;charset=UTF-8");
        try (
                PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Un nouveau salon </title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Hello " + session.getAttribute("login") + " Veuillez choisir une opération pour "+request.getAttribute("room")+"</h1>");
            out.println("<nav> <ul>");
            out.println("<li><a href='CreateRoom.html?room="+request.getAttribute("room")+"'>accéder au salon</a></li>");//!!!
            out.println(" <li><a href='modif.jsp?"+request.getAttribute("room")+"'>Modifier vos salons crées</a></li>");
            out.println(" <li><a href='Supprimer?"+request.getAttribute("room")+"'>Supprimer vos salons crées</a></li>");
            out.println("</ul>");
            out.println("</nav>");
            out.println("</body>");
            out.println("</html>");
        } catch (IOException e) {
            e.printStackTrace();
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
            show(request, response);
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
            show(request, response);
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