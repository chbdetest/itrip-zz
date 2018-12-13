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
    </script>
</head>
<body>
<div>
    因为不方便直接在浏览器中设置header,此处使用设置cookie的方式模拟，
    客户端设置了cookie，里面包含了token,在retoken.js中使用代码将cookie里面的值放入header,request.setRequestHeader("token", token);
    可以直接使用谷歌浏览器F12开发模式中的console里面设置cookie
    <hr>
    cookie需要设置token,token可以在登录时获取或者可以在postman中获取
    <hr>
    cookie的格式:document.cookie="token=token:PC-b7782e510d27f86dd1ba7c0c915c2351-12-20181126143909-0aeb0a"
</div>
</body>
</html>
