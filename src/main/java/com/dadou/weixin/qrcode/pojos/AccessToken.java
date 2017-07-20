package com.dadou.weixin.qrcode.pojos;

import java.util.Date;

/**
 * AccessToken
 * @author fengge
 *
 */
public class AccessToken {
	/**
	 * access_token有效时间，单位为妙 30分钟
	 */
	private long expire_in = 1800;	
	/**
	 * 保存的时间
	 */
	private Date save_time;
	
	/**
	 * Json串
	 */
	
	private String json;
	public long getExpire_in() {
		return expire_in;
	}
	public void setExpire_in(long expire_in) {
		this.expire_in = expire_in;
	}
	public Date getSave_time() {
		return save_time;
	}
	public void setSave_time(Date save_time) {
		this.save_time = save_time;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
}
