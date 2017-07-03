package com.dadou.sys.dept.controller;

import com.dadou.core.config.ConfigHelper;
import com.dadou.sys.CmsConst;
import com.dadou.sys.dept.pojos.Department;
import com.dadou.sys.dept.service.DepartmentService;
import com.dadou.sys.tree.pojos.MenuItem;
import com.dadou.sys.tree.pojos.TreeNode;
import com.dadou.sys.tree.service.TreeUtils;
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
 * Department对象的操作Action. <br>
 * 负责Department的管理及各种操作
 */
@Controller
@RequestMapping(value="/dept",produces="text/html;charset=UTF-8")
@Scope("prototype")
public class DepartmentController extends BaseController {
	public static final String PREFIX = "modules/sys/dept";
	/**部门处理类**/
	@Resource(name="departmentService")
	private DepartmentService departmentService;
	/**
	 * 转向添加界面
	 */
	@RequestMapping(value="/add")
	public String add(@RequestParam Integer parentId, Map<String,Object> map) {
		map.put("parentId",parentId);
		return PREFIX+"/add";
	}

	/**
	 * 保存部门对象
	 */
	@RequestMapping(value="/save")
	@ResponseBody
	public String save(Department dept) {
		try {
			// 设置按字母查询
			if (!StringUtils.isEmpty(dept.getDeptName())) {
				String deptName = dept.getDeptName();
				String head = deptName.substring(0, 1);
				GB2Alpha gb = new GB2Alpha();
				String alpha = gb.String2Alpha(head);
				dept.setAlpha(alpha);
			}
			//自动生成ID
			dept.setId(getUUID());
			departmentService.save(dept);
			this.putRootJson(ResultMsg.MSG, "部门保存成功!");
			this.putRootJson(ResultMsg.SUCCESS, true);
		} catch (Exception ex) {
			this.putRootJson(ResultMsg.SUCCESS, false);
			String error = ExceptionUtils.formatStackTrace(ex);
			logger.error(error);
			this.putRootJson(ResultMsg.MSG, "部门保存失败!");
		}
		return this.getJsonStr();
	}
	/**
	 * 根据id查询部门对象,并转向部门编辑页面
	 */
	@RequestMapping(value="/edit")
	public String edit(Map<String,Object> map,@RequestParam String id) {
		Department dept = this.departmentService.findById(id);
		map.put("dept", dept);
		return PREFIX+"/edit";
	}

	/**
	 * 更新部门对象
	 */
	@RequestMapping(value="/update")
	@ResponseBody
	public String update(Department dept) {
		try {
			// 设置按字母查询
			if (!StringUtils.isEmpty(dept.getDeptName())) {
				String deptName = dept.getDeptName();
				String head = deptName.substring(0, 1);
				GB2Alpha gb = new GB2Alpha();
				String alpha = gb.String2Alpha(head);
				dept.setAlpha(alpha);
			}
			departmentService.update(dept);
			// 记录系统日志,是不是采用线程处理日志的记录?
			this.putRootJson(ResultMsg.SUCCESS, true);
			this.putRootJson(ResultMsg.MSG, "部门保存成功!");
		} catch (Exception ex) {
			this.putRootJson(ResultMsg.SUCCESS, false);
			String error = ExceptionUtils.formatStackTrace(ex);
			logger.error(error);
			this.putRootJson(ResultMsg.MSG, "部门保存失败!");
		}
		return this.getJsonStr();
	}
	/**
	 * 显示树形结构
	 */
	@RequestMapping(value="/showTree")
	@ResponseBody
	public String showTree() {
		// 获取MenuItem列表
		List<MenuItem> menuItemList = departmentService
				.findDepartmentsAndUsers(ConfigHelper.getValue("dept_tree_id"));

		TreeNode root = TreeUtils.createTree(menuItemList,"-1");
		String json = "["+ JsonUtils.toJson(root) + "]";
		this.putRootJson("menus", json);

		return this.getJsonStr();
	}

	/**
	 * 删除部门
	 */
	@RequestMapping(value="/remove")
	@ResponseBody
	public String remove(@RequestParam String id) {
		try {
			departmentService.remove(id);
			this.putRootJson(ResultMsg.SUCCESS, true);
		} catch (Exception ex) {
			this.putRootJson(ResultMsg.SUCCESS, false);
			String error = ExceptionUtils.formatStackTrace(ex);
			logger.error(error);
		}
		return this.getJsonStr();
	}

	/**
	 * 获取部门名称
	 */
	@RequestMapping(value="/findDeptName")
	@ResponseBody
	public String findDeptName(@RequestParam String id) {
	    Department dept = this.departmentService.findById(id);
	    super.putRootJson("deptName", dept.getDeptName());
	    return this.getJsonStr();
	}
	/**
	 * 根据部门的首字母进行查询
	 * 为员工选择部门时弹出的窗口
	 */
	@RequestMapping(value="/search")
	@ResponseBody
	public String search(@RequestParam(required=false) String alpha, @RequestParam(required=false) String deptName) {
		// 从请求参数中获取pageNumber
		String[] str = ActionContextUtils.getParameters(CmsConst.PAGE_NUMBER_E);
		int pageNo = 1;
		if(str != null && str.length>0)
			pageNo = NumberUtils.toInt(str[0], 1);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(alpha)){
			queryMap.put("alpha", alpha);
		}
		if(StringUtils.isNotEmpty(deptName)){
			queryMap.put("deptName", deptName);
		}
		Pagination<Department> pagination = this.departmentService.findByPage(
				pageNo, this.getPageSize4E(),queryMap);
		// 记录总数
		int max = pagination.getMaxElements();
		// 生成JSON格式的数据
		List<Department> plist = pagination.getList();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>(plist.size());
		for (Department dt : plist) {
		    Map<String, String> tmap = new HashMap<String, String>();
		    String id = dt.getId();
		    tmap.put("id", String.valueOf(id));
		    tmap.put("deptName", dt.getDeptName());
		    tmap.put("description", dt.getDescription());
		    list.add(tmap);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(TOTAL, max);
		map.put(ROWS, list);
		String jsonData = JsonUtils.toJsonIncludeNull(map);
		return jsonData;
	}
	/**
	 * 部门管理界面
	 */
	@RequestMapping(value="/manage")
	public String manage() {
		return PREFIX+"/manage";
	}
	
	/**
	 * 菜单树形界面里的详情
	 * @return
	 */
	@RequestMapping(value="/detail")
	public String detail(@RequestParam String id) {
	    Department dt = this.departmentService.findById(id);
	    ActionContextUtils.setAtrributeToRequest("dept", dt);
	    return PREFIX+"/detail";
	}
}
