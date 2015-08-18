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
<head lang="en">
<meta charset="UTF-8">
<title>充值到手机</title>
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
$(function() {
	var effect_type_text;
	var phone;//手机号
	//充值按钮点击事件
	$('#doc-confirm-toggle-2').click(function(e){
		phone = $('#phone').val();
		/* re=/^1[3578]\d{9}$/
		if(!re.test(phone)) {
			alert('请输入正确手机号,亲~');
			return;
		} */
		$.ajax({
			cache:false,
			type:'POST',
	   		dataType: 'json',
	   		url:"<%=path%>/InternetConvention/exchangePhoneType",
	   		data : {
				phone : phone
			},
			success : function(data) {
				if(data.operatorType==""){
					alert('请输入正确手机号,亲~');
					return;
				}
				//if(data.operatorType!="cmcc"){
				//	alert('亲，抱歉无法为该运营商手机号充值，请更换为移动手机号码~');
				//	return;
				//}
				$('.am-dimmer').click(function(){
					$(this).hide();
				});
				
				$('#confirm').find("span").eq(0).text(phone);
				
				//confirm事件实现
				$('#my-confirm').modal({
			        onConfirm: function(options) {
						$.ajax({
							cache:false,
							type:'POST',
					   		dataType: 'json',
					   		url:"<%=path%>/InternetConvention/exchangePhone",
							data : {
								openId : '${openId}',
								productId : '${productId}',
								phone : phone,
								code : '${receiveCode}'
							},
							success : function(data) {
								if(data.success=='ture'){
						    		document.location.href = "internetConventionPhoneDataSuccess?openId="+'${openId}';
								} else {
									document.location.href = "internetConventionPhoneDataFailure?openId="+'${openId}';
								}
							}
						});
			        },
			        onCancel: function() {
			        	$('.am-dimmer').hide();
			        }
			    });
			}
		});
	});
});
</script>
</head>
<body>
	<div class="am-g am-top1">
		
		<div class="am-form">
			<fieldset>
				<div class="am-form-group">
					<label for="doc-ipt-email-1">请输入11位手机号码</label> <input type="tel"
						id="phone" placeholder="手机号码">
				</div>

				<span class="am-u-center"><span class="c1 fon3">可兑换移动30M/联通20M/电信30M</span></span>
				<br />
				<br />
				
				<div id="recharge_btn" class="doc-example">
					<button class="am-btn am-btn-danger1 am-round am-btn-block"
						id="doc-confirm-toggle-2">充值</button>
				</div>
			</fieldset>
		</div>
	</div>
	<!--点击充值后弹出框代码-->
	<div class="am-modal am-modal-confirm" tabindex="-1" id="my-confirm">
		<div class="am-modal-dialog">
			<div id="confirm" class="am-modal-bd">
				充值到 <span></span><br />
			</div>
			<div class="am-modal-footer">
				<span class="am-modal-btn" data-am-modal-cancel>取消</span> <span
					class="am-modal-btn" data-am-modal-confirm>确定</span>
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
