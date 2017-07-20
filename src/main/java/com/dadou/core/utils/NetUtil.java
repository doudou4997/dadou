package com.dadou.core.utils;

import com.dadou.core.exception.AppException;
import com.framework.core.utils.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

;

/**
 * API客户端接口：用于访问网络数据
 */
public class NetUtil {
	private static Log logger = LogFactory.getLog(NetUtil.class);
	public static final String UTF_8 = "UTF-8";
	public static final String DESC = "descend";
	public static final String ASC = "ascend";
	
	private final static int TIMEOUT_CONNECTION = 20000;
	private final static int TIMEOUT_SOCKET = 20000;
	private final static int RETRY_TIME = 5;
	
	private static HttpClient getHttpClient() {
		
        HttpClient httpClient = HttpClients.createDefault();
        // 请求超时
//		// 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
//		httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
//        // 设置 默认的超时重试处理策略
//		httpClient.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
//		// 设置 连接超时时间
//		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIMEOUT_CONNECTION);
//		// 设置 读数据超时时间 
//		httpClient.getHttpConnectionManager().getParams().setSoTimeout(TIMEOUT_SOCKET);
//		// 设置 字符集
//		httpClient.getParams().setContentCharset(UTF_8);
		return httpClient;
	}	
	
	private static HttpPost getHttpPost(String url, String cookie, String userAgent) {
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Connection","Keep-Alive");
		httpPost.addHeader("Cookie", cookie);
		httpPost.addHeader("User-Agent", userAgent);
		return httpPost;
	}
	private static HttpGet getHttpGet(String url, String cookie, String userAgent) {
		HttpGet httpGet = new HttpGet(url);
		// 设置 请求超时时间
		httpGet.addHeader("Connection","Keep-Alive");
		httpGet.addHeader("Cookie", cookie);
		httpGet.addHeader("User-Agent", userAgent);
		return httpGet;
	}
	/**
	 * get请求URL
	 * @param url
	 * @throws
	 */
	public static String executeGet(String url) throws AppException {	
		HttpClient httpClient = null;
		HttpGet httpGet = null;
		String responseBody = "";
		int time = 0;
		do{
			try 
			{
				httpClient = getHttpClient();
				httpGet = getHttpGet(url,null,null);			
				HttpResponse response = httpClient.execute(httpGet);
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode != HttpStatus.SC_OK) {
					throw new AppException("状态值："+statusCode);
				}
	        	responseBody= EntityUtils.toString(response.getEntity(), "UTF-8");
	        	break;
			} catch (Exception e) {
				time++;
				String  event = ExceptionUtils.formatStackTrace(e);
				logger.error(event);
				if(time < RETRY_TIME) {
					try {
						Thread.sleep(4000);
					} catch (InterruptedException e1) {} 
					continue;
				}
				
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				throw new AppException(e);
			} finally {
				// 释放连接
				//TODO
			}
		}while(time < RETRY_TIME);
		
      return responseBody;
	}
	
	/**
	 * 公用post方法
	 * @param url
	 * @param
	 * @param
	 * @throws AppException
	 */
	public static String executePost(String url, String json) throws AppException {
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String responseBody = "";
		int time = 0;
		do{
			try 
			{
				httpClient = getHttpClient();
				httpPost = getHttpPost(url,null,null);
				StringEntity stringEntity = new StringEntity(json,"UTF-8");
				httpPost.setEntity(stringEntity);
				HttpResponse response = httpClient.execute(httpPost);
				int statusCode = response.getStatusLine().getStatusCode();
		        if(statusCode != HttpStatus.SC_OK) 
		        {
		        	throw new AppException("状态值："+statusCode);
		        }
		        responseBody= EntityUtils.toString(response.getEntity(), "UTF-8");
		     	break;	     	
			} catch (Exception e) {
				time++;
				if(time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {} 
					continue;
				}
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				throw new AppException(e);
			} finally {
				// 释放连接
				//httpPost.releaseConnection();
				//httpClient = null;
			}
		}while(time < RETRY_TIME);
        return responseBody;      
  	}
}
