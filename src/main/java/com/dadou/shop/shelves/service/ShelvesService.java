package com.dadou.shop.shelves.service;

import com.dadou.shop.shelves.dao.ShelvesDao;
import com.dadou.shop.shelves.pojos.Shelves;
import com.framework.core.page.Pagination;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service("shelvesService")
public class ShelvesService {
	@Resource(name="shelvesDao")
	private ShelvesDao shelvesDao;

	/**
	 * 分页查询
	 * @param pageNo 页码
	 * @param pageSize 页面记录数
	 * @param map
	 * @return
	 */
	public Pagination<Shelves> findByPage(int pageNo, Integer pageSize,
										  Map<String, Object> map) {
		return  shelvesDao.findByPage(pageNo, pageSize,map);
	}

	/**
	 * 保存货柜信息
	 * @param shelves 货柜信息
	 * @return
	 */
	public String save(Shelves shelves) {
		int returnId = shelvesDao.save(shelves);
		String goodsCode = String.valueOf(returnId);
		return goodsCode;
	}
	/**
	 * 删除货柜信息
	 * @param goods 删除货柜id信息
	 * @return
	 */
	public void del(Map<String, Object> goods) {
		shelvesDao.remove(goods);
	}

}
