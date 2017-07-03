<%@ page language="java" pageEncoding="utf-8"%>
<%@include file="/comm/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/styles/lib/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/styles/lib/layer/layer.js?v=2.2"></script>
<script type="text/javascript">
	//如果出现错误信息,则提示
    var msg = '${msg}';
    if(msg == '') msg = 'Session过期,需要重新登录!';
    alert(msg);
    if(window!=top){
       window.top.location.replace("${ctx}/main/login.jsp");
     }else{
       location.replace("${ctx}/main/login.jsp");
     }
     /*
     var closeIndex=layer.alert(msg', {icon: 0},function(){
		 //关闭弹出框
		 layer.close(closeIndex);
		 //父窗口搜索刷新
		 window.top.location.replace("${ctx}/main/login.jsp");
	 });*/
</script>
