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
	/* var cmcc;
	var cucc;
	var ctcc;
	if('${cmccSize}' == '0' || '${cmccSize}' == 'null'){
		cmcc = 0;
	}else{
		cmcc = '${cmccSize}'
	}
	if('${cuccSize}' == '0' || '${cuccSize}' == 'null'){
		cucc = 0;
	}else{
		cucc = '${cuccSize}'
	}
	if('${ctccSize}' == '0' || '${ctccSize}' == 'null'){
		ctcc = 0;
	}else{
		ctcc = '${ctccSize}'
	} */
	if('${cmccSize}' == '0' && '${cuccSize}' == '0' && '${ctccSize}' == '0'){
		$('#phoneDataDiv').attr("hidden","hidden");
	}
	if('${includeFlowProduct}' == 'null' || '${includeFlowProduct}' == '0'){
		$('#wechatCardDiv').attr("hidden","hidden");
	}
	var accessCode = $('#accessCode').val();
	$('#flowCoinDiv').click(function(e){
		$.ajax({
			cache:false,
			type:'POST',
	   		dataType: 'json',
	   		url:"<%=path%>/exchange/exchangeFlowCoin",
			data : {
				openId : '${openId}',
				accessCode : accessCode
			},
			success : function(data) {
				if(data.success=='ture'){
	    			document.location.href = "exchangeFlowCoinSuccess?quantity="+'${coinAmount}'+"&openId="+'${openId}';
				} else {
	    			document.location.href = "exchangeFlowCoinFailure";
				}
			}
		});
	});
	$('#phoneDataDiv').click(function(e){
		var operatorType = "";
		if('${cmccSize}' == '0'){
			operatorType = operatorType + "cmcc";
		}
		if('${cuccSize}' == '0'){
			operatorType = operatorType + "cucc";
		}
		if('${ctccSize}' == '0'){
			operatorType = operatorType + "ctcc";
		}
		document.location.href = "exchangePhoneData?accessCode="+accessCode+"&openId="+'${openId}'+"&operatorType="+operatorType;
	});
	$('#wechatCardDiv').click(function(e){
		document.location.href = "exchangeWechatCard?accessCode="+accessCode+"&openId="+'${openId}'+"&wechatCardId="+'${wxId}';
	});
});
</script>
</head>
<body>


	<div class="am-g am-top1">
		<fieldset class="am-form">
			<label for="doc-ipt-email-1">输入领取码验证</label>
			<div class="am-form-group">
				<input type="text" id="accessCode" class="am-form-field am-btn-sm" disabled="disabled"
					 value="${receiveCode}" >
			</div>
			<div class="am-form-group">
				<button id="check" disabled="disabled"
					class="am-btn am-btn-primary am-radius am-btn-block ">验&nbsp;&nbsp;&nbsp;证</button>
			</div>

		</fieldset>
	</div>

	<br />

	<div>
		<!--内容1-->
		<div class="btnbg3" id="flowCoinDiv">
			<span class="am-text-xl">流量币<span>${coinAmount}</span>个</span>
			<div class="am-g c5 fon1">
			    <div class="am-u-sm-5" align="left">·&nbsp;随用随充</div>
			    <div class="am-u-sm-7" align="left">·&nbsp;拆分更小流量包</div>
			    <div class="am-u-sm-5" align="left">·&nbsp;与好友分享</div>
			    <div class="am-u-sm-7" align="left">·&nbsp;兑换其它商品</div>
			</div>
			<!-- <input type="submit" id="flowCoin" style="display:none;"
					class="am-btn am-btn-danger am-round am-btn-block am-btn-xs"> -->
		</div>
		<!--内容2-->
		<div class="btnbg4" id="wechatCardDiv">
			<span class="c5 am-u-center am-text-xl">微信卡券<span>${includeFlowProduct}</span>张</span>
			<div class="am-g c5 fon1">
				<div>${wxName}</div>
				<br />
			</div>
		</div>
		<!--内容3-->
		<div class="btnbg5" id="phoneDataDiv">
			<span class="c5 am-u-center am-text-xl">充值到手机</span>
			<div class="am-g c5 fon1">
				<div class="am-u-sm-4">
					<img src="<%=path%>/resource/assets/images/yys1.png" width="27"
						height="28" alt="移动">&nbsp;<span>${cmccSize}</span>MB
				</div>
				<div class="am-u-sm-4">
					<img src="<%=path%>/resource/assets/images/yys2.png" width="29"
						height="27" alt="联通">&nbsp;<span>${cuccSize}</span>MB
				</div>
				<div class="am-u-sm-4">
					<img src="<%=path%>/resource/assets/images/yys3.png" width="29"
						height="27" alt="电信">&nbsp;<span>${ctccSize}</span>MB
				</div>
			</div>
		</div>
	</div>

	<!--[if (gte IE 9)|!(IE)]><!-->
	<script src="<%=path%>/resource/assets/js/jquery.min.js"></script>
	<script src="<%=path%>/resource/assets/js/amazeui.min.js"></script>
	<!--<![endif]-->
</body>
</html>