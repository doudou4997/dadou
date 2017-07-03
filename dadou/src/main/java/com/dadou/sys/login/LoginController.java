package com.dadou.sys.login;

import com.dadou.core.validcode.ImageCodeUtils;
import com.dadou.core.validcode.ValidateCode;
import com.dadou.sys.dic.service.DictionaryService;
import com.dadou.sys.emp.pojos.Employee;
import com.dadou.sys.emp.pojos.UserMenu;
import com.dadou.sys.emp.service.EmployeeService;
import com.dadou.sys.log.pojos.SysLog;
import com.dadou.sys.role.pojos.Role;
import com.dadou.sys.role.pojos.RoleMenu;
import com.dadou.sys.role.service.RoleService;
import com.dadou.sys.sys.pojos.Sys;
import com.dadou.sys.sys.service.SysService;
import com.dadou.sys.tree.pojos.TreeNode;
import com.dadou.sys.tree.service.TreeUtils;
import com.framework.core.exception.BaseAppRuntimeException;
import com.framework.core.exception.ResultMsg;
import com.framework.core.utils.*;
import com.framework.core.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 用与用户的登录与退出
 * 
 * @author gaof
 */
@RequestMapping(value = "/login",produces="text/html;charset=UTF-8")
@Controller
public class LoginController extends BaseController {
	/**
	 * 员工业务类
	 */
	@Resource(name = "sys_employeeService")
	private EmployeeService employeeService;
	/** RoleService **/
	@Resource(name = "sys_roleService")
	private RoleService roleService;
	@Resource(name = "sys_sysService")
	/**子系统**/
	private SysService sysService;
	@Resource(name = "im_dictionaryService")
	private DictionaryService dictionaryService;
	/** 工厂权限 **/
	//@Resource(name="im_siteService")
	//private SiteService siteService;
	/**
	 * 跳转到登录界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "toLogin")
	public String toLogin(Map<String, Object> map) {
		// 跳转到登录界面login.jsp
		return "main/login";
	}
	//跳转到主页面
	@RequestMapping(value = "index")
	public String index(Map<String, Object> map) {
		Employee emp = LoginManager.currentUser();
		if(emp  == null){
			//处理Session过期问题
			return 	"comm/reLogin";
		}
		// 查找子系统
		ActionContextUtils.setAtrributeToRequest("emp", emp);
		// 跳转到登录界面login.jsp
		return "main/index";
	}
	/**
	 * 用户登录
	 */
	@RequestMapping(value = "/loginAjax")
	@ResponseBody
	public String loginAjax() {
		String userName = ActionContextUtils.getParameter("userName");
		String password = ActionContextUtils.getParameter("password");
		if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)){
			putRootJson(ResultMsg.MSG, "用户名或密码为空!");
			putRootJson(ResultMsg.SUCCESS,false);
			return getJsonStr();
		}
		// 获取验证码
		HttpServletRequest request = ActionContextUtils.getRequest();
		Employee emp = null;
		userName = userName.trim();
		emp = employeeService.findByCodeOrUserName(userName);
		// 如果用户被冻结
		if (emp != null && "1".equals(emp.getFreeze())) {
			String msg = "账户冻结";
			// 把信息保存到request中
			ActionContextUtils.setAtrributeToRequest(ResultMsg.MSG, msg);
			if (logger.isDebugEnabled()) {
				logger.debug("用户:" + emp.getUserName() + "已经被冻结!");
			}
			putRootJson(ResultMsg.MSG, "用户已经被冻结!");
			putRootJson(ResultMsg.SUCCESS,false);
			return getJsonStr();
		}
		if (isValidUser(emp, password)) {
			// 更新最后一次登录的日期和IP
			String date = DateUtils.formatDate();
			emp.setLastLoginTime(date);
			String ip = request.getRemoteAddr();
			emp.setLastLoginIp(ip);
			this.employeeService.updateLoginInfo(emp);
			/**
			 * 加载系统所有的操作权限及菜单权限
			 * 分配左侧菜单树,处理系统头部功能点
			 */
			this.initAllAuth(emp);
			/**
			 * 获取工厂权限
			 */
			//this.initSiteAuth(emp);
			// 查找子系统
			ActionContextUtils.setAtrributeToRequest("emp", emp);
			//将数据字典放入session
			//ActionContextUtils.setAttributeToSession(SESSION_DICTIONARY, dictionaryService.getAllMap());
			// 更新缓存
			LoginManager.login(emp);
			// 在后台显示登录成功
			if (logger.isDebugEnabled()) {
				logger.debug("用户:" + emp.getUserName() + "登录成功!");
			}
			//记录日志
			super.saveOperationLog(SysLog.LOG_MODULE_SYS,"用户登录");
			//登录FTPC平台
			//FtpcLoginManager.login("admin", "admin");//TODO
			//登录结束
			putRootJson(ResultMsg.MSG, "登录成功!");
			putRootJson(ResultMsg.SUCCESS,true);
			return getJsonStr();
		} else {
			// 把信息保存到request中
			if (logger.isDebugEnabled()) {
				logger.debug("用户名或密码错误!");
			}
			putRootJson(ResultMsg.MSG, "用户名或密码错误!");
			putRootJson(ResultMsg.SUCCESS,false);
			return getJsonStr();
		}
	}
	/**
	 * 用户登录
	 */
	@RequestMapping(value = "/login")
	public ModelAndView login() {
		String userName = ActionContextUtils.getParameter("userName");
		String password = ActionContextUtils.getParameter("password");
		//String code  = ActionContextUtils.getParameter("code");
		//获取View对象
		ModelAndView view = this.getModelAndView();
		view.setViewName("/comm/reLogin");
		if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)){
			return view;
		}
		
        // 判断用户是否在线
		if (LoginManager.isOnline()) {
			Employee emp = LoginManager.currentUser();
			ActionContextUtils.setAtrributeToRequest("emp", emp);
			// 查找子系统
			view.setViewName("main/index");
			return view;
		}
		// 获取验证码
		HttpServletRequest request = ActionContextUtils.getRequest();
		HttpSession session = request.getSession();
