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
  <title>消息</title>
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


<div class="am-g am-top3">
    <div class="am-u-center am-bottom1" id="bg_c3">
            <c:forEach items="${data.result}" var="row" >
            	<div class="jl_bgc4_3">
                	${row.content}<br /><span class="c3">${row.createTime}</span>
                </div>
            </c:forEach>
    </div>
</div>

<input type="hidden" name="openId" id="openId" value="${openId}" />
<input type="hidden" name="start" id="start" value="${start}" />

<div id="window_mask" style=" position:absolute;top:0px;left:0px; filter:alpha(opacity=70); opacity:0.7; width: 100%; height: 100%; display: none; z-index: 100; background-color:#FFF"></div>

<div class="am-list-news-ft">
	<a class="am-list-news-more am-btn am-btn-default " href="javascript:void(0);" onClick="loadMessageMore()">查看更多 &raquo;</a>
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

<script>


	//加载更多消息
	function loadMessageMore(){
		var url = '<%=basePath%>'+"/user/loadMessageMore";
		var start = parseInt($("#start").val())+1;
		var params = {   
				openId:$("#openId").val(),
				start:start
		};
		
		$("#window_mask").css("display","block");
		jQuery.post(url, params, function(data){
			
			//var jsonData = eval("("+data+")");
			
			var list = data.result;
			$("#window_mask").css("display","none");
			if(list == null || list.length == 0){
				alert("没有更多数据!");
				return;
			}
			
			for(var i=0;i<list.length;i++){
				var createTime = list[i].createTime;
				var content = list[i].content;
				
				$("#bg_c3").append("<div class=\"jl_bgc4_3\">"+content+"<br /><span class=\"c3\">"+createTime+"</span></div>");

			}//end for
			
			$("#start").val(start); //页码加1
			
		}, 'json');
		
	}//end getMessage
	
	

</script>

</body>
</html>
