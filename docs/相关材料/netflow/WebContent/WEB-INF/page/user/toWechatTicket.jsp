<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>
<head lang="en">
  <meta charset="UTF-8">
  <title>卡券</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <meta name="format-detection" content="telephone=no">
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-siteapp" />
  
  
  <link rel="stylesheet" href="<%=basePath%>resource/assets/css/amazeui.min.css"/>
  <link rel="stylesheet" href="<%=basePath%>resource/assets/css/style.css"/>  
  <link rel="stylesheet" href="<%=basePath%>resource/assets/css/body_style.css"/>
  
  <script src="<%=basePath%>resource/assets/js/jquery.min.js"></script>
  <script src="<%=basePath%>resource/assets/js/amazeui.min.js"></script>
</head>




<body>
	<form id="form1" action="" method="get"></form>
    
    
    <div class="am-g am-u-sm-12" style="padding-top: 1em;">
		<!--内容1-->
		<div class="bg_c4">
			<sapn class="c5 am-u-center am-text-xl">已领取卡券列表</sapn>
			<div class="am-g c5 fon1">
				<div class="am-u-sm-6">&nbsp;</div>
				<div class="am-u-sm-6">&nbsp;</div>
				<div class="am-u-sm-6">&nbsp;</div>
				<div class="am-u-sm-6">&nbsp;</div>
			</div>
			<div style="padding-left: 1em; padding-right: 1em; padding-top: 2em;">
				<button id="flowCoin" 
                    class="am-btn am-btn-danger am-round am-btn-block am-btn-xs" onclick="toHaveReceive()">查看</button>
			</div>
		</div>
		<!--内容2-->
		<div class="bg_c4_2" id="phoneDataDiv">
			<sapn class="c5 am-u-center am-text-xl">未领取卡券列表</sapn>
			<div class="am-g c5 fon1">
				<div class="am-u-sm-6">&nbsp;</div>
				<div class="am-u-sm-6">&nbsp;</div>
				<div class="am-u-sm-6">&nbsp;</div>
				<div class="am-u-sm-6">&nbsp;</div>
			</div>
			<div style="padding-left: 1em; padding-right: 1em; padding-top: 2em;">
				<button id="phoneData" style="border-color:#1487D7;"
					class="am-btn am-btn-danger am-round am-btn-block am-btn-xs" onclick="toUnhaveReceive()">查看</button>
			</div>
		</div>
		
	</div>
    
    <script>

		function toHaveReceive(){
    		//$("#form1").attr("action",'<%=basePath%>'+"user/toHaveReceive?wechat_card_js=1"); //已领取
			//$("#form1").submit();
			
			window.location.href='<%=basePath%>'+"user/toHaveReceive?wechat_card_js=1";
    		
        }

    	function toUnhaveReceive(){
    		$("#form1").attr("action",'<%=basePath%>'+"user/toUnhaveReceive"); //未领取
    		$("#form1").submit();
        }
        
    </script>
</body>
</html>
