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


<input type="hidden" name="openId" id="openId" value="${openId}" />
<input type="hidden" name="start" id="start" value="${start}" />

<!--
<div id="window_mask" style=" position:absolute;top:0px;left:0px; filter:alpha(opacity=70); opacity:0.7; width: 100%; height: 100%; display: none; z-index: 100; background-color:#FFF"></div>
-->

<div class="am-g am-top3" style="background-color:#FFF; padding-bottom:500px;">

<section data-am-widget="accordion" class="am-accordion am-accordion-gapped" data-am-accordion='{  }' id="sectionEle">

	<c:if test="${empty data.resultList}">
        亲，您还没有流量好友
    </c:if>

	<c:forEach items="${data.resultList}" var="row" >
        <dl class="am-accordion-item" >
                <dt class="am-accordion-title" onClick="getFriendDetail('${row.friendOpenId}',this)">
                    <div class="am-g">
                        <div class="am-u-sm-8" >
                                <c:choose >
                                    <c:when test="${not empty row.headimgUrl}">
                                        <img src='${row.headimgUrl}' width="45" height="45" class="am-img-thumbnail am-radius" >
                                    </c:when>
                                    <c:otherwise>
                                        <img src="<%=basePath%>resource/assets/images/p0.jpg" width="45" height="45" class="am-img-thumbnail am-radius" >
                                    </c:otherwise>
                                </c:choose>
                                
                                <span class="widget-name">${row.nickName}</span>
                        </div>
                        <div class="am-u-sm-4 am-gallery-desc lin1 fon1">${row.lastContact}</div>
                    </div>
                </dt>
                <dd class="am-accordion-bd am-collapse ">
                      <!-- 规避 Collapase 处理有 padding 的折叠内容计算计算有误问题， 加一个容器 -->
                      <div class="am-accordion-content">
                            <!--
                            	<span class="c1">2015-02-24  9:00                   收到100个</span>
                        		<hr data-am-widget="divider" class="am-divider am-divider-default"/>
                             -->
                       </div>
                </dd>
          </dl>
    </c:forEach>
    
    <!-- 测试 -->
   <%-- <dl class="am-accordion-item">
            <dt class="am-accordion-title" onClick="getFriendDetail('123',this)">
                <div class="am-g">
                    <div class="am-u-sm-8" >
                            <img src="${basePath}resource/assets/images/p0.jpg" width="45" height="45" class="am-img-thumbnail am-radius" > 
                            <span class="widget-name">非你不可</span>
                    </div>
                    <div class="am-u-sm-4 am-gallery-desc lin1 fon1">赠送100个</div>
                </div>
            </dt>
            <dd class="am-accordion-bd am-collapse ">
                  <!-- 规避 Collapase 处理有 padding 的折叠内容计算计算有误问题， 加一个容器 -->
                  <div class="am-accordion-content">
                        <span class="c1">2015-02-24  9:00                   收到100个</span>
                        <hr data-am-widget="divider" class="am-divider am-divider-default"/>
                        <span class="c2">2015-02-24  9:00                   赠送50个</span>
                        <hr data-am-widget="divider" class="am-divider am-divider-default"/>
                        <span class="c3">2015-02-24  9:00                   流量卡一</span>
                        <hr data-am-widget="divider" class="am-divider am-divider-default"/>
                        <span class="c4">2015-5-20  15:36                   签到</span>
                   </div>
            </dd>
      </dl>--%>


	<c:if test="${not empty data.result}">
          <div class="am-list-news-ft">
            <a class="am-list-news-more am-btn am-btn-default " href="javascript:void(0)" onClick="loadFriendsMore()">查看更多 &raquo;</a>
          </div>
    </c:if>

  
  
   
