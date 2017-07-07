<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="/comm/taglibs.jsp" %>
<div>
    <form id="shelvesSaveForm" method="post">
        <table class="table-search" align="center" width="100%">
            <tr>
                <th>货柜条码：</th>
                <td>
                    <input class="easyui-numberbox" type="text"  id="shelvesCode" name="shelvesCode"
                           data-options="validType:'length[1,13]',required:true,missingMessage:'货柜条码不能为空'"
                           style="width: 60%"/>
                    &nbsp;<span style="color:#FF0000;">*</span>
                    <a href="javascript:void(0)" id="addShelvesCode"
                       class="easyui-linkbutton" iconCls="icon-search">校验</a>
                </td>
                <th>货柜名称：</th>
                <td><input class="easyui-validatebox" type="text" id="shelvesName" name="shelvesName"
                           data-options="required:true,missingMessage:'货柜名称不能为空'" style="width: 80%"/>
                    &nbsp;<span style="color:#FF0000;">*</span></td>
            </tr>

            <tr>
                <th>货柜类型：</th>
                <td>
                    <input id="shelvesType" name="shelvesType" class="easyui-combobox"
                           data-options="required:true,missingMessage:'货柜类型不能为空'" style="width:100px;height:auto"／>
                    &nbsp;<span style="color:#FF0000;">*</span></td>
                <th>货柜容量：</th>
                <td>
                    <input class="easyui-numberbox" type="text" id="shelvesCapacity" name="shelvesCapacity"/>
                    &nbsp;</td>
            </tr>
            <tr>
                <th>货柜投放地址：</th>
                <td colspan="4">
                    <input class="easyui-validatebox" type="text" id="shelvesAddress" name="shelvesAddress"
                           data-options="validType:'length[1,50]'" style="width: 60%"/>
                    &nbsp;<span style="color:#FF0000;">*&nbsp;建议不要超过50个字</span> </td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript">
    var checkFlag = 0; //用于校验商品条码是否唯一，校验成功则为1.

    //初始化
    $(function() {
        //下拉列表
        $('#shelvesType').combobox({
            panelHeight: 'auto',
            valueField: 'key',
            textField: 'value',
            editable: false,
            url: '${ctx}/dict/dictionary?dtype=' + "shelvesType"
        });
        $('#shelvesType').combobox('setValue', '');

    });


    function doInsert(){
        var shelvesSaveForm = $('#shelvesSaveForm');
        //整个form的校验
        var valid = shelvesSaveForm.form("validate");
        if(!valid) {
            $.messager.alert('校验失败',"请填写必选项！",'info');
            return;
        }
        //产品码校验
        if(checkFlag == 1){
            $.messager.alert('校验失败',"必须进行货柜条码校验",'info');
            return;
        }
        var url = "${ctx}/shelves/save";
        //表单提交
        $.ajax({
            url:url,
            type:'POST',
            data:$("#shelvesSaveForm").serialize(),
            dataType:'json',
            success: function(data){
                if(data.success){
                    $.messager.alert('',data.msg,'info',function(){
                        $("#addShelves_Win").dialog("close");
                        $('#shelves-data-table').datagrid('reload');
                    });
                }else{
                    $.messager.alert('提示',data.msg,'info');
                }
            },
            error:function(info){
                $.messager.alert('提示','系统产生错误,请联系管理员!','error');
            }

        });
    }// end doInsert
    <%--//提交表单--%>
    <%--function submitSavePic() {--%>
        <%--var s = document.sopPicForm.picFile.value;--%>
        <%--if (s == "") {--%>
            <%--$.messager.alert('', "请选择要上传的图片", "info");--%>
            <%--document.sopPicForm.picFile.focus();--%>
            <%--return;--%>
        <%--}--%>
        <%--$('#savePic').form('submit', {--%>
            <%--url: '${ctx}/goods/savePic',--%>
            <%--success: function (data) {--%>
                <%--var data = eval('(' + data + ')');--%>
                <%--if (data.success) {--%>
                    <%--$.messager.alert('', data.msg, "info");--%>
                    <%--$('#data-pic-table').datagrid('reload');--%>
                    <%--$('#pic-add-window').dialog('close');--%>
                <%--} else {--%>
                    <%--$.messager.alert('', data.msg, "error");--%>
                <%--}--%>
            <%--}--%>
        <%--});--%>
    <%--}--%>
</script>
</body>
</html>
