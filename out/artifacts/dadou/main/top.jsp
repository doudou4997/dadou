<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/comm/taglibs.jsp"%>
<%@ page import="com.dadou.sys.login.LoginManager" %>
<script type="text/javascript">
var top = new Object();
top.closePwdWin = function() {
	$("#top-pwdWin").dialog("close");
}
top.topAlert = function(title, msg, icon, func) {
	$.messager.alert(title, msg, icon, func);
}

$(function(){
	// 控制面板菜单
	$('#site-control').menubutton({
		iconCls: 'icon-plugin',
		menu: '#top-siteMenu'
	});
	// 控制面板菜单
	$('#top-control').menubutton({
		iconCls: 'icon-cog',
		menu: '#top-controlMenu'
	});
	
	// 注销菜单
	$('#top-cancel').menubutton({
		iconCls: 'icon-undo',
		menu: '#top-cancelMenu'
	});
	
	// begin "修改密码"按钮的事件响应
	$("#top-pwdMi").click(function(){
		var path="${ctx}/login/changePwd";
		$("#top-pwdWin").dialog({href:path}).dialog("open");
	});//pwd
	//"退出系统"按钮的事件响应
	$("#top-exit").click(function(){
		$.messager.confirm('确认', '您确认退出系统吗？', function(r){
			if (r){
				window.location.replace(config.ctx + "/login/logout");
			}
		});
	});//exit 
	//工厂
	/*
	$('#mySelect').change(function(){ 
		alert($(this).children('option:selected').val()); 
		var p1=$(this).children('option:selected').val();//这就是selected的值 
		var p2=$('#param2').val();//获取本页面其他标签的值 
		window.location.href="xx.php?param1="+p1+"¶m2="+p2+"";//页面跳转并传参 
		}) 
		*/
});
</script>

<div class="top" data-options="region:'north',border:false">
	<div class="top_l">
		<div class="logo"></div>
		<div class="menu_top">
			<ul>
				<!-- 计算头部信息 -->
				<gw:top />
			</ul>
		</div>
	</div>
	<div class="right">
			<%--
			<a href="javascript:void(0)" id="top-skin">更换皮肤</a>
			<div id="top-skinMenu" class="easyui-menu" style="width:40px;">
				<div id="" data-options="iconCls:'icon-tip'">default</div>
				<div class="menu-sep"></div>
				<div id="" data-options="iconCls:'icon-tip'">grey</div>
			</div>
				<div style="margin-left: 120px" >
			 --%>
			<%-- 
			<a href="javascript:void(0)" id="site-control">工厂列表</a>
			<div id="top-siteMenu" class="easyui-menu" style="width:40px;">
				<div id="top-pwdMi1">修改密码</div>
				<div class="menu-sep"></div>
				<div id="top-myRole1">我的角色</div>
				<div class="menu-sep"></div>
				<div id="top-myMenu1">我的权限</div>
			</div>
			--%>
			<a href="javascript:void(0)" id="top-control">控制面板</a>
			<div id="top-controlMenu" class="easyui-menu" style="width:40px;">
				<div id="top-exit" data-options="iconCls:'icon-door'">退出系统</div>
			    <div class="menu-sep"></div>
				<div id="top-pwdMi" data-options="iconCls:'icon-edit'">修改密码</div>
				<!-- 
				<div class="menu-sep"></div>
				<div id="top-myRole" data-options="iconCls:'icon-tux'">我的角色</div>
				<div class="menu-sep"></div>
				<div id="top-myMenu" data-options="iconCls:'icon-wrench'">我的权限</div>
				 -->
			</div>
			<%-- 
			<a href="javascript:void(0)" id="top-cancel">注销</a>
			<div id="top-cancelMenu" class="easyui-menu" style="width:40px;">
				<!-- 
				<div id="top-lock" data-options="iconCls:'icon-hammer2'">锁定窗口</div>
				<div class="menu-sep"></div>
				 -->
				<div id="top-exit" data-options="iconCls:'icon-door'">退出系统</div>
			</div>
			--%>
			<%-- 
			<span style="font-size: 13px;color: blue;font-family:微软雅黑;font-weight:bold;">（${emp.name }）</span>--%>
	</div>
</div>
<div id="top-pwdWin" class="easyui-dialog" title="修改密码"
	style="width:330px;height:200px;"
	data-options="iconCls:'icon-edit',resizable:true,modal:true,closed:true">
</div>