<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.util.Random" %>
<%@ include file="/comm/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/comm/base.jsp" />
<meta charset="UTF-8">
<title>商品管理</title>
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
<div class="easyui-panel" title="商品管理" style="width:auto;padding:10px;">
	<form action="${ctx}/goods/list" id="queryForm" name="queryForm">
		<table class="table-search" width="100%"
			   style="margin:0px 0px 10px 0px;">
			<tr>
				<th width="10%">商品编号：</th>
				<td width="12%"><input name="q_goodsCode" id="q_goodsCode" size="15" /></td>
				<th width="10%">商品名称：</th>
				<td width="12%"><input name=q_"goodsName" id="q_goodsName" size="15" /></td>
				<th width="10%">商品状态：</th>
				<td width="6%">
					<input id="q_goodsStatus" name="q_goodsStatus"  class="easyui-combobox" style="width:70px;height:auto" ／>
				</td>
				<th width="10%">商品类型：</th>
				<td width="6%">
					<input id="q_goodsType" name="q_goodsType"  class="easyui-combobox"  style="width:60px;height:auto" ／>
				</td>
				<td align="left"><a href="javascript:void(0)" class="btn btn-info" onclick="searchx()">
					<img src="${ctx }/styles/lib/easyui/themes/icons/search.png" align="absmiddle" />&nbsp;查询</a></td>
			</tr>
		</table>
	</form>
	<table id="goods-data-table"  width="100%"></table>
</div>

<!-- 添加配组信息 -->
<div id="addGoods_Win"   title="新添商品" style="width:850px;height:450px;"></div>

<script type="text/javascript">
    /**
     * 获取选中的id
     */
    function getDatagridChecked() {
        var rows = $('#goods-data-table').datagrid('getChecked');
        if(rows.length == 0) {
            return null;
        } else {
            return rows[0].id;
        }
    }
    //避免重名
    var goodsList = new Object();
    // 打开新添商品窗口
    goodsList.addGoods = function(){
        var path="${ctx}/goods/add";
        $('#addGoods_Win').dialog({href:path}).dialog("open");
    }

    // 删除商品
    goodsList.delGoods = function(){
        var id = getDatagridChecked();
        if(id == null) {
            $.messager.alert('提示',"请先选中一行",'error');
            return;
        }
        $.messager.confirm('确认','您确认想要删除此商品吗？',function(r){
            if (r){
                $.ajax({
                    url: '${ctx}/goods/del',
                    data: {id: id},
                    success: function (data) {
                        var data = eval('(' + data + ')');
                        if(data.success) {
                            $.messager.alert('',data.msg,"info");
                            $('#goods-data-table').datagrid('reload');
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
        $('#goods-data-table').datagrid('reload',
			{
            	q_goodsName: $.trim($("#q_goodsName").val()),
                q_goodsCode: $.trim($("#q_goodsCode").val()),
                q_goodsStatus: $("#q_goodsStatus").combobox('getValue'),
                q_goodsType: $("#q_goodsType").combobox('getValue')
           // formData: decodeURIComponent($("#queryForm").serialize(),true)
        });
    }
    //初始化
    $(function() {
        $('#goods-data-table').datagrid({
            url : '${ctx}/goods/listAjax',
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
                    goodsList.addGoods();
                }
            },'-', {
                text : '删除',
                iconCls : 'icon-no',
                handler : function() {
                    goodsList.delGoods();
                }
            }, '-', {
                text : '修改',
                iconCls : 'icon-ok',
                handler : function() {
                    goodsList.editGoods();
                }
            },  '-'],
            frozenColumns : [ [
                {field : 'id',align : 'center',checkbox : true},
                {field:'goodsPicUrl',title:'图片',width:90,align:'center',
                    formatter:function(val,row,index){
                        var btn = '<img src="<%=request.getAttribute("rootpath")%>/assets/installimages/'+val+'" height="80" width="80" />';
                        return btn;
                    }
                },
                {field : 'goodsCode',title : '商品条码',width : 100,align : 'center'},
                {field : 'goodsType',title : '商品类型',width : 60,align : 'center'},
                {field : 'goodsName',title : '商品名称',width : 120,align : 'center'},
            ] ],
            columns : [ [
                {field : 'flag',title : '状态',width : 50,align : 'center'},
                {field : 'goodsPrice',title : '价格',width : 50,align : 'center'},
                {field : 'goodsASP',title : '商品售价',width : 50,align : 'center'},
                {field : 'goodsNum',title : '商品数量',width : 60,align : 'center'},
                {field : 'goodsBrand',title : '品牌',width : 80,align : 'center'},
                {field : 'goodsPoint',title : '兑换积分',width : 60,align : 'center'},
                {field : 'goodsShort',title : '简述',width : 200,align : 'left'},
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
        $('#q_goodsStatus').combobox({
            panelHeight : 'auto',
            valueField : 'key',
            textField : 'value',
            editable : false,
            url : '${ctx}/dict/dictionary?dtype='+"goodsStatus"
        });
        $('#q_goodsStatus').combobox('setValue', '');

        //下拉列表
        $('#q_goodsType').combobox({
            panelHeight : 'auto',
            valueField : 'key',
            textField : 'value',
            editable : false,
            url : '${ctx}/dict/dictionary?dtype='+"goodsType"
        });
        $('#q_goodsType').combobox('setValue', '');


        //添加
        $('#addGoods_Win').dialog({
            title: '商品添加',
            width: 850,
            height:450,
            closed: true,
            cache: false,
            iconCls:'icon-add',
            resizable:false,
            modal: true,
            buttons: [{
                text:'保存',
                iconCls:'icon-ok',
                handler:function(){
					alert("dddd");
                    if($('#addGoods_Win').find("#goodsSaveForm").size() > 0) {
                        doInsert();
                    } else if($('#addGoods_Win').find("#goodsEditForm").size() > 0) {
                        doUpdate();
                    }
                }
            },{
                text:'关闭',
                iconCls:'icon-cancel',
                handler:function(){
                    $('#addGoods_Win').dialog("close");
                }
            }]
        });
    });
</script>

</body>
</html>
