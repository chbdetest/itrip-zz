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
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/retoken.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#bt2").click(function () {
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
    </script>
</head>
<body>
<div>
    <input type="button" id="bt2" value="获取用户列表"/>
</div>
<div id="tabDiv">

</div>
</body>
</html>
