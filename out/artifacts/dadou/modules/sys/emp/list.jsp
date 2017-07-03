<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.util.Random" %>
<%@ include file="/comm/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/comm/base.jsp" />
<meta charset="UTF-8">
<title>员工管理</title>
<style type="text/css">
body {
    font-family:helvetica,tahoma,verdana,sans-serif;
    padding:8px;
    font-size:13px;
    margin:0;
}
h2 {
    font-size:18px;
    font-weight:bold;
    margin:0;
    margin-bottom:15px;
}
</style>
</head>
<body>
	<div class="easyui-panel" title="员工管理" style="width:auto;padding:10px;">
		<form action="${ctx}/role/list" id="queryForm" name="queryForm">
			<table class="table-search" width="100%" style="margin:0px 0px 10px 0px;">
				<tr>
					<th width="10%">用户名： </th>
					<td width="15%"><input id="srhUn" style="width:80px"></td>
					<th width="10%">姓名： </th>
					<td width="15%"><input id="srhNm" style="width:80px"></td>
					<td >
						<a href="javascript:void(0)" id="search" class="btn btn-info"> <img src="${ctx }/styles/lib/easyui/themes/icons/search.png" align="absmiddle"/>&nbsp;查询</a>
						<a href="${ctx}/emp/list" class="btn"><img src="${ctx }/styles/lib/easyui/themes/icons/undo.png" align="absmiddle"/>&nbsp;清空</a>
					</td>
				</tr>
				
			</table>
		</form>
		<table id="dg" width="100%"></table>
	</div>
     <%-- 
     <div id="empList-dd" class="easyui-dialog" title="信息" style="width:550px;height:320px;"
    		data-options="iconCls:'icon-clipboardtext',resizable:true,modal:true,closed:true">
    	<iframe  width="100%" height="99%" name="empList-ddFrame" id="empList-ddFrame" frameborder="0"></iframe>
    </div>
    --%>
    <div id="empList-dd"></div>
    <div id="empList-addEmpWin"   title="新添用户" style="width:750px;height:320px;"></div>
    <div id="empList-asnRoleWin"  title="分配角色" style="width:700px;height:480px;"></div>
    <div id="empList-asnSiteWin"  title="分配工厂" style="width:700px;height:480px;"></div>
<script type="text/javascript">
/**
* 获取选中的id
*/
function getDatagridChecked() {
	var rows = $('#dg').datagrid('getChecked'); 
	if(rows.length == 0) {
		return null;
	} else {
  	  return rows[0].id;
	}
}

// 避免重名
var empList = new Object();
// 打开新添用户窗口
empList.addEmp = function(){
	var path="${ctx}/emp/add";
	$('#empList-addEmpWin').dialog({href:path}).dialog("open");
}

// 打开修改用户窗口
empList.editEmp = function(){
	var id = getDatagridChecked();
	if(id == null) {
		 $.messager.alert('提示',"请先选中一行",'error');
		 return;	
    }
	var path="${ctx}/emp/edit?id=" + id;
	$('#empList-addEmpWin').dialog({width: 750,height:320});
	$('#empList-addEmpWin').dialog({href:path}).dialog("open");
}

// 打开分配角色窗口
empList.asnRole = function(){
	var id = getDatagridChecked();
	
	if(id == null) {
		 $.messager.alert('提示',"请先选中一行",'error');
		 return;	
    }
	var path="${ctx}/emp/assignRoleAjax?id="+ id+"&r=" + Math.random();
	$('#empList-asnRoleWin').dialog({width: 700,height:480});
	$('#empList-asnRoleWin').dialog({href:path}).dialog("open");
}
//打开分配工厂窗口
empList.asnSite = function(){
	
	var id = getDatagridChecked();
	if(id == null) {
		 $.messager.alert('提示',"请先选中一行",'error');
		 return;	
    }
	var path="${ctx}/emp/assignSiteAjax?id="+ id+"&r=" + Math.random();
	//$("#empList-asnSiteWin").dialog('options').href = path; 
	//assignSite(path);
	//解决重复加载的问题
	$('#empList-asnSiteWin').dialog({href:path}).dialog("open");
}
//初始化密码
empList.initPass = function(){
	var id = getDatagridChecked();
	if(id == null) {
		 $.messager.alert('提示',"请先选中一行",'error');
		 return;	
    }
	$.messager.confirm("提示", "确定初始化密码？（默认:123456）", function(r){
		if (r){
	    	var url="${ctx}/emp/initPass?id=" + id;
			$.ajax({
				url:url,
				type:'POST',
				dataType:'json',
				success: function(data){
					if(data.success == true) {
						 $.messager.alert("提示",data.msg,"info");
					} else {
						 $.messager.alert("提示",data.msg,"error");
					}
				},
				error:function(info){
					 $.messager.alert("提示","系统产生错误,请联系管理员!","error");
				}
			});
		}
	});
}

//删除用户
empList.removeEmployee = function(){
	var id = getDatagridChecked();
	if(id == null) {
		 $.messager.alert('提示',"请先选中一行",'error');
		 return;	
    }
    $.messager.confirm('确认','您确认想要删除此用户吗？',function(r){
    	 if (r){
    		 $.ajax({
             url: '${ctx}/emp/removeEmp',
             data: {id: id},
             success: function (data) {
                 var data = eval('(' + data + ')');
                 if(data.success) {
     				$.messager.alert('',data.msg,"info");
     				$('#dg').datagrid('reload');
     			} else {
     				$.messager.alert('',data.msg,"error");
     			}
             }
         });
         }
    }); 
}

