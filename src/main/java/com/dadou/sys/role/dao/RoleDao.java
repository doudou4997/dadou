package com.dadou.sys.role.dao;

import com.dadou.sys.emp.dao.EmployeeDao;
import com.dadou.sys.emp.pojos.Employee;
import com.dadou.sys.menu.pojos.Menu;
import com.dadou.sys.privilege.pojos.Privilege;
import com.dadou.sys.role.pojos.Role;
import com.dadou.sys.role.pojos.RoleMenu;
import com.dadou.sys.role.pojos.RolePrivilege;
import com.framework.core.daos.mybatis.GenericMybatisDao;
import com.framework.core.utils.UUIDGenerator;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Role Dao实现类
 */
@Repository("sys_roleDao")
public class RoleDao extends GenericMybatisDao<Role, String> {
	/**
	 * 分配权限:菜单权限和操作权限
	 */
	public void doAssign(List<Menu> menuList, List<Privilege> privilegeList,
						 Role role) {
		// 清空以前的缓存
		this.clearAll(role.getId());
		if(menuList!=null){
		// 添加信息的权限信息
		List<RoleMenu> rmList = new ArrayList<RoleMenu>();
		for (Menu menu : menuList) {
			// 只对树状结构中的节点处理
			// 非树状结构的菜单只是组织权限，非树状结构的菜单下不能有树状节点
			//if (menu.isTree()) {
				RoleMenu rm = new RoleMenu();
				rm.setId(UUIDGenerator.randomId());
				rm.setMenuId(menu.getId());
				rm.setRoleId(role.getId());
				rm.setParentId(menu.getParentId());
				rmList.add(rm);
			//}
		}
		this.batchSave("batchSaveRoleMenus", rmList);

		// 分配权限
		List<RolePrivilege> rpList = new ArrayList<RolePrivilege>();
		if (privilegeList != null) {
			for (Privilege privilege : privilegeList) {
				RolePrivilege rp = new RolePrivilege();
				rp.setId(UUIDGenerator.randomId());
				rp.setPrivilegeId(privilege.getId());
				rp.setRoleId(role.getId());
				rpList.add(rp);
			}
		    this.batchSave("batchSaveRolePrivileges",rpList);
		}
		}
	}

	/**
	 * 清除所有菜单权限和操作权限
	 * 
	 * role
	 */
	private void clearAll(String roleId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);
		//删除权限信息
		this.remove("removeRPByRoleId", params);
		//删除角色菜单信息
		this.remove("removeRmByRoleId", params);

	}
	/**
	 * 根据员工Id，获取对应的Role列表
	 * @param empId
	 * @return
	 */
	public List<Role> findRolesOfEmp(String empId){
		return this.findList("findRolesOfEmp",empId);
	}
	/**
	 * 删除用户角色
     * 删除菜单同时先删除RolePrivilege的级联数据
     * menuId
     */
	public void removeRole(String roleId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);
		this.remove("removeRPByRoleId", params);
		this.remove("removeRmByRoleId", params);
		this.removeById(roleId);
	}
    /**
     * 根据roleId获取 用于左侧导航树的RoleMenus列表
     * @param
     * @return
     */
	public List findRoleMenusForLeftTree(List<String> roleIds) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("ids", roleIds);
		return findList("findRoleMenusForLeftTree", paramsMap);
	}
    /**
     * 根据roleId获取RoleMenus列表
     * @param roleId
     * @return
     */
	public List findRoleMenus(String roleId) {
		return findList("findRoleMenus", roleId);
	}
	public List  findRolePrivileges(String roleId) {
		return findList("findRolePrivileges", roleId);
	}
    /**
     * 根据一组Role ID获取相应权限
     * @param roleIds
     * @return
     */
	public List findRolePrivilegesByIds(List<String> roleIds) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", roleIds);
		return findList("findRolePrivilegesByIds",params);
	}
	/**
	 * 查找我的角色
	 * @return
	 */
	public List<Role> findMyRoleList(Employee emp, Map<String, Object> map) {
		// 获取该员工的角色集合
		List<String> ids = new ArrayList<String>();
		Set<Role> roleSet = emp.getRoleList();
		for(Role r : roleSet){
			ids.add(r.getId());
		}
		map.put("ids", ids);
		return super.findAll(map);
	}
	/**
	 * 判断该角色是否分配过员工
	 * @return
	 */
	public boolean isExsitEmpOfRole(String roleId) {
		boolean flag = true;
		String sqlId = EmployeeDao.class.getName();
		Object obj = findOne(sqlId+".findEmpOfRole_count", roleId);
		int count = NumberUtils.toInt(obj.toString());
		if(count == 0 ){
			flag = false;
		}
		return flag;
	}

}
