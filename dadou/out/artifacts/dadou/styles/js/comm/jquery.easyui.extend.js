
//提示框按钮文字
$.extend($.messager.defaults,{
	ok:"确定",
	cancel:"取消"
});
//增加、编辑角色使用
$.extend($.fn.validatebox.defaults.rules, {
    sysBlong: {
        validator: function (value, param) {
        	return (value!="-1");
        },
        message: '必选'
    }
});

//数字校验
$.extend($.fn.validatebox.defaults.rules, {
    number: {
        validator: function (value, param) {
        	return $.isNumeric(value);
        },
        message: '请输入数字'
    }
});

//默认提示
$.extend($.fn.panel.defaults, {
	loadingMessage : '正在载入....'
});
$.extend($.fn.datagrid.defaults, {
	loadMsg : '正在载入....'
});

//重写pagination的中文配置
$.extend($.fn.pagination.defaults, {
	
    beforePageText: '第',//页数文本框前显示的汉字  
    afterPageText: '页    共 {pages} 页',  
    displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
});

//继承自dialog，作用：移动dialog时，不能超出屏幕可视区域
$.extend($.fn.dialog.defaults, {
    onMove:function(left,top) {
    	var l = left;
		var t = top;
		if (l < 1) {
			l = 1;
		}
		if (t < 1) {
			t = 1;
		}
		var width = parseInt($(this).parent().css('width')) + 14;
		var height = parseInt($(this).parent().css('height')) + 14;
		var right = l + width;
		var buttom = t + height;
		var browserWidth = $(window).width();
		var browserHeight = $(window).height();
		if (right > browserWidth) {
			l = browserWidth - width;
		}
		if (buttom > browserHeight) {
			t = browserHeight - height;
		}
		
		//onMove内部不能使用$(selector).dialog("move"...)方法，某些情况会引起异常
		$(this).parent().css({/* 修正面板位置 */
			left : l,
			top : t
		});
	    
    }
});

/**
* 获取datagrid选中的id
*/
function getDatagridChecked(selector) {
	if(selector == undefined) {
		selector = "#data-table";
	}
	var rows = $(selector).datagrid('getChecked'); 
	if(rows.length == 0) {
		return null;
	} else {
  	   return rows[0].id;
	}
	
}
/**
* 获取datagrid选中的根据MAP转换的ID
*/
function getDatagridCheckedByID(selector) {
	if(selector == undefined) {
		selector = "#data-table";
	}
	var rows = $(selector).datagrid('getChecked'); 
	if(rows.length == 0) {
		return null;
	} else {
  	return rows[0].ID;
	}
	
}
/**
* 获取datagrid选中的id 多个
*/
function getDatagridCheckeds(selector) {
	if(selector == undefined) {
		selector = "#data-table";
	}
	var rows = $(selector).datagrid('getChecked'); 
	if(rows.length == 0) {
		return null;
	} else {
		var result = new Array();
		for(var i=0; i<rows.length; i++) {
			result.push(rows[i].id);
		}
		
  		return result.join(";");
	}
	
}
function getDatagridOnlyChecked(selector) {
	if(selector == undefined) {
		selector = "#data-table";
	}
	var rows = $(selector).datagrid('getChecked'); 
	if(rows.length == 0) {
		return null;
	} else if(rows.length > 1) {
		return null;
	} else {
		var result = new Array();
		for(var i=0; i<rows.length; i++) {
			result.push(rows[i].id);
		}
		
  		return result.join(";");
	}
	
}
function getDatagridCheckedArray(selector) {
	if(selector == undefined) {
		selector = "#data-table";
	}
	var rows = $(selector).datagrid('getChecked'); 
	if(rows.length == 0) {
		return null;
	} else {
		var result = new Array();
		for(var i=0; i<rows.length; i++) {
			result.push(rows[i].id);
		}
		
  		return result;
	}
	
}
/***
 * 返回数组的方式在方法体内处理
 * add by gaof 
 * @since 20161209
 * @param selector
 * @returns
 */
