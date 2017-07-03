package com.dadou.sys.role.controller;

import com.dadou.sys.CmsConst;
import com.dadou.sys.role.pojos.Role;
import com.dadou.sys.role.pojos.RoleMenu;
import com.dadou.sys.role.pojos.RolePrivilege;
import com.dadou.sys.role.service.RoleService;
import com.dadou.sys.sys.pojos.Sys;
import com.dadou.sys.sys.service.SysService;
import com.dadou.sys.tree.pojos.MenuItem;
import com.dadou.sys.tree.pojos.TreeNode;
import com.dadou.sys.tree.service.TreeUtils;
import com.framework.core.exception.ResultMsg;
import com.framework.core.page.Pagination;
import com.framework.core.utils.ActionContextUtils;
import com.framework.core.utils.ExceptionUtils;
import com.framework.core.utils.JsonUtils;
import com.framework.core.utils.StringUtils;
import com.framework.core.web.controller.BaseController;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现角色的各种业务操作
 */
@Controller
@RequestMapping(value="/role",produces="text/html;charset=UTF-8")
@Scope("prototype")
public class RoleController extends BaseController {
	public static final String PREFIX = "modules/sys/role";
	/**
	 * 角色服务类
	 */
	@Resource(name="sys_roleService")
	private RoleService roleService;
	@Resource(name="sys_sysService")
	private SysService sysService;
	
	/**
	 * 转向角色添加界面
	 */
	@RequestMapping(value="/add")
	public String add(Map<String,Object> map) {
		//获得所有子系统列表
		List<Sys> sysList = sysService.findAll();
		map.put("sysList", sysList);
		map.put("role", new Role());
		return PREFIX+"/add";
	}

	/**
	 * 角色保存
	 */
	@RequestMapping(value="/save")
	@ResponseBody
	public String save(Role role) {
		try {
			// 保存角色
			//role.setId(getUUID());
			this.roleService.saveRole(role);
			this.putRootJson(ResultMsg.MSG, "角色保存成功!");
		} catch (Exception ex) {
			String error = ExceptionUtils.formatStackTrace(ex);
			logger.error(error);
			this.putRootJson(ResultMsg.MSG, "角色保存失败!");
		}
		return getJsonStr();
	}

	/**
	 * 角色删除
	 */
	@RequestMapping(value="/remove")
	@ResponseBody
	public String remove(@RequestParam String id) {
		// 判断该角色是否可以删除
		if (!roleService.findExsitEmpOfRole(id)) {
			this.roleService.removeRole(id);
			this.putRootJson(ResultMsg.SUCCESS, true);
			this.putRootJson(ResultMsg.MSG, "角色删除成功!");
		} else {
			this.putRootJson(ResultMsg.SUCCESS,false);
			this.putRootJson(ResultMsg.MSG, "角色删除失败!");
		}
		return this.getJsonStr();
	}

	/**
	 * 角色列表
	 */
	@RequestMapping(value="/list")
	public String list() {
		/*// 初始化系统列表
		map.put("role", new Role());
		List<Sys> sysList = new ArrayList<>();
		sysList.add(new Sys("-1","--全部--"));
		sysList.addAll(1,sysService.findAll());
		map.put("sysList", sysList);*/
		return PREFIX+"/list";
	}
	
	/**
	 * 角色类型下拉框Ajax
	 */
	@RequestMapping(value="/sysListAjax")
	@ResponseBody
	public String sysListAjax(){
		List<Sys> sysList = sysService.findAll();
		sysList.add(0, new Sys("-1", "--全部--"));
		return JsonUtils.toJsonIncludeNull(sysList);
	}
	
	
	/**
	 * Ajax列表
	 */
	@RequestMapping(value="/listAjax")
	@ResponseBody
	public String listAjax(@RequestParam(required=false) String roleName) {
		// 从请求参数中获取pageNumber
		String sysId = ActionContextUtils.getParameter("sysId");
		String[] str = ActionContextUtils.getParameters(CmsConst.PAGE_NUMBER_E);
		int pageNo = NumberUtils.toInt(str[0], 1);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(roleName)){
			queryMap.put("roleName", roleName);
		}
		if(StringUtils.isNotEmpty(sysId)&& !sysId.equals("-1")){
			queryMap.put("sysId", sysId);
		}
		Pagination<Role> pagination = this.roleService.findByPage(pageNo, this.getPageSize4E(),queryMap);
		// 记录总数
		int max = pagination.getMaxElements();
		// 生成JSON格式的数据
		List<Role> plist = pagination.getList();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>(plist.size());
		for (Role dt : plist) {
		    Map<String, String> tmap = new HashMap<String, String>();
		    String id = dt.getId();
		    tmap.put("id", String.valueOf(id));
		    tmap.put("roleName", dt.getRoleName());
		    tmap.put("description", dt.getDescription());
		    list.add(tmap);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(TOTAL, max);
		map.put(ROWS, list);
		String jsonData = JsonUtils.toJsonIncludeNull(map);
		return jsonData;
	}


