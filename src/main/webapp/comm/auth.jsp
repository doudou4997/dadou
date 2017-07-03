<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	isErrorPage="true"%>
<%@include file="/comm/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
<TITLE>抱歉！系统正在维护中...</TITLE>
<META content="text/html; charset=gb2312" http-equiv=Content-Type>
<STYLE type=text/css>
BODY {
	PADDING-BOTTOM: 0px;
	MARGIN: 0px auto;
	PADDING-LEFT: 0px;
	PADDING-RIGHT: 0px;
	FONT-FAMILY: Microsoft YaHei, Verdana, Geneva;
	COLOR: #666;
	FONT-SIZE: 12px;
	PADDING-TOP: 0px
}

.wrap {
	BORDER-BOTTOM: #ccc 5px solid;
	BORDER-LEFT: #ccc 5px solid;
	BACKGROUND-COLOR: #fff;
	MARGIN: 100px auto;
	WIDTH: 800px;
	HEIGHT: 200px;
	BORDER-TOP: #ccc 5px solid;
	BORDER-RIGHT: #ccc 5px solid
}

IMG {
	BORDER-BOTTOM: medium none;
	BORDER-LEFT: medium none;
	DISPLAY: block;
	BORDER-TOP: medium none;
	BORDER-RIGHT: medium none
}

.wrap IMG {
	MARGIN-TOP: 30px;
	DISPLAY: inline;
	FLOAT: left;
	MARGIN-LEFT: 90px
}

.reson {
	TEXT-ALIGN: center;
	MARGIN: 50px 50px 0px 0px;
	WIDTH: 400px;
	DISPLAY: inline;
	FONT-SIZE: 14px
}

.reson SPAN {
	MARGIN-TOP: 20px;
	DISPLAY: block;
	FONT-SIZE: 14px
}
</STYLE>
<META name=GENERATOR content="MSHTML 8.00.7601.17720">
</HEAD>
<BODY>
	<br />
	<DIV align="center">
		<IMG src="${ctx}/styles/images/noauth.jpg" width=838
		 height=406>
		<DIV class=reson>
			抱歉！您不具备访问权限!<br /> 请联系管理员!
		</DIV>
	</DIV>
</BODY>
</HTML>
