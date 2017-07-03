package com.dadou.sys.role.pojos;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * 角色实体对象
 * @author gaofeng
 */
public class Role {

	/**
	 * 主键id
	 */
	private String id;
	/**
	 * 角色名称
	 */
	private String roleName;
	/**
	 * 所属子系统ID
	 */
	private String sysId;
	
	/**
	 * 角色描述
	 */
	private String description;
	/**
	 * 菜单列表
	 */
	private Set<RoleMenu> menuList = new HashSet<RoleMenu>();
	///////////////////////////////////////
	/////getter/setter方法
	///////////////////////////////////////
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}	
	public Set<RoleMenu> getMenuList() {
		return menuList;
	}
	public void setMenuList(Set<RoleMenu> menuList) {
		this.menuList = menuList;
	}	

	////////////////////////////////////
	////重写toString,equals hashCode方法
	/////////////////////////////////////
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		           .append("id", getId())
		           .append("name",getRoleName())
		           .toString();
	}
    @Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if (!(other instanceof Role))
			return false;
		Role castOther = (Role) other;
		return new EqualsBuilder()
		        .append(this.getId(), castOther.getId())
		        .isEquals();
	}
    @Override
	public int hashCode() {
    	return new HashCodeBuilder()
    	        .append(this.getId())
    	        .toHashCode();
	}
	public String getSysId() {
		return sysId;
	}
	public void setSysId(String sysId) {
		this.sysId = sysId;
	}	
}
