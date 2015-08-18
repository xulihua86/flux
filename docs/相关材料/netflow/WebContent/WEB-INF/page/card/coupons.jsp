<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html>
<head lang="en">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-title" content="" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>微信卡券</title>
<meta name="renderer" content="webkit">
<link rel="stylesheet"
	href="<%=path%>/resource/assets/css/amazeui.min.css" />
<link rel="stylesheet" href="<%=path%>/resource/assets/css/style.css" />
<link rel="stylesheet"
	href="<%=path%>/resource/assets/css/body_style.css" />
<script src="<%=path%>/resource/assets/js/jquery.min.js"></script>
<script src="<%=path%>/resource/assets/js/amazeui.js"></script>
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
<script type="text/javascript">
$(function(){
	var cardtype;//卡券类型
	var flowtype;//流量类型
	var coins;//花费流量币
	var cardId;//微信卡券id
	var wxProductId;//产品Id
	//为所有的按钮添加点击事件
	if(window.name=="over"){
		$('#subbtn').attr('disabled','disabled');
	}
	$('td').click(function(){
		//流量卡类型
		cardtype=$(this).find('span').eq(-2).text();
		//流量类型
		flowtype=$(this).find('span[name=flowtype]').text();
		//流量币个数
		
		coins=$(this).find('span:last').text();
		cardId=$(this).find('span').eq(-3).text();
		wxProductId=$(this).find('span').eq(-4).text();
		$('#my-confirm').find('span').eq(0).text(cardtype);	
		$('#my-confirm').find('span').eq(1).text(coins);
	});
	//提交按钮事件
	$('#subbtn').click(function(){
		$('radio[checked=checked]').parent().parent('button').click();
		var code='${weixincode}';//微信卡券code
		var openId='${openId}';
		if(window.name=="over"){
			$('#subbtn').attr('disabled','disabled');
			return false;
		}
		
		if($('#my-confirm').find('span').eq(0).text()==null ||$('#my-confirm').find('span').eq(0).text()==''){
			alert('亲,请选择兑换的卡券类型');
			return;
		} 
		
		 $('#my-confirm').modal({
			onConfirm: function(options) { 
				$('.am-dimmer').hide();
				/* var oauth2url = '${oauth2url}';//获取urlconfig.properties中通用的授权写法
				var getcardUrl = '${wechatPlat}'+'${getcardUrl}';
				getcardUrl = getcardUrl+"?cardId="+cardId+"&code="+code+"&wxProductId="+wxProductId+"&coins="+coins+"";
				getcardUrl = encodeURI(getcardUrl);
				oauth2url = oauth2url.replace("REDIRECTURL", getcardUrl);
				document.location.href = oauth2url; */
				location.href="<%=path%>/cards/getcard?cardId="
															+ cardId
															+ "&code="
															+ code
															+ "&wxProductId="
															+ wxProductId
															+ "&coins="
															+ coins
															+ "";
													return false;
												},
												onCancel : function() {
													$('.am-dimmer').hide();
												}

											});
							return false;
						});
		$('.am-dimmer').click(function() {
			$(this).hide();
		});
	});
