<%--
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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TODO supply a title</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

</head>
<body>
<%--<script type="text/javascript" src="src.js"></script>--%>
<form action="Suppression" method="post">

    <h1> Suppression </h1>

    <input type='hidden' name='room' value=session.getAttribute("room") />
    <label> Vous voulez supprimer quel salon? </label>
    <br>
    <label> les salons possibles à supprimer:  </label>
    <br>
    <%
        HttpSession session1 = (HttpSession) request.getSession();
//        Hashtable<Integer, User> users = (Hashtable<Integer, User>)request.getAttribute("users");
        Hashtable<Integer,Model.Room> rooms = ((User)session1.getAttribute("user")).getRoomsCreated();
        Set<Integer> keys =rooms.keySet();
        //Obtaining iterator over set entries
        Iterator<Integer> itr = keys.iterator();
        while (itr.hasNext()) {
            int index = (int) itr.next();
//            if(UserManager.getUsersTable().get(index).getLogin()!=session.getAttribute("login"))
            out.println("<input name=\"suppr\" type=\"radio\" value="+rooms.get(index).getTitre()+" />"+rooms.get(index).getTitre()+" ");

        }
//        out.println(request.getSession().getAttribute("usersString"));
    %>
    <br>
<%--    <input type="text" id="suppr" name="suppr"/>--%>
<%--    <br>--%>

    <input type="submit" value="Submit">
</form>

</body>
</html>

