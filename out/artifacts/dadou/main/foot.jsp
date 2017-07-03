<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/comm/taglibs.jsp"%>
<%@page import="com.dadou.sys.login.LoginManager"%>
<div data-options="region:'south',border:false" style="line-height:20px;background:#eff1f6; font-family:Verdana, Geneva, sans-serif; font-size:12px; color:#999; text-align:center">
	<span  style="margin-left: 20px;float:left;font-size:12px; color:#000;">在线人数：<c:choose>
			<c:when test="${emp.userCode eq 100001}"><a
					href="./online.jsp" target="_blank"
					style="text-decoration: underline"><%=LoginManager.getOnlineNum()%></a>
			</c:when>
			<c:otherwise><%=LoginManager.getOnlineNum()%></c:otherwise>
		</c:choose>&nbsp;人</span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

 </div>