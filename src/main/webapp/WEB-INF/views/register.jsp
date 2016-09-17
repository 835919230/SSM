<%--
  Created by IntelliJ IDEA.
  User: 诚
  Date: 2016/9/13
  Time: 15:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册</title>
</head>
<body>
    <form action="/register" method="post">
        <input type="text" name="username"/><br/>
        <input type="password" name="password"/><br/>
        <input type="text" name="firstname"/><br/>
        <input type="text" name="lastname"/><br/>
        <input type="submit" value="提交">
    </form>
</body>
</html>