</script>
<body>

	<div class="am-g">
		<h2 class="am-u-center">
			您拥有<b class="c2">${availableAmount}</b>流量币
		</h2>
		<br /> <br />

		<div class="am-g am-u-sm-12 am-u-sm-centered am-u-center">
			<!--判断流量币个数 -->
			<!-- 流量币不足 -->
			<c:if test="${empty resultList}">
				<article data-am-widget="paragraph" class="am-paragraph"
					data-am-paragraph="{ tableScrollable: true, pureview: true }">
					<p class="am-text-xl">您的流量币不足，请及时充值！</p>
				</article>
				<br>
				<br>
				<br>
				<br>
				<div class="bg_c1">
					<iframe src="<%=path%>/link" scrolling="no" width="100%"
						onload="this.height=this.contentWindow.document.documentElement.scrollHeight"></iframe>
				</div>
			</c:if>
			<!-- 流量币够 -->
			<c:if test="${resultList.size()==1}">
				<table border="0" cellspacing="0" cellpadding="0" width="49%">
			</c:if>
			<c:if test="${resultList.size()!=1}">
				<table border="0" cellspacing="0" cellpadding="0" style='margin:0 auto;' width='100%'>
			</c:if>
				<tr>
					<c:forEach items="${resultList}" var="weixin" varStatus="obj">
						<c:choose>
							<c:when test="${fn:contains(weixin.name, '三网流量包')}">
								<td style="padding-left: .5em;" width='49%'>
									<div style="background:url(<%=path%>/resource/assets/images/qwt1.png) no-repeat #fc5b30;"
										class="jl_bgc4 bgcolo" style="vertical-align: middle;">
											<br/>
											<a href="javascript:void(0)"> 
												<b>
													<label class="am-radio-inline"> 
														<input type="radio" value="0" name="docInlineRadio"/>
															${fn:substringAfter(weixin.name, '包')}
													</label>
												</b>
											<br/>
											<span style="font-size: 10px;">${weixin.description}</span><br />
											<b>${weixin.sellPrice}个流量币</b> <span style="display: none;">${weixin.wxProductId}</span>
											<span style="display: none;">${weixin.wxCardId}</span> <span
											style="display: none;">${weixin.name}</span> <span
											style="display: none;">${weixin.sellPrice}</span>
										</a>
									</div>
								</td>
								<c:if test="${obj.count%2 == '0'}">
										</tr><tr>
								</c:if>
						</c:when>
						<c:otherwise>
							<td style="padding-left: .5em;" width='49%'>
							<c:if test="${weixin.operatorTypes=='cmcc'}">
								<div style="background:url(<%=path%>/resource/assets/images/yd1.png) no-repeat #fc5b30;" class="jl_bgc4 bgcolo" style="vertical-align: middle;">
									<br/>
									<a href="#"> 
									<b>
									<label class="am-radio-inline">
									 <input type="radio" value="0" name="docInlineRadio"/>
										 中国移动
									 </label>
							</c:if> 
							<c:if test="${weixin.operatorTypes=='cucc'}">
								<div style="background:url(<%=path%>/resource/assets/images/lt1.png) no-repeat #fc5b30;" class="jl_bgc4 bgcolo">
									<a href="#"> 
									<b>
									<label class="am-radio-inline"> 
									<input type="radio" value="0" name="docInlineRadio"/>
									中国联通
									</label>
							</c:if> 
							<c:if test="${weixin.operatorTypes=='ctcc'}">
								<div style="background:url(<%=path%>/resource/assets/images/dx1.png) no-repeat #fc5b30;" class="jl_bgc4 bgcolo">
									<a href="#">
									<b>
									<label class="am-radio-inline">
									<input type="radio" value="0" name="docInlineRadio">
									中国电信
									</label>
							</c:if> 
							</b>
							<br/>
						<span style="font-size: 12px;"> ${weixin.description}</span><br />
							<b>${weixin.sellPrice}个流量币</b> <span style="display: none;">${weixin.wxProductId}</span>
							<span style="display: none;">${weixin.wxCardId}</span> <span
							style="display: none;">${weixin.name}</span> <span
							style="display: none;">${weixin.sellPrice}</span> </a>
							</div></td>
						<c:if test="${obj.count%2 == '0'}">
				</tr>
				<tr>
					</c:if>
					</c:otherwise>
					</c:choose>
					</c:forEach>
				</tr>
			</table>

			<c:if test="${!empty resultList}">
				<div class="am-form-group">
					<button id="subbtn" type="button"
						class="am-btn am-btn-primary am-radius am-btn-block ">兑&nbsp;&nbsp;换</button>
				</div>
			</c:if>

		</div>
	</div>

	<!--点击充值后弹出框代码-->
	<div class="am-modal am-modal-confirm" tabindex="-1" id="my-confirm">
		<div class="am-modal-dialog">
			<div class="am-modal-bd">
				您将兑换<span></span>一张<br /> 花费<span></span>个流量币
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
<![endif]-->
</body>
</html>
