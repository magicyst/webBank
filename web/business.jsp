<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>银行系统</title>
<link href="css/index.css"  rel="stylesheet"/>
<link rel="stylesheet" href="css/bootstrap.min.css" />
<script src="js/jquery-1.7.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/util.js"></script>
<style>
	#show_message span{
		margin: 0px auto 10px auto;
		display: block;
		min-width: 20px;
		font-size: 16px;
		font-weight: bolder;
		color: #ffffff;
	}
</style>
</head>

<body class="bank_body" onUnload="window.location.href='${pageContext.request.contextPath}/logout?userId=${requestScope.userId}'">
	<!--banner container-->
	<div class="banner_container">
    	<div class="banner">
    		
            <div id="user">
            <table style="margin:15px auto 0px auto;">
            	<tr>
                	<td align="center" width="60"><span style="font-size:20px;display:block;width:60px; height:30px;color:#FFF;font-weight:bolder;margin-top:5px">${requestScope.username}</span><td>
                    <td align="center" width="80"><a href="${pageContext.request.contextPath}/logout?userId=${requestScope.userId}&username=${requestScope.username}">注销</a><td>
                </tr>
            </table>
            
            </div>
        </div>
    </div>
    
    	     
        <div id="user_wall">
        
        	
        </div>
        <div id="user_wall_bottom">
        
        	<div id="business_optaion">
            	<ul>
                	<li class="in" id="cha"><center>查询</center></li>
                    <li class="in" id="chun"><center>存款</center></li>
                    <li class="in" id="qv"><center>取款</center></li>
                    <li class="in" id="zhuan"><center>转账</center></li>
                    <li class="in" id="log"><center>记录</center></li>
                </ul>
            </div>
            
            <div class="optaion_block">
            		<input id="token" type="hidden" name="userId" value="${requestScope.userId}"/>
            	   <div id="block">
                   		<div id="input_div">
                        	<div class="input-group" id="div1">
  								<span class="input-group-addon">$</span>
  								<input name="money" id="money" type="text" class="form-control" aria-label="Amount (to the nearest dollar)">
  								<span class="input-group-addon">.00</span>
                                
							</div>
                            <div id="div2" style="margin-top:20px;">
                            	转账人:<input id="target" type="text" name="target"/>
                            </div>
                            <button style="width:100px; height:35px; font-size:15px; margin:20px auto 0px auto;" class="btn btn-primary btn-lg btn-block" id="send">存款</button>
                            
                            <center><div id="show_message"></div></center>
                        </div>
                   </div>
            </div>
            <div style="clear:both">
        </div>
        </div>
    
    
   
</body>
<script>

	var userId = $("#token").attr("value");
	var li_list = $("#business_optaion").find("ul li");
	li_list.each(function() {
		   $(this).click(function() {
			   li_list.each(function() {
				   $(this).attr("class","in")
			   });
			   $(this).attr("class","out")
			   var id = $(this).attr("id");
			   switch(id){
				   case "cha":
				   		cha();
						break;
				   case "chun":
				   		chun();
						break;
				   case "qv":
				   		qv();
						break;
				   case "zhuan":
				   		zhuan();
						break;
				   case "log":
				   		getlog();
						break;	   		
			   }
		   });
		});
	
	function cha(){
		$.ajax({
			type:"get",
			url:"/bank/business?",
			dataType:"text",
			data:{
				"userId":userId,
				"type":"inquiry"
			},
			success: function(money){
				$("#div1").css("display","none");
				$("#div2").css("display","none");
				$("#send").css("display","none");
				$("#show_message").empty();
				$("#show_message").append($("<span></span>")
						.text("余额为:"+money+"元")
				);
			},
			error:function(){
				alert("查询失败,请重试");
			}		
		});	
	}
	
	function chun(){
		$("#div1").css("display","");
		$("#div2").css("display","none");
		$("#send").css("display","");
		$("#send").css("display","").text("存款");
		$("#send").attr("onclick","chun_req()");
		$("#show_message").empty();
		//$("#send").click(chun_req());
	}

	function chun_req(){
		$.ajax({
			type:"get",
			url:"/bank/business",
			dataType:"text",
			data:{
				"userId":userId,
				"type":"dePosit",
				"dePosit_money":$("#money").val()
			},
			success: function(money){
				$("#money").val("");
				$("#show_message").append($("<span></span>")
						.text("已经存款"+money+"元")
				);
				//$("#show_message").text("已经存款"+money+"元");
			},
			error:function(){
				alert("存款失败,请重试");
			}
		});
	}

	function qv_req(){
		$.ajax({
			type:"get",
			url:"/bank/business",
			dataType:"text",
			data:{
				"userId":userId,
				"type":"withdrawals",
				"withdrawals_money":$("#money").val()
			},
			success: function(money){
				$("#money").val("");
				$("#show_message").append($("<span></span>")
						.text("已经取款"+money+"元")
				);
				//$("#show_message").text("已经取款"+money+"元");
			},
			error:function(){
				alert("取款失败,请重试");
			}
		});
	}

	function chuan_req(){
		$.ajax({
			type:"get",
			url:"/bank/business",
			dataType:"text",
			data:{
				"type":"transfer",
				"userId":userId,
				"transfer_money":$("#money").val(),
				"target_name":$("#target").val()
			},
			success: function(money){
				$("#money").val("");
				var name = $("#target").val();
				$("#target").val("");
				$("#show_message").append($("<span></span>")
						.text("已转账给"+name+money+"元")
				);
				//$("#show_message").text("已转账给"+$("#target").val()+money+"元");
			},
			error:function(){
				alert("取款失败,请重试");
			}
		});
	}

	function qv(){
		$("#div1").css("display","");
		$("#div2").css("display","none");
		$("#send").css("display","");
		$("#send").css("display","").text("取款");
		$("#show_message").empty();
		$("#send").attr("onclick","qv_req()")
		//$("#send").click(qv_req());
	}
	
	function zhuan(){
		$("#div1").css("display","");
		$("#div2").css("display","");
		$("#send").css("display","");
		$("#target").css("display","");
		$("#send").css("display","").text("转账");
		$("#show_message").empty();
		$("#send").attr("onclick","chuan_req()");
		//$("#send").click();
	}

	function getlog(){
		$.ajax({
			type:"get",
			url:"/bank/business",
			dataType:"json",
			data:{
				"type":"log",
				"userId":userId
			},
			success: function(logs){
				$("#div1").css("display","none");
				$("#div2").css("display","none");
				$("#send").css("display","none");
				$("#target").css("display","");
				$("#show_message").empty();
				var eul = $("<ul></ul>");
				eul.attr("class","list-group");
				var list = logs.logs.loadlogs;//记录
				for(s in list){
					eul.append($("<li></li>")
							.attr("class","list-group-item list-group-item-info")
							.text(list[s])
					);
				}
				$("#show_message").append(eul);
			},
			error:function(){
				alert("获取记录失败");
			}
		});
	}

</script>
</html>