package com.dadou.shop.goods.service;

import com.dadou.shop.goods.dao.GoodsDao;
import com.dadou.shop.goods.pojos.Goods;
import com.framework.core.page.Pagination;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service("goodsService")
public class GoodsService {
	@Resource(name="goodsDao")
	private GoodsDao goodsDao;

	/**
	 * 分页查询
	 * @param pageNo 页码
	 * @param pageSize 页面记录数
	 * @param map
	 * @return
	 */
	public Pagination<Goods> findByPage(int pageNo, Integer pageSize,
										Map<String, Object> map) {
		return  goodsDao.findByPage(pageNo, pageSize,map);
	}

	/**
	 * 保存商品信息
	 * @param goods 商品信息
	 * @return
	 */
	public String save(Goods goods) {
		int returnId = goodsDao.save(goods);
		String goodsCode = String.valueOf(returnId);
		return goodsCode;
	}
	/**
	 * 删除商品信息
	 * @param goods 删除商品id信息
	 * @return
	 */
	public void del(Map<String, Object> goods) {
		goodsDao.remove(goods);
	}

}
