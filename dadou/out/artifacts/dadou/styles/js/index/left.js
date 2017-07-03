$(function() {
	var sys_id = $("#hidden_sysId").val();
	var west_tree_url = config.ctx+'/login/leftMenu?sys_id='+sys_id;
	
	//取cookie
	var COOKIE_TREE_E = "ffk_io8d234_s345";
	var cookie_vals = Cookie.getCookie(COOKIE_TREE_E);
	/**
	 * 递归调用,用于保留展开或折叠的状态
	 */
	function changJson(json) {
		for(var i=0; i<json.length; i++) {
			var ele = json[i];
			
			if($.isArray(ele.children) && ele.children.length>0) {
				if(cookie_vals==undefined) {
					cookie_vals = "";
				}
				//demo单个: -24-
				//demo多个: -24--10007--106--44--117-
				//直接替换--为-，便于分割
				cookie_vals.replace(eval("/--/g"),"-");
				var array = cookie_vals.split("-");
			
				//如果在cookie中，则是要展开的
				if($.inArray((ele.id+""),array) == -1) {
					ele.state = "closed";
				} else {
					ele.state = "open";
				}
				changJson(ele.children)
			}
		}
	}
	
	$('#left_tree').tree({
	    url: west_tree_url,
	    loadFilter: function(data){
	        if (data.root_node){
	        	var json = eval(data.root_node);
	        	//更改json的配置state属性
	        	changJson(json);
	        	return json;
	        } else {
	            return null;
	        }
	    },
		onClick:function(node) {
			if (node.attributes && node.attributes.url) {
				var url;
				if (node.attributes.url.indexOf('/') == 0) {/*如果url第一位字符是"/"，那么代表打开的是本地的资源*/
					url = config.ctx + node.attributes.url;
				    /**遮罩处理
				    parent.$.messager.progress({
							title : '提示',
							text : '数据处理中，请稍后....'
				    });*/
				} else {
				     /*打开跨域资源*/
					url = node.attributes.url;
				}
				addTab({
					url : url,
					title : node.text,
					iconCls : node.iconCls
				});
			} else {
				$('#left_tree').tree("toggle",node.target);
			}
		},
		onBeforeLoad : function(node, param) {
			if (west_tree_url) {//只有刷新页面才会执行这个方法
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
			}
		},
		onLoadSuccess : function(node, data) {
			parent.$.messager.progress('close');
		},
		onExpand:function(node) {
			//转为cookie中存的形式
			var _id = "-"+node.id+"-";
			//cookie中值
			var val = Cookie.getCookie(COOKIE_TREE_E);
			//先替换一下，防止重复添加
			if(val != null && val!=undefined && $.trim(val)!="") {
				val = val.replace(eval("/"+_id+"/g"),"");
			} else {
				val = "";
			}
			//追加到cookie
			val += _id;
			Cookie.setCookie(COOKIE_TREE_E,val);
		},
		onCollapse:function(node) {
			var val = Cookie.getCookie(COOKIE_TREE_E);
			if(val != null && val!=undefined && $.trim(val)!="") {
				val = val.replace(eval("/-"+node.id+"-/g"),"");
			} else {
				val ="";
			}
			Cookie.setCookie(COOKIE_TREE_E,val);
		}
	});
	
})