package com.dadou.sys.menu.pojos;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 菜单列表,菜单与权限绑定形成一棵权限树<br>
 * 每一个权限都属于某个菜单<br>
 * 每个菜单又可分为:导航树中节点或非导航树中节点
 * @author gaof
 *
 */
public class Menu implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    /**
     * 菜单Id
     */
	private String id;
    /**
     * 父Id
     */
	private String parentId;
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
     * 判断是否是树状结构中的元素,0：否 1:是
     */
	private int isTree; 
    /**
     * 排序
     */
	private Integer sortNum;
	/**
	 * 子系统Id
	 */
	private String sysId;
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
	/**
	 * 判断是否是树形节点,1:是 0:否
	 * @return
	 */
	public boolean isTree() {
		return (getIsTree() == 1);
	}
	public int getIsTree(){
		return isTree;
	}
	public void setIsTree(int isTree) {
		this.isTree = isTree;
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
		if (!(other instanceof Menu))
			return false;
		Menu castOther = (Menu) other;
		//通过比较id来确定是否相等
		return new EqualsBuilder().append(this.getId(), castOther.getId())
				.isEquals();
	}
    @Override
	public int hashCode() {
		//通过id来重写hashCode
    	return new HashCodeBuilder().append(getId()).toHashCode();
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
}