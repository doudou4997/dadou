package com.dadou.shop.user.service;

import com.dadou.shop.user.dao.UserDao;
import com.dadou.shop.user.pojos.User;
import com.framework.core.page.Pagination;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service("userService")
public class UserService {
	@Resource(name="userDao")
	private UserDao userDao;

	/**
	 * 分页查询
	 * @param pageNo 页码
	 * @param pageSize 页面记录数
	 * @param map
	 * @return
	 */
	public Pagination<User> findByPage(int pageNo, Integer pageSize,
									   Map<String, Object> map) {
		return  userDao.findByPage(pageNo, pageSize,map);
	}
}
