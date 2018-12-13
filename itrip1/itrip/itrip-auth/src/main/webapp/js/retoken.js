$(document).ready(function () {
    //循环执行，每隔5秒钟执行一次 5000
    window.setInterval(refreshCount, 5000);
    function refreshCount() {
        send();
    }
    //去掉定时器的方法
    //window.clearInterval(t1);
});

function send() {
    $.ajax({
        "url": "/authDemo/api/retoken",
        "type": "GET",
        //"data": {"name": "1044732267@qq.com", "password": "123456"},
        "dataType": "json",
        "success": function (data) {
            console.log(data);
            if (data.success=="true") {
                setCookie("token", data.data.token);
                setCookie("expTime", data.data.expTime);
            }else{
                //location.href="/authDemo/tokenError.jsp";
            }
        },
        beforeSend: function (request) {
            var token = $.cookie("token");
            request.setRequestHeader("token", token);
        }
    });
};
function setCookie(name, value) {
    document.cookie = name + '=' + escape(value);
}