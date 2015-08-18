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
<title>卡券</title>
<meta name="renderer" content="webkit">
<link rel="stylesheet" href="<%=path%>/resource/assets/css/amazeui.min.css" />
<link rel="stylesheet" href="<%=path%>/resource/assets/css/app.css" />
<link rel="stylesheet" href="<%=path%>/resource/assets/css/amazeui.css" />
<link rel="stylesheet" href="<%=path%>/resource/assets/css/style.css" />
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="<%=path%>/resource/iscroll/jquery-2.0.3.min.js"></script>
<script type="text/javascript" src="<%=path%>/resource/iscroll/iscroll.js"></script>
<script type="text/javascript" src="<%=path%>/resource/iscroll/iscrollAssist.js"></script>
<script type="text/javascript">
function rechargeCoupons(){
	location.href="<%=path%>/card/rechargeCoupons";
}
</script>
</head>
<body>
<div class="am-g am-u-sm-12" style="padding-top:1em;"> 
<a href="javascript:rechargeCoupons()"><img src="<%=path%>/resource/assets/images/ka1.png" alt="#" class="am-img-responsive" /></a> 
<a href="javascript:rechargeCoupons()"><img src="<%=path%>/resource/assets/images/ka2.png" alt="#" class="am-img-responsive" /></a> 
<a href="javascript:rechargeCoupons()"><img src="<%=path%>/resource/assets/images/ka2.png" alt="#" class="am-img-responsive" /></a> 
<a href="javascript:rechargeCoupons()"><img src="<%=path%>/resource/assets/images/ka2.png" alt="#" class="am-img-responsive" /></a> 
<a href="javascript:rechargeCoupons()"><img src="<%=path%>/resource/assets/images/ka2.png" alt="#" class="am-img-responsive" /></a> 
</div>



<!--[if lt IE 9]>
<script src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<![endif]-->
</body>
</html>