	/**
	 * 转向角色编辑界面
	 */
    @RequestMapping(value="/edit")
	public ModelAndView edit(@RequestParam String id, Map<String,Object> map) {
    	List<Sys> sysList = sysService.findAll();
    	map.put("sysList",sysList);
		Role role = this.roleService.findById(id);
		ModelAndView modelAndView = this.getModelAndView();
		modelAndView.addObject(role);
		modelAndView.setViewName(PREFIX+"/edit");
		return modelAndView;
	}

	/**
	 * 角色更新
	 */
    @RequestMapping(value="/update")
    @ResponseBody
	public String update(Role role) {
		try {
			this.roleService.updateRole(role);
			this.putRootJson(ResultMsg.SUCCESS, true);
			this.putRootJson(ResultMsg.MSG, "更新成功!");
		} catch (Exception ex) {
			this.putRootJson(ResultMsg.SUCCESS, false);
			this.putRootJson(ResultMsg.MSG, "更新失败!");
			String error = ExceptionUtils.formatStackTrace(ex);
			logger.error(error);
		}
		return getJsonStr();
	}

	/**
	 * 分配权限:转到分配权限界面
	 */
    @RequestMapping(value="/assignMenu")
    @ResponseBody
	public String assignMenu(@RequestParam String id) {
		Role role = this.roleService.findById(id);
		String sysId=role.getSysId();
		// 初始化所有的权限信息
		List<MenuItem> menuItemList = roleService.findMenuAndPrivileges(CmsConst.MENU_ROOT_ID,sysId);
		TreeNode root = TreeUtils.createTree(menuItemList,"1");
		String json = "["+ JsonUtils.toJson(root) + "]";
		this.putRootJson("menus", json);
		return this.getJsonStr();
	}

	/**
	 * 分配权限:菜单权限和操作权限
	 */
    @RequestMapping(value="/doAssign")
    @ResponseBody
	public String doAssign() {
		try {
			String roleId = ActionContextUtils.getParameter("id");
			String[] privilegeIds = ActionContextUtils.getParameters("privilegeIds[]");
			String[] menuIds = ActionContextUtils.getParameters("menuIds[]");
			roleService.doAssign(menuIds, privilegeIds,roleId);
			this.putRootJson(ResultMsg.SUCCESS, true);
			this.putRootJson(ResultMsg.MSG, "分配成功!");
		} catch (Exception ex) {
			this.putRootJson(ResultMsg.SUCCESS, false);
			String error = ExceptionUtils.formatStackTrace(ex);
			logger.error(error);
			this.putRootJson(ResultMsg.MSG, "分配失败!");
		}
		return this.getJsonStr();
	}

	/**
	 * 初始化子系统列表并跳转至分配权限页面
	 */
    @RequestMapping(value="/redirect")
	public String redirect(@RequestParam String id, Map<String,Object> map) {
		Role role = this.roleService.findById(id);
		map.put("role", role);
		// 设置菜单Ids
		List<RoleMenu> rmset = roleService.findRoleMenus(id);
		String[] menuIds = new String[rmset.size()];
		int index = 0;
		for (RoleMenu rm : rmset) {
			menuIds[index] = rm.getMenuId();
			index++;
		}
		// 设置权限Ids
		List<RolePrivilege> pSet = roleService.findRolePrivileges(id);
		String[] privilegeIds = new String[pSet.size()];
		index = 0;
		for (RolePrivilege p : pSet) {
			privilegeIds[index] = p.getPrivilegeId();
			index++;
		}
		String mIds = StringUtils.join(menuIds, CmsConst.SPLITCHAR);
		ActionContextUtils.setAtrributeToRequest("mIds", mIds);
		String pIds = StringUtils.join(privilegeIds, CmsConst.SPLITCHAR);
		ActionContextUtils.setAtrributeToRequest("pIds", pIds);
		
		ActionContextUtils.setAtrributeToRequest("role", role);
		
		return PREFIX+"/assignMenu";
	}
}
