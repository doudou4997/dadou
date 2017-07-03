// 禁止用F5键  和 回退键
// 禁用F5的时候 $(dcument).keydown $(dcument).bind("keydown",..)  
// $(dcument).bind("keydown keyup",..) 对IE8无效
//   
if ($.browser.msie) {
    document.onkeydown = function() {
        if (event.keyCode == 116) {
            event.keyCode = 0;      
            event.cancelBubble = true;      
            return false;      
        }
        if (event.keyCode == 8) {
            var target    = event.target || event.srcElement; // 获得事件源   
            var tn = target.tagName; 
            tn = tn.toUpperCase(tn);
            if (tn != "INPUT" && tn != "TEXTAREA") {
                event.keyCode = 0;      
                event.cancelBubble = true;      
                return false;    
            }
        }
        if (event.keyCode == 13) {
            return false;
        }
        if (event.keyCode == 27) {
            event.keyCode = 0;
            event.cancelBubble = true;   
            alert("禁用Esc按键");
            return false;
        }
    }
} else {
    document.onkeydown = function(event) {      
        if (event.keyCode == 116) {
            event.keyCode = 0;      
            event.cancelBubble = true;      
            return false;      
        }
        if (event.keyCode == 8) {
            var target    = event.target || event.srcElement; // 获得事件源   
            var tn = target.tagName; 
            tn = tn.toUpperCase(tn);
            if (tn != "INPUT" && tn != "TEXTAREA") {
                event.keyCode = 0;      
                event.cancelBubble = true;      
                return false;    
            }
        }
        if (event.keyCode == 13) {
            return false;
        }
        // 火狐下无法拦截Esc按键，甚至用alert也不行
        if (event.keyCode == 27) {
            window.location.href = ExamConfig.ctx + "/exam.action?method=list";
            return false;
        }
        return true;
    }
}