package com.dadou.shop.shelves.dao;

import com.dadou.shop.shelves.pojos.Shelves;
import com.framework.core.daos.mybatis.GenericMybatisDao;
import com.framework.core.page.Pagination;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 商品管理
 */
@Repository("shelvesDao")
public class ShelvesDao extends GenericMybatisDao<Shelves, String> {
	/**
	 * 分页查询
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination<Shelves> findByPage(int pageNo, Integer pageSize,
                                        Map<String, Object> map) {
		map.put("pageNo", pageNo);
		map.put("pageSize", pageSize);
		return super.findByPage(pageNo,pageSize,map);
	}

}
