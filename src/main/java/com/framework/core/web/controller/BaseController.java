package com.framework.core.web.controller;

import com.dadou.sys.dic.service.DictionaryService;
import com.dadou.sys.dic.pojos.Dictionary;
import com.dadou.sys.emp.pojos.Employee;
import com.dadou.sys.log.pojos.SysLog;
import com.dadou.sys.log.service.LogService;
import com.dadou.sys.login.LoginManager;
import com.framework.core.exception.ResultMsg;
import com.framework.core.utils.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 基础控制器
 * @since 2015-11-05
 *
 */
public abstract class BaseController {
    /**
     * Commons Logging日志实例
     */
    public static Log logger = LogFactory.getLog(BaseController.class);
    /**
     * 用户票据在Cookie中的保存时间
     */
    public static final int COOKIE_USER_TICKET_S = 60 * 60 * 24 * 365; // 365天

    static final String ERROR_MSG = "出错啦！我们对此表示歉意，请告知我们此次操作的出错过程，我们会尽快改进。谢谢！";
    static final String NULL_MSG = "未找到该记录";
    /**
     * JsonObject
     */
    private JsonObject json = new JsonObject();
    
    ////////////////////////////
    /// Jquery Easy UI //////////
	public static final String ORDER = "order";//排序 desc asc
	public static final String SORT = "sort";
	public static final String ROWS = "rows";  //行数
	public static final String TOTAL = "total";//符合条件的总条数
	public static final String MERGES= "merges";//用于动态合并多行

    /** 拦截器中设置 **/
    private Integer pageSize;
    /** 数据字典 **/
    @Resource(name = "im_dictionaryService")
    private DictionaryService dictionaryService;
    /**
     * 日志模块
     */
    @Resource(name="sys_logService")
    private LogService logService;

	 /** 基于@ExceptionHandler异常处理 */  
    @ExceptionHandler
    public void throwException(Exception e) throws IOException,ServletException {
    	try{
    		String error = ExceptionUtils.formatStackTrace(e);
        	logger.error(error);
        	//保存日志信息
            saveLog(error);
    	}catch(Exception ex){
    		//TODO
    	}
    	HttpServletRequest request = ActionContextUtils.getRequest();
    	//返回对象
    	ResultMsg resultMsg = new ResultMsg();
    	if(ActionContextUtils.isAjaxRequest(request)){
        	//定义返回信息编码
    		//是Ajax请求
        	Employee emp = LoginManager.currentUser();
        	if(emp == null){
            	//转成UNICODE编码
            	String message = StringEscapeUtils.escapeJava("Session过期,请重新登录!");
            	String ctx= request.getContextPath();
            	String redirectUrl = ctx+"/main/login.jsp";
            	resultMsg.setRedirectUrl(redirectUrl);
        		resultMsg.setStatusCode(ResultMsg._601);
        		resultMsg.setMessage(message);
        	}else{
        		if(e instanceof NullPointerException){
        			resultMsg.setMessage("NullPointerException 异常!");
        		}else{
        			resultMsg.setMessage(e.getMessage());
        		}
        		resultMsg.setStatusCode(ResultMsg._602);
        	}
        	//写Ajax
        	ActionContextUtils.writeToHeaders(resultMsg);
    	}else{
    		//非Ajax请求
            request.setAttribute(ResultMsg.RESULT_MSG, resultMsg);  
            //信息跳转
            // TODO
            //sendRedirect(resultMsg.getRedirectUrl());
            //后面不能再有返回
    	}

    }
    //重定向
	protected void sendRedirect( String targetUrl) throws IOException {
		HttpServletResponse response = ActionContextUtils.getResponse();
		response.sendRedirect(response.encodeRedirectURL(targetUrl));
	}
	//请求转发
	protected void forwardRedirect(String targetUrl) throws IOException,ServletException {
		HttpServletRequest request = ActionContextUtils.getRequest();
		HttpServletResponse response = ActionContextUtils.getResponse();
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(targetUrl);
		requestDispatcher.forward(request, response);
	}
    /**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
	/**
	 * 得到ModelAndView
	 */
	public ModelAndView getModelAndView(){
		return new ModelAndView();
	}
    /**
     * 一页显示的行数 10 20 30
     * @return
     */
    public Integer getPageSize() {
        pageSize = (Integer) ActionContextUtils.getAttribute("pageSize",
                "request");
        if (pageSize == null) {
            String temp = ActionContextUtils.getParameter("pageSize");
            pageSize = NumberUtils.toInt(temp, 20);
        }
        return pageSize;
    }
    /**
     * Jquery easyUi传递上来的page 为页码 rows 为行数
     * @return
     */
    public Integer getPageSize4E() {
        pageSize = (Integer) ActionContextUtils.getAttribute("rows",
                "request");
        if (pageSize == null) {
            String temp = ActionContextUtils.getParameter("rows");
            pageSize = NumberUtils.toInt(temp, 20);
        }
        return pageSize;
    }


