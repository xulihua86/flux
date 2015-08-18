<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-title" content="" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>充值失败</title>
<meta name="renderer" content="webkit">
<link rel="stylesheet" href="<%=path%>/resource/assets/css/amazeui.min.css" />
<link rel="stylesheet" href="<%=path%>/resource/assets/css/style.css"/>
<link rel="stylesheet" href="<%=path%>/resource/assets/css/body_style.css"/>
<link rel="stylesheet" href="<%=path%>/resource/assets/css/app.css" />
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="<%=path%>/resource/assets/js/jquery.min.js"></script>
<script src="<%=path%>/resource/assets/js/amazeui.js"></script>
<style type="text/css">
body {
	background: #EBEBEB;
}
.am-u-sm-pd0{
	padding-left:0;
	padding-right:0;
}
</style>
<script type="text/javascript">
var coinPrice = '';
var currencyId = '';
$(function() {
	$('#btn').on('click', function(){
		var alertStr = '';
		var isBackToAccount = $("#isBackToAccount").val();
		//document.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx5c286b3c2e95a31b&redirect_uri=http%3A%2F%2Fjinzhoujiannan.xicp.net%2Fnetflow%2Frecharge%2Ftoexchange&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
		//document.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx137810a045c9a71e&redirect_uri=http%3A%2F%2Fsongyaxd.oicp.net%2Fnetflow%2Frecharge%2Ftoexchange&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
		if(isBackToAccount=='1'){
			alert("您已经回退到账户，无法重新充值");
			return;
		}
		//校验该条充值记录的充值状态，只有失败的状态才可以继续退存操作。
		$.ajax({
			cache:false,
			type:'POST',
   			dataType: 'json',
   			url:"<%=path%>/chargeRecord/getStatus",
			data : {
				orderId : '${orderId}'
			},
			async : false,
			success : function(data) {
				if(data.code=='000000'){
					if(data.status!='3'){
						alertStr = "您的充值状态已经改变，无法进行此操作，具体请查看您的充值记录。";
					}
				}else{
					document.location.href="<%=path%>/chargeRecord/wrongAccount";
				}
			}
		});
		if(alertStr!=''){
			alert(alertStr);
			return;
		}
		var oldRecchargeMobileNo = "${recchargeMobileNo}";
		var orderId = "${orderId}";
		var activeTypeId = "${activeTypeId}";
		//var openId = "${openId}";
		//var value ="oldRecchargeMobileNo="+oldRecchargeMobileNo+"|orderId="+orderId+"|activeTypeId="+activeTypeId +"|openId="+openId;
		var value ="oldRecchargeMobileNo="+oldRecchargeMobileNo+"|orderId="+orderId+"|activeTypeId="+activeTypeId;
		var url = "<%=path%>/chargeRecord/rechargeAgain?content="+value;
		url=encodeURI(url); 
		url=encodeURI(url); 
		$("#btnback").attr("disabled",true);
		document.location.href=url;
	});
	/*
	*点击退存流量账户按钮 
	*/
	$('#btnback').on('click', function(){
		var confirmStr = '';
		var alertStr = '';
		//获取可退存流量币个数
		$.ajax({
			cache:false,
			type:'POST',
   			dataType: 'json',
   			url:"<%=path%>/chargeRecord/account",
			data : {
				orderId : '${orderId}'
			},
			async : false,
			success : function(data) {
				if(data.status=='success'){
					coinPrice = data.coinAmount;
					confirmStr = "可退存流量币"+ coinPrice +"个到流量账户。";
				}else if(data.status=='false'){
					document.location.href="<%=path%>/chargeRecord/wrongAccount";
				}
			}
		});
		//校验该条充值记录的充值状态，只有失败的状态才可以继续退存操作。
		$.ajax({
			cache:false,
			type:'POST',
   			dataType: 'json',
   			url:"<%=path%>/chargeRecord/getStatus",
			data : {
				orderId : '${orderId}'
			},
			async : false,
			success : function(data) {
				if(data.code=='000000'){
					if(data.status!='3'){
						alertStr = "您的充值状态已经改变，无法进行此操作，具体请查看您的充值记录。";
					}
				}else{
					document.location.href="<%=path%>/chargeRecord/wrongAccount";
				}
			}
		});
		if(alertStr!=''){
			alert(alertStr);
			return;
		}
		//显示confirm对话框
		$("#confirmtext").text(confirmStr);
		$('#my-confirm').modal({
			onConfirm: function(options) {//确认
				//真正的退存操作
				$.ajax({
					cache:false,
					type:'POST',
		   			dataType: 'json',
		   			url:"<%=path%>/chargeRecord/restoreToFlowAccount",
					data : {//restoreToFlowAccount退存接口所需参数
						rechargeId:'${orderId}'//充值记录Id 
					},
					async : false,
					success : function(data) {
						if(data.status=='success'){
							$("#isBackToAccount").val("1");//已经回退到账户
							document.location.href="<%=path%>/chargeRecord/successAccount?coinPrice="+coinPrice;
						}else if(data.status=='false'){
							document.location.href="<%=path%>/chargeRecord/wrongAccount";
						}
					}
				});
				//document.location.href="<%=path%>/chargeRecord/getReturnBackAmount?orderId="+${orderId}+"&code="+currencyId+"&coinPrice="+coinPrice+"";
		    },
		    onCancel: function() {//取消
		    }
		 });
	});
	function back(){
		document.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6868fd8dca43c3e2&redirect_uri=http%3A%2F%2Fcard.orientalwisdom.com%2Fudp%2Fbocps%2Findex&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
	}
	
	function question(){
		//location.href="<%=path%>/question/question;
	}
});
</script>
</head>

<body>
	<div class="am-g am-top2">
		<div class="am-u-sm-centered">
		<div class="am-g am-text-sm">
		  		<div class="am-u-sm-8">${modifyTime}</div>
		  		<div class="am-u-sm-4 c2">${status}</div>
		  		<div class="am-u-sm-4 c1">${recchargeMobileNo}</div>
		  		<div class="am-u-sm-5 am-u-sm-pd0">${rechargeSnap}</div>
		        <div class="am-u-sm-3">${activeTypeName}</div>
		</div>
		<hr data-am-widget="divider" class="am-divider am-divider-default"/><br /><br />
		<button id="btn" type="button" class="am-btn am-btn-danger1 am-round am-btn-block ">重新充值</button>
		<button id="btnback" type="button" class="am-btn am-btn-primary am-round am-btn-block ">退存到流量账户</button><br />
		<div class="am-modal am-modal-confirm" tabindex="-1" id="my-confirm">
	  		<div class="am-modal-dialog">
			    <div class="am-modal-bd" id="confirmtext"></div>
			    <div class="am-modal-footer">
			      <span class="am-modal-btn" data-am-modal-cancel>取消</span>
			      <span class="am-modal-btn" data-am-modal-confirm>确定</span>
			    </div>
			</div>
		</div>
		<span class="c1 am-u-center"><a href="<%=basePath%>/user/toQuestion">常见问题</a></span><br /><br /><br /><br /><br />
		<span class="c1 am-u-center fon2">联系客服：400 1076 866</span>
		<input type = "hidden" id = "isBackToAccount" value = "0"></input>
		</div>
	</div>
</body>
</html>
