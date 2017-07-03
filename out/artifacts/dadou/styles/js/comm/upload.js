
var uploadUrl = "/cms/modules/upload/uploadImg.jsp?r="+Math.random();
/**
 * 上传图片等资源,被ckeditor-->image.js中方法调用
 */
function addUploadImage(txtUrlId) {
	var OPEN_WINDOW_STATE = "dialogWidth=480px;dialogHeight=120px;scroll=yes;resizable=yes;status=no;center=yes";
	//显示窗口
	var imgPath = window.showModalDialog(uploadUrl, window, OPEN_WINDOW_STATE);
	//在upload结束后通过js代码window.returnValue=...可以将图片url返回给imgUrl变量。
	var urlObj = document.getElementById(txtUrlId);
	//触发url文本框的onchange事件，以便预览图片,没起到效果
	urlObj.value = imgPath;
	//IE浏览器
	/*
	if($.browser.msie){
	   urlObj.onpropertychange = function(){
	     alert(11);
	   }
	}else{
	   urlObj.addEventListener("input",function(){alert(333)},false); 
	}*/
}
/**
 * 设定图片类型
 */
 function setImgType(type){
   uploadUrl = uploadUrl + "&type="+type;
 }
/**
 * 获取文件名称
 */
function getFileName(filePathName) {
	var pos = filePathName.lastIndexOf("\\");
	if (pos != -1) {
		return filePathName.substring(pos + 1, filePathName.length);
	}
	pos = filePathName.lastIndexOf("/");
	if (pos != -1) {
		return filePathName.substring(pos + 1, filePathName.length);
	} else {
		return filePathName;
	}
}
/**
 * 取得文件扩展名
 */
function getFileExtName(fileName) {
	var indexOfDot = fileName.lastIndexOf(".");
	if (indexOfDot == -1) {
		return "";
	}
	return fileName.substr(indexOfDot + 1, fileName.length - indexOfDot - 1);
}	
//检查文件扩展名是否在允许上传范围内
function notAllowed(fileExtName) {
	fileExtName = fileExtName.toLowerCase();
	var exts = "jpg,jpeg,png,bmp,gif".split(",");
	for (var i = 0; i < exts.length; i++) {
		if (fileExtName == exts[i]) {
			return true;
		}
	}
	return false;
}

