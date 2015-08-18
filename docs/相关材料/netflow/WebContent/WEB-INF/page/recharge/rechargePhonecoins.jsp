<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String openId = request.getParameter("openId");
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String basePath2 = request.getScheme() + "://"
			+ request.getServerName()+ path + "/";
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
<meta name="mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-title" content="" />
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="stylesheet"
	href="<%=path%>/resource/assets/css/amazeui.min.css" />
<link rel="stylesheet" href="<%=path%>/resource/assets/css/style.css" />
<link rel="stylesheet"
	href="<%=path%>/resource/assets/css/body_style.css" />
<script src="<%=path%>/resource/assets/js/jquery.min.js"></script>
<script src="<%=path%>/resource/assets/js/amazeui.min.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<%-- <script type="text/javascript" src="<%=path%>/resource/js/weixin.js"></script> --%>
<script type="text/javascript">
	$(function() {
		
		var dealFlag =true;	
		
		//手机号改变事件
		var effect_type_text;
		var phone;//手机号
		var flowSize;//流量包大小，单位：兆
		var flowAmount;//花费流量币数量
		var openid='${openid}';
		var quantity;
		var flowId;//流量包产品编号
		var operator;//运营商
		//电话号变更事件
		$('#phone').change(function(){
			$('#doc-confirm-toggle-2').hide();
		});
		//充值按钮点击事件
		
		$('#doc-confirm-toggle-2').click(function(e){
			var oldphone=phone;
			phone = $('#phone').val();
			if(phone!=oldphone){
				$('#doc-confirm-toggle-2').hide();
				alert("请重新选择充值档位,获取新的运营商信息！");
				return;
			}
			re=/^1[3578]\d{9}$/
				if(!re.test(phone)) {
					alert('请输入正确手机号,亲~');
					return;
				}
			$('#confirm').find("span").eq(1).text(phone);
			$('#confirm').find("span").eq(0).text($('#showmes').find('span').eq(1).text());
			$('#confirm').find("span").eq(2).text($('#showmes').find('span').eq(2).text());
			var effect_type = $('input:radio[name=docInlineRadio]:checked').val();
			if(effect_type==null ||effect_type==''){
				alert('亲,请选择生效方式');
				return;
			} 
			if(effect_type==0){
				effect_type_text ="立即生效";
			}else{
				effect_type_text ="次月生效";
			}
			$('#confirm').find("span").eq(3).text(effect_type_text);
			
			quantity=$('#showmes').find('span').eq(2).text();
			//confirm事件实现
			$('#my-confirm').modal({
		        onConfirm: function(options) {
		        	if(dealFlag==false){
		        		$('#doc-confirm-toggle-2').css('display','none');
		        		return false;
		        	}
		        	dealFlag=false;
		        	if(operator=='中国电信'){
		        		operator="ctcc";
		        	}
		        	if(operator=='中国联通'){
		        		operator="cucc";
		        	}
		        	if(operator=='中国移动'){
		        		operator="cmcc";
		        	}
		        	effect_type = $('input:radio[name=docInlineRadio]:checked').val();
		        	$('#doc-confirm-toggle-2').css('disabled','disabled');
		        	$.ajax({
						cache:false,
						type:'post',
				   		dataType: 'json',
				   	 	async: false,
				   		url:"<%=path%>/recharge/rechargeSuccess",
						data : {
							phone : phone,
							effectType : effect_type,
							
							quantity : quantity,
							flowId : flowId,
							operator : operator
						},
						success : function(data) {
							if(data.success=='ture'){
								document.location.href = "<%=path%>/recharge/rechargeSuccessTopage?&effect_type="+data.effect_type+"";
							} else {
								document.location.href = "<%=path%>/recharge/rechargeFailTopage?msg="+data.msg+"";
							}
						}
					});
		        	
		  			
		        },
		        onCancel: function() {
		        	$('.am-dimmer').hide();
		        }
		      });
			
		});
		//选择充值档位按钮的事件
		$('#doc-confirm-toggle-1').click(function(e){
			phone = $('#phone').val();
			 re=/^1[3578]\d{9}$/
				if(!re.test(phone)) {
					alert('请输入正确手机号,亲~');
					return;
				}
			$.ajax({
				cache:false,
				type:'POST',
		   		url:"<%=path%>/recharge/selectRange",
										data : {
											
											phone : phone
										},
										async : false,
										success : function(data) {
											//拿到返回的数据
											json1 = eval("(" + data + ")");
											if(json1.code == "100000"){
												alert("您输入的手机号段不对！");
												return false;
											}
											if (json1.code == "111111") {
												alert("您的流量币不足！");
												return false;
											} else {
												
												$('#select-confirm').children()
														.remove();
												var str;
												if(json1.length==1){
												str = "<table border='0' cellspacing='0' cellpadding='0' width='34%'><tr>";
												}else{
													
												str = "<table border='0' cellspacing='0' cellpadding='0' style='margin:0 auto;' width='100%'><tr>";
												}
												for (var i = 0; i < json1.length; i++) {
													if(json1[i].operator=='移动'){
														var imgstr="yd1";
													}
													if(json1[i].operator=='联通'){
														var imgstr="lt1";
													}
													if(json1[i].operator=='电信'){
														var imgstr="dx1";
													}
													<%-- str = "<button type='button' style='width:49%;float:left;color:#FFF;background-color:#0c79b1;border-radius:20px;' class='am-btn am-btn-default'><input type='hidden' value="+json1[i].operator+"><input type='hidden' value="+json1[i].id+"><span>"
															+"<img src='<%=path%>/resource/assets/images/yys"
															+ imgstr
															+ ".png'>"
															+ json1[i].operator
															+ "<br/>"
															+ json1[i].flowSize
															+ "</span>M<br/>花费<span>"
															+ json1[i].coinAmount
															+ "</span>流量币</button>"; --%>
															
													str = str + "<td style='padding-left:.5em;text-align:center;'>"
																	+"<input type='hidden' value="+json1[i].operator+"><input type='hidden' value="+json1[i].id+">"
																	+"<div  style='background:url(<%=path%>/resource/assets/images/"+imgstr+".png) no-repeat #fc5b30;' class='jl_bgc4_1 bgcolo1'>"
																		+"<a href='#'>"
																		+"<b><span>中国"+json1[i].operator+"</span></b><br/><span>"+json1[i].flowSize+"</span>M<br/>"
																		+"<b style='font-size:13px;'>花费<span>"+json1[i].coinAmount+"</span>个流量币</b>"
																		+"</a>"
																	+"</div>"
																+"</td>";
													if (i % 2 == 1) {
														str = str + "</tr><tr>";
													}
													
												}
												str = str.substring(0, str.length)+"</tr></table>";
												//alert(str);
												$("#select-confirm").append($(str));
												addselectEvent();
												$("#my-popup").modal({
													closeViaDimmer : 0
												}).modal('open');
											}
										}
									});

						});
		//给档位按钮添加事件
		function addselectEvent() {
			$('#select-confirm').find('td').click(
					function() {
						//运营商
						$('#showmes').find('span').eq(0).text(
								$(this).find('span').eq(0).text());
						//流量数
						$('#showmes').find('span').eq(1).text(
								$(this).find('span').eq(1).text());
						//流量币数
						$('#showmes').find('span').eq(2).text(
								$(this).find('span').eq(2).text());
						$('#my-popup').modal();
						operator = $(this).find('input').eq(0).val();
						if(operator=='移动'){
							var imgstr="yd1";
						}
						if(operator=='联通'){
							var imgstr="lt1";
						}
						if(operator=='电信'){
							var imgstr="dx1";
						}
						flowId = $(this).find('input').eq(1).val();
						$('.am-dimmer').hide();
						$('#showmes').find('div').attr("style", "background:url(<%=path%>/resource/assets/images/"+imgstr+".png) no-repeat #fc5b30;");
						$('#showmes').show();
						$('#doc-confirm-toggle-2').show();

					});

		}
	});
