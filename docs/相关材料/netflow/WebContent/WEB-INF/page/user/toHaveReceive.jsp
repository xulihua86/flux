<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

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

  <title>微信卡券</title>

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
  
</head>
<body>

<div id="window_mask" style=" position:absolute;top:0px;left:0px; filter:alpha(opacity=70); opacity:0.7; width: 100%; height: 100%; display: block; z-index: 100; background-color:#FFF"></div>

<div data-am-widget="list_news" class="am-list-news am-list-news-default">
  
  <%--${data.cardSign}<br/>
  ${data.nonceStr}<br/>
  ${data.timestamp}<br/>
  ${data.signature}<br/>--%>
  
</div>


<input type="hidden" id="cardId" />
<input type="hidden" id="encryptCode" onChange="showCardDetail()" />

<input type="hidden" id="backFlag" value="1" /> <!-- 1:第一次加载;2:通过手机上"返回" -->

<script>
	function showCardDetail(){
		
		//将encryptCode转为code
		var url = '<%=basePath%>user/getCode';
		var params = {   
				cardId:$("#cardId").val(),
				encryptCode:$("#encryptCode").val()
		};
		
		/*$.post(url, params, function(data){
			
			alert(222);
			if(data.operateFlag == "1"){
				var code = data.code;
				alert("code："+code);
				//wx.openCard({cardId:$("#cardId").val(),code:code});
				
				wx.openCard({
					cardList: [{
						cardId: $("#cardId").val(),
						code: code
					}]// 需要打开的卡券列表
				});

			}else{
				alert(data.operateMessage);
			}
			
		}, 'json');*/
		
		$.ajax({
               type: "GET",
			   //contentType: "application/json;utf-8",
               async: false,
               url: url,
               data: params,
               success: function(data){
                    
					data = eval("("+data+")");
					var code = data.cardCode;

					wx.openCard({
						cardList: [{
							cardId: $("#cardId").val(),
							code: code
						}]// 需要打开的卡券列表
					});
               },
               error: function(data){
                    alert("encrypt_code转换异常，卡券打开失败");
               }
            });
		
	}//end showCardDetail

	/**
	  *调用微信js-sdk
	  */
	  wx.config({
			 debug: false, 
			 appId : '${data.appId}',
			 timestamp : parseInt('${data.timestamp}'),
			 nonceStr : '${data.nonceStr}',
			 signature : '${data.signature}',
			 jsApiList: ['chooseCard' ,'openCard' ] 
	 });
	 wx.error( function(res) {
		  alert(res.errMsg);
	 });


	$(document).ready(function(e) {
				
		 wx.ready(function(){
			 	$("#window_mask").css("display","none");
				wx.chooseCard({      
					  cardSign: '${data.cardSign}',
					  timestamp: '${data.timestamp}',      
					  nonceStr: '${data.nonceStr}',      
					  success: function (res) {
						  
						if(res == null){
							alert("没有卡券");
						}
						   
						var cardList = res.cardList;
						if(cardList == null || cardList.length == 0 ){
							alert("空券为空^_^");
						}else{
							
							cardList = eval("("+cardList+")");
							
							var cardId = cardList[0]["card_id"];
							var encryptCode = cardList[0]["encrypt_code"];
							
							$("#cardId").val(cardId);
							$("#encryptCode").val(encryptCode);
							
							$("#encryptCode").trigger("change");
							
						}//end else
						
					  }
				 });//end wx.chooseCard
		 });//end wx.ready(function(){

	
			/*wx.chooseCard({
				shopId: '', // 门店Id
				cardType: '', // 卡券类型
				cardId: '', // 卡券Id
				timestamp: 0, // 卡券签名时间戳
				nonceStr: '', // 卡券签名随机串
				signType: '', // 签名方式，默认'SHA1'
				cardSign: '', // 卡券签名，详见附录4
				success: function (res) {
					var cardList= res.cardList; // 用户选中的卡券列表信息
					alert(1111);
				}
			});*/
		
    });

	

</script>


</body>
</html>