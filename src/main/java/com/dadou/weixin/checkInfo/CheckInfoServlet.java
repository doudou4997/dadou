package com.dadou.weixin.checkInfo;

import com.dadou.core.utils.MessageUtil;
import com.dadou.core.utils.SignUtil;
import com.dadou.core.utils.SpringContextHolder;
import com.dadou.weixin.scan.service.WeixinService;
import com.framework.core.utils.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Map;


@SuppressWarnings("serial")
@WebServlet(name="/checkInfo")
public class CheckInfoServlet extends HttpServlet {
	private static Log logger = LogFactory.getLog(CheckInfoServlet.class);
	/**
	 * doGet
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/**
		 * 1. 将token、timestamp、nonce三个参数进行字典序排序
		 * 2. 将三个参数字符串拼接成一个字符串进行sha1加密 3.
		 * 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
		 */
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");
		PrintWriter out = response.getWriter();
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			// 原样返回
			out.print(echostr);
		}
		//out.print("hello world");
		out.close();
		out.flush();
	}

	/**
	 * doPost
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		InputStream inputStream = null;
		WeixinService weixinService  = null;
		//获取WeixinService
		try {
			weixinService = (WeixinService) SpringContextHolder.getBean("weixinService");
			inputStream = request.getInputStream();
			Map<String, String> params =  MessageUtil.parseXml(inputStream);
			//处理返回结果
			String processResult  = weixinService.doProcessRequest(params);
			// TODO
			PrintWriter out = response.getWriter();
			out.print(processResult);
			out.flush();
			out.close();
		} catch (Exception ex) {
           String event = ExceptionUtils.formatStackTrace(ex);
           logger.error(event);
           //保存错误日志
		} finally {
            //TODO
			if(inputStream!=null){
				inputStream.close();
			}
		}
	}
}
