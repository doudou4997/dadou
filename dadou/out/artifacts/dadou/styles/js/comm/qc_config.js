/**
 * 质量模块通用参数配置
 * 李凡荣 2016-11-02
 */
/*
 检验类型guid
 */

/*****************************************************************************/
//数据类型
/** ************************************************************************** */
var CONST_DATA_TYPE = [ {
	Code : "Number",
	Value : "数字"
}, {
	Code : "Text",
	Value : "文本"
}, {
	Code : "Date",
	Value : "日期"
} ];

function format_data_type(val, row) {
	for (var i = 0; i < CONST_DATA_TYPE.length; i++) {
		if (CONST_DATA_TYPE[i].Code == val) {
			return CONST_DATA_TYPE[i].Value;
		}
	}
	return val;
}
var CONST_DATA_ISALLOW = [ {
	Code : "1",
	Value : "同意"
}, {
	Code : "0",
	Value : "不同意"
}];
function format_data_isallow(val, row) {
	for (var i = 0; i < CONST_DATA_ISALLOW.length; i++) {
		if (CONST_DATA_ISALLOW[i].Code == val) {
			return CONST_DATA_ISALLOW[i].Value;
		}
	}
	return val;
}
var CONST_PRINT_TYPE = [ {
	Code : "doc",
	Value : "word"
}, {
	Code : "xls",
	Value : "excel"
}];
function format_print_type(val, row) {
	for (var i = 0; i < CONST_PRINT_TYPE.length; i++) {
		if (CONST_PRINT_TYPE[i].Code == val) {
			return CONST_PRINT_TYPE[i].Value;
		}
	}
	return val;
}
/*
 * 打印模板类型："01":装箱清单  "02":测试报告单  "03":出厂检验单
 */
var CONST_PRINTMODEL_TYPE = [ {
	Code : "01",
	Value : "装箱清单"
}, {
	Code : "02",
	Value : "测试报告单"
}, {
	Code : "03",
	Value : "出厂检验单"
}];
function format_printModel_type(val, row) {
	for (var i = 0; i < CONST_PRINTMODEL_TYPE.length; i++) {
		if (CONST_PRINTMODEL_TYPE[i].Code == val) {
			return CONST_PRINTMODEL_TYPE[i].Value;
		}
	}
	return val;
}
/*
 * 电控：02加严检验，01正常检验
 */
var CONST_CHECK_TYPE = [ {
	Code : "01",
	Value : "正常检验"
}, {
	Code : "02",
	Value : "加严检验"
}];
/**
 * 机械：08基于项目供应商维度，09基于供应商单次到货
 */
var CONST_CHECK_TYPE_JX= [ {
	Code : "08",
	Value : "基于项目供应商维度"
}, {
	Code : "09",
	Value : "基于供应商单次到货"
}];

var CONST_CHECK_TYPE_ALL= [ {
	Code : "01",
	Value : "正常检验"
}, {
	Code : "02",
	Value : "加严检验"
},{
	
	Code : "08",
	Value : "基于项目供应商维度"
}, {
	Code : "09",
	Value : "基于供应商单次到货"
}];



function format_check_type(val, row) {
	for (var i = 0; i < CONST_CHECK_TYPE.length; i++) {
		if (CONST_CHECK_TYPE[i].Code == val) {
			return CONST_CHECK_TYPE[i].Value;
		}
	}
	for (var i = 0; i < CONST_CHECK_TYPE_JX.length; i++) {
		if (CONST_CHECK_TYPE_JX[i].Code == val) {
			return CONST_CHECK_TYPE_JX[i].Value;
		}
	}
	return val;
}
/**
 * 08：项目供应商维度【01正常，04先入库后检验，05仅生成一个样】
 */
var CONST_CHECK_TYPE_CHILD_EIGHTH= [ {
	Code : "01",
	Value : "正常"
},  {
	Code : "04",
	Value : "先入库后检验"
}];
/**
 * 09：供应商单次到货【11标准件M12，12弹性垫圈，13电缆网套，14油脂类、油类、漆类，15滚动轴承，16液压连接弯管，17橡胶件】
 */
var CONST_CHECK_TYPE_CHILD_NINE= [{
	Code : "02",
	Value : "紧固件M16"
}, {
	Code : "03",
	Value : "单机台机组用量磁钢"
},  {
	Code : "05",
	Value : "按照检验比例检验，生成一个检验样本"
},{
	Code : "11",
	Value : "标准件M12"
}, {
	Code : "12",
	Value : "紧固件M16"
}, {
	Code : "13",
	Value : "单机台机组用量磁钢"
}, {
	Code : "14",
	Value : "油脂类、油类、漆类"
}, {
	Code : "15",
	Value : "滚动轴承"
}, {
	Code : "16",
	Value : "液压连接弯管"
}, {
	Code : "17",
	Value : "橡胶件"
}];

