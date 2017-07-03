<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/comm/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>锁定窗口</title>
<%@include file="/comm/base.jsp"%>
<script type="text/javascript">
// 提交表单
function submitForm(){
	$('#lock-form').form('submit', {
		url: "${ctx}/system/login.action?method=unlock",
		success:function(data){
			var data = eval('(' + data + ')');
			if(data.success == true) {
				$.messager.alert("提示","成功","info", function(){
					$('#empList-addEmpWin').dialog("close");
				});
			} else {
				$.messager.alert("提示","失败","error");
			}
		}
	
	});
}
</script>
</head>

<body>
	<div style="padding:10px 0 0px 30px">
	    <form id="lock-form" method="post">
	    	<table cellspacing="5">
	    		<tr>
	    			<td><span style="font-size: 12px;">员工号：</span></td>
	    			<td>
	    				<input class="easyui-validatebox" type="text" size="20" name="userName" style="width:149px;" value="${emp.userCode }"
	    					data-options="required:true,missingMessage:'员工号不能为空'"></input>	
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><span style="font-size: 12px;">密码：</span></td>
	    			<td>
	    				<input class="easyui-validatebox" type="password" size="20" name="password" style="width:149px;" value=""
	    					data-options="required:true,missingMessage:'密码不能为空'"></input>
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	<div style="text-align:center;padding:5px">
	   	<a href="javascript:void(0)" class="btn btn-info" onclick="submitForm()"><img src="${ctx }/styles/lib/easyui/themes/icons/ok.png" align="absmiddle"/>&nbsp;登录</a>
	</div>
</body>
</html>
