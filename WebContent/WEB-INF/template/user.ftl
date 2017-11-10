<!DOCTYPE html>
<html>
    <head>
        <title>
            个人中心-国家自然基金深度搜索引擎
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
				width:100%;
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
			.left-center{
				position:relative;
				width: 190px;
				height: auto;
				border:1px solid silver;
				float:left;
				display:inline-block;
				padding-bottom:30px;
			}
			.list{
				margin-left:10px;
				position:relative;
				width: 990px;
				min-height: 600px;
				border:1px solid silver;
				float:left;
				display:inline-block;
			}
			.center{
				display:inline-block;
				position:relative;
				width: 1200px;
				height:auto;
				left:50%;
				margin-left:-600px;
				margin-top:55px;
				border: 0;
				padding-top:30px;
				border:0;
			}
			.user-logo{
				width:120px;
				margin:auto;
				border:1px solid silver;
				margin-top:30px;
			}
			.user-logo img{
				width:120px;
			}
			.user-name{
				margin:auto;
				width:120px;
				margin-top:10px;
				line-height:30px;
				text-align:center;
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
				margin-top:20px;
				padding-bottom:20px;
				display:inline-block;
			}
			.setWidth{
				width:140px;
				margin:auto;
				margin-top:10px;
			}
        </style>
        <script type="text/javascript">
        	$(function(){
        		$("#confirmBtn").click(function(){
        			showLoading();
        			$.ajax({
		                    url: "./dologout.action",
		                    type: "post",
		                    data: {
		                    },
		                    async: "false",
		                    dataType: "json",
		                    success: function (data) {
		                    	if(data.code == 200){
		                    		console.log("登陆成功");
		                    		window.location.href="./login.action";
		                    	}
		                    },
		                    error: function (XMLHttpRequest, textStatus, errorThrown) {
		                        $('#myModal').modal();
		                    	$("#modal-title").css({"color":"red"});
		                    	$("#modal-title").text("服务器错误");
		                    	$("#modal-message").text("通讯异常");
		                    	$("#submitBtn").text("登陆");
		                		$("#submitBtn").removeAttr("disabled");
		                    },
        				});
        		});
        	})
        </script>
    </head>
    <body>

        <div class="top">
        	<div class="top-banner">
        		<a href="./index.action"><img class="pull-left" src="./images/logo-left.png"></a>
        		<p class="pull-left" style="margin-left: 20px; color: white; font-size: 22px;">国家自然基金项目深度搜索引擎</p>
        	</div>
        </div>
        <div>
        <div class="center">
        	<div class="left-center">
        		<div class="user-logo">
        			<img src="./images/default_icon.png">
        		</div>
        		<div class="user-name">${userInfo.userName}</div>
        		<div class="user-quit">
        		<button type="button" id="confirmBtn" class="btn btn-primary btn-block setWidth">退出登陆</button>
        		</div>
        	</div>
        	<div class="list">
        		<!--  头部信息 -->
				<header>
			    	<h3><i class="icon-list-ul"></i> 收藏相关结果：<small>${totals}条</small></h3>
			  	</header>

			  	<#if (totals > 0) >
			  	<!-- 中间列表 -->
			  	<div class="items items-hover">
				   	<#assign list= listInfo />
					<#list list.items as item>
						<div class="item">
						    <div class="item-heading">
							    <div class="pull-right label label-primary">${(item.pro_year == 'null')?string('未知',item.pro_year)}</div>
							    	<h4><a href="./project.action?id=${item.pro_approvalNumber}">${(item.pro_name == 'null')?string('国家自然科学基金项目',item.pro_name)}</a></h4>
							</div>
						    <div class="item-content">
						        <div class="content">
						        	${(item.pro_ChineseAbstract == 'null')?string('该项目的摘要暂时为空',item.pro_ChineseAbstract)}
						        </div>
						    </div>
						    <div class="item-footer">
						        <i class="icon-user"></i>&nbsp;
						        <a href="javascript:void(0);" class="text-muted label label-badge label-warning" data-toggle="tooltip" data-placement="right" title="机构：${(item.leader_organization == 'null')?string('不详',item.leader_organization)}；职称：${(item.leader_jobTitle == 'null' )?string('未知',item.leader_jobTitle)}">${(item.leader_name =='null' )?string('未知',item.leader_name)}</a>&nbsp;
						        <i class="icon-home"></i>&nbsp;
						        <a href="#" class="text-muted label label-success">${(item.leader_organization == 'null' )?string('未知',item.leader_organization)}</a>&nbsp; 
						        <i class="icon-tag"></i>&nbsp;
						        <a href="#" class="text-muted label label-info">${(item.pro_type == 'null' )?string('国家自然基金项目',item.pro_type)}</a>
						    </div>
					    </div>
					</#list>

				<!-- 底部页面-->
				<footer>
				    <ul class="pager">
				        <li class="previous">
				        <#if (pageNum == 1) >
				        <a href="javascript:void(0)" class="disabled">« 上一页</a></li>	
				        <#else>    
					    <a href="./result.action?key=${key}&sortName=${sortName}&pageNum=${pageNum - 1}&pageSize=${pageSize}">« 上一页</a></li>
				        </#if>
				        <#if (pageNum != 1) >
				        <li><a href="./result.action?key=${key}&sortName=${sortName}&pageNum=1&pageSize=${pageSize}">1</a></li>
					    </#if>
					    <#if (totalPage<=7) >
						    <#assign indexBeg = pageNum >
						    <#assign indexEnd = totalPage >
						    <#if (indexBeg <= indexEnd) >
							    <#list indexBeg..indexEnd as i > 
								    <#if pageNum == i>	   
			  						<li class="active">
			  							<a href="javascript:void(0)">${i}</a>
			  					    </li>
			  					    <#else>
			  					    <li class="active">
			  							<a href="./result.action?key=${key}&sortName=${sortName}&pageNum=${i}&pageSize=${pageSize}">${i}</a>
			  					    </li>
			  					    </#if>
								</#list>
							</#if>
					    <#else>   
					        <#if ((pageNum - 3) > 1) >
					        <li><a href="javascript:void(0)" class="disabled">⋯</a></li>
					        </#if>
						    <#if ((pageNum - 2) > 1) >
					        <li><a href="./result.action?key=${key}&sortName=${sortName}&pageNum=${pageNum - 2}&pageSize=${pageSize}">${pageNum - 2}</a></li>
					        </#if>
					        <#if ((pageNum - 1) > 1) >
					        <li><a href="./result.action?key=${key}&sortName=${sortName}&pageNum=${pageNum - 1}&pageSize=${pageSize}">${pageNum - 1}</a></li>
					        </#if>
					        
					        <li class="active"><a href="javascript:void(0)">${pageNum}</a></li>
					       
					        <#if ((pageNum + 1) < totalPage) >
					        <li><a href="./result.action?key=${key}&sortName=${sortName}&pageNum=${pageNum + 1}&pageSize=${pageSize}">${pageNum + 1}</a></li>
					        </#if>
					        <#if ((pageNum + 2) < totalPage) >
					        <li><a href="./result.action?key=${key}&sortName=${sortName}&pageNum=${pageNum + 2}&pageSize=${pageSize}">${pageNum + 2}</a></li>
					        </#if>
					        <#if ((pageNum + 3) < totalPage) >
					        <li><a href="javascript:void(0)" class="disabled">⋯</a></li>
					        </#if>
					    </#if>
				        <#if (pageNum != totalPage)>
				        <li><a href="./result.action?key=${key}&sortName=${sortName}&pageNum=${totalPage}&pageSize=${pageSize}">${totalPage}</a></li>
				        </#if>
				        <li class="next">
				        	<#if (pageNum == totalPage) >
				        	<a href="javascript:void(0)" class="disabled">下一页 »</a>
				        	<#else>
				        	<a href="./result.action?key=${key}&sortName=${sortName}&pageNum=${pageNum - 1}&pageSize=${pageSize}">下一页 »</a>
				        	</#if>
				        </li>
				    </ul>
				  </footer>
				  <#else>
				  
				  <div class="item">
				      <div class="item-heading">
					    <h3>未查询到相关记录</h3>
					  </div>
				  </div>
				    
				 </#if>
			</div>
        </div>
        <div class="clear"></div>
        <div class="clear"></div>
        <div class="bottom">
            <div class="bottom-info">
                国家自然基金项目深度搜索引擎<br/>
                版权所有：&copy;2017&nbsp;郭月盟
            </div>
        </div>
    </body>
</html>