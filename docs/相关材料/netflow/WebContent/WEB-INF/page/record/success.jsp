<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!doctype html>
<html>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!-- Add to homescreen for Chrome on Android -->
<meta name="mobile-web-app-capable" content="yes">
<!-- Add to homescreen for Safari on iOS -->
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-title" content="Amaze UI" />
<link rel="apple-touch-icon-precomposed" href="assets/i/app-icon72x72@2x.png">
<title>手机充值</title>
<meta name="renderer" content="webkit">

<link rel="stylesheet" href="<%=path%>/resource/assets/css/amazeui.min.css" />
<link rel="stylesheet" href="<%=path%>/resource/assets/css/style.css"/>  
<link rel="stylesheet" href="<%=path%>/resource/assets/css/body_style.css"/>
<link rel="stylesheet" href="<%=path%>/resource/assets/css/app.css" />
<script src="<%=path%>/resource/assets/js/jquery.min.js"></script>
<script src="<%=path%>/resource/assets/js/amazeui.js"></script>
<script type="text/javascript">
	var effect_type = "${effect_type}";//生效方式1是下月生效
	window.name = "over";
	function rechrge(){
		//window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6868fd8dca43c3e2&redirect_uri=http%3A%2F%2Fcard.orientalwisdom.com%2Fnetflow%2FchargeRecord&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
		var oauth2url = '${oauth2url}';
		//充值记录
		var chargeRecordUrl = '${wechatPlat}'+'${chargeRecordUrl}';
		chargeRecordUrl = encodeURI(chargeRecordUrl);
		var fullUrl = oauth2url.replace("REDIRECTURL",chargeRecordUrl);
		window.location.href=fullUrl;
	}
	$(function(){
		if("${effect_type}"=="立即生效"){
			$("#dysx").css("display","block");
			$("#xysx").css("display","none");
		}else{
			$("#dysx").css("display","none");
			$("#xysx").css("display","block");
		}
		
	});
</script>

<body>
<%-- <div class="am-g am-top1">
<div class="am-u-sm-centered am-u-center am-bottom1">
<span>已提交，运营商正在加速处理中</span><br /><br />
<div id = "dysx" class="bg_c4_3">亲选择的是${effect_type}，请亲耐心等待。充值成功后，运营商会进行短信提示。若24小时内未收到提示短信，可到个人中心—<a style='color:yellow;' href="javascript:rechrge()">充值记录</a>查看。</div>
<div id = "xysx" class="bg_c4_3">亲选择的是${effect_type}，若下月初未收到充值提示短信，可到个人中心—<a style='color:yellow;' href="javascript:rechrge()">充值记录</a>查看充值状态。。</div>
</div>
</div> --%>

<div class="am-g am-top1">
<div class="am-u-sm-centered am-u-center am-bottom1">
<div align="center"><img src="<%=path%>/resource/assets/images/p03.jpg" alt="#" class="am-img-responsive"></div><br />
<h3 class="am-article-title" style = "font-size: 18px;">已提交，运营商正在加速处理中</h3>
<div id = "dysx" class="jl_bgc4_2">
亲选择的是${effect_type}，请亲耐心等待一下。若长时间未收到提示短信，可到个人中心 —  <a href="javascript:rechrge()" class="c2">充值记录</a> 查看。 </div>
</div>
<div id = "xysx" class="jl_bgc4_2">
亲选择的是${effect_type}，若下月初未收到充值提示短信，可到个人中心 —  <a href="javascript:rechrge()" class="c2">充值记录</a> 查看。 </div>
</div>
<div class="bg_c1">
<iframe src="<%=path%>/link" scrolling="no" width="100%" onload="this.height=this.contentWindow.document.documentElement.scrollHeight"></iframe>
</div>
</div>
</body>
</html>