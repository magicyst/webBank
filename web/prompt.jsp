
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    response.setHeader("refresh","3;/bank/index.jsp");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统提示</title>
<link href="css/index.css"  rel="stylesheet"/>
<link rel="stylesheet" href="css/bootstrap.min.css" />
<script src="js/jquery-1.7.min.js"></script>
<script src="js/bootstrap.min.js"></script>
    <style>
        #message_show{
            font-weight: bolder;
            color: #FFF;
            background-color: #666;
            padding: 30px;
            min-height: 160px;
            width: 450px;
            margin:200px auto 0px auto;
            border-radius: 20px;
            border: thin solid #FFF;
            box-shadow: 7px 4px 20px 0px black;
            overflow: hidden;
        }
    </style>
</head>

<body class="bank_body">
	<div id="message_show">
    	
        <h1>${requestScope.message}</h1>
    	<div id="show"></div>
    </div> 
</body>

<script>
    var timeout = 3;
    var time = function(){
        var t = setTimeout('time()',1000);
        document.getElementById("show").innerHTML = "<font color='red'>"+timeout+"</font>"
                +" 秒后跳回主页面....";
        timeout--;
        if(timeout <= 0){
            clearTimeout(t);
        }
    }
    window.onload = function(){
        time();
    }
</script>
</html>

