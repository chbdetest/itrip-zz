<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.cookie.js"></script>
    <script type="text/javascript">
        $(function () {
            $(document).ready(function(){
                //循环执行，每隔1秒钟执行一次 1000
                window.setInterval(refreshCount, 3000);
                function refreshCount() {
                    send();
                }
                //去掉定时器的方法
                //window.clearInterval(t1);
            });
            function send(){
                $.ajax({
                    "url": "${pageContext.request.contextPath}/api/retoken",
                    "type": "GET",
                    "data": {"name": "1044732267@qq.com", "password": "123456"},
                    "dataType": "json",
                    "success": function (data) {
                        console.log(data);
                        alert(data.success);
                        if(data.success){
                            alert("ok");
                            alert(data.data.token);
                            alert(data.data.expTime);
                            setCookie("token", data.data.token);
                            setCookie("expTime", data.data.expTime);
                        }else{
                            alert("no");
                        }
                    },
                    beforeSend: function(request) {
                        var token = $.cookie("token");
                        request.setRequestHeader("token", token);
                    }
                });
            };
        });
        function setCookie(name, value) {
            document.cookie = name + '=' + escape(value);
        }
    </script>
</head>
<body>
<div>
    <input type="button" id="bt2" value="置换token"/>
</div>
</body>
</html>
