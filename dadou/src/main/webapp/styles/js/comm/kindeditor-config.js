
var kindeditor={};

/**
 * 配置
 */
kindeditor.config = (function() { 
	return {
		/**
		 * 工具栏
		 */
		tool:function() { 
			return ['source', '|','undo', 'redo', 'cut', 'copy', 'paste',
		         'plainpaste', 'wordpaste', '|', /*'justifyleft', 'justifycenter', 'justifyright',
		         'justifyfull',*/ 'insertorderedlist', 'insertunorderedlist',/* 'indent', 'outdent', 'subscript',
		         'superscript', */'|', 'selectall',
		         'fontcolor','emoticons', '|','table', 'color', 'bgcolor','forecolor', 'hilitecolor','fontsize',  'bold',  
		         'italic', 'underline', 'strikethrough', 'removeformat', '|',  'advtable', 'hr'];
		},
		/**
		 * 颜色
		 */
		color:function() {
			return [ 
				['#E53333', '#E56600', '#FF9900', '#64451D', '#DFC5A4', '#FFE500'],
				['#009900', '#006600', '#99BB00', '#B8D100', '#60D978', '#00D5FF'],
				['#337FE5', '#003399', '#4C33E5', '#9933E5', '#CC33E5', '#EE33EE'],
				['#FFFFFF', '#CCCCCC', '#999999', '#666666', '#333333', '#000000'],
				['#F0FFFF', '#F0E68C', '#EEEE00', '#EEE8AA', '#EEDFCC', '#EED5B7'],
				['#00FF00', '#00EE76', '#00CDCD', '#00BFFF', '#008B45', '#006400'],
				['#FFA54F', '#FF8C00', '#FF7F50', '#FF6EB4', '#FF4500', '#FF3030']
			];
		},
		/**
		 * 基础配置
		 */
		base:function() {
			return  {   
				items : kindeditor.config.tool(),  
				colorTable : kindeditor.config.color(),
				pasteType:1,  
				newlineTag:'br',/*换行格式*/
				cssData : 'body {font-size: 14px;}',
				filterMode:false  
			};
		}
		
	}
})();
