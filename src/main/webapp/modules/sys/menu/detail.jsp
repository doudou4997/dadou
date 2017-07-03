<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/comm/taglibs.jsp"%>
<table border="0" cellspacing="1" cellpadding="2" bgcolor="#999999"  width="90%"  style="font-size:12px;font-family:Verdana, Geneva, sans-serif; ">
	<tr>
		<td width="15%"  class="title">菜单ID：</td>
		<td class="content">${menu.id }</td>
	</tr>
	<tr>
		<td width="15%" class="title">菜单名：</td>
		<td class="content">${menu.name }</td>
	</tr>
	<tr>
		<td width="15%" class="title">Title：</td>
		<td class="content">${menu.title }</td>
	</tr>
	<tr>
		<td width="15%" class="title">菜单顺序：</td>
		<td class="content">${menu.sortNum }</td>
	</tr>
	<tr>
		<td width="15%" class="title">是否树状：</td>
		<td class="content">
			<c:if test="${menu ne null}">
				${menu.isTree==1?"是":"否" }
			</c:if>
		</td>
	</tr>
	<tr>
		<td width="10%" class="title">Target：</td>
		<td class="content">${menu.target }</td>
	</tr>
	<tr>
		<td width="10%" class="title">图标：</td>
		<td class="content">${menu.icon }</td>
	</tr>
	<tr>
		<td width="10%" class="title">URL：</td>
		<td class="content">${menu.url }</td>
	</tr>
</table>