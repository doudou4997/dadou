<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.util.Random" %>
<%@ include file="/comm/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/comm/base.jsp" />
<meta charset="UTF-8">
<title>货柜管理</title>
<style type="text/css">
body {
    font-family:helvetica,tahoma,verdana,sans-serif;
    padding:8px;
    font-size:13px;
    margin:0;
}
h2 {
    font-size:18px;
    font-weight:bold;
    margin:0;
    margin-bottom:15px;
}
</style>
</head>
<body>
<div class="easyui-panel" title="货柜管理" style="width:auto;padding:10px;">
	<form action="${ctx}/shelves/list" id="queryForm" name="queryForm">
		<table class="table-search" width="100%"
			   style="margin:0px 0px 10px 0px;">
			<tr>
				<th width="10%">货柜编号：</th>
				<td width="12%"><input name="q_shelvesCode" id="q_shelvesCode" size="15" /></td>
				<th width="10%">货柜名称：</th>
				<td width="12%"><input name=q_"shelvesName" id="q_shelvesName" size="15" /></td>
				<th width="10%">货柜状态：</th>
				<td width="6%">
					<input id="q_shelvesStatus" name="q_shelvesStatus"  class="easyui-combobox" style="width:70px;height:auto" ／>
				</td>
				<th width="10%">货柜类型：</th>
				<td width="6%">
					<input id="q_shelvesType" name="q_shelvesType"  class="easyui-combobox"  style="width:60px;height:auto" ／>
				</td>
				<td align="left"><a href="javascript:void(0)" class="btn btn-info" onclick="searchx()">
					<img src="${ctx }/styles/lib/easyui/themes/icons/search.png" align="absmiddle" />&nbsp;查询</a></td>
			</tr>
		</table>
	</form>
	<table id="shelves-data-table"  width="100%"></table>
</div>

<!-- 添加货柜信息 -->
<div id="addShelves_Win"   title="新添货柜" style="width:850px;height:450px;"></div>

<script type="text/javascript">
    /**
     * 获取选中的id
     */
    function getDatagridChecked() {
        var rows = $('#shelves-data-table').datagrid('getChecked');
        if(rows.length == 0) {
            return null;
        } else {
            return rows[0].id;
        }
    }
    //避免重名
    var shelvesList = new Object();
    // 打开新添货柜窗口
    shelvesList.addshelves = function(){
        var path="${ctx}/shelves/add";
        $('#addShelves_Win').dialog({href:path}).dialog("open");
    }

    // 删除货柜
    shelvesList.delshelves = function(){
        var id = getDatagridChecked();
        if(id == null) {
            $.messager.alert('提示',"请先选中一行",'error');
            return;
        }
        $.messager.confirm('确认','您确认想要删除此货柜吗？',function(r){
            if (r){
                $.ajax({
                    url: '${ctx}/shelves/del',
                    data: {id: id},
                    success: function (data) {
                        var data = eval('(' + data + ')');
                        if(data.success) {
                            $.messager.alert('',data.msg,"info");
                            $('#shelves-data-table').datagrid('reload');
                        } else {
                            $.messager.alert('',data.msg,"error");
                        }
                    }
                });
            }
        });
    }
    var parentId;
    //查询
    function searchx() {

        parentId = "";
        $('#shelves-data-table').datagrid('reload',
			{
            	q_shelvesName: $.trim($("#q_shelvesName").val()),
                q_shelvesCode: $.trim($("#q_shelvesCode").val()),
                q_shelvesStatus: $("#q_shelvesStatus").combobox('getValue'),
                q_shelvesType: $("#q_shelvesType").combobox('getValue')
           // formData: decodeURIComponent($("#queryForm").serialize(),true)
        });
    }
    //初始化
    $(function() {
        $('#shelves-data-table').datagrid({
            url : '${ctx}/shelves/listAjax',
            rownumbers : true,
            autoRowHeight : true,
            singleSelect : true,
            fitColumns: true,//列宽 自适应
            pagination : true,
            height:$(window).height() - 110,
            onClickRow:function(index,row) {
               // parentId=row.id;
              //  $('#settingItem-data-table').datagrid('reload', {
              //      groupId:parentId
              //  });
            },
            onLoadSuccess:function(data){
                //$('#settingItem-data-table').datagrid('loadData', { total: 0, rows: [] });
            },
            toolbar : [{
                text : '添加',
                iconCls : 'icon-add',
                handler : function() {
                    shelvesList.addshelves();
                }
            },'-', {
                text : '删除',
                iconCls : 'icon-no',
                handler : function() {
                    shelvesList.delshelves();
                }
            }, '-', {
                text : '修改',
                iconCls : 'icon-ok',
                handler : function() {
                    shelvesList.editshelves();
                }
            },  '-'],
           // frozenColumns : [ [

            //] ],
            columns : [ [
                {field : 'id',align : 'center',checkbox : true},
                {field : 'shelvesCode',title : '货柜条码',width : 100,align : 'center'},
                {field : 'shelvesType',title : '货柜类型',width : 60,align : 'center'},
                {field : 'shelvesName',title : '货柜名称',width : 120,align : 'center'},
                {field : 'flag',title : '状态',width : 50,align : 'center'},
                {field : 'shelvesCapacity',title : '货柜容量',width : 50,align : 'center'},
                {field : 'shelvesAddress',title : '货柜地址',width : 60,align : 'center'},
                {field:'opt',title:'操作',width:30,align:'center',
                    formatter:function(value,rec){
                        var btn = '<a class="editcls" onclick="editRow(\''+rec.projectname+'\',\''+rec.projectpackage+'\')" href="javascript:void(0)">编辑</a>';
							//+'&nbsp;&nbsp;<a class="editcls" onclick="editRow(\''+rec.projectname+'\',\''+rec.projectpackage+'\')" href="javascript:void(0)">上架</a>';
                        return btn;
                    }
                }
            ] ]
	/*,
            onLoadSuccess:function(data){
                $('.editcls').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'});
            }*/
        });
		//下拉列表
        $('#q_shelvesStatus').combobox({
            panelHeight : 'auto',
            valueField : 'key',
            textField : 'value',
            editable : false,
            url : '${ctx}/dict/dictionary?dtype='+"goodsStatus"
        });
        $('#q_shelvesStatus').combobox('setValue', '');

        //下拉列表
        $('#q_shelvesType').combobox({
            panelHeight : 'auto',
            valueField : 'key',
            textField : 'value',
            editable : false,
            url : '${ctx}/dict/dictionary?dtype='+"shelvesType"
        });
        $('#q_shelvesType').combobox('setValue', '');


        //添加
        $('#addShelves_Win').dialog({
            title: '货柜添加',
            width: 600,
            height:300,
            closed: true,
            cache: false,
            iconCls:'icon-add',
            resizable:false,
            modal: true,
            buttons: [{
                text:'保存',
                iconCls:'icon-ok',
                handler:function(){
                    if($('#addShelves_Win').find("#shelvesSaveForm").size() > 0) {
                        doInsert();
                    } else if($('#addShelves_Win').find("#shelvesEditForm").size() > 0) {
                        doUpdate();
                    }
                }
            },{
                text:'关闭',
                iconCls:'icon-cancel',
                handler:function(){
                    $('#addShelves_Win').dialog("close");
                }
            }]
        });
    });
</script>

</body>
</html>
