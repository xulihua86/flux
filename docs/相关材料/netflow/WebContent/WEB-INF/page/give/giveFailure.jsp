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
  		//转赠
		var giveGiveIndexUrl = '${wechatPlat}'+'${giveGiveIndexUrl}';
		giveGiveIndexUrl = encodeURI(giveGiveIndexUrl);
  		var fullUrl = oauth2url.replace("REDIRECTURL",giveGiveIndexUrl);
		window.location.href=fullUrl;
  		
  	}
  </script>
</head>
<body>

<%-- <div class="am-g am-top1">
<div class="am-u-sm-centered am-u-center am-bottom1">
<article data-am-widget="paragraph" class="am-paragraph"
data-am-paragraph="{ tableScrollable: true, pureview: true }">
  <p class = "am-text-xl">出错了，<br/>请稍后再试</p>
  <p class = "am-text-default"><a class= "am-text-danger" href = "<%=basePath%>user/toQuestion">常见问题</a></p>
  
</article>
<br>
<br>
<br>
<br>
<div class="bg_c1">
<iframe src="<%=path%>/link" scrolling="no" width="100%" onload="this.height=this.contentWindow.document.documentElement.scrollHeight"></iframe>

</div>
</div> --%>

<div class="am-g am-top1">
<div class="am-u-sm-centered am-u-center">
<div align="center"><img src="<%=path%>/resource/assets/images/p01.jpg" alt="" class="am-img-responsive"><br /></div>
<h3 class="c2">  失败了</h3>
<div class="am-form-group">
      <button type="button" class="am-btn am-btn-primary am-radius am-btn-block " onclick = "gotogive()">再试一次</button>
</div>

<div class="bg_c1">
<iframe src="<%=path%>/link" scrolling="no" width="100%" onload="this.height=this.contentWindow.document.documentElement.scrollHeight"></iframe>
</div>
</div>

</div>

<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/amazeui.min.js"></script>
</body>
</html>