package com.dadou.shop.user.dao;

import com.dadou.shop.user.pojos.User;
import com.framework.core.daos.mybatis.GenericMybatisDao;
import com.framework.core.page.Pagination;
import com.framework.core.utils.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 终端用户管理
 */
@Repository("userDao")
public class UserDao extends GenericMybatisDao<User, String> {
	/**
	 * 分页查询
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination<User> findByPage(int pageNo, Integer pageSize,
										   Map<String, Object> map) {
		map.put("pageNo", pageNo);
		map.put("pageSize", pageSize);
		String userName = (String)map.get("userName");
		if (StringUtils.isNotEmpty(userName)) {
			map.put("userName", "%" + userName.trim() + "%");
		}
		return super.findByPage(pageNo,pageSize,map);
	}

}
