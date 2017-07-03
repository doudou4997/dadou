<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglibs.jsp"%>
<div>
	<form id="empSaveForm" method="post">
		<table class="table-search" align="center" width="100%">
			<tr>
				<th>所属部门：</th>
				<td>
			  <select id="deptId" class="easyui-combogrid" style="width:250px" name="deptId" data-options="
			    panelWidth: 500,
			    idField: 'id',
			    textField: 'deptName',
			    url: '${ctx}/dept/search',
			    method: 'get',
			    columns: [[
				   {field:'deptName',title:'部门名称',width:120},
				   {field:'description',title:'部门描述',width:120},
			   ]],
			   fitColumns: true,required:true,missingMessage:'部门不能为空'">
	      </select>
	        </td>
				<th>职位名称：</th>
				<td><input class="easyui-validatebox" type="text" name="jobName"/></td>
			</tr>
			<tr>
				<th>用户名：</th>
				<td><input class="easyui-validatebox" type="text" name="userName" data-options="required:true,missingMessage:'用户名不能为空'" /> 
                    &nbsp;<span style="color:#FF0000;">*</span></td>
				<th>密码：</th>
				<td><input class="easyui-validatebox" type="password" name="password" style="width:127px;" value="123456" data-options="required:true,missingMessage:'密码不能为空'"></input>
					&nbsp;<span style="color:#FF0000;">*</span> (默认：123456)</td>
			</tr>
			<tr>
				<th>姓名：</th>
				<td><input class="easyui-validatebox" type="text" name="name" data-options="required:true,missingMessage:'姓名不能为空'"/>
					&nbsp;<span style="color:#FF0000;">*</span></td>
				<th>性别：</th>
				<td><input type="radio" name="gender" checked="checked" value="男"/>男 <input type="radio" name="gender" value="女"/>女
				</td>
			</tr>
			<tr>
				<th>员工编号：</th>
				<td><input id="addUserCode" class="easyui-validatebox" style="width:70px;" type="text" name="userCode" maxLength="16" data-options="required:true,missingMessage:'员工编号不能为空'"/>
					&nbsp;<span style="color:#FF0000;">*</span>
					<a href="javascript:void(0)" id="addValidateCodeBtn" class="easyui-linkbutton" iconCls="icon-plugin">验证</a></td>
				<th>手机：</th>
				<td><input class="easyui-numberbox" type="text" name="mobile"
					data-options="required:false" maxLength="20"></input></td>
			</tr>
			<tr>
				<th>电子邮件：</th>
				<td colspan="3"><input class="easyui-validatebox" type="text" name="email" data-options="required:false,validType:'email',invalidMessage:'请输入一个格式正确的邮件地址'"/>
				</td>
			</tr>
			<tr>
				<th>备注：</th>
				<td colspan="3"><textarea name="remark" style="width:250px;height:60px;"></textarea></td>
			</tr>
		</table>
	</form>
</div>
<script type="text/javascript">
function doInsert(){
	var empSaveForm = $('#empSaveForm');
	//整个form的校验
	var valid = empSaveForm.form("validate");
	if(!valid) {
		$.messager.alert('校验失败',"请选择必选项",'info');
	   return;
	}
	var url = "${ctx}/emp/save";
	//表单提交
	$.ajax({
		url:url,
		type:'POST',
		data:$("#empSaveForm").serialize(),
		dataType:'json',
		success: function(data){
			$.messager.alert('',data.msg,'info',function(){
				$("#empList-addEmpWin").dialog("close");
				$('#dg').datagrid('reload');
			});
		},
		error:function(info){
			$.messager.alert('提示','系统产生错误,请联系管理员!','error');		
		}
		
	});
}// end doInsert

$(function(){
	$("#addValidateCodeBtn").bind("click", function(){
		var userCode = $("#addUserCode").val();
		if ("" == userCode) {
			$.messager.alert("提示","员工号不能为空","error");
			return;
		}
		$.ajax({
			url: "${ctx}/emp/validateCode",
			type: "POST",
			data: {userCode: userCode},
			async: false,
			dataType: "json",
			success: function(data){
				var success = data.success;
				if (success) {
					$.messager.alert("提示","可以使用此员工号","info");
				} else {
					$.messager.alert("提示","您不能使用此员工号","error");
				}
			},
			error: function(){
				$.messager.alert("提示","操作超时，请重新登录!","error");
			}
		});
	});
});
</script>
