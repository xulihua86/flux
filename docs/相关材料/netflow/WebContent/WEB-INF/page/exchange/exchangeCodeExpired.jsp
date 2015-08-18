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
				} else if(data.success=='expired'){
	    			document.location.href = "exchangeCodeExpired?openId="+'${openId}';
				} else if(data.success=='used'){
	    			document.location.href = "exchangeCodeUsed?openId="+'${openId}';
				} else {
	    			document.location.href = "exchangeCodeFailure?openId="+'${openId}';
				}
			}
		});
	});
});
</script>
</head>
<body>

	<div class="am-g am-top1 am-u-sm-12">
		<fieldset class="am-form">
			<label for="doc-ipt-email-1">输入领取码进行验证</label>
			<div class="am-g">
				<div class="am-u-sm-9">
					<input type="text" id="accessCode" class="am-form-field am-btn-sm">
				</div>
				<div class="am-u-sm-3">
					<button id="check" class="am-btn am-btn-danger am-round">验证</button>
				</div>
			</div>
		</fieldset>
	</div>
	<br />
	<span class="am-u-center">亲，您的领取码已过期，请咨询发码方了解详情</span>
	
	<!--[if (gte IE 9)|!(IE)]><!-->
	<script src="<%=path%>/resource/assets/js/jquery.min.js"></script>
	<script src="<%=path%>/resource/assets/js/amazeui.min.js"></script>
	<!--<![endif]-->
</body>
</html>