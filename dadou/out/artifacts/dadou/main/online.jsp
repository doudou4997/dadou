<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.dadou.sys.emp.pojos.Employee"%>
<%@page import="com.dadou.sys.login.LoginManager"%>
<%@include file="/comm/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>用户退出</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link type="text/css" href="${ctx}/styles/css/bootstrap.css" rel="stylesheet" />
		<script src="${ctx}/styles/js/comm/jquery/jquery.js" type="text/javascript"></script>
		<script src="${ctx}/styles/js/comm/bootstrap/bootstrap.js"></script>
		
		<script type="text/javascript">
		   	 $(function(){
		      $('#tbdy tr:odd').addClass('odd');
		      $('#tbdy tr:even').addClass('even'); 
		      
		      $("#userTypeBtns").children().bind("click", function(){
		    	  var userType = $(this).attr("userType");
		    	  $.each($("#tbdy tr"), function(i,val){
		    		  var tr = $(val);
		    		  if (userType == "0" || tr.attr("userType") == userType)
		    			  tr.show();
		    		  else
		    			  tr.hide();
		    	  });  
		      });
		      
		   	  }
		   	 );
		   function forceQuit(userId){
		     	  var url = "${ctx}/user/login.action?method=forceQuit";
		   			 $.ajax({
						url:url,
						data:{userId:userId},
						type:'POST',
						dataType:'json',
						success: function(data){
							 if(data.flag){
							    alert('强制退出成功!');
							 }
							$('#forceLogoffSpan'+userId).html('强制退出成功'); 
						},
						error:function(info){
							alert("系统产生错误,请联系管理员!");
						}
					});		     	  
		     	  
		    }
		</script>
		<style type="text/css">
/* 地址导航 */
div.address-nevigation {
	padding-top: 10px;
	margin-bottom: 10px;
	font-size: 12px;
	color: #278bdf;
}

div.address-nevigation .content-icon {
	margin-top: 2px;
	margin-left: 5px;
	height: 16px;
}

input {
	font-family: Arial, Helvetica, sans-serif;
	color: #1c1c1c;
	font-size: 12px;
}

.content-icon span {
	margin-left: 18px;
}

.content-icon span.split-right {
	margin-left: 5px;
	margin-right: 5px;
	font-family: 宋体;
	font-size: 11px;
}

span.split-right {
	margin-left: 5px;
	margin-right: 5px;
	font-family: 宋体;
	font-size: 11px;
}

/* 数据表格 */
.default-data-grid {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	color: #1c1c1c;
	min-width: 740px;
	vertical-align: middle;
	margin-left: 5px;
	margin-right: 5px;
}

.default-data-grid th,.default-data-grid td {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	color: #1c1c1c;
	vertical-align: middle;
	padding-left: 3px;
}

.default-data-grid th {
	text-align: center;
}

.default-data-grid td {
	height: 28px;
	padding-left: 3px;
}

.default-data-grid tr:hover td {
	background: #accbe9;
	cursor: default;
}

/*偶数行*/
.default-data-grid .even {
	background: #E3EFFB;
}

/*奇数行*/
.default-data-grid .odd {
	background: #FFFFFF;
}
</style>
	</head>
	<body>
		<!-- 地址导航部分 -->
		<div class="address-nevigation">
			<div class="content-icon">
				<span class="address">在线考试系统 <span class="split-right">&gt;&gt;&gt;</span>
					在线用户(<%=LoginManager.getOnlineNum()%>人)</span>
			</div>
		</div>
		<!-- 头部开始-->
		<br />
		<div id="userTypeBtns" class="btn-group " data-toggle="buttons-radio" style="left:32px">
			<button userType="0" class="btn active">全部 (<%=LoginManager.getOnlineNum() %>)</button>
		</div>
		<hr />
		<div align="center">
			<table width="95%" border="0" align="Center" cellpadding="0"
				cellspacing="1" bgcolor="#a9a9a9" id="gvOnLineUser"
				class="default-data-grid">
				<thead>
					<tr align="center" bgcolor="#e6e6e6" height="26">
						<th scope="col">
							编号
						</th>
						<th scope="col">
							用户名
						</th>
						<th scope="col">
							姓名
						</th>
						<th scope="col">
							登陆时间
						</th>
						<th scope="col">
							IP
						</th>
						<th scope="col">
							&nbsp;
						</th>
					</tr>
				</thead>
				<tbody id="tbdy">
					<%
					    int index = 0;
					    for (Employee u : LoginManager.getLoggedinUsers()) {
					        index++;
					        pageContext.setAttribute("u", u);
					%>
					<tr align="center" bgcolor="#ffffff" height="26">
						<td style="width: 5%;">
							<%=index%>
						</td>
						<td style="width: 10%;">
							${u.userName}
						</td>
						<td style="width: 10%;">
							${u.name}
						</td>
						<td style="width: 5%;">
							员工
						</td>
						<td style="width: 15%;">
							<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${u.lastLoginTime }"/>
						</td>
						<td style="width: 15%;">
							${u.lastLoginIp }
						</td>
						<td>
							<span id="forceLogoffSpan${u.id }"> <input type="button"
									id="a_forceExit" onclick="forceQuit('${u.id}')" value="强制退出" />
							</span>
						</td>
					</tr>
					<%
					    }
					%>
				</tbody>
			</table>
		</div>
	</body>
</html>
