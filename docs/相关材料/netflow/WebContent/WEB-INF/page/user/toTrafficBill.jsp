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

<input type="hidden" name="openId" id="openId" value="${openId}" />
<input type="hidden" name="start" id="start" value="${start}" />


<div id="window_mask" style=" position:absolute;top:0px;left:0px; filter:alpha(opacity=70); opacity:0.7; width: 100%; height: 100%; display: none; z-index: 100; background-color:#FFF"></div>

<div class="am-u-sm-centered am-u-center bgcolo4">
	<span class="fon3 c1">${data.availableAmount}</span><span class="fon1 c1">流量币</span><br /><br />
    <div class="am-g">
        <div class="am-u-sm-6 c3">共计收入<b class="c1">${data.inAmount}</b>个</div>
        <div class="am-u-sm-6 c3">支出<b class="c1"> ${data.outAmount}</b>个</div>
    </div>
</div>

<div class="am-u-sm-centered" id="divRecords">

	<c:if test="${empty data.resultList}">
        <button class="am-btn am-btn-danger am-round am-btn-block am-btn-xs" onclick="toPlayPage()">快来领取免费流量币</button>
    </c:if>
    <c:forEach items="${data.resultList}" var="row" >
        
        <c:if test='${row.inOutFlag == "0"}'>
            <div class="am-g jl_bgc4_5">
                <div class="am-u-sm-9">${row.description}</div>
                <div class="am-u-sm-3 c1">+${row.amount}</div>
                <div class="am-u-sm-12 c3">${row.createTime}</div>
            </div>
        </c:if>
        <c:if test='${row.inOutFlag == "1"}'>
            <div class="am-g jl_bgc4_5">
                <div class="am-u-sm-9">${row.description}</div>
                <div class="am-u-sm-3 c2">-${row.amount}</div>
                <div class="am-u-sm-12 c3">${row.createTime}</div>
            </div>
        </c:if>

        
    </c:forEach>

    
    
    
    
   <%--<div class="am-g jl_bgc4_5">
        <div class="am-u-sm-9">签到送流量币</div>
        <div class="am-u-sm-3 c1">＋5</div>
        <div class="am-u-sm-12 c3">2015-09-26  11:25:59</div>
    </div>
    
    <div class="am-g jl_bgc4_5">
        <div class="am-u-sm-9">签到送流量币</div>
        <div class="am-u-sm-3 c2">＋5</div>
        <div class="am-u-sm-12 c3">2015-09-26  11:25:59</div>
    </div>--%>

</div>
    
    <!--
    <c:if test="${not empty data.result}">
    	<div class="am-list-news-ft">
            <a class="am-list-news-more am-btn am-btn-default " href="javascript:void(0);" onClick="loadTrafficBillMore()">查看更多 &raquo;</a>
        </div>
    </c:if>
    -->
    
    <div class="am-list-news-ft">
        <a class="am-list-news-more am-btn am-btn-default " href="javascript:void(0);" onClick="loadTrafficBillMore()">查看更多 &raquo;</a>
    </div>
    
    
</div>

<!--[if (gte IE 9)|!(IE)]><!-->
<script src="<%=basePath%>resource/assets/js/jquery.min.js"></script>
<script src="<%=basePath%>resource/assets/js/amazeui.min.js"></script>
<!--<![endif]-->

<script>

	function toPlayPage(){
		window.location.href="<%=basePath%>resource/nullpage.html";
	}

	//加载更多
	function loadTrafficBillMore(){
		
		$("#window_mask").css("display","block");
		
		//页码加1
		var start = parseInt($("#start").val())+1;
		
		var url = '<%=basePath%>'+"/user/loadTrafficBillMore";
		var params = {   
				openId:$('#openId').val(),
				start:start
		};

		$.post(url, params, function(data){
			
			$("#window_mask").css("display","none");
			
			var jsonData = eval("("+data+")");
			
			var list = jsonData.resultList;
			
			
			if(list == null || list.length == 0){
				alert("没有更多数据!");return;
			}
			
			$("#start").val(start);
			
			for(var i=0;i<list.length;i++){
				
				var createTime = list[i].createTime;
                var typeId = list[i].inOutFlag;
                var amount = list[i].amount;
                var description = list[i].description;
                
                var htmlArray = new Array();
                
				htmlArray.push("<div class=\"am-g jl_bgc4_5\">");
				htmlArray.push("	<div class=\"am-u-sm-9\">"+description+"</div>");
				
				if(typeId == "0"){
					htmlArray.push("	<div class=\"am-u-sm-3 c1\">+"+amount+"</div>");
				}else{
					htmlArray.push("	<div class=\"am-u-sm-3 c2\">-"+amount+"</div>");
				}
				htmlArray.push("	<div class=\"am-u-sm-12 c3\">"+createTime+"</div>");
				htmlArray.push("</div>");

				$("#divRecords").append(htmlArray.join(""));
			}
			
			
		});//end post
	}//end loadTrafficBillMore
	
</script>

</body>
</html>
