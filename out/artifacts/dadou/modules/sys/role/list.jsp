<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglibs.jsp"%>
<%@page import="java.util.Date"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/comm/base.jsp" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>角色列表</title>
<style type="text/css">
	body {
    font-family:helvetica,tahoma,verdana,sans-serif;
    padding:8px;
    font-size:13px;
    margin:0;
}

</style>
<!--
添加上可以实现多选的功能
.datagrid-header-check input {display:inline-block;}
-->
</head>
<body>
	<div class="easyui-panel" title="角色列表" style="width:auto;padding:10px;">
		<form action="${ctx}/role/list" id="queryForm" name="queryForm">
		<table class="table-search" width="100%" style="margin:0px 0px 10px 0px;">
			<tr>
				<th width="10%">角色名： </th>
				<td width="15%">
				   <input name="roleName" id="roleName" size="18"/>
				</td>
				<th width="10%">角色类型： </th>
				<td width="15%">
				   <%-- <form:select id="sysList"  path="sysList"  items="${sysList}" itemLabel="name" itemValue="id" cssStyle="width:110px;height:auto"/> --%>
				   <input id="sysList" name="sysList" class="easyui-combobox" style="width:110px;height:auto" />
				   </td>
			    <td align="left">
				<a href="javascript:void(0)" class="btn btn-info"  onclick="searchx()" ><img src="${ctx }/styles/lib/easyui/themes/icons/search.png" align="absmiddle"/>&nbsp;查询</a>
				</td>
			</tr>
		  </table>
		</form>
		<table id="data-table"  width="100%" ></table>
	</div>
	<div id="dlg-window"></div>
	<div id="dlg-assignmenu" class="easyui-dialog" title="权限分配" style="width:650px;height:450px;" data-options="iconCls:'icon-clipboardtext',resizable:true,modal:true,closed:true">
		<iframe width="100%" height="100%" name="frameAssign" id="frameAssign" frameborder="0"></iframe>
	</div>
</body>
<script type="text/javascript">
//权限分配
function assignMenu(){
	//获取ID
    var id = getDatagridOnlyChecked();
	if(id == null) {
		$.messager.alert('提示',"请先选中一行(只允许单行操作)",'error');
		return;	
	}
	    //open
  	var path="${ctx}/role/redirect?id="+id;
  	//window.open(path);  
  	document.getElementById('frameAssign').src = path;
  	$('#dlg-assignmenu').dialog('open');
}
//增加角色按钮的事件响应
function addRole(){
	var path="${ctx}/role/add?r=";
	$('#dlg-window').dialog('refresh', path);
	$('#dlg-window').dialog('open');
}	
//编辑角色
function editRole(){
	 //获取ID
	 var id = getDatagridOnlyChecked();
	 if(id == null) {
		 $.messager.alert('提示',"请先选中一行(只允许单行操作)",'error');
		 return;	
   }
	//open
	var path="${ctx}/role/edit?id="+id;
	$('#dlg-window').dialog('refresh', path);
	$('#dlg-window').dialog('open');
}
//删除角色相应时间
function delRole(){
	$.messager.confirm('提示', "确定删除角色?", function(r){
		if(!r) {return;}

		//获取Id
		var id = getDatagridOnlyChecked();
		if(id == null) {
			$.messager.alert('提示',"请先选中一行(只允许单行操作)",'error');
			return;	
		}
    	//ajax
		var queryForm = $('#queryForm');
		$.ajax({
			url:"${ctx}/role/remove?id="+id,
			type:"POST",
			data: queryForm.serialize(),
			dataType:"json",
			success:function(data){
				if(data.success){// search
					$.messager.alert('',data.msg,'info',function(){
						searchx();
					});		
				}else{ // 弹出对话框
					$.messager.alert('提示',"请先删除该角色关联的用户",'error');		
				}
			},
			error:function(info){
				$.messager.alert('提示',"系统错误,请联系管理员",'error');	
			}
		});
	});
}

//查询 
 function searchx(){
	var roleName = $("#queryForm #roleName").val();
	var sysId =  $("#sysList").combobox('getValue');
	$('#data-table').datagrid('reload', {
	    roleName: roleName,
	    sysId:sysId
	});
}

$(function() {
	
	//角色类型下拉框
	$('#sysList').combobox({
		panelHeight:'auto',
		valueField:'id',
		textField:'name',
		editable:false,
		url:'${ctx}/role/sysListAjax',
		onSelect: function(rec){
			searchx();
	    },
	    onLoadSuccess : function() {
	    	$('#sysList').combobox('setValue','-1');
		},
	});
	
	//设置分页控件  
	$('#data-table').datagrid({
		url:'${ctx}/role/listAjax',
		rownumbers:true,
		autoRowHeight:true,
		singleSelect:true,
		pagination:true,
		fitColumns:true,
		height:$(window).height() - 110,
		onLoadSuccess:function() {
			//关掉因parent的addTab产生的progress
			parent.$.messager.progress("close");
		},
		toolbar:[{
			text:'增加角色',
			iconCls:'icon-add',
			handler:function(){addRole();}
		},'-',{
			text:'编辑角色',
			iconCls:'icon-edit',
			handler:function(){editRole();}
		},'-',{
			text:'分配权限',
			iconCls:'icon-cog',
			handler:function(){assignMenu();}
		},'-',{
			text:'删除角色',
			iconCls:'icon-no',
			handler:function(){delRole();}
		}],
		frozenColumns : [[ 
		   {field : 'id',align: 'center',checkbox:true},
		   {field: 'roleName', title: '角色名', width: 200, sortable:true}
		]],
		columns: [[
           {field: 'description', title: '角色描述', width: 500}
       ]]
	});

	//window
	$('#dlg-window').dialog({
	    title: '角色管理',
	    width: 530,
	    height:250,
	    closed: true,
	    cache: false,
	    modal: true,
	    buttons: [{
			text:'保存',
			iconCls:'icon-ok',
			handler:function(){
	    		if($('#dlg-window').find("#roleSaveForm").size() > 0) {
	    			doSave();
	    		} else if($('#dlg-window').find("#roleEditForm").size() > 0) {
	    			doUpdate();
	    		}
			}
		},{
			text:'关闭',
			iconCls:'icon-cancel',
			handler:function(){
				$('#dlg-window').dialog("close");
			}
		}]
	});
	//分配权限dialog
	$('#dlg-assignmenu').dialog({
	    title: '权限分配',
	    width: 650,
	    height:450,
	    closed: true,
	    cache: false,
	    modal: true,
	    buttons: [{
			text:'分配权限',      
			iconCls:'icon-ok',
			handler:function(){
	    		frameAssign.window.doAssign(); 
			}
		},{
			text:'关闭',
			iconCls:'icon-cancel',
			handler:function(){
				$('#dlg-assignmenu').dialog("close");
			}
		}]
	});
})
</script>
</html>
