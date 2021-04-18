<%@ page import="jakarta.servlet.http.HttpSession" %><%--

  Created by Huiling LI.
  User: lihuiling
  Date: 16/04/2021
  Time: 14:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.Hashtable" %>
<%@ page import="Model.User" %>
<%@ page import="Model.Room" %>
<%@ page import="java.util.Set" %>
<%@ page import="Controller.UserManager" %>
<%@ page import="java.util.Iterator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TODO supply a title</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

</head>
<body>
<%--<script type="text/javascript" src="src.js"></script>--%>
<form action="Modification" method="post">

    <h1> Modification </h1>

    <input type='hidden' name='room' value=session.getAttribute("room") />
    <label> Vous voulez modifier quel salon? </label>
    <br>
    <label> les salons disponibles à modifier:  </label>
<%--    <%--%>
<%--        out.println(request.getSession().getAttribute("modif"));--%>
<%--    %>--%>
    <br>
    <%//可以取javabean 不用传
        HttpSession session1 = (HttpSession) request.getSession();
//        Hashtable<Integer, User> users = (Hashtable<Integer, User>)request.getAttribute("users");
        Hashtable<Integer,Model.Room> rooms = ((User)session1.getAttribute("user")).getRoomsCreated();
        Set<Integer> keys =rooms.keySet();//所有的键的set集合：所有序号
        //Obtaining iterator over set entries
        Iterator<Integer> itr = keys.iterator();
        while (itr.hasNext()) {
            int index = (int) itr.next();//2.遍历序号 从index找User 再找Login 看看等不等于输入的username
            //可以取javabean 不用传
//            if(UserManager.getUsersTable().get(index).getLogin()!=session.getAttribute("login"))
                out.println("<input name=\"modif\" type=\"radio\" value="+rooms.get(index).getTitre()+" />"+rooms.get(index).getTitre()+" ");

        }
//        out.println(request.getSession().getAttribute("usersString"));
    %>
    <br>
<%--&lt;%&ndash;    <input type="text" id="modif" name="modif"/>&ndash;%&gt;--%>
<%--    <br>--%>
    <label> Titre </label>
    <input type="text" id="titre" name="titre"/>
    <br>
    <label> Description </label>
    <input type="text" id="desc" name="desc" value="${desc}"/>
    <br>

    <label> Duree de validation </label>
    <input type="text" id="duree" name="duree" value="${duree}"/>
    <br>
    <label> Inviter vos amis </label>
    <br>
    <label> Les amis disponibles:  </label>
    <br>
    <%//可以取javabean 不用传
//        Hashtable<Integer, User> users = (Hashtable<Integer, User>)request.getAttribute("users");
        Set<Integer> key = UserManager.getUsersTable().keySet();//所有的键的set集合：所有序号
        //Obtaining iterator over set entries
        Iterator<Integer> it = key.iterator();
        while (it.hasNext()) {
            int index = (int) it.next();//2.遍历序号 从index找User 再找Login 看看等不等于输入的username
            //可以取javabean 不用传
            if(UserManager.getUsersTable().get(index).getLogin()!=session.getAttribute("login"))
                out.println("<input name=\"invit\" type=\"checkbox\" value="+UserManager.getUsersTable().get(index).getLogin()+" />"+UserManager.getUsersTable().get(index).getLogin()+" ");

        }
//        out.println(request.getSession().getAttribute("usersString"));
    %>
<%--    <input type="text" id="invits" name="invits"/>--%>

    <input type="submit" value="Submit">
    <label>${err}</label>
</form>

</body>
</html>

