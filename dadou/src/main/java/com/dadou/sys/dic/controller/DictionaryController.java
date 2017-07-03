package com.dadou.sys.dic.controller;

import com.dadou.sys.dic.pojos.Dictionary;
import com.dadou.sys.dic.service.DictionaryService;
import com.dadou.sys.log.pojos.SysLog;
import com.framework.core.exception.ResultMsg;
import com.framework.core.utils.ExceptionUtils;
import com.framework.core.utils.JsonUtils;
import com.framework.core.web.controller.BaseController;
import com.framework.core.web.ui.OptionsString;
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
 * 数据字典 Controller
 * @author wangs
 *
 */
@Controller("im_dictionaryController")
@RequestMapping(value = "/dict", produces = "text/html;charset=UTF-8")
@Scope("prototype")
public class DictionaryController extends BaseController {
	public static final String PREFIX = "modules/sys/dict";
	
	@Resource(name="im_dictionaryService")
	DictionaryService dictionaryService;
	/**xin
	 * 数据字典列表
	 */
	@RequestMapping(value="/list")
	public String list() {
		return PREFIX+"/list";
	}
	
	/**
	 * 数据字典列表
	 * @param typeName
	 * @return
	 */
	@RequestMapping(value="/listAjax")
	@ResponseBody
	public String listAjax(@RequestParam(required=false) String typeName) {
		Map<String, Object> map = new HashMap<String, Object>();
			List<Dictionary> typeNameList = dictionaryService.findDictList(typeName);
			List<Dictionary> fatherList = new ArrayList<Dictionary>();
			//遍历父节点，转成可显示的数据
			for (Dictionary dic : typeNameList) {
				Dictionary d = new Dictionary();
				d.setValue(dic.getTypeName());
				fatherList.add(d);
			}
			//查找子节点数据
			List<Dictionary> chilList = dictionaryService.findByTypeName(typeNameList);
			//把孩子和父放到一个List中
			chilList.addAll(fatherList);
			map.put("totle", chilList.size());
			map.put("rows", chilList);
		return JsonUtils.toJsonIncludeNull(map);
	}
	
	/**
	 * 添加页面
	 */
	@RequestMapping(value="/add")
	public String add() {
		return PREFIX+"/add";
	}
	
	/**
	 * 父节点下拉框
	 */
	@RequestMapping(value="/parentAjax")
	@ResponseBody
	public String parentAjax() {
		List<Dictionary> typeNameList = dictionaryService.findDictList(null);
		System.out.println(JsonUtils.toJsonIncludeNull(typeNameList));
		return JsonUtils.toJsonIncludeNull(typeNameList);
	}
	
	/**
	 * 校验实际的值是否存在
	 */
	@RequestMapping(value="/saveAjax")
	@ResponseBody
	public String saveAjax(@RequestParam String key, @RequestParam String type){
		Dictionary dic = dictionaryService.findByKeyAndType(key,type);
		if(dic!=null){
			//说明已经存在了
			putRootJson(ResultMsg.MSG, type+"中，"+key+"已存在了，请核实！");
			putRootJson(ResultMsg.SUCCESS, false);
		}else{
			putRootJson(ResultMsg.SUCCESS, true);
		}
		return getJsonStr();
	}
	
	
	/**
	 * 添加
	 * @param value  显示的名称
	 * @param key  实际的值
	 * @param type  类型值
	 * @param typeName  类型名称
	 * @return
	 */
	@RequestMapping(value="/save")
	@ResponseBody
	public String saveDict(@RequestParam String value, @RequestParam String key, @RequestParam String type, @RequestParam String typeName) {
		Dictionary dic = new Dictionary();
		dic.setValue(value);
		dic.setKey(key);
		dic.setType(type);
		dic.setTypeName(typeName);
		try {
			dictionaryService.save(dic);
			putRootJson(ResultMsg.MSG, "数据字典保存成功！");
			putRootJson(ResultMsg.SUCCESS, true);
			//关键操作日志记录
			saveOperationLog(SysLog.LOG_MODULE_IM,"添加数据字典基础信息");
		} catch (Exception e) {
			putRootJson(ResultMsg.MSG, "数据字典保存失败");
			putRootJson(ResultMsg.SUCCESS, false);
			String error = ExceptionUtils.formatStackTrace(e);
			logger.error(error);
			saveLog(error);
		}
		return getJsonStr();
	}
	
	@RequestMapping(value="/remove")
	@ResponseBody
	public String remove(@RequestParam String id) {
		try {
			dictionaryService.removeById(id);
			putRootJson(ResultMsg.SUCCESS, true);
			putRootJson(ResultMsg.MSG, "删除成功");
			//关键操作日志记录
			saveOperationLog(SysLog.LOG_MODULE_IM,"删除数据字典基础信息");
		} catch (Exception e) {
			putRootJson(ResultMsg.SUCCESS, false);
			putRootJson(ResultMsg.MSG, "删除失败");
			String error = ExceptionUtils.formatStackTrace(e);
			logger.error(error);
			saveLog(error);
		}
		return getJsonStr();
	}
	
	/**
	 * 编辑
	 * @param id 数据id
	 * @return
	 */
	@RequestMapping(value="/edit")
	public String edit(@RequestParam String id, Map<String, Object> map) {
		Dictionary dict = dictionaryService.findById(id);
		map.put("dict", dict);
		return PREFIX+"/edit";
	}
	
	/**
	 * 修改
	 * @param dict
	 * @return
	 */
	@RequestMapping(value="/update")
	@ResponseBody
	public String update(Dictionary dict) {
		try {
			dictionaryService.update(dict);
			putRootJson(ResultMsg.SUCCESS, true);
			putRootJson(ResultMsg.MSG, "编辑成功");
			//关键操作日志记录
			saveOperationLog(SysLog.LOG_MODULE_IM,"编辑数据字典基础信息");
		} catch (Exception e) {
			putRootJson(ResultMsg.SUCCESS, false);
			putRootJson(ResultMsg.MSG, "编辑失败");
			String error = ExceptionUtils.formatStackTrace(e);
			logger.error(error);
			saveLog(error);
		}
		return getJsonStr();
	}
	/**
	 * 获取数据字典
	 */
	@RequestMapping(value="/dictionary")
	@ResponseBody
	public String dictionary(String dtype){
		Map<String,String> map = this.getKeyAndValueByType(dtype);
		List<OptionsString> list = new ArrayList<>();
		list.add(new OptionsString("","--"));
		for (Map.Entry<String, String> entry : map.entrySet()) {
			list.add(new OptionsString(entry.getKey(), entry.getValue()));
		}
		return JsonUtils.toJsonIncludeNull(list);
	}
	
}
