<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/comm/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<jsp:include page="/comm/base.jsp" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>出错啦</title>
		<link href="${ctx}/styles/css/base.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<br />
		<div class="error">
			<div class="error_pic"></div>
			<div>
				<div class="error_top"></div>
				<div class="error_mid">
					<p class="blue14">
						<s:property value="exception.message" />
					</p>
					<!-- <p>系统将自动跳转到首页。</p> -->
					<p class="green14">
						您可以重新登录：
						<span><a href="${ctx}/login/login" style="text-decoration: underline;color: blue">重新登录 </a> </span>
					</p>
				</div>
				<div class="error_bottom"></div>
			</div>
		</div>
	</body>
</html>