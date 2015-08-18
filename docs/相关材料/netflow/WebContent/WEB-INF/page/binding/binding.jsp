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
  <head>
    <title>绑定手机号</title>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <meta name="format-detection" content="telephone=no">
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="stylesheet"
	href="<%=path%>/resource/assets/css/amazeui.min.css" />
<link rel="stylesheet" href="<%=path%>/resource/assets/css/style.css" />
<link rel="stylesheet"
	href="<%=path%>/resource/assets/css/body_style.css" />

  <script>
    var _hmt = _hmt || [];
    (function() {
      var hm = document.createElement("script");
      hm.src = "//hm.baidu.com/hm.js?789d2116cf568ce6c4aab600f7f7b62b";
      var s = document.getElementsByTagName("script")[0]; 
      s.parentNode.insertBefore(hm, s);
    })();
  </script>

<script src="<%=path%>/resource/assets/js/jquery.min.js"></script>
<script src="<%=path%>/resource/assets/js/amazeui.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="<%=path%>/resource/js/weixin.js"></script>
<script type="text/javascript">
var countdown;
$(document).ready(function(){
	$('#smsCheck').click(function(e){
		var phone = $('#phone').val();
		
		re=/^1[3578]\d{9}$/;
		if(!re.test(phone)) {
			alert('请输入正确手机号,亲~');
			return;
		} else {
			$.ajax({
				cache:false,
				type:'POST',
		   		url:"<%=path%>/binding/mobileCheck",
				data : {
					phone : phone
				},
				async : false,
				success : function(data) {
					//拿到返回的数据
					if(data != null && data.code == "100000"){
						alert("请输入的正确手机号,亲~");
						return;
					} else {
						$.ajax({
							cache:false,
							type:'POST',
					   		url:"<%=path%>/binding/smscheck",
							data : {
								phone : phone,
								codeType:0
							},
							async : false,
							success : function(data) {
								if(data != null && data.bindStatus == "Y"){
									alert("此手机号已被其他人绑定,请更换！");
									return;
								} else if(data != null) {
									alert("验证码已通过短信发送给您");
									countdown=60;
									settime();
								}
							}
						});
					}
				}
			});
		}
	});
	
	$('#binding').click(function(e){
		var phone = $('#phone').val();
		var sms = $('#checkNum').val();
		re=/^1[3578]\d{9}$/;
		if(!re.test(phone)) {
			alert('请输入正确手机号,亲~');
			return;
		}
		if(sms==null || sms==''){
			alert('请输入验证码，亲~');
			return;
		}
		$.ajax({
			cache:false,
			type:'POST',
	   		dataType: 'json',
	   		url:"<%=path%>/binding/bindingPhone",
			data : {
				phone : phone,
				code:sms
			},
			async : false,
			success : function(data) {
				if(data != null && data.result != null && data.result != ""){
					document.location.href="message?result="+data.result+
					"&bindingState="+data.bindingState+"&phone="+data.phone+"&isBoundMobile=" + $("#isBoundMobile").val();
				} else {
					alert("对不起，绑定失败，请退出至首页后重新进行操作！");
				}
				 
			}
		});

	});
});

function settime() { 
	if (countdown == 0) { 
		$("#smsCheck").removeAttr("disabled"); 
		$("#smsCheck").val("获取验证码");
	} else { 
		$("#smsCheck").attr('disabled',"true");
		$("#smsCheck").val("获取验证码(" + countdown + ")"); 
		countdown--; 
		setTimeout("settime()",1000);
	}
}
		
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
</script>
  </head>
  <body>
  	<input type="hidden" id="isBoundMobile" value="${isBoundMobile}" />

    <div class="am-g am-top1">
		<fieldset>
			<div class="am-form-group">
		   		<label for="doc-ipt-email-1">绑定手机号</label>
		   		<table border="0" cellspacing="0" cellpadding="0">
			  		<tr>
			    		<td width="5%"><div class="am-btn am-radius" style="background-color: #3ba4fc;"><font color="#fff">+86</font></div></td>
			    		<td width="94%"><input type="text" id="phone" placeholder="请输入手机号" class="am-form-field am-btn-sm"></td>
			  		</tr>
		  		</table>
		   	</div>
		   	<div class="am-form-group">
		   		<table border="0" cellspacing="0" cellpadding="0" width="100%">
		  			<tr>
		    			<td width="80%"><input type="text" id="checkNum" class="am-form-field am-btn-sm"></td>
		    			<td width="19%"><input type="button" id="smsCheck" name="smsCheck" value="获取验证码" class="am-btn am-radius am-btn-primary"></td>
		  			</tr>
		  		</table>
		   	</div>
		    <div class="am-form-group">
		    	<button type="button" id="binding" class="am-btn am-btn-primary am-radius am-btn-block ">绑定手机号</button>
		    </div>
		</fieldset>
	</div>
  </body>
</html>
