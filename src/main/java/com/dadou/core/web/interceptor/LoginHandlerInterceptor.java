package com.dadou.core.web.interceptor;

import com.dadou.sys.emp.pojos.Employee;
import com.dadou.sys.login.LoginManager;
import com.framework.core.exception.ResultMsg;
import com.framework.core.utils.ActionContextUtils;
import com.framework.core.utils.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * =====================================================
 * 创建人：gaof
 * 创建时间：2016/9/7
 * ======================================================
 * 类说明：拦截器模块
 * 可以实现Filter的功能,还可以实现更精确的控制拦截精度
 * <p>
 * ======================================================
 * 除了不对登录进行拦截，对其他模块进行拦截Session拦截。
 * 
 * <dd>备注：
 * @author gaofeng
 * @version 1.01 2016-08-03
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter {
    /**
     * Commons Logging日志实例
     */
    public static Log logger = LogFactory.getLog(LoginHandlerInterceptor.class);
	/**
	 * 上下文路径
	 */
	private String contextPath;
	/**跳转地址**/
	private String loginUrl;
	/**权限地址**/
	private String authUrl;
	/**是否启用操作权限**/
	private boolean operate;
	/**拦截规则 列表**/
	private List<String> rules;
	
	public String getLoginUrl() {
		return loginUrl;
	}
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}
	public List<String> getRules() {
		return rules;
	}
	public void setRules(List<String> rules) {
		this.rules = rules;
	}
	public String getAuthUrl() {
		return authUrl;
	}
	public void setAuthUrl(String authUrl) {
		this.authUrl = authUrl;
	}
	public boolean isOperate() {
		return operate;
	}
	public void setOperate(boolean operate) {
		this.operate = operate;
	}
	/**
	 * 预处理
	 * 1.进行编码
	 * 2.安全控制
	 * <li>当preHandle方法返回false时，从当前拦截器往回（上一个拦截器）执行所有拦截器的afterCompletion方法。</li>
	 * <li>因为当返回false时，DispatcherServlet处理器认为拦截器已经处理完了请求，而不继续执行执行链中的其它拦截器和处理器</li>
	 * <li>当preHandle方法全为true时，执行下一个拦截器,直到所有拦截器执行完。再运行被拦截的Controller。</li>
	 * <li>然后进入拦截器链，运行所有拦截器的postHandle方法,完后从最后一个拦截器往回执行所有拦截器的afterCompletion方法.当有拦截器抛出异常时,会从当前拦截器往回执行所有拦截器的afterCompletion方法</li>
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		contextPath = request.getContextPath();
		// 不用request.getRequestURL()
		//请求路径
		String requestPath = request.getServletPath();
		boolean flag = isMatch(requestPath);
		// 如果匹配成功则继续下一个拦截器
		if (flag) return flag;
		//地址转发
		String redirectUrl = contextPath + loginUrl;
		// 处理登录Session问题,TODO 此处可以改为分布式Session redis
		Employee currentUser = LoginManager.currentUser();
		if (currentUser == null) {
			// 判断请求是不是Ajax请求
			if (ActionContextUtils.isAjaxRequest(request)) {
				String message = StringEscapeUtils
						.escapeJava("Session过期,请重新登录!");
				ResultMsg resultMsg = new ResultMsg();
				resultMsg.setRedirectUrl(redirectUrl);
				resultMsg.setStatusCode(ResultMsg._601);
				resultMsg.setMessage(message);
				ActionContextUtils.writeToHeaders(resultMsg);
			} else {
				// 采用请求转发的方式
				request.getRequestDispatcher(loginUrl).forward(request,response);
			}
		} else {
			if(operate){
			  /*用户已登录的情况*/
			  // 处理当前登录用户不为空的时候
			  // 操作权限(菜单权限+操作权限)
			  Map<String, Map<String, String>> userPermissionMap = currentUser.getUserPermission();
			  //处理通配符,用于该链接下所有的请求都允许访问
			  //普通权限:COMMON
		      //正则权限:REG_COMMON
		      Map<String, String> COMMON_MAP = userPermissionMap.get(Employee.AUTH_COMMON);
		      Map<String, String> REG_COMMON_MAP = userPermissionMap.get(Employee.AUTH_REG_COMMON);
		      //考虑普通权限
			  String url = COMMON_MAP.get(requestPath);
			  if (StringUtils.isNotEmpty(url)) {
				  // 具备权限
				  flag = true;
				  if(logger.isDebugEnabled()){
					logger.debug("具备"+requestPath+"权限...");
				  }
			  }else{
				//看看是否有通配符权限
				if(isMatchReg(REG_COMMON_MAP,requestPath)){
					//说明具备统配权限
					flag  = true;
					if(logger.isDebugEnabled()){
						logger.debug("具备"+requestPath+"通配符权限...");
				    }
				}else{
					flag = false;
					if(logger.isDebugEnabled()){
						logger.debug("不具备"+requestPath+"权限...");
				    }
				}
				
			  }
			  //不具备权限时,回传信息
			  if(!flag){
				 if (ActionContextUtils.isAjaxRequest(request)) {
					// NO AUTH
					String message = "没有该操作权限!";//StringEscapeUtils.escapeJava("没有该操作权限!");
					String json = "{\"" + ResultMsg.SUCCESS + "\":false,\""	+ ResultMsg.MSG + "\":\"" + message + "\"}";
					ActionContextUtils.writeToResponse(json, "json");
				 } else {
					// 采用请求转发的方式
					request.getRequestDispatcher(authUrl).forward(request,response);
				 }
			  }
			}else{
				//默认不开启权限控制
				flag  = true;
			}
		}
		//

		return flag;
	}
	/**
	 * 是否匹配
	 * @param REG_COMMON_MAP 含有正则匹配的权限列表
	 * @param requestPath
	 * @return
	 */
	private boolean isMatchReg(Map<String, String> REG_COMMON_MAP, String requestPath) {
		boolean flag = false;
		//获取所有权限列表
		Set<String> regSets = REG_COMMON_MAP.keySet();
		for(String reg : regSets){
			if(requestPath.matches(reg)){
				//匹配成功
				flag = true;
				break;
			}
		}
		return flag;
	}
	/**
	 * 判断是否规则和路径是否匹配
	 * @param requestPath
	 * @return
	 */
	private boolean isMatch(String requestPath){
		boolean flag = false;
		if (rules != null && rules.size() > 0) {
			// 处理拦截规则
			for (String rule : rules) {
				if (requestPath.matches(rule.trim())) {
					// 只要有一个匹配的规则则跳出循环
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
	//最多五层operate，例如： 
	//对于一种情况
	private  String getMatchRex(String requestPath){
		String operate ="";
		if(StringUtils.isNotEmpty(requestPath)){
			 int lastPos = requestPath.lastIndexOf("/");
			  if(lastPos > 0 ){
				  operate =  requestPath.substring(0,lastPos+1);
				  operate = operate+"*";
			  }
		}
		return operate;
	}
	/**
	 * 后处理
	 * 调用了Service并返回ModelAndView，但未进行页面渲染 可以修改ModelAndView
	 */
	@Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,	ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}
	/**
	 * 返回处理（已经渲染了页面）可以根据ex是否为null，进行判断处理
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,	HttpServletResponse response, Object handler, Exception ex)	throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}
	public static void main(String[] args) {
		String requestPath = "/menu/aaa/list/aa";
		System.out.println(requestPath.matches("/menu/aaa/.*"));
		
		Map<String,String> map = new HashMap<String, String>();
		map.put("/menu/*", "/menu/*");
		System.out.println(map.get("/menu/*"));
		System.out.println("/menu/.*".matches(requestPath));
	}
}