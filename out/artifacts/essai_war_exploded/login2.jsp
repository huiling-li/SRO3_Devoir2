<%--
  Created by Huiling LI.
  User: lihuiling
  Date: 17/04/2021
  Time: 18:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en" >
<head>
    <meta charset="UTF-8">
    <title></title>

    <link rel='stylesheet' href='font-awesome.min.css'>

    <link rel="stylesheet" href="style.css">

</head>
<body>

<div class="container">
    <div class="card"></div>
    <div class="card">
        <h1 class="title">Inscription</h1>
        <form action="Connexion" method="POST">
            <!--    <form action="Connexion" id="loginForm" method="post">-->
            <div class="input-container">
                <input type="text" id="user" name="username" required="required" value="${uname}"/>
                <label >login</label>
                <div class="bar"></div>
            </div>
            <div class="input-container">
                <input type="password" id="pass" name="password" minlength="8" required required="required" value="${upwd}"/>
                <label >password</label>
                <div class="bar"></div>
            </div>
            <div class="button-container">
                <!--        <input type="submit" value="Sign in">-->
                <button type="submit" value="Sign in" id="btn"><span>se connecter</span></button>
            </div>
            <div><p style="color: red;text-align: center" id="err_msg">${msg}</p></div>
            <!--      <input type="submit" value="Sign in">-->
        </form>
    </div>



<script src='/js/jquery.min.js'></script>
<script src="/js/script.js"></script>
<div style="text-align:center;">
</div>
</body>
</html>

