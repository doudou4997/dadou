<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@include file="/comm/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>权限树</title>
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
		.small-font {font-size:12px;}
		</style>
	</head>
	<body class="easyui-layout" >
		<div data-options="region:'west',split:true,tools:'#west-tools'" title="权限管理" style="width:200px;">
			<div align="left" style="padding:10px">
			   <form:select id="sysList" path="sysList" items="${sysList}" itemLabel="name" itemValue="id" cssStyle="width:110px"/>
			</div>
			<ul id="menuTree" class="easyui-tree" data-options="animate:false"></ul>
			<div id="mm" class="easyui-menu" style="width:120px;">
				<div onclick="edit()" data-options="iconCls:'icon-edit'">编辑</div>
				<div onclick="deletex()" data-options="iconCls:'icon-remove'">删除</div>
				<div class="menu-sep"></div>
				<div onclick="addSibling()" data-options="iconCls:'icon-add'">添加平级</div>
				<div onclick="addChild()" data-options="iconCls:'icon-add'">添加下级</div>
			</div>
			<div id="west-tools">
				<a href="javascript:void(0)" class="icon-reload" title="刷新所有角色" onclick="javascript:refresh();"></a>
			</div>
		</div>
		<div id="info-detail" data-options="region:'center',iconCls:'icon-ok'" class="detail" title="详情" style="padding:20px;">
			<jsp:include page="detail.jsp"></jsp:include>
		</div>
		<div id="dlg"        class="easyui-dialog"   style="width:420px;height:360px;" ></div>
		<div id="dlg-choose" class="easyui-dialog"   style="width:200px;height:130px;"></div>
		<div id="dlg-pri"    class="easyui-dialog"   style="width:420px;height:360px;"></div>
	</body>
	<script type="text/javascript">
	// 避免重名
	var menuList = new Object();
	menuList.closeDlg = function(){
		$("#dlg").dialog("close");
	}
	/****************************************************
	函数名称:添加子节点
	功能:    用于添加下级节点
	输入参数:添加的类型 子节点
	输出参数:
	****************************************************/ 
	function addChild() {
		var node = $('#menuTree').tree('getSelected');
		if(node.id==1){
			alert("子系统级别权限请联系管理员进行添加！")
			return;
		}
		if (node.attributes.type == 2) {
		    //权限无子节点
			$.messager.alert('提示','请选择一个菜单!','error');
			return;
		}
		
		var path="${ctx}/modules/sys/menu/choose.jsp?pid="+node.id;
		$('#dlg-choose').dialog({href:path}).dialog("open");
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
		
		if(parent.id==1){
			alert("子系统级别权限请联系管理员进行添加！")
			return;
		}
		var path="${ctx}/modules/sys/menu/choose.jsp?pid="+parent.id;
		$('#dlg-choose').dialog({href:path}).dialog("open");
	}
	/****************************************************
	函数名称:编辑部门节点
	功能:    用于编辑部门的名称及描述
	输出参数:
	****************************************************/ 
	function edit() {
		var node = $('#menuTree').tree('getSelected');
		var id = node.id;

		var path = "";
		if(node.attributes.type == 1) {
			path="${ctx}/menu/edit?id="+id+'&ran='+Math.random();
			$('#dlg').dialog({href:path}).dialog("open");
		} else if(node.attributes.type == 2) {
			path="${ctx}/privilege/edit?id="+id+'&ran='+Math.random();
			$('#dlg-pri').dialog({href:path}).dialog("open");
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
			//选择删除的是权限
			var children = $('#menuTree').tree('getChildren',node.target);
			if (children.length > 0) {
				$.messager.alert('提示','选择的菜单中包含下级菜单或权限，无法删除','error');
				return;
			}
			url = "${ctx}/menu/remove?id="+node.id;
		} else if(node.attributes.type == 2) {
			//选择删除的是权限
			url="${ctx}/privilege/remove?id="+node.id;
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
						 $('#menuTree').tree('reload');
					},
					error:function(info){
						$.messager.alert('提示','系统产生错误,请联系管理员!','error');
					}
				});
			}
		});
	}
	/**
	 *刷新缓存
	 */
	function refresh(){
		$.messager.confirm('提示', '确定刷新菜单与权限缓存吗?', function(r){
			if(!r) {
				return;
			}
			var url = "${ctx}/menu/refresh";
			 //删除部门
			 $.ajax({
				url:url,
				type:'POST',
				dataType:'json',
				success: function(data){
					if(data.success == true) {
						$.messager.alert('提示',"成功！");
						//重新加载树形结构
    			        //window.location.reload();
    			        $('#menuTree').tree('reload');
					} else {
						$.messager.alert('提示','失败!','error');
					}
				},
				error:function(info){
					$.messager.alert('提示','系统产生错误,请联系管理员!','error');
				}
			});
		});
	}
	$(function() {
		//系统切换
		$('#sysList').change(function(){
		  $('#menuTree').tree("reload");
		});

		//dialog
		$('#dlg').dialog({
		    title: '菜单管理',
		    width: 420,
		    height:320,
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

		//选择dialog
		$('#dlg-choose').dialog({
		    title: '类型选择',
		    width: 200,
		    height: 130,
		    closed: true,
		    autoOpen: false,
		    cache: false,
		    modal: true,
		    buttons: [{
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){
		    		toPage();
		    		$('#dlg-choose').dialog("close");
				}
			},{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function(){
					$('#dlg-choose').dialog("close");
				}
			}]
		});

		//权限添加、编辑dialog
		$('#dlg-pri').dialog({
		    title: '权限管理',
		    width: 440,
		    height:280,
		    closed: true,
		    cache: false,
		    modal: true,
		    buttons: [{
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){
			    	if($('#dlg-pri').find("#privilegeSaveForm").size() > 0) {
						doInsertPrivilege();
					} else if($('#dlg-pri').find("#privilegeEditForm").size() > 0) {
						doEditPrivilege();
					}
				}
			},{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function(){
					$('#dlg-pri').dialog("close");
				}
			}]
		});
		
		//权限树
		$('#menuTree').tree({
		    url: "${ctx }/menu/showTree",
		    //filter
		    loadFilter: function(data){
		        if (data.menus){
		            return eval(data.menus);
		        } else {
		            return null;
		        }
		    },
			onLoadSuccess:function() {
		    	//关掉因parent的addTab产生的progress
				parent.$.messager.progress("close");
			},
		    //增加参数
		    onBeforeLoad:function(node, param) {
		    	//上传参数
		    	param.sysId = $('#sysList').val();
			},
			//右键
		    onContextMenu: function(e,node){
				e.preventDefault();
				$(this).tree('select',node.target);
				$('#mm').menu('show',{
					left: e.pageX,
					top: e.pageY
				});
			},
			//详情
			onClick:function(node) {
				var url = "";
				if(node.attributes.type == 1) {
					url = "${ctx}/menu/detail";
				} else if(node.attributes.type == 2) {
					url = "${ctx}/privilege/detail";
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