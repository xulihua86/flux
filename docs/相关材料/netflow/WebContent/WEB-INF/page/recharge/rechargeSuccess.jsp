<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!-- Add to homescreen for Chrome on Android -->
<meta name="mobile-web-app-capable" content="yes">
<!-- Add to homescreen for Safari on iOS -->
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-title" content="Amaze UI" />
<link rel="apple-touch-icon-precomposed"
	href="assets/i/app-icon72x72@2x.png">
<title>手机充值</title>
<meta name="renderer" content="webkit">
<link rel="stylesheet"
	href="<%=path%>/resource/assets/css/amazeui.min.css" />
<link rel="stylesheet" href="<%=path%>/resource/assets/css/app.css" />
</head>
<script src="<%=path%>/resource/assets/js/jquery.min.js"></script>
<script src="<%=path%>/resource/assets/js/amazeui.js"></script>
<script type="text/javascript"></script>
<style type="text/css">
#div1 {
	font-family: Arial;
	border: 2px solid #379082;
	width: 90%;
	height: 50%;
	margin-left: 6%; padding-top : 10px;
	border-radius: 13px;
	text-align: center;
	background: #FFFFFF;
	padding-top: 10px
}

#div2 {
	font-family: Arial;
	margin-left: 4%;
	padding-top: 10px;
	text-align: center;
}

body {
	background: #EBEBEB;
}
</style>
<body>
	<br />
	<div id="div2">已提交，${comname}正在玩命处理</div>
	<br />
	<br />
	<div id="div1">
		亲选择的是${effect_type}。亲可以通过--<a href="#">充值记录</a>查询充值状态，也可以查询<a href="#">常见问题</a>，已得到相关提示帮助
	</div>
</body>
</html>