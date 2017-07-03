$(function() {
	     /**
	      * 处理tab子页的关闭情况
	      */
		var index_tabs = $('#index_tabs').tabs({
			fit : true,
			border : false,
			onAdd:function(title,index) {
				//添加时出现进度条
				$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				//超时时间：5000ms，最多等待5s，否则自动隐藏，防止子页面没添加progress
				setTimeout(function() {
					$.messager.progress("close");
				},2000);
			},
			onClose:function(title,index) {
			},
			onSelect:function(title){
				//2016-10-12
				//每一次点击时,都要重新加载新的tab
				var exist =$('#index_tabs').tabs('exists',title);
				if(exist){
					//alert('refreshTab');
					//加载速度太慢,想法优化
					//refreshTab();
				}
			},
			onContextMenu : function(e, title) {
				e.preventDefault();
				index_tabsMenu.menu('show', {
					left : e.pageX,
					top : e.pageY
				}).data('tabTitle', title);
			},
			tools : [ {
				iconCls : 'icon-reload',
				handler : refreshTab
			}, {
				iconCls : 'tab_delete',
				handler : function() {
					var index = index_tabs.tabs('getTabIndex', index_tabs.tabs('getSelected'));
					var tab = index_tabs.tabs('getTab', index);
					if (tab.panel('options').closable) {
						index_tabs.tabs('close', index);
					} else {
						$.messager.alert('提示', '[' + tab.panel('options').title + ']不可以被关闭！', 'error');
					}
				}
			} ]
		});  // end  index_tabs
		
	    //刷新界面,重新加载
	    function refreshTab(){
	    	     
	    	    var index_tabs = $('#index_tabs');
				var href = index_tabs.tabs('getSelected').panel('options').href;
				if (href) {/*说明tab是以href方式引入的目标页面*/
					var index = index_tabs.tabs('getTabIndex', index_tabs.tabs('getSelected'));
					index_tabs.tabs('getTab', index).panel('refresh');
				} else {/*说明tab是以content方式引入的目标页面*/
					var panel = index_tabs.tabs('getSelected').panel('panel');
					var frame = panel.find('iframe');
					try {
						if (frame.length > 0) {
							for ( var i = 0; i < frame.length; i++) {
								frame[i].contentWindow.document.write('');
								frame[i].contentWindow.close();
								frame[i].src = frame[i].src;
							}
							if (navigator.userAgent.indexOf("MSIE") > 0) {// IE特有回收内存方法
								try {
									CollectGarbage();
								} catch (e) {
								}
							}
						}
					} catch (e) {
					}
				}
	    }// refreshTab	
	    
		var index_tabsMenu = $('#index_tabsMenu').menu({
			onClick : function(item) {
				var curTabTitle = $(this).data('tabTitle');
				var type = $(item.target).attr('title');

				if (type === 'refresh') {
					index_tabs.tabs('getTab', curTabTitle).panel('refresh');
					return;
				}

				if (type === 'close') {
					var t = index_tabs.tabs('getTab', curTabTitle);
					if (t.panel('options').closable) {
						index_tabs.tabs('close', curTabTitle);
					}
					return;
				}

				var allTabs = index_tabs.tabs('tabs');
				var closeTabsTitle = [];

				$.each(allTabs, function() {
					var opt = $(this).panel('options');
					if (opt.closable && opt.title != curTabTitle && type === 'closeOther') {
						closeTabsTitle.push(opt.title);
					} else if (opt.closable && type === 'closeAll') {
						closeTabsTitle.push(opt.title);
					}
				});

				for ( var i = 0; i < closeTabsTitle.length; i++) {
					index_tabs.tabs('close', closeTabsTitle[i]);
				}
			}
		});//end index_tabsMenu
	 /**
	  * 处理tab页点击时，树形菜单的切换
	  */
	  $('.menu_top a').click(
	       function(){
	           //默认设置所有li为menu_off
               $('.menu_top li').removeClass().addClass('menu_off');
               //去掉所有a 的样式
               $('.menu_top a').removeClass();
               //未选中的
               $(this).removeClass().addClass('white');
               $(this).parent().addClass('menu_on');
               var sys_id=$(this).attr('sys_id');
               //展示不同的子系统
               showTree(sys_id);
	       }
	     );//end menu_top
	     
	     //通用Dialog
	     $.openCommonDlg = function(path, width, height) {
	     	if(width!=undefined && height!=undefined) {
	     		$('#dlg-common').dialog('resize',{
					width: width,
					height: height
				});
	     	}
	     	document.getElementById('frameCommon').src = path;
	     	$('#dlg-common').dialog("open");
	     }
	     $.closeCommonDlg = function() {
	     	$('#dlg-common').dialog("close");
	     }
	     $('#dlg-common').dialog({
		    title: '系统',
		    width: 800,
		    height:500,
		    closed: true,
		    cache: false,
		    modal: true
		});
	  }
	 );
   /**
    * 显示导航树
    */
   function showTree(sys_id){
	   var west = $("#index-layout").layout("panel","west");
       west.panel('resize',{width: 220}).panel("setTitle","模块导航").panel('refresh',config.ctx+'/login/left?sys_id='+sys_id);
       $("#index-layout").layout("resize");
   }
   /***
    * 添加tab页
    */
   function addTab(params) {
	   /*
   		if($('#index_tabs').tabs('exists', "修改试题") || $('#index_tabs').tabs('exists', "添加试题")) {
   			$.messager.alert('提示',"请先提交“修改试题/添加试题”内容！",'error');
   			return;
   		}
   		*/
		var iframe = '<iframe src="' + params.url + '" frameborder="0" 	scrolling="no" style="border:0;width:100%;height:98%;"></iframe>';
		var t = $('#index_tabs');
		var opts = {
			title : params.title,
			closable : true,
			iconCls : params.iconCls,
			content : iframe,
			border : false,
			fit : true
		};
		if (t.tabs('exists', opts.title)) {
			t.tabs('select', opts.title);
		} else {
			t.tabs('add', opts);
		}
	}
	
	function getTabSelected() {
		return $('#index_tabs').tabs('getSelected');
	}