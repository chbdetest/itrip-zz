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
    <script type="text/javascript" src="/itrip-auth/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#bt2").click(function () {
                /*数据转换方式*/
               // var arr = new Array();
               // arr.push({"linkUserName":"zhangsan"});
               // var arrs = JSON.stringify(arr);

                /*普通javabean*/
                var str = {"linkUserName":"张三"};
                var arrs = JSON.stringify(str);
                console.log(arrs);

                $.ajax({
                    "url": "/itrip-biz/api/userinfo/queryuserlinkuser",
                    "type": "POST",
                    "data":arrs,
                    "contentType" : 'application/json',
                    "dataType" : 'json',
                    "success": function (data) {
                        console.log(data);
                    }
                });
            });
        });
    </script>
</head>
<body>
<div>
    <input type="button" id="bt2" value="提交1"/>
</div>
<div id="tabDiv">

</div>
</body>
</html>
