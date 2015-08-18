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
  <title>兑换流量币</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <meta name="format-detection" content="telephone=no">
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-siteapp" />
  
  
  <link rel="stylesheet" href="<%=basePath%>resource/assets/css/amazeui.min.css"/>
  <link rel="stylesheet" href="<%=basePath%>resource/assets/css/style.css"/>  
  <link rel="stylesheet" href="<%=basePath%>resource/assets/css/body_style.css"/>
  
  <script src="<%=basePath%>resource/assets/js/jquery.min.js"></script>
  <script src="<%=basePath%>resource/assets/js/amazeui.min.js"></script>
</head>
	  
<body>
    
    <div class="am-g am-top1">
            <div class="am-u-sm-centered am-u-center am-bottom1">
                    <article data-am-widget="paragraph" class="am-paragraph" data-am-paragraph="{ tableScrollable: true, pureview: true }">
                      <!-- <p class = "am-text-xl">出错了，<br/>亲稍后再 试一下~</p> -->
                      <p class = "am-text-xl">
                            <c:choose >
                                <c:when test="${data.operateFlag =='1'}">
                                    ${data.operateMessage} <br/>
                                    <a href="<%=basePath%>user/toList?openId=${data.openId}" >去流量账户查看</a>  
                                </c:when>
                                <c:otherwise>
                                    ${data.operateMessage}
                                </c:otherwise>
                            </c:choose>
                      </p>
                      <p class = "am-text-default"><a class= "am-text-danger" href = "<%=path%>/user/toQuestion">常见问题</a></p>
                    </article>
                    <br>
                    <br>
                    <br>
                    <br>
                    <div class="bg_c1">
                            <!-- <div class="tit1">赚三网流量</div> -->
                            <iframe src="<%=path%>/link" scrolling="no" width="100%" onload="this.height=this.contentWindow.document.documentElement.scrollHeight"></iframe>
                    </div>
            </div>
    </div>
    
</body>	
</html>