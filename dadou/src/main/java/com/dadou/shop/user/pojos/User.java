package com.dadou.shop.user.pojos;

/**
 * 终端用户实体类
 * 
 * @author dadou
 * 
 */
public class User {
	/**
	 * 用户id
	 */
	private String id;
	/**
	 * 微信号标识
	 */
	private String openId;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 联系方式
	 */
	private String phone;
	/**
	 * 性别
	 */
	private String gender;
    /**
     * 地区
     */
    private String area;
	/**
	 * 关注时间
	 */ 
	private String followtime;
	/**
	 * 用户等级
	 */
	private String flag;
	/**
	 * 用户积分
	 */
	private Integer point;
	/**
	 * 删除标识
	 */
	private String deleteflag;


	/**
	 * 默认构造方法
	 */
	public User() {

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

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getFollowtime() {
		return followtime;
	}

	public void setFollowtime(String followtime) {
		this.followtime = followtime;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public String getDeleteflag() {
		return deleteflag;
	}

	public void setDeleteflag(String deleteflag) {
		this.deleteflag = deleteflag;
	}
}
