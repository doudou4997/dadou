package com.dadou.sys.dic.pojos;

import com.google.gson.annotations.Expose;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.Serializable;

/**
 * 数据字典类
 * @author wangs
 *
 */
public class Dictionary implements Comparable<Dictionary>,Serializable {
	
	public static final String ORDER_TYPE="orderType";//订单类型
	public static final String ORDER_STATUS="orderStatus";//订单状态
	public static final String GOODS_TYPE="goodsType";//商品类型
	public static final String GOODS_STATUS="goodsStatus";//商品状态
	public static final String YESNO="YESNO";
	public static final String YES="1";
	public static final String NO="0";
	@Expose
	private String id;//主键id
	@Expose
	private String key;//实际的值
	@Expose
	private String value;//显示的名称
	@Expose
	private String type;//值类型
	@Expose
	private String typeName;//类型名称
	
	//关联使用
	@Expose
	private String _parentId;//父节点Id
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String get_parentId() {
		return _parentId;
	}
	public void set_parentId(String _parentId) {
		this._parentId = _parentId;
	}
	public int compareTo(Dictionary o) {
		int v1 = NumberUtils.toInt(this.key);
		int v2 = NumberUtils.toInt(o.key);
		if(v1 > v2){
			return 1;
		}else {
			return -1;
		}
	}
	
}
