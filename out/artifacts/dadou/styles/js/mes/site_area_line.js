/**
 * 工厂->车间-->生产线联动
 * 支持工厂单列表、工厂－车间联动、工厂->车间-->生产线联动
 * 对象ID必须定位为siteList、areaList、lineList
 */
$(function(){
	var ISEXIST_AREALIST=false;//车间对象是否存在
	var ISEXIST_LINELIST=false;//生产线对象是否存在
	if(document.getElementById("areaList")!=undefined){
		ISEXIST_AREALIST=true;
	}
	if(document.getElementById("lineList")!=undefined){
		ISEXIST_LINELIST=true;
	}
	//工厂下拉框设置
	$('#siteList').combobox({
		panelHeight:'auto',
		valueField:'key',
		textField:'value',
		editable:false,
		url:config.ctx+'/im/site/siteListLimit',
		onSelect: function(rec){
			if(ISEXIST_AREALIST==true){
				var url =config.ctx+'/im/area/areaList?siteId='+rec.key+'&random='+Math.random();
				$('#areaList').combobox('reload',url);
				$('#areaList').combobox('setValue','-1');
			}
	    },
		onLoadSuccess:function(){
			//默认显示当前工厂
			$('#siteList').combobox('setValue',CURRENT_SITE_ID);
		}
	});

	//车间下拉框设置
	if(ISEXIST_AREALIST==true){
		$('#areaList').combobox({
			panelHeight:'auto',
			valueField:'key',
			textField:'value',
			editable:false,
			url:config.ctx+'/im/area/areaList?siteId='+CURRENT_SITE_ID,
			onSelect: function(rec){
				if(ISEXIST_LINELIST==true){
					var url =config.ctx+'/im/line/lineList?areaId='+rec.key+'&random='+Math.random();
					$('#lineList').combobox('reload',url);
				}
		    },
			onLoadSuccess:function(){
				var dataArray=$('#areaList').combobox('getData');
				if(dataArray.length>1){//默认显示第一个车间
					$('#areaList').combobox('setValue',dataArray[1].key);
					if(ISEXIST_LINELIST==true){
						var url =config.ctx+'/im/line/lineList?areaId='+dataArray[1].key+'&random='+Math.random();
						$('#lineList').combobox('reload',url);
					}
				}
			}
		});

	}
	if(ISEXIST_LINELIST==true){
		//生产线下拉框设置
		$('#lineList').combobox({
			panelHeight: 300,
			valueField:'key',
			textField:'value',
			editable:false,
			data:[{key:'-1',value:'--全部--'}],
			onLoadSuccess:function(){
				//默认显示全部
				$('#lineList').combobox('setValue','-1');
			}
		});

	}
    
});	