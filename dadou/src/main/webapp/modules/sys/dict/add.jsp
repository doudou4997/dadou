<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglibs.jsp"%>
<div>
	<form id="form-data" method="post">
		<table id="shiftdata" class="table-search" width="100%"style="margin:0px 0px 10px 0px;">
		  <tr>
		  <th>显示的名称：</th>
			<td><input id="value" name="value" style="width:110px;" data-options="required:true,missingMessage:'显示的名称不能为空'"/></td>
		  </tr>
		  <tr>
		  <th>实际的值：</th>
			<td><input id="key" name="key" style="width:110px;" data-options="required:true,missingMessage:'实际的值不能为空'" /></td>
		  </tr>
		  <tr>
		  <th>值类型：</th>
			<td><input id="type" name="type" style="width:110px;" /></td>
		  </tr>
		  <tr>
		  <th>类型名称： </th>
			<td><input id="typeName-data" name="typeName" style="width:110px;" />
				选择已有类型名称<input id="typeNameList"  class="easyui-combobox" style="width:110px;height:auto" /></td>
		  </tr>
		</table>
	</form>
<script type="text/javascript">

	function doSaveAjax(){
		var key = $("#form-data #key").val();//实际的值
		var type = $("#form-data #type").val();//值类型
		
		//整个form的校验
		var valid = $('#form-data').form("validate");
		if(!valid) {
		   return;
		}
		//校验值是否存在
		var url = "${ctx}/dict/saveAjax";
		//表单提交
		$.ajax({
			url:url,
			type:'POST',
			data: { key: key,type: type},
			dataType:'json',
			success: function(data){
				if(data.success){
					//$.messager.alert('', data.msg, "info", function(){
						doSave();
					//});
				}else{getRows
					$.messager.alert('提示', data.msg, "error", function(){});
				}
			},
			error:function(info){
				$.messager.alert('提示','系统产生错误,请联系管理员!','error');		
			}
	 });
	}
	  
	  //保存模型
	 function doSave(){
		 var value = $("#form-data #value").val();//显示的名称
		 var key = $("#form-data #key").val();//实际的值
		 var type = $("#form-data #type").val();//值类型
		 var typeName = $("#form-data #typeName-data").val();//类型名称
		 //var typeNameList = $('#typeNameList').combobox('getValue'); 
		 
		    //添加日模型
			var url = "${ctx}/dict/save";
			//表单提交
			$.ajax({
				url:url,
				type:'POST',
				data: { value: value,key: key,type: type,typeName: typeName},
				dataType:'json',
				success: function(data){
					if(data.success){
						$.messager.alert('', data.msg, "info", function(){
							 $('#add-window').dialog("close");
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
		//类型下拉框
		$('#typeNameList').combobox({    
			url:'${ctx}/dict/parentAjax',
			valueField:'type',
			textField:'typeName',
			editable:false,
			onSelect:function(rec){
				$('#type').val(rec.type);//输入框赋值
				$('#typeName-data').val(rec.typeName);//输入框赋值
			}
		});
		
		//初始化easyui
		$("#form-data  #typeName-data").validatebox({
			required:true,
			missingMessage:"类型名称不能为空！"
		});
		$("#form-data  #value").validatebox({
			required:true,
			missingMessage:"显示的名称不能为空！"
		});
		$("#form-data  #key").validatebox({
			required:true,
			missingMessage:"实际的值不能为空！"
		});
		$("#form-data  #type").validatebox({
			required:true,
			missingMessage:"值类型不能为空！"
		});
	});
	
</script>
</div>