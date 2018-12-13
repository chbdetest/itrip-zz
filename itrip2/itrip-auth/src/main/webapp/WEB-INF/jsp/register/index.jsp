<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册</title>
</head>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
    $(function () {
        $("#div_register input[name='userCode']").blur(function () {
            var userCode = $("#div_register input[name='userCode']").val();
            $.ajax({
                "url": "${pageContext.request.contextPath}/api/ckusr",
                "type": "GET",
                "data": {"name": userCode},
                "dataType": "json",
                "success": function (data) {
                    console.log(data);
                }
            });
        });

        $("#tijiao").click(function () {
            var str = {
                "userCode": $("#div_register input[name='userCode']").val(),
                "userName": $("#div_register input[name='userName']").val(),
                "userPassword": $("#div_register input[name='userPassword']").val()
            };
            var arrs = JSON.stringify(str);
            $.ajax({
                "url": "${pageContext.request.contextPath}/api/doregister",
                "type": "POST",
                "data": arrs,
                "contentType": 'application/json',
                "dataType": "json",
                "success": function (data) {
                    console.log(data);
                    if (data.success == "true") {
                        console.log("注册成功，邮件发送成功");
                        $("#div_activate").css("display", "block");
                    } else {
                        console.log("注册失败，邮件发送失败");
                        $("#div_activate_fail").html("注册失败，邮件发送失败");
                    }
                }
            });
        });

        $("#activateTijiao").click(function () {
            var user = $("#div_activate input[name='user']").val();
            var code = $("#div_activate input[name='code']").val();
            var url = "${pageContext.request.contextPath}/api/activate?user=" + user + "&code=" + code;
            $.ajax({
                "url": url,
                "type": "PUT",
                "dataType": "json",
                "success": function (data) {
                    console.log(data);
                }
            });
        });

        $("#activateId").click(function () {
            $("#div_activate").css("display", "block");
        })
    });
</script>
<body>
<div id="div_register">
    <form>
        <table align="center">
            <tr>
                <td>注册邮箱:</td>
                <td><input type="text" name="userCode"></td>
            </tr>
            <tr>
                <td>昵称:</td>
                <td><input type="text" name="userName"></td>
            </tr>
            <tr>
                <td>登录密码:</td>
                <td><input type="text" name="userPassword"></td>
            </tr>
            <tr>
                <td>确认密码:</td>
                <td><input type="text" name="userPassword2"></td>
            </tr>
            <tr>
                <td><input type="button" id="tijiao" value="提交">
                    <input type="reset" value="重置"></td>
                <td><input type="button" id="activateId" value="激活已经有账号"></td>
            </tr>
        </table>
    </form>
</div>
<div id="div_activate" style="display: none">
    <form id="form1">
        <table align="center">
            <tr>
                <td>激活账号:</td>
            </tr>
            <tr>
                <td>邮箱:</td>
                <td><input type="text" name="user"></td>
            </tr>
            <tr>
                <td>激活码:</td>
                <td><input type="text" name="code"></td>
            </tr>
            <tr>
                <td><input type="button" id="activateTijiao" value="提交"></td>
                <td><input type="reset" value="重置"></td>
            </tr>
        </table>
    </form>
</div>
<div id="div_activate_fail"></div>
</body>
</html>
