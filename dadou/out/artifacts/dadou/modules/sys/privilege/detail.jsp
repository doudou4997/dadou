<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/comm/taglibs.jsp"%>
<table border="0" cellspacing="1" cellpadding="2" bgcolor="#999999"  width="90%" style="font-size:12px;font-family:Verdana, Geneva, sans-serif; ">
	<tr>
		<td width="15%" class="title">权限Id：</td>
		<td class="content">${privilege.id }</td>
	</tr>
	<tr>
		<td width="15%" class="title">权限名称：</td>
		<td class="content">${privilege.name }</td>
	</tr>
	<tr>
		<td width="15%" class="title">权限顺序：</td>
		<td class="content">${privilege.sortNum }</td>
	</tr>
	<tr>
		<td width="15%" class="title">操作：</td>
		<td class="content">${privilege.operate }</td>
	</tr>
	<%--
	<tr>
		<td width="15%" class="title">URL：</td>
		<td class="content">${privilege.url }</td>
	</tr>
	 --%>
	<tr>
		<td width="15%" class="title">权限描述：</td>
		<td class="content">${privilege.description }</td>
	</tr>
</table>