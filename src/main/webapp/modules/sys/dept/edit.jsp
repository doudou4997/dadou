<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@include file="/comm/taglibs.jsp"%>
<script type="text/javascript">
function doEdit(){
    //输入校验
	if ($.trim($("#menuForm #deptName").val())==""){
		$.messager.alert('提示','请输入部门名称！','error');
		return;
	}
	
	var url = "${ctx}/dept/update";
	//表单提交
	$.ajax({
		url:url,
		type:'POST',
		dataType:'json',
		data:$("#menuForm").serialize(),
		success: function(data){
			if(data.success){
				$.messager.alert('', data.msg, "info", function(){
					 window.location.reload();
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
</script>
<div class="easyui-panel" style="width: auto;">
	<div style="padding: 10px 0 10px 60px;">
		<form name="menuForm" id="menuForm" method="post">
			<table>
				<tr>
					<td><span style="font-size: 12px;">部门名称：</span></td>
					<td><input id="deptName" name="deptName" size="15" maxlength="15" value="${dept.deptName }"/></td>
				</tr>
				<tr>
					<td><span style="font-size: 12px;">部门描述：</span></td>
					<td><input id="description" name="description" size="15" maxlength="15" value="${dept.description }"/></td>
				</tr>
				<input type="hidden" name="sortNum" value="${dept.sortNum }"/>
				<input type="hidden" name="parentId" value="${dept.parentId }"/>
				<input type="hidden" name="id" value="${dept.id }"/>
			</table>
		</form>
	</div>
</div>