<%@ page import="java.util.Hashtable" %>
<%@ page import="Model.User" %>
<%@ page import="java.util.Set" %>
<%@ page import="Controller.UserManager" %>
<%@ page import="java.util.Iterator" %><%--
  Created by Huiling LI.
  User: lihuiling
  Date: 16/04/2021
  Time: 14:35
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
<%--<script type="text/javascript" src="src.js"></script>--%>
<form action="RoomManager" method="post">
    <!--    <label> Login </label>-->
    <!--    <input type="text" id="login" name="login"/>-->
<%--    <input type='hidden' name='login' value=session.getAttribute("login") />--%>
    <label> Titre :</label>
    <input type="text" id="titre" name="titre"  minlength="3"  required="required"/>
    <br>
    <label> Description :</label>
    <input type="text" id="desc" name="desc" minlength="3" required="required" value="${desc}" />
    <br>
    <label> Duree de validation:</label>
    <input type="text" id="duree" name="duree"  required="required" value="${duree}"/>
    <br>
    <label> Inviter vos amis, </label>
    <br>
    <label> les amis disponibles:  </label>
    <br>
    <%//可以取javabean 不用传
//        Hashtable<Integer, User> users = (Hashtable<Integer, User>)request.getAttribute("users");
        Set<Integer> keys = UserManager.getUsersTable().keySet();//所有的键的set集合：所有序号
        //Obtaining iterator over set entries
        Iterator<Integer> itr = keys.iterator();
        while (itr.hasNext()) {
            int index = (int) itr.next();//2.遍历序号 从index找User 再找Login 看看等不等于输入的username
  //可以取javabean 不用传
            if(UserManager.getUsersTable().get(index).getLogin()!=session.getAttribute("login"))
               out.println("<input name=\"invit\" type=\"checkbox\" value="+UserManager.getUsersTable().get(index).getLogin()+" />"+UserManager.getUsersTable().get(index).getLogin()+" ");

            }
//        out.println(request.getSession().getAttribute("usersString"));
    %>
<%--    <input type="text" id="invits" name="invits"/>--%>
<%--    <!--复选框不写死 根据人变化-->--%>
<%--    <input type="checkbox" name="admin" value="admin" />--%>
<%-- <label><input name="invit" type="checkbox" value="" />香蕉 </label>--%>
<%-- <label><input name="invit" type="checkbox" value=<%=UserManager.getUsersTable().get( (int) itr.next()).getLogin()%> />itr.next().getLogin() </label>-->--%>
<%--已经在循环外面了，自然是不行的--%>

    <input type="submit" value="Submit">
    <label>${er}</label>
</form>
<%--    <p>${er}</p>--%>
<%--错误信息放表单外--%>
</body>
</html>

