<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/comm/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>系统登录</title>
<%@include file="/comm/base.jsp"%>
<script type="text/javascript" src="${ctx}/styles/js/comm/config.js"></script>
<script type="text/javascript" src="${ctx}/styles/js/index/index.js"></script>
<script type="text/javascript" src="${ctx}/styles/js/comm/cookie.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/css/top.css">
	</head>
	<body class="easyui-layout" id="index-layout">
		<jsp:include page="top.jsp"></jsp:include>
		<div id="index_nav_lef" data-options="region:'west',split:true,title:'模块导航'" style="width:220px;overflow:auto">
		</div>
		<div data-options="region:'center'" style="overflow: hidden;">
			<div id="index_tabs"  style="overflow: hidden;">
				<div title="首页" data-options="border:false" style="overflow: hidden;">
					<iframe id="mainFrame" name="mainFrame" src="${ctx }/main/process.jsp" frameborder="0" scrolling="no" style="border: 0; width: 100%; height: 530px;"></iframe>
				</div>
			</div>
		</div>
		<jsp:include page="foot.jsp"></jsp:include>
		<div id="index_tabsMenu" style="width: 120px; display: none;">
		<div title="refresh" data-options="iconCls:'icon-reload'">刷新</div>
		<div class="menu-sep"></div>
		<div title="close" data-options="iconCls:'tab_delete'">关闭</div>
		<div title="closeOther" data-options="iconCls:'tab_delete'">关闭其他</div>
		<div title="closeAll" data-options="iconCls:'tab_delete'">关闭所有</div>
	</div>

	<!-- 通用弹出框 -->
	<div id="dlg-common" class="easyui-dialog" >
		<iframe width="100%" height="98%" name="frameCommon" id="frameCommon" frameborder="0"></iframe>
	</div>
	</body>
	<script type="text/javascript">
	/**
	 * 初始化 首页菜单
	 */
	  $(function(){
		  showTree('');
	  });
	</script>
</html>