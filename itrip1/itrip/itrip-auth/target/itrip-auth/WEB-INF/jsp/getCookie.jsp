<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/11/14 0014
  Time: 20:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="/authDemo/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="/authDemo/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/authDemo/js/retoken.js"></script>
    <script type="text/javascript">
        $(function () {
            //alert($.cookie("token"));
            $("#token").html($.cookie("token"));
        });
    </script>
</head>
<body>
<div>
    <input type="button" id="v_token" value="查看token" onclick="location.reload();"/>
    <input type="button" value="login登录页" onclick="location.href='index.jsp'"/>
</div>
<div id="token">token:</div>
</body>
</html>
