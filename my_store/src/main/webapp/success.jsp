<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/8/1
  Time: 19:34

  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <style>
        .content{
            background-color:#FFFFCC;
            width: 60%;
            margin: 0px auto;
            min-height: 200px;
        }

    </style>
    <script>
        var  num=5;
        setInterval(function(){
            num--;
            if(num<1){
                <%--location.href="http://www.itheima01.com:8020/web/view/order/info.html?oid=${oid}";--%>
                location.href="http://www.yanxuemeng.com:8020/web/index.html";
            }else{
                document.getElementById("time").innerHTML=num;
            }

        },1000);




    </script>
</head>
<body>
<div>
    <div style="margin-left: 200px;">
        <h3>收银台</h3>
    </div>
    <div style="text-align: center;">
        <div class="content">
            <div id="info">
               您的订单已支付成功，请稍等！
            </div>
            <div >
                <span id="time">5</span>秒之后跳转商城页面!!!
            </div>
        </div>
    </div>
</div>
</body>
</html>

