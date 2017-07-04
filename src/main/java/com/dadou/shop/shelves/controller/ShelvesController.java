package com.dadou.shop.shelves.controller;

import com.dadou.shop.shelves.pojos.Shelves;
import com.dadou.shop.shelves.service.ShelvesService;
import com.dadou.shop.shop_enum.GoodsFlag;
import com.dadou.shop.shop_enum.ShelvesType;
import com.dadou.sys.CmsConst;
import com.dadou.sys.dic.service.DictionaryService;
import com.dadou.sys.login.LoginManager;
import com.framework.core.exception.ResultMsg;
import com.framework.core.page.Pagination;
import com.framework.core.utils.*;
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
 * 货柜对象的操作Action. <br>
 * 负责货柜的管理及各种操作
 */
@Controller
@RequestMapping(value="/shelves",produces="text/html;charset=UTF-8")
@Scope("prototype")
public class ShelvesController extends BaseController {
	public static final String PREFIX = "modules/shop/shelves";
	/**货柜处理类**/
	@Resource(name="shelvesService")
	private ShelvesService shelvesService;

	/**基础数据处理类**/
	@Resource(name="im_dictionaryService")
	private DictionaryService dictionaryService;

	/**
	 * 货柜列表
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
	public String listAjax(@RequestParam(required=false) String q_shelvesName,String q_shelvesCode,
						   String q_shelvesStatus,String q_shelvesType) {
		// 从请求参数中获取pageNumber
		String[] str = ActionContextUtils.getParameters(CmsConst.PAGE_NUMBER_E);
		int pageNo = NumberUtils.toInt(str[0], 1);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("q_shelvesName", q_shelvesName);
		queryMap.put("q_shelvesCode", q_shelvesCode);
		queryMap.put("q_shelvesStatus", q_shelvesStatus);
		queryMap.put("q_shelvesType", q_shelvesType);
		queryMap.put(ORDER, this.getOrder());
		queryMap.put(SORT, this.getSort());

		Pagination<Shelves> pagination = this.shelvesService.findByPage(
				pageNo, this.getPageSize4E(), queryMap);
		List<Shelves> shelveslist = pagination.getList();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>(shelveslist.size());
		for (Shelves shelves: shelveslist) {
			Map<String, String> shelvesMap = new HashMap<String, String>();
			shelvesMap.put("id",shelves.getId());
			shelvesMap.put("shelvesCode",shelves.getShelvesCode());
			shelvesMap.put("shelvesType", ShelvesType.getName(Integer.valueOf(shelves.getShelvesType())));
			shelvesMap.put("shelvesName",shelves.getShelvesName());
			shelvesMap.put("flag", GoodsFlag.getName(Integer.valueOf(shelves.getFlag())));
			shelvesMap.put("shelvesCapacity", String.valueOf(shelves.getShelvesCapacity()));
			shelvesMap.put("shelvesAddress",shelves.getShelvesAddress());
			list.add(shelvesMap);
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(TOTAL, pagination.getMaxElements());
		resultMap.put(ROWS, list);
		String jsonData = JsonUtils.toJsonIncludeNull(resultMap);
		return jsonData;
	}


	/**
	 * 添加货柜
	 */
	@RequestMapping(value="/add")
	public String add(Map<String,Object> map) {

		//添加货柜
		return PREFIX+"/add";
	}

	/**
	 * 保存添加的货柜
	 */
	@RequestMapping(value="/save")
	@ResponseBody
	public String add(Shelves shelves) {
		//货柜条码
		String shelvesCode = shelves.getShelvesCode();
		if(!StringUtils.isEmpty(shelvesCode)){
			//获取货柜信息成功，增加关键字段
			//shelves.setFlag("0");//未上架
			//shelves.setDeleteflag("0");
			shelves.setInserttime(DateUtils.formatDate());
			shelves.setInsertemp(LoginManager.getCurrentUserId());
		}
		try {
			this.shelvesService.save(shelves);
			this.putRootJson(ResultMsg.SUCCESS, true);
			this.putRootJson(ResultMsg.MSG, "商品保存成功!");
		} catch (Exception e) {
			this.putRootJson(ResultMsg.SUCCESS, false);
			this.putRootJson(ResultMsg.MSG, "商品保存失败!");
			String error = ExceptionUtils.formatStackTrace(e);
			logger.error(error);
			saveLog(error);
		}
		return getJsonStr();
	}

	/**
	 * 删除货柜
	 */
	@RequestMapping(value="/del")
	@ResponseBody
	public String removeEmp(@RequestParam String id){
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("id",id);
			params.put("deleteflag","1");
			params.put("updateemp",LoginManager.getCurrentUserId());
			params.put("updatetime",DateUtils.formatDate());
			shelvesService.del(params);
			putRootJson(ResultMsg.SUCCESS, true);
			putRootJson(ResultMsg.MSG, "货柜删除成功!");
		} catch (Exception e) {
			putRootJson(ResultMsg.SUCCESS, false);
			putRootJson(ResultMsg.MSG, "货柜删除失败...");
			String error = ExceptionUtils.formatStackTrace(e);
			// 记录异常信息
			logger.error(error);
		}
		return getJsonStr();
	}


}
