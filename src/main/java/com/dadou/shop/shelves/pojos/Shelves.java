package com.dadou.shop.shelves.pojos;

/**
 * 货柜实体类
 * 
 * @author dadou
 * 
 */
public class Shelves {
	/**
	 * 货柜id
	 */
	private String id;
	/**
	 * 货柜条码
	 */
	private String shelvesCode;
	/**
	 * 货柜名
	 */
	private String shelvesName;
	/**
	 * 货柜类型
	 */ 
	private String shelvesType;
	/**
	 * 货柜地址
	 */
	private String shelvesAddress;
	/**
	 * 货柜总容量
	 */
	private Integer shelvesCapacity;
	/**
	 * 货柜状态 0未上架 1上架 2缺货报警
	 */
	private String flag;
	/**
	 * 删除标识
	 */
	private String deleteflag;
	private String inserttime;
	private String insertemp;
	private String updatetime;
	private String updateemp;

	/**
	 * 默认构造方法
	 */
	public Shelves() {

	}
	///////////////////////////////////////
	///所有属性的gettet/setter方法
	///////////////////////////////////////


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShelvesCode() {
		return shelvesCode;
	}

	public void setShelvesCode(String shelvesCode) {
		this.shelvesCode = shelvesCode;
	}

	public String getShelvesName() {
		return shelvesName;
	}

	public void setShelvesName(String shelvesName) {
		this.shelvesName = shelvesName;
	}

	public String getShelvesType() {
		return shelvesType;
	}

	public void setShelvesType(String shelvesType) {
		this.shelvesType = shelvesType;
	}

	public String getShelvesAddress() {
		return shelvesAddress;
	}

	public void setShelvesAddress(String shelvesAddress) {
		this.shelvesAddress = shelvesAddress;
	}

	public Integer getShelvesCapacity() {
		return shelvesCapacity;
	}

	public void setShelvesCapacity(Integer shelvesCapacity) {
		this.shelvesCapacity = shelvesCapacity;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getDeleteflag() {
		return deleteflag;
	}

	public void setDeleteflag(String deleteflag) {
		this.deleteflag = deleteflag;
	}

	public String getInserttime() {
		return inserttime;
	}

	public void setInserttime(String inserttime) {
		this.inserttime = inserttime;
	}

	public String getInsertemp() {
		return insertemp;
	}

	public void setInsertemp(String insertemp) {
		this.insertemp = insertemp;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getUpdateemp() {
		return updateemp;
	}

	public void setUpdateemp(String updateemp) {
		this.updateemp = updateemp;
	}
}
