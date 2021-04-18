<%@ page import="Model.User" %>
<%@ page import="java.util.Hashtable" %><%--
  Created by IntelliJ IDEA.
  User: lihuiling
  Date: 20/03/2021
  Time: 00:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <title>TODO supply a title</title>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<form action="RoomManager" method="post">
  <!--    <label> Login </label>-->
  <!--    <input type="text" id="login" name="login"/>-->
  <input type='hidden' name='login' value=session.getAttribute("login") />
  <label> Titre </label>
  <input type="text" id="titre" name="titre"/>
  <br>
  <label> Description </label>
  <input type="text" id="desc" name="desc"/>
  <br>
  <label> Duree de validation </label>
  <input type="text" id="duree" name="duree"/>
  <br>
  <label> Inviter vos amis: </label>
  <!--复选框不写死 根据人变化-->
  <%
//    Hashtable<Integer, User> usersTable= session.getAttribute("users");

        out.println(request.getSession().getAttribute("users"));
        out.println("<>");
        out.println("<li>Connected</li>");
<%--    out.println("<li><a href="<%=Url%>"   >");--%>
  %>

  <label><input name="invit" type="checkbox" value="" />session.getAttribute("users") </label>
  <label><input name="invit" type="checkbox" value="" />桃子 </label>
  <label><input name="invit" type="checkbox" value="" />香蕉 </label>
  <label><input name="invit" type="checkbox" value="" />梨 </label>


  <!--    <input type="checkbox" name="admin" value="admin" />-->
  <input type="submit" value="Submit">
</form>

</body>
</html>