/*var CONST_CHECK_TYPE_CHILD_ALL= [ {
	Code : "11",
	Value : "标准件M12"
}, {
	Code : "12",
	Value : "紧固件M16"
}, {
	Code : "13",
	Value : "单机台机组用量磁钢"
}, {
	Code : "14",
	Value : "油脂类、油类、漆类"
}, {
	Code : "15",
	Value : "滚动轴承"
}, {
	Code : "16",
	Value : "液压连接弯管"
}, {
	Code : "17",
	Value : "橡胶件"
}];
*/

var CONST_CHECK_SAMPLESTATUS = [ {
	Code : "0",
	Value : "初始待检"
}, {
	Code : "1",
	Value : "已检测"
}, {
	Code : "2",
	Value : "已提交"
} , {
	Code : "3",
	Value : "已删除"
}, {
	Code : "4",
	Value : "已退回"
}, {
	Code : "5",
	Value : "退回已检测"
}, {
	Code : "6",
	Value : "待处理"
}];

function format_check_samplestatus(val, row) {
	for (var i = 0; i < CONST_CHECK_SAMPLESTATUS.length; i++) {
		if (CONST_CHECK_SAMPLESTATUS[i].Code == val) {
			return CONST_CHECK_SAMPLESTATUS[i].Value;
		}
	}
	return val;
}

var CONST_CHECK_PRIORITY = [ {
	Code : "01",
	Value : "高"
}, {
	Code : "02",
	Value : "正常"
}, {
	Code : "03",
	Value : "低"
} ];
function format_check_priority(val, row) {
	for (var i = 0; i < CONST_CHECK_PRIORITY.length; i++) {
		if (CONST_CHECK_PRIORITY[i].Code == val) {
			return CONST_CHECK_PRIORITY[i].Value;
		}
	}
	return val;
}

/*
 * 零部件类型
 */
var CONST_DEPARTTYPE = [ {
	Code : "01",
	Value : "关键件"
}, {
	Code : "02",
	Value : "一般件"
}, {
	Code : "04",
	Value : "生产工序"
}, {
	Code : "05",
	Value : "检验工序"
}, {
	Code : "03",
	Value : "其它"
}, {
	Code : "06",
	Value : "主要件"
}, {
	Code : "07",
	Value : "标准件"
}

];

function format_depart_type(val, row) {
	for (var i = 0; i < CONST_DEPARTTYPE.length; i++) {
		if (CONST_DEPARTTYPE[i].Code == val) {
			return CONST_DEPARTTYPE[i].Value;
		}
	}
	return val;
}
/*
 * 判断方式
 */
var CONST_JUDGESTYLE_TYPE = [ {
	Code : "01",
	Value : "上下限比较"
}, {
	Code : "02",
	Value : "文字判断"
}, {
	Code : "03",
	Value : "不判断"
} ];

function format_judgestyle_type(val, row) {
	for (var i = 0; i < CONST_JUDGESTYLE_TYPE.length; i++) {
		if (CONST_JUDGESTYLE_TYPE[i].Code == val) {
			return CONST_JUDGESTYLE_TYPE[i].Value;
		}
	}
	return val;
}
/* 结论 */
var CONST_RESULT_EVAL = [ {
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
},];


function format_result_type(val, row) {
	for (var i = 0; i < CONST_RESULT_EVAL.length; i++) {
		if (CONST_RESULT_EVAL[i].Code == val) {
			return CONST_RESULT_EVAL[i].Value;
		}
	}
	return val;
};
/**
 * 优先级处理状态
 */
var CONST_STATUS = [ {
	"Code" : "0",
	"Value" : "未处理"
}, {
	"Code" : "1",
	"Value" : "已处理"
}
];
function format_status(val, row) {
	for (var i = 0; i < CONST_STATUS.length; i++) {
		if (CONST_STATUS[i].Code == val) {
			return CONST_STATUS[i].Value;
		}
	}
	return val;
};

/*
 * 处理状态
 */
var CONST_DEALSTATE = [ {
	"Code" : "0",
	"Value" : "未处理"
}, {
	"Code" : "1",
	"Value" : "已确认故障"
} ,{
	"Code" : "2",
	"Value" : "已确认原因"
},{
	"Code" : "3",
	"Value" : "已处理"
}
];

