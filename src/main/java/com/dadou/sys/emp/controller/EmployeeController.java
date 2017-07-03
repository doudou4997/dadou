package com.dadou.sys.emp.controller;

import com.dadou.sys.CmsConst;
import com.dadou.sys.dept.service.DepartmentService;
import com.dadou.sys.emp.pojos.Employee;
import com.dadou.sys.emp.service.EmployeeService;
import com.dadou.sys.role.pojos.Role;
import com.dadou.sys.role.service.RoleService;
import com.framework.core.exception.ResultMsg;
import com.framework.core.page.Pagination;
import com.framework.core.utils.ActionContextUtils;
import com.framework.core.utils.ExceptionUtils;
import com.framework.core.utils.JsonUtils;
import com.framework.core.utils.StringUtils;
import com.framework.core.web.controller.BaseController;
import com.framework.core.web.ui.OptionsString;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;


/**
 * Employee对象的操作Action <br>
 * 负责Employee的管理及各种操作
 */
@Controller
@RequestMapping(value="/emp",produces="text/html;charset=UTF-8")
@Scope("prototype")
public class EmployeeController extends BaseController {
	public static final String PREFIX = "modules/sys/emp";
	/**Employee服务类**/
	@Resource(name="sys_employeeService")
	private EmployeeService employeeService;
	/**角色服务类**/
	@Resource(name="sys_roleService")
	private RoleService roleService;
	/**部门的服务类**/
	@Resource(name="departmentService")
	private DepartmentService departmentService;
	/** 工厂权限 **/
	//@Resource(name="im_siteService")
	//private SiteService siteService;
	/**
	 * 员工列表
	 */
	@RequestMapping(value="/add")
	public String add(Map<String,Object> map) {
		//List<Site> siteList = siteService.findAll();
		//map.put("siteList", siteList);
		map.put("radioGenderList", getRadioGenderListValues());
		map.put("emp", new Employee());
		
		//工厂列表
		return PREFIX+"/add";
	}
	/**
	 * 员工列表
	 */
	@RequestMapping(value="/list")
	public String list(Map<String,Object> map) {
		//默认情况下,分配权限时出现权限树
		return PREFIX+"/list";
	}

