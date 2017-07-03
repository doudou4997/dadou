<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglibs.jsp"%>
<%@page import="java.util.Date"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/comm/base.jsp" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>数据字典列表</title>
<style type="text/css">
	body {
    font-family:helvetica,tahoma,verdana,sans-serif;
    padding:8px;
    font-size:13px;
    margin:0;
}
.datagrid-editable-input input{
	width:40;
}

</style>
</head>
<body>
 	<div id="add-window" ></div>
 	<div id="edit-window" ></div>
		<form id="test" name="queryForm">
		<table class="table-search" width="100%" style="margin:0px 0px 10px 0px;">
			<tr>
				<th width="10%">类别名称： </th>
				<td width="15%">
				   <input name="typeName" id="typeName" size="18"/>
				</td>
			    <td align="left">
				<a href="javascript:void(0)" class="btn btn-info" id="select"><img src="${ctx }/styles/lib/easyui/themes/icons/search.png" align="absmiddle"/>&nbsp;查找</a>
				</td>
			</tr>
		  </table>
		</form>
	 <table id="data-table"  width="100%" ></table>
</body>
<script type="text/javascript">

//避免重名
var dictList = new Object();

//打开查看详情窗口
dictList.display = function(id){
	var path="${ctx}/pr/sop/soplist?sopid="+id;
	/* $('#dlg-window').dialog('open').dialog('refresh', path); */
	$('#dlg-window').dialog({href:path}).dialog('open');
};

//添加
dictList.add = function(){
	var path="${ctx}/dict/add";
	/* $('#add-window').dialog('open').dialog('refresh', path); */
	$('#add-window').dialog({href:path}).dialog('open');
};

var editingId;
dictList.edit = function(){	
	var id = getDataId();
	var parentId = isParentNode();
	if(parentId != null){
		if(id == null) {
			 $.messager.alert('提示',"请先选中一行",'error');
			 return;	
	    }
		var path="${ctx}/dict/edit?id="+id;
		/* $('#edit-window').dialog('open').dialog('refresh', path); */
		$('#edit-window').dialog({href:path}).dialog('open');
	}else{
		$.messager.alert('', "无法编辑父节点！", "info");
	}
};
//获取
function getDataId() {
	var row = $('#data-table').treegrid('getSelected');
 	if(row == undefined) {
 	 	return null;
 	 } else {
 	   	return row.id;
 	 }
}
function isParentNode(){
	var row = $('#data-table').treegrid('getSelected');
	if(row._parentId==null) {
 	 	return null;
 	 } else {
 	   	return row._parentId;
 	 }
}


dictList.removeAjax = function(){
	var id = getDataId();
	var parentId = isParentNode();
	if(id == null) {
		$.messager.alert('提示',"请先选中一行",'error');
		return;	
	}
	if(parentId != null){
		$.messager.confirm('确认','确定删除？',function(r){
		   	 if (r){
		   		$.ajax({
		   			url: '${ctx}/dict/remove',
		   			data: {id: id},
		   			success: function (data) {
		   				var data = eval('(' + data + ')');
		   	            if(data.success) {
		   	            	$.messager.alert('',data.msg,"info");
		   	            	$('#data-table').treegrid('reload');
		   	 			}else{
		   	 				$.messager.alert('', data.msg, "info");
		   	 			}
		   	         }
		   	     });
		        }
		   });
	}else{
		$.messager.alert('', "无法删除父节点！", "info");
	}
	
};

$(function() {
	//treegrid
	$('#data-table').treegrid({
		//url:'${ctx}/modules/im/dict/treegrid_data4.json',
		url:'${ctx}/dict/listAjax',
		//pagination:true,
		rownumbers: true,
		showFooter: true,
		animate:true,
		fitColumns: true,
		idField: 'id',
		treeField:'value',
		showFooter: true,
		height:$(window).height()-60,
		toolbar:[{
			text:'新添',
			iconCls:'icon-add',
			handler:function(){
				dictList.add();
			}
		},'-',{
			text:'编辑',
			iconCls:'icon-edit',
			handler:function(){
				dictList.edit();
			}
		},'-',{
			text:'删除',
			iconCls:'icon-group',
			handler:function(){
				dictList.removeAjax();
			}
		},'-'],
		 columns: [[
			{field: 'value', title: '显示的名称', width:'280',editor:'text'}, 
			{field: 'key', title: '实际的值', width:'160',editor:'text'},
			{field: 'type', title: '值类型', width:'150',editor:'text'},
			{field: 'typeName', title: '类型名称', width:'190',editor:'text'}
		]]
	}); 
	
	//添加窗口
	$('#add-window').dialog({
		title : '添加数据字典',
		width : 500,
		height:200,
		closed : true,
		cache : false,
		modal : true,
		onBeforeLoad:function(param){ 
		},
		buttons : [
					{
					  text : '保存',
					  iconCls : 'icon-ok',
					  handler : function() {
						  doSaveAjax();
						  //doSave();
					  }
					}, 
					{
					  text : '关闭',
					  iconCls : 'icon-cancel',
					  handler : function() {
						  $('#add-window').dialog("close");
					  }
				    } 
				 ]//end buttons
      });//end 添加窗口
      
	//编辑窗口
	$('#edit-window').dialog({
		title : '编辑',
		width : 300,
		height : 200,
		closed : true,
		cache : false,
		modal : true,
		onBeforeLoad:function(param){ 
		},
		buttons : [
					{
					  text : '保存',
					  iconCls : 'icon-ok',
					  handler : function() {
						 // 
						  doUpdate();
					  }
					}, 
					{
					  text : '关闭',
					  iconCls : 'icon-cancel',
					  handler : function() {
						  $('#edit-window').dialog("close");
					  }
				    } 
				 ]//end buttons
      });//end 添加窗口
      
	// 搜索按钮的事件响应
    $('#select').bind('click', function () {
        var typeName = $.trim($("#typeName").val());
        $('#data-table').treegrid('load', {
        	typeName: typeName
    	});
    });
});
</script>
</html>