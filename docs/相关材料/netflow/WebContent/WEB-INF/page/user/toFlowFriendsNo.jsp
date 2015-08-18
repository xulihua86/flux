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
  <title>我的流量好友</title>
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
<span>亲，您还没有流量好友哦~~</span>
</div>

</div>
<div class="bg_c1">
<!-- <div class="tit1">赚三网流量</div> -->
<div data-am-widget="list_news" class="am-list-news am-list-news-default">
  <!--列表标题-->
  <div class="am-list-news-bd">
    <ul class="am-list">
      <!--缩略图在标题左边-->
      <li class="am-g am-list-item-desced am-list-item-thumbed am-list-item-thumb-left">
        <div class="am-u-sm-4 am-list-thumb">
          <a href="registration1.html">
            <img src="<%=basePath%>resource/assets/images/p1.jpg" alt="图片名称" class="am-img-thumbnail am-circle" />
          </a>
        </div>
        <div class=" am-u-sm-8 am-list-main">
          <h3 class="am-list-item-hd">
            <a href="<%=basePath%>resource/registration1.html" class="">每日签到送流量</a>
          </h3>
          
        </div>
      </li>
      <li class="am-g am-list-item-desced am-list-item-thumbed am-list-item-thumb-left">
        <div class="am-u-sm-4 am-list-thumb">
          <a href="#">
            <img src="<%=basePath%>resource/assets/images/p2.jpg" alt="图片名称" class="am-img-thumbnail am-circle" />
          </a>
        </div>
        <div class=" am-u-sm-8 am-list-main">
          <h3 class="am-list-item-hd">
            <a href="#" class="">每日10G流量，每晚10点，准时开抢！！</a>
          </h3>
          
        </div>
      </li>
      <li class="am-g am-list-item-desced am-list-item-thumbed am-list-item-thumb-left">
        <div class="am-u-sm-4 am-list-thumb">
          <a href="unbound_phone.html">
            <img src="<%=basePath%>resource/assets/images/p3.jpg" alt="图片名称" class="am-img-thumbnail am-circle" />
          </a>
        </div>
        <div class=" am-u-sm-8 am-list-main">
          <h3 class="am-list-item-hd">
            <a href="unbound_phone.html" class="">再不绑定就完了，现在绑定手机号，立即送25M流量</a>
          </h3>
          
        </div>
      </li>
    </ul>
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
<script src="<%=basePath%>resource/assets/js/jquery.min.js"></script>
<script src="<%=basePath%>resource/assets/js/amazeui.min.js"></script>
<!--<![endif]-->
</body>
</html>
