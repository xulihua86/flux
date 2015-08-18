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
<head lang="en">
  <meta charset="UTF-8">
  <title>转赠流量</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <meta name="format-detection" content="telephone=no">
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-siteapp" />
  <link rel="stylesheet" href="<%=path%>/resource/assets/css/amazeui.min.css"/>
  <link rel="stylesheet" href="<%=path%>/resource/assets/css/style.css"/>  
  <link rel="stylesheet" href="<%=path%>/resource/assets/css/body_style.css"/>
  <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
  <script src="<%=path%>/resource/assets/js/jquery.min.js"></script>
  <script src="<%=path%>/resource/assets/js/amazeui.js"></script>
  <script src="<%=path%>/resource/assets/js/handlebars.min.js"></script>
  <script src="<%=path%>/resource/assets/js/amazeui.widgets.helper.js"></script>
  <script type="text/javascript">
  	function gotogive(){
  		var oauth2url = '${oauth2url}';
  		//流量账单
		var mycount_toTrafficBill = '${wechatPlat}'+'${mycount_toTrafficBill}';
  		mycount_toTrafficBill = encodeURI(mycount_toTrafficBill);
  		var fullUrl = oauth2url.replace("REDIRECTURL",mycount_toTrafficBill);
		window.location.href=fullUrl;
  	}
  </script>
</head>
<body>
<%-- <div class="am-g am-top1">
	<div class="am-u-sm-11 am-u-sm-centered am-u-center">
	<span class="c1">${coin}个</span>  流量币已送出<br /><br />
	<button id="btngotogive" type="button" onclick = "gotogive()" class="am-btn am-btn-primary am-round am-btn-block ">继续转赠</button><br /><br />
	</div>
	<div class="bg_c1" style="padding-bottom:300px;">
		<iframe src="<%=path%>/link" scrolling="no" width="100%" onload="this.height=this.contentWindow.document.documentElement.scrollHeight"></iframe>
	</div>
</div> --%>
<div class="am-g am-top1">
	<div class="am-u-sm-centered am-u-center">
		<div align="center"><img src="<%=path%>/resource/assets/images/p14.jpg" alt="" class="am-img-responsive"><br /></div>
		<h3 class="am-article-title" style="font-size: 18px;"><b class="c2"> ${coin}</b> 个流量币已送出</h3>
		<div class="am-form-group">
		      <button type="button" class="am-btn am-btn-primary am-radius am-btn-block " onclick = "gotogive()">去查看</button>
		</div>
		
		<div class="bg_c1">
			<iframe src="<%=path%>/link" scrolling="no" width="100%" onload="this.height=this.contentWindow.document.documentElement.scrollHeight"></iframe>
		</div>
	</div>
</div>
</body>
</html>