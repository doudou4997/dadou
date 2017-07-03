package com.dadou.sys.role.pojos;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * 角色和菜单关联表<br>
 * @author gaof
 *
 */
@SuppressWarnings("serial")
public class RoleMenu implements java.io.Serializable,Comparable<RoleMenu>{
    /**
     * 主键Id
     */
	private String id;
    /**
     * 所属菜单的父Id
     */
	private String parentId;
	/**
	 * 所属菜单id
	 */
    private String menuId;
    /**
     * 所属角色id
     */
	private String roleId;
    /**
     * 菜单名称
     */
	private String menuName;
    /**
     * 菜单url
     */
	private String url;
	/**
	 * 打开时图标
	 */
	private String icon;
    /**
     * 排序
     */
	private Integer sortNum;
	/**
	 * 级别
	 */
	private Integer level;
	private String sysId;
	////////////////////////////////////////
	//////getter/setter方法
	////////////////////////////////////////
	
	////////////////////////////////////
	////重写toString,equals hashCode方法
	/////////////////////////////////////
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		           .append("id", getId()+" ")
		           .append("parentId",this.parentId+" ")
		           .append("menuId",this.menuId+" ")
		           .append("roleId",this.roleId+" ")
		           .append("menuName",this.menuName+" ")
		           .append("sortNum",this.sortNum+" ")
		           .toString();
	}

	public Integer getSortNum() {
		return sortNum;
	}
	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
	@Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if (!(other instanceof RoleMenu))
			return false;
		RoleMenu castOther = (RoleMenu) other;
		return new EqualsBuilder()
		        .append(this.getMenuId(), castOther.getMenuId())
		        .isEquals();//比较的是MenuId
	}
    @Override
	public int hashCode() {
    	return new HashCodeBuilder()
    	        .append(getId())
    	        .toHashCode();
	}
	public int compareTo(RoleMenu o) {
        if (sortNum == null) {
            if (o.sortNum == null)
                return 0;
            return -1;
        }
        if (o.sortNum == null) {
            if (sortNum == null)
                return 0;
            return 1;
        }
        return sortNum.compareTo(o.sortNum);
	
	}
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public static void main(String[] args) {
		Set<RoleMenu> treeMenus = new HashSet<>();
		RoleMenu r  = new RoleMenu();
		r.setMenuId("2");
		r.setSortNum(2);
		treeMenus.add(r);
		r  = new RoleMenu();
		r.setMenuId("3");
		r.setSortNum(3);
		treeMenus.add(r);
		r  = new RoleMenu();
		r.setMenuId("2");
		r.setSortNum(4);
		treeMenus.add(r);
		System.out.println(treeMenus);
		Set<RoleMenu> treeMenus1 = new  TreeSet<>();
		treeMenus1.addAll(treeMenus);
		System.out.println(treeMenus1);
		
	}
}