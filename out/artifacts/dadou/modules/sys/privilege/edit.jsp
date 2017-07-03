<%@ page language="java" pageEncoding="utf-8"%>
<%@include file="/comm/taglibs.jsp"%>
<script type="text/javascript">
	function doEditPrivilege(){
		//整个form的校验
		var valid = $("#privilegeEditForm").form("validate");
		if(!valid) {
		   return;
		}
	    //privilegeEditForm.action;
		//表单提交
		var url = "${ctx}/privilege/update";
		$.ajax({
			url:url,
			type:'POST',
			data:$("#privilegeEditForm").serialize(),
			dataType:'json',
			success: function(data){
				if(data.success){
				     $.messager.alert('提示', data.msg, 'info',function(r){
			            //重新加载树形结构
			            window.location.reload();
				     });
				 }else{
					 $.messager.confirm('提示', data.msg, 'error');
				 }
			},
			error:function(info){
				$.messager.alert('提示','系统产生错误,请联系管理员!','error');
			}
		});
	}

	$(function() {
		//easyui validatebox
		$("#privilegeEditForm  input[name=name],#privilegeEditForm  input[name=operate],#privilegeEditForm  input[name=url]").validatebox({
			required:true,
			missingMessage:"必须填写！"
		});
		$("#privilegeEditForm input[name=sortNum]").validatebox({
			validType:'number'
		});
	})	
</script>
	<div style="padding:10px">
    <form name="privilegeEditForm" id="privilegeEditForm" method="post" cssStyle="margin:0px;padding:0px" action="/privilege/update">
    <input type="hidden" name="parentId" value="${privilege.parentId}"/>
    <input type="hidden" name="id" value="${privilege.id}"/>
    	<table class="table-search">
    		<%--
    		<tr>
    			<th class="small-font">权限Id:</th>
    			<td align="left"><input type="text" name="id" size="20" readonly="true" value="${privilege.id }"/></td>
    		</tr>
    		 --%>
    		<tr>
    			<th class="small-font">权限名称:</th>
    			<td align="left"><input type="text" name="name" size="20" value="${privilege.name }"/><span style="color:red">&nbsp;*</span></td>
    		</tr>
    		<tr>
    			<th class="small-font">权限顺序:</th>
    			<td align="left"><input type="text" name="sortNum" size="20" value="${privilege.sortNum }"/></td>
    		</tr>
    		<%--
    		<tr>
    			<th class="small-font">操作:</th>
    			<td align="left"><input type="text" name="operate" size="20" value="${privilege.operate }"/><span style="color:red">&nbsp;*</span></td>
    		</tr>
    		 --%>
    		<tr>
    			<th class="small-font">URL:</th>
    			<td align="left"><input type="text" name="url" size="40" value="${privilege.url }"/><span style="color:red">&nbsp;*</span></td>
    		</tr>
    		<tr>
    			<th class="small-font">权限描述:</th>
    			<td align="left">
    				<input type="text" name="description" size="40"  value="${privilege.description }"/>
    			</td>
    		</tr>
    	</table>
      </form>
    </div>
