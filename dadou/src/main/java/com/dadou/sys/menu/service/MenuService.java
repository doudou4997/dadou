package com.dadou.sys.menu.service;

import com.dadou.sys.menu.dao.MenuDao;
import com.dadou.sys.menu.pojos.Menu;
import com.dadou.sys.tree.pojos.MenuItem;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("sys_menuService")
public class MenuService {
    @Resource(name="sys_menuDao")
	private MenuDao menuDao;
	/**
	 * 查询全部的菜单和权限
	 */
	public List<MenuItem> findMenuAndPrivileges(String menuId, String sysId) {
		return menuDao.getMenuAndPrivileges(menuId,sysId);
	}
	public Menu findById(String id) {
		return menuDao.findById(id);
	}

	public void save(Menu menu) {
		menuDao.save(menu);
	}

	public void remove(String menuId) {
		//删除t_menu及t_role_menu 和 t_user_menu 关联表(先删除关联表信息)
		menuDao.remove(menuId);
	}
	 public void update(Menu menu) {
		//更新t_menu及t_role_menu 和 t_user_menu 关联表(先删除关联表信息)
		 menuDao.updateMenu(menu);
	 }
}
