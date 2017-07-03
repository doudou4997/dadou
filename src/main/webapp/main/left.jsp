<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/comm/taglibs.jsp"%>
<style>
<!--
.left-menu ul {
	list-style: none;
	float:left;
	margin:0;
	padding:0;
	width:100%;
}
.left-menu ul li {
  background: none repeat scroll 0 0 #F2F2F2;
  border-bottom: 1px solid #E4E8ED !important;
  border-top: 1px solid #FCFCFC !important;
  padding: 10px 0;
  text-align: center;
  width: 100%;
}

.left-menu ul li a {
	color: #333333;
	text-decoration: none;
  	text-shadow: 0 1px 0 #FFFFFF;
}

/* 二级菜单加粗 */
#left_tree > li > ul > li > .tree-node > .tree-title {font-weight: 900} 
-->
</style>
<script type="text/javascript" src="${ctx }/styles/js/index/left.js"></script>
<input type="hidden" id="hidden_sysId" value="${param.sys_id}" />
<div class="easyui-accordion" data-options="fit:true,border:false">
	<div id="left_accordion" title="系统菜单" style="padding: 5px;" data-options="border:false,iconCls:'anchor',tools : [ {
			iconCls : 'icon-reload',
			handler : function() {
				$('#layout_west_tree').tree('reload');
			}
		}, {
			iconCls : 'next',
			handler : function() {
				var node = $('#left_tree').tree('getSelected');
				if (node) {
					$('#left_tree').tree('expandAll', node.target);
				} else {
					$('#left_tree').tree('expandAll');
				}
			}
		}, {
			iconCls : 'previous',
			handler : function() {
				var node = $('#left_tree').tree('getSelected');
				if (node) {
					$('#left_tree').tree('collapseAll', node.target);
				} else {
					$('#left_tree').tree('collapseAll');
				}
			}
		} ]">
	<div class="well well-small">
		<ul id="left_tree"></ul>
	</div>
  </div>
</div>
