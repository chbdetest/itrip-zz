<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.cookie.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/connect/zh_CN/htmledition/js/wxLogin.js"></script>
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
    function setCookie(name, value) {
        document.cookie = name + '=' + escape(value);
    }
    });
</script>
<script src="http://libs.baidu.com/jquery/1.6.4/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.webcam.min.js"></script>
<script type="text/javascript">
    $(function () {
        var w = 320, h = 240;
        var pos = 0, ctx = null, saveCB, image = [];
        var canvas = document.createElement("canvas");
        canvas.setAttribute('width', w);
        canvas.setAttribute('height', h);
        console.log(canvas.toDataURL);
        if (canvas.toDataURL) {
            ctx = canvas.getContext("2d");
            image = ctx.getImageData(0, 0, w, h);
            saveCB = function(data) {
                var col = data.split(";");
                var img = image;
                for(var i = 0; i < w; i++) {
                    var tmp = parseInt(col[i]);
                    img.data[pos + 0] = (tmp >> 16) & 0xff;
                    img.data[pos + 1] = (tmp >> 8) & 0xff;
                    img.data[pos + 2] = tmp & 0xff;
                    img.data[pos + 3] = 0xff;
                    pos+= 4;
                }
                if (pos >= 4 * w * h) {
                    ctx.putImageData(img, 0, 0);
                    $.ajax({
                        type: "post",
                        url: "${pageContext.request.contextPath}/Face/img?t="+new Date().getTime(),
                        data: {type: "pixel", image: canvas.toDataURL("image/png")},
                        dataType: "html",
                        success: function(data){
                            console.log("===="+data);
                            pos = 0;
                            alert(data);
                            var path="C:\\Users\\Administrator\\Desktop\\ch04-注册激活账号\\itrip\\itrip-auth\\target\\itrip-auth"+data;
                            aa(path);
                        }
                    });
                }
            };
        } else {
            saveCB = function(data) {
                image.push(data);
                pos+= 4 * w;
                if (pos >= 4 * w * h) {
                    $.ajax({
                        type: "post",
                        url: "${pageContext.request.contextPath}/Face/img",
                        data: {type: "pixel", image: image.join('|')},
                        dataType: "json",
                        success: function(data){
                            console.log("+++++"+eval(msg));
                            pos = 0;
                            alert(data);
                            var path="C:\\Users\\Administrator\\Desktop\\ch04-注册激活账号\\itrip\\itrip-auth\\target\\itrip-auth"+data;
                            aa(path);
                        }
                    });
                }
            };
        }
        function aa(path){
            alert(path);
            $.ajax({
                type: "post",
                url: "${pageContext.request.contextPath}/Face/pd",
                data: {"path": path},
                dataType: "html",
                success: function(data){
                    alert(data);
                    if(data=='true'){
                        alert("扫脸判断是真人！！！");
                    }else if(data=='false'){
                        alert("你是假的！！！");
                    }

                }
            });
        }
        $("#Face").click(function () {

            $("#webcam").css("display","block")
            $(".btn").css("display","block")
        })
        $("#close").click(function () {

            $("#webcam").css("display","none")
            $(".btn").css("display","none")
        })
        $("#webcam").webcam({
            width: w,
            height: h,
            mode: "callback",
            swffile: "${pageContext.request.contextPath}/js/jscam_canvas_only.swf",
            onSave: saveCB,
            onCapture: function () {
                webcam.save();
            },
            debug: function (type, string) {
                console.log(type + ": " + string);
            }
        });
    });
    //拍照
    function savePhoto(){
        webcam.capture();
    }

</script>

<style type="text/css">

    #webcam { border: 1px solid #666666; width: 320px; height: 240px; display: none;}
    .photos { border: 1px solid #666666; width: 320px; height: 240px; }
    .btn { width: 320px; height: auto; margin: 5px 0px;display: none }
    .btn input[type=button] { width: 150px; height: 50px; line-height: 50px; margin: 3px; }
</style>
<body>
<h2>Hello world!</h2>
<form action="/authDemo/api/dologin"
      method="post">
    <div>
        <input type="text" id="name" name="name"/>
    </div>
    <div>
        <input type="password" id="password" name="password"/>
    </div>
    <div>
        <input type="button" value="扫脸登录" id="Face"/>
        <<a href="https://ai.baidu.com/facekit/page/form/E36025960232464DACCECDE3368D8BA1">注册人脸</a>

        <input type="button" id="bt1" value="登录2"/>
        <input type="reset" value="重置"/>
        <a href="  http://2310b45c.ngrok.io/itrip-auth/wecat/login?signature=12&timestamp=333&nonce=23&echostr=122">微信登录</a>
        <a href="${pageContext.request.contextPath}/path/jsp/validateToken">验证token</a>
        <a href="${pageContext.request.contextPath}/path/jsp/getUserList">获取用户列表</a>
        <a href="${pageContext.request.contextPath}/path/jsp/refrToken">置换token</a>
        <a href="${pageContext.request.contextPath}/path/register/phone">手机注册</a>
        <a href="${pageContext.request.contextPath}/path/register/index">邮箱注册</a>
    </div>
</form>

<div id="webcam"></div>
<div class="btn">
    <input type="button" value="删除" id="delBtn" onclick="delPhoto();"/>
    <input type="button" value="拍照" id="saveBtn" onclick="savePhoto();"/>
    <input type="button" value="关闭" id="close" />
</div>

<%--<div class="photos">
    <img src="C:\\Users\\Administrator\\Desktop\\ch04-注册激活账号\\itrip\\itrip-auth\\target\\itrip-auth\\upload\\1544322020530.png" id="img">
</div>
</body>--%>
</html>
