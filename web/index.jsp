
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>bank</title>
<link href="css/index.css"  rel="stylesheet"/>
<link rel="stylesheet" href="css/bootstrap.min.css" />
<script src="js/jquery-1.7.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</head>

<body class="bank_body">
	<!--banner container-->
	<div class="banner_container">
    	<div class="banner">
            <div id="user">
            </div>
        </div>
    </div>
    <div id="img_show">
    	<!--login_div-->
    	<div id="login_div">
            <form method="post" action="${pageContext.request.contextPath}/login">
                <input type="hidden" name="flag" value="1"/>
           	  <table style="width:90%; height:50%; margin: 0px auto;">
              		<tr>
                    	<td height="70" align="center"></td>
                        <td width="248" align="center"><label><h3 style="font-weight:bolder">账号密码登录</h3></label></td>
                    </tr>
                	<tr>
                    	<td width="10" height="50" align="center"><label></label></td>
                        <td align="center"><input class="form-control" name="username" type="text" placeholder="username"/></td>
                    </tr>
                    <tr>
                    	<td height="50" align="center"><label></label></td>
                        <td align="center"><input class="form-control" name="password" type="password" placeholder="password"/> </td>
                    </tr>
                    <tr>
                    	<td height="60" align="center"><label></label></td>
                        <td align="center">
                      	
                            <div class="input-group">
                              <span class="input-group-addon">
                                <input type="checkbox" name="remuber" value="1">
                                <span>记住密码</span>
                                <span style="float:right"><a onclick="myShow()">注册</a></span>
                              </span>
                              
                            </div>
                          
                      </td>
                    </tr>
                    <tr>
                    	<td height="60" align="center"><label></label></td>
                        <td align="center"> 
							<button style="width:270px; height:40px" type="submit" class="btn btn-primary btn-lg btn-block">登录</button>
                        </td>
                    </tr>
                    <tr>     
                </table>
            </form>
        </div>
    </div>

    <div class="cover" id="cover">
        <div id="register">
            <form action="${pageContext.request.contextPath}/register" method="get" onsubmit="return validate()">
                <table style="width:400px; height:220px;">
                    <tr>
                        <td width="18" height="50" align="right"></td>
                        <td width="370" align="center"><input style="width:250px" class="form-control" name="username" type="text" placeholder="用户名"/></td>
                    </tr>
                    <tr>
                        <td width="18" height="50" align="right"></td>
                        <td align="center"><input id="register_password" style="width:250px" class="form-control" name="password" type="password" placeholder="密码"/></td>
                    </tr>
                    <tr>
                        <td width="18" height="50" align="right"></td>
                        <td align="center"><input id="register_password_complete" style="width:250px" class="form-control" name="complete" type="password" placeholder="确认密码"/></td>
                    </tr>
                </table>
                <div style="width:210px; height:40px; margin:0px auto;">
                    <button type="submit" style="width:100px; height:40px" class="button">注册</button>
                    <button type="button" style="width:100px; height:40px" class="button" onclick="myHidden()">取消</button>
                </div>
            </form>
        </div>
    </div>

</body>
<script>
    //隐藏注册
    function myHidden(){
        $("#cover").css("display","none");
    }
    //显示注册
    function myShow(){
        $("#cover").css("display","block");
    }

    function validate(){
        var p1 = $("#register_password").val();
        var p2 = $("#register_password_complete").val();
        var flag = (p1==p2);
        if(!flag){
            alert("密码不一致");
            return false;
        }
        return true;
    }
</script>

</html>