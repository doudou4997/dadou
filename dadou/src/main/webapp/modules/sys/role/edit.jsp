<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglibs.jsp"%>
<script type="text/javascript">
//角色保存
function doUpdate(){
	//整个form的校验
	var valid = $('#roleEditForm').form("validate");
	if(!valid) {
	   return;
	}
	 // JQuery采用ajax方式保存
     var url = "${ctx}/role/update";
     $('#roleEditForm #sysList').attr("disabled",false);
	 $.ajax({
	     url:url,
	     type:'POST',
		 dataType:'json',
	     data:$("#roleEditForm").serialize(),			     
		 success: function(data){
		 		$.messager.alert('提示',data.msg);	
				 if(data.success==true){
					searchx();
					$('#dlg-window').dialog('close');	
                }
		 },
		 error:function(info,er){
		     $.messager.alert('提示','系统产生错误,请联系管理员!','error');	
   	     }
     });
}
$(function() {
	//初始化easyui
	$("#roleEditForm  #sysList").validatebox({
		validType:'sysBlong'
	});
	$("#roleEditForm  #roleName").validatebox({
		required:true,
		missingMessage:"必须填写！"
	});
})
      
</script>
<div style="padding:10px;">
   <form action="/role/update" method="post" name="roleEditForm" id="roleEditForm">
     <input type="hidden" id="id" name="id" value="${role.id}">
  	<table class="table-search" align="center"  width="100%">
  		<tr>
  			<th>角色名称：</th>
  			<td>
  			   <input name="roleName" id="roleName" size="35" value="${role.roleName }"/>
  			<span style="color:red">&nbsp;*</span></td>
  		</tr>
  		<tr>
  			<th>所属系统：</th>
  			<td id="sys">
  			 <form:select id="sysList" path="role.sysId" items="${sysList}" itemValue="id" itemLabel="name"/>
  			<span style="color:red">&nbsp;*</span></td>
  		</tr>
  		<tr>
  			<th valign="top">角色描述：</th>
  			<td>
  			  <textarea name="description" rows="6" cols="35" style="font-size:14px">${role.description }</textarea>
  			</td>
  		</tr>
  	</table>
  </form>
  </div>

