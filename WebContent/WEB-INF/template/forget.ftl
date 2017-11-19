<!DOCTYPE html>
<html>
    <head>
        <title>
            用户注册-国家自然基金深度搜索引擎
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
				height:280px;
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
				width:60px !important; margin-left:5px;
			}
        </style>
        <script type="text/javascript">
        	$(function(){
        		$("#nextBtn").click(function(){
        			var email = $("#emailInput").val().trim();
        			if(email == "" || email == null){
        				$('#myModal').modal();
            			$("#modal-title").css({"color":"yellow"});
            			$("#modal-title").text("警告");
            			$("#modal-message").text("邮箱不能为空");
        			}else{
        				$.ajax({
		                    url: "./getcheck.action",
		                    type: "post",
		                    data: {
		                        "email":email,
		                    },
		                    async: "false",
		                    dataType: "json",
		                    success: function (data) {
		                    	if(data.code == 200){
		                    		$('#myModal').modal();
				            		$("#modal-title").css({"color":"blue"});
				            		$("#modal-title").text("通知");
				            		$("#modal-message").text("系统已经向您:"+email+"的邮箱发送了验证码。（仅限一次使用）");
				            		$("#emailInput").attr("readonly",true);
				        			$("#nextForm").remove();
				        			$(".loginFrom").css({"height":"380px"})
				        			$("#confirmForm").show();
		                    	}else if(data.code == 208){
		                    		$('#myModal').modal();
		                    		$("#modal-title").css({"color":"yellow"});
		                    		$("#modal-title").text("警告");
		                    		$("#modal-message").text(data.message);
		                    	}
		                    	$("#confirmBtn").text("确认更改");
		                		$("#confirmBtn").removeAttr("disabled");
		                    },
		                    error: function (XMLHttpRequest, textStatus, errorThrown) {
		                        $('#myModal').modal();
			                    $("#modal-title").css({"color":"red"});
			                    $("#modal-title").text("服务器错误");
			                    $("#modal-message").text("通讯异常");
			                    $("#confirmBtn").text("确认更改");
			                    $("#confirmBtn").removeAttr("disabled");
		                    },
	        			})
        			}
        		})
        		$("#confirmBtn").click(function(){
        			$("#confirmBtn").attr("disabled","disabled");
        			var email = $("#emailInput").val().trim(); 
        			var password = $("#passInput").val().trim();
        			var confirm = $("#confirmInput").val().trim();

        			if(password == "" || password == null || confirm == "" || confirm == null){
        				$('#myModal').modal();
            			$("#modal-title").css({"color":"yellow"});
            			$("#modal-title").text("警告");
            			$("#modal-message").text("密码信息不能为空");
            			$("#confirmBtn").removeAttr("disabled");
        			}else{
    					$.ajax({
		                    url: "./doforget.action",
		                    type: "post",
		                    data: {
		                        "email":email,
		                        "password":password,
		                        "confirm":confirm
		                    },
		                    async: "false",
		                    dataType: "json",
		                    success: function (data) {
		                    	if(data.code == 200){
		                    		$('#myModal').modal();
		                    		$("#modal-title").css({"color":"blue"});
		                    		$("#modal-title").text("通知");
		                    		$("#modal-message").text("密码重置成功，请使用新密码登陆");
		                    	}else if(data.code == 206){
		                    		$('#myModal').modal();
		                    		$("#modal-title").css({"color":"yellow"});
		                    		$("#modal-title").text("警告");
		                    		$("#modal-message").text(data.message);
		                    	}else if(data.code == 209){
		                    		$('#myModal').modal();
		                    		$("#modal-title").css({"color":"yellow"});
		                    		$("#modal-title").text("警告");
		                    		$("#modal-message").text(data.message);
		                    	}else if(data.code == 500){
		                    		$('#myModal').modal();
		                    		$("#modal-title").css({"color":"red"});
		                    		$("#modal-title").text("服务器错误");
		                    		$("#modal-message").text(data.message);
		                    	}
		                    	$("#confirmBtn").text("确认更改");
		                		$("#confirmBtn").removeAttr("disabled");
		                    },
		                    error: function (XMLHttpRequest, textStatus, errorThrown) {
		                        $('#myModal').modal();
			                    $("#modal-title").css({"color":"red"});
			                    $("#modal-title").text("服务器错误");
			                    $("#modal-message").text("通讯异常");
			                    $("#confirmBtn").text("确认更改");
			                    $("#confirmBtn").removeAttr("disabled");
		                    },
        				})
        			}
        		});
        	})
        </script>
    </head>
    <body>
    	<div class="loginFrom">
    		<div class="logo"><img src="./images/logo-banner.png"></div>
    		<form class="form-horizontal" style="margin-left:20px; margin-top:30px;">
			  <div class="form-group">
			    <label for="emailInput" class="col-xs-2 required fixLabel">邮箱</label>
			    <div class="col-md-6 col-xs-10">
			      <input type="text" class="form-control setWidth" id="emailInput" placeholder="用户已验证邮箱">
			    </div>
			  </div>
			  <div id="nextForm">
			  <div class="form-group">
			  	<div class="col-sm-offset-2 col-xs-2"></div>
			    <div class="col-sm-offset-2 col-xs-10">
			      <button type="button" id="nextBtn" class="btn btn-primary btn-block btn-lg setWidth">下一步</button>
			    </div>
			  </div>
			  </div>
			  <div id="confirmForm" hidden="hidden">
				  <div class="form-group">
				    <label for="passInput" class="col-xs-2 required fixLabel">密码</label>
				    <div class="col-md-6 col-xs-10">
				      <input type="password" class="form-control setWidth" id="passInput" placeholder="新密码">
				    </div>
				  </div>
				  <div class="form-group">
				    <label for="confirmInput" class="col-xs-2 required fixLabel">验证码</label>
				    <div class="col-md-6 col-xs-10">
				      <input type="text" class="form-control setWidth" id="confirmInput" placeholder="邮箱验证码">
				    </div>
				  </div>
				  <br/>
				  <div class="form-group">
				  	<div class="col-sm-offset-2 col-xs-2"></div>
				    <div class="col-sm-offset-2 col-xs-10">
				      <button type="button" id="confirmBtn" class="btn btn-primary btn-block btn-lg setWidth">确认更改</button>
				    </div>
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