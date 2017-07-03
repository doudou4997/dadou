<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/comm/taglibs.jsp"%>
<table border="0" cellspacing="1" cellpadding="2" bgcolor="#999999"  width="90%"  style="font-size:12px;font-family:Verdana, Geneva, sans-serif; ">
	<tr>
		<td width="15%" class="title">部门编号：</td>
		<td class="content">${dept.id }</td>
	</tr>
	<tr>
		<td width="15%" class="title" >部门名称：</td>
		<td class="content">${dept.deptName }</td>
	</tr>
	<tr>
		<td width="15%" class="title" >部门描述：</td>
		<td class="content">${dept.description }</td>
	</tr>
</table>