package com.dadou.sys.privilege.pojos;


import com.dadou.sys.menu.pojos.Menu;
import com.dadou.sys.role.pojos.RolePrivilege;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 权限类,主要是指操作权限<br>
 * 每一个权限都归属一个菜单<br>
 *  @author gao
 * 
 */
public class Privilege implements Serializable {
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
	 * 权限描述
	 */
	private String description;
	/**
	 * 所属菜单id
	 */
	private String parentId;
	/**
	 * 所属菜单
	 */
	private Menu menu;
	/**
	 * 排序
	 */
	private Integer sortNum; 
	/**子系统**/
	private String sysId;
	/**
	 * 权限集合
	 */
	private Set<RolePrivilege> set = new HashSet<RolePrivilege>();
	////////////////////////////////////
	////重写toString,equals hashCode方法
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
	public Set<RolePrivilege> getSet() {
		return set;
	}

	public void setSet(Set<RolePrivilege> set) {
		this.set = set;
	}

	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
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
		if (!(other instanceof Privilege))
			return false;
		Privilege castOther = (Privilege) other;
		//通过比较id来确定是否相等
		return new EqualsBuilder().append(this.getId(), castOther.getId())
				.isEquals();
	}
    @Override
	public int hashCode() {
		//通过id来重写hashCode
    	return new HashCodeBuilder().append(getId()).toHashCode();
	}
	
}
