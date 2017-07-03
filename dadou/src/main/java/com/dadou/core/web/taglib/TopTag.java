package com.dadou.core.web.taglib;

import com.dadou.sys.emp.pojos.Employee;
import com.dadou.sys.login.LoginManager;
import com.dadou.sys.sys.pojos.Sys;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.util.List;


/**
 * 动态显示子系统标签。 <BR>
 * @author gaof
 * @version 1.0
 * @since 2016-11-20
 */
public class TopTag extends BodyTagSupport {
	StringBuffer output;
	/**
	 * 覆盖父类 doStartTag 方法。 <br>
	 * @return 返回 EVAL_BODY_BUFFERED 表示计算标签体
	 * @throws JspException
	 *             参数无效时抛出异常
	 */
	public int doStartTag() throws JspException {
		// 实例化 output ，以保存标签体内容。
		output = new StringBuffer();
		Employee emp  = LoginManager.currentUser();
		if(emp == null) return EVAL_BODY_BUFFERED;
		// 子系统
		List<Sys> sysList = emp.getSysList();
		//首选获取第一个子系统
		if(sysList!=null && sysList.size()>0){
			//获取第一个子系统
			Sys sys = sysList.get(0);
		    output.append("<li class='menu_on'>");
		    output.append("  <a href='javascript:void(0)' class='white' sys_id='"+sys.getId()+"'><span style='font-size:16px '>"+sys.getName()+"</span></a>");
		    output.append("</li>");
		    if(sysList.size()>1){
		    	for(int i = 1;i<sysList.size();i++){
		    		Sys otherSys = sysList.get(i);
		    		//处理其他类型的
				    output.append("<li class='menu_off'>");
				    output.append("  <a href='javascript:void(0)' sys_id='"+otherSys.getId()+"'><span style='font-size:16px '>"+otherSys.getName()+"</span></a>");
				    output.append("</li>");		
		    	}
		    }
		}
		// 输出全部内容
		try {
			pageContext.getOut().println(output.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	    // 计算标签体
		return EVAL_BODY_BUFFERED;
	}
	
	private static final long serialVersionUID = 1L;

}