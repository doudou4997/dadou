<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="/comm/taglibs.jsp" %>
<div>
    <form id="layerSaveForm" method="post">
        <table class="table-search" align="center" width="100%">
                  <input  type="text"  id="id_shelves" name="id_shelves" value="${id_shelves}" />
            <tr>
                <th>货架序号：</th>
                <td>
                    <input class="easyui-numberbox" type="text"  id="layerIndex" name="layerIndex"
                           data-options="validType:'length[1,3]',required:true,missingMessage:'货架序号不能为空'"   style="width: 60%"/>
                    &nbsp;<span style="color:#FF0000;">*</span>
                </td>
                <th>货架别名：</th>
                <td><input class="easyui-validatebox" type="text" id="layerName" name="layerName"
                           data-options="required:true,missingMessage:'货架别名不能为空'" style="width: 80%"/>
                    &nbsp;<span style="color:#FF0000;">*</span></td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript">
    function doInsertLayer(){
        var layerSaveForm = $('#layerSaveForm');
        //整个form的校验
        var valid = layerSaveForm.form("validate");
        if(!valid) {
            $.messager.alert('校验失败',"请填写必选项！",'info');
            return;
        }
        var url = "${ctx}/shelves/saveLayer";
        //表单提交
        $.ajax({
            url:url,
            type:'POST',
            data:$("#layerSaveForm").serialize(),
            dataType:'json',
            success: function(data){
                if(data.success){
                    $.messager.alert('',data.msg,'info',function(){
                        $("#addLayer_Win").dialog("close");
                        $('#layer-data-table').datagrid('reload');
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