</script>
<script>
	var _hmt = _hmt || [];
	(function() {
		var hm = document.createElement("script");
		hm.src = "//hm.baidu.com/hm.js?789d2116cf568ce6c4aab600f7f7b62b";
		var s = document.getElementsByTagName("script")[0];
		s.parentNode.insertBefore(hm, s);
	})();
</script>

</head>
<body>

	<div class="am-g am-top1">
		<h2 align="center">
			您拥有<b class="c2"> ${availablAmount}</b> 个流量币<br /> <span>${msg}</span>
		</h2>

		<div class="am-form">
			<fieldset>
				<div class="am-form-group">
					<label for="doc-ipt-email-1">请输入11位手机号码</label>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="5%"><div class="am-btn am-radius" style="background-color: #3ba4fc;"><font color="#fff">+86</font></div></td>
							<td width="94%"><input type="tel" placeholder="请输入手机号"
								id="phone" class="am-form-field am-btn-sm"></td>
						</tr>
					</table>
				</div>

				<div class="am-form-group">
					<label class="am-radio-inline"> <input type="radio" checked
						value="0" name="docInlineRadio"> 本月生效
					</label> <label class="am-radio-inline"> <input type="radio"
						value="1" name="docInlineRadio"> 下月生效
					</label>

				</div>

				<div class="am-form-group">
					<button class="am-btn am-btn-primary am-radius am-btn-block "
						id="doc-confirm-toggle-1">选择充值档位</button>
				</div>

				<table border="0" cellspacing="0" cellpadding="0" width="100%"  style = "display:none;" id="showmes"> 
					<tr>
						<td>
							<div class="jl_bgc4_1 bgcolo1">
								<a href="#"> <b><span>dianxin</span></b><br /> <span>5</span>M<br /> <b>花费<span>25</span>个流量币</b>
								</a>
							</div>
						</td>

					</tr>

				</table>

				<div class="am-form-group">
					<button id="doc-confirm-toggle-2" style = "display:none;"
						class="am-btn am-btn-warning am-radius am-btn-block ">充&nbsp;&nbsp;值</button>
				</div>

				<p class="am-u-sm-11 ">
					操作说明：<br /> 1.输入手机号码，选择‘本月生效’或‘下月生效’。<br /> 2.点击‘选择充值档位’选择指定充值档。<br />
					3.点击‘充值’并根据提示消息‘确定’完成充值
				</p>
			</fieldset>
		</div>

	</div>
	
	<!--点击充值后弹出框代码-->
	<div class="am-modal am-modal-confirm" tabindex="-1" id="my-confirm">
		<div class="am-modal-dialog">
			<div id="confirm" class="am-modal-bd">
				充值<span></span>M流量到<span></span><br /> 花费流量币<span></span>个<br />
				生效类型：<span></span><br /> 亲~请确认充值信息，是否充值？
			</div>
			<div class="am-modal-footer">
				<span class="am-modal-btn" data-am-modal-cancel>取消</span> <span 
					class="am-modal-btn" data-am-modal-confirm id="deal">确定</span>
			</div>
		</div>
	</div>

	<!--点击选择充值档位弹出框代码-->
	<!-- <div class="am-modal am-modal-alert" tabindex="-1" id="select-confirm"></div> -->
	<!-- <div class="am-modal am-modal-loading am-modal-no-btn" tabindex="-1" id="select-confirm">
		
	  <div class="am-modal-dialog">
	    <div class="am-modal-hd"></div>
	    <div class="am-modal-bd">
	      <span class="am-icon-spinner am-icon-spin"></span>
	    </div>
	  </div>
	</div> -->

	<div class="am-popup" id="my-popup">
		<div class="am-popup-inner">
			<div class="am-popup-hd">
				<h4 class="am-popup-title">请选择充值档位</h4>
				<span data-am-modal-close class="am-close">&times;</span>
			</div>
			<div class="am-popup-bd" id="select-confirm" style="text-align:center;"></div>
		</div>
	</div>

</body>
</html>
