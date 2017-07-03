package com.dadou.shop.user.controller;

import com.dadou.shop.user.pojos.User;
import com.dadou.shop.user.service.UserService;
import com.dadou.sys.CmsConst;
import com.framework.core.page.Pagination;
import com.framework.core.utils.ActionContextUtils;
import com.framework.core.utils.JsonUtils;
import com.framework.core.web.controller.BaseController;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户对象的操作Action. <br>
 * 负责Department的管理及各种操作
 */
@Controller
@RequestMapping(value="/user",produces="text/html;charset=UTF-8")
@Scope("prototype")
public class UserController extends BaseController {
	public static final String PREFIX = "modules/shop/user";
	/**用户处理类**/
	@Resource(name="userService")
	private UserService userService;
	/**
	 * 用户列表
	 */
	@RequestMapping(value="/list")
	public String list(Map<String,Object> map) {
		//默认情况下,分配权限时出现权限树
		return PREFIX+"/list";
	}

	/**
	 * Ajax列表
	 */
	@RequestMapping(value="/listAjax")
	@ResponseBody
	public String listAjax(@RequestParam(required=false) String userName) {
		// 从请求参数中获取pageNumber
		String[] str = ActionContextUtils.getParameters(CmsConst.PAGE_NUMBER_E);
		int pageNo = NumberUtils.toInt(str[0], 1);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("userName", userName);
		queryMap.put(ORDER, this.getOrder());
		queryMap.put(SORT, this.getSort());
		Pagination<User> pagination = this.userService.findByPage(
				pageNo, this.getPageSize4E(), queryMap);
		List<User> userlist = pagination.getList();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>(userlist.size());
		for (User user: userlist) {
			Map<String, String> usermap = new HashMap<String, String>();
			String empId = user.getId();
			usermap.put("id", empId);
			usermap.put("username", user.getUsername());
			usermap.put("openid", user.getOpenId());
			usermap.put("gender", user.getGender());
			usermap.put("followtime", user.getFollowtime());
			usermap.put("point", String.valueOf(user.getPoint()));
			usermap.put("phone", user.getPhone());
			list.add(usermap);
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(TOTAL, pagination.getMaxElements());
		resultMap.put(ROWS, list);
		String jsonData = JsonUtils.toJsonIncludeNull(resultMap);
		return jsonData;
	}
}
