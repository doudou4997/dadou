package com.dadou.weixin;


import com.dadou.core.utils.SignUtil;
import com.framework.core.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 
* 类名称：WeixinController.java
* 类描述： 微信公共平台开发 
* @author FH
* 作者单位： 
* 联系方式：
* 创建时间：2014年7月10日
* @version 1.0
 */
@RequestMapping(value = "/weixin",produces="text/html;charset=UTF-8")
@Controller
public class WeixinController extends BaseController {

//	@Resource(name="textmsgService")
//	private TextmsgService textmsgService;
//	@Resource(name="commandService")
//	private CommandService commandService;
//	@Resource(name="imgmsgService")
//	private ImgmsgService imgmsgService;
	
	/**
	 * 接口验证,总入口
	 * @param out
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	 @RequestMapping(value="/index")
	 public void index(
			 PrintWriter out,
			 HttpServletRequest request,
			 HttpServletResponse response
			 ) throws Exception{
		 logger.info("微信接口");
		try{
			String signature = request.getParameter("signature");		//微信加密签名
			String timestamp = request.getParameter("timestamp");		//时间戳
			String nonce	 = request.getParameter("nonce");			//随机数
			String echostr 	 = request.getParameter("echostr");			//字符串

			if(null != signature && null != timestamp && null != nonce && null != echostr){/* 接口验证  */
				logger.info( "进入身份验证");
				// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
				if (SignUtil.checkSignature(signature, timestamp, nonce)) {
					// 原样返回
					out.print(echostr);
				}
				out.flush();
				out.close(); 
			}else{/* 消息处理  */
				logger.info("进入消息处理");
				response.reset();
				//sendMsg(request,response);
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	}

	//================================================获取access_token==============================================================
}


//================================================获取access_token==============================================================