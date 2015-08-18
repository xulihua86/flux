window.onload = function() {
	if (isWeiXin()) {
		var p = document.getElementsByTagName('p');
		p[0].innerHTML = window.navigator.userAgent;
	}
}
function isWeiXin() {
	var ua = window.navigator.userAgent.toLowerCase();
	if (ua.match(/MicroMessenger/i) == 'micromessenger') {
		return true;
	} else {
		return false;
	}
}