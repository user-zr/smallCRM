<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%><!DOCTYPE html>

<html>


<head>


    <meta charset="utf-8">

    <title>Fullscreen Login</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <meta name="description" content="">

    <meta name="author" content="">


    <!-- CSS -->

    <link rel="stylesheet" href="jquery/bootstrap_3.3.0/css/reset.css">

    <link rel="stylesheet" href="jquery/bootstrap_3.3.0/css/supersized.css">

    <link rel="stylesheet" href="jquery/bootstrap_3.3.0/css/style.css">

    <!-- Javascript -->

    <script src="jquery/bootstrap_3.3.0/js/jquery-1.8.2.min.js"></script>

    <script src="jquery/bootstrap_3.3.0/js/supersized.3.2.7.min.js"></script>

    <script src="jquery/bootstrap_3.3.0/js/supersized-init.js"></script>

    <script src="jquery/bootstrap_3.3.0/js/scripts.js"></script>


    <!--<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>-->


    <script>
        $(function () {

            //页面加载完毕后，将用户文本框中的内容清空
            $("#loginPwd").val("")

            //页面加载完毕后，让用户的文本自动获得焦点
            $("#loginAct").focus()


            //event 可以取得我们敲的那个键
            $(window).keydown(function (envent) {
                //回车键的键值为13
                if (event.keyCode == 13) {
                    $("#submit").click()
                }
            })

        })

        function login() {


            var loginAct = $("#loginAct").val()
            var loginPwd = $("#loginPwd").val()
            console.log(loginPwd)
            console.log(loginAct)
            if (loginAct == "" || loginPwd == "") {
                $("#msg").html("账号密码不能为空")

                //如果账号密码为空，需要强制种植方法
                return false;
            }

            //去后台验证相关操作
            $.ajax({
                url: "/login.do",
                data: {
                    "loginAct": loginAct,
                    "loginPwd": loginPwd
                },
                type: "post",
                dataType: "json",
                success: function (data) {
                    if (data.status) {
                        window.location.href = "/index"
                    } else {
                        $("#loginPwd").val("")

                        $("#msg").html(data.msg)
                    }

                }
            })
        }


    </script>
</head>


<body>


<div class="page-container">

    <h1>Login</h1>


    <input id="loginAct" type="text" name="username" class="username" placeholder="Username"><br>

    <input id="loginPwd" type="password" name="password" class="password" placeholder="Password"><br>

    <button type="submit" id="submit" onclick="login()">登录</button>
    <br>

    <div class="error"><span id="msg"></span></div>
    <br>


    <div class="connect">

    </div>

</div>


</body>


</html>



