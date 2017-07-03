<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@include file="/comm/taglibs.jsp"%>
<script language="javascript">
function toPage(){
	var obj=document.getElementById("menu");
	//打开地址
	var path= "";
	if(obj.checked){
	    path="${ctx}/menu/add?parentId=${param.pid}";
	    //发送一次请求
	    $('#dlg').dialog({href:path}).dialog("open");
     }else{
	    path="${ctx}/privilege/add?parentId=${param.pid}";
	    $('#dlg-pri').dialog({href:path}).dialog("open");
	}
}
</script>
<div style="margin:0 auto;display: block;width:100%;">
	<div style="padding:5px 20px 0px 20px;;text-align: center">
		<form id="sub" name="sub" method="POST">
			<table>
				<tr>
					<td colspan="2"><span style="font-size:13px;font-weight: 900">请选择类型：</span></td>
				</tr>
				<tr>
					<td><input class="forCheck" type="radio" name="R1" id="menu"
						checked> <span style="font-size:13px;">菜单</span></td>
					<td><input class="forCheck" type="radio" name="R1"
						id="privilege"> <span style="font-size:13px;">权限</span></td>
				</tr>
			</table>
		</form>
	</div>
</div>