<!DOCTYPE html>
<html>
    <head>
        <title>
            用户登陆-国家自然基金深度搜索引擎
        </title>
        <!-- ZUI 标准版压缩后的 CSS 文件 -->
        <link rel="stylesheet" href="./dist/css/zui.min.css">

        <!-- ZUI Javascript 依赖 jQuery -->
        <script src="./dist/lib/jquery/jquery.js"></script>
        <!-- ZUI 标准版压缩后的 JavaScript 文件 -->
        <script src="./dist/js/zui.min.js"></script>
        <script src="./js/jQueryRotate.js" type="text/javascript">
        </script>
        <script type="text/javascript" src="./js/tools.js"></script>
        <style>
            *,a{
				margin: 0;
				padding: 0;
				z-index: 0;
			    color:#666666;
				list-style-type: none;
				text-decoration: none;
			}
			body{
				background: #ffffff;
				font-family: Microsoft Yahei;
			}
			.loginFrom{
				position:relative;
				width:400px;
				height:360px;
				border:1px solid #f0f0f0;
				left:50%;
				margin-left:-200px;
				margin-top:80px;
				background:#ffffff;
				box-shadow:3px 3px 60px #f0f0f0;
			}
			.logo{
				width:250px;
				margin:auto;
				margin-top:40px;
			}
			.setWidth{
				width:270px;
			}
			.navi{
				width:180px;
			}
			.fixLabel{
				width:50px !important; margin-left:15px;
			}
        </style>
        <script type="text/javascript">
        	$(function(){
        		$("#submitBtn").click(function(){
        			$("#submitBtn").text("正在登陆...");
		            $("#submitBtn").attr("disabled","disabled");
        			var email = $("#emailInput").val().trim();
        			var password = $("#passInput").val().trim();
        			if(email == "" || email == null){
        				$('#myModal').modal();
		                $("#modal-title").css({"color":"yellow"});
		                $("#modal-title").text("警告");
		                $("#modal-message").text("email不能为空");
		                $("#submitBtn").text("登陆");
		                $("#submitBtn").removeAttr("disabled");
        			}else if(password == "" || password == null){
        				$('#myModal').modal();
		                $("#modal-title").css({"color":"yellow"});
		                $("#modal-messagge").text("密码不能为空");
		                $("#submitBtn").text("登陆");
		                $("#submitBtn").removeAttr("disabled");
        			}else{
        				var fullUrl = window.location.href;
        				var redirect = "";
        				if(fullUrl.indexOf("redirect") > 0){
        					redirect = fullUrl.split("redirect")[1].substring(1);
        				}
        				$.ajax({
		                    url: "./dologin.action",
		                    type: "post",
		                    data: {
		                        "email":email,
		                        "password":password,
		                        "redirect":redirect
		                    },
		                    async: "false",
		                    dataType: "json",
		                    success: function (data) {
		                    	if(data.code == 200){
		                    		console.log("登陆成功");
		                    		window.location.href="./index.action";
		                    	}else if(data.code == 301){
		                    		window.location.href=data.url;
		                    	}else if(data.code == 201){
		                    		$('#myModal').modal();
		                    		$("#modal-title").css({"color":"yellow"});
		                    		$("#modal-title").text("警告");
		                    		$("#modal-message").text(data.message);
		                    	}else if(data.code == 202){
		                    		$('#myModal').modal();
		                    		$("#modal-title").css({"color":"yellow"});
		                    		$("#modal-title").text("警告");
		                    		$("#modal-message").text(data.message);
		                    	}else if(data.code == 203){
		                    		$('#myModal').modal();
		                    		$("#modal-title").css({"color":"yellow"});
		                    		$("#modal-title").text("警告");
		                    		$("#modal-message").text(data.message);
		                    	}else if(data.code == 500){
		                    		$('#myModal').modal();
		                    		$("#modal-title").css({"color":"red"});
		                    		$("#modal-title").text("警告");
		                    		$("#modal-message").text(data.message);
		                    	}
		                    	$("#submitBtn").text("登陆");
		                		$("#submitBtn").removeAttr("disabled");
		                    },
		                    error: function (XMLHttpRequest, textStatus, errorThrown) {
		                        $('#myModal').modal();
		                    	$("#modal-title").css({"color":"red"});
		                    	$("#modal-title").text("服务器错误");
		                    	$("#modal-message").text("通讯异常");
		                    	$("#submitBtn").text("登陆");
		                		$("#submitBtn").removeAttr("disabled");
		                    },
        				})
        			}
        		})
            })
        </script>
    </head>
    <body>
    	<div class="loginFrom">
    		<div class="logo"><img src="./images/logo-banner.png"></div>
    		<form class="form-horizontal" style="margin-left:20px; margin-top:30px;">
			  <div class="form-group">
			    <label for="emailInput" class="col-xs-2 required fixLabel">账号</label>
			    <div class="col-xs-10">
			      <input type="text" class="form-control setWidth" id="emailInput" placeholder="邮箱">
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="passInput" class="col-xs-2 required fixLabel">密码</label>
			    <div class="col-xs-10">
			      <input type="password" class="form-control setWidth" id="passInput" placeholder="密码">
			    </div>
			  </div>
			  <div class="form-group">
			  	<div class="col-sm-offset-2 col-xs-2"></div>
			    <div class="col-sm-offset-2 col-xs-10">
			      <button type="button" id="submitBtn" class="btn btn-primary btn-block btn-lg setWidth">登录</button>
			    </div>
			  </div>
			  <div class="form-group">
			    <div class="col-xs-3" style="margin-left:70px;">
			      <a href="./forget.action">忘记密码</a>
			    </div>
			    <div class="col-xs-3" style=" margin-left:120px;">
			      <a href="./register.action">注册</a>
			    </div>
			  </div>
			</form>
    	</div>

		<!-- 对话框HTML -->
		<div class="modal fade" id="myModal">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
		        <h4 class="modal-title" id = "modal-title"></h4>
		      </div>
		      <div class="modal-body">
		        <p id="modal-message"></p>
		      </div>
		    </div>
		  </div>
		</div>

    </body>
</html>