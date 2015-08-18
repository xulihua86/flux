<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
	String basePath2 = request.getScheme() + "://"
			+ request.getServerName()+ path + "/";
%>
<!doctype html>
<html>
<head lang="en">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-title" content="" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>充值记录</title>
<meta name="renderer" content="webkit">
<link rel="stylesheet" href="<%=path%>/resource/assets/css/amazeui.min.css" />
<link rel="stylesheet" href="<%=path%>/resource/assets/css/style.css" />
<link rel="stylesheet" href="<%=path%>/resource/assets/css/body_style.css" />
<link rel="stylesheet" href="<%=path%>/resource/assets/css/app.css" />
<script type="text/javascript" src="<%=path%>/resource/assets/js/jquery.min.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="<%=path%>/resource/iscroll/iscroll.js"></script>
<script type="text/javascript" src="<%=path%>/resource/iscroll/iscrollAssist.js"></script>
<script type="text/javascript" >
var length=10;
var generatedCount=0;
//var openId = "${payerId}";
$(function() {//初始化数据
	initdata();
});
(function($) {
	$(function() {
		$('#pullup').hide();
		var pulldownAction = function() {
			$('#pullup').hide();
			//var content = getChargeRecords(generatedCount);
			//$('#datalist').prepend(content);
			this.refresh();
			console.log("下拉执行逻辑");
		};
		var pullupAction = function() {
			var content = getChargeRecords(generatedCount);
			$('#datalist').append(content);
			this.refresh();
			$('#pullup').hide();
			console.log("上拉执行逻辑");
		};
		var iscrollObj = iscrollAssist.newVerScrollForPull($('#wrapper'), pulldownAction, pullupAction, {click:true});
		iscrollObj.refresh();
	});
})(jQuery);
	
