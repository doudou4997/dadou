<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ page import="com.dadou.sys.login.LoginManager" %>
<%
	//下述代码，用来取得实际文件所在的文件夹，计算出 basePath
	//设定正确的 base 之后，dreamweaver 也可以识别
	//取得应用所在的目录
	String path = request.getContextPath();
	//取得文件所在的文件夹，并且取得最后一个 "/" 的部分
	String path1 = request.getServletPath();
	int i = path1.lastIndexOf("/");
	String path2 = path1.substring(0, i);

	String rootPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + path2 + "/";
	request.setAttribute("basepath", basePath);
	request.setAttribute("rootpath", rootPath);
%>
<script type="text/javascript">
	var CURRENT_SITE_ID='';
</script>

<base href="<%=request.getAttribute("basepath")%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<%--
  bootstrap cupertino dark-hive  icons  metro  metro-blue  metro-gray
   metro-green metro-orange metro-red metro-grinder pepper-gray sunny
 --%>
<link rel="stylesheet" type="text/css" 	href="<%=request.getAttribute("rootpath")%>/styles/lib/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" 	href="<%=request.getAttribute("rootpath")%>/styles/css/ext_easyui.css" />
<link rel="stylesheet" type="text/css" 	href="<%=request.getAttribute("rootpath")%>/styles/css/ext_icons.css" />
<link rel="stylesheet" type="text/css" 	href="<%=request.getAttribute("rootpath")%>/styles/lib/easyui/themes/icon.css" />
<link rel="stylesheet" type="text/css" 	href="<%=request.getAttribute("rootpath")%>/styles/lib/easyui/themes/IconExtension.css" />

<script type="text/javascript" 	src="<%=request.getAttribute("rootpath")%>/styles/lib/easyui/jquery.min.js"></script>
<script type="text/javascript" 	src="<%=request.getAttribute("rootpath")%>/styles/lib/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" 	src="<%=request.getAttribute("rootpath")%>/styles/lib/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" 	src="<%=request.getAttribute("rootpath")%>/styles/js/comm/jquery.easyui.extend.js"></script>
<script type="text/javascript"  src="<%=request.getAttribute("rootpath")%>/styles/js/comm/config.js"></script>
<script type="text/javascript"  src="<%=request.getAttribute("rootpath")%>/styles/js/comm/common.js"></script>
<script type="text/javascript"  src="<%=request.getAttribute("rootpath")%>/styles/js/comm/ajaxfileupload.js"></script>



