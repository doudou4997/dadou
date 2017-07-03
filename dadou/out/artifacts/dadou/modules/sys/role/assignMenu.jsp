<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@include file="/comm/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<jsp:include page="/comm/base.jsp" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>角色列表</title>
		<script type="text/javascript">
		/*******************************
		 *分配权限:菜单权限和操作权限
		 *******************************/
		function doAssign(){
		  $.messager.confirm('提示', '确定为当前角色分配权限吗?', function(r){
			 if(!r) {
				 return;
			 }
             var menuIds = new Array();
             var privilegeIds = new Array();
			 //获取角色的Id
			 var roleId = $("#roleId").val();
			 var url="${ctx}/role/doAssign?id="+roleId;
			 //选中的
		  	 var nodes = $('#menuTree').tree('getChecked');
		
		  	 //不明确的节点
		  	 var indeterminateNodes= $('#menuTree').tree('getChecked','indeterminate');
		  	 $.each(indeterminateNodes,function(index,node){
		  		nodes.push(node);
		  	 });
		  	 if(nodes.length == 0){
		  		$.messager.alert('提示','请选择节点！','info');
		  		return;
		  	 }
		  	 //组织节点
		  	 for(var i=0; i<nodes.length; i++) {
			  	 var temp = nodes[i];
			  	 if(temp.attributes.type == 1) {
				  	 //菜单列表
			  		 //pars += "&menuIds="+temp.id;
			  		menuIds.push(temp.id);
				 } else if(temp.attributes.type == 2) {
					 //权限列表
					 //pars += "&privilegeIds="+temp.id;
					privilegeIds.push(temp.id);
				 }
			 }
		  	 //console.log(nodes);
			 //Ajax
			 $.ajax({
				url:url,
				type:'POST',
				data:{"menuIds":menuIds,"privilegeIds":privilegeIds},
				dataType:'json',
				success: function(data){
					if(data.success == true) {
						$.messager.alert('提示','分配成功！',"info",function(){
							//关闭父窗口
							parent.$('#dlg-assignmenu').dialog("close");
						});
					} else {
						$.messager.alert('提示','分配失败！','error');
					}
				},
				error:function(info,er){
					$.messager.alert('提示','系统产生错误,请联系管理员!','error');
				}
			});
		  });
		
		}

		//全选
		function checkAll(flag) {
			var root = $('#menuTree').tree("getRoot");
			
			//$('#menuTree').tree(flag, root.target);
			checkOne(root,flag);
			var children = $('#menuTree').tree("getChildren");
			for(var i=0; i<children.length; i++) {
				//$('#menuTree').tree(flag, children[i].target);
				checkOne(children[i],flag);
			}
		}

		//自定义选中
		function checkOne(node,checked) {
			var tc = $(".tree-node[node-id='"+node.id+"']", $('#menuTree')).find(".tree-checkbox");
			if(checked) {
				tc.removeClass("tree-checkbox0").addClass("tree-checkbox1");
			} else {
				tc.removeClass("tree-checkbox1").addClass("tree-checkbox0");
			}
		}

		//自定义选中父节点
		function checkParent(node,checked) {
			checkOne(node,checked);

			var parent = $('#menuTree').tree('getParent',node.target);
			if(parent != null) {
				checkParent(parent,checked);
			}
		}

		
		$(function() {
			//菜单权限
			var menuIds = '${mIds}';
			var menuArray = menuIds.split(";");
			var menuArray = new Array();
			if(menuIds != "") {
				menuArray = menuIds.split(";");
		    }
		    //操作权限
	        var privilegeIds= '${pIds}';
	        var privilegeArray = new Array();
	        if(privilegeIds != "") {
	        	privilegeArray = privilegeIds.split(";");
		    }

			$('#menuTree').tree({
			    url: "${ctx}/role/assignMenu?id=${param.id}",
			    checkbox:true,
			    cascadeCheck:true,
			    loadFilter: function(data){
			    	//console.log(data.menus);
			        if (data.menus){
			            return eval(data.menus);
			        } else {
			            return null;
			        }
			    },
			    onCheck:function(node, checked) {
				    /*
			    	//选中子节点
					var children = $('#menuTree').tree("getChildren",node.target);
					if(children != null) {
						for(var i=0; i<children.length; i++) {
							//$('#menuTree').tree('check', children[i].target);
							checkOne(children[i],checked);
						}
					}

					//选中父节点
					if(checked) {
						var parent = $('#menuTree').tree('getParent',node.target);
				    	if(parent != null) {
					    	//$('#menuTree').tree('check', parent.target);
				    		checkParent(parent,checked);
					    }
					}
		    		*/
			    	
				},
			    onLoadSuccess:function(node, data) {
					for(var i=0; i<menuArray.length; i++) {
						    var item = menuArray[i];
							var node_ = $('#menuTree').tree('find', item);
							//如果是叶子节点才进行选中
							//console.log(node);
							var isLeaf = $('#menuTree').tree('isLeaf', node_.target);
							if(isLeaf){
							  //只有叶子节点才选中
							  $('#menuTree').tree('check', node_.target);
							}
					}// menuArray

					for(var i=0; i<privilegeArray.length; i++) {
						    var item = privilegeArray[i];
						    console.log(item);
							var node_ = $('#menuTree').tree('find', item);
							//console.log(node_);
							var isLeaf = $('#menuTree').tree('isLeaf', node_.target);
							if(isLeaf){
								  //只有叶子节点才选中
							   $('#menuTree').tree('check', node_.target);
							} 
					}//privilegeArray
					
				}//onLoadSuccess:function
			});

		})
		</script>
		<style type="text/css">
		.well {
		  background-color: #F5F5F5;
		  border: 1px solid #E3E3E3;
		  border-radius: 4px 4px 4px 4px;
		  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.05) inset;
		  padding: 19px;
		}
		</style>
	</head>
	<body>
		<div class="easyui-layout" style="width:100%;height: 340px;">
			<div data-options="region:'east',split:true" title="操作" style="width:200px;height: 300px">
				<div class="well" style="padding:10px;margin:15px;width:140px;height: 200px">
					<div style="font-weight: 900">${role.roleName }：</div>
					<div style="border-radius:3px;padding:4px;background-color:#a9e2ff;color:#043d5a;">${empty role.description?"无描述":role.description }</div>
				</div>
				<%-- 
				<div class="well" style="padding:10px;margin:15px;text-align:center;width:140px">
					<br/>
					<br/>
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="checkAll(true)" data-options="iconCls:'icon-light'">全选</a>
					<br/>
					<br/>
					<br/>
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="checkAll(false)" data-options="iconCls:'icon-light-off'">反选</a>
					<br/>
					<br/>
				</div>
				--%>
			</div>
			<div data-options="region:'center',title:'权限设置',iconCls:'icon-ok'" class="well" style="padding-top: 2px">
				<input type="hidden" id="roleId" value="${role.id}"/>
				<ul id="menuTree" class="easyui-tree" data-options="animate:false"></ul>
			</div>
		</div>
		<div style="margin-top: 20px;text-align: center;">
			<!-- 
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-hammer2'" onclick="doAssign()">分配权限</a>
			 -->
		</div>
	</body>
</html>
