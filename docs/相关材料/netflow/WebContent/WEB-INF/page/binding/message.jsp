<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
    <title>绑定手机号</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-capable" content="yes"> 
<meta http-equiv="Cache-Control" content="no-siteapp" />
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-title" content="" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="renderer" content="webkit">
<link rel="stylesheet"
	href="<%=path%>/resource/assets/css/amazeui.min.css" />
<link rel="stylesheet" href="<%=path%>/resource/assets/css/style.css" />
<link rel="stylesheet"
	href="<%=path%>/resource/assets/css/body_style.css" />
<script src="<%=path%>/resource/assets/js/jquery.min.js"></script>
<script src="<%=path%>/resource/assets/js/amazeui.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="<%=path%>/resource/js/weixin.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$('a').click(function(e){
		var oauth2url = '${oauth2url}';//获取urlconfig.properties中通用的授权写法
		var toListUrl = '${wechatPlat}' + '${mycount_toList}';
		toListUrl = encodeURI(toListUrl);
		toListUrl = oauth2url.replace("REDIRECTURL", toListUrl);
		document.location.href = toListUrl; 
	});
	
   	$('#bindOther').click(function(e){
   		/*document.location.href = "https://open.weixin.qq.co/connect/oauth2/authorize?appid=wx137810a045c9a71e&redirect_uri=http%3A%2F%2Ffreeliu.orientalwisdom.com%2Fnetflow%2Fbinding%2FinitBinding&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";*/
    	var oauth2urlB = '${oauth2url}';//获取urlconfig.properties中通用的授权写法
		var initBindingUrl = '${wechatPlat}' + '${initBindingUrl}';
		initBindingUrl = encodeURI(initBindingUrl);
		bindingUrl = oauth2urlB.replace("REDIRECTURL", initBindingUrl);
		document.location.href = bindingUrl;
		/*$('#bindingUrl2').attr('href',bindingUrl);*/ 
   	});
	
});
    
</script>
</head>
<body>
<div class="am-g am-top1">
	<div class="am-u-sm-11 am-u-sm-centered am-u-center">
		<c:choose>
			<c:when test="${bindingState == 0 and (result == 2 or result == 0 or result == 5)}">
				<div align="center">
					<img src="<%=path%>/resource/assets/images/p02.jpg" alt="" class="am-img-responsive"><br />
				</div> 
			</c:when>
			<c:when test="${bindingState == 1 and (result == 2 or result == 0)}">
				<div align="center" class="am-img-responsive">
					<img src="<%=path%>/resource/assets/images/p03.jpg" alt="" ><br />
				</div> 
			</c:when>
			<c:otherwise>
				<div align="center"  class="am-img-responsive">
					<img src="<%=path%>/resource/assets/images/p01.jpg" alt=""><br />
				</div> 
			</c:otherwise>
		</c:choose>
		<h4 class="am-article-title am-text-xl">
			${stateMessage}<br /><br />
			手机号码：${phone}<br />
		</h4>
		<span>
			<c:if test="${bindingState == 0 and result == 2 and isBoundMobile == 'Y'}">
				流量币已到账，请到<br />个人中心 － <a class="c1">我的账户</a> 查看
			</c:if>
			<c:if test="${bindingState == 0 and isBoundMobile == 'N'}">
				请到个人中心 － <a class="c1">我的账户</a> 查看
			</c:if>
		</span>
		<c:if test="${bindingState == 1 and (result == 2 or result == 0)}">
			<div class="am-form-group">
			      <button id="bindOther" type="button" class="am-btn am-btn-primary am-radius am-btn-block ">绑定其他手机号</button>
			</div>
		</c:if>
		
	</div>
</div>


<%-- <div class="am-g am-top1">
<div class="am-u-sm-11 am-u-sm-centered am-u-center">
${stateMessage}<br />
<span class="c1">${phone}</span>

	<c:if test="${bindingState == 0 and result == 2 and isBoundMobile == 'Y'}">
		<div class="bg_c4_3">
			流量币已到账，请到<br />个人中心-<a style='color:yellow;'>我的账户</a>查看
		</div>
	</c:if>
	<c:if test="${bindingState == 1 and (result == 2 or result == 0)}">
		<br />
		<div>
			<a href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx137810a045c9a71e&redirect_uri=http%3A%2F%2Ffreeliu.orientalwisdom.com%2Fnetflow%2Fbinding%2FinitBinding&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect">
				<button class="am-btn am-btn-danger1 am-round am-btn-block">绑定其他手机号</button>
			</a>
		</div>
	</c:if>
	
</div>
	
<br />
<div class="bg_c1">
	<iframe src="<%=path%>/link" scrolling="no" width="100%" onload="this.height=this.contentWindow.document.documentElement.scrollHeight"></iframe>
</div>

</div> --%>



<!--[if lt IE 9]>
<script src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="assets/js/polyfill/rem.min.js"></script>
<script src="assets/js/polyfill/respond.min.js"></script>
<script src="assets/js/amazeui.legacy.js"></script>
<![endif]-->

<!--[if (gte IE 9)|!(IE)]><!--
<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/amazeui.min.js"></script>-->
<!--<![endif]-->
</body>
</html>