//		String validateCode = (String) session.getAttribute("validateCode");
//		if (StringUtils.isEmpty(validateCode)
//				|| StringUtils.isEmpty(code)) {
//			ActionContextUtils.setAtrributeToRequest("msg", "请输入验证码!");
//			return view;
//		}
//		//删除验证码 防止暴力破解
//		if (!validateCode.trim().equalsIgnoreCase(
//				code.trim())) {
//			ActionContextUtils.setAtrributeToRequest("msg", "验证码不正确!");
//			return view;
//		}

		Employee emp = null;
		if (StringUtils.isEmpty(userName)) {
			ActionContextUtils.setAtrributeToRequest(ResultMsg.MSG, "请输入员工号或用户名!");
			return view;
		}
		userName = userName.trim();
		emp = employeeService.findByCodeOrUserName(userName);
		// 如果用户被冻结
		if (emp != null && "1".equals(emp.getFreeze())) {
			String msg = "账户冻结";
			// 把信息保存到request中
			ActionContextUtils.setAtrributeToRequest(ResultMsg.MSG, msg);
			if (logger.isDebugEnabled()) {
				logger.debug("用户:" + emp.getUserName() + "已经被冻结!");
			}
			return view;
		}
		if (isValidUser(emp, password)) {
			// 更新最后一次登录的日期和IP
			String date = DateUtils.formatDate();
			emp.setLastLoginTime(date);
			String ip = request.getRemoteAddr();
			emp.setLastLoginIp(ip);
			this.employeeService.updateLoginInfo(emp);
			/**
			 * 加载系统所有的操作权限及菜单权限
			 * 分配左侧菜单树,处理系统头部功能点
			 */
			this.initAllAuth(emp);
			/**
			 * 获取工厂权限
			 */
			//this.initSiteAuth(emp);
			// 查找子系统
			ActionContextUtils.setAtrributeToRequest("emp", emp);
			//将数据字典放入session
			//ActionContextUtils.setAttributeToSession(SESSION_DICTIONARY, dictionaryService.getAllMap());
			// 更新缓存
			LoginManager.login(emp);
			// 在后台显示登录成功
			if (logger.isDebugEnabled()) {
				logger.debug("用户:" + emp.getUserName() + "登录成功!");
			}
			//记录日志
			super.saveOperationLog(SysLog.LOG_MODULE_SYS,"用户登录");
			//登录FTPC平台
			//FtpcLoginManager.login("admin", "admin");//TODO
			//登录结束
			view.setViewName("main/index");
			return view;
		} else {
			String msg = "密码不正确!";
			// 把信息保存到request中
			ActionContextUtils.setAtrributeToRequest(ResultMsg.MSG, msg);
			if (logger.isDebugEnabled()) {
				logger.debug("用户名或密码错误!");
			}
			return view;
		}
	}
	/**
	 * 处理员工的当前工厂信息
	 * @param emp
	 */