    // ////////////////////////////////
    // //放置的Json对象与msg同级
    // /////////////////////////////////
    /**
     * 放置的json与msg同级
     */
    public BaseController putRootJson(String key, String value) {
        json.addProperty(key, value);
        return this;
    }

    public BaseController putRootJson(String key, Character value) {
        json.addProperty(key, value);
        return this;
    }

    public BaseController putRootJson(String key, Number value) {
        json.addProperty(key, value);
        return this;
    }

    public BaseController putRootJson(String key, Boolean value) {
        json.addProperty(key, value);
        return this;
    }
    public void writeToResponseForUpload(String jsonStr) {
        try {
            HttpServletRequest request = ActionContextUtils.getRequest();
            HttpServletResponse response = ActionContextUtils.getResponse();
            String contentType;
            String agent = request.getHeader("USER-AGENT");
            // 判断浏览器是否为火狐
            if (null != agent && -1 != agent.indexOf("Firefox")) {
                contentType = "application/json";
            } else {
                contentType = "text/html; charset=UTF-8";
            }
            response.setContentType(contentType);
            PrintWriter out = response.getWriter();
            out.write(jsonStr);
            out.flush();
            out.close();
        } catch (Exception e) {
            // 添加异常日志
        	String msg = ExceptionUtils.formatStackTrace(e);
        	logger.error(msg);
        	saveLog(msg);
        }
    }
    /**
     * SESSION中的数据字典： 根据类型和键值获得文字（数据字典数据）
     */
    //public final String SESSION_DICTIONARY = "sysDictionary";
    public String getValueByKey(String type,String key){
//        Map<String,Map<String,String>> map=(Map<String,Map<String,String>>) ActionContextUtils.getAttribute("sysDictionary", "session");
//        if(map==null){//如果session中数据为空则重新查询
//            map=dictionaryService.getAllMap();
//            ActionContextUtils.setAttributeToSession(SESSION_DICTIONARY, map);
//        }
//        if(map.get(type)!=null){
//            return map.get(type).get(key);
//        }else{
//            return null;
//        }
        
        Map<String,String> map = getKeyAndValueByType(type);
        if(map != null){
        	return map.get(key);
        }
        return null;
    }
    
    /**
     * SESSION中的数据字段：根据类型获取集合，直接转成JSON供前台使用 wangs
     */
    public List<Dictionary> getValueByType(String type){
    	return dictionaryService.findByType(type);
    }
    
    
    
