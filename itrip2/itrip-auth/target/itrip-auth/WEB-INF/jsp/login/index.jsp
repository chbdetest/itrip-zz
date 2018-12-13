<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.cookie.js"></script>
<script type="text/javascript">
    $(function () {
        $("#bt1").click(function () {
            var name = $("#name").val();
            var passwrod = $("#password").val();
            alert(name + " " + passwrod);
            $.ajax({
                "url": "${pageContext.request.contextPath}/api/dologin",
                "type": "POST",
                "data": {"name": name, "password": passwrod},
                "dataType": "json",
                "success": function (data) {
                    console.log(data);
                    if(data.success=="true"){
                        setCookie("token", data.data.token);
                        setCookie("expTime", data.data.expTime);
                    }else{
                        //location.href="/authDemo/tokenError.jsp";
                    }
                }
            });
        });

        $("#lxr").click(function () {
            $.ajax({
                "url": "${pageContext.request.contextPath}/api/getUserList",
                "type": "GET",
                //"data": {"name": "1044732267@qq.com", "password": "123456"},
                "dataType": "json",
                "success": function (data) {
                    alert(data);
                    console.log(data);
                    var list = data.data;
                    alert(data.data);
                    $("#tabDiv").html("");
                    var tab = $("<table border='1'></table>").append("<tr><td>id</td><td>用户名</td><td>密码</td></tr>").appendTo($("#tabDiv"));
                    $(list).each(function () {
                        alert(this.userCode);
                        tab.append("<tr><td>" + this.id + "</td><td>" + this.userCode + "</td><td>" + this.userName + "</td></tr>");
                    });
                },
                beforeSend: function (request) {
                    request.setRequestHeader("token", $.cookie("token"));
                }
            });
        });
    });

    function setCookie(name, value) {
        document.cookie = name + '=' + escape(value);
    }
</script>
<body>
<h2>Hello world!</h2>
<form action="/authDemo/api/dologin"
      method="post">
    <div>
        <input type="text" id="name" name="name" value="itrip@163.com"/>
    </div>
    <div>
        <input type="password" id="password" name="password" value="123456"/>
    </div>
    <div>
        <input type="button" id="bt1" value="登录3"/>
        <input type="reset" value="重置"/>
        <a href="${pageContext.request.contextPath}/api/validateToken.html">验证token</a>
        <a href="${pageContext.request.contextPath}/api/getUserList.html">获取用户列表</a>
        <a href="${pageContext.request.contextPath}/api/refrToken.html">置换token</a>
        <a href="${pageContext.request.contextPath}/api/register.html">注册</a>
        <a href="${pageContext.request.contextPath}/api/userlink.html">联系人</a>
        <%--<a href="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()%>//api/userlink">联系人</a>--%>
    </div>
</form>
</body>
</html>
