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
  <title>流量账单</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <meta name="format-detection" content="telephone=no">
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-siteapp" />
  
  <link rel="stylesheet" href="<%=basePath%>resource/assets/css/amazeui.min.css"/>
  <link rel="stylesheet" href="<%=basePath%>resource/assets/css/style.css"/>  
  <link rel="stylesheet" href="<%=basePath%>resource/assets/css/body_style.css"/>
</head>
<body>

<div class="am-g am-top3" style="background-color:#FFF;">
<div class="am-u-sm-centered am-u-center am-bottom1">
<div class="bg_c3">
<span><b class="c2 fon3">${data.availablAmount}</b>流量币</span>
<div class="am-g">
  	<div class="am-u-sm-6">共计收入${data.inAmount}个</div>
	<div class="am-u-sm-6">支出 ${data.outAmount}个</div>
</div>
</div>
<br /><br /><br />
<span>亲，您还没有账单记录~</span>
</div>

</div>
<div class="bg_c1">
<!-- <div class="tit1">赚三网流量</div> -->
<iframe src="<%=basePath%>/link" scrolling="no" width="100%" onload="this.height=this.contentWindow.document.documentElement.scrollHeight"></iframe>



</div>

<!--[if lt IE 9]>
<script src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="assets/js/polyfill/rem.min.js"></script>
<script src="assets/js/polyfill/respond.min.js"></script>
<script src="assets/js/amazeui.legacy.js"></script>
<![endif]-->

<!--[if (gte IE 9)|!(IE)]><!-->
<script src="<%=basePath%>resource/assets/js/jquery.min.js"></script>
<script src="<%=basePath%>resource/assets/js/amazeui.min.js"></script>
<!--<![endif]-->
</body>
</html>