function init(){
	var html = getChargeRecords("0");
	if(html != null && html != ''){
		$('#datalist').html(html);
		console.log("init");
	}else{
		document.location.href="<%=path%>/chargeRecord/noChargeRecord";
	}
}
function initdata(){
	var html = '';
	var data = "${resultList}";
	data = data.replace(/[\r\n]/g, "");
	data = data.substring(2, data.length-2);
	var dataArray = data.split("}, {");
	var length = dataArray.length;
	for(var i =0;i<length;i++){
		generatedCount++
		var rowdata = "";//一条数据,字符串格式的
		rowdata = rowdata+dataArray[i];
		var rowdataArray = rowdata.split(",");//每条数据中包含的属性数据,数据格式{a=a,b=b,c=c}
		
		var modifyTimeArray = String(rowdataArray[0]).split("=");
		var idArray = String(rowdataArray[1]).split("=");
		var istatusArray = String(rowdataArray[2]).split("=");
		var activeTypeIdArray = String(rowdataArray[3]).split("=");
		var recchargeMobileNoArray = String(rowdataArray[4]).split("=");
		var rechargeSnapArray = String(rowdataArray[5]).split("=");
		var operatorTypeArray = String(rowdataArray[6]).split("=");
		var sourceTypeArray = String(rowdataArray[7]).split("=");
		
		var modifyTime = modifyTimeArray[1];
		var id = idArray[1];
		var istatus = istatusArray[1];
		var activeTypeId = activeTypeIdArray[1];
		var recchargeMobileNo = recchargeMobileNoArray[1];
		var rechargeSnap = rechargeSnapArray[1];
		var operatorType = operatorTypeArray[1];
		var sourceType = sourceTypeArray[1];//0、领取码；1、券；2、流量币
		//组织html
		var activeTypeName = "当月生效";
 		if(activeTypeId== '1') {
 			activeTypeName = "下月生效";
 		}
 		var imagesrc = "";
 		if(sourceType=='0'){
 			imagesrc = "<%=path%>/resource/assets/images/btn03.png";
 		}else if(sourceType=='1'){
 			imagesrc = "<%=path%>/resource/assets/images/btn01.png";
 		}else if(sourceType=='2'){
 			imagesrc = "<%=path%>/resource/assets/images/btn02.png";
 		}
 		var status = '充值失败';
 		if(istatus == '1'){
 			status='等待充值';
 		}else if(istatus == '2'){
 			status='充值成功';
 		}else if(istatus == '3'){
 			status='充值失败';
 		}else if(istatus == '4'){
 			status='已退存';
 		}
 		//var value ="modifyTime="+modifyTime+",status="+status+",recchargeMobileNo="+recchargeMobileNo +",rechargeSnap="+rechargeSnap+",activeTypeName="+activeTypeName+",orderId="+id+",activeTypeId="+activeTypeId+",openId="+openId+",sourceType="+sourceType;
 		var value ="modifyTime="+modifyTime+",status="+status+",recchargeMobileNo="+recchargeMobileNo +",rechargeSnap="+rechargeSnap+",activeTypeName="+activeTypeName+",orderId="+id+",activeTypeId="+activeTypeId+",sourceType="+sourceType;
		//开始
 		//html += "<div class='am-g'><div class='am-u-sm-centered am-text-sm'>";
 		//列表
 		if(istatus == '1'){
 			html += "<div class='am-g fon1'><hr data-am-widget='divider' class='am-divider am-divider-top'/><table border='0' cellspacing='0' cellpadding='0'>";
 			html += "<tr><td width='20%' style='padding-left:1.2em;'><img src='"+imagesrc+"' class='am-img-responsive' ></td>";
 			html += "<td width='40%' style='padding-left:.5em;'><b>"+recchargeMobileNo+"</b><br />";
 			html += "<span class='c3'>("+activeTypeName+")<br />"+modifyTime+"</span></td>";
 			html += "<td width='23%' style='padding-left:.5em;'><span class='c2'>"+rechargeSnap+"</span><br /><br /><span class='c2'>正在充值</span></td>";
 			html += "</tr></table><hr data-am-widget='divider' class='am-divider am-divider-bottom'/></div>";
 		}else if(istatus == '2'){
 			html += "<div class='am-g fon1'><hr data-am-widget='divider' class='am-divider am-divider-top'/><table border='0' cellspacing='0' cellpadding='0'>";
 			html += "<tr><td width='20%' style='padding-left:1.2em;'><img src='"+imagesrc+"' class='am-img-responsive' ></td>";
 			html += "<td width='40%' style='padding-left:.5em;'><b>"+recchargeMobileNo+"</b><br />";
 			html += "<span class='c3'>("+activeTypeName+")<br />"+modifyTime+"</span></td>";
 			html += "<td width='23%' style='padding-left:.5em;'><span class='c2'>"+rechargeSnap+"</span><br /><br /><span class='c3'>充值成功</span></td>";
 			html += "</tr></table><hr data-am-widget='divider' class='am-divider am-divider-bottom'/></div>";
 		}else if(istatus == '4'){
 			html += "<div class='am-g fon1'><hr data-am-widget='divider' class='am-divider am-divider-top'/><table border='0' cellspacing='0' cellpadding='0'>";
 			html += "<tr><td width='20%' style='padding-left:1.2em;'><img src='"+imagesrc+"' class='am-img-responsive' ></td>";
 			html += "<td width='40%' style='padding-left:.5em;'><b>"+recchargeMobileNo+"</b><br />";
 			html += "<span class='c3'>("+activeTypeName+")<br />"+modifyTime+"</span></td>";
 			html += "<td width='23%' style='padding-left:.5em;'><span class='c2'>"+rechargeSnap+"</span><br /><br /><span class='c3'>已退存</span></td>";
 			html += "</tr></table><hr data-am-widget='divider' class='am-divider am-divider-bottom'/></div>";
 		}else{
 			html += "<div class='am-g fon1 c0'><hr data-am-widget='divider' class='am-divider am-divider-top'/><table border='0' cellspacing='0' cellpadding='0'>";
 			html += "<tr><td width='20%' style='padding-left:1.2em;'><img src='"+imagesrc+"' class='am-img-responsive' ></td>";
 			html += "<td width='45%' style='padding-left:.5em;'><b>"+recchargeMobileNo+"</b><br />";
 			html += "<span class='c3'>("+activeTypeName+")<br />"+modifyTime+"</span></td>";
 			html += "<td width='25%' style='padding-left:.5em;'><span class='c2'>"+rechargeSnap+"</span><br /><span class='c3'>点击重新充值</span><br />";
 			html += "<span class='c1'><a href='javascript:recharge(\"" + value+"\");'>充值失败</a></span></td>";
 			html += "</tr></table><hr data-am-widget='divider' class='am-divider am-divider-bottom'/></div>";
 		}
 		
 		//结束 
 		//html += "</div></div>";
	}
	if(html != null && html != ''){
		$('#datalist').html(html);
		console.log("init");
	}else{
		document.location.href="<%=path%>/chargeRecord/noChargeRecord";
	}
}