function format_dealstate(val, row) {
	for (var i = 0; i < CONST_DEALSTATE.length; i++) {
		if (CONST_DEALSTATE[i].Code == val) {
			return CONST_DEALSTATE[i].Value;
		}
	}
	return val;
};

/*
 * 缺陷类型
 */
var CONST_LOWTYPE = [ {
	"Code" : "GWF-",
	"Value" : "故障"
}, {
	"Code" : "GWR-",
	"Value" : "原因"
} ,{
	"Code" : "GWD-",
	"Value" : "责任"
}];

function format_lowtype(val, row) {
	for (var i = 0; i < CONST_LOWTYPE.length; i++) {
		if (CONST_LOWTYPE[i].Code == val) {
			return CONST_LOWTYPE[i].Value;
		}
	}
	return val;
};

/*
 * 缺陷等级类型
 */
var CONST_LOWLEVELTYPE = [ {
	"Code" : "01",
	"Value" : "主类"
}, {
	"Code" : "02",
	"Value" : "一级子类"
}, {
	"Code" : "03",
	"Value" : "二级子类"
}, {
	"Code" : "04",
	"Value" : "三级子类"
} ];

function format_lowleveltype(val, row) {
	for (var i = 0; i < CONST_LOWLEVELTYPE.length; i++) {
		if (CONST_LOWLEVELTYPE[i].Code == val) {
			return CONST_LOWLEVELTYPE[i].Value;
		}
	}
	return val;
};

/*
 * 事务处理结果
 */
var CONST_TRANSICATIONRESULT = [ {
	"Code" : "01",
	"Value" : "让步接收"
}, {
	"Code" : "02",
	"Value" : "退货"
}, {
	"Code" : "03",
	"Value" : "返厂"
}, {
	"Code" : "04",
	"Value" : "返修"
}, {
	"Code" : "05",
	"Value" : "换货"
}, {
	"Code" : "06",
	"Value" : "挑选入库"
}, {
	"Code" : "07",
	"Value" : "待处理"
}

];

function format_transicationresult(val, row) {
	for (var i = 0; i < CONST_TRANSICATIONRESULT.length; i++) {
		if (CONST_TRANSICATIONRESULT[i].Code == val) {
			return CONST_TRANSICATIONRESULT[i].Value;
		}
	}
	return val;
};

/*
 * 比较方式
 */
var CONST_COMPARE_TYPE = [ {
	Code : "01",
	Value : "大于"
}, {
	Code : "02",
	Value : "大于等于"
}, {
	Code : "03",
	Value : "小于"
}, {
	Code : "04",
	Value : "小于等于"
}, {
	Code : "05",
	Value : "闭区间"
}, {
	Code : "06",
	Value : "开区间"
}, {
	Code : "07",
	Value : "左开右闭"
}, {
	Code : "08",
	Value : "左闭右开"
}, {
	Code : "09",
	Value : "文字判断"
}, {
	Code : "10",
	Value : "特殊比较"
} ];

function format_compare_type(val, row) {
	for (var i = 0; i < CONST_COMPARE_TYPE.length; i++) {
		if (CONST_COMPARE_TYPE[i].Code == val) {
			return CONST_COMPARE_TYPE[i].Value;
		}
	}
	return val;
}
/* guocheng */
var CONST_CHECKTYPE = [ {
	Code : "01",
	Value : "入厂检"
}, {
	Code : "02",
	Value : "过程检"
}, {
	Code : "03",
	Value : "工艺巡检"
}, {
	Code : "04",
	Value : "下线检"
} ,{
	Code : "05",
	Value : "通电测试"
},{
	Code : "06",
	Value : "终检"
}];

function format_checktype(val, row) {
	for (var i = 0; i < CONST_CHECKTYPE.length; i++) {
		if (CONST_CHECKTYPE[i].Code == val) {
			return CONST_CHECKTYPE[i].Value;
		}
	}
	return val;
}

/*
 * 检验次数
 */
var CONST_CHECKNUM = [ {
	Code : "01",
	Value : "单次检测"
}, {
	Code : "02",
	Value : "多次检测"
}, {
	Code : "03",
	Value : "子项检测"
} ];

var CONST_CHECKDEPTTYPE = [ {
	Code : "01",
	Value : "入厂检"
}, {
	Code : "02",
	Value : "过程检"
}, {
	Code : "03",
	Value : "巡检"
}, {
	Code : "04",
	Value : "终检"
} ];

