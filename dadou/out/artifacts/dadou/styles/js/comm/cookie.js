///////////////////Cookie Start ////////////////////////////////////
/*
	FileName    :dh.cookie.js
	Author      :motaimin
	Version     :20120723
*/
// 有关cookie的操作
function Cookie() {
}
/**
* 设置cookie值
* 参数：cookie名，值，生存天数，生存小时数
*/
Cookie.setCookie = function (name, value, days, hours) {
    // 如果没有传入生存期，则默认为30天2小时
	value = value + "";
	days = days || 30;
	hours = hours || 2;
	var expTime = new Date();
	expTime.setDate(expTime.getDate() + days);
	expTime.setHours(expTime.getHours() + hours);
	document.cookie = escape(name) + "=" + escape(value) + ";" + "expires=" + expTime.toGMTString();
};

// 获取某个cookie值
// 返回：cookie值或undefined
Cookie.getCookie = function (name) {
	var rs = document.cookie.match(new RegExp("(?:^| )" + escape(name) + "=([^;]*)(?:;|$)", "i"));
	return rs ? unescape(rs[1]) : undefined;
};
// 删除某个cookie值
Cookie.deleteCookie = function (name) {
	document.cookie = escape(name) + "=;expires=" + new Date().toGMTString();
};
// 清除全部cookie
Cookie.clearCookie = function () {
    // 获取过期时间字符串
	var expStr = new Date().toGMTString();
    // 获取当前可访问cookie中的所有name值
	var rs = document.cookie.match(new RegExp("([^ ;][^;]*)(?=(=[^;]*)(;|$))", "gi"));
    // 删除所有cookie
	for (var i in rs) {
		document.cookie = rs[i] + "=;expires=" + expStr;
	}
};
///////////////////Cookie End ////////////////////////////////////

