<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/comm/taglibs.jsp"%>
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
<table border="0" cellspacing="1" cellpadding="2" bgcolor="#999999"  width="90%" style="font-size:12px;font-family:Verdana, Geneva, sans-serif;">
	<tr>
		<td width="15%" class="title">用户名：</td>
		<td class="content">${emp.userName }</td>
	</tr>
	<tr>
		<td width="15%" class="title">姓名：</td>
		<td class="content">${emp.name }</td>
	</tr>
	<tr>
		<td width="15%" class="title">部门：</td>
		<td class="content">${emp.deptName }</td>
	</tr>
	<tr>
		<td width="15%" class="title">员工编号：</td>
		<td class="content">${emp.userCode }</td>
	</tr>
	<tr>
		<td width="15%" class="title">手机：</td>
		<td class="content">${emp.mobile }</td>
	</tr>
</table>