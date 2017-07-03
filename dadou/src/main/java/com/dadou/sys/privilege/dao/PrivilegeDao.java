package com.dadou.sys.privilege.dao;

import com.dadou.sys.privilege.pojos.Privilege;
import com.framework.core.daos.mybatis.GenericMybatisDao;
import org.springframework.stereotype.Repository;

@Repository("sys_privilegeDao")
public class PrivilegeDao extends GenericMybatisDao<Privilege, String> {
	/**
     * 删除菜单同时先删除RolePrivilege的级联数据
     * menuId
     * @return
     */
	public void remove(String parentId){
		this.remove("removeRolePrivilegeByPId", parentId);
		this.removeById(parentId);
	}
	/**
	 * 更新权限
	 */
	public void updatePrivilege(Privilege privilege) {
		// 删除RoleMenu表信息
		this.update(privilege);
	}
}
