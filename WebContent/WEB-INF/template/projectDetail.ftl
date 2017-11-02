<!DOCTYPE html>
<html>
    <head>
        <title>
            项目详情-国家自然基金深度搜索引擎
        </title>
        <meta charset="utf-8">
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
				border: 1px solid silver;
				background: #0DA2C7;
				top:0;
				box-shadow:3px 5px 10px #0F5FC6;
			}
			.top-banner{
				border:1px solid trasparent;
				width: 1200px;
				height: 55px;
				margin:auto;
				line-height: 55px;
			}
			.top-banner img{
				width: 80px;
				margin-top: -10px;
				z-index: 96px;
			}
			.top-search{
				width: 400px;
				height: 50px;
				border: 1px solid trasparent;
				margin-left: 40px;
			}
			.top-user{
				margin-right: 20px;
				border: 1px solid trasparent;
				width: 120px;
				height: 50px;
			}
			.top-user span{
				margin-left: 20px;
				font-size: 16px;
				color: white;
			}
			.top-user a{
				color: white;
			}
			.center{
				width: 1200px;
				min-height: 600px;
				margin: auto;
				margin-top:55px;
				border: 1px solid transparent;
			}
			.project-intro{
				border:1px solid transparent;
				position: relative;
				margin-top: 58px;
				min-height: 120px;
				width: 100%;
				min-width: 1200px;
				background: #EEEEEE;
			}
			.prjoect-intro-contain{
				width:1200px;
				min-height:80px;
				border:1px solid transparent;
				margin:auto;
				margin-top:20px;
				margin-bottom:20px;
				padding-left:10px;
				padding-top:10px;
			}
			.project-name,.project-leader{
				max-width:1200px;
				word-wrap:break-word;
				font-size:18px;
				font-weight:bold;
				color:black;
			}
			.project-leader{
				margin-top: 5px;
				margin-bottom:5px;
				font-size:15px;
			}
			.project-leader-value{

			}
			.project-info-contain{
				border: 0;
				width: 1200px;
				height: 300px;
			}
			.project-info-contain-top{
				width:590px;
				height:30px;
				border:1px solid silver;
				border-radius:10px;
				background:#EEEEEE;
				line-height:30px;
				font-size:16px;
				font-weight:bold;
				padding-left:15px;
				color:black;
			}
			.project-info, .project-organization-info{
				border:1px solid silver;
				border-radius:10px;
				width:590px;
				height:300px;
			}
			.project-organization-info{
				margin-left:18px;
			}
			.project-info-contain .title{
				color:black;
				font-size:15px;
				text-align:right;
				width:120px;
				line-height:23px;
			}
			.project-info-contain .content{
				line-height:23px;
			}
			.list-banner{
				margin-top:40px;
				border:1px solid transparent;
				width:1200px;
				height:20px;
				line-height:20px;
			}
			.list-banner p{
				color:black;
				margin:0 20px;
				font-weight:bold;
				font-size:17px;
				line-height:20px;
			}
			.list-banner img{
				margin-top:8px;
			}
			.project-abstract-keyword{
				border:1px solid transparent;
				width:1200px;
				min-height:20px;
				margin-top:20px;
				line-height:20px;
				word-wrap:break-word;
				text-indent:2px;
				padding:5px 10px;
			}
			.bottom{
				width: 100%;
				min-width: 1200px;
				height: 60px;
				bottom:0;
				border: 1px solid silver;
				background: #FAFAFA;
				text-align: center;
				line-height: 30px;
				font-size: 16px;
				margin-top:40px;
			}
        </style>
        <script type="text/javascript">
        	$(function(){
        	
        		$("#searchBtn").click(function(){
        			showLoading();
        			var key = $("#queryStr").val();
        			window.location.href="./result.action?key="+key+"&sortName=pro_year";
        		});
        		
        	})
        	function dologin(){
        		window.location.href="./login.action?redirect="+window.location.href;
        	}
        </script>
    </head>
    <body>

        <div class="top">
        	<div class="top-banner">
        		<a href="./index.action"><img class="pull-left" src="./images/logo-left.png"></a>
        		<p class="pull-left" style="margin-left: 20px; color: white; font-size: 22px;">国家自然基金项目深度搜索引擎</p>
        		<div class="pull-left top-search">
        			<div class="input-group" >
			           <input id = "queryStr" type="text" class="form-control" placeholder="请输入关键词..." value="${key}" style="margin-top: 11px;">
			           <span class="input-group-btn" id="searchBtn">
			             <button class="btn btn-primary" type="button"><i class="icon icon-search" ></i>&nbsp;&nbsp;搜索</button>
			           </span>
			        </div>
        		</div>
        		<div class="top-user pull-right">
        		
        			<#if (userInfo.onlineStatus == 1)>
	            		<span>
	                    	<a href="./my.action">${userInfo.userName}</a>
	                	</span>
	            	<#else>
		                <span>
		                    <a href="javascript:dologin()">登陆</a>
		                </span>
		                <span>
		                    <a href="./register.action">注册</a>
		                </span>
	                </#if>
        		</div>
        	</div>
        </div>
        
        <div class="project-intro">
        	<div class="prjoect-intro-contain">
        		<div class="project-name">${(detailInfo.pro_name == 'null')?string('国家自然科学基金项目',detailInfo.pro_name)}</div>
        		<div class="project-leader">负责人：<span class="project-leader-value">${(detailInfo.leader_name == 'null')?string('未知',detailInfo.leader_name)}</span></div>
        	</div>
        </div>
        <div class="center">
        	<div class="project-info-contain">
        		<div class="project-info pull-left">
        			<div class="project-info-contain-top">项目信息:</div>
        			<table class="table table-borderless table-condensed table-fixed">
        				<tr>
        					<td class="title">项目批准号：</td>
        					<td class="content">${(detailInfo.pro_approvalNumber == 'null')?string('N/A',detailInfo.pro_approvalNumber)}</td>
        				</tr>
        				<tr>
        					<td class="title">项目批准年度：</td>
        					<td class="content">${(detailInfo.pro_year == 'null')?string('N/A',detailInfo.pro_year)}年</td>
        				</tr>
        				<tr>
        					<td class="title">项目类型：</td>
        					<td class="content">${(detailInfo.pro_type == 'null')?string('N/A',detailInfo.pro_type)}</td>
        				</tr>
        				<tr>
        					<td class="title">项目申请号：</td>
        					<td class="content">${(detailInfo.pro_applyCode == 'null')?string('N/A',detailInfo.pro_applyCode)}</td>
        				</tr>
        				<tr>
        					<td class="title">项目研究时间：</td>
        					<td class="content">${(detailInfo.pro_researchPeriod == 'null')?string('N/A',detailInfo.pro_researchPeriod)}</td>
        				</tr>
        				<tr>
        					<td class="title">项目基金：</td>
        					<td class="content">${detailInfo.pro_funding}</td>
        				</tr>
                    </table>
        		</div>
        		<div class="project-organization-info pull-left">
        			<div class="project-info-contain-top">机构信息:</div>
        			<table class="table table-borderless table-condensed table-fixed">
        				<tr>
        					<td class="title">机构代码：</td>
        					<td class="content">${(detailInfo.org_code == 'null')?string('N/A',detailInfo.org_code)}</td>
        				</tr>
        				<tr>
        					<td class="title">机构联合代码：</td>
        					<td class="content">${(detailInfo.org_unitcode == 'null')?string('N/A',detailInfo.org_unitcode)}</td>
        				</tr>
        				<tr>
        					<td class="title">机构名称：</td>
        					<td class="content">${(detailInfo.org_ChineseName == 'null')?string('N/A',detailInfo.org_ChineseName)}</td>
        				</tr>
        				<tr>
        					<td class="title">机构英文：</td>
        					<td class="content">${(detailInfo.org_EnglishName == 'null')?string('N/A',detailInfo.org_EnglishName)}</td>
        				</tr>
        				<tr>
        					<td class="title">机构省份：</td>
        					<td class="content">${(detailInfo.org_province == 'null')?string('N/A',detailInfo.org_province)}</td>
        				</tr>
        				<tr>
        					<td class="title">机构城市：</td>
        					<td class="content">${(detailInfo.org_city == 'null')?string('N/A',detailInfo.org_city)}</td>
        				</tr>
        				<tr>
        					<td class="title">机构地址：</td>
        					<td class="content">${(detailInfo.org_address == 'null')?string('N/A',detailInfo.org_address)}</td>
        				</tr>
        				<tr>
        					<td class="title">机构官网：</td>
        					<td class="content">${(detailInfo.org_url == 'null')?string('N/A',detailInfo.org_url)}</td>
        				</tr>
        			</table>
        		</div>
        	</div>
        	<div class="list-banner">
        		<img src="./images/banner-01.png" class="pull-left">
        		<img src="./images/banner-02.png" class="pull-left" style="margin-left:20px; margin-top:5px;">
        		<p class="pull-left">中文摘要</p>
        		<img src="./images/banner-01.png" class="pull-right">
        		<img src="./images/banner-02.png" class="pull-right" style="margin-right:20px;margin-top:5px;">
        	</div>
        	<div class="project-abstract-keyword"> 
        		${(detailInfo.pro_ChineseAbstract == 'null')?string('该项目的中文摘要暂缺',detailInfo.pro_ChineseAbstract)}
        	</div>

        	<div class="list-banner">
        		<img src="./images/banner-01.png" class="pull-left">
        		<img src="./images/banner-02.png" class="pull-left" style="margin-left:20px; margin-top:5px;">
        		<p class="pull-left">关键字</p>
        		<img src="./images/banner-01.png" class="pull-right">
        		<img src="./images/banner-02.png" class="pull-right" style="margin-right:20px;margin-top:5px;">
        	</div>

        	<div class="project-abstract-keyword"> 
					${(detailInfo.pro_ChineseSubjectWord == 'null')?string('该项目的中文关键字暂缺',detailInfo.pro_ChineseSubjectWord)}
        	</div>


        	<div class="list-banner">
        		<img src="./images/banner-01.png" class="pull-left">
        		<img src="./images/banner-02.png" class="pull-left" style="margin-left:15px; margin-top:5px;">
        		<p class="pull-left" style = "margin:0 10px;">ABSTRACT</p>
        		<img src="./images/banner-01.png" class="pull-right">
        		<img src="./images/banner-02.png" class="pull-right" style="margin-right:15px;margin-top:5px;">
        	</div>

			<div class="project-abstract-keyword">
		    	${(detailInfo.pro_EnglishAbstract == 'null')?string('该项目的英文摘要暂缺',detailInfo.pro_EnglishAbstract)}
		    </div>

        	<div class="list-banner">
        		<img src="./images/banner-01.png" class="pull-left">
        		<img src="./images/banner-02.png" class="pull-left" style="margin-left:15px; margin-top:5px;">
        		<p class="pull-left" style = "margin:0 20px;">keyWord</p>
        		<img src="./images/banner-01.png" class="pull-right">
        		<img src="./images/banner-02.png" class="pull-right" style="margin-right:15px;margin-top:5px;">
        	</div>

        	<div class="project-abstract-keyword"> 
        			${(detailInfo.pro_EnglishSubjectWord == 'null')?string('该项目的英文关键字暂缺',detailInfo.pro_EnglishAbstract)}
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