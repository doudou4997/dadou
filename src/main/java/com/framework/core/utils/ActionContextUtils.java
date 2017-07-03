package com.framework.core.utils;

import com.framework.core.exception.ResultMsg;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * 处理ActionContext中的关于request,session,application等范围对象中的方法。
 * @since 2011-4-12
 */
@SuppressWarnings("unchecked")
public final class ActionContextUtils {
    /**
     * Commons Logging日志实例
     */
    public static Log logger = LogFactory.getLog(ActionContextUtils.class);
    /**
     * 获取指定的键所对应的对象的值 。<br>
     * 对应HttpServletRequest对象中的request.getParameterMap()方法。
     * 
     * @param key
     *            指定的键
     * @return 指定的键所映射到的值
     */
    public static String[] getParameters(String key) {
     	HttpServletRequest request = getRequest();
    	return request.getParameterValues(key);
    }

    /**
     * 获取指定的键所对应的对象的值 的第一个字符串。<br>
     * 对应HttpServletRequest对象中的request.getParameterMap()方法。
     * 
     * @param key
     *            指定的键
     * @return 指定的键所映射到的值的第一个字符串
     */
    public static String getParameter(String key) {
    	HttpServletRequest request = getRequest();
    	return request.getParameter(key);
    }

    /**
     * 从指定的对象范围中获取指定的键所对应的对象，并返回该对象的值。<br/> 如果指定的对象范围内不包含指定键的任何映射，则返回{@code NULL}。<br/>
     * 该方法对应对象范围的getAttribute()方法。
     * 
     * @param scopeName
     *            对象范围的名称 （request,session,application）
     * @param key
     *            指定的键
     * @return 指定的键所映射到的值
     */
    public static Object getAttribute(String key, String scopeName) {
        if (StringUtils.isEmpty(scopeName)) {
            scopeName = "session";
        }
        if (!"request".equals(scopeName) && !"session".equals(scopeName)
                && !"application".equals(scopeName)) {
            throw new RuntimeException(
                    "scopeName的值只能是request或session或application!");
        }
        if(scopeName.equals("session")){
        	return getRequest().getSession().getAttribute(key);
        }
        if(scopeName.equals("request")){
        	return getRequest().getAttribute(key);
        }
        return getServletContext().getAttribute(key);
    }

    /**
     * 将对象保存在request中，对应setAttribute()方法。
     * 
     * @param key
     *            对象标识（键）
     * @param value
     *            保存对象
     */
    public static void setAtrributeToRequest(String key, Object value) {
        // 返回request对象
    	HttpServletRequest request = getRequest();
        request.setAttribute(key, value);
    }

    /**
     * 将对象保存在session中，对应setAttribute()方法。
     * 
     * @param key
     *            对象标识
     * @param value
     *            保存对象
     */
    public static void setAttributeToSession(String key, Object value) {
        // 返回session对象
    	HttpSession session = getRequest().getSession();
        session.setAttribute(key, value);
    }

    /**
     * 将对象保存在application中，对应ServletContext的setAttribute()方法。
     * 
     * @param key
     *            对象标识
     * @param value
     *            保存对象
     */
    public static void setAttributeToApplication(String key, Object value) {
        // 返回application对象
    	ServletContext context = getServletContext();
    	if(context!=null){
    		context.setAttribute(key, value);
    	}
    }

    /**
     * 从request中删除指定的对象，对应ServletRequest的removeAttribute()方法。
     * 
     * @param key
     *            对象标识
     */
    public static void removeAttrFromRequest(String key) {
        HttpServletRequest request = getRequest();
        request.removeAttribute(key);
    }

    /**
     * 从session中删除指定的对象，对应HttpSession的removeAttribute()方法。
     * 
     * @param key
     *            对象标识
     */
    public static void removeAttrFromSession(String key) {
        HttpServletRequest request = getRequest();
        request.getSession().removeAttribute(key);
    }

    /**
     * 从application中删除指定的对象，对应ServletContext的removeAttribute()方法。
     * 
     * @param key
     *            对象标识
     */
    public static void removeAttrFromApplication(String key) {
        ServletContext context = getServletContext();
        context.removeAttribute(key);
    }

    /**
     * 获取HttpServletRequest对象。
     * 
     * @return HttpServletRequest对象
     */
    public static HttpServletRequest getRequest() {
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();;
        return request;
    }
    /**
     * 判断是否是Ajax请求
     * @return
     */
    public static boolean isAjaxRequest() {
        HttpServletRequest request = getRequest();
        if (request.getHeader("x-requested-with") != null
                && request.getHeader("x-requested-with").equalsIgnoreCase(
                        "XMLHttpRequest")) {
            return true;
        }
        return false;
    }
    /**
     * 判断是否是Ajax请求
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        if (request.getHeader("x-requested-with") != null
                && request.getHeader("x-requested-with").equalsIgnoreCase(
                        "XMLHttpRequest")) {
            return true;
        }
        return false;
    }
    /**
     * 获取HttpServletResponse对象。
     * 
     * @return HttpServletResponse对象
     */
    public static HttpServletResponse getResponse() {
    	HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    	return response;
    }

    /**
     * 获取ServletContext对象。
     * 
     * @return ServletContext对象
     */
    public static ServletContext getServletContext() {
        return getRequest().getServletContext();
    }

    /**
     * 获取工程上下文路径。
     * 
     * @return 工程上下文路径
     */
    public static String getContextPath() {
        return getRequest().getContextPath();
    }
    /**
     * 把Json字符串写到response中
     * 
     * @param jsonStr
     *            Json字符串
     */
    public static void writeToResponse(String content,String type) {
        try {
        	HttpServletResponse httpResponse =  ActionContextUtils.getResponse();
			httpResponse.setContentType("text/"+type+";charset=UTF-8"); 
			httpResponse.setHeader("Cache-Control", "no-cache"); 
			PrintWriter out = httpResponse.getWriter();
            out.write(content);
            out.flush();
            out.close();
        } catch (Exception e) {
            // 添加异常日志
           String error = ExceptionUtils.formatStackTrace(e);
           logger.error(error);
        }
    }
    public static void writeToHeaders(ResultMsg resultMsg){
		try {
			HttpServletResponse response = ActionContextUtils.getResponse();
			response.setContentType("text/json;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			String json = JsonUtils.toJson(resultMsg);
			response.setHeader(ResultMsg.RESULT_MSG, json);
			PrintWriter out = response.getWriter();
			out.print("{\""+ResultMsg.SUCCESS+"\":false,\""+ResultMsg.MSG+"\":\""+resultMsg.getMessage()+"\"}");
			out.flush();
			out.close();
		} catch (Exception e) {
			// 添加异常日志
			String error = ExceptionUtils.formatStackTrace(e);
			logger.error(error);
		}
    }
}
