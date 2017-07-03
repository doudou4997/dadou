<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglibs.jsp"%>
<script type="text/javascript">
function doclose(){
	self.close();
}
		
//角色保存
function doSave(){
	//整个form的校验
	var valid = $('#roleSaveForm').form("validate");
	if(!valid) {
	   return;
	}
 
	//表单提交
	var url = "${ctx}/role/save";
	$.ajax({
		url:url,
		type:'POST',
		dataType:'json',
		data:$("#dlg-window #roleSaveForm").serialize(),
		success: function(data){
			$.messager.alert('',data.msg,"info",function(){
				//父窗口刷新
				searchx();
			});
			$('#dlg-window').dialog('close');
		},
		error:function(info,er){
			alert("系统产生错误,请联系管理员!");
		}
	});
}

$(function() {
	//初始化easyui
	$("#roleSaveForm  #roleName").validatebox({
		required:true,
		missingMessage:"必须填写！"
	});
	$("#roleSaveForm").validatebox({
		validType:'sysBlong'
	});
})
	
</script>
<div  style="padding:10px;">
   <form action="/role/save" method="post" name="roleSaveForm" id="roleSaveForm">
  	<table class="table-search"  align="center"  width="100%">
  		<tr>
  			<th>角色名称：</th>
  			<td> <input id="roleName" name="roleName" size="25" maxlength="25" style="font-size:13px"/><span style="color:red">&nbsp;*</span></td>
  		</tr>
  		<tr>
  			<th>所属系统：</th>
  			<td id="sys">
  			  <form:select id="sysList" path="role.sysId"  items="${sysList}" itemLabel="name" itemValue="id" cssStyle="font-size:13px" /> 
  			<span style="color:red">&nbsp;*</span></td>
  		</tr>
  		<tr>
  			<th valign="top">角色描述：</th>
  			<td><textarea id="description" name="description" rows="4" cols="25" style="font-size:13px" ></textarea></td>
  		</tr>
  	</table>
  </form>
  </div>

