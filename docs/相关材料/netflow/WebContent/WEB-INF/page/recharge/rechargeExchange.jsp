<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String openId = request.getParameter("openId");
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-title" content="" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title></title>
<meta name="renderer" content="webkit">
<link rel="stylesheet"
	href="<%=path%>/resource/assets/css/amazeui.min.css" />
<link rel="stylesheet" href="<%=path%>/resource/assets/css/app.css" />
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="<%=path%>/resource/assets/js/jquery.min.js"></script>
<script src="<%=path%>/resource/assets/js/amazeui.js"></script>
<script type="text/javascript">
	$(function() {
		 
		$('#consume').click(function(e){
			var phone = $('#phone').val();
			var effect_type = $('input:radio[name=effectType]:checked').val();
			re=/^1[3578]\d{9}$/
			if(!re.test(phone)) {
				alert('请输入正确手机号,亲~');
				return;
			}
			
			if(effect_type==null ||effect_type==''){
				alert('亲,请选择生效方式');
				return;
			}
			//confirm事件实现
			var confirmStr;
			//显示confirm对话框
			if(effect_type==1){
				confirmStr="充值30M流量到"+phone+"立即生效";
				
			}else{
				
				confirmStr="充值30M流量到"+phone+"次月生效";
			}
			$("#confirmtext").text(confirmStr);
			$('#my-confirm').modal({
		        onConfirm: function(options) {
		          $('#mymodal').modal();
		  			location.href="<%=path%>/recharge/rechargeSuccess?phone="+phone+"&effect_type="+effect_type+"";
		  			$('#mymodal').modal();
		        },
		        onCancel: function() {
		        }
		      });
			<%-- $.ajax({
				cache:false,
				type:'POST',
		   		dataType: 'json',
		   		url:"<%=path%>/cards/consume",
				data : {
					card_id : '${card_id}',
					openId : '${openid}',
					encrypt_code : '${encrypt_code}',
					phone : phone,
					effect_type : effect_type
				},
				async : false,
				success : function(data) {
					if(data.success=='true'){
						document.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6868fd8dca43c3e2&redirect_uri=http%3A%2F%2Fcard.orientalwisdom.com%2Fudp%2Fcards%2Fsuccess&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
					}else{
						document.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6868fd8dca43c3e2&redirect_uri=http%3A%2F%2Fcard.orientalwisdom.com%2Fudp%2Fcards%2Ferror&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
					}
				}
			}); --%>
			
		});
	});
 
	wx.config({
		debug : false,
		appId : '${appId}',
		timestamp : '${timestamp}',
		nonceStr : '${nonceStr}',
		signature : '${signature}',
		jsApiList : [ 'hideMenuItems' ,'hideOptionMenu']
	});

	wx.ready(function() {
		wx.hideMenuItems({
			menuList : [ 'menuItem:copyUrl' ]
		//隐藏 复制连接按钮
		});
		wx.hideOptionMenu();
	});

	//wx.error(function(res) {
		//
	//	alert(res.errMsg);
	//});
</script>
</head>
<body>
	<header data-am-widget="header" class="am-header am-header-default">
		<h1 class="am-header-title">流量充值</h1>
	</header>
	<article data-am-widget="paragraph"
		class="am-paragraph am-paragraph-default"
		data-am-paragraph="{ tableScrollable: true, pureview: false }">
		<fieldset class="am-form">
			<!-- <legend>流量充值</legend> -->
			<div class="am-form-group">
				<label for="phone">手机号码：</label> 
				<input type="text" id="phone" placeholder="亲,请输入手机号" required />
				<hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
			</div>
			<div class="am-form-group">
				<label>生效方式： </label>
				 <label class="am-radio-inline">
				 	<input type="radio" value="1" name="effectType" required  data-am-ucheck checked> 立即生效
					</label> 
					<label class="am-radio-inline">
						<input type="radio" name="effectType" value="2"> 次月生效
				</label>
			</div>
			<button id="consume" class="am-btn am-btn-warning am-btn-block am-btn-xl">立即充值</button>
		</fieldset>
	</article>
	<hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
	
	<div class="am-modal am-modal-loading am-modal-no-btn" tabindex="-1" id="mymodal">
	  <div class="am-modal-dialog">
	    <div class="am-modal-hd">亲,请稍后...</div>
	    <div class="am-modal-bd">
	      <span class="am-icon-spinner am-icon-spin"></span>
	    </div>
	  </div>
	</div>
	<!-- confirm -->
	<div class="am-modal am-modal-confirm" tabindex="-1" id="my-confirm">
		<div class="am-modal-dialog">
			<div class="am-modal-bd" id="confirmtext"></div>
			<div class="am-modal-footer">
				<span class="am-modal-btn" data-am-modal-cancel>取消</span> <span
					class="am-modal-btn" data-am-modal-confirm>确定</span>
			</div>
		</div>
	</div>
</body>
</html>
