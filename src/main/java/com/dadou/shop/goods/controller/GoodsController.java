package com.dadou.shop.goods.controller;

import com.dadou.shop.goods.pojos.Goods;
import com.dadou.shop.goods.service.GoodsService;
import com.dadou.shop.shop_enum.GoodsFlag;
import com.dadou.shop.shop_enum.GoodsType;
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
@RequestMapping(value="/goods",produces="text/html;charset=UTF-8")
@Scope("prototype")
public class GoodsController extends BaseController {
	public static final String PREFIX = "modules/shop/goods";
	/**商品处理类**/
	@Resource(name="goodsService")
	private GoodsService goodsService;

	/**基础数据处理类**/
	@Resource(name="im_dictionaryService")
	private DictionaryService dictionaryService;

	/**
	 * 商品列表
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
	public String listAjax(@RequestParam(required=false) String q_goodsName,String q_goodsCode,
						   String q_goodsStatus,String q_goodsType) {
		// 从请求参数中获取pageNumber
		String[] str = ActionContextUtils.getParameters(CmsConst.PAGE_NUMBER_E);
		int pageNo = NumberUtils.toInt(str[0], 1);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("q_goodsName", q_goodsName);
		queryMap.put("q_goodsCode", q_goodsCode);
		queryMap.put("q_goodsStatus", q_goodsStatus);
		queryMap.put("q_goodsType", q_goodsType);
		queryMap.put(ORDER, this.getOrder());
		queryMap.put(SORT, this.getSort());
		Pagination<Goods> pagination = this.goodsService.findByPage(
				pageNo, this.getPageSize4E(), queryMap);
		List<Goods> goodslist = pagination.getList();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>(goodslist.size());
		for (Goods goods: goodslist) {
			Map<String, String> goodsMap = new HashMap<String, String>();
			goodsMap.put("id", String.valueOf(goods.getId()));
			goodsMap.put("goodsCode", goods.getGoodsCode());
			//Dictionary goodsType = dictionaryService.findByKeyAndType(goods.getGoodsType(),Dictionary.GOODS_TYPE);
			//if(goodsType!=null){
			//	goodsMap.put("goodsType", goodsType.getValue());
			//}
			goodsMap.put("goodsType", GoodsType.getName(Integer.valueOf(goods.getGoodsType())));
			goodsMap.put("goodsName", goods.getGoodsName());
			goodsMap.put("goodsPrice", String.valueOf(goods.getGoodsPrice()));
			goodsMap.put("flag", GoodsFlag.getName(Integer.valueOf(goods.getFlag())));
			goodsMap.put("goodsASP", String.valueOf(goods.getGoodsASP()));
			goodsMap.put("goodsNum", String.valueOf(goods.getGoodsNum()));
			goodsMap.put("goodsBrand", goods.getGoodsBrand());
			goodsMap.put("goodsPoint", String.valueOf(goods.getGoodsPoint()));
			goodsMap.put("goodsShort", goods.getGoodsShort());
			goodsMap.put("goodsPicUrl", goods.getGoodsPicUrl());
			list.add(goodsMap);
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(TOTAL, pagination.getMaxElements());
		resultMap.put(ROWS, list);
		String jsonData = JsonUtils.toJsonIncludeNull(resultMap);
		return jsonData;
	}


	/**
	 * 添加商品
	 */
	@RequestMapping(value="/add")
	public String add(Map<String,Object> map) {

		//添加商品
		return PREFIX+"/add";
	}

	/**
	 * 保存添加的商品
	 */
	@RequestMapping(value="/save")
	@ResponseBody
	public String add(Goods goods) {
		//商品条码
		String goodsCoade = goods.getGoodsCode();
		if(!StringUtils.isEmpty(goodsCoade)){
			//获取商品信息成功，增加关键字段
			//goods.setFlag("0");//未上架
			//goods.setDeleteflag("0");
			goods.setInserttime(DateUtils.formatDate());
			goods.setInsertemp(LoginManager.getCurrentUserId());
		}
		try {
			this.goodsService.save(goods);
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
	 * 删除商品
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
			Goods delGoods = new Goods();
			delGoods.setId(id);
			delGoods.setDeleteflag("1");
			delGoods.setUpdateemp(LoginManager.getCurrentUserId());
			delGoods.setUpdatetime(DateUtils.formatDate());
			goodsService.del(params);
			putRootJson(ResultMsg.SUCCESS, true);
			putRootJson(ResultMsg.MSG, "商品删除成功!");
		} catch (Exception e) {
			putRootJson(ResultMsg.SUCCESS, false);
			putRootJson(ResultMsg.MSG, "商品删除失败...");
			String error = ExceptionUtils.formatStackTrace(e);
			// 记录异常信息
			logger.error(error);
		}
		return getJsonStr();
	}










/**

	@RequestMapping("/savePic")
	@ResponseBody
	public String saveStampdefByForm(MultipartFile picFile,

								   HttpServletRequest request) throws Exception {
//		InputStream inputStream = picFile.getInputStream();
//		if ((inputStream.available()) != 0) {
//			// inputStream.available()返回文件的字节长度
//			byte[] data = new byte[inputStream.available()];
//			//// 将文件中的内容读入到数组中
//			inputStream.read(data);
//			inputStream.close();
//		}
		// 保存文件
		boolean a = saveFile(request, picFile);
		if (a){
			//添加商品
			return PREFIX+"/list";
		}else{
			//添加商品
			return PREFIX+"/add";
		}
	}

 **/
	/***
	 * 保存文件
	 *
	 * @param file
	 * @return
	 *
	private boolean saveFile(HttpServletRequest request, MultipartFile file) {
		// 判断文件是否为空
		if (!file.isEmpty()) {
			try {
				// 保存的文件路径(如果用的是Tomcat服务器，文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\upload\\文件夹中  )
				String filePath = request.getSession().getServletContext()
						.getRealPath("/") + "upload/" + file.getOriginalFilename();
				System.err.println(filePath);
				File saveDir = new File(filePath);
				if (!saveDir.getParentFile().exists())
					saveDir.getParentFile().mkdirs();
				System.err.println(filePath);
				// 转存文件
				file.transferTo(saveDir);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	*/
}
