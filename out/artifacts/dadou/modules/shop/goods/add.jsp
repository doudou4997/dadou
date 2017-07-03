<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="/comm/taglibs.jsp" %>
<div>
    <form id="goodsSaveForm" method="post">
        <table class="table-search" align="center" width="100%">
            <tr>
                <th>商品条码：</th>
                <td>
                    <input class="easyui-numberbox" type="text"  id="goodsCode" name="goodsCode"
                           data-options="validType:'length[1,13]',required:true,missingMessage:'商品数量不能为空'"   style="width: 60%"/>
                    &nbsp;<span style="color:#FF0000;">*</span>
                    <a href="javascript:void(0)" id="addproductCode"
                       class="easyui-linkbutton" iconCls="icon-search">校验</a>
                </td>
                <th>商品名：</th>
                <td><input class="easyui-validatebox" type="text" id="goodsName" name="goodsName"
                           data-options="required:true,missingMessage:'商品名不能为空'" style="width: 80%"/>
                    &nbsp;<span style="color:#FF0000;">*</span></td>
                <th>商品类型：</th>
                <td>
                    <input id="goodsType" name="goodsType" class="easyui-combobox"
                           data-options="required:true,missingMessage:'商品类型不能为空'" style="width:100px;height:auto"／>
                    &nbsp;<span style="color:#FF0000;">*</span></td>
            </tr>

            <tr>
                <th>商品售价：</th>
                <td>
                    <input class="easyui-numberbox" type="text" id="goodsASP" name="goodsASP"
                           data-options="min:0.01,precision:2,required:true,missingMessage:'商品售价不能为空'"/>
                    &nbsp;<span style="color:#FF0000;">*</span></td>
                <th>商品数量：</th>
                <td>
                    <input class="easyui-numberbox" type="text"  id="goodsNum" name="goodsNum"
                           data-options="min:0,precision:0,required:true,missingMessage:'商品数量不能为空'"/>
                    &nbsp;<span style="color:#FF0000;">*</span></td>
                <th>兑换积分：</th>
                <td>
                    <input class="easyui-numberbox" type="text"  id="goodsPoint" name="goodsPoint"  style="width: 60%"/>
                </td>
            </tr>
            <tr>
                <th>商品简述：</th>
                <td colspan="4">
                    <input class="easyui-validatebox" type="text" id="goodsShort" name="goodsShort"
                           data-options="required:true,missingMessage:'商品简述不能为空',validType:'length[1,25]'" style="width: 60%"/>
                    &nbsp;<span style="color:#FF0000;">*&nbsp;建议不要超过25个字</span> </td>
            </tr>

            <tr>
                <th>品牌：</th>
                <td><input class="easyui-validatebox" type="text" id="goodsBrand" name="goodsBrand" style="width: 80%"/></td>
                <th>产地：</th>
                <td><input class="easyui-validatebox" type="text" id="goodsArea" name="goodsArea" style="width: 80%"/></td>
                <th>入库价：</th>
                <td><input class="easyui-validatebox" type="text" id="goodsPrice" name="goodsPrice" style="width: 80%"/></td>
            </tr>
            <tr>
                <th>生产日期：</th>
                <td><input   id="goodsPD" name="goodsPD" class="easyui-datebox" size="25" maxlength="25"
                             style="font-size:13px"/>
                <th>保质期：</th>
                <td>
                    <input class="easyui-validatebox" type="text" id="goodsEXP" name="goodsEXP" style="width: 80%"/>
                    &nbsp;<span style="color:#FF0000;">天</span>
                </td>
                <th>商品折扣：</th>
                <td>
                    <input class="easyui-numberbox" type="text"  id="goodsDct" name="goodsDct"
                           data-options="min:0,precision:1," style="width: 40%"/>
                    &nbsp;<span style="color:#FF0000;">8折请填写0.8</span>
                </td>
            </tr>
            <tr>
                <th>商品描述：</th>
                <td colspan="4">
                    <textarea class="easyui-validatebox" type="text" id="goodsDesc" name="goodsDesc"
                              style="width: 100%"/>
                </td>
            </tr>
            <tr>
                <th>商品图片：</th>
                <td colspan="3">
                    <span style="display:none;"><input type="file"  id="inputPic"  name="inputPic" /></span>
                    <input type="hidden" id="goodsPicUrl" name="goodsPicUrl" />
                    <span id="imageSpan" name="imageSpan"></span>
                    <img id="imageTemp" name="imageTemp" src="${ctx}/styles/images/add_common.png"
                         onclick="upload('${ctx}/', 'imageSpan', 'goodsPicUrl')" />
                </td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript">
    var checkFlag = 0; //用于校验商品条码是否唯一，校验成功则为1.

    //初始化
    $(function() {
        //下拉列表
        $('#goodsType').combobox({
            panelHeight: 'auto',
            valueField: 'key',
            textField: 'value',
            editable: false,
            url: '${ctx}/dict/dictionary?dtype=' + "goodsType"
        });
        $('#goodsType').combobox('setValue', '');

    });

    //上传图片
    function upload(contextPath, spanId, inputId){
        // 选择文件后自动上传
        $("#inputPic").unbind().bind('change', function(){
            $.ajaxFileUpload({
                url:  contextPath + 'upload/imageuplod',
                secureuri: false,
                fileElementId: "inputPic",
                dataType: 'text',
                success: function (data, status){
                    data = data.slice(data.indexOf("{"), data.lastIndexOf("}") + 1);
                    data = JSON.parse(data);
                    if(data.result == 1){
                        var name = data.name;
                        $("#" + spanId).empty();
                        $("#" + spanId).append("<img title='双击删除图片' class='"+spanId+"'  src='"+contextPath + "assets/installimages/" + name +"'  style='width:64px;'/>&nbsp;&nbsp;");
                        $("#" + inputId).val(name);
                        // 给图片增加删除事件
                        $("." + spanId).unbind().bind('dblclick', function(){
                            $(this).remove();
                            $("#" + inputId).val("");
                            $("#imageTemp").show();
                        });
                        $("#imageTemp").hide();

                    }
                }
            });
        });
        return $("#inputPic").click();
    }


    function doInsert(){
        alert("fff");
        var goodsSaveForm = $('#goodsSaveForm');
        //整个form的校验
        var valid = goodsSaveForm.form("validate");
        if(!valid) {
            $.messager.alert('校验失败',"请填写必选项！",'info');
            return;
        }
        //产品码校验
        if(checkFlag == 1){
            $.messager.alert('校验失败',"必须进行商品条码校验",'info');
            return;
        }


        var url = "${ctx}/goods/save";
        //表单提交
        $.ajax({
            url:url,
            type:'POST',
            data:$("#goodsSaveForm").serialize(),
            dataType:'json',
            success: function(data){
                if(data.success){
                    $.messager.alert('',data.msg,'info',function(){
                        $("#addGoods_Win").dialog("close");
                        $('#goods-data-table').datagrid('reload');
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
