<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String openId = request.getParameter("openId");
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="format-detection" content="telephone=no">
<meta name="mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-title" content="" />
<title>领取码兑换</title>
<meta name="renderer" content="webkit">
<link rel="stylesheet"
	href="<%=path%>/resource/assets/css/amazeui.min.css" />
<link rel="stylesheet" href="<%=path%>/resource/assets/css/app.css" />
<link rel="stylesheet" href="<%=path%>/resource/assets/css/style.css" />
<link rel="stylesheet"
	href="<%=path%>/resource/assets/css/body_style.css" />
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="<%=path%>/resource/assets/js/jquery.min.js"></script>
<script src="<%=path%>/resource/assets/js/amazeui.js"></script>
<script type="text/javascript" src="<%=path%>/resource/js/weixin.js"></script>
<!-- <script type="text/javascript">
	window.onload = function() {
		if (isWeiXin()) {
			var p = document.getElementsByTagName('p');
			p[0].innerHTML = window.navigator.userAgent;
		}
	}
	function isWeiXin() {
		var ua = window.navigator.userAgent.toLowerCase();
		if (ua.match(/MicroMessenger/i) == 'micromessenger') {
			return true;
		} else {
			return false;
		}
	}
</script> -->
<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?789d2116cf568ce6c4aab600f7f7b62b";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
</script>
<script type="text/javascript">
$(function() {
	$('#check').click(function(e){
		var accessCode = $('#accessCode').val();
		accessCode = accessCode.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
		if(accessCode=="") {
			alert('请输入领取码,亲~');
			return;
		}
		$.ajax({
			cache:false,
			type:'POST',
	   		dataType: 'json',
	   		url:"<%=path%>/exchange/checkAccessCode",
			data : {
				openId : '${openId}',
				accessCode : accessCode
			},
			success : function(data) {
				if(data.success=='success'){
	    			document.location.href = "exchangeCodeSuccess?accessCode="+accessCode+"&openId="+'${openId}';
				} else {
					$('#prompt').find("span").eq(0).text(data.success);
					$('#prompt').show();
				}
			}
		});
	});
});
</script>
</head>
<body>

	<div class="am-g am-top1">
		<fieldset class="am-form">
			<label for="doc-ipt-email-1">输入领取码验证</label>
			<div class="am-form-group">
				<input type="text" id="accessCode" class="am-form-field am-btn-sm"
					placeholder="输入领取码">
			</div>
			<div class="am-form-group">
				<button id="check"
					class="am-btn am-btn-primary am-radius am-btn-block ">验&nbsp;&nbsp;&nbsp;证</button>
			</div>

		</fieldset>
	</div>

	<br />
	
	<div id="prompt" align="center" style = "display:none;">
		<img src="<%=path%>/resource/assets/images/p10.jpg" alt="" class="am-img-responsive"><br />
		<span class="am-u-center"></span>
	</div>
	
	<!--[if (gte IE 9)|!(IE)]><!-->
	<script src="<%=path%>/resource/assets/js/jquery.min.js"></script>
	<script src="<%=path%>/resource/assets/js/amazeui.min.js"></script>
	<!--<![endif]-->
</body>
</html>