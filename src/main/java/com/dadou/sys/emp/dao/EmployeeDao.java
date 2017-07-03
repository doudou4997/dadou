package com.dadou.sys.emp.dao;

import com.dadou.sys.dept.dao.DepartmentDao;
import com.dadou.sys.emp.pojos.EmpRole;
import com.dadou.sys.emp.pojos.Employee;
import com.framework.core.daos.mybatis.GenericMybatisDao;
import com.framework.core.page.Pagination;
import com.framework.core.utils.MD5;
import com.framework.core.utils.StringUtils;
import com.framework.core.utils.UUIDGenerator;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Dao实现类
 */
@Repository(value="sys_employeeDao")
public class EmployeeDao extends GenericMybatisDao<Employee, String> {
	public static String DEPT_SQLID = DepartmentDao.class.getName();
	/**
	 * 冻结用户 1:冻结 0：解冻
	 */
	public void doFreeze(Integer id) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("freeze", "1");
		this.update(params);
	}
	/**
	 * 解冻用户
	 */
	public void doUnFreeze(Integer id) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("freeze", "0");
		this.update(params);
	}

	/**
	 * 判断给定的userCode是否存在
	 */
	public boolean isExist(String userCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userCode", userCode);
		List<Employee> list  = this.findList(params);
		if (list != null && list.size() != 0) {
			return true;
		}
		return false;
	}
    /**
     * 分配角色前，删除该系统的以前所有角色
     * @param empId
     */
    public void removeRoleOfEmp(String empId) {
    	HashMap<String, Object> params = new HashMap<String, Object>();
    	params.put("empId", empId);
        this.remove("removeRoleOfEmp", params);        
    }
	/**
	 * 分配角色
	 * @param empId
	 * @param roleIds
	 */
    public void doAssign(String empId, String[] roleIds) {
    	HashMap<String, Object> params = new HashMap<String, Object>();
    	List<EmpRole> empRoles = new ArrayList<>();
    	for(String roleId:roleIds){
    		EmpRole empRole = new EmpRole();
    		empRole.setId(UUIDGenerator.randomId());
    		empRole.setEmpId(empId);
    		empRole.setRoleId(roleId);
    		empRoles.add(empRole);
    	}
    	params.put("empRoles", empRoles);
    	if(params.size() > 0){
    	  this.save("batchSaveEmpRole", params);
    	}
    }
	/**
	 * 分配工厂
	 * @param empId
	 * @param siteIds
	 */
    public void doAssignSite(String empId, String[] siteIds) {
    	HashMap<String, Object> params = new HashMap<String, Object>();
    	List<Map<String, String>> list = new ArrayList<>();
    	for(String siteId:siteIds){
    		Map<String,String> map = new HashMap<String, String>();
    		map.put("id", UUIDGenerator.randomId());
    		map.put("empId", empId);
    		map.put("siteId", siteId);
    		list.add(map);
    	}
    	params.put("list", list);
    	if(params.size() > 0){
    	  this.save("batchSaveEmpSite", params);
    	}
    }
    
	/**
	 * 根据用户获取对象
	 */
	public Employee findByCodeOrUserName(String userCode) {
		List<Employee> list = this.findList("findByCodeOrUserName",userCode);
		if (list != null && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}
	/**
	 * 初始化密码
	 */
	public void initPass(String id) {
		MD5 md = new MD5();
		String password = md.getMD5ofStr("123456");
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("password", password);
		this.update(params);
	}

	/**
	 * 修改密码
	 */
	public void updatePass(Integer id, String passwordMD5) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("password", passwordMD5);
		this.update(params);
	}

   public void updateLoginInfo(String lastLoginIp,Date lastLoginTime){
	   Map<String,Object> params = new HashMap<String,Object>();
	   params.put("lastLoginIp", lastLoginIp);
	   params.put("lastLoginTime", lastLoginTime);
	   this.update(params);
   }
    /**
     * 回收角色
     * id
     */
    public void doRevokeRole(Map<String, Object> params) {
        this.remove("removeUserRoleOfEmp",params);
    }
	public List  findUserMenus(String empId) {
		return this.findList("findUserMenus", empId);
	}
    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Pagination<Employee> findByPage(int pageNo, Integer pageSize,
                                           Map<String, Object> map) {
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        String userName = (String)map.get("userName");
        if (StringUtils.isNotEmpty(userName)) {
        	map.put("userName", "%" + userName.trim() + "%");
        }
        String name = (String)map.get("name");
        if (StringUtils.isNotEmpty(name)) {
        	map.put("name", "%" + name.trim() + "%");
        }
        String deptName = (String)map.get("deptName");
        if (StringUtils.isNotEmpty(deptName)) {
        	map.put("deptName", "%" + deptName.trim() + "%");
        }
        return super.findByPage(pageNo,pageSize,map);
    }
    /**
     * 根据用户名查询是否存在
     * @param userName
     * @return
     */
	public boolean findEmpByUserName(String userName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", userName);
		List<Employee> list = this.findList(params);
		if(list!=null && list.size()>0) return true;
		return false;
	}
	/**
	 * 删除员工所有的工厂权限
	 * @param empId
	 */
	public void removeSiteOfEmp(String empId) {
    	HashMap<String, Object> params = new HashMap<String, Object>();
    	params.put("empId", empId);
        this.remove("removeSiteOfEmp", params);   
	}
}