    /**
     * SESSION中的数据字典： 根据类型获取所有值（数据字典数据）
     * @param type
     * 			数据字典里对应的类型
     * @return
     * @author liyp
     * Create DateTime: 2016年11月28日 上午3:53:09
     */
	public Map<String,String> getKeyAndValueByType(String type){
    	return dictionaryService.getDictionaryMap(type);
//        Map<String,Map<String,String>> map=(Map<String,Map<String,String>>) ActionContextUtils.getAttribute("sysDictionary", "session");
//        if(map==null){//如果session中数据为空则重新查询
//            map=dictionaryService.getAllMap();
//            ActionContextUtils.setAttributeToSession(SESSION_DICTIONARY, map);
//        }
//        if(map.get(type)!=null){
//            return map.get(type);
//        }else{
//            return null;
//        }
    }
    /**
     * SESSION中的工厂编码
     */
//    public String getSiteCode(){
//
//        Site site= LoginManager.currentUser().getCurrentSite();
//        if(site!=null){
//            return site.getCode();
//        }else{
//            return null;
//        }
//    }
    //////////////////////////////////////////
    ///国际化处理begin
    /////////////////////////////////////////
    /**
     * 配置的负责处理国际化的bean名称为messageSource，该名称是Spring中规定的不能改变
     */
    private final String MESSAGE_SOURCE_BEAN_NAME = "messageSource";
    /**
     * Session中保存的消息Map
     */
    public static final String MESSAGE_MAP = "MESSAGE_MAP";
    private MessageSource getMessageSource() {
        // 获取与WebApplicationContext对应的键值
        String key = WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;
        /**
         * Spring的WebApplicationContext接口
         */
        WebApplicationContext applicationContext = (WebApplicationContext) ActionContextUtils
                .getAttribute(key, "application");
        if (applicationContext == null) {
            logger.error("必须初始化WebApplicationContext对象!");
            throw new RuntimeException("必须初始化WebApplicationContext对象!");
        }
        /**
         * Spring中负责处理国际化的接口
         */
        MessageSource messageSource = (MessageSource) applicationContext
                .getBean(MESSAGE_SOURCE_BEAN_NAME);
        if (messageSource == null) {
            logger.error("在Spring配置文件中必须配置messageSource对象!");
            throw new RuntimeException("在Spring配置文件中必须配置messageSource对象!");
        }
        return messageSource;
    }
    /**
     * 把成功或失败信息保存到Session中
     */
    protected void saveMessage(String code) {
        String msg = getMessage(code);
        // 获取request对象对应的Map
        ActionContextUtils.setAtrributeToRequest(MESSAGE_MAP, msg);
    }

    /**
     * 把成功或失败信息保存到Session中
     */
    protected void saveMessage(String code, Object[] args) {
        String msg = getMessage(code, args);
        // 获取request对象对应的Map
        ActionContextUtils.setAtrributeToRequest(MESSAGE_MAP, msg);
    }
    /**
     * 把成功或失败信息保存到Session中
     */
    protected void saveMessage(String code, Object[] args, String defaultMessage) {
        String msg = getMessage(code, args, defaultMessage);
        // 获取request对象对应的Map
        ActionContextUtils.setAtrributeToRequest(MESSAGE_MAP, msg);
    }
    /**
     * 返回国际化消息，如果没有找到则返回默认消息.
     * 
     * @param code
     *            例如： 'app.title=nihao',其中，app.title为code
     * @param args
     *            参数占位数组，例如：'app.title=nihao,{0},how are you,{1}'
     *            <br/>args中的args[0]和args[1]分别替代{0},{1}。
     * @param defaultMessage
     *            找不到信息是，返回默认信息。
     * @return 返回国际化信息
     */
    protected String getMessage(String code, Object[] args,
            String defaultMessage) {
    	HttpServletRequest request = ActionContextUtils.getRequest();
        String msg = getMessageSource().getMessage(code, args, defaultMessage,
        		request.getLocale());
        return msg;
    }

    /**
     * 根据key值，返回国际化消息
     * 
     * @param code
     *            例如： 'app.title=nihao',其中，app.title为code
     * @param args
     *            参数占位数组，例如：'app.title=nihao,{0},how are you,{1}'
     *            <br/>args中的args[0]和args[1]分别替代{0},{1}。
     * @return 返回国际化信息
     */
    protected String getMessage(String code, Object[] args) {
    	HttpServletRequest request = ActionContextUtils.getRequest();
        String msg = getMessageSource()
                .getMessage(code, args, request.getLocale());
        return msg;
    }

    /**
     * 返回国际化消息，如果没有找到则返回默认消息.
     * 
     * @param code
     *            例如： 'app.title=nihao',其中，app.title为code
     * @return 返回国际化信息
     */
    protected String getMessage(String code) {
    	HttpServletRequest request = ActionContextUtils.getRequest();
        String msg = getMessageSource()
                .getMessage(code, null, request.getLocale());
        return msg;
    }

