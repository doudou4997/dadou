<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglibs.jsp"%>
<div>
	<form id="form-data" method="post">
		<table id="dictdata" class="table-search" width="100%"style="margin:0px 0px 10px 0px;">
		  <tr>
		  <th>显示的名称：</th>
			<td>
			<input type="hidden" name="id" value="${dict.id }">
			<input value="${dict.value }" id="editvalue" name="value" style="width:110px;" /></td>
		  </tr>
		  <tr>
		  <th>实际的值：</th>
			<td><input value="${dict.key }" id="editkey" name="key" style="width:110px;" /></td>
		  </tr>
		  <tr>
		  <th>值类型： </th>
			<td><input value="${dict.type }" style="width:110px;" disabled/>
				<input type="hidden" value="${dict.type }" id="edittype" name="type"/></td>
		  </tr>
		  <tr>
		  <th>类型名称：</th>
			<td><input value="${dict.typeName }" style="width:110px;" disabled/>
			<input type="hidden" value="${dict.typeName }" id="editTypeName" name="typeName"/></td>
		  </tr>
		</table>
	</form>
<script type="text/javascript">
	  
	  //保存模型
	 function doUpdate(){
		//整个form的校验
			var valid = $('#form-data').form("validate");
			if(!valid) {
			   return;
			}
		    //修改数据字典
			var url = "${ctx}/dict/update";
			//表单提交
			$.ajax({
				url:url,
				type:'POST',
				data:$("#form-data").serialize(),
				dataType:'json',
				success: function(data){
					if(data.success){
						$.messager.alert('', data.msg, "info", function(){
							 $('#edit-window').dialog("close");
							 $('#data-table').treegrid('reload');
						});
					}else{
						$.messager.alert('提示', data.msg, "error", function(){});
					}
				},
				error:function(info){
					$.messager.alert('提示','系统产生错误,请联系管理员!','error');		
				}
		 });
	  }
	 $(function(){
		//初始化easyui
		$("#form-data  #editTypeName").validatebox({
			required:true,
			missingMessage:"类型名称不能为空！"
		});
		$("#form-data  #editvalue").validatebox({
			required:true,
			missingMessage:"显示的名称不能为空！"
		});
		$("#form-data  #editkey").validatebox({
			required:true,
			missingMessage:"实际的值不能为空！"
		});
		$("#form-data  #edittype").validatebox({
			required:true,
			missingMessage:"值类型不能为空！"
		});
	 });
	
	
</script>
</div>