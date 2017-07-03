package com.dadou.sys.role.pojos;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * 权限类,主要是指操作权限<br>
 * 每一个权限都归属一个菜单<br>
 *  @author gao
 * 
 */
public class RolePrivilege implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 权限编号
	 */
	private String id;
	/**
	 * 权限名称
	 */
	private String name;
	/**
	 * 权限URL,如 /system/role.action
	 */
	private String url;
	/**
	 * 具体操作,如:delete update等操作名称
	 */
	private String operate;
	/**
	 * 所属权限id
	 */
	private String privilegeId;
	/**
	 * 所属角色id
	 */
	private String roleId;
    ////////////////////////////////////
	////getter/setter方法
	/////////////////////////////////////
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	public String getPrivilegeId() {
		return privilegeId;
	}
	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	////////////////////////////////////
	////重写toString,equals hashCode方法
	/////////////////////////////////////
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		           .append("id", getId())
		           .append("name",getName())
		           .toString();
	}
    @Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if (!(other instanceof RolePrivilege))
			return false;
		RolePrivilege castOther = (RolePrivilege) other;
		//通过比较id来确定是否相等
		return new EqualsBuilder().append(this.getId(), castOther.getId())
				.isEquals();
	}
    @Override
	public int hashCode() {
		//通过id来重写hashCode
    	return new HashCodeBuilder().append(this.getId()).toHashCode();
	}
	
}
