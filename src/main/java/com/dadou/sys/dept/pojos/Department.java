package com.dadou.sys.dept.pojos;

/**
 * 部门实体类
 * 
 * @author gaofeng
 * 
 */
public class Department {
	/**
	 * 部门编号
	 */
	private String id;
	/**
	 * 父Id
	 */
	private String parentId;
	/**
	 * 部门名称
	 */
	private String deptName;
	/**
	 * 部门描述
	 */
	private String description;
	/**
	 * 排序
	 */
	private Integer sortNum;
    /**
     * 删除标识
     */
    private Integer deleteFlag;
	/**
	 * 便于用首字母查询
	 */ 
	private String alpha;
	/**
	 * 默认构造方法
	 */
	public Department() {

	}
	///////////////////////////////////////
	///所有属性的gettet/setter方法
	///////////////////////////////////////
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	

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

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getAlpha() {
		return alpha;
	}
	public void setAlpha(String alpha) {
		this.alpha = alpha;
	}

}
