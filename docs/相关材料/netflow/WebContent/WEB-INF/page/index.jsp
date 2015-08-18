<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <title>首页</title>
    <meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta http-equiv="Cache-Control" content="no-siteapp" />
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-title" content="" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="renderer" content="webkit">
	
	<link rel="stylesheet" href="<%=path%>/resource/assets/css/amazeui.min.css"/>
    <link rel="stylesheet" href="<%=path%>/resource/assets/css/style.css"/>  
    <link rel="stylesheet" href="<%=path%>/resource/assets/css/body_style.css"/>
	
	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script src="<%=path%>/resource/assets/js/jquery.min.js"></script>
	<script src="<%=path%>/resource/assets/js/amazeui.js"></script>
	
  </head>
  <body>
  	<div class="am-g am-top1">
		<div style="margin-left: 15px;margin-right: 15px; background-color:#FFF; padding-bottom:10px;">
			<div>
				<img id="mainNews" alt="图片名称" class="am-img-thumbnail" src="<%=path%>/resource/assets/images/mainNews.jpg"/>
			</div>
			<iframe src="<%=path%>/link" scrolling="no" width="100%" onload="this.height=this.contentWindow.document.documentElement.scrollHeight"></iframe>
		</div>
	</div>
  </body>
</html>
