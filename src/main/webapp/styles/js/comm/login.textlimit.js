/*
 * TextLimit - jQuery plugin for counting and limiting characters for input and textarea fields
 * 
*/
(function(jQuery) {
	jQuery.fn.textlimit=function(thelimit, fun,speed) {
		var charDelSpeed = speed || 15;
		var toggleCharDel = speed != -1;
		var toggleTrim = true;
		var that = this[0];
		var isCtrl = false; 
		updateCounter();
		
		function updateCounter(){
			if(typeof that == "object"){
			  if(thelimit == that.value.length){
			    //调用传入的函数
			    fun();
			  }
			}
		};
		
		this.keyup (function(e){
		   var updateFlag = true;
		  if(e.which == 17) isCtrl = true;
			var ctrl_a = (e.which == 65 && isCtrl == true) ? true : false; // detect and allow CTRL + A selects all.
			var ctrl_v = (e.which == 86 && isCtrl == true) ? true : false; // detect and allow CTRL + V paste.
			//禁止Ctrl+A 和 Ctrl+V
			if(ctrl_a || ctrl_v){
			    updateFlag = false;
			} 
			// 8 is 'backspace' and 46 is 'delete'
			if(e.which == '8' || e.which == '46'){
			     updateFlag = false;
			}
			if(e.keyCode=="37"||e.keyCode=="38"||e.keyCode=="39"||e.keyCode=="40"){
			     updateFlag = false;
			}
			if(updateFlag){
			   updateCounter();
			}
			if( this.value.length >= thelimit && toggleTrim ){
				if(toggleCharDel){
					// first, trim the text a bit so the char trimming won't take forever
					// Also check if there are more than 10 extra chars, then trim. just in case.
					if ( (this.value.length - thelimit) > 10 )
						that.value = that.value.substr(0,thelimit+100);
					var init = setInterval
						( 
							function(){ 
								if( that.value.length <= thelimit ){
									init = clearInterval(init); updateCounter() 
								}
								else{
									// deleting extra chars (one by one)
									that.value = that.value.substring(0,that.value.length-1); 
								}
							} ,charDelSpeed 
						);
				}
				else this.value = that.value.substr(0,thelimit);
			}
		});
	};
})(jQuery);
