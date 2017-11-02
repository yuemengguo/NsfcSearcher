<!DOCTYPE html>
<html>
    <head>
        <title>
            国家自然基金深度搜索引擎
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
			.top{
				width: 100%;
				min-width: 1200px;
				height: 55px;
				position: fixed;
				z-index: 99;
				border: 1px solid #f4f4f4;
				background: #ffffff;
				top:0;
				box-shadow:1px 1px 20px #f3f3f3;
			}
			.top-banner{
				width:120px;
				height: 55px;
				display: inline-block; 
				border: 0;
				line-height: 55px;
				float: right;
				margin-right: 50px;
				text-align: center;
			}
			.top-banner span{
				margin-left: 15px;
				font-size: 16px;
			}

			.center{
				width: 1200px;
				height: 600px;
				margin: auto;
				margin-top:55px;
				border: 1px solid white;
			}
			.bottom{
				width: 100%;
				min-width: 1200px;
				height: 60px;
				z-index: 99px;
				position: fixed;
				bottom:0;
				border: 1px solid silver;
				background: #ffffff;
				text-align: center;
				line-height: 30px;
				font-size: 16px;
			}
			.center-banner{
				border:0;
				width:300px;
				height:88px;
				margin:auto;
				margin-top: 80px;
			}
			.center-search{
				border: 0;
				width:600px;
				height: 45px;
				margin:auto;
			}
			.center-nsfc-logo{
				border: 1px solid silver;
				width:300px;
				height: 180px;
				margin:auto;
				margin-top: 60px;
			}
			.center-nsfc-logo img{
				width: 100px;
				margin-top: 30px;
				position: relative;
				left: 50%;
				margin-left: -50px;
			}
			.center-nsfc-logo p{
				margin-top: 10px;
				text-align: center;
			}
        </style>
        <script type="text/javascript">
        	$(function(){
        		$(document).keyup(function(event){
        			
					if(event.keyCode ==13){
						var key = $("#queryStr").val();
						showLoading();
						window.location.href="./result.action?key="+key+"&sortName=pro_year";
					}
				});
        		$("#searchBtn").click(function(){
        			showLoading();
        			var key = $("#queryStr").val();
        			window.location.href="./result.action?key="+key+"&sortName=pro_year";
        		});
        	})
        </script>
    </head>
    <body>

        <div class="top">
            <div class="top-banner">
            	<#if userInfo.onlineStatus == 1>
            		<span>
                    	<a href="./my.action">${userInfo.userName}</a>
                	</span>
            	<#else>
	                <span>
	                    <a href="./login.action">登陆</a>
	                </span>
	                <span>
	                    <a href="./register.action">注册</a>
	                </span>
                </#if>
            </div>
        </div>
        <div class="center">
        	<div class="center-banner">
        		<img src="./images/logo-banner.png">
        	</div>
        	<div class="center-search">
        		<div class="input-group">
			      <input id = "queryStr" type="text" class="form-control" placeholder="请输入关键词..." style="height: 45px; font-size: 16px; line-height: 45px;">
			      <span class="input-group-btn" id="searchBtn">
			        <button class="btn btn-primary" type="button" style="height: 45px; font-size: 18px;"><i class="icon icon-search" style="font-size: 18px;"></i>&nbsp;&nbsp;搜索</button>
			     </span>
			    </div>
        	</div>
        	<div class="center-nsfc-logo">
        		<img src="./images/nsfc-logo.jpg">
        		<p>国家自然基金</p>
        	</div>
        </div>
        <div class="bottom">
            <div class="bottom-info">
                国家自然基金项目深度搜索引擎<br/>
                版权所有：&copy;2017&nbsp;郭月盟
            </div>
        </div>
    </body>
</html>