<%@ page language="java" pageEncoding="utf-8"%>
<%@include file="/comm/taglibs.jsp"%>
<script language=javascript>
// 把messager默认的ok按钮和cancel按钮里的文字改成中文
$.messager.defaults.ok = "确定";
$.messager.defaults.cancel = "取消";

function doInsert(){
	var menuFormSave = $('#menuFormSave');
    //输入校验
	if ($.trim($("#deptName").val())==""){
		$.messager.alert('提示','请输入部门名称！','error');
		return;
	}
	var url = "${ctx}/dept/save";
	//表单提交
	$.ajax({
		url:url,
		type:'POST',
		data:$("#menuFormSave").serialize(),
		dataType:'json',
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
function enter(){
	if(window.event.keyCode == 13){
		event.returnValue=false;
	}
}
</script>
<div class="easyui-panel" style="width: auto;">
		<div style="padding:10px 0 10px 60px;height:100px;">
	    <form name="menuFormSave" id="menuFormSave" method="post">
	    	<table>
	    		<tr>
	    			<td><span style="font-size: 12px;">部门名称:</span></td>
	    			<td>
	    				<input class="easyui-validatebox" type="text" id="deptName" name="deptName" size="15" maxLength="15"
	    					data-options="required:true,missingMessage:'部门名称不能为空'"></input>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><span style="font-size: 12px;">部门描述:</span></td>
	    			<td><input name="description" size="15" maxlength="15""/></td>
	    		</tr>
	    	</table>
	    	<input name="parentId" value="${parentId }" type="hidden"/> 
	    </form>
	    </div>
	</div>