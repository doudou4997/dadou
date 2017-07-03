<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglibs.jsp"%>
<div>
	<form id="empSaveForm" method="post">
	    <input type="hidden"  id="empSaveForm_method" value="add"/>
	    <input type="hidden"  id="empSaveForm_roleIds" name="roleIds"/>
	    <input type="hidden"  id="empSaveForm_siteIds" name="siteIds"/>
		<table class="table-search" align="center" width="100%">
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
				<td><input id="addUserCode" class="easyui-validatebox" style="width:70px;" type="text" name="userCode" maxLength="16"  onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" data-options="required:true,missingMessage:'员工编号必须是数字且不能为空'"/>
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
				<td colspan="3"><textarea name="remark" style="width:250px;height:40px;"></textarea></td>
			</tr>
			<tr>
				<th>角色分配：</th>
				<td colspan="3">
				     <input type="button" id="assignRole"  class="btn btn-success" value="分配角色"/> 
				     &nbsp;&nbsp;&nbsp;
				</td>
			</tr>
		</table>
	</form>
</div>
<script type="text/javascript">
//分配的角色窗口是否打开过,不需要再次打开
var roleOpen = 0;
//分配工厂的窗口是否打开过,如果已经打开过不需要再打开
var siteOpen = 0;


function doInsert(){

	var empSaveForm = $('#empSaveForm');
	//整个form的校验
	var valid = empSaveForm.form("validate");
	if(!valid) {
		$.messager.alert('校验失败',"请填写必选项！",'info');
	   return;
	}
	//分配角色校验
	//角色校验
	var roleIds = $('#empSaveForm_roleIds').val();
	if($.trim(roleIds) == ''){
		$.messager.alert('校验失败',"请为用户分配角色",'info');
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
			if(data.success){
				$.messager.alert('',data.msg,'info',function(){
					$("#empList-addEmpWin").dialog("close");
					$('#dg').datagrid('reload');
				});
			}else{
				$.messager.alert('提示',data.msg,'info');
			}
		},
		error:function(info){
			$.messager.alert('提示','系统产生错误,请联系管理员!','error');		
		}
		
	});
}// end doInsert

$(function(){
	//分配角色
	$("#assignRole").bind("click",function(){
		if(roleOpen == 0){
		  var path="${ctx}/emp/assignRoleAjax?r=" + Math.random();
		  $('#empList-asnRoleWin').dialog({href:path}).dialog("open");
		  roleOpen = 1;
		}else{
			 $('#empList-asnRoleWin').dialog({width: 700,height:480,href:''}).dialog("open");
		}
	});

	//1.校验员工编号是否为空，2.校验员工编号是否已存在
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
