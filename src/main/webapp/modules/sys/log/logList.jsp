<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/comm/base.jsp" />
<meta charset="UTF-8">
<title>操作日志</title>
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
.demo-info{
	background:#FFFEE6;
	color:#8F5700;
	padding:12px;
}
.demo-tip{
	width:16px;
	height:16px;
	margin-right:8px;
	float:left;
}
</style>
</head>
<body>
	<div class="easyui-panel" title="操作日志" style="width:auto;padding:10px;">
		<s:form action="/system/role.action?method=list" id="queryForm" name="queryForm">
			<table class="table-search" width="100%" style="margin:0px 0px 10px 0px;">
				<tr>
					<th width="10%">用户名： </th>
					<td width="15%"><input id="srhUn" style="width:80px"></td>
					<th width="10%">姓名： </th>
					<td width="15%"><input id="srhNm" style="width:80px"></td>
					<td >
						<a href="javascript:void(0)" id="search" class="btn btn-info"> <img src="${ctx }/styles/lib/easyui/themes/icons/search.png" align="absmiddle"/>&nbsp;查询</a>
						<a href="${ctx}/system/emp.action?method=list" class="btn"><img src="${ctx }/styles/lib/easyui/themes/icons/undo.png" align="absmiddle"/>&nbsp;清空</a>
					</td>
				</tr>
				
			</table>
		</s:form>
		<table id="dg" width="100%"></table>
	</div>
     <div id="logList-dd" class="easyui-dialog" title="信息" style="width:750px;height:320px;"
    		data-options="iconCls:'icon-clipboardtext',resizable:true,modal:true,closed:true">
    	<iframe width="100%" height="99%" name="logList-ddFrame" id="logList-ddFrame" frameborder="0"></iframe>
    </div>
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
var logList = new Object();


// 打开用户详情窗口
logList.displayUser = function(id){
	var path="${ctx}/system/log.action?method=display&id=" + id + "&sysType=" +
			sysType + "&r=" + Math.random();
	document.getElementById("ogList-ddFrame").src = path;	
	$('#logList-dd').dialog("open");
}

$(function(){
	// 生成用户列表
	$('#dg').datagrid({
		url:'${ctx}/system/log.action?method=listAjax&r=Math.random()',
		rownumbers:true,
		singleSelect:true,
		autoRowHeight:true,
		pagination:true,
		onLoadSuccess:function() {
			//关掉因parent的addTab产生的progress
			parent.$.messager.progress("close");
		},
		columns: [[
		   {field:'id',align: 'center',checkbox:true},
           {field: 'operateTime', title: '操作时间', width: 80},
           {field: 'userName', title: '用户名', width: 80},
           {field: 'moudleType', title: '模块名', width: 180},
           {field: 'operateName', title: '操作名', width: 80},
           {field: 'empId', title: '员工号', width: 120},
           {field: 'event', title: '事件', width: 60}
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
	
});

</script>

</body>
</html>
