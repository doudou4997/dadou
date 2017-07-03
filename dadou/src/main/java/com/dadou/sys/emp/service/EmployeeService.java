package com.dadou.sys.emp.service;

import com.dadou.sys.CmsConst;
import com.dadou.sys.emp.dao.EmployeeDao;
import com.dadou.sys.emp.pojos.Employee;
import com.dadou.sys.emp.pojos.UserMenu;
import com.dadou.sys.menu.dao.MenuDao;
import com.dadou.sys.privilege.dao.PrivilegeDao;
import com.dadou.sys.sys.dao.SysDao;
import com.framework.core.page.Pagination;
import com.framework.core.utils.MD5;
import com.framework.core.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Employee业务逻辑层 服务类
 */
@Service(value="sys_employeeService")
public class EmployeeService {
    @Resource(name="sys_employeeDao")
    private EmployeeDao employeeDao;
	/**
	 * 菜单操作Dao
	 */
    @Resource(name="sys_menuDao")
	private MenuDao menuDao;
	/**
	 * 权限操作Dao
	 */
    @Resource(name="sys_privilegeDao")
	private PrivilegeDao privilegeDao;

    /**
     * 子系统列表
     */
    @Resource(name="sys_sysDao")
    private SysDao sysDao;
	/**
	 * 添加用户,同时把数据保存到中间表中
	 */
	public void save(Employee emp) {
		// 设置删除标识 未删除
		emp.setDeleteFlag(0);
		// 设置冻结标识 未冻结
		emp.setFreeze("0");
		// 密码加密
		MD5 md = new MD5();
		// 得到加密以后的密码
		String password = md.getMD5ofStr(emp.getPassword());
		emp.setPassword(password);
		employeeDao.save(emp);
	}

	/**
	 * 冻结用户 1:冻结 0：解冻
	 */
	public void doFreeze(Integer id) {
	    employeeDao.doFreeze(id);
	}

	/**
	 * 解冻用户
	 */
	public void doUnFreeze(Integer id) {
	    employeeDao.doUnFreeze(id);
	}

	/**
	 * 判断给定的userCode是否存在
	 */
	public boolean findEmpByUserCode(String userCode) {
		return employeeDao.isExist(userCode);
	}
    /**
     * 给定userName 判断是否存在
     */
	public boolean findEmpByUserName(String userName){
		return employeeDao.findEmpByUserName(userName);
	}
	/**
	 * 根据用户获取对象
	 */
	public Employee findByCodeOrUserName(String userCode) {
		return employeeDao.findByCodeOrUserName(userCode);
	}
	/**
	 * 初始化密码
	 * @param id
	 */
	public void doInitPass(String id) {
	    employeeDao.initPass(id);
	}

	/**
	 * 更新密码
	 * 
	 * userCode
	 * passwordMD5
	 */
	public void updatePass(Integer id, String passwordMD5) {
	    employeeDao.updatePass(id, passwordMD5);
	}

	public void updateLoginInfo(Employee emp) {
		   Map<String,Object> params = new HashMap<String,Object>();
		   params.put("lastLoginIp", emp.getLastLoginIp());
		   params.put("lastLoginTime", emp.getLastLoginTime());
		   params.put("id", emp.getId());
		   employeeDao.update(params);
	}

	/**
	 * 为用户分配角色
	 * 
	 * id
	 * roleIds
	 */
	public void doAssign(String empId, String roleId) {
		String[] roleIds = null;
		// 把字符串转换成数组
		if (StringUtils.isNotEmpty(roleId)) {
			String[] ids = roleId.split(CmsConst.SPLITCHAR);
			roleIds = new String[ids.length];
			for (int i = 0; i < ids.length; i++) {
				roleIds[i] = (ids[i]);
			}
		}
		// 删除以前所有权限,用户可以没有角色,但不能删除基础角色
		employeeDao.removeRoleOfEmp(empId);
		// 重新分配角色
		if (roleIds != null && roleIds.length > 0) {
			employeeDao.doAssign(empId, roleIds);
		}
	}
	/**
	 * 为用户分配工厂
	 * 
	 * id
	 * siteId
	 */
	public void doAssignSite(String empId, String siteId) {
		String[] siteIds = null;
		// 把字符串转换成数组
		if (StringUtils.isNotEmpty(siteId)) {
			String[] ids = siteId.split(CmsConst.SPLITCHAR);
			siteIds = new String[ids.length];
			for (int i = 0; i < ids.length; i++) {
				siteIds[i] = (ids[i]);
			}
		}
		// 删除以前所有权限,用户可以没有工厂权限
		employeeDao.removeSiteOfEmp(empId);
		// 重新分配角色
		if (siteIds != null && siteIds.length > 0) {
			employeeDao.doAssignSite(empId, siteIds);
		}
	}
	
	/**
	 * 更新用户信息
	 * @param emp
	 */
    public void updateEmp(Employee emp) {
       employeeDao.update(emp);
    }
    
    /**
     * 分页查询
     * @param pageNo 页码
     * @param pageSize 页面记录数
     * @param map
     * @return
     */
    public Pagination<Employee> findByPage(int pageNo, Integer pageSize,
                                           Map<String, Object> map) {
       return  employeeDao.findByPage(pageNo, pageSize,map);
    }
	/**
	 * 回收角色
	 * mp_role_id
	 */
	public void doRevokeRole(String emp_id,String role_id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("empId", Integer.valueOf(emp_id));
		params.put("roleId", Integer.valueOf(role_id));
		employeeDao.doRevokeRole(params);
	}

    public Employee findById(String id) {
       return employeeDao.findById(id);
    }
    /**
     * 查询那些员工分配了该角色
     * @param pageNo
     * @param pageSize
     * @param queryMap
     * @return
     */
    public Pagination<Employee> findEmpOfRole(int pageNo, Integer pageSize,
											  Map<String, Object> queryMap) {
    	queryMap.put("pageNo", pageNo);
		queryMap.put("pageSize", pageSize);
        return employeeDao.findByPage("findEmpOfRole",queryMap);
    }
    public List<UserMenu> findUserMenus(String empId){
    	return employeeDao.findUserMenus(empId);
    }

	public void updatePass(String id, String passwordMD5) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("password", passwordMD5);
		employeeDao.update(params);
	}

	public void save(Employee emp, String roleIds) {
		// 设置删除标识 未删除
		emp.setDeleteFlag(0);
		// 设置冻结标识 未冻结
		emp.setFreeze("0");
		// 密码加密
		MD5 md = new MD5();
		// 得到加密以后的密码
		String password = md.getMD5ofStr(emp.getPassword());
		emp.setPassword(password);
		employeeDao.save(emp);
		//保存角色列表
		doAssign(emp.getId(), roleIds);
	}
	
	public void remove(String id)throws Exception{
		Employee emp = new Employee();
		emp.setId(id);
		emp.setDeleteFlag(1);
		employeeDao.update(emp);
	}
}
