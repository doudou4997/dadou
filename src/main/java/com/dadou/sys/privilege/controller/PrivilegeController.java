package com.dadou.sys.privilege.controller;

import com.dadou.sys.menu.service.MenuService;
import com.dadou.sys.privilege.pojos.Privilege;
import com.dadou.sys.privilege.service.PrivilegeService;
import com.framework.core.exception.ResultMsg;
import com.framework.core.utils.ActionContextUtils;
import com.framework.core.utils.ExceptionUtils;
import com.framework.core.web.controller.BaseController;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller("sys_PrivilegeController")
@RequestMapping(value="/privilege",produces="text/html;charset=UTF-8")
@Scope("prototype")
public class PrivilegeController extends BaseController {
	public static final String PREFIX = "modules/sys/privilege";
	/**
	 * 业务操作类
	 */
	@Resource(name="sys_privilegeService")
	private PrivilegeService privilegeService ;
	/**
	 * 业务操作类
	 */
	@Resource(name="sys_menuService")
	private MenuService menuService ;
	/**
	 * 转向添加界面
	 */
	@RequestMapping(value="/add")
	public String add(@RequestParam String parentId, Map<String,Object> map){
		map.put("parentId", parentId);
		return PREFIX+"/add";
    }
    /**
     * 保存权限对象
     */	
	@RequestMapping(value="/save")
	@ResponseBody
	public String save(Privilege privilege){
		try {
			//设置提示信息
			//privilege.setId(getUUID());
		    privilegeService.save(privilege);
		    this.putRootJson(ResultMsg.SUCCESS, true);
		    this.putRootJson(ResultMsg.MSG, "权限保存成功!");
		} catch(Exception ex){
			this.putRootJson(ResultMsg.SUCCESS, false);
			this.putRootJson(ResultMsg.MSG, "权限保存失败!");
			String error = ExceptionUtils.formatStackTrace(ex);
			logger.error(error);			
			saveLog(error);
		}
		return this.getJsonStr();
	}
	/**
	 * 根据id删除权限
	 */
	@RequestMapping(value="/remove")
	@ResponseBody
	public String remove(@RequestParam String id){
		try {			
		    privilegeService.remove(id);
		    this.putRootJson(ResultMsg.SUCCESS, true);
		    this.putRootJson(ResultMsg.MSG, "权限删除成功!");
		} catch(Exception ex){
			this.putRootJson(ResultMsg.SUCCESS, false);
			ex.printStackTrace();
			String error = ExceptionUtils.formatStackTrace(ex);
			logger.error(error);			
			//保存错误信息
			this.putRootJson(ResultMsg.MSG, "权限删除失败!");
		}
		return this.getJsonStr();
	}
	/**
	 * 根据id查询权限对象,并转向权限编辑页面
	 */
	@RequestMapping(value="/edit")
	public String edit(@RequestParam String id, Map<String,Object> map){
		Privilege privilege = privilegeService.findById(id);
		map.put("privilege", privilege);
		return PREFIX+"/edit";
	}
	/**
	 * 更新权限对象
	 */
	@RequestMapping(value="/update")
	@ResponseBody
	public String update(Privilege privilegeTmp){
		try{
			Privilege privilege = this.privilegeService.findById(privilegeTmp.getId());
			//把数据封装到privilege中
			privilege.setName(privilegeTmp.getName());
			privilege.setUrl(privilegeTmp.getUrl());
			privilege.setOperate(privilegeTmp.getOperate());
			privilege.setDescription(privilegeTmp.getDescription());
			privilege.setSortNum(privilegeTmp.getSortNum());
			//成功信息
			this.privilegeService.update(privilege);
			this.putRootJson(ResultMsg.SUCCESS, true);
			this.putRootJson(ResultMsg.MSG, "权限更新成功!");
		}catch (Exception ex) {
			this.putRootJson(ResultMsg.SUCCESS, false);
			String error = ExceptionUtils.formatStackTrace(ex);
			logger.error(error);
			this.putRootJson(ResultMsg.MSG, "权限更新失败!");
			saveLog(error);
		}
		return this.getJsonStr();		
	}
	
	/**
	 * 详情
	 */
	@RequestMapping(value="/detail")
	public String detail(@RequestParam String id) {
		Privilege privilege = this.privilegeService.findById(id);
		ActionContextUtils.setAtrributeToRequest("privilege", privilege);
		return PREFIX+"/detail";
	}
	
}
