package com.dadou.shop.goods.dao;

import com.dadou.shop.goods.pojos.Goods;
import com.framework.core.daos.mybatis.GenericMybatisDao;
import com.framework.core.page.Pagination;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 商品管理
 */
@Repository("goodsDao")
public class GoodsDao extends GenericMybatisDao<Goods, String> {
	/**
	 * 分页查询
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination<Goods> findByPage(int pageNo, Integer pageSize,
										Map<String, Object> map) {
		map.put("pageNo", pageNo);
		map.put("pageSize", pageSize);
//		String goodsName = (String)map.get("goodsName");
//		if (StringUtils.isNotEmpty(goodsName)) {
//			map.put("goodsName", "%" + goodsName.trim() + "%");
//		}
		return super.findByPage(pageNo,pageSize,map);
	}

}
