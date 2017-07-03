<%@ page language="java" pageEncoding="utf-8"%>
<%@include file="/comm/taglibs.jsp"%>
		<script type="text/javascript">
	function doInsertPrivilege(){
		//整个form的校验
		var valid = $("#privilegeSaveForm").form("validate");
		if(!valid) {
		   return;
		}

		//表单提交privilegeSaveForm.action
		var url = "${ctx}/privilege/save";
		
		$.ajax({
			url:url,
			type:'POST',
			data:$("#privilegeSaveForm").serialize(),
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
		$("#privilegeSaveForm  input[name=name],#privilegeSaveForm  input[name=operate],#privilegeSaveForm  input[name=url]").validatebox({
			required:true,
			missingMessage:"必须填写！"
		});
		$("#privilegeSaveForm input[name=sortNum]").validatebox({
			validType:'number'
		});
	})	
	</script>
		<div style="padding:10px">
	    <form name="privilegeSaveForm" id="privilegeSaveForm" cssStyle="margin:0px;padding:0px" method="post" action="/privilege/save">
	       <input type="hidden" name="parentId" value="${parentId}"/>
	    	<table class="table-search">
	    		<tr>
	    			<th class="small-font">权限名称:</th>
	    			<td align="left"><input type="text" name="name" size="20"/><span style="color:red">&nbsp;*</span></td>
	    		</tr>
	    		<tr>
	    			<th class="small-font">权限顺序:</th>
	    			<td align="left"><input type="text" name="sortNum" size="20"/></td>
	    		</tr>
	    		<!-- 
	    		<tr>
	    			<th class="small-font">操作:</th>
	    			<td align="left"><input type="text" name="operate" size="20"/><span style="color:red">&nbsp;*</span></td>
	    		</tr>
	    		 -->
	    		<tr>
	    			<th class="small-font">URL:</th>
	    			<td align="left"><input type="text" name="url" size="40"/><span style="color:red">&nbsp;*</span></td>
	    		</tr>
	    		<tr>
	    			<th class="small-font">权限描述:</th>
	    			<td align="left">
	    				<input type="text" name="description" size="40"/>
	    			</td>
	    		</tr>
	    	</table>
	      </form>
	    </div>
