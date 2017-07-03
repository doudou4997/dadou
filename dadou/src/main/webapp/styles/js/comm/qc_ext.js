var CONST_RESULT_EVAL = [ 
{
	"Code" : "0",
	"Value" : "合格"
}, {
	"Code" : "1",
	"Value" : "不合格"
}, {
	"Code" : "2",
	"Value" : "未检测"
}, {
	"Code" : "3",
	"Value" : "详见检测结论"
}];


var Codes = new Object();

function format_result_eval(val, row) {  
	for (var i = 0; i < CONST_RESULT_EVAL.length; i++) {
		if (CONST_RESULT_EVAL[i].Code == val) {
			return CONST_RESULT_EVAL[i].Value;
		}
	}
	return val;
}

function format_result_eval1(val, row) { 
	if(val == undefined || val==null || val=="" )
		return "未检测";
	for (var i = 0; i < CONST_RESULT_EVAL.length; i++) {
		if (CONST_RESULT_EVAL[i].Code == val) {
			return CONST_RESULT_EVAL[i].Value;
		}
	}
	return val;
}

function setComboBox(url, objectid, varr, req, mode, value) {
	if (!Codes[varr]) {
		callAjax(url, {
			mode : mode,
			codeGroup : varr,
		}, function(data, context) {
			Codes[context.varr] =  new Array();
			Codes[context.varr] = Codes[context.varr].concat(data);
			setComboBox(context.url, context.objectid, context.varr, context.req, context.mode,
					context.value);
		}, function() {

		}, {
			url : url,
			objectid : objectid,
			varr : varr,
			req : req,
			mode : mode,
			value : value
		});
	} else {
		var tmp = new Array();
		if (mode == 0) {
			tmp = [{
				CODE : "",
				CNVALUE : "--请选择--"
			}].concat(Codes[varr]);
		} else {
			tmp = [{
				CODE : "",
				CNVALUE : "--全部--"
			}].concat(Codes[varr]);
		}
		
		
		if (!req) {
			
			$('#' + objectid).combobox({
				panelHeight : 'auto',
				valueField : 'CODE',
				textField : 'CNVALUE',
				editable : false,
				data : tmp,
			});

		} else {
			$('#' + objectid).combobox({
				panelHeight : 'auto',
				valueField : 'CODE',
				textField : 'CNVALUE',
				editable : false,
				data : tmp,
				required : true,
				validtype : "selectRequired['" + objectid + '"]',
			});
		}

		if (value) {
			$('#' + objectid).combobox("setValue", value);
		}
	}
}

/**
 * easyui combobox 校验
 * 
 * <input id="ITEMTYPE" name="ITEMTYPE" size="20" required="true"
 * validtype="selectRequired['ITEMTYPE']" value="${obj.ITEMTYPE}" maxlength="20"
 * style="font-size: 13px" />
 * 
 * $(function() { $('#ITEMTYPE').combobox({ required : true, validType : [
 * "comboVry", "..." ] }); });
 */

Date.prototype.format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)

		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};

function errorFun(info, er) {
	alert("系统产生错误,请联系管理员!");
}

function succFun(data) {
	$.messager.alert('', data.msg, "info");
}

function formatDate(value, row, index) {
	var d = new Date(Date.parse(value));
	if (d == "Invalid Date") {
		return "";
	}
	return d.format("yyyy-MM-dd");
}

function formatDateTime(value, row, index) {
	var d = new Date(Date.parse(value));
	if (d == "Invalid Date") {
		return "";
	}
	return d.format("yyyy-MM-dd hh:mm:ss");
}

function checkvalue(value, low, up, target, style, compare) {

	// 如果没有输入任何信息,返回未检测
	if (value == "") {
		return "2";
	}
	
	if(value.toLowerCase() == "ok"){
		return "0";
	}else if(value.toLowerCase() == "ng"){
		return "1";
	}else if(value.toLowerCase() == "符合"){
		return "0";
	}else if(value.toLowerCase() == "不符合"){
		return "1";
	}
	var result = "";
	/*
	 * 0 合格 1 不合格 2 未检测 3 见检测结论
	 * 
	 */
	var boolIsGood = true;
	switch (style) {
	case "01": // 上下限比较
		switch (compare) {
		case "01":// 大于等于
			boolIsGood = value >= low;
			break;
		case "02":// 大于
			boolIsGood = value > low;
			break;
		case "03":// 小于等于
			boolIsGood = value <= up;
			break;
		case "04":// 小于
			boolIsGood = value <= up;
			break;
		case "05":// 闭区间
			boolIsGood = (value <= up) && (value >= low);
			break;
		case "06":// 开区间
			boolIsGood = (value < up) && (value > low);
			break;
		case "07":// 左开又闭
			boolIsGood = (value <= up) && (value > low);
			break;
		case "08":// 左闭右开
			boolIsGood = (value < up) && (value >= low);
			break;
		case "09":// 特殊比较
			boolIsGood = true;
			break;
		default:
			boolIsGood = true;
			break;
		}
		break;
	case "02":// 文字判断
		boolIsGood = value == "1";
		break;
	case "03":// 不判断
		boolIsGood = true;
		break;
	default:
		boolIsGood = true;
		break;
	}
	if (boolIsGood) {
		return "0";
	} else {
		return "1";
	}
	return "3";
}
// 单元格编辑扩展
$.extend($.fn.datagrid.methods, {
	editCell : function(jq, param) {
		return jq.each(function() {
			var opts = $(this).datagrid('options');
			var fields = $(this).datagrid('getColumnFields', true).concat(
					$(this).datagrid('getColumnFields'));
			for (var i = 0; i < fields.length; i++) {
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor1 = col.editor;
				if (fields[i] != param.field) {
					col.editor = null;
				}
			}
			$(this).datagrid('beginEdit', param.index);
			for (var i = 0; i < fields.length; i++) {
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor = col.editor1;
			}
		});
	}
});

function callAjax(url, data, succ, error, context) {
    $.messager.progress({title:"提示", msg:"数据加载中..."});
    $.ajax({
		url : url,
		type : 'POST',
		dataType : 'json',
		data : data,
		context : context,
		success : function(data) {
			$.messager.progress("close");
			if (succ) {
				succ(data, this);
			} else {
				$.messager.alert('', data.msg, "info");
			}
		},
		error : function(info, er) {
			$.messager.progress("close");
			if (error) {
				error(info, er);
			} else {
				alert("系统产生错误,请联系管理员!");
			}
		}
	});
}