/*
2.添加valide的用户 在后台添加用户的工具人 不是用户接口 但如果你想看有几个用户也可以直接访问
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * 不要从login.jsp进入 输入数据 没有validation
 新定义的tostring要加参数呀 虽然不报错
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
import static java.lang.System.exit;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;



@WebServlet(name = "UserManager", urlPatterns = {"/UserManager"})
public class UserManager extends HttpServlet {
    private static  Hashtable<Integer, User> usersTable= new Hashtable<Integer, User>();

    public static Hashtable<Integer, User> getUsersTable() {
        return usersTable;
    }

    protected String toString(Hashtable<Integer, User> usersTable){
        Set<Integer> keys = usersTable.keySet();//所有的键的set集合：所有序号
        String allUsers="";
        //Obtaining iterator over set entries
        Iterator<Integer> itr = keys.iterator();

        while (itr.hasNext()) {
            int index = (int) itr.next();//2.遍历序号 从index找User 再找Login 看看等不等于输入的username
            allUsers=allUsers+usersTable.get(index).getLogin()+" ";
            }
        return allUsers;
        }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)//当然可以自定义方法 在生命周期函数内调用
    //post传输调用这个方法 只要搞几个对象进来干活即可
            throws ServletException, IOException {
        //1.获取信息（从validation传过来的 已经被同意了的 的信息） 有新用户输入嘛
        response.setContentType("text/html;charset=UTF-8");
        String firstName=request.getParameter("User first name");
        String lastName=request.getParameter("User familly name");
        String mail=request.getParameter("User email");
        String gender=request.getParameter("gender");
        String password=request.getParameter("User password");
        String role = request.getParameter("admin");
        //2.添加一个用户（值和用户map对应 跟字典一样）被valide过就放心加吧
        User user = new User(lastName,firstName,mail,usersTable.size(),gender,password);
        usersTable.put(usersTable.size(), user);

        response.setContentType("text/html;charset=UTF-8");
//3.添加成功，说点废话
        try (PrintWriter out = response.getWriter()) {//表单传来新用户的数据 在这里加入UserTable 并显示
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Un nouveau utilisateur </title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1> Un nouveau utilisateur est ajouté : </h1>");
            out.println(usersTable.get(usersTable.size()-1).toString());//toString应该是HsshTable自带方法
            //会打印用户信息（自定义好的 想说的信息）
            out.println("</body>");
            out.println("</html>");
//            在这里搞一个作用域不知道create拿不拿得到
            HttpSession session = request.getSession();
            session.setAttribute("users", UserManager.getUsersTable());
            session.setAttribute("user",user);
            session.setAttribute("usersString",toString(usersTable));
//            session.setAttribute("user",);
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
        //没有新输入用户 就循环下把原来的用户都输出即可
        //从地址栏输入 而不是从login发来表单信息
        //1.这里只能查看 要修改得先valide 表单提交来才行
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Un nouveau utilisateur </title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1> Liste des utilisateurs : </h1>");
            out.println("<ol>");
            out.println("<li>");
            out.println((String) toString(usersTable));
            System.out.println(toString(usersTable));
            out.println("</li>");

            Set<Integer> keys = usersTable.keySet();
            //Obtaining iterator over set entries
            Iterator<Integer> itr = keys.iterator();
            while(itr.hasNext()){
                out.println("<li>");
                out.println(usersTable.get(itr.next()).toString());
                out.println("</li>");
            }
            out.println("</ol>");
            out.println("</body>");
            out.println("</html>");
        }
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
    //没啥用的

}