	/**
	 * Ajax列表
	 */
	@RequestMapping(value="/listAjax")
	@ResponseBody
	public String listAjax(@RequestParam(required=false) String userName, @RequestParam(required=false) String name) {
		// 从请求参数中获取pageNumber
		String[] str = ActionContextUtils.getParameters(CmsConst.PAGE_NUMBER_E);
		int pageNo = NumberUtils.toInt(str[0], 1);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("userName", userName);
		queryMap.put("name", name);
		queryMap.put(ORDER, this.getOrder());
		queryMap.put(SORT, this.getSort());
		Pagination<Employee> pagination = this.employeeService.findByPage(
				pageNo, this.getPageSize4E(), queryMap);
		List<Employee> elist = pagination.getList();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>(elist.size());
		for (Employee e : elist) {
		    Map<String, String> emap = new HashMap<String, String>();
		    String empId = e.getId();
		    emap.put("id", empId);
		    emap.put("userName", e.getUserName());
		    emap.put("name", e.getName());
		    emap.put("gender", e.getGender());
		    emap.put("jobName", e.getJobName());
		    emap.put("deptName", e.getDeptName());
		    emap.put("userCode", String.valueOf(e.getUserCode()));
		    emap.put("mobile", e.getMobile());
		    emap.put("email", e.getEmail());
		    list.add(emap);
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(TOTAL, pagination.getMaxElements());
		resultMap.put(ROWS, list);
		String jsonData = JsonUtils.toJsonIncludeNull(resultMap);
		return jsonData;
	}	


	/**
	 * 保存员工,转向列表界面
	 */
	@RequestMapping(value="/save")
	@ResponseBody
	public String save(Employee emp) {
		String userCode = emp.getUserCode();
		// 如果用户名存在提示错误
		if (employeeService.findEmpByUserName(emp.getUserName())) {
			this.putRootJson(ResultMsg.SUCCESS, false);
			this.putRootJson(ResultMsg.MSG, "该用户名已经存在");
			return getJsonStr();
		}
		// 如果员工号已经存在,返回添加界面
		if (employeeService.findEmpByUserCode(userCode)) {
			this.putRootJson(ResultMsg.SUCCESS, false);
			this.putRootJson(ResultMsg.MSG, "员工编号已经存在");
			return getJsonStr();
		}
		String roleIds = ActionContextUtils.getParameter("roleIds");

		if(StringUtils.isEmpty(roleIds)){
			this.putRootJson(ResultMsg.SUCCESS, false);
			this.putRootJson(ResultMsg.MSG, "请为员工分配角色!");
			return getJsonStr();
		}
//		String siteIds = ActionContextUtils.getParameter("siteIds");
//		if(StringUtils.isEmpty(siteIds)){
//			this.putRootJson(ResultMsg.SUCCESS, false);
//			this.putRootJson(ResultMsg.MSG, "请为员工分配工厂!");
//			return getJsonStr();
//		}
		
		try {
			// 把数据封装到emp中
			//自动生成ID
			//emp.setId(getUUID());
			this.employeeService.save(emp,roleIds);
			this.putRootJson(ResultMsg.SUCCESS, true);
			this.putRootJson(ResultMsg.MSG, "员工保存成功!");
		} catch (Exception e) {
			this.putRootJson(ResultMsg.SUCCESS, false);
			this.putRootJson(ResultMsg.MSG, "员工保存失败!");
			String error = ExceptionUtils.formatStackTrace(e);
			logger.error(error);
			saveLog(error);
		}
		return getJsonStr();
	}

	/**
	 * 更新用户
	 */
	@RequestMapping(value="/update")
	@ResponseBody
	public String update(Employee emp) {
		// 封装实体类
		try {
			// 把数据封装到emp中
			this.employeeService.updateEmp(emp);
			this.putRootJson(ResultMsg.SUCCESS, true);
			this.putRootJson(ResultMsg.MSG, "保存成功");
		} catch (Exception ex) {
			this.putRootJson(ResultMsg.SUCCESS, false);
			this.putRootJson(ResultMsg.MSG, "保存失败");
			String error = ExceptionUtils.formatStackTrace(ex);
			logger.error(error);
			saveLog(error);
		}
		return getJsonStr();
	}
	/**
	 * 根据用户id冻结用户
	 */
	@RequestMapping(value="/freeze")
	@ResponseBody
	public String freeze(Map<String,Object> map,@RequestParam Integer id) {
		try {
			this.employeeService.doFreeze(id);
			map.put(ResultMsg.MSG, "用户冻结成功!");
		} catch (Exception ex) {
			String error = ExceptionUtils.formatStackTrace(ex);
			logger.error(error);
			map.put(ResultMsg.MSG, "用户冻结失败!");
			
		}
		return getJsonStr();
	}

	/**
	 * 根据用户id解冻用户
	 */
	@RequestMapping(value="/unFreeze")
	@ResponseBody
	public String unFreeze(Map<String,Object> map,@RequestParam Integer id) {
		try {
			this.employeeService.doUnFreeze(id);
			map.put(ResultMsg.MSG, "用户解冻成功!");
		} catch (Exception ex) {
			String error = ExceptionUtils.formatStackTrace(ex);
			logger.error(error);
			map.put(ResultMsg.MSG, "用户解冻失败!");
		}
		return getJsonStr();
	}

	/**
	 * 验证员工号是否存在
	 */
	@RequestMapping(value="/validateCode")
	@ResponseBody
	public String validateCode(@RequestParam String userCode) {
		if (employeeService.findEmpByUserCode(userCode)) {
			this.putRootJson(ResultMsg.SUCCESS, false);
		} else {
			this.putRootJson(ResultMsg.SUCCESS, true);
		}
		return this.getJsonStr();
	}
	/**
	 * tab页中利用ajax方法，转到编辑用户界面
	 * 
	 */
	@RequestMapping(value="/edit")
	public String edit(@RequestParam String id, Map<String,Object> map) {
		try {
			map.put("radioGenderList", getRadioGenderListValues());
			Employee emp = this.employeeService.findById(id);
			// 部门id
			emp.setOldDeptId(emp.getDeptId());
			map.put("emp", emp);
		} catch (Exception ex) {
			String error = ExceptionUtils.formatStackTrace(ex);
			logger.error(error);
			saveLog(error);
		}
		return PREFIX+"/edit";
	}
	/**
	 * 利用ajax方法，转到分配工厂界面
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/assignSiteAjax")
	public String assignSiteAjax(Map<String,Object> map) {
//		try {
//			String empId = ActionContextUtils.getParameter("id");
//			Set<Site> assignedSet = new HashSet<Site>();
//			/**已分配工厂**/
//			List<Site> assignedList = new ArrayList<Site>(0);
//			if(StringUtils.isNotEmpty(empId)){
//				// 获取员工对象
//				Employee emp = employeeService.findById(empId);
//				map.put("emp", emp);
//				List<Site> siteList = siteService.findSitesOfEmp(empId);
//
//				// 设置所有已工厂角色
//				// 过滤重复的Site
//				assignedSet.addAll(siteList);
//				assignedList.addAll(assignedSet);
//
//			}
//			/**所有工厂**/
//			List<Site> allSites = siteService.findAll();
//			// 获取未分配工厂集合
//			@SuppressWarnings("unchecked")
//			Collection<Site> unAssignedList = CollectionUtils.subtract(
//					allSites,assignedList);
//			//名称排序
//			Collections.sort(allSites, new Comparator<Site>() {
//				public int compare(Site site1, Site site2) {
//					return site1.getName().compareTo(site1.getName());
//				}
//			});
//			//已分配工厂
//			map.put("assignedList", assignedList);
//			//所有工厂
//			map.put("unAssignList", unAssignedList);
//		} catch (Exception ex) {
//			String error = ExceptionUtils.formatStackTrace(ex);
//			logger.error(error);
//			saveLog(error);
//		}

		return PREFIX+"/assignSiteAjax";
	}
	/**
	 * 利用ajax方法，转到分配角色界面
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/assignRoleAjax")
	public String assignRoleAjax(Map<String,Object> map) {
		try {
			String empId = ActionContextUtils.getParameter("id");
			Set<Role> assignedSet = new HashSet<Role>();
			/**已分配角色**/
			List<Role> assignedList = new ArrayList<Role>(0);
			if(StringUtils.isNotEmpty(empId)){
				// 获取员工对象
				Employee emp = employeeService.findById(empId);
				map.put("emp", emp);
				List<Role> roleList = roleService.findRolesOfEmp(empId);

				// 设置所有已分配角色
				// 过滤重复的Role
				assignedSet.addAll(roleList);
				assignedList.addAll(assignedSet);
				
			}
			/**所有角色**/
			List<Role> allRoles = roleService.findAll(null);
			// 获取未分配角色集合
			@SuppressWarnings("unchecked")
			Collection<Role> unAssignedList = CollectionUtils.subtract(
					allRoles,assignedList);
			//名称排序
			Collections.sort(allRoles, new Comparator<Role>() {
				public int compare(Role role1, Role role2) {
					return role1.getRoleName().compareTo(role2.getRoleName());
				}
			});
			//已分配角色
			map.put("assignedList", assignedList);
			//所有角色
			map.put("unAssignList", unAssignedList);
		} catch (Exception ex) {
			String error = ExceptionUtils.formatStackTrace(ex);
			logger.error(error);
			saveLog(error);
		}

		return PREFIX+"/assignRoleAjax";
	}
	/**
	 * 初始化密码操作
	 */
	@RequestMapping(value="/initPass")
	@ResponseBody
	public String initPass(@RequestParam String id) {
		try {
			this.employeeService.doInitPass(id);
			this.putRootJson(ResultMsg.SUCCESS, true);
			this.putRootJson(ResultMsg.MSG, "密码初始化成功!");
		} catch (Exception ex) {
			this.putRootJson(ResultMsg.SUCCESS, false);
			this.putRootJson(ResultMsg.MSG, "密码初始化失败!");
			String error = ExceptionUtils.formatStackTrace(ex);
			logger.error(error);
			saveLog(error);
		}
		return this.getJsonStr();
	}

	/**
	 * 分配角色
	 */
	@RequestMapping(value="/doAssign")
	@ResponseBody
	public String doAssign(Map<String,Object> map) {
		try {
			String empId = ActionContextUtils.getParameter("id");
			String roleIds = ActionContextUtils.getParameter("roleIds");
			putRootJson(ResultMsg.MSG, "角色分配成功!");
			this.employeeService.doAssign(empId, roleIds);
		} catch (Exception e) {
			String error = ExceptionUtils.formatStackTrace(e);
			// 记录异常信息
			logger.error(error);
			putRootJson(ResultMsg.MSG, "角色分配失败!");
		}
		return getJsonStr();
	}
	/**
	 * 分配角色
	 */
	@RequestMapping(value="/doAssignSite")
	@ResponseBody
	public String doAssignSite(Map<String,Object> map) {
		try {
			String empId = ActionContextUtils.getParameter("id");
			String siteIds = ActionContextUtils.getParameter("siteIds");
			putRootJson(ResultMsg.MSG, "角色分配成功!");
			this.employeeService.doAssignSite(empId, siteIds);
		} catch (Exception e) {
			String error = ExceptionUtils.formatStackTrace(e);
			// 记录异常信息
			logger.error(error);
			putRootJson(ResultMsg.MSG, "角色分配失败!");
		}
		return getJsonStr();
	}
	/**
	 * 菜单树形界面里的详情
	 */
	@RequestMapping(value="/detail")
	public String detail(@RequestParam String id) {
	    Employee emp = this.employeeService.findById(id);
	    ActionContextUtils.setAtrributeToRequest("emp", emp);
	    return PREFIX+"/detail";
	}
	/**
	 * 初始化性别
	 */
	private List<OptionsString> getRadioGenderListValues() {
		TreeSet<OptionsString> set = new TreeSet<OptionsString>();
		set.add(new OptionsString("女","女",1));
		set.add(new OptionsString("男","男",0));
		List<OptionsString> radioGenderList = new ArrayList<OptionsString>();
		radioGenderList.addAll(set);
		return radioGenderList;
	}
	
	/**
	 * 删除用户
	 * id  用户id
	 * wangs
	 */
	@RequestMapping(value="/removeEmp")
	@ResponseBody
	public String removeEmp(@RequestParam String id){
		try {
			employeeService.remove(id);
			putRootJson(ResultMsg.SUCCESS, true);
			putRootJson(ResultMsg.MSG, "角色删除成功!");
		} catch (Exception e) {
			putRootJson(ResultMsg.SUCCESS, false);
			putRootJson(ResultMsg.MSG, "角色删除失败...");
			String error = ExceptionUtils.formatStackTrace(e);
			// 记录异常信息
			logger.error(error);
		}
		return getJsonStr();
	}
	
    
}
