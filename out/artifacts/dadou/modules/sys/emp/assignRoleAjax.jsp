<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/comm/taglibs.jsp"%>
<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<script type="text/javascript">
function getRoleIds(){
	 var rselect = document.getElementById("right_select");
	 //构造角色ids,分隔符为';'
	 var roleIds = "";
	 for(i = 0;i < rselect.length; i++){
	    if(i > 0 && i< rselect.length){
	      roleIds+=";";
	    }
	    roleIds +=rselect.options[i].value;
	 }
     return roleIds;
}
//为用户分配多个角色
function doAssign(){
 var rselect = document.getElementById("right_select");
 //构造角色ids,分隔符为';'
 var roleIds = "";
 for(i = 0;i < rselect.length; i++){
    if(i > 0 && i< rselect.length){
      roleIds+=";";
    }
    roleIds +=rselect.options[i].value;
 }

 $('#roleIds').attr('value',roleIds);
 //利用Ajax赋值
 var url = "${ctx}/emp/doAssign";
 $.ajax({
 	url:url,
 	data:$("#assignForm").serialize(),
 	type:'POST',
 	dataType:'json',
 	success:function(data) {
 		if(data.success = true) {
 			$.messager.alert("", data.msg,"info",function(){
 				$("#empList-asnRoleWin").dialog('close');
 			});
 		} else {
 			$.messager.alert("", data.msg,"error", function(){
 				$("#empList-asnRoleWin").dialog('close');
 			});
 		}
 	},
 	error:function(info) {
 		$.messager.alert("提示", "系统产生错误，请联系管理员", "error");
 	}
 });
}
//选择右侧所有对象
function selectAll(){	
	var rselect = document.getElementById("right_select");
	for(i=0; i<rselect.length; i++){
	  rselect.options[i].selected = true;  
	}
}
//克隆Option选项
function cloneOption(option){
	//创建Option对象
	var out = new Option(option.text,option.value);
	//选择状态赋值
	out.selected = option.selected;
	//默认的选择状态赋值
	out.defaultSelected = option.defaultSelected;
	return out;
}
/****************************************************
函数名称:moveSelected
功能:   把选项对象从源移动到目的地
输入参数:源对象名称,目的对象名称
****************************************************/ 
function moveSelected(fromName,toName){
    var from = document.getElementById(fromName);
    var to   = document.getElementById(toName);
	//创建存放目的对象的数组
	newTo = new Array();
	for(i=0; i<from.options.length; i++){
		//是否结束本次循环的标识
		var continueflag = 0;
		//如果选中且目的地有此对象,则不拷贝
		if (from.options[i].selected){	
			//拷贝对象到新数组
			newTo[newTo.length] = cloneOption(from.options[i]);
			from.options[i] = null;
			i--;
		}
	}	
	for(i=0; i<to.options.length; i++) {
		newTo[newTo.length] = cloneOption(to.options[i]);
		newTo[newTo.length-1].selected = false;
	}
	
   //根据内容进行排序
   newTo.sort(function(x,y){
		if(x.text>y.text){
		return 1;
		}else{
		return -1;
		}})	;
	to.options.length = 0;
	for(i=0; i<newTo.length; i++){
		to.options[to.options.length] = newTo[i];
	}
  }
//把对象由原地移动到目的地
function move(fromName,toName){
    moveSelected(fromName,toName);
}
</script>
<form method="post" id="assignForm" name="assignForm">
    <input type="hidden" name="id" id="empId" value="${emp.id }"/>
    <input type="hidden" id="roleIds" name="roleIds"/>
	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td valign="top" style="padding-top: 8px;">
				<table width="100%" border="0" align="left" cellpadding="0"
					cellspacing="0">
					<tr>
						<td width="100%" valign="top">
							<div align="center">
								<table width="100%" border="0" cellpadding="6" cellspacing="1">
									<tr>
										<td colspan="3">
											<div align="left" style="height:20px;padding:3px; border-radius:5px; border:1px solid #ccc; background:#f2f2f2;">
												<b>为用户&nbsp;&nbsp;<span style="color:red;">${emp.userName }</span>&nbsp;&nbsp;分配角色:</b>
											</div>
										</td>
									</tr>
								</table>
								<table width="100%" border="0" cellpadding="6" cellspacing="1"
									id="content">
									<tr>
										<td colspan="5">
											<table cellSpacing=0 cellPadding=0 border=0 width="100%">
												<tr>
													<td>
														<div align="center">
															<table align='center' >
																<tr>
																	<td width='40%' align='center' bgcolor="#C0C0C0" height="20">
																		未分配角色
																	</td>
																	<td>
																	</td>
																	<td width='40%' align='center' bgcolor="#56d9ff;" height="20">
																		<span style="color:white">已分配角色</span>
																	</td>
																</tr>
																<tr>
																	<td width='45%' align='center'>
																	    <form:select id="left_select" path="unAssignList" items="${unAssignList }" ondblclick="move('left_select','right_select');" itemLabel="roleName" itemValue="id" cssStyle="HEIGHT: 300px; WIDTH: 250px" multiple="true"/>
																	</td>
																	<td width='10%' align='center'>
																		<img name='btn_select_addany' id="btn_select_addany"
																			src='${ctx}/styles/images/arrowRight_clicked.gif'
																			onClick="moveSelected('left_select','right_select');">
																		<br>
																		<br>
																		<img name='btn_select_dltany' id="btn_select_dltany"
																			src='${ctx}/styles/images/arrowLeft_clicked.gif'
																			onClick="moveSelected('right_select','left_select');"">
																	</td>
																	<td width='45%' align='center'>
																	   <form:select id="right_select" path="assignedList"  items="${assignedList }" ondblclick="move('right_select','left_select');" itemLabel="roleName" itemValue="id" cssStyle="HEIGHT: 300px; WIDTH: 250px" multiple="true"/>
																	</td>
																</tr>
															</table>
														</div>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</td>
	</table>
</form>