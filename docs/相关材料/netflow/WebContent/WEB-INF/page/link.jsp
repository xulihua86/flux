<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String openId = request.getParameter("openId");
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

	<link rel="stylesheet" href="<%=path%>/resource/assets/css/amazeui.min.css"/>
    <link rel="stylesheet" href="<%=path%>/resource/assets/css/style.css"/>  
    <link rel="stylesheet" href="<%=path%>/resource/assets/css/body_style.css"/>
    <script type="text/javascript" src="<%=path%>/resource/assets/js/jquery.min.js"></script>
    <script type="text/javascript">
    $(document).ready(function() {
    	//$('#bindingLink').click(function(e){
    		/* document.location.href = "https://open.weixin.qq.co/connect/oauth2/authorize?appid=wx137810a045c9a71e&redirect_uri=http%3A%2F%2Ffreeliu.orientalwisdom.com%2Fnetflow%2Fbinding%2FinitBinding&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect"; */
	    	var oauth2urlB = '${oauth2url}';//获取urlconfig.properties中通用的授权写法
			var initBindingUrl = '${wechatPlat}' + '${initBindingUrl}';
			initBindingUrl = encodeURI(initBindingUrl);
			bindingUrl = oauth2urlB.replace("REDIRECTURL", initBindingUrl);
			/* document.location.href = bindingUrl; */
			$('#bindingLink').attr('href',bindingUrl); 
    	//});
		
		//$('#signLink').click(function(e){
			/* document.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx137810a045c9a71e&redirect_uri=http%3A%2F%2Ffreeliu.orientalwisdom.com%2Fnetflow%2Fsign%2Fsign&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect"; */
			var oauth2urlS = '${oauth2url}';//获取urlconfig.properties中通用的授权写法
			var signUrl = '${wechatPlat}' + '${signUrl}';
			signUrl = encodeURI(signUrl);
			signUrl = oauth2urlS.replace("REDIRECTURL", signUrl);
			/* document.location.href = signUrl; */
			$('#signLink').attr('href',signUrl); 
		//});
    });
    
	</script>
	
	<div><img src="<%=path%>/resource/assets/images/p04.jpg" alt="" class="am-img-responsive"></div><br />
	<div class="btnbg1" ><a id="signLink" target="_parent" style="color: #fff">每日签到送流量</a></div>
	<div class="btnbg2" ><a id="bindingLink" target="_parent" style="color: #fff">再不绑定就晚了，现在绑定手机号，<br/>立即送25个流量币</a></div>
    
	<%-- <div data-am-widget="list_news" class="am-list-news">
	  <!--列表标题-->
	  <div class="am-list-news-bd">
	    <ul class="am-list">
	      <!--缩略图在标题左边-->
	      <li class="am-g am-list-item-desced am-list-item-thumbed am-list-item-thumb-left">
	        <div class="am-u-sm-4 am-list-thumb">
	          <a id="signLink1" target="_parent">
	            <img src="<%=path%>/resource/assets/images/p1.jpg" alt="图片名称" class="am-img-thumbnail am-circle" />
	          </a>
	        </div>
	        <div class=" am-u-sm-8 am-list-main">
	          <h3 class="am-list-item-hd">
	            <a id="signLink2" target="_parent" class="">每日签到送流量</a>
	          </h3>
	          
	        </div>
	      </li>
	      <li class="am-g am-list-item-desced am-list-item-thumbed am-list-item-thumb-left">
	        <div class="am-u-sm-4 am-list-thumb">
	          <a href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx137810a045c9a71e&redirect_uri=http%3A%2F%2Ffreeliu.orientalwisdom.com%2Fnetflow%2Fgrab%2FinitGrab&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect" target="_parent">
	            <img src="<%=path%>/resource/assets/images/p2.jpg" alt="图片名称" class="am-img-thumbnail am-circle" />
	          </a>
	        </div>
	        <div class=" am-u-sm-8 am-list-main">
	          <h3 class="am-list-item-hd">
	            <a href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx137810a045c9a71e&redirect_uri=http%3A%2F%2Ffreeliu.orientalwisdom.com%2Fnetflow%2Fgrab%2FinitGrab&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect" target="_parent" class="">每日10G流量，每晚10点，准时开抢！！</a>
	          </h3>
	          
	        </div>
	      </li>
	      <li class="am-g am-list-item-desced am-list-item-thumbed am-list-item-thumb-left">
	        <div class="am-u-sm-4 am-list-thumb">
	          <a id="bindingLink1" target="_parent">
	            <img src="<%=path%>/resource/assets/images/p3.jpg" alt="图片名称" class="am-img-thumbnail am-circle" />
	          </a>
	        </div>
	        <div class=" am-u-sm-8 am-list-main">
	          <h3 class="am-list-item-hd">
	            <a id="bindingLink2" target="_parent" class="">再不绑定就晚了，现在绑定手机号，立即送25个流量币</a>
	          </h3>
	        </div>
	      </li>
	    </ul>
	  </div>
	</div> --%>
	
