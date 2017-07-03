//-------------------------------------------------------------------------------  
//     结合SHIFT,CTRL,ALT键实现单选或多选  
//-------------------------------------------------------------------------------  
var KEY = { SHIFT:16, CTRL:17, ALT:18, DOWN:40, RIGHT:39, UP:38, LEFT:37};
var selectIndexs = {firstSelectRowIndex:0, lastSelectRowIndex:0};
var inputFlags = {isShiftDown:false, isCtrlDown:false, isAltDown:false}

function keyPress(event){//响应键盘按下事件  
    var e = event || window.event;
    var code = e.keyCode | e.which | e.charCode;
    switch(code) {
        case KEY.SHIFT:
            inputFlags.isShiftDown = true;
            $('#data-table').datagrid('options').singleSelect = false;
            break;
        case KEY.CTRL:
            inputFlags.isCtrlDown = true;
            $('#data-table').datagrid('options').singleSelect = false;
            break;
        /* 
         case KEY.ALT:    
         inputFlags.isAltDown = true; 
         $('#data-table').datagrid('options').singleSelect = false;            
         break; 
         */
        default:
    }
}

function keyRelease(event) { //响应键盘按键放开的事件  
    var e = event || window.event;
    var code = e.keyCode | e.which | e.charCode;
    switch(code) {
        case KEY.SHIFT:
            inputFlags.isShiftDown = false;
            selectIndexs.firstSelectRowIndex = 0;
            //$('#data-table').datagrid('options').singleSelect = true;
            break;
        case KEY.CTRL:
            inputFlags.isCtrlDown = false;
            selectIndexs.firstSelectRowIndex = 0;
            //$('#data-table').datagrid('options').singleSelect = true;
            break;
        /* 
         case KEY.ALT:    
         inputFlags.isAltDown = false; 
         selectIndexs.firstSelectRowIndex = 0; 
         $('#data-table').datagrid('options').singleSelect = true;             
         break; 
         */
        default:
    }
}  