<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/comm/taglibs.jsp"%>
<script type="text/javascript">
// 自定义身份证号的验证
$.extend($.fn.validatebox.defaults.rules, {
    confirmPwd: {
    	validator: function(value, param){
    		if (value == "") {
    			this.message = "确认新密码不能为空";
    			return false;
    		}
    		var p1Val = $("#p1").val();
    		if (p1Val != value) {
    			this.message = "新密码和确认新密码不一致";
    			return false;
    		}
    		return true;
    	},
    	message: ""
    }
});

// 提交表单
function submitForm(){
	$('#chgPwd-form').form('submit', {
		url: "${ctx}/login/updatePwd",
		success:function(data){
			var data = eval('(' + data + ')');
			if(data.success == true) {
				$.messager.alert("提示","成功","info", function(){
					top.closePwdWin();
				});
			} else {
				$.messager.alert("提示", data.msg, "error");
			}
		}
	
	});
}
</script>
	<div style="padding:10px 0 0px 30px">
	    <form id="chgPwd-form" method="post">
	    	<table  cellspacing="5">
	    		<tr>
	    			<td><span style="font-size: 12px;">原密码：</span></td>
	    			<td>
	    				<input class="easyui-validatebox" type="password" size="20" name="po" style="width:149px;" value=""
	    					data-options="required:true,missingMessage:'原密码不能为空'"></input>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><span style="font-size: 12px;">新密码：</span></td>
	    			<td>
	    				<input class="easyui-validatebox" type="password" size="20" name="p1" id="p1" style="width:149px;"
	    					data-options="required:true,missingMessage:'新密码不能为空'"></input>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><span style="font-size: 12px;">确认新密码：</span></td>
	    			<td>
	    				<input class="easyui-validatebox" type="password" size="20" name="p2" id="p2" style="width:149px;"
	    					data-options="validType:'confirmPwd[5]',required:true,missingMessage:'新密码不能为空'"></input>
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	<div style="text-align:center;padding:5px">
	   	<a href="javascript:void(0)" class="btn btn-info" onclick="submitForm()"><img src="${ctx }/styles/lib/easyui/themes/icons/ok.png" align="absmiddle"/>&nbsp;修改密码</a>
	</div>
