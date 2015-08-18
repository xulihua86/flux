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
	   		url:"<%=path%>/exchange/exchangePhoneType",
	   		data : {
				phone : phone
			},
			success : function(data) {
				if(data.operatorType==""){
					alert('请输入正确手机号,亲~');
					return;
				}
				if('${operatorType}'.indexOf(data.operatorType) >= 0){
					alert('亲，抱歉无法为该运营商手机号充值，请换一个手机号，或返回存到流量账户哦~');
					return;
				}
				$('.am-dimmer').click(function(){
					$(this).hide();
				});
				
				$('#confirm').find("span").eq(0).text(phone);

				var effect_type = $('input:radio[name=docInlineRadio]:checked').val();
				if(effect_type==null ||effect_type==''){
					alert('亲,请选择生效方式');
					return;
				}
				if(effect_type == 0){
					effect_type_text ="立即生效";
				}else{
					effect_type_text ="次月生效";
				}
				$('#confirm').find("span").eq(1).text(effect_type_text);
				
				//confirm事件实现
				$('#my-confirm').modal({
			        onConfirm: function(options) {
			        	effect_type = $('input:radio[name=docInlineRadio]:checked').val();
						$.ajax({
							cache:false,
							type:'POST',
					   		dataType: 'json',
					   		url:"<%=path%>/exchange/exchangePhone",
							data : {
								openId : '${openId}',
								phone : phone,
								activateType : effect_type,
								accessCode : '${accessCode}'
							},
							success : function(data) {
								if(data.success=='ture'){
									if(effect_type == 0){
						    			document.location.href = "exchangePhoneDataSuccess?openId="+'${openId}';
									}else{
										document.location.href = "exchangePhoneDataSuccessN?openId="+'${openId}';
									}
								} else {
									document.location.href = "exchangePhoneDataFailure";
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
					<label for="doc-ipt-email-1">请输入11位手机号码</label>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="5%"><input type="submit" name="" value="+86"
								class="am-btn am-radius am-btn-primary"></td>
							<td width="94%"><input type="tel" placeholder="请输入手机号"
								id="phone" class="am-form-field am-btn-sm"></td>
						</tr>
					</table>
				</div>

				<div class="am-form-group">
					<label class="am-radio-inline"> <input type="radio"
						value="0" name="docInlineRadio" checked>
						本月生效
					</label> <label class="am-radio-inline"> <input type="radio"
						value="1" name="docInlineRadio" >下月生效
					</label>

				</div>

				<div class="am-form-group">
					<button id="doc-confirm-toggle-2"
						class="am-btn am-btn-warning am-radius am-btn-block ">充&nbsp;&nbsp;值</button>
				</div>
			</fieldset>
		</div>
	</div>
	<!--点击充值后弹出框代码-->
	<div class="am-modal am-modal-confirm" tabindex="-1" id="my-confirm">
		<div class="am-modal-dialog">
			<div id="confirm" class="am-modal-bd">
				充值到 <span></span><br /> <span></span>
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
