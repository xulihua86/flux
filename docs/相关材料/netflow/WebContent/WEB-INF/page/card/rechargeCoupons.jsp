<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<!doctype html>
<html>
<head lang="en">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-title" content="" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>优惠券充值</title>
<meta name="renderer" content="webkit">
<link rel="stylesheet" href="<%=path%>/resource/assets/css/amazeui.min.css" />
<link rel="stylesheet" href="<%=path%>/resource/assets/css/app.css" />
<link rel="stylesheet" href="<%=path%>/resource/assets/css/amazeui.css" />
<link rel="stylesheet" href="<%=path%>/resource/assets/css/style.css" />
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="<%=path%>/resource/iscroll/jquery-2.0.3.min.js"></script>
<script type="text/javascript" src="<%=path%>/resource/iscroll/iscroll.js"></script>
<script type="text/javascript" src="<%=path%>/resource/iscroll/iscrollAssist.js"></script>
</head>
<body style="background-color:#FFF">
<div class="bg_c1 am-g am-u-sm-12" style="padding-top:1em;">
<!--内容1-->
<div class="bg_c4">
<sapn class="c5 am-u-center am-text-xl" style="text-align:center;">可充流量币<span style="color:red;">30</span>个</sapn>
<div class="am-g c5 fon1">
    <div class="am-u-sm-6">.&nbsp;随用随充</div>
    <div class="am-u-sm-6">.&nbsp;拆分成更小流量包</div>
    <div class="am-u-sm-6">.&nbsp;与好友分享</div>
    <div class="am-u-sm-6">.&nbsp;兑换其它商品</div>
</div>

<div style="padding-left:1em; padding-right:1em;"><input type="submit" name="" value="存到流账户" class="am-btn am-btn-default am-round am-btn-block am-btn-xs"></div>
</div>
<!--内容2-->
<div class="bg_c5">
<sapn class="c5 am-u-center am-text-xl">直接充值到手机</sapn>
<div class="am-g c5 fon1">
    <div class="am-u-sm-4"><img src="<%=path%>/resource/assets/images/yys1.png" width="27" height="28" alt="移动">&nbsp;<span class="c1">30</span>MB</div>
    <div class="am-u-sm-4"><img src="<%=path%>/resource/assets/images/yys2.png" width="29" height="27" alt="移动">&nbsp;<span class="c1">30</span>MB</div>
    <div class="am-u-sm-4"><img src="<%=path%>/resource/assets/images/yys3.png" width="29" height="27" alt="移动">&nbsp;<span class="c1">30</span>MB</div>
</div>
<div style="padding-left:1em; padding-right:1em;"><input type="submit" name="" value="充值到手机" class="am-btn am-btn-default am-round am-btn-block am-btn-xs"></div>
</div>
<br /><br />
<input type="submit" name="" value="转赠好友" class="am-btn am-btn-success am-round am-btn-block ">

</div>

<!--[if lt IE 9]>
<script src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<![endif]-->

</body>
</html>

