<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String openId = request.getParameter("openId");
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html>
<head lang="en">
  <meta charset="UTF-8">
  <title>签到送流量</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <meta name="format-detection" content="telephone=no">
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-siteapp" />
  <link rel="stylesheet" href="<%=path%>/resource/assets/css/amazeui.min.css"/>
  <link rel="stylesheet" href="<%=path%>/resource/assets/css/style.css"/>  
  <link rel="stylesheet" href="<%=path%>/resource/assets/css/body_style.css"/>
  
  
  <!--[if lt IE 9]>
  <script src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js"></script>
  <script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
  <script src="assets/js/polyfill/rem.min.js"></script>
  <script src="assets/js/polyfill/respond.min.js"></script>
  <script src="assets/js/amazeui.legacy.js"></script>
  <![endif]-->

  <script>
    var _hmt = _hmt || [];
    (function() {
      var hm = document.createElement("script");
      hm.src = "//hm.baidu.com/hm.js?789d2116cf568ce6c4aab600f7f7b62b";
      var s = document.getElementsByTagName("script")[0]; 
      s.parentNode.insertBefore(hm, s);
    })();
  </script>
  
  <!--[if (gte IE 9)|!(IE)]><!-->
  <script src="<%=path%>/resource/assets/js/jquery.min.js"></script>
  <script src="<%=path%>/resource/assets/js/amazeui.min.js"></script>
  <script type="text/javascript" src="<%=path%>/resource/js/weixin.js"></script>
  <!--<![endif]-->
  
  <script type="text/javascript">
    $(function() {
    	var message = $("#message").val();
    	if(message != null && message == "fail") {
    		$("#sign").attr('disabled',"true");
    		alert("亲，页面存在异常，暂时无法签到哦~");
    	}

    	$('#sign').click(function(e){
    		/*
    		var oauth2urlS = '${oauth2url}';//获取urlconfig.properties中通用的授权写法
			var toSignUrl = '${wechatPlat}' + '${toSignUrl}';
			toSignUrl = encodeURI(toSignUrl);
			oauth2urlS = oauth2urlS.replace("REDIRECTURL", toSignUrl);
			*/
    		$.ajax({
    			cache:false,
    			type:'POST',
    	   		dataType: 'json',
    	   		url:"<%=path%>/sign/toSign",
    			data : {
    				
    			},
    			async : false,
    			success : function(data) {
    				if(data != null && data.checkCount != null && data.checkCount != ""){
	   				 	document.location.href = "signOver?awardByOne=${awardByOne}&awardCumulatively=${awardCumulatively}&checkCount="+data.checkCount;
    				} else {
    					alert("对不起，签到失败，请退出至首页后重新进行操作！");
    				}
    			}
    		});
    	});
    });
    
    </script>
</head>
<body>

<!-- composed_type -->

<input type="hidden" id="message" value="${message}">

<div class="am-g am-top1">
	<div class="am-u-sm-centered am-u-center am-bottom1">
		<h3 class="am-article-title am-text-xl">签到一次  送<b class="c2">${awardByOne}</b>个流量币<br />
		一月累计签到<b class="c2">20</b>天<br />再送<b class="c2">${awardCumulatively}</b>个流量币~<br /></h3>
		本月累计签到<b class="c2">${checkCount}</b>次<br /><br />
		<div class="am-form-group">
		      <button type="button" id="sign" class="am-btn am-btn-primary am-radius am-btn-block ">签&nbsp;&nbsp;&nbsp;到</button>
		</div>
		<span class="c3">每天只能签一次哟~</span>
	</div>
	<div class="bg_c1">
		<iframe src="<%=path%>/link" scrolling="no" width="100%" onload="this.height=this.contentWindow.document.documentElement.scrollHeight"></iframe>
	</div>
</div>

<%-- <div class="am-g am-top1">
	<div class="am-u-sm-centered am-u-center am-bottom1">
		<h3 class="am-article-title fon3">签到一次  送<b class="c1">${awardByOne}</b>个流量币<br />
		一月累计签到20天 再送<b class="c1">${awardCumulatively}</b>个流量币~<br /></h3>
		<span class="c1 fon1">本月累计签到${checkCount}次</span>
		<button id="sign" type="button" class="am-btn am-btn-danger1 am-round am-btn-block">签到</button>
		<span class="c1 fon1">每天只能签一次哟~</span>
	</div>
	<div class="bg_c1">
		<div class="tit1">赚三网流量</div>
		<iframe src="<%=path%>/link" scrolling="no" width="100%" onload="this.height=this.contentWindow.document.documentElement.scrollHeight"></iframe>
	</div>
</div> --%>
</body>
</html>
