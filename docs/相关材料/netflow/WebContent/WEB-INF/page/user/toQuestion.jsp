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
  <title>常见问题</title>
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
<div class="am-g am-top2">
<div class="am-u-sm-centered">

    <div class="jl_bgc4_3">
        <span class="c2">Q：将流量币存到流量账户时，为什么提示出错？</span><br />
        <span class="c1">A：系统繁忙是导致提示出错的最主要原因，请亲稍后再试一下。</span>
    </div>
    
    <div class="jl_bgc4_3">
        <span class="c2">Q：绑定手机号时，为什么提示出错？</span><br />
        <span class="c1">A：系统繁忙是导致提示出错的最主要原因，请亲稍后再试一下。</span>
    </div>
    
    <div class="jl_bgc4_3">
        <span class="c2">Q：充值时，为什么提示出错？</span><br />
        <span class="c1">A：充值系统繁忙是导致提示出错的最主要原因，请亲稍后再试一下。</span>
    </div>
    
    <div class="jl_bgc4_3">
        <span class="c2">Q：充值提交后，流量包多长时间能到账？</span><br />
        <span class="c1">A：【本月生效】充值过程约需要24小时，充值成功后可收到运营商提示短信；<br/>
        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;【下月生效】流量包将于下个月1日充值到您的手机号，充值成功后可收到运营商提示短信。
        </span>
    </div>
    
    <div class="jl_bgc4_3">
        <span class="c2">Q：为什么会充值失败？</span><br />
        <span class="c1">A：您的号码如果存在如下问题，会出现充值失败的情况：<br/>
        	&nbsp;&nbsp;1.用户号码状态异常（如号码欠费、业务互斥等）<br/>         
            &nbsp;&nbsp;2.用户资料不全<br/>          
            &nbsp;&nbsp;3.被运营商设置进入了黑名单 <br/>         
            &nbsp;&nbsp;4.用户号码没有通过实名制认证 <br/>         
            &nbsp;&nbsp;5.一个月内多次充值流量加油包 <br/>
        </span>
    </div>
    
    <div class="jl_bgc4_3">
        <span class="c2">如果您存在以上问题，请及时与您的当地运营商取得联系。如果不是上述情况，请与我们的客服联系：4001076866。</span><br />
        <span class="c1"></span>
    </div>


</div>
</div>

<!--[if (gte IE 9)|!(IE)]><!-->
<script src="<%=basePath%>resource/assets/js/jquery.min.js"></script>
<script src="<%=basePath%>resource/assets/js/amazeui.min.js"></script>
<!--<![endif]-->
</body>
</html>
