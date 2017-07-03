package com.dadou.sys.privilege.service;

import com.dadou.sys.menu.dao.MenuDao;
import com.dadou.sys.menu.pojos.Menu;
import com.dadou.sys.privilege.dao.PrivilegeDao;
import com.dadou.sys.privilege.pojos.Privilege;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("sys_privilegeService")
public class PrivilegeService {
	/**
	 * RoleMenu更新语句
	 */
    @Resource(name="sys_privilegeDao")
	private PrivilegeDao privilegeDao;
    //
    @Resource(name="sys_menuDao")
	private MenuDao menuDao;
	public void save(Privilege privilege) {
		Menu menu = menuDao.findById(privilege.getParentId());
		privilege.setSysId(menu.getSysId());
		//保存权限
		privilegeDao.save(privilege);
	}

	public void remove(String pid) {
		//删除t_menu及t_role_pribvilege 关联表(先删除关联表信息)
		privilegeDao.remove(pid);
	}

	public Privilege findById(String id) {
		return privilegeDao.findById(id);
	}

	public void update(Privilege privilege) {
		privilegeDao.updatePrivilege(privilege);
	}

}
