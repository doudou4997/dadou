package com.dadou.core.web.interceptor;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
/**
 * =====================================================
 * 创建人：gaof
 * 创建时间：2016/11/21
 * ======================================================
 * 类说明：拦截器模块 只针对操作界面进行拦截
 * 
 * <dd>备注：
 * @author gaofeng
 * @version 1.01 2016-11-21
 */
public class OperatorHandlerInterceptor extends HandlerInterceptorAdapter {
	/**
	 * 上下文路径
	 */
	private String contextPath;
	/**跳转地址**/
	private String loginUrl;
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
		String redirectUrl = contextPath+loginUrl;
	    boolean flag = true;
	    //不用request.getRequestURL()
	    String requestPath = request.getServletPath();
	    if("/op".equals(requestPath) || "/op/".equals(requestPath)){
	    	try{
	    		response.sendRedirect(redirectUrl);
	    		//停止后面的拦截器
	    		return false;
	    	}catch(Exception ex){
	    		//TODO
	    	}
	    }
		if(rules != null && rules.size() > 0){
			//处理拦截规则
			for(String rule : rules){
				if(requestPath.matches(rule.trim())){
					//只要有一个匹配的规则则跳出循环
					flag = true;
					break;
				}
			}
		}
		//如果匹配成功则继续下一个拦截器
		if(flag) return flag;
		return flag;
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
}