    //////////////////////////////////////////
    /////end 国际化
    //////////////////////////////////////////
    public BaseController putRootJson(String key, JsonElement value) {
        json.add(key, value);
        return this;
    }
	/**
	 * 获取客户端的IP
	 * @param request
	 * @return
	 */
	public String  getInstrumentIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
			//多次反向代理后会有多个IP值，第一个IP才是真实IP
			int index = ip.indexOf(",");
			if(index != -1){
				return ip.substring(0,index);
			}else{
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
			return ip;
		}
		return request.getRemoteAddr();
	}
	public String  getInstrumentIp() {
		HttpServletRequest request = ActionContextUtils.getRequest();
		return this.getInstrumentIp(request);
	}
	public String getUUID(){
		return UUIDGenerator.randomId();
	}
	public JsonObject getJson() {
		return json;
	}
	public void setJson(JsonObject json) {
		this.json = json;
	}
    public String getJsonStr() {
        return json.toString();
    }
	public String getOrder() {
		String order = this.getRequest().getParameter("order");
		return order;
	}
	public String getSort() {
		String sort = this.getRequest().getParameter("sort");
		return sort;
	}
	/**
	 * 出错信息保存
	 * @param event
	 */
	public void saveLog(String event){
		//记录日志信息
    	String ip = getInstrumentIp();
    	String userName = "unkown";
    	Employee emp = LoginManager.currentUser();
    	if(emp !=null){
    		userName = emp.getUserName();
    	}
    	String operateDate = DateUtils.getDate14();
    	SysLog sysLog = new SysLog(ip,event,userName,operateDate,SysLog.LOG_TYPE_ERROR,null);
    	sysLog.setId(getUUID());
    	logService.save(sysLog);
	}

    /**
     * 系统关键操作信息保存
     * @param moduleName 模块名称
     * @param event 操作内容
     */
    public void saveOperationLog(String moduleName,String event){
        //记录关键操作信息
        String ip = getInstrumentIp();
        String userName = "unkown";
        Employee emp = LoginManager.currentUser();
        if(emp !=null){
            userName = emp.getUserName();
        }
        String operateDate = DateUtils.getDate14();
        SysLog sysLog = new SysLog(ip,event,userName,operateDate,SysLog.LOG_TYPE_OPERATE,moduleName);
       // sysLog.setId(getUUID());
        logService.save(sysLog);
    }
    /**
   	 * 根据list对象的某一个属性进行分组
   	 * @param list 需要分组的集合
   	 * @param entity 集合里的对象泛型
   	 * @param field 分组根据的属性名
   	 * @return
   	 * @author 赵春晖
   	 * Create DateTime: 2017年2月27日 下午5:43:05
   	 */
   	 @SuppressWarnings({ "unchecked", "rawtypes" })
   	public Map getListByKey(Object entity,String field ,List list)throws Exception {
      	  Map map = new HashMap();
      	  for (int i = 0; list != null && i < list.size(); i++) {
      		Object obj = (Object) list.get(i);
      		//获取属性名对应的get方法
			Method m = (Method) entity.getClass().getMethod(
					"get" + getMethodName(field));
			//执行get方法获取值
			String key = (String) m.invoke(obj);
      		//ChangeMaterials obj = (ChangeMaterials) list.get(i);
      	   //String key = obj.getSupplierCode();

      	   if (map.containsKey(key)) {
      	    List lists = (List) map.get(key);
      	    lists.add(obj);
      	   } else {
      	    List lists = new ArrayList();
      	    lists.add(obj);
      	    map.put(key, lists);
      	   }
      	  }
      	  return map;
      	 }
   //把属性明的第一个字母大写
 	private static String getMethodName(String fildeName) throws Exception{
 		fildeName =	fildeName.replaceFirst(fildeName.substring(0, 1),fildeName.substring(0, 1).toUpperCase());
 		return fildeName;
 	}
}
