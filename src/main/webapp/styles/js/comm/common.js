/****
 * 判断数字
 * @param input
 * @returns {Boolean}
 */
function checkNumber(input) {
	var re = /^(-|\+)?\d+(\.\d+)?$/;
	if (re.test(input)) {
		return true;
	}
	return false;
}
/*
 * 1.把加载回来的数据以innerHTML的方式放入到一个动态生成的Div中；
 * 2.创建一个文档片段，把Div中的子元素都AppendChild到片段中；
 * 3.选出文档片段中的Script节点；
 * 4.把文档片段中的非Script节点添加到页面；
 * 5.如果此节点存在src属性，那就再一次请求此src，并取回内容；如果此节点不存在src属性， 那就把此节点的文本内容取回；
 * 6.取出DOM中的Head节点，再创建一个Script节点，设置节点Type属性为Text/javascript类型；
 * 7.把第5步中取出的内容，添加到第6步创建的Script节点中，并把此节点插入为Head节点的第一个子节点；
 * 8.删除Head节点中在第7步插入的Script节点;
 */

/** 
 * 添加innerHTML到节点中 
 * @param elem 节点 
 * @param html HTMLCode 
 */
function setInnerHtml(elem, html, doc) {
	/* 生成一个动态 */
	var dynDiv = doc.createElement('div');
	/* 把内容都添加到此div中 , 因为如果第一个节点为script时ie会忽略此节点，所以就要上面加一个新节点 */
	dynDiv.innerHTML = '<span style="display:none;">for ie</span>' + html;
	/* 取出动态div中的script节点 */
	var scripts = dynDiv.getElementsByTagName('script');
	/* 取出head节点，再新生成的节点添加到head中 */
	var head = doc.getElementsByTagName('head')[0];
	/* 把script中的脚本或要引入的外部 脚本 */
	for (var i = 0; i < scripts.length; i++) {
		var jsCode = '';
		/* 如果为外部脚本，就再去加载数据 */
		if (scripts[i].src) {
			$.ajax({
				url : scripts[i].src,
				type : 'get',
				success : function(respon) {
					jsCode = respon.responseText;
					evalJs(jsCode);
				}
			});
			/* 如果只是内部脚本，就取出程序代码 */
		} else {
			jsCode = scripts[i].innerText || scripts[i].textContent
					|| scripts[i].text || '';
			evalJs(jsCode);
		}
	}
	function evalJs(jsCode) {
		/* 新建一个script节点 */
		var scpt = doc.createElement('script');
		scpt.type = 'text/javascript';
		scpt.text = jsCode;
		head.insertBefore(scpt, head.firstChild);
		head.removeChild(scpt);
	}
	/* 删除内容中的script节点 */
	for (var i = 0; i < scripts.length; i++) {
		if (scripts[0].parentNode) {
			scripts[0].parentNode.removeChild(scripts[0]);
			i--;
		}
	}
	$(elem).html(dynDiv.innerHTML);
}

/**
 * 格式化时间，方便辨认
 * @param time
 * @returns {String}
 */
function formateTime(time) {
	var sec = time % 60;
	var min = 0;
	if (sec > 0) {
		min = (time - sec) / 60;
	} else {
		min = time / 60;
	}
	var timeStr = "";
	if (min < 10)
		timeStr += "0" + min + "分";
	if (min >= 10)
		timeStr += "" + min + "分";
	if (sec < 10)
		timeStr += "0" + sec + "秒";
	if (sec >= 10)
		timeStr += sec + "秒";
	return timeStr;
}
/********************
 * 取窗口滚动条高度
 ******************/
function getScrollTop() {
	var scrollTop = 0;
	if (document.documentElement && document.documentElement.scrollTop) {
		scrollTop = document.documentElement.scrollTop;
	} else if (document.body) {
		scrollTop = document.body.scrollTop;
	}
	return scrollTop;
}

/********************
 * 取窗口可视范围的高度
 *******************/
function getClientHeight() {
	var clientHeight = 0;
	if (document.body.clientHeight && document.documentElement.clientHeight) {
		var clientHeight = (document.body.clientHeight < document.documentElement.clientHeight) ? document.body.clientHeight
				: document.documentElement.clientHeight;
	} else {
		var clientHeight = (document.body.clientHeight > document.documentElement.clientHeight) ? document.body.clientHeight
				: document.documentElement.clientHeight;
	}
	return clientHeight;
}

/********************
 * 取文档内容实际高度
 *******************/
function getScrollHeight() {
	return Math.max(document.body.scrollHeight,
			document.documentElement.scrollHeight);
}
/** 
 *在点击排序字段时，改变传入后台的字段 
 *param对应onBeforeLoad事件的参数 
 *map自定义的字段映射Map 
 */  
function onSortColumn(param,map) {
    //取出map中字段的映射关系值  
    var fieldSort=map[param.sort];  
    if(fieldSort!='' && fieldSort!=undefined){  
        //设置新的排序字段名，设置完之后，发送请求时一并会发送到服务端  
        param.sort=fieldSort;  
    }  
}
/**
 * easyui-datetimebox日期格式转换方法
 */
function dateFormatter(date){  
    var y = date.getFullYear();  
    var m = date.getMonth()+1;  
    var d = date.getDate();  
    var h = date.getHours();  
    return  y+'年'+(m<10?('0'+m):m)+'月'+(d<10?('0'+d):d)+'日'+(h<10?('0'+h):h)+'点';  
      
}  
function dateParser(s){  
    var reg=/[\u4e00-\u9fa5]/  //利用正则表达式分隔  
    var ss = (s.split(reg));  
    var y = parseInt(ss[0],10);  
    var m = parseInt(ss[1],10);  
    var d = parseInt(ss[2],10);  
    var h = parseInt(ss[3],10);  
    if (!isNaN(y) && !isNaN(m) && !isNaN(d) && !isNaN(h)){  
        return new Date(y,m-1,d,h);  
    } else {  
        return new Date();  
    }
}
