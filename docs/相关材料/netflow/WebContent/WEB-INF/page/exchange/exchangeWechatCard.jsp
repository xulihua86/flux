<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String openId = request.getParameter("openId");
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-title" content="" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>微信卡券二维码</title>
<meta name="renderer" content="webkit">
<link rel="stylesheet" href="<%=path%>/resource/assets/css/amazeui.css" />
<link rel="stylesheet" href="<%=path%>/resource/assets/css/style.css" />
<script src="<%=path%>/resource/assets/js/jquery.min.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="<%=path%>/resource/assets/js/amazeui.min.js"></script>
<script type="text/javascript" src="<%=path%>/resource/js/weixin.js"></script>
<style type="text/css">
.body_bg, .am_body_bg {
	background: #b70e13;
}
</style>
<body class="am_body_bg">
	<article data-am-widget="paragraph"
		class="am-paragraph am-paragraph-default"
		data-am-paragraph="{ tableScrollable: true, pureview: true }">
		<img class="am-center"
			src="<%=path%>/resource/assets/images/wc/qrcodetip_011.jpg">
		<img src="${qrcodeUrl}" class="am-center" autofocus>
		<img class="am-center"
			src="<%=path%>/resource/assets/images/wc/qrcodetip_02.gif">
	</article>
</body>
</html>

