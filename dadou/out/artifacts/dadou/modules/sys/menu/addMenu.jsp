<%@ page language="java" pageEncoding="utf-8"%>
<%@include file="/comm/taglibs.jsp"%>
<script language=javascript>
function doInsert(){
	var menuFormSave = $('#menuFormSave');

	//整个form的校验
	var valid = menuFormSave.form("validate");
	if(!valid) {
	   return;
	}
	var url = "${ctx}/menu/save";
	//表单提交
	$.ajax({
		url:url,
		type:'POST',
		data:$("#menuFormSave").serialize(),
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
function enter(){
	if(window.event.keyCode == 13){
		event.returnValue=false;
	}
}
$(function() {
	$("#menuFormSave  #name").validatebox({
		required:true,
		missingMessage:"必须填写！"
	});
	$("#menuFormSave  #sortNum").validatebox({
		validType:'number'
	});
})
</script>
		<div style="padding:10px;text-align: left">
	    <form name="menuFormSave" id="menuFormSave" style="margin:0px;padding:0px" method="post" action="/menu/save">
	    <input type="hidden" name="parentId" value="${parentId}"/>
	    	<table class="table-search">
	    		<tr>
	    			<th><span style="font-size: 12px;">菜单名:</span></th>
	    			<td> <input type="text" name="name"  size="20"/> <span style="color:red">&nbsp;*</span></td>
	    		</tr>
	    		<tr>
	    			<th><span style="font-size: 12px;">Title:</span></th>
	    			<td><input type="text" name="title"  size="20"/></td>
	    		</tr>
	    		<tr>
	    			<th><span style="font-size: 12px;">菜单顺序:</span></th>
	    			<td><input type="text" name="sortNum"  size="20"/></td>
	    		</tr>
	    		<tr>
	    			<th><span style="font-size: 12px;">是否树状:</span></th>
					<td> <form:select id="isTree" path="menu.isTree"  items="${treeList}" itemLabel="value" itemValue="key"/> </td>
	    		</tr>
	    		<tr>
	    			<th><span style="font-size: 12px;">Target:</span></th>
	    			<td>
	    				<input type="text" name="target"  size="20"/>
	    			</td>
	    		</tr>
	    		<tr>
	    			<th><span style="font-size: 12px;">图标:</span></th>
	    			<td>
	    				<input type="text" name="icon"  size="20"/>
	    			</td>
	    		</tr>
	    		<tr>
	    			<th><span style="font-size: 12px;">URL:</span></th>
	    			<td>
	    				<input type="text" name="url"  size="20"/>
	    			</td>
	    		</tr>
	    	</table>
	    	</form>
	    </div>