//	private void initSiteAuth(Employee emp) {
//		List<Site> siteList = siteService.findSitesOfEmp(emp.getId());
//		if(siteList!=null && siteList.size()>0){
//			//设置当前工厂
//			emp.setCurrentSite(siteList.get(0));
//		    emp.getSiteList().addAll(siteList);
//		}
//	}

	/**
	 * 判断是否是正确的用户
	 * 
	 * emp
	 * name
	 * @param pass
	 */
	private boolean isValidUser(Employee emp, String pass) {
		boolean b = false;
		if (emp == null) {
			return b;
		}
		MD5 md = new MD5();
		pass = md.getMD5ofStr(pass);
		if (!emp.getPassword().equals(pass)) {
			return b;
		}
		b = true;
		return b;
	}
	@RequestMapping(value = "/isValidCode")
	@ResponseBody
	public String isValidCode(){
		//获取验证码
		String codeFromSession =(String) ActionContextUtils.getAttribute("validateCode", "session");
		String codeFromRequest = ActionContextUtils.getParameter("validateCode");
		if(StringUtils.isNotEmpty(codeFromSession)&& StringUtils.isNotEmpty(codeFromRequest)){
			if(codeFromRequest.trim().equalsIgnoreCase(codeFromSession.trim())){
				//用户输入的与Session中的相同
				this.putRootJson(ResultMsg.SUCCESS, true);
			}else{
				this.putRootJson(ResultMsg.SUCCESS, false);
			}
		}else{
			this.putRootJson(ResultMsg.SUCCESS, false);
		}
		return this.getJsonStr();
	}
	/**
	 * 导航到left.jsp
	 * @return
	 */
	@RequestMapping(value = "/left")
	public String left(){
		return "main/left";
	}
	/**
	 * 左侧导航树
	 * 用户登录后只初始化一次，后面都放在用户对象中
	 */
	@RequestMapping(value="/leftMenu")
	@ResponseBody
	public String leftMenu() {
		try {
			String sys_id = ActionContextUtils.getParameter("sys_id");
			Employee emp = LoginManager.currentUser();
			if (StringUtils.isEmpty(sys_id)) {
				// 默认获取第一个子系统
				List<Sys> sysList = emp.getSysList();
				Sys sys = sysList.get(0);
				sys_id = sys.getId();
			}
			Map<String, TreeNode> leftMenu = emp.getLeftMenuNode();
			// 获取已有的rootNode
			TreeNode rootNode = leftMenu.get(sys_id);
			if (rootNode == null) {
				Map<String, Set<RoleMenu>> menuMap = emp.getMenuList();
				// MenuTree实用类
				rootNode = new TreeNode();
				// 子系统菜单
				Set<RoleMenu> sysMenu = menuMap.get(sys_id);
				if (sysMenu != null) {
					// 根据系统类型动态分配菜单树
					Set<UserMenu> userMenu = emp.getUserMenuList();
					rootNode = TreeUtils.buildNavTree(sysMenu, userMenu);
				}
				// 保存Json串
				emp.getLeftMenuNode().put(sys_id, rootNode);
			}
			String json = "[" + JsonUtils.toJson(rootNode) + "]";
			this.putRootJson(TreeUtils.ROOT_NODE, json);
			String resultJson = this.getJsonStr();
			return resultJson;
			//return handleJson();
		} catch (Exception ex) {
			String event = ExceptionUtils.formatStackTrace(ex);
			logger.error(event);
			saveLog(event);
			throw new BaseAppRuntimeException(ex);
		}
	}
   /**
    * 分配权限
    * 
    * @param emp
    */
	private void initAllAuth(Employee emp) {
		// 获取用户的角色Id
		List<Role> roleList = roleService.findRolesOfEmp(emp.getId());
		// 初始化
		emp.getRoleList().addAll(roleList);
		// 获取员工的所有角色列表,如果加入缓存后则一条一条的获取
		List<String> roleIds = new ArrayList<>();
		for (Role role : roleList) {
			roleIds.add(role.getId());
		}
		//
		if (roleIds.size() > 0) {
			// 处理权限问题(包括菜单权限和操作权限)
			List<String> authList = roleService
					.findRolePrivilegesByIds(roleIds);
			emp.setUserPermission(authList);
			// 所有角色问题
			List<RoleMenu> roleMenuList = roleService
					.findRoleMenusForLeftTree(roleIds);
			HashSet<String> sysIds = new HashSet<>();
			// 根据角色 并获取每个子系统的所有权限 sysId <-->roleMenu
			// 循环结束后，sysAndMenus中存储的是sysId和对应的用户拥有的菜单列表
			Map<String, Set<RoleMenu>> sysAndMenus = new HashMap<>();

			for (RoleMenu rm : roleMenuList) {
				String sysId = rm.getSysId();
				sysIds.add(sysId);
				Set<RoleMenu> roleMenus = sysAndMenus.get(sysId);
				if (roleMenus == null) {
					roleMenus = new HashSet<>();
					sysAndMenus.put(sysId, roleMenus);
				}
				roleMenus.add(rm);
			}
			// 存储到EMP中
			emp.getMenuList().putAll(sysAndMenus);
			// 获取子系统列表
			if (sysIds != null && sysIds.size() > 0) {
				List<Sys> sysList = sysService.findAllSys(sysIds);
				emp.getSysList().addAll(sysList);
			}
		}

	}

	/**
	 * 退出系统
	 * 
	 */
	@RequestMapping(value = "/logout")
	public ModelAndView logout() throws Exception {
		String result = "forward:/login/toLogin";// 必须使用ModelAndView才能forward
		ModelAndView modelAndView = this.getModelAndView();
		modelAndView.setViewName(result);
		// 获取用户名称
		Employee emp = LoginManager.currentUser();
		if (emp != null) {
			//记录日志
			super.saveOperationLog(SysLog.LOG_MODULE_SYS,"用户退出");
			// 获取request对象
			LoginManager.logout();
		}
		return modelAndView;
	}

	/**
	 * 验证码生成
	 */
	@RequestMapping(value = "/code")
	public void code(HttpServletResponse response) throws Exception {
		// 设置浏览器不要缓存此图片
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		Map<String, Object> resultMap = ImageCodeUtils.createImageCode();

		String rand = String.valueOf(resultMap.get(ValidateCode.RAND));
		// 写到浏览器中、同时保存到Session中
		LoginManager.setAttribute("validateCode", rand);
		byte[] buf = (byte[]) resultMap.get(ValidateCode.BUFFER);
		response.setContentLength(buf.length);
		// 获取输出流对象
		ServletOutputStream out = response.getOutputStream();
		out.write(buf);
		// 关闭流
		out.close();
	}
	
	private String handleJson(){
		StringBuffer buffer = new StringBuffer();
		InputStream in = null;
		BufferedReader br = null;
		try {
			in = LoginController.class.getResource("/menu/menu.js")
					.openStream();
			// .getFile() + "menu.json";
			br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				if(line.contains("//")){
					//只支持单行注释
					continue;
				}
				buffer.append(line.trim());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (Exception e) {
			}

		}
		String result = buffer.toString();
		return result;
	}
    /**
     * 修改密码
     */
	@RequestMapping("/changePwd")
    public String changePwd() {
        return "/main/chgPwd";
    }
	/**
	 * 设置当前工厂信息
	 * @return
	 */
