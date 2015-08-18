<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="">
  <meta name="keywords" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <!-- Set render engine for 360 browser -->
  <meta name="renderer" content="webkit">
  <!-- No Baidu Siteapp-->
  <meta http-equiv="Cache-Control" content="no-siteapp"/>  

  <!-- Add to homescreen for Chrome on Android -->
  <meta name="mobile-web-app-capable" content="yes">  

  <title>未领取卡券列表</title>

  <!-- Add to homescreen for Safari on iOS -->
  <meta name="apple-mobile-web-app-capable" content="yes">
  <meta name="apple-mobile-web-app-status-bar-style" content="black">
  <meta name="apple-mobile-web-app-title" content="Amaze UI"/>
  
  <link rel="apple-touch-icon-precomposed" href="<%=basePath%>resource/assets/i/app-icon72x72@2x.png">
  <!-- Tile icon for Win8 (144x144 + tile color) -->
  <meta name="msapplication-TileImage" content="<%=basePath%>resource/assets/i/app-icon72x72@2x.png">
  <meta name="msapplication-TileColor" content="#0e90d2">
  
  <link rel="icon" sizes="192x192" href="<%=basePath%>resource/assets/i/app-icon72x72@2x.png">
  <link rel="icon" type="image/png" href="<%=basePath%>resource/assets/i/favicon.png">  

  <link rel="stylesheet" href="<%=basePath%>resource/assets/css/amazeui.min.css">
  <link rel="stylesheet" href="<%=basePath%>resource/assets/css/app.css">
  
  <script src="<%=basePath%>resource/assets/js/jquery.min.js"></script>
  <script src="<%=basePath%>resource/assets/js/amazeui.js"></script>
  
  <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
  
	<style>
		td{border:none;}
	</style>
  
</head>
<body>

	<c:if test="${empty data.cardDetailList}">
        <center>亲，您还没有未领取的卡券</center>
    </c:if>
    
    <!--
    <c:if test="${not empty data.operateMessage}">
        <br/>${data.operateMessage}
    </c:if>
    -->
    
    <c:forEach items="${data.cardDetailList}" var="row" >
        <div class="am-container" style="margin-top:10px;width:100%;">
         	 <a href="<%=basePath%>user/toQRCard?card_id=${row.cardId}&card_code=${row.cardCode}"> 
       	 	 	 <table border="1 solid #D0D0BF;" style="width:100%;">
       	 	 	 	<tr>
       	 	 	 		<td style="width:25%"> <img src="<%=basePath%>resource/images/flllczk20150630.jpg" width="80" height="80" style="margin-top:2px;margin-bottom:2px;margin-left:2px;"/> </td>
       	 	 	 		<td> &nbsp;${row.title} <br/> &nbsp;${row.brand_name} </td>
       	 	 	 	</tr>
       	 	 	 </table>
         	 </a>
        </div>
    </c:forEach>
    
</body>
</html>