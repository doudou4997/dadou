<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@include file="/comm/taglibs.jsp"%>
<html>
	<head>
		<title>部门管理树</title>
		<jsp:include page="/comm/base.jsp" />
		<style type="text/css">
		.detail .title {
			background-color: #eee;
			text-align: right
		}
		.detail .content {
			background-color: white;
			padding:0 10px;
		}
		</style>
	</head>
	<body style="margin:0px;padding:8px;border:0 none;overflow:hidden">
		<div class="easyui-layout" style="height:100%;margin:0px;overflow:hidden">
			<div data-options="region:'west',split:true,tools:'#west-tools'" title="系统" style="width:200px;">
				<ul id="menuTree" class="easyui-tree" data-options="animate:false"></ul>
				<div id="mm" class="easyui-menu" style="width:120px;">
					<div onclick="edit()" data-options="iconCls:'icon-edit'">编辑</div>
					<div onclick="deletex()" data-options="iconCls:'icon-remove'">删除</div>
					<div class="menu-sep"></div>
					<div onclick="addSibling()" data-options="iconCls:'icon-add'">添加平级</div>
				</div>
				<div id="deptMgRootMenu" class="easyui-menu" style="width:120px;">
					<div onclick="edit()" data-options="iconCls:'icon-edit'">编辑</div>
					<div class="menu-sep"></div>
					<div onclick="addChild()" data-options="iconCls:'icon-add'">添加下级</div>
				</div>
			</div>
			<div id="info-detail" data-options="region:'center',iconCls:'icon-ok'" class="detail" title="详情" style="padding:20px;">
				<jsp:include page="detail.jsp"></jsp:include>
			</div>
			<div id="dlg" class="easyui-dialog"  style="padding:10px;text-align: center;" ></div>
		</div>
	</body>
	<script type="text/javascript">
	
	// 把messager默认的ok按钮和cancel按钮里的文字改成中文
	$.messager.defaults.ok = "确定";
	$.messager.defaults.cancel = "取消";
	
	/****************************************************
	函数名称:添加子节点
	功能:    用于添加下级节点
	输入参数:添加的类型 子节点
	输出参数:
	****************************************************/ 
	function addChild() {
		var node = $('#menuTree').tree('getSelected');

		if (node.attributes.type == 2) {
		    //员工无子节点
			$.messager.alert('提示','请选择一个部门!','error');
			return;
		}
		
		var path="${ctx}/dept/add?parentId="+node.id;
		$('#dlg').dialog('refresh', path);
		$('#dlg').dialog('open');
	}
	/****************************************************
	函数名称:添加平级节点
	功能:    用于添加平级节点
	输入参数:添加的类型 兄弟节点
	输出参数:
	****************************************************/ 
	function addSibling() {		
		var node = $('#menuTree').tree('getSelected');
		var parent = $('#menuTree').tree('getParent',node.target);
		var path="${ctx}/dept/add?parentId="+parent.id;
		$('#dlg').dialog('refresh', path);
		$('#dlg').dialog('open');
	}
	/****************************************************
	函数名称:编辑部门节点
	功能:    用于编辑部门的名称及描述
	输出参数:
	****************************************************/ 
	function edit() {
		var node = $('#menuTree').tree('getSelected');
		var id = node.id;

		var url = null;
		if(node.attributes.type == 1) {
			url="${ctx}/dept/edit?id="+id+'&ran='+Math.random();
			$('#dlg').dialog('refresh', url);
			$('#dlg').dialog('open');
		} else if(node.attributes.type == 2) {
			$.messager.alert('提示','只能查看员工','error');
		}
	}
	/****************************************************
	函数名称:删除部门节点
	功能:   用于删除部门,如果部门有子节点,不可以直接删除
	输出参数:
	****************************************************/ 
	function deletex() {
		var node = $('#menuTree').tree('getSelected');
		var url = "";
		if(node.attributes.type == 1) {
			//选择删除的是部门
			var children = $('#menuTree').tree('getChildren',node.target);
			if (children.length > 0) {
				$.messager.alert('提示','选择的部门中包含员工，无法删除','error');
				return;
			}
			url = "${ctx}/dept/remove?id="+node.id;
		} else if(node.attributes.type == 2) {
			//选择删除的是权限
			$.messager.alert('提示','您只能查看员工','error');
			return;
		}	
		$.messager.confirm('提示', '确定删除吗?', function(r){
			if (r){
				//删除
				$.ajax({
					url:url,
					type:'POST',
					dataType:'json',
					success: function(data){
						//重新加载树形结构
	 			        window.location.reload();
					},
					error:function(info){
						$.messager.alert('提示','系统产生错误,请联系管理员!','error');
					}
				});
			}
		});
	}
	$(function() {
		//系统切换
		//dialog
		$('#dlg').dialog({
		    title: '部门操作',
		    width: 400,
		    height: 220,
		    closed: true,
		    cache: false,
		    modal: true,
		    buttons: [{
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){
					if($('#dlg').find("#menuForm").size() > 0) {
			    		doEdit();
					} else if($('#dlg').find("#menuFormSave").size() > 0) {
						doInsert();
					}
				}
			},{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function(){
					$('#dlg').dialog("close");
				}
			}]
		});
		//部门树
		$('#menuTree').tree({
		    url: "${ctx}/dept/showTree",
		    //filter
		    loadFilter: function(data){
		        if (data.menus){
		            return eval(data.menus);
		        } else {
		            return null;
		        }
		    },
		    //增加参数
		    onBeforeLoad:function(node, param) {
			},
			onLoadSuccess:function() {
		    	//关掉因parent的addTab产生的progress
				parent.$.messager.progress("close");
			},
			//右键
		    onContextMenu: function(e,node){
				e.preventDefault();
				$(this).tree('select',node.target);
				if (node.id == 100) {
					// 根节点的右键菜单
					$('#deptMgRootMenu').menu('show',{
						left: e.pageX,
						top: e.pageY
					});
				}else if (node.attributes.type == 1) {
					// 部门的右键菜单
					$('#mm').menu('show',{
						left: e.pageX,
						top: e.pageY
					});
				} else {
					// 员工的右键菜单
					$('#deptMgEmpMenu').menu('show',{
						left: e.pageX,
						top: e.pageY
					});
				}
			},
			//详情
			onClick:function(node) {
				var url = "";
				if(node.attributes.type == 1) {
					url = "${ctx}/dept/detail";
				} else if(node.attributes.type == 2) {
					url = "${ctx}/emp/detail";
				}
				$.ajax({
			      url:url,
			      data:{id : node.id},
			      type: "POST",
			      dataType: "html",
			      success: function(data){
			         $("#info-detail").html(data);
			      }
			   });
			}
		});
	});
	
	</script>
</html>