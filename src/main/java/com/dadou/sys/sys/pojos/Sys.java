package com.dadou.sys.sys.pojos;


import com.dadou.sys.role.pojos.RoleMenu;

import java.util.HashSet;
import java.util.Set;

/**
 * 子系统
 * @author gaof
 *
 */
@SuppressWarnings("serial")
public class Sys implements java.io.Serializable{
    /**Id**/
	private String id;
	/**子系统名称**/
	private String name;
	/**子系统顺序**/
	private Integer orderNum;
	/**对应的所有菜单,去重**/
	private Set<RoleMenu> roleMenus = new HashSet<>();
	public Sys(){
		
	}
	public Sys(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public Set<RoleMenu> getRoleMenus() {
		return roleMenus;
	}
	public void setRoleMenus(Set<RoleMenu> roleMenus) {
		this.roleMenus = roleMenus;
	}
}
