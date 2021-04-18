<%--
    el表达式：专门搞域对象，而不用写前面 比jsp标签方便 写在java块外面
    Java块可以拼在html里面
    不能搞普通局部变量 认为是不存在的域对象
  Created by Huiling LI.
  User: lihuiling
  Date: 17/04/2021
  Time: 16:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>TODO supply a title</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

</head>
<body>
<!--<script type="text/javascript" src="src.js"></script>-->
<form action="Modification" method="post">

    <label> Modification </label>
    <br>
    <input type='hidden' name='room' value=session.getAttribute("room") />
    <label> Titre </label>
    <input type="text" id="titre" name="titre"/>
    <br>
    <label> Description </label>
    <input type="text" id="desc" name="desc"/>
    <br>
    <label> Duree de validation </label>
    <input type="text" id="duree" name="duree"/>
    <br>
    <label> Inviter vos amis:(séparé par l'espace) </label>
    <label> amis disponibles: " + session.getAttribute("usersString") + "</label>
    <input type="text" id="invits" name="invits"/>

    <select name="TypeName" id="type">
        <option  selected="selected">==请选择==</option>
        <option value="医药制造业">医药制造业</option>
        <option value="计算机软件/硬件">计算机软件/硬件</option>
    </select>

    <input type="submit" value="Submit">
</form>
<!--放到表单外面才行-->
<%
    String i = (String)request.getSession().getAttribute("modif");
    String K = "MMM";
    pageContext.setAttribute("una","nnnnn");
    request.setAttribute("una","nngyhnnn");
    session.setAttribute("una","nnnhunn");
    application.setAttribute("una","nnftcfnnn");

%>
el:${una}
<button onclick="add();"><%=K%></button>
<script type="text/javascript">
    function add() {
        var mm = document.createElement("p")
        mm.innerHTML = "hhhhhh"
        console.log(mm);//属性节点
    }
    var type = document.getElementById("type")//属性值不能加（斜杠变文本） 赋值不是追加 覆盖掉了 +=即可 666666
    type.innerHTML += "<option value=\"医药制造业\">医药制造业</option>"
</script>

</body>
</html>

