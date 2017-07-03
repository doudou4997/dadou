<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@include file="/comm/taglibs.jsp"%>
<script type="text/javascript">

function doEdit(){
	//整个form的校验
	var valid = $("#menuForm").form("validate");
	if(!valid) {
	   return;
	}
	//menuForm.action;
	var url = "${ctx}/menu/update";
	//表单提交
	$.ajax({
		url:url,
		type:'POST',
		data:$("#menuForm").serialize(),
		dataType:'json',
		success: function(data){
			$.messager.alert('',data.msg,'info',function(){
				menuList.closeDlg();
				$('#menuTree').tree('reload');
			});
		},
		error:function(info){
			$.messager.alert('提示','系统产生错误,请联系管理员!','error');
		}
	});
}
$(function() {

	//easyui validatebox
	$("#menuForm  #name").validatebox({
		required:true,
		missingMessage:"必须填写！"
	});
	$("#menuForm  #sortNum").validatebox({
		validType:'number'
	});
})
</script>
		<div style="padding:10px;text-align: left">
	    <form name="menuForm" id="menuForm" style="margin:0px;padding:0px" method="post" action="/menu/update">
	    	<table class="table-search">
	    	    <input type="hidden" name="id" value="${menu.id }"/>
	    		<tr>
	    			<th><span style="font-size: 12px;">菜单名：</span></th>
	    			<td><input name="name"  id="name" size="20" value="${menu.name }"/> </td>
	    		</tr>
	    		<tr>
	    			<th><span style="font-size: 12px;">Title：</span></th>
	    			<td><input name="title" size="20" value="${menu.title}"/></td>
	    		</tr>
	    		<tr>
	    			<th><span style="font-size: 12px;">菜单顺序：</span></th>
	    			<td><input name="sortNum" id="sortNum" size="20" value="${menu.sortNum }"/></td>
	    		</tr>
	    		<tr>
	    			<th><span style="font-size: 12px;">是否树状：</span></th>
	    			<td>
	    			   <form:select path="menu.isTree"  items="${treeList}" itemLabel="value" itemValue="key"/>
	    			</td>
	    		</tr>
	    		<tr>
	    			<th><span style="font-size: 12px;">Target：</span></th>
	    			<td><input name="target" size="20" value="${menu.target}"/></td>
	    		</tr>
	    		<tr>
	    			<th><span style="font-size: 12px;">图标：</span></th>
	    			<td><input name="icon" size="20" value="${menu.icon }"/></td>
	    		</tr>
	    		<tr>
	    			<th><span style="font-size: 12px;">URL：</span></th>
	    			<td><input name="url" size="30" value="${menu.url }"/></td>
	    		</tr>
	    	</table>
	    </form>
	    </div>
