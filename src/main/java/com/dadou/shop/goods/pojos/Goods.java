package com.dadou.shop.goods.pojos;

/**
 * 商品实体类
 * 
 * @author dadou
 * 
 */
public class Goods {
	/**
	 * 商品id
	 */
	private String id;
	/**
	 * 商品图片
	 */
	private String goodsPicUrl;
	/**
	 * 商品条码
	 */
	private String goodsCode;
	/**
	 * 商品名
	 */
	private String goodsName;
	/**
	 * 价格
	 */
	private float goodsPrice;
	/**
	 * 品牌
	 */
	private String goodsBrand;
    /**
     * 产地
     */
    private String goodsArea;
	/**
	 * 商品类型
	 */ 
	private String goodsType;
	/**
	 * 商品生产日期
	 */
	private String goodsPD;
	/**
	 * 商品保质期
	 */
	private String goodsEXP;
	/**
	 * 商品折扣
	 */
	private float goodsDct;

	/**
	 * 商品售价
	 */
	private float goodsASP;
	/**
	 * 商品数量
	 */
	private Integer goodsNum;
	/**
	 * 商品兑换积分
	 */
	private Integer goodsPoint;
	/**
	 * 商品描述
	 */
	private String goodsDesc;
	/**
	 * 商品简述
	 */
	private String goodsShort;

	/**
	 * 商品状态 0未上架 1上架 2缺货报警
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
	public Goods() {

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

	public String getGoodsPicUrl() {
		return goodsPicUrl;
	}

	public void setGoodsPicUrl(String goodsPicUrl) {
		this.goodsPicUrl = goodsPicUrl;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public float getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(float goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getGoodsBrand() {
		return goodsBrand;
	}

	public void setGoodsBrand(String goodsBrand) {
		this.goodsBrand = goodsBrand;
	}

	public String getGoodsArea() {
		return goodsArea;
	}

	public void setGoodsArea(String goodsArea) {
		this.goodsArea = goodsArea;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getGoodsPD() {
		return goodsPD;
	}

	public void setGoodsPD(String goodsPD) {
		this.goodsPD = goodsPD;
	}

	public String getGoodsEXP() {
		return goodsEXP;
	}

	public void setGoodsEXP(String goodsEXP) {
		this.goodsEXP = goodsEXP;
	}

	public float getGoodsDct() {
		return goodsDct;
	}

	public void setGoodsDct(float goodsDct) {
		this.goodsDct = goodsDct;
	}

	public float getGoodsASP() {
		return goodsASP;
	}

	public void setGoodsASP(float goodsASP) {
		this.goodsASP = goodsASP;
	}

	public Integer getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}

	public Integer getGoodsPoint() {
		return goodsPoint;
	}

	public void setGoodsPoint(Integer goodsPoint) {
		this.goodsPoint = goodsPoint;
	}

	public String getGoodsDesc() {
		return goodsDesc;
	}

	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}

	public String getGoodsShort() {
		return goodsShort;
	}

	public void setGoodsShort(String goodsShort) {
		this.goodsShort = goodsShort;
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
