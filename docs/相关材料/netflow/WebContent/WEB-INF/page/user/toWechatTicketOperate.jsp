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
  <title>使用卡券</title>
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
	<form id="form1" action="" method="post">
        <input type="hidden" name="card_id" id="card_id" value="${card_id}" />
        <input type="hidden" name="card_code" id="card_code" value="${card_code}" />
        <%-- <input type="hidden" name="openId" id="openId" value="${openId}" /> --%>
    </form>
    
    
    <div class="am-g am-u-sm-12" style="padding-top: 1em;">
		<!--内容1-->
		<div class="bg_c4">
			<sapn class="c5 am-u-center am-text-xl">可换流量币<span> ${result[0].quantity}</span>个</sapn>
			<div class="am-g c5 fon1">
				<div class="am-u-sm-6">.&nbsp;随用随充</div>
				<div class="am-u-sm-6">.&nbsp;拆分更小流量包</div>
				<div class="am-u-sm-6">.&nbsp;与好友分享</div>
				<div class="am-u-sm-6">.&nbsp;兑换其它商品</div>
			</div>
			<div style="padding-left: 1em; padding-right: 1em; padding-top: 2em;">
				<button id="flowCoin" 
                    class="am-btn am-btn-danger am-round am-btn-block am-btn-xs" onclick="exchageTraffic()">存到流量账户</button>
			</div>
		</div>
		<!--内容2-->
		<div class="bg_c4_2" id="phoneDataDiv">
			<sapn class="c5 am-u-center am-text-xl">直接充值到手机</sapn>
			<div class="am-g c5 fon1">
				<div class="am-u-sm-4">
					<img src="<%=path%>/resource/assets/images/yys1.png" width="27"
						height="28" alt="移动">&nbsp;<span> ${result[0].cmccSize}</span>MB
				</div>
				<div class="am-u-sm-4">
					<img src="<%=path%>/resource/assets/images/yys2.png" width="29"
						height="27" alt="联通">&nbsp;<span> ${result[0].cuccSize}</span>MB
				</div>
				<div class="am-u-sm-4">
					<img src="<%=path%>/resource/assets/images/yys3.png" width="29"
						height="27" alt="电信">&nbsp;<span> ${result[0].ctccSize}</span>MB
				</div>
			</div>
			<div style="padding-left: 1em; padding-right: 1em; padding-top: 2em;">
				<button id="phoneData" style="border-color:blue;"
					class="am-btn am-btn-danger am-round am-btn-block am-btn-xs" onclick="rechargeToPhone()">充值到手机</button>
			</div>
		</div>
		
	</div>
    
    <script>

    	function rechargeToPhone(){
    		$("#form1").attr("action",'<%=basePath%>'+"cards/toRechargePhone"); //充值到手机
    		$("#form1").submit();
        }
        function exchageTraffic(){
    		$("#form1").attr("action",'<%=basePath%>'+"user/exeExchageTraffic"); //兑换流量币
    		$("#form1").submit();
        }
    </script>
</body>
</html>