function getDatagridArray(selector) {
	if(selector == undefined) {
		selector = "#data-table";
	}
	var rows = $(selector).datagrid('getChecked'); 
	if(rows.length == 0) {
		return null;
	} else {
        return rows;
	}
	
}
/**
 * @requires jQuery,EasyUI
 * 
 * 为datagrid、treegrid增加表头菜单，用于显示或隐藏列
 * 注意：冻结列不在此菜单中
 */
var createGridHeaderContextMenu = function(e, field) {
	e.preventDefault();
	var grid = $(this);/* grid本身 */
	var headerContextMenu = this.headerContextMenu;/* grid上的列头菜单对象 */
	if (!headerContextMenu) {
		var tmenu = $('<div style="width:100px;"></div>').appendTo('body');
		var fields = grid.datagrid('getColumnFields');
		for ( var i = 0; i < fields.length; i++) {
			var fildOption = grid.datagrid('getColumnOption', fields[i]);
			if (!fildOption.hidden) {
				$('<div iconCls="tick" field="' + fields[i] + '"/>').html(fildOption.title).appendTo(tmenu);
			} else {
				$('<div iconCls="bullet_blue" field="' + fields[i] + '"/>').html(fildOption.title).appendTo(tmenu);
			}
		}
		headerContextMenu = this.headerContextMenu = tmenu.menu({
			onClick : function(item) {
				var field = $(item.target).attr('field');
				if (item.iconCls == 'tick') {
					grid.datagrid('hideColumn', field);
					$(this).menu('setIcon', {
						target : item.target,
						iconCls : 'bullet_blue'
					});
				} else {
					grid.datagrid('showColumn', field);
					$(this).menu('setIcon', {
						target : item.target,
						iconCls : 'tick'
					});
				}
			}
		});
	}
	headerContextMenu.menu('show', {
		left : e.pageX,
		top : e.pageY
	});
};
$.fn.datagrid.defaults.onHeaderContextMenu = createGridHeaderContextMenu;
$.fn.treegrid.defaults.onHeaderContextMenu = createGridHeaderContextMenu;
/**
 * grid tooltip参数
 * 实现特定的列的tip提示
 */
var gridTooltipOptions = {
	tooltip : function(jq, fields) {
		return jq.each(function() {
			var panel = $(this).datagrid('getPanel');
			if (fields && typeof fields == 'object' && fields.sort) {
				$.each(fields, function() {
					var field = this;
					bindEvent($('.datagrid-body td[field=' + field + '] .datagrid-cell', panel));
				});
			} else {
				bindEvent($(".datagrid-body .datagrid-cell", panel));
			}
		});

		function bindEvent(jqs) {
			jqs.mouseover(function() {
				var content = $(this).text();
				if (content.replace(/(^\s*)|(\s*$)/g, '').length > 5) {
					$(this).tooltip({
						content : content,
						trackMouse : true,
						position : 'bottom',
						onHide : function() {
							$(this).tooltip('destroy');
						},
						onUpdate : function(p) {
							var tip = $(this).tooltip('tip');
							if (parseInt(tip.css('width')) > 500) {
								tip.css('width', 500);
							}
						}
					}).tooltip('show');
				}
			});
		}
	}
};
/**
 * Datagrid扩展方法tooltip 基于Easyui 1.3.3，可用于Easyui1.3.3+
 * 
 * 简单实现，如需高级功能，可以自由修改
 * 
 * 使用说明:
 * 在easyui.min.js之后导入本js
 * 代码案例:
 * $("#dg").datagrid('tooltip'); 所有列
 * 
 * $("#dg").datagrid('tooltip',['productid','listprice']); 指定列
 */
$.extend($.fn.datagrid.methods, gridTooltipOptions);

/**
 * Treegrid扩展方法tooltip 基于Easyui 1.3.3，可用于Easyui1.3.3+
 * 
 * 简单实现，如需高级功能，可以自由修改
 * 使用说明:
 * 在easyui.min.js之后导入本js
 * 
 * 代码案例:
 * $("#dg").treegrid('tooltip'); 所有列
 * $("#dg").treegrid('tooltip',['productid','listprice']); 指定列
 * 
 */
$.extend($.fn.treegrid.methods, gridTooltipOptions);
