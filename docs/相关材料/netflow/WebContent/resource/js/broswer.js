var ua = navigator.userAgent, is_ios = /(iPhone|iPad|iPod|iOS)/i.test(ua), is_android = /(Android)/i
		.test(ua);

//获取微信版本号：例如5.2.1
function getWxVersion() {
	var reg = /MicroMessenger\/([\d\.]+)/i, ret = ua.match(reg);

	if (ret && ret[1]) {
		return ret[1];
	}
	return false;
}

var opfunc = {
	"cp-1" : function(a, b) {
		return a < b;
	},
	"cp0" : function(a, b) {
		return a == b;
	},
	"cp1" : function(a, b) {
		return a > b;
	},
};
//判断版本跟传入的version比较
//version为字符串：5.0.1
//op = -1小于 0等于 1大于
//如果 mmversion <op> version 返回true，否则返回false，如果浏览器不是微信内置webview则返回undefined
function cpVersion(version, op, canEq) {
	var mmversion = getWxVersion();
	if (!mmversion) {
		return;//如果浏览器不是微信内置webview则返回undefined
	}
	mmversion = mmversion.split(".");
	version = version.split(".");

	//5.2.380最后一位是versioncode 不加入判断 所以pop掉
	mmversion.pop();
	//version.pop();//传递进来比较的version不要理解versioncode这个！

	var mmv, v;
	var cp = opfunc["cp" + op];
	for (var i = 0, len = Math.max(mmversion.length, version.length); i < len; ++i) {
		mmv = mmversion[i] || 0;
		v = version[i] || 0;

		mmv = parseInt(mmv) || 0;
		v = parseInt(v) || 0;
		var eq = opfunc["cp0"](mmv, v);
		if (eq) {
			continue;
		}
		return cp(mmv, v);
	}
	return (!!canEq || op == 0) ? true : false;
}
function eqVersion(version) {
	return cpVersion(version, 0);
}
function gtVersion(version, canEq) {
	return cpVersion(version, 1, canEq);
}
function ltVersion(version, canEq) {
	return cpVersion(version, -1, canEq);
}

function isIOS() {
	return is_ios;
}
function isAndroid() {
	return is_android;
}

function getPlatform() {
	if (isIOS()) {
		return "ios";
	} else if (isAndroid()) {
		return "android";
	}
	return "unknown";
}