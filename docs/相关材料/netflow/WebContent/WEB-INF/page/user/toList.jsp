<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head lang="en">
  <meta charset="UTF-8">
  <title>我的流量账户</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <meta name="format-detection" content="telephone=no">
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-siteapp" />
  
  
  <link rel="stylesheet" href="<%=basePath%>resource/assets/css/amazeui.min.css"/>
  <link rel="stylesheet" href="<%=basePath%>resource/assets/css/style.css"/>  
  <link rel="stylesheet" href="<%=basePath%>resource/assets/css/body_style.css"/>
  
  <!-- 百度统计浏览次数 -->
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




<body style="width:98%;">

<input type="hidden" id="code" value="${data.code}" /> <!-- 300001 (参数错误), 000000 (成功) -->
<input type="hidden" id="awardValue" value="${data.awardValue}" />  <!-- 奖励流量币数 -->
	
<div class="am-g">
<div class="am-u-sm-centered am-u-center bgcolo2">
<div class="jl_bgc4_4">
<span class="fon3">${data.availableAmount}</span><br /><span class="fon1">流量币</span><br /><br />
<div class="am-g">
  	<div class="am-u-sm-6"><a href="<%=basePath%>/user/toTrafficBill"><div style="padding-top:1.3em;"><input type="submit" name="" value="流量账单" class="am-btn am-radius am-btn-default1 am-btn-block am-btn-xs"></div></a></div>
	<div class="am-u-sm-6"><a href="<%=basePath%>/recharge/toexchange"><div style="padding-top:1.3em;"><input type="submit" name="" value="充值到手机" class="am-btn am-radius am-btn-default1 am-btn-block am-btn-xs"></div></a></div>
</div>
</div>
</div>

<div class=" bgcolo3">
<ul class="am-list m-widget-list" style="-webkit-transition: 0ms cubic-bezier(0.1, 0.57, 0.1, 1); transition: 0ms cubic-bezier(0.1, 0.57, 0.1, 1); -webkit-transform: translate(0px, 0px) translateZ(0px);">
<li><a href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx137810a045c9a71e&redirect_uri=http%3A%2F%2Ffreeliu.orientalwisdom.com%2Fnetflow%2Fbinding%2FinitBinding&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect" data-rel="accordion">
<div class="am-g">
<div class="am-u-sm-6"><img class="widget-icon" src="<%=basePath%>resource/assets/images/bt1.png" > <span class="widget-name">绑定手机号</span></div>
<div class="am-u-sm-4 c3 fon2">
	<c:choose >
        <c:when test="${data.isBindingMobile =='Y'}">
            <span class="am-list-date widget-name">${data.mobileNo}</span>
        </c:when>
        <c:otherwise>
            <span class="am-list-date widget-name">首次绑定送25个流量币</span>
        </c:otherwise>
    </c:choose>
</div>
<div class="am-u-sm-2 fon2"><img class="widget-icon" src="<%=basePath%>resource/assets/images/btn1.png" ></div>
</div>
</a>
</li>
<li><a href="<%=basePath%>/user/toFlowFriends?start=0" data-rel="accordion">
<div class="am-g">
<div class="am-u-sm-7"><img class="widget-icon" src="<%=basePath%>resource/assets/images/bt2.png" > <span class="widget-name">我的流量好友</span></div>
<div class="am-u-sm-3 c3 fon2"> <%--<span class="am-list-date widget-name"> ${data.friendNum} </span>--%> </div>
<div class="am-u-sm-2 fon2"><img class="widget-icon" src="<%=basePath%>resource/assets/images/btn1.png" ></div>
</div>
</a>
</li>
<li><a href="<%=basePath%>/user/toWechatTicket" data-rel="accordion">
<div class="am-g">
<div class="am-u-sm-7"><img class="widget-icon" src="<%=basePath%>resource/assets/images/bt3.png" > <span class="widget-name">微信卡券</span></div>
<div class="am-u-sm-3 c3 fon2"></div>
<div class="am-u-sm-2 fon2"><img class="widget-icon" src="<%=basePath%>resource/assets/images/btn1.png" ></div>
</div>
</a>
</li>
<li><a href="<%=basePath%>/user/toMessage" data-rel="accordion">
<div class="am-g">
<div class="am-u-sm-7"><img class="widget-icon" src="<%=basePath%>resource/assets/images/bt4.png" > <span class="widget-name">消息</span></div>
<div class="am-u-sm-3 c3 fon2"><c:if test="${data.messageNum > 0}"> ${data.messageNum}</c:if></div>
<div class="am-u-sm-2 fon2"><img class="widget-icon" src="<%=basePath%>resource/assets/images/btn1.png" ></div>
</div>
</a>
</li>
<li><a href="<%=basePath%>/user/toQuestion" data-rel="accordion">
<div class="am-g">
<div class="am-u-sm-10"><img class="widget-icon" src="<%=basePath%>resource/assets/images/bt5.png" > <span class="widget-name">常见问题</span></div>
<div class="am-u-sm-2 fon2"><img class="widget-icon" src="<%=basePath%>resource/assets/images/btn1.png" ></div>
</div>
</a>
</li>
</ul>  
</div>

</div>
    <div class="bg_c1">
        <!-- <div class="tit1">赚三网流量</div> -->
        <iframe src="<%=basePath%>/link" scrolling="no" width="100%" onload="this.height=this.contentWindow.document.documentElement.scrollHeight"></iframe>
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
<script src="<%=basePath%>assets/js/jquery.min.js"></script>
<script src="<%=basePath%>assets/js/amazeui.min.js"></script>
<!--<![endif]-->

</body>
</html>
