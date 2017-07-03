<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.util.Random" %>
<%@ include file="/comm/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/comm/base.jsp" />
<meta charset="UTF-8">
<title>用户管理</title>
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
	<div class="easyui-panel" title="用户管理" style="width:auto;padding:10px;">
		<form action="${ctx}/user/list" id="queryForm" name="queryForm">
			<table class="table-search" width="100%" style="margin:0px 0px 10px 0px;">
				<tr>
					<th width="10%">用户名： </th>
					<td width="15%"><input id="uname" style="width:80px"></td>
					<td >
						<a href="javascript:void(0)" id="search" class="btn btn-info"> <img src="${ctx }/styles/lib/easyui/themes/icons/search.png" align="absmiddle"/>&nbsp;查询</a>
						<a href="${ctx}/user/list" class="btn"><img src="${ctx }/styles/lib/easyui/themes/icons/undo.png" align="absmiddle"/>&nbsp;清空</a>
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
var userList = new Object();

//重新加载
$(function(){
	// 生成用户列表
	$('#dg').datagrid({
		url:'${ctx}/user/listAjax?r=Math.random()',
		rownumbers:true,
		singleSelect:true,
		autoRowHeight:true,
		fitColumns:true,
		pagination:true,
        height:$(window).height() - 110,
		toolbar:['-'],
		onLoadSuccess:function() {
			//关掉因parent的addTab产生的progress
			parent.$.messager.progress("close");
		},
		columns: [[
		   {field:'id',align: 'center',checkbox:true},
           {field: 'openid', title: '微信标识', width: 80},
           {field: 'username', title: '用户名', width: 80},
           {field: 'gender', title: '性别', width: 30},
           /* {field: 'jobName', title: '职位', width: 60},*/
            {field: 'phone', title: '手机', width: 120,},
           {field: 'point', title: '积分', width: 100,sortable:true},
           {field: 'followtime', title: '关注时间', width: 120}
       ]]
	});
	
	// 搜索按钮的事件响应
	$('#search').bind('click', function(){
		var un = $.trim($("#uname").val());
		$('#dg').datagrid('reload',{
			userName: un
		});
	});
	//显示Dialog
});

</script>

</body>
</html>
