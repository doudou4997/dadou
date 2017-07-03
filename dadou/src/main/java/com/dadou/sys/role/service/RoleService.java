package com.dadou.sys.role.service;

import com.dadou.sys.emp.pojos.Employee;
import com.dadou.sys.menu.dao.MenuDao;
import com.dadou.sys.menu.pojos.Menu;
import com.dadou.sys.privilege.dao.PrivilegeDao;
import com.dadou.sys.privilege.pojos.Privilege;
import com.dadou.sys.role.dao.RoleDao;
import com.dadou.sys.role.pojos.Role;
import com.dadou.sys.role.pojos.RoleMenu;
import com.dadou.sys.role.pojos.RolePrivilege;
import com.dadou.sys.tree.pojos.MenuItem;
import com.framework.core.page.Pagination;
import com.framework.core.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Role 业务类实现类
 */
@Service("sys_roleService")
public class RoleService{
    /**
     * 角色Dao
     */
	@Resource(name="sys_roleDao")
    private RoleDao roleDao;
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
     * 为角色分配菜单和权限
     *  menuIds
     * privilegeIds
     * roleId
     */
    public void doAssign(String[] menuIds, String[] privilegeIds,
    		String roleId) {
        Role role = roleDao.findById(roleId);
        // 获取菜单列表
        List<Menu> menuList = null;
        HashMap<String, Object> menuId = new HashMap<String, Object>();
        menuId.put("params", menuIds);
        if (menuIds != null) {
            menuList = this.menuDao.findAll(menuId);
        }
        List<Privilege> privilegeList = null;
        HashMap<String, Object> privilegeId = new HashMap<String, Object>();
        privilegeId.put("params", privilegeIds);
        if (privilegeIds != null) {
            // 获取权限对象列表
            privilegeList = this.privilegeDao.findAll(privilegeId);
        }
         // 分配所有权限
        roleDao.doAssign(menuList, privilegeList, role);
    }
    /**
     * 获取菜单和权限
     * @param menuId
     * @return sysId
     */
    public List<MenuItem> findMenuAndPrivileges(String menuId, String sysId) {
        return menuDao.getMenuAndPrivileges(menuId,sysId);
    }

    /**
     * 修改角色基本信息
     */
    public void updateRole(Role role) {
        // 修改角色基本信息
        roleDao.update(role);
    }
    /**
     * 根据条件查询全部角色
     * queryMap
     */
	public List<Role> findAll(Map<String, Object> params) {
		if(params == null){
			params = new HashMap<>();
		}
		return roleDao.findAll(params);
	}
    /**
     * 根据id删除角色
     */
    public void removeRole(String roleId) {
        roleDao.removeRole(roleId);
    }

    /**
     * 根据角色id判断是否有用户已分配该角色
     * 
     * @return
     * @return
     */
    public boolean findExsitEmpOfRole(String roleId) {
       return roleDao.isExsitEmpOfRole(roleId);
    }

    /**
     * 查找我的角色
     * queryMap 参数Map
     * @return
     */
    public List<Role> findMyRoleList(Employee emp, Map<String, Object> map) {
       return this.roleDao.findMyRoleList(emp,map);
    }
    @SuppressWarnings("unchecked")
	public List<RoleMenu> findRoleMenusForLeftTree(List<String> roleIds){
        return this.roleDao.findRoleMenusForLeftTree(roleIds);
    }
    @SuppressWarnings("unchecked")
	public List<RoleMenu> findRoleMenus(String roleId){
        return this.roleDao.findRoleMenus(roleId);
    }
	@SuppressWarnings("unchecked")
	public List<RolePrivilege> findRolePrivileges(String roleId) {
		return this.roleDao.findRolePrivileges(roleId);
	}
	/**
	 * 获取权限列表
	 * @param roleIds
	 * @return
	 */
	public List<String> findRolePrivilegesByIds(List<String> roleIds) {
		List<String> urls = new ArrayList<>();
		//获取所有权限
		@SuppressWarnings("unchecked")
		List<Map<String, String>> mapList = this.roleDao.findRolePrivilegesByIds(roleIds);
		if(mapList!=null && mapList.size()>0){
			for(Map<String, String> map : mapList){
				String url = map.get("URL");
				if(StringUtils.isNotEmpty(url)){
					urls.add(url);
				}
			}
		}
		return urls;
	}
    /**
     * 
     * 保存囧色
     * @param role
     */
    public void saveRole(Role role) {
        roleDao.save(role);
    }
     public Role findById(String id) {
        return roleDao.findById(id);
    }
    /**
     * 分页查询角色
     * @param pageNo
     * @param pageSize
     * @param queryMap
     * @return
     */
    public Pagination<Role> findByPage(int pageNo, Integer pageSize,
                                       Map<String, Object> queryMap) {
    	queryMap.put("pageNo", pageNo);
		queryMap.put("pageSize", pageSize);
        return roleDao.findByPage(pageNo, pageSize, queryMap);
    }
    /**
     * 查询员工所有的角色
     * @param empId
     * @return
     */
    public List<Role> findRolesOfEmp(String empId){
    	return roleDao.findRolesOfEmp(empId);
    }
}
