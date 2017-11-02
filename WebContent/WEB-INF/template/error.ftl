code:${errorMessage.code}<br/>
message:${errorMessage.errorMessage}
<!DOCTYPE html>
<html>
    <head>
        <title>
            whoops-国家自然基金深度搜索引擎
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
				background: #F9F9F9;
				font-family: Microsoft Yahei;
			}
			.top{
				width: 100%;
				min-width: 1200px;
				height: 55px;
				position: fixed;
				z-index: 99;
				border: 1px solid #f3f3f3;
				background: #F9F9F9;
				top:0;
				box-shadow:1px 1px 40px #f1f1f1;
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
				height: 700px;
				margin: auto;
				margin-top:55px;
				border: 1px solid transparent;
				overflow: hidden;
			}
			.center img{
				width: 1200px;
			}
			.note{
				position: relative;
				width: 400px;
				height: 170px;
				z-index: 9px;
				border:1px solid transparent;
				left:50%;
				margin-left: -210px;
				top: -230px;
				overflow:hidden; 
			}
			.message{
				width:400px;
				height: 120px;
				border-bottom:1px solid silver;
			}
			.option{
				line-height: 40px;
				text-align: center;
				font-size: 16px;
			}
			.option a:hover{
				text-decoration: none;
				color: #666666;
			}
			.note-message{
				width:400px;
				height: 80px;
				border: 1px solid transparent;
				line-height: 20px;
				padding: 0 10px;
				word-wrap:break-word;
			}
			.bottom{
				width: 100%;
				min-width: 1200px;
				height: 60px;
				z-index: 99px;
				position: fixed;
				bottom:0;
				border: 1px solid transparent;
				background: #ffffff;
				text-align: center;
				line-height: 30px;
				font-size: 16px;
			}

        </style>
        <script type="text/javascript">
        	$(function(){
        		
        	})
        	function back(){
        		window.history.back(-1);
        	}
        </script>
    </head>
    <body>

        <div class="top">
            <div class="top-banner">
                <span>
                    <a href="./login.action">登陆</a>
                </span>
                <span>
                    <a href="./register.action">注册</a>
                </span>
            </div>
        </div>
        <div class="center">
        	<img src="./images/error.png">
        	<div class="note">
        		<div class="message">
        			<h4>Note:</h4>
        			<div class="note-message"><b>Code</b>:&nbsp;&nbsp;${errorMessage.code}<br/><b>message</b>:&nbsp;&nbsp;${errorMessage.errorMessage}</div>
        		</div>
        		<div class="option">
        			<a href="./index.action"><i class="icon icon-home"></i>首页</a>
        			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        			<a href="javascript:back()"><i class="icon icon-reply">返回</i></a>
        		</div>
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