</section>

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

	$(document).ready(function(e) {
        String.prototype.replaceAll = function (s1, s2) { 
			return this.replace(new RegExp(s1,"gm"),s2);
		}
    });

	function getFriendDetail(friendOpenId,dtEle){
		
		if($(dtEle).attr("loadFlag") == "1"){
			return;
		}
		
		var url = '<%=basePath%>'+"/user/getFriendDetail";
		var params = {   
				openId:$('#openId').val(),
				friendOpenId:friendOpenId
		};
		jQuery.post(url, params, function(data){
			//var jsonData = eval("("+data+")");
			
			var list = data.resultList;
			
			if(list == null || list.length == 0){
				alert("没有更多数据!");return;
			}
			
			$("#start").val(start);
			
			for(var i=0;i<list.length;i++){
				//var typeid = list[i].typeId; //0(企业活动领取码)   1(流量币领取码)  2(卡券)
				var typeid = "1"; //业务变更，这里只有流量币
				var type = list[i].type; //S(支出),R(收入)
				var availableTime = list[i].usedTime; //生效时间
				var itemQty = list[i].coinAmount; //兑换商品数量
				
				
				var strHtml = "<span class=\"c1\">@availableTime                   @type @typeid @itemQty个</span>";
				strHtml = strHtml.replaceAll("@availableTime",availableTime);
				
				if(typeid == '0'){
					strHtml = strHtml.replaceAll("@typeid","企业活动领取码");
				}else if(typeid == '1'){
					strHtml = strHtml.replaceAll("@typeid","流量币");
				}else if(typeid == '2'){
					strHtml = strHtml.replaceAll("@typeid","卡券");
				}else{
					strHtml = strHtml.replaceAll("@typeid","");
				}
				
				if(type == 'S'){
					strHtml = strHtml.replaceAll("@type","支出");
				}else if(type == 'R'){
					strHtml = strHtml.replaceAll("@type","收入");
				}else{
					strHtml = strHtml.replaceAll("@type","");
				}
				strHtml = strHtml.replaceAll("@itemQty",itemQty);
				
				
				$(dtEle).next("dd").children("div").append(strHtml);
				$(dtEle).next("dd").children("div").append("<hr data-am-widget=\"divider\" class=\"am-divider am-divider-default\"/>");
			
			}//end for
			
			$(dtEle).attr("loadFlag","1");
			
		}, 'json');
	}//end getFriendDetail
	
	//加载更多
	function loadFriendsMore(){

		//$("#window_mask").css("display","block");		
		
		var start = parseInt($("#start").val())+1;
		
		var url = '<%=basePath%>'+"user/loadFriendsMore";
		var params = {   
				openId:$('#openId').val(),
				start:start
		};
		
		//jQuery
		$.post(url, params, function(data){
			
			//$("#window_mask").css("display","none");	
			
			var list = data.resultList;
			
			if(list == null || list.length == 0){
				alert("没有更多数据");return;
			}
			
			for(var i=0;i<list.length;i++){
				var friendOpenId = list[i].friendOpenId;
				var nickName = list[i].nickName;
				var lastContact = list[i].lastContact;
				var headimgUrl = list[i].headimgUrl;
				
				var htmlArray = new Array();
				htmlArray.push("<dl class=\"am-accordion-item\">");
				htmlArray.push("    <dt class=\"am-accordion-title\" onClick=\"getFriendDetail('"+friendOpenId+"',this)\">");
				htmlArray.push("        <div class=\"am-g\">");
				htmlArray.push("            <div class=\"am-u-sm-8\" >");
				if(headimgUrl == "" || headimgUrl==null){
					htmlArray.push("                    <img src=\"${basePath}resource/assets/images/p0.jpg\" width=\"45\" height=\"45\" class=\"am-img-thumbnail am-radius\" > ");
				}else{
					htmlArray.push("                    <img src=\""+headimgUrl+"\" width=\"45\" height=\"45\" class=\"am-img-thumbnail am-radius\" > ");
				}
				htmlArray.push("                    <span class=\"widget-name\">"+nickName+"</span>");
				htmlArray.push("            </div>");
				htmlArray.push("            <div class=\"am-u-sm-4 am-gallery-desc lin1 fon1\">"+lastContact+"</div>");
				htmlArray.push("        </div>");
				htmlArray.push("    </dt>");
				htmlArray.push("    <dd class=\"am-accordion-bd am-collapse \">");
				htmlArray.push("          <div class=\"am-accordion-content\">");
				htmlArray.push("                ");
				htmlArray.push("           </div>");
				htmlArray.push("    </dd>");
				htmlArray.push("</dl>");
				
				var htmlStr = htmlArray.join("");
				
				$("#sectionEle").append(htmlStr); //向容器中添加html元素
			}
			
			$("#start").val(start);// 页码加1
			
		}, 'json');  //end .post

	}//end loadMore

</script>

</body>
</html>