function getChargeRecords(start){
	var html = '';
	$.ajax({
		cache:false,
		type:'POST',
   		dataType: 'json',
   		url:"<%=basePath2%>chargeRecord/getList",
		data : {
			start:start,
			length:length 
			},
		async : false,
		success : function(data) {
			if(data.status=='success' && data.result && data.result.length > 0){
				$.each(data.result, function(i,val){
					generatedCount++;
					var activeTypeName = "当月生效";
			 		if(val.activeTypeId== '1') {
			 			activeTypeName = "下月生效";
			 		}
			 		var imagesrc = "";
			 		if(val.sourceType=='0'){
			 			imagesrc = "<%=path%>/resource/assets/images/btn03.png";
			 		}else if(val.sourceType=='1'){
			 			imagesrc = "<%=path%>/resource/assets/images/btn01.png";
			 		}else if(val.sourceType=='2'){
			 			imagesrc = "<%=path%>/resource/assets/images/btn02.png";
			 		}
			 		var status = '充值失败';
			 		if(val.status == '1'){
			 			status='等待充值';
			 		}else if(val.status == '2'){
			 			status='充值成功';
			 		}else if(val.status == '3'){
			 			status='充值失败';
			 		}else if(val.status == '4'){
			 			status='已退存';
			 		}
			 		var value ="modifyTime="+val.modifyTime+",status="+status+",recchargeMobileNo="+val.recchargeMobileNo +",rechargeSnap="+val.rechargeSnap+",activeTypeName="+activeTypeName+",orderId="+val.id+",activeTypeId="+val.activeTypeId+",sourceType="+val.sourceType;
					//var value ="modifyTime="+val.modifyTime+",status="+status+",recchargeMobileNo="+val.recchargeMobileNo +",rechargeSnap="+val.rechargeSnap+",activeTypeName="+activeTypeName+",orderId="+val.id+",activeTypeId="+val.activeTypeId+",openId="+openId+",sourceType="+val.sourceType;
			 		//开始
			 		//html += "<div class='am-g'><div class='am-u-sm-centered am-text-sm'>";
			 		//列表
			 		if(val.status == '1'){
			 			html += "<div class='am-g fon1'><hr data-am-widget='divider' class='am-divider am-divider-top'/><table border='0' cellspacing='0' cellpadding='0'>";
			 			html += "<tr><td width='20%' style='padding-left:1.2em;'><img src='"+imagesrc+"' class='am-img-responsive' ></td>";
			 			html += "<td width='40%' style='padding-left:.5em;'><b>"+val.recchargeMobileNo+"</b><br />";
			 			html += "<span class='c3'>("+activeTypeName+")<br />"+val.modifyTime+"</span></td>";
			 			html += "<td width='23%' style='padding-left:.5em;'><span class='c2'>"+val.rechargeSnap+"</span><br /><br /><span class='c2'>正在充值</span></td>";
			 			html += "</tr></table><hr data-am-widget='divider' class='am-divider am-divider-bottom'/></div>";
			 		}else if(val.status == '2'){
			 			html += "<div class='am-g fon1'><hr data-am-widget='divider' class='am-divider am-divider-top'/><table border='0' cellspacing='0' cellpadding='0'>";
			 			html += "<tr><td width='20%' style='padding-left:1.2em;'><img src='"+imagesrc+"' class='am-img-responsive' ></td>";
			 			html += "<td width='40%' style='padding-left:.5em;'><b>"+val.recchargeMobileNo+"</b><br />";
			 			html += "<span class='c3'>("+activeTypeName+")<br />"+val.modifyTime+"</span></td>";
			 			html += "<td width='23%' style='padding-left:.5em;'><span class='c2'>"+val.rechargeSnap+"</span><br /><br /><span class='c3'>充值成功</span></td>";
			 			html += "</tr></table><hr data-am-widget='divider' class='am-divider am-divider-bottom'/></div>";
			 		}else if(val.status == '4'){
			 			html += "<div class='am-g fon1'><hr data-am-widget='divider' class='am-divider am-divider-top'/><table border='0' cellspacing='0' cellpadding='0'>";
			 			html += "<tr><td width='20%' style='padding-left:1.2em;'><img src='"+imagesrc+"' class='am-img-responsive' ></td>";
			 			html += "<td width='40%' style='padding-left:.5em;'><b>"+val.recchargeMobileNo+"</b><br />";
			 			html += "<span class='c3'>("+activeTypeName+")<br />"+val.modifyTime+"</span></td>";
			 			html += "<td width='23%' style='padding-left:.5em;'><span class='c2'>"+val.rechargeSnap+"</span><br /><br /><span class='c3'>已退存</span></td>";
			 			html += "</tr></table><hr data-am-widget='divider' class='am-divider am-divider-bottom'/></div>";
			 		}else{
			 			html += "<div class='am-g fon1 c0'><hr data-am-widget='divider' class='am-divider am-divider-top'/><table border='0' cellspacing='0' cellpadding='0'>";
			 			html += "<tr><td width='20%' style='padding-left:1.2em;'><img src='"+imagesrc+"' class='am-img-responsive' ></td>";
			 			html += "<td width='45%' style='padding-left:.5em;'><b>"+val.recchargeMobileNo+"</b><br />";
			 			html += "<span class='c3'>("+activeTypeName+")<br />"+val.modifyTime+"</span></td>";
						html += "<td width='25%' style='padding-left:.5em;'><span class='c2'>"+val.rechargeSnap+"</span><br /><span class='c3'>点击重新充值</span><br />";
 						html += "<span class='c1'><a href='javascript:recharge(\"" + value+"\");'>充值失败</a></span></td>";
			 			html += "</tr></table><hr data-am-widget='divider' class='am-divider am-divider-bottom'/></div>";
			 		}
			 		//结束 
			 		//html += "</div></div>";
			 		
        			
				});
			}
		}
	});
	return html;
}
function recharge(val){
	var url = "<%=path%>/chargeRecord/getOne?content="+val+"";
	url=encodeURI(url); 
	url=encodeURI(url); 
	document.location.href=url;
}
</script>
<style>
body {
	background: #EBEBEB;
}
/****** 下拉刷新、上拉加载更多的样式 ********/
#pulldown,#pullup {
	background: #EBEBEB;
	height: 40px;
	line-height: 40px;
	border-bottom: 1px solid #ccc;
	font-weight: bold;
	font-size: 14px;
	color: #888;
}

