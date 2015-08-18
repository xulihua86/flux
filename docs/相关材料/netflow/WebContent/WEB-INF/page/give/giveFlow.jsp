<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String openId = request.getParameter("openId");
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String basePath2 = request.getScheme() + "://"
			+ request.getServerName()+ path + "/";
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-title" content="" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>转赠流量</title>
<meta name="renderer" content="webkit">
<link rel="stylesheet" href="<%=path%>/resource/assets/css/amazeui.min.css" />
<link rel="stylesheet" href="<%=path%>/resource/assets/css/style.css"/>
<link rel="stylesheet" href="<%=path%>/resource/assets/css/body_style.css"/> 
<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?789d2116cf568ce6c4aab600f7f7b62b";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
</script>
<script type="text/javascript" src="<%=path%>/resource/assets/js/jquery.min.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="<%=path%>/resource/assets/js/amazeui.js"></script>
<script type="text/javascript" src="<%=path%>/resource/assets/js/handlebars.min.js"></script>
<script type="text/javascript" src="<%=path%>/resource/assets/js/amazeui.widgets.helper.js"></script>
<script type="text/javascript">
	//window.location.href="http://mp.weixin.qq.com/s?__biz=MzAxMjIwMzg1Nw==&mid=216040890&idx=2&sn=96b1195d8271e91d351bceafdd82a3af#rd";
	var num = 0;
	//var openId = '${openId}';
	var oauth2link = "";
	/**
	*校验输入的是否是数字
	*/
	function validate(value){  
		//var reg = /^\d+$/;
		var reg = "^[1-9]*[1-9][0-9]*$";
	    if(value.match( reg )){
	        return true;
	    }
	    return false;
	};
	/**
	*加载时隐藏分享提示面板
	*/
	$(function(){
		$("#result").val(0);
		$("#panel").hide();
	});
	
	function clearzero(){
		var value  = $("#result").val();
		if(value == 0 || value =='0'){
			$("#result").val('');
		}
	}
	  /**
	  *拖动滑块事件
	  */
	  function chaneValue(){
	  	var value = parseInt($("#range").val());
	  	$("#result").val(value);
	  };
	  /**
	  *输入文本框时，给滑块赋值
	  */
	  function changeLineValue(){
	  	var value  = $("#result").val();
	  	if(!validate(value)){
	  		return;
	  	}
	  	$("#range").val(value);
	  	
	  };
	  /**
	  *点击选择好友异步获取分享链接，加载微信分享菜单
	  */
	  function showPanel(){
	  	//校验
	  	var value  = $("#result").val();
	  	if(!validate(value)){
	  		alert("亲，只能送整数个");
	  		$("#result").val(0);
	  		return;
	  	}
	  	value = parseInt($("#result").val());
	  	var minvalue = parseInt($("#minvalue").text());
	  	var maxvalue = parseInt($("#maxvalue").text());
	  	if(value<=0){
	  		alert("亲，请送出数量大于0的流量币");
	  		return;
	  	}
	  	if(value>maxvalue){
	  		alert("亲，您的流量币不够！");
	  		return;
	  	}
	  	
	  	num = $("#result").val();
	  	$("body").css("background-color","#191816");
	  	$("#main").hide();
	  	$("#panel").show();
	  	//异步调用获取分享链接，获取分享链接的同时生成领取码扣除流量币	
		$.ajax({
		  	cache:false,
			type: "POST",
			url: "<%=basePath%>give/getShareLink",
			data :{
				//openId:'${openId}',
				coin:$("#result").val()
			}, 
			async : true,
			success: function(data){//获取链接成功
				if(parseInt(data.code) != 000000){// 调用接口失败
					 window.location.href = "<%=basePath%>give/giveFailure";
				}
				oauth2link = data.oauth2link;//加密签名后的领取链接
				var receiveCode = data.receiveCode;//领取码
				var imgurl = "<%=basePath2%>resource/images/give/u8.jpg";
				//加载微信分享菜单
			    wx.onMenuShareAppMessage({
		              title: '我送给你'+num+'个流量币，快去飞流流量公社兑换流量吧!', // 分享标题
		              desc: '如果你想以后还能收到如此精彩的文章，请点击本文标题和第一张图片之间的……', // 分享描述
		              link:oauth2link,//分享链接
		              //imgUrl: 'http://jinzhoujiannan.xicp.net/netflow/resource/images/give/u8.jpg',
		              imgUrl: imgurl,
		              success: function () {
		              //分享成功回调
		              	window.location.href = "<%=basePath%>give/giveSuccess?coin="+num+"&receiveCode="+receiveCode;
		              },
		              fail: function() {
		              //分享失败回调
		              	window.location.href = "<%=basePath%>give/giveFailure";
		              },
		              cancel: function () {
		                  // 用户取消分享后执行的回调函数
		                  $("body").css("background-color","#ffffff");
						  $("#main").show();
						  $("#panel").hide();
		              }
			     });
			},
			error:function(){//获取分享链接失败，跳转到失败页面
				 window.location.href = "<%=basePath%>give/giveFailure";
			}
		});	
	  };
	  /**
	  *调用微信js-sdk
	  */
      wx.config({
         debug: false, 
         appId : '${appId}',
		timestamp : parseInt('${timestamp}'),
		nonceStr : '${nonceStr}',
		signature : '${signature}',
         jsApiList: ['onMenuShareTimeline' ,'hideMenuItems', 'onMenuShareAppMessage' ] 
     });
     
     wx.ready( function() {
          wx.hideMenuItems({
               menuList: ['menuItem:share:email' ,'menuItem:openWithSafari',
               'menuItem:openWithQQBrowser' ,'menuItem:readMode',
               'menuItem:originPage' ,
               'menuItem:editTag','menuItem:jsDebug' ,
               'menuItem:favorite' ,'menuItem:share:facebook',
               'menuItem:share:weiboApp' ,'menuItem:copyUrl',
               'menuItem:refresh','menuItem:share:qq',
               'menuItem:share:timeline' ]
          });
          
     });

     wx.error( function(res) {
		 window.location.href = "<%=basePath%>give/giveFailure";
          //alert(res.errMsg);
     });
 
  </script>
