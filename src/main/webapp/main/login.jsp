<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/comm/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>运营平台<%=request.getLocalAddr() %></title>
<style type="text/css">
body {
	margin: 0px auto;
	background: #d6dee0;
}

.login_bg {
	background: url(${ctx}/styles/images/login/login_bg.jpg) repeat-x;
	height: 580px;
	width: 100%;
}

.login {
	background: url(${ctx}/styles/images/login/login.jpg) no-repeat;
	width: 1186px;
	height: 580px;
	margin: 0 auto;
}

.login_text {
	padding: 265px 0px 0px 440px;
	font-size: 14px;
	color: #505050;
}

input {
	height: 23px;
	padding: 3px;
	border: 1px solid #c9c9c9;
	margin-bottom: 13px;
	line-height: 29px;
}

.span1 {
	width: 200px;
}

.login_button {
	background: url(${ctx}/styles/images/login/login_off.jpg) no-repeat;
	width: 124px;
	height: 43px;
	margin-left: 75px;
	margin-top: 5px;
}

.login_button:hover {
	background: url(${ctx}/styles/images/login/login_on.jpg) no-repeat;
	cursor: pointer;
}

.login_foot {
	font-size: 12px;
	color: #989797;
	text-align: center;
	font-family: Verdana, Geneva, sans-serif;
	margin-top: 100px;
	width: 1186px;
}

.clear {
	clear: both;
}

* {
	padding: 0px;
	margin: 0px;
}
</style>

<script type="text/javascript" src="${ctx}/styles/lib/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/styles/js/comm/common.js"></script>
<script type="text/javascript" src="${ctx}/styles/js/comm/login.textlimit.js"></script>
<script type="text/javascript">
	function checkCode() {
		var url = "${ctx}/login/isValidCode";
		$.ajax({
			url : url,
			data : {
				validateCode : $("#txtValidateCode").val()
			},
			type : 'POST',
			dataType : 'json',
			success : function(data) {
				var flag = data.success;
				if (typeof success == "boolean" && true == success) {
					$('#tipImg').attr('src', '${ctx}/styles/images/right.png');
					$('#tipImg').show();
				} else {
					$('#tipImg').attr('src', '${ctx}/styles/images/wrong.png');
					$('#tipImg').show();
				}
			}
		});
	}

	//如果出现错误信息,则提示
	$(function() {
		var msg = '${msg}';
		if ($.trim(msg) != "") {
			$("#error_msg").show();
		} else {
			$("#error_msg").hide();
		}
		//刷新界面时
		$("#txtUserName").val("请输入员工号").css("color", "#999");
		$("#txtPwdInfo").val("请输入密码").css("color", "#999");
		//验证员工号
		$("#txtUserName").focus(function() {
			if ($(this).val() == "请输入员工号") {
				$(this).val("").css("color", "#000");
			}
		}).blur(function() {
			if ($.trim($(this).val()) == "") {
				$(this).val("请输入员工号").css("color", "#999");
			}
		});
		//密码检查
		$("#txtPassword").blur(function() {
			if ($.trim($(this).val()) == "") {
				$(this).hide();
				$("#txtPwdInfo").css("color", "#999").show();
			}
		});
		//显示输入密码
		$("#txtPwdInfo").focus(function() {
			$(this).hide();
			$("#txtPassword").show().focus();
		});

		$("#loginImg").click(function() {
			var userName = $.trim($("#txtUserName").val());
			if (userName == "" || userName == "请输入员工号") {
				$("#txtUserName").focus();
				return;
			}
			var txtPassword = $("#txtPassword").val();
			if ($.trim(txtPassword) == "") {
				$("#txtPwdInfo").focus();
				return;
			} else {
				$("#txtPwdInfo").hide();
				$("#txtPassword").show();
			}
			var url = "${ctx}/login/loginAjax";
			//登录
			$.ajax({
				url : url,
				data : $("#loginForm").serialize(),
				type : 'POST',
				dataType : 'json',
				success : function(data) {
					var flag = data.success;
					if (typeof flag == "boolean" && true == flag) {
						//跳转到主页面
						location.href = "${ctx}/login/index";
					} else {
						alert(data.msg);
					}
				}
			});
		});
		$('#cancelImg').click(function() {
			$('input[name=userName]').attr('value', '');
			$('input[name=password]').attr('value', '');
		});
		$('#txtValidateCode').textlimit(4, checkCode, -1);
	});

	//处理回车键
	$(window).keydown(function(event) {
		if (event.keyCode == 13) {
			$("#loginImg").click();
			//阻止事件传播
			event.stopPropagation();
			event.preventDefault();
		}
	});
</script>
</head>

<body>
	<form id="loginForm" method="post" name="loginForm"
		action="${ctx}/login/login">
		<div class="login_bg">
			<div class="login">
				<div class="login_text">
					<div>
						<input type="text" id="txtUserName" name="userName" tabindex="1"
							value="请输入员工号" class="span1" />
						<!-- 
							<input type="text" id="txtUserName" name="userName" tabindex="1"
							value="admin" class="span1" />
							 -->
					</div>
					<div>
					
						<input type="password" name="password" tabindex="2"
							id="txtPassword" class="span1" style="display: none ;" /> <input
							type="text" id="txtPwdInfo" value="请输入密码" tabindex="2"
							class="span1" />
					 
					 	<!-- 	<input type="password" name="password" tabindex="2" value="123456"
							id="txtPassword" class="span1" style="display: none ;" /> <input
							type="text" id="txtPwdInfo" value="123456" tabindex="2"
							class="span1" />-->
					</div>
					<%-- 
					<div>
						<input name="code" type="text" size="8"
							id="txtValidateCode" style="float:left;line-height:23px;" /> <img
							id="tipImg" src="${ctx}/styles/images/right.png"
							style="display: none;float:left;margin-top:8px; margin-left:3px;" />
						<img src="${ctx}/login/code?r=<%=Math.random() %>" id="img_code"
							style="margin-left:3px;cursor:pointer; float:left; border:1px solid #ccc;"
							align="absmiddle" width="80" height="29"
							onclick="document.getElementById('img_code').src='${ctx}/login/code?' + Math.random();"
							title="看不清换一张" />
					</div>
					--%>
					<div class="clear"></div>
					<p style="font-size:12px;" id="error_msg">
						信息提示：<font color="red">${msg}</font>
					</p>

					<div class="login_button" id="loginImg"></div>

				</div>

				<div class="login_foot">©版权所有 金风科技 技术电话：010-8888888</div>
			</div>
		</div>
	</form>
</body>
</html>