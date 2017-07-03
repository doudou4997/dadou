package com.dadou.sys.menu.controller;

import com.dadou.sys.CmsConst;
import com.dadou.sys.menu.pojos.Menu;
import com.dadou.sys.menu.service.MenuService;
import com.dadou.sys.role.service.RoleService;
import com.dadou.sys.sys.pojos.Sys;
import com.dadou.sys.sys.service.SysService;
import com.dadou.sys.tree.pojos.MenuItem;
import com.dadou.sys.tree.pojos.TreeNode;
import com.dadou.sys.tree.service.TreeUtils;
import com.framework.core.exception.ResultMsg;
import com.framework.core.utils.ActionContextUtils;
import com.framework.core.utils.ExceptionUtils;
import com.framework.core.utils.JsonUtils;
import com.framework.core.utils.StringUtils;
import com.framework.core.web.controller.BaseController;
import com.framework.core.web.ui.OptionsString;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 菜单管理Action
 * 
 * @author gaofeng
 * 
 */

@Controller("sys_MenuController")
@RequestMapping(value="/menu",produces="text/html;charset=UTF-8")
@Scope("prototype")
public class MenuController extends BaseController {
	private static final String PREFIX = "modules/sys/menu";
	/**
	 * 菜单服务类
	 */
	@Resource(name="sys_menuService")
	private MenuService menuService;
	/**
	 * 角色服务类
	 */
	@Resource(name="sys_roleService")
	private RoleService roleService;
	/**
	 * 子系统Service
	 */
	@Resource(name="sys_sysService")
	private SysService sysService;
	// ////////////////////////////////////
	// ///各种业务操作
	// ///////////////////////////////////
	/**
	 * 权限树管理界面初始化
	 */
	@RequestMapping(value="/manage")
	public String manage(Map<String,Object> map) throws Exception {
		List<Sys> sysList = sysService.findAll();
		map.put("sysList", sysList);
		return PREFIX+"/menuManage";
	}
	/**
	 * 显示树形结构
	 */
	@RequestMapping(value="/showTree")
	@ResponseBody
	public String showTree() {
		String sysId = ActionContextUtils.getParameter("sysId");
		if(StringUtils.isNotEmpty(sysId)){
			// 获取MenuItem列表 sysId 为-1 表示默认跟节点
			List<MenuItem> menuItemList = menuService.findMenuAndPrivileges(CmsConst.MENU_ROOT_ID,sysId);
			TreeNode root = TreeUtils.createTree(menuItemList,"-1");
			String json = "["+ JsonUtils.toJson(root) + "]";
			this.putRootJson("menus", json);	
		}
		return this.getJsonStr();
	}
	/**
	 * 转向添加界面
	 */
	@RequestMapping(value="/add")
	public String add(@RequestParam String parentId, Map<String,Object> map) {
		map.put("parentId", parentId);
		map.put("menu", new Menu());
		map.put("treeList", getTreeList());
		map.put("useList",getUseList());
		return PREFIX+"/addMenu";
	}
	/**
	 * 保存菜单对象
	 */
	@RequestMapping(value="/save")
	@ResponseBody
	public String save(Menu menu /*自动给menu赋值*/) {
		try {
			Menu parentMenu = this.menuService.findById(menu.getParentId());
			
			this.putRootJson(ResultMsg.MSG, "保存成功!");
			//父菜单sysId
			String sysId = parentMenu.getSysId();
			menu.setSysId(sysId);
			//增加级别
			menu.setLevel(parentMenu.getLevel()+1);
			//menu.setId(getUUID());
			menuService.save(menu);
		} catch (Exception ex) {
			String error = ExceptionUtils.formatStackTrace(ex);
			logger.error(error);
			this.putRootJson(ResultMsg.MSG, "保存失败!");
		}
		return getJsonStr();
	}

	/**
	 * 根据id查询菜单对象,并转向菜单编辑页面
	 */
	@RequestMapping(value="/edit")
	public String edit(@RequestParam String id, Map<String,Object> map) {
		try {
			Menu menu = menuService.findById(id);
			map.put("menu", menu);
			map.put("treeList", getTreeList());
			map.put("useList",getUseList());
		} catch (Exception ex) {
			String error = ExceptionUtils.formatStackTrace(ex);
			logger.error(error);
		}
		return PREFIX+"/editMenu";
	}
	/**
	 * 详情
	 */
	@RequestMapping(value="/detail")
	public String detail(@RequestParam String id, Map<String,Object> map) {
		Menu menu = this.menuService.findById(id);
		map.put("menu", menu);
		return PREFIX+"/detail";
	}
	/**
	 * 更新菜单对象
	 */
	@RequestMapping(value="/update")
	@ResponseBody
	public String update(@RequestParam String id, Menu menuTmp) {
		try {
			// 加载对象
			Menu menu = this.menuService.findById(id);
			// 把数据封装到menu中
			menu.setName(menuTmp.getName());
			menu.setIsTree(menuTmp.getIsTree());
			menu.setSortNum(menuTmp.getSortNum());
			menu.setTarget(menuTmp.getTarget());
			menu.setTitle(menuTmp.getTitle());
			menu.setUrl(menuTmp.getUrl());
			menu.setIcon(menuTmp.getIcon());
			// 设置提示信息
			this.putRootJson(ResultMsg.MSG, "更新成功!");
			this.menuService.update(menu);
			/**
			 * 不能在同一个Service
			 */
			//this.roleService.refreshAll();
			this.putRootJson(ResultMsg.SUCCESS, true);
		} catch (Exception ex) {
			this.putRootJson(ResultMsg.SUCCESS, false);
			String error = ExceptionUtils.formatStackTrace(ex);
			logger.error(error);
			this.putRootJson(ResultMsg.MSG, "更新失败!");
		}
		return getJsonStr();
	}

	/**
	 * 删除菜单对象
	 */
	@RequestMapping(value="/remove")
	@ResponseBody
	public String remove(@RequestParam String id) {
		try {
			menuService.remove(id);
			this.putRootJson(ResultMsg.MSG, "删除成功!");
		} catch (Exception ex) {
			ex.printStackTrace();
			String error = ExceptionUtils.formatStackTrace(ex);
			logger.error(error);
			this.putRootJson(ResultMsg.MSG, "删除失败!");
		}
		return getJsonStr();
	}
	/**
	 * 重新刷新角色信息
	 * @return
	 */
	@RequestMapping(value="/refresh")
	@ResponseBody
	public String refresh() {
		try {
			// 重新刷新角色信息
			//this.roleService.refreshAll();
			this.putRootJson(ResultMsg.SUCCESS, true);
		} catch (Exception e) {
			this.putRootJson(ResultMsg.SUCCESS, false);
			String error = ExceptionUtils.formatStackTrace(e);
			logger.error(error);
		}
		return getJsonStr();
	}

	public List<OptionsString> getTreeList() {
		//判断是否是导航树状结构中的节点
		List<OptionsString> treeList = new ArrayList<OptionsString>();
		treeList.add(new OptionsString("1", "是"));
		treeList.add(new OptionsString("0", "否"));
		return treeList;
	}
	public List<OptionsString> getUseList() {
	    //是否常用
		List<OptionsString> useList = new ArrayList<OptionsString>();
		useList.add(new OptionsString("1", "是"));
		useList.add(new OptionsString("0", "否"));
		return useList;
	}
}