</head>
<body>
	<%-- <div id = "main" class="am-g am-top1">
		<div class="am-u-sm-centered am-u-center">
		您拥有<span class="c1">${availableAmount}</span>流量币<br />
		请叫我流量大户！<br />
		<h2>赠送</h2><br />
		<div style= "width:70%;margin-bottom:0;height:2rem;">
	  		<ul class="am-avg-sm-2">
			  <li id = "minvalue" style = "text-align:left;">0</li>
			  <li id = "maxvalue" style = "text-align:right;">${availableAmount}</li>
			</ul>
		</div>
		<span><input id="range" type="range" min = "0" max = "${availableAmount}"  value = "0" onchange="chaneValue()" style = "width:70%;"/></span>
		<span><input id="result" type="text" value="" onchange="changeLineValue()" onfocus="clearzero()" style = "width:20%;"class = "am-text-middle"></span>
		<sapn style = "width:5%;" class = "am-text-middle">个</sapn>
		<h2>TO</h2>
		<button type="button" class="am-btn am-btn-danger1 am-round am-btn-block" onclick = "showPanel()">选择好友</button>
		</div>
	</div>
	<div id = "panel" style = "width:100%;margin: 0 auto 0 auto;text-align: center;display: none;">
		<img alt="" src="<%=path%>/resource/images/give/prompt.png">
	</div> --%>
	
	
	<div id = "main" class="am-g am-top1">
		<div class="am-u-sm-centered am-u-center">
			<h2 align="center">您拥有<b class="c2"> ${availableAmount}</b> 个流量币</h2>
			<h2 align="left">赠送</h2>
			<div style= "width:70%;margin-bottom:0;height:2rem;">
		  		<ul class="am-avg-sm-2">
				  <li id = "minvalue" style = "text-align:left;">0</li>
				  <li id = "maxvalue" style = "text-align:right;">${availableAmount}</li>
				</ul>
			</div>
			<span><input id="range" type="range" min = "0" max = "${availableAmount}"  value = "0" onchange="chaneValue()" style = "width:70%;"/></span>
			<span><input id="result" type="text" value="" onchange="changeLineValue()" onfocus="clearzero()" style = "width:20%;"class = "am-text-middle"></span>
			<span style = "width:5%;" class = "am-text-middle">个</span>
			<br />
			<br />
			<br />
			<button type="button" class="am-btn  am-btn-primary am-radius am-btn-block"  onclick = "showPanel()">选择好友</button>
		</div>
	</div>
	<div id = "panel" style = "width:100%;margin: 0 auto 0 auto;text-align: center;display: none;">
		<img alt="" src="<%=path%>/resource/images/give/prompt.png">
	</div>
</body>
</html>