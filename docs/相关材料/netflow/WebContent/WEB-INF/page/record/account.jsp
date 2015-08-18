<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<title>退存</title>
<meta name="renderer" content="webkit">
<link rel="stylesheet" href="<%=path%>/resource/assets/css/amazeui.min.css"/>
<link rel="stylesheet" href="<%=path%>/resource/assets/css/app.css" />
<link rel="stylesheet" href="<%=path%>/resource/assets/css/amazeui.css" />
<link rel="stylesheet" href="<%=path%>/resource/assets/css/style.css"/>  
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="<%=path%>/resource/assets/js/jquery.min.js"></script>
<script src="<%=path%>/resource/assets/js/amazeui.js"></script>
<script src="<%=path%>/resource/assets/js/handlebars.min.js"></script>
<style type="text/css">
</style>
<script type="text/javascript">
function go(){
	//测试
	//document.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx0d9e108d969fb6e6&redirect_uri=http%3A%2F%2Fsongyaxd.oicp.net%2Fnetflow%2Fuser%2FtoList&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
	//正式
	//document.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx137810a045c9a71e&redirect_uri=http%3A%2F%2Ffreeliu.orientalwisdom.com%2Fnetflow%2Fuser%2FtoList&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
	var oauth2url = '${oauth2url}';
 	//流量账户
	var mycount_toList = '${wechatPlat}'+'${mycount_toList}';
	mycount_toList = encodeURI(mycount_toList);
	var fullUrl = oauth2url.replace("REDIRECTURL",mycount_toList);
	window.location.href=fullUrl;
}
</script>
</head>
<body>
</br>
</br>
<div class="am-g am-top1" style="text-align:center;">
	<div class="am-text-xl cen1">赞一个,<br />
	您的流量账户又多了<span class="c1" style="color:red;"><b>${coinPrice}</b></span> 个流量币 <br />
	<a class="c1" href="javascript:void(0)" onclick = "go()">快去看一下！</a></div>
</br>
<div class="bg_c1">
	<iframe src="<%=path%>/link" scrolling="no" width="100%" onload="this.height=this.contentWindow.document.documentElement.scrollHeight"></iframe>
</div>

</body>
</html>
