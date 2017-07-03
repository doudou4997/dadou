<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglibs.jsp"%>
	<div>
	    <form id="empEditForm" method="post" >
	    	<input type="hidden" name="oldDeptId" value="${emp.oldDeptId }" />
	    	<input type="hidden" name="id" value="${emp.id}">
	    	<table class="table-search" align="center"  width="100%">
	    		<tr>
	    			<th>用户名：</th>
	    			<td>
	    			    <input name="userName" size="20" readonly="true" value="${emp.userName }"/>
	    			</td>
	    			<th>姓名：</th>
	    			<td>
	    			    <input name="name" size="20"  value="${emp.name }" data-options="required:true" />
	    			</td>
	    		</tr>
	    		<tr>
	    		    <th>员工编号：</th>
	    			<td> <input name="userCode" size="20" readonly="true" value="${emp.userCode }" disabled/>
	    			</td>
	    			<th>性别：</th>
	    			<td>
	    				<input type="radio" name="gender" value="男" ${emp.gender eq "男" ? "checked='checked'" : "" }></input>男
	    				<input type="radio" name="gender" value="女" ${emp.gender eq "女" ? "checked='checked'" : "" }></input>女
	    			</td>
	    		</tr>
	    		<tr>
	    		    <th>手机：</th>
	    			<td><input class="easyui-numberbox" type="text" name="mobile" data-options="required:false" value="${emp.mobile}"/></td>
	    			<th>电子邮件：</th>
	    			<td>
	    				<input class="easyui-validatebox" type="text" name="email" data-options="required:false,validType:'email'" value="${emp.email}"/>
	    			</td>
	    		</tr>
	    		<tr>
	    			<th>备注：</th>
	    			<td colspan="3">
						<textarea name="remark" style="width:200px;height:60px;">${emp.remark }</textarea>
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	</div>
<script>
function doUpdate(){
	var empEditForm = $('#empEditForm');
	//整个form的校验
	var valid = empEditForm.form("validate");
	if(!valid) {
		$.messager.alert('校验失败',"请选择必选项",'info');
	   return;
	}
	var url = "${ctx}/emp/update";
	//表单提交
	$.ajax({
		url:url,
		type:'POST',
		data:$("#empEditForm").serialize(),
		dataType:'json',
		success: function(data){
			$.messager.alert('提示',data.msg,'info',function(){
				$("#empList-addEmpWin").dialog("close");
				$('#dg').datagrid('reload');
			});
		},
		error:function(info){
			$.messager.alert('提示','系统产生错误,请联系管理员!','error');		
		}
	});
}// end doUpdate
</script>
</body>
</html>
