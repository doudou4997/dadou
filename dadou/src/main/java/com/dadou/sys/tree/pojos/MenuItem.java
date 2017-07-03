package com.dadou.sys.tree.pojos;
import java.io.Serializable;

/**
 * 树节点,该节点是由Menu和Privilege组合而成
 * 
 * @author gaof
 * 
 */
@SuppressWarnings("serial")
public class MenuItem implements Serializable,Comparable<MenuItem>{
	/**
	 * 主键id
	 */
	private String id;
	/**
	 * 显示名称
	 */
	private String name;
	/**
	 * 菜单的父id
	 */
	private String parentId;
	/**
	 * 展开层次
	 */
	private int level;
	/**
	 * 是否是叶子节点,0:非叶子 1：叶子
	 */
	private boolean isLeaf;
	/**
	 * 类型,1:菜单 2:操作权限
	 */
	private int type;
	/**
	 * 每一个菜单所具有的子节点的个数
	 */
	private int itemCount;
	/**
	 * 顺序
	 */
	private int sortNum;
	/**
	 * 图标
	 */
	private String icon;

	/////////////////////////////////////
	////getter/setter方法
	/////////////////////////////////////
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getParentId() {
		return parentId;
	}

	public int getLevel() {
		return level;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public int getType() {
		return type;
	}
	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getItemCount() {
		return itemCount;
	}

	public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
	public int compareTo(MenuItem o) {
	        if(this.sortNum>o.sortNum)
	            return 1;
	        else
	            return -1;
   }

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIcon() {
		return icon;
	}
}