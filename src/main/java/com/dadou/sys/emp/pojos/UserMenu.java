package com.dadou.sys.emp.pojos;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 用户和菜单关联表<br>
 * @author sunbin
 *
 */
public class UserMenu implements java.io.Serializable,Comparable<UserMenu>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	private String userId;
    /**
     * 菜单名称
     */
	private String name;
    /**
     * 菜单url
     */
	private String url;
	/**
	 * 显示标题
	 */
	private String title;
	/**
	 * 打开位置,如:_self _top
	 */
	private String target;
	/**
	 * 闭合时图标
	 */
	private String icon;
	/**
	 * 打开时图标
	 */
	private String iconOpen;	
    /**
     * 排序
     */
	private Integer sortNum;
	/**
	 * 级别
	 */
	private Integer level;
	////////////////////////////////////////
	//////getter/setter方法
	////////////////////////////////////////
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getIconOpen() {
		return iconOpen;
	}
	public void setIconOpen(String iconOpen) {
		this.iconOpen = iconOpen;
	}
	public Integer getSortNum() {
		return sortNum;
	}
	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
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
		if (!(other instanceof UserMenu))
			return false;
		UserMenu castOther = (UserMenu) other;
		return new EqualsBuilder()
		        .append(this.getId(), castOther.getId())
		        .isEquals();
	}
    @Override
	public int hashCode() {
    	return new HashCodeBuilder()
    	        .append(getId())
    	        .toHashCode();
	}
	public int compareTo(UserMenu o) {
        if(this.sortNum>o.sortNum)
            return 1;
        else if(this.sortNum == o.sortNum)
        	return 0;
        else
            return -1;
	
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
}