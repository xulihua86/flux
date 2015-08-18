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
  <title>充值失败</title>
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
</head>
<body>

<div class="am-g am-top1">
<div class="am-u-sm-centered am-u-center am-bottom1">
<!-- <span>已提交，运营商GG正在玩命处理中</span><br /><br />
<div class="bg_c2">亲选择的是本月生效，请亲耐心等待一下。若长时间未收到提示短信，可到个人中心—<a href="prepaid_list.html">充值记录</a>查看。</div>
</div> -->
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
</div>

<!--[if lt IE 9]>
<script src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="assets/js/polyfill/rem.min.js"></script>
<script src="assets/js/polyfill/respond.min.js"></script>
<script src="assets/js/amazeui.legacy.js"></script>
<![endif]-->

<!--[if (gte IE 9)|!(IE)]><!-->
<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/amazeui.min.js"></script>
<!--<![endif]-->
</body>
</html>