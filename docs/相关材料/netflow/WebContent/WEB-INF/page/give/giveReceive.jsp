<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String openId = request.getParameter("openId");
	String receiveCode = request.getParameter("receiveCode");
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
<title>飞流流量公社</title>
<meta name="renderer" content="webkit">
<link rel="stylesheet" href="<%=path%>/resource/assets/css/amazeui.min.css" />
<link rel="stylesheet" href="<%=path%>/resource/assets/css/style.css"/>
<link rel="stylesheet" href="<%=path%>/resource/assets/css/body_style.css"/>  
<style type="text/css">
	.focuson{
		width: 100%;
		text-align: center;
	}
	.space_bottom{
		margin-bottom: 20px;
	}
	.focuson h2{
		color: #990000;
		margin: 0;
	}
</style>
<script src="<%=path%>/resource/assets/js/jquery.min.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="<%=path%>/resource/assets/js/amazeui.js"></script>
<script src="<%=path%>/resource/assets/js/handlebars.min.js"></script>
<script src="<%=path%>/resource/assets/js/amazeui.widgets.helper.js"></script>
<script type="text/javascript">
/* $(function(){
	$("#qrcode").hide();
	$("#qrcodelink").click(function(){
		$("#qrcode").toggle();
	})
}) */
</script>
</head>
<body>
	<%-- <div id = "main" class = "" style = "width:95%;margin: 0 auto 0 auto;">
		<h2>我送给你${coin}个流量币，快去飞流流量公社兑换流量吧！</h2>
	</div>
	<div class = "focuson">
		<h2>您的流量币领取码</h2>
		<h2>${receiveCode}</h2>
	</div>
	<article data-am-widget="paragraph" class="am-paragraph am-paragraph-default"
data-am-paragraph="{ tableScrollable: true, pureview: true }">
	<p>
		<dl>
		  <dt>流量领取方法</dt>
		</dl>
	</p>
	</article>
	<figure id = "qrcode" data-am-widget="figure" class="am am-figure am-figure-default ">
		  <img width="50%" height="50%" src="<%=path%>/resource/images/give/qrcode.jpg"/>
	</figure>
	<article data-am-widget="paragraph" class="am-paragraph am-paragraph-default"
data-am-paragraph="{ tableScrollable: true, pureview: true }">
	<p>
		<dl>
		  <dd>一、复制上方领取码或长按上方二维码，在弹出菜单内点击识别二维码；</dd>
		  <dd>二、关注飞流流量公社；</dd>
		  <dd>三、在公众平台内，点击领取码兑换；</dd>
		  <dd>四、填写您的流量币领取码，即可充值。</dd>
		</dl>
	</p>
	</article>
	 <div id = "panel" style = "width:100%;margin: 0 auto 0 auto;text-align: center;display: none;">
		
	</div> --%>
	
	<div class="am-g am-top1">
		<div class="am-u-sm-centered am-u-center">
		<div align="center"><img src="<%=path%>/resource/assets/images/p13.jpg" alt="" class="am-img-responsive"><br /></div>
		<span class="c1">领取码：</span><span class="c1">${receiveCode}</span><br />
		我送你<b class="c1"> ${coin}</b> 个流量币，快去飞流流<br />量公社兑换流量吧！
		            
		<p align="left" class="c1">流量领取方法<br /></p>
		
		<div align="center"><img src="<%=path%>/resource/images/give/qrcode.jpg" alt="" class="am-img-responsive"><br /></div>
		<p align="left" class="c3">
		一.复制上面领取码或长按上方二维码，在弹出菜单内点击识别二维码；<br />
		二.关注飞流流量公社；<br />
		三.在公众平台内，点击领取码兑换；<br />
		四.填写您的流量币领取码，即可领取<br />
		</p>
		</div>
	</div>
</body>
</html>