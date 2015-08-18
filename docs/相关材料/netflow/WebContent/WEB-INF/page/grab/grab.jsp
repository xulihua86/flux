<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String openId = request.getParameter("openId");
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
  <head>
  	<title>流量大抢yeah</title>
    <meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<meta name="format-detection" content="telephone=no">
	<meta name="renderer" content="webkit">
	<meta http-equiv="Cache-Control" content="no-siteapp" />
	
	<link rel="stylesheet" href="<%=path%>/resource/assets/css/amazeui.min.css"/>
    <link rel="stylesheet" href="<%=path%>/resource/assets/css/style.css"/>  
    <link rel="stylesheet" href="<%=path%>/resource/assets/css/body_style.css"/>
    
    <script src="<%=path%>/resource/assets/js/jquery.min.js"></script>
  	<script src="<%=path%>/resource/assets/js/amazeui.min.js"></script>
  	
  	<style type="text/css">
	    #rule {
	      position:absolute;
		  left:30px;
		  top:320px;
		  width:290px;
		  height:140px;
		  color:#FFFFFF;
		  line-height: 20px;
	    }
	    
	    #rule_title {
	      position:absolute;
	      left:30px;
		  text-align: center;
		  top:270px;
		  width:290px;
		  height:19px;
		  font-family:'Arial Normal', 'Arial';
		  font-weight:700;
		  font-style:normal;
		  font-size:17px;
		  color:#FFFFFF;
		}
		
		.ax_paragraph {
		  font-family:'Arial Normal', 'Arial';
		  font-weight:400;
		  font-style:normal;
		  font-size:13px;
		  color:#333333;
		  text-align:left;
		  line-height:normal;
		}
  	</style>
  </head>
  <body>
	  <div>
	  	<div>
	  	  <img id="grab" alt="图片名称" src="<%=path%>/resource/assets/images/u2.png"/>
	  	</div>
	  	<div>
	      <img id="grab_insurence" alt="图片名称" src="<%=path%>/resource/assets/images/u6.png"/>
	  	</div>
	      <div id="rule_title">
	      	<p><img id="u12_line" src="<%=path%>/resource/assets/images/u12_line.png" alt="u12_line"/><span>活动规则</span><img id="u12_line" class="img " src="<%=path%>/resource/assets/images/u12_line.png" alt="u12_line"/></p>
	      </div>
	 	  <div id="rule" class="ax_paragraph">
	      	<span class="am-g">
		      	1、在整个活动期间,飞流流量公社每个微信粉丝都可以参加抢流量活动；<br>
		      	2、每天10点开抢，10GB抢完为止；<br>
		      	3、抢到后，可赠等值流量给好友，抢一份，得两份；<br>
		      	4、最高可抢1GB流量，最低可抢5MB流量；<br>
		      	5、活动解释权归飞流流量公社所有。
		     </span>
	      </div>
	  </div>
  </body>
</html>