//重新加载
$(function(){
	// 生成用户列表
	$('#dg').datagrid({
		url:'${ctx}/emp/listAjax?r=Math.random()',
		rownumbers:true,
		singleSelect:true,
		autoRowHeight:true,
		fitColumns:true,
		pagination:true,
		height:$(window).height() - 110,
		toolbar:[{
			text:'新添用户',
			iconCls:'icon-add',
			handler:function(){empList.addEmp();}
		},'-',{
			text:'修改用户',
			iconCls:'icon-edit',
			handler:function(){empList.editEmp();}
		},'-',{
			text:'分配角色',
			iconCls:'icon-group',
			handler:function(){empList.asnRole();}
		},'-',{
			text:'分配工厂',
			iconCls:'icon-organisation',
			handler:function(){empList.asnSite();}
		},'-',{
			text:'初始化密码',
			iconCls:'icon-lock',
			handler:function(){empList.initPass();}
		},'-',{
			text:'删除用户',
			iconCls:'icon-group',
			handler:function(){empList.removeEmployee();}
		},'-'],
		onLoadSuccess:function() {
			//关掉因parent的addTab产生的progress
			parent.$.messager.progress("close");
		},
		columns: [[
		   {field:'id',align: 'center',checkbox:true},
           {field: 'userName', title: '用户名', width: 80},
           {field: 'name', title: '姓名', width: 80},
           {field: 'gender', title: '性别', width: 30},
           /* {field: 'jobName', title: '职位', width: 60},
           {field: 'deptName', title: '部门名称', width: 100}, */
           {field: 'userCode', title: '员工编号', width: 80,sortable:true},
           {field: 'mobile', title: '手机', width: 120,sortable:true},
           {field: 'email', title: 'email', width: 120,sortable:true}
       ]]
	});
	
	// 搜索按钮的事件响应
	$('#search').bind('click', function(){
		var un = $.trim($("#srhUn").val());
		var nm = $.trim($("#srhNm").val());
		$('#dg').datagrid('reload',{
			userName: un,
			name: nm
		});
	});
	//显示Dialog
	//window
	$('#empList-dd').dialog({
	    title: '员工详情',
	    width: 550,
	    height:320,
	    closed: true,
	    cache: false,
	    iconCls:'icon-clipboardtext',
	    resizable:false,
	    modal: true,
	    buttons: [{
			text:'关闭',
			iconCls:'icon-cancel',
			handler:function(){
				$('#empList-dd').dialog("close");
			}
		}]
	});
	//角色分配
	$('#empList-asnRoleWin').dialog({
	    title: '角色分配',
	    width: 700,
	    height:480,
	    closed: true,
	    cache: false,
	    iconCls:'icon-group',
	    resizable:false,
	    modal: true,
	    buttons: [{
			text:'分配',
			iconCls:'icon-ok',
			handler:function(){
				var operate_method = $("#empSaveForm_method").val();
				if(typeof(operate_method)!=undefined && operate_method == 'add'){
					//设置角色
					var roleIds = getRoleIds();
					$("#empSaveForm_roleIds").val(roleIds);
					$('#empList-asnRoleWin').dialog({width: 0,height:0});
				}else{
					//确认分配
					doAssign();
				}
			}
	       },{
			text:'关闭',
			iconCls:'icon-cancel',
			handler:function(){
				$('#empList-asnRoleWin').dialog("close");
			}
		}]
	});
	//工厂分配
	$('#empList-asnSiteWin').dialog({
	    title: '分配工厂',
	    width: 700,
	    height:480,
	    closed: true,
	    cache: false,
	    iconCls:'icon-organisation',
	    resizable:false,
	    modal: true,
	    buttons: [{
			text:'分配',
			iconCls:'icon-ok',
			handler:function(){
				var operate_method = $("#empSaveForm_method").val();
				if(typeof(operate_method)!=undefined && operate_method == 'add'){
					//设置工厂
					var siteIds = getSiteIds();
					$("#empSaveForm_siteIds").val(siteIds);
					$('#empList-asnSiteWin').dialog({width: 0,height:0});
				}else{
					//确认分配
					doAssignSite();
				}
			}
	       },{
			text:'关闭',
			iconCls:'icon-cancel',
			handler:function(){
				$('#empList-asnSiteWin').dialog("close");
			}
		}]
	});

   //添加
	$('#empList-addEmpWin').dialog({
	    title: '员工维护',
	    width: 750,
	    height:320,
	    closed: true,
	    cache: false,
	    iconCls:'icon-add',
	    resizable:false,
	    modal: true,
	    buttons: [{
			text:'保存',
			iconCls:'icon-ok',
			handler:function(){
	    		if($('#empList-addEmpWin').find("#empSaveForm").size() > 0) {
	    			doInsert();
	    		} else if($('#empList-addEmpWin').find("#empEditForm").size() > 0) {
	    			doUpdate();
	    		}
			 }
		    },{
			text:'关闭',
			iconCls:'icon-cancel',
			handler:function(){
				$('#empList-addEmpWin').dialog("close");
			}
		}]
	});
});

</script>

</body>
</html>
