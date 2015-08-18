<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<title>存到流量账户</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="stylesheet"
	href="<%=path%>/resource/assets/css/amazeui.min.css" />
<link rel="stylesheet" href="<%=path%>/resource/assets/css/style.css" />
<link rel="stylesheet"
	href="<%=path%>/resource/assets/css/body_style.css" />
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="<%=path%>/resource/assets/js/jquery.min.js"></script>
<script src="<%=path%>/resource/assets/js/amazeui.js"></script>
<script src="<%=path%>/resource/assets/js/handlebars.min.js"></script>
<script src="<%=path%>/resource/assets/js/amazeui.widgets.helper.js"></script>
<script type="text/javascript" src="<%=path%>/resource/js/weixin.js"></script>
<script type="text/javascript">
  	function gotogive(){
  		var oauth2url = '${oauth2url}';
  		//流量账单
		var mycount_toTrafficBill = '${wechatPlat}'+'${mycount_toTrafficBill}';
  		mycount_toTrafficBill = encodeURI(mycount_toTrafficBill);
  		var fullUrl = oauth2url.replace("REDIRECTURL",mycount_toTrafficBill);
		window.location.href=fullUrl;
  	}
</script>
</head>
<body>

	<div class="am-g am-top1">
		<div class="am-u-sm-centered am-u-center am-bottom1">
			<div align="center">
				<img src="<%=path%>/resource/assets/images/p12.jpg" alt="" class="am-img-responsive">
			</div>

			<h3>
				<b class="c1">赞一个</b>，您的流量账户又多了<b class="c1">${quantity}</b>个流量币～
			</h3>

			<div class="am-form-group">
				<button type="submit"
					class="am-btn am-btn-primary am-radius am-btn-block"
					onclick="gotogive()">去查看</button>
			</div>

			<div class="bg_c1">
				<iframe src="<%=path%>/link" scrolling="no" width="100%" onload="this.height=this.contentWindow.document.documentElement.scrollHeight"></iframe>
			</div>
		
		</div>
		
	</div>

<!--[if lt IE 9]>
<script src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="assets/js/polyfill/rem.min.js"></script>
<script src="assets/js/polyfill/respond.min.js"></script>
<script src="assets/js/amazeui.legacy.js"></script>
<![endif]-->

		<!--[if (gte IE 9)|!(IE)]><!-->
		<script src="<%=path%>/resource/assets/js/jquery.min.js"></script>
		<script src="<%=path%>/resource/assets/js/amazeui.min.js"></script>
		<!--<![endif]-->
</body>
</html>
