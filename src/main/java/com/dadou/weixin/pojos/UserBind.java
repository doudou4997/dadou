package com.dadou.weixin.pojos;

import com.google.gson.annotations.Expose;

/**
 * 用户微信绑定
 */
public class UserBind {
	/**绑定Id**/
	@Expose
	private Integer id;
	/**用户Id**/
	private Integer userId;
	/**终端MAC**/
	private String mac;
	/**绑定时间**/
	@Expose
	private String bindTime;
	/**微信号加密后的OpenId,每个用户号对不同的公众号 openId都唯一**/
	@Expose
	private String openId;
	/**绑定名称**/
	@Expose
	private String bindName;
	/**绑定手机号**/
	private String mobile;
	/**qq**/
	private String qq;
	/**0 以前设备  1当前设备**/
	@Expose
	private Integer status;
	@Expose 
	private String statusName;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getBindTime() {
		return bindTime;
	}
	public void setBindTime(String bindTime) {
		this.bindTime = bindTime;
	}
	public String getBindName() {
		return bindName;
	}
	public void setBindName(String bindName) {
		this.bindName = bindName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getStatusName() {
		return this.statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}