#pulldown .pulldown-icon,
#pullup .pullup-icon {
	display: block;
	float: left;
	width: 40px;
	height: 40px;
	background: url(<%=path%>/resource/iscroll/images/pull-icon@2x.png) 0 0 no-repeat;
	-webkit-background-size: 40px 80px;
	background-size: 40px 80px;
	-webkit-transition-property: -webkit-transform;
	-webkit-transition-duration: 250ms;
}

#pulldown .pulldown-icon {
	-webkit-transform: rotate(0deg) translateZ(0);
}

#pullup .pullup-icon {
	-webkit-transform: rotate(-180deg) translateZ(0);
}

#pulldown .flip .pulldown-icon {
	-webkit-transform: rotate(-180deg) translateZ(0);
}

#pullup .flip .pullup-icon {
	-webkit-transform: rotate(0deg) translateZ(0);
}

#pulldown .loading .pulldown-icon,
#pullup .loading .pullup-icon {
	background-position: 0 100%;
	-webkit-transform: rotate(0deg) translateZ(0);
	-webkit-transition-duration: 0ms;
	-webkit-animation-name: loading;
	-webkit-animation-duration: 2s;
	-webkit-animation-iteration-count: infinite;
	-webkit-animation-timing-function: linear;
}

@-webkit-keyframes loading {from { -webkit-transform:rotate(0deg)translateZ(0);}

to {-webkit-transform: rotate(360deg) translateZ(0);}

}
dd {
	border-bottom: 1px solid #ccc;
	height: 50px;
	line-height: 50px;
	margin-left: 0px;
	text-align: center;
}
.am-divider-mine{
	height: 1px;
	border: none;
	margin: 0 auto;
	overflow: hidden;
	background-color: #dddddd;
	clear: both;
}
.am-u-sm-pd0{
	padding-left:0;
	padding-right:0;
}
.am-divider-bottom {
	height: 1px;
	border: none;
	margin: 1.5rem auto 0 auto;
	overflow: hidden;
	background-color: #ddd;
	clear: both;
}
.am-divider-top {
	height: 1px;
	border: none;
	margin: 0 auto 1.5rem auto;
	overflow: hidden;
	background-color: #ddd;
	clear: both;
}
</style>
</head>
<body style="overflow-y: hidden; position: absolute; left: 0em; right: 0em; top: 0em; bottom: 0em; margin: 0em">
	<!-- <div id="wrapper" style="position: absolute; width: 100%; height: 100%; overflow: hidden">
		<dl style="-webkit-margin-before: 0em;">
			<div id="pulldown">
				<span class="pulldown-icon"></span><span id="pulldown-label"></span>
			</div>
			<div class="am-g">
				<div class="am-u-sm-centered"  id="datalist">
				</div>
			</div>
			<div class="am-paragraph am-paragraph-default" id="datalist">
			</div>
			<div id="pullup">
				<span class="pullup-icon"></span><span id="pullup-label"></span>
			</div>
		</dl>
	</div> -->
	<div id="wrapper" style="position: absolute; width: 100%; height: 100%; overflow: hidden">
		<dl style="-webkit-margin-before: 0em;">
			<div id="pulldown">
				<span class="pulldown-icon"></span><span id="pulldown-label"></span>
			</div>
			<div class="am-g am-top2">
			<div class="am-u-sm-centered" id="datalist">
			
			
			</div>
			</div>	
			<div id="pullup">
				<span class="pullup-icon"></span><span id="pullup-label"></span>
			</div>
		</dl>
	</div> 
<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/amazeui.min.js"></script>
</body>
</html>