function format_checknum(val, row) {
	for (var i = 0; i < CONST_CHECKNUM.length; i++) {
		if (CONST_CHECKNUM[i].Code == val) {
			return CONST_CHECKNUM[i].Value;
		}
	}
	return val;
}

/*
 * 缺陷等级代码
 */
var CONST_FAULT_LEVEL = [ {
	Code : "A",
	Value : "A"
}, {
	Code : "B",
	Value : "B"
}, {
	Code : "C",
	Value : "C"
} , {
	Code : "04",
	Value : "不合格"
} ];

function format_fault_level(val, row) {
	for (var i = 0; i < CONST_FAULT_LEVEL.length; i++) {
		if (CONST_FAULT_LEVEL[i].Code == val) {
			return CONST_FAULT_LEVEL[i].Value;
		}
	}
	return val;
}

/*
 * 检测项等级代码
 */
var CONST_CHECK_ITEM_LEVEL = [ {
	Code : "A",
	Value : "A"
}, {
	Code : "B",
	Value : "B"
}, {
	Code : "C",
	Value : "C"
}, {
	Code : "04",
	Value : "关键"
}, {
	Code : "05",
	Value : "一般"
} ];

function format_check_item_level(val, row) {
	for (var i = 0; i < CONST_CHECK_ITEM_LEVEL.length; i++) {
		if (CONST_CHECK_ITEM_LEVEL[i].Code == val) {
			return CONST_CHECK_ITEM_LEVEL[i].Value;
		}
	}
	return val;
}

/*
 * 项目类型
 */
var CONST_ITEMTYPE = [ {
	Code : "01",
	Value : "主项"
}, {
	Code : "02",
	Value : "子项"
} ];

function format_itemtype(val, row) {
	for (var i = 0; i < CONST_ITEMTYPE.length; i++) {
		if (CONST_ITEMTYPE[i].Code == val) {
			return CONST_ITEMTYPE[i].Value;
		}
	}
	return val;
}

/*
 * 工厂类型
 */
var CONST_FACTORYTYPE = [ {
	Code : "01",
	Value : "机械"
}, {
	Code : "02",
	Value : "电控"
} ];

function format_factorytype(val, row) {
	for (var i = 0; i < CONST_FACTORYTYPE.length; i++) {
		if (CONST_FACTORYTYPE[i].Code == val) {
			return CONST_FACTORYTYPE[i].Value;
		}
	}
	return val;
}

/*
 * 信息字段类型
 */
var CONST_INFOMATIONTYPE = [ {
	Code : "file",
	Value : "文件字段"
}, {
	Code : "info",
	Value : "信息字段"
} ];

function format_informationtype(val, row) {
	for (var i = 0; i < CONST_INFOMATIONTYPE.length; i++) {
		if (CONST_INFOMATIONTYPE[i].Code == val) {
			return CONST_INFOMATIONTYPE[i].Value;
		}
	}
	return val;
}
/*
 * 代码字段类型
 */
var CONST_CODETYPE = [ {
	Code : "9901",
	Value : "物料代码"
}, {
	Code : "9902",
	Value : "工序代码"
}, {
	Code : "9903",
	Value : "其它"
} ];

function format_codetype(val, row) {
	for (var i = 0; i < CONST_CODETYPE.length; i++) {
		if (CONST_CODETYPE[i].Code == val) {
			return CONST_CODETYPE[i].Value;
		}
	}
	return val;
}

/*
 * 自动绑定相应字段至select控件上
 */
function BuildSelecthtml(objid, data, result, allowEmpty) {

	if (allowEmpty)
		$("#" + objid).append(
				"<option value=\"\" selected=\"selected\">...请选择...</option>");

	var iLen = data.length;
	for (var i = 0; i < iLen; i++) {
		if (result == data[i].Code) {
			$("#" + objid).append(
					"<option value=\"" + data[i].Code
							+ "\" selected=\"selected\">" + data[i].Value
							+ "</option>");
		} else
			$("#" + objid).append(
					"<option value=\"" + data[i].Code + "\">" + data[i].Value
							+ "</option>");

	}
}

function GetQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

function S4() {
	return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
}
/*
 * 生成GUID
 */
function guid() {
	return (S4() + S4() + "-" + S4() + "-" + S4() + "-" + S4() + "-" + S4()
			+ S4() + S4());
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
$.extend($.fn.validatebox.defaults.rules, {
	selectRequired : {
		validator : function(value, param) {
			var selectVal = $("input[name=" + param[0] + "]").val();
			$("#msg").html(selectVal);
			return value != "--请选择--";
		},
		message : '请选择',
	}
});
