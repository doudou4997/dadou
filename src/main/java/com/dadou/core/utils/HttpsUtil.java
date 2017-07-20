package com.dadou.core.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.net.ssl.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HttpsUtil {
	private static Log logger = LogFactory.getLog(HttpsUtil.class);
	public static final String UTF_8 = "UTF-8";

	private static class TrustAnyTrustManager implements X509TrustManager {
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}

	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

	/**
	 * post方式请求服务器(https协议)
	 * 
	 * @param url
	 *            请求地址
	 * @param content
	 *            参数
	 * @param charset
	 *            编码
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws IOException
	 */
	public static String httpsPost(String url, String content) {
		SSLContext sc;
		try {
			sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
					new java.security.SecureRandom());
			URL console = new URL(url);
			HttpsURLConnection conn = (HttpsURLConnection) console
					.openConnection();
			conn.setSSLSocketFactory(sc.getSocketFactory());
			conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
			conn.setDoOutput(true);
			conn.connect();
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			out.write(content.getBytes(UTF_8));
			// 刷新、关闭
			out.flush();
			out.close();
			InputStream is = conn.getInputStream();
			if (is != null) {
//				BufferedReader reader = new BufferedReader(
//						new InputStreamReader(is));
//				StringBuilder sb = new StringBuilder();
//
//				String line = null;
//				try {
//					while ((line = reader.readLine()) != null) {
//						sb.append(line + "\n");
//					}
//				} catch (IOException e) {
//					e.printStackTrace();
//				} finally {
//					try {
//						is.close();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//				return sb.toString();
				String result = IOUtils.toString(is, UTF_8);
				return result;
			}
			return null;
		} catch (NoSuchAlgorithmException | IOException
				| KeyManagementException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}

	}
	
	/** 
     * 得到参数列表字符串 
     * @param method   请求类型 get or  post 
     * @param paramValues 参数map对象 
     * @return  参数列表字符串 
     */  
    public static String getParams(String method,Map<String, String> paramValues)  
    {  
        String params="";  
        Set<String> key = paramValues.keySet();  
        String beginLetter="";  
        if (method.equalsIgnoreCase("get"))  
        {  
            beginLetter="?";  
        }  
  
        for (Iterator<String> it = key.iterator(); it.hasNext();) {  
            String s = (String) it.next();  
            if (params.equals(""))  
            {  
                params += beginLetter + s + "=" + paramValues.get(s);  
            }  
            else  
            {  
                params += "&" + s + "=" + paramValues.get(s);  
            }  
        }  
        return params;  
    }  

}