//	@RequestMapping("/setCurrentSite")
//	@ResponseBody
//    public String setCurrentSite(){
//		try{
//			String siteId = ActionContextUtils.getParameter("siteId");
//			Employee emp = LoginManager.currentUser();
//			for(Site site : emp.getSiteList()){
//				if(siteId.equals(site.getId())){
//					emp.setCurrentSite(site);
//					break;
//				}
//			}
//	        putRootJson(ResultMsg.SUCCESS, true);
//	        putRootJson(ResultMsg.MSG, "当前工厂设置成功!");
//		}catch(Exception ex){
//	        putRootJson(ResultMsg.SUCCESS, false);
//	        putRootJson(ResultMsg.MSG, "当前工厂设置失败!");
//		}
//       return getJsonStr();
//    }
    /**
     * 更新密码
     * @return
     */
	@RequestMapping("/updatePwd")
	@ResponseBody
    public String updatePwd() {
        // 把http返回报头里的Content-Type属性设置成如下内容： 
        // Content-Type: text/html;charset=UTF-8
        // 这样做是为了避免ＩＥ解析通过ajax返回的信息时出现乱码
        HttpServletResponse response = ActionContextUtils.getResponse();
        response.setContentType("text/html; charset=UTF-8");
        // 原密码
        String po = ActionContextUtils.getParameter("po");
        // 新密码
        String p1 = ActionContextUtils.getParameter("p1");
        // 确认新密码
        String p2 = ActionContextUtils.getParameter("p2");
        if (StringUtils.isEmpty(po)) {
            // 如果原密码为空
            super.putRootJson(ResultMsg.SUCCESS, false);
            super.putRootJson(ResultMsg.MSG, "原密码不能为空");
        } else if (StringUtils.isEmpty(p1)) {
            // 如果新密码为空
            super.putRootJson(ResultMsg.SUCCESS, false);
            super.putRootJson(ResultMsg.MSG, "新密码不能为空");
        } else if (StringUtils.isEmpty(p2)) {
            // 如果确认新密码为空
            super.putRootJson(ResultMsg.SUCCESS, false);
            super.putRootJson(ResultMsg.MSG, "确认新密码不能为空");
        } else {
            // 如果原密码、新密码、确认新密码都不为空
            po = po.trim();
            p1 = p1.trim();
            p2 = p2.trim();
            if (!p1.equals(p2)) {
                // 如果新密码和确认新密码不一致
                super.putRootJson(ResultMsg.SUCCESS, false);
                super.putRootJson(ResultMsg.MSG, "新密码和确认新密码不一致");
            } else {
                Employee emp = LoginManager.currentUser();
                String password = emp.getPassword();
                MD5 md5 = new MD5();
                po = md5.getMD5ofStr(po);
                if (password.equals(po)) {
                    // 如果原密码正确
                    String passwordMD5 = md5.getMD5ofStr(p1);
                    employeeService.updatePass(emp.getId(), passwordMD5);
                    emp.setPassword(passwordMD5);
                    super.putRootJson(ResultMsg.SUCCESS, true);
                } else {
                    // 如果原密码不正确
                    super.putRootJson(ResultMsg.SUCCESS, false);
                    super.putRootJson(ResultMsg.MSG, "原密码不正确");
                }
            }
        }
        return getJsonStr();
    }
	public static void main(String[] args) {
		HashSet<String> set = new HashSet<>();
		set.add("11");
		set.add("22");
		set.add("11");
		System.out.println(set);
	}
}
