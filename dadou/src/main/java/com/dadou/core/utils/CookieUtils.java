package com.dadou.core.utils;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.framework.core.utils.ActionContextUtils;
import com.framework.core.utils.StringUtils;

/**
 * Cookie 工具类，主要针对Cookie进行各种处理。
 * @author gaof
 * @since 2012-08-03
 */
public final class CookieUtils {
    protected static final Logger logger = Logger.getLogger(CookieUtils.class);

    /**
     * 获取Cookie的值， 无编码格式。
     * 
     * @param cookieName
     * 			Cookie名
     * 
     * @return Cookie的值
     */
    public static String getCookieValue(String cookieName) {
        return getCookieValue(cookieName, false);
    }

    /**
     * 获取Cookie的值， 选择是否存在编码格式。
     * 
     * @param cookieName
     * 			Cookie名
     * @param isDecode
     * 			是否指定编码格式的标识。
     * <ul>说明：存在编码格式时，默认的编码格式为{@code UTF-8}。</ul>
     * 
     * @return Cookie的值
     */
    public static String getCookieValue(String cookieName, boolean isDecode) {
        HttpServletRequest request = ActionContextUtils.getRequest();
        Cookie cookies[] = request.getCookies();
        if (cookies == null || cookieName == null){
        	return null;
        }
        String retValue = null;
        try {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    if (isDecode) {
                        retValue = URLDecoder.decode(cookie.getValue(),"utf-8");
                    } else {
                        retValue = cookie.getValue();
                    }
                    break;
                }
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("Cookie Decode Error.", e);
        }
        return retValue;
    }

    /**
     * 获取Cookie的值， 指定编码格式。
     * 
     * @param cookieName
     * 			Cookie名
     * @param encodeString
     * 			编码格式
     * 
     * @return Cookie的值
     */
    public static String getCookieValue(String cookieName, String encodeString) {
        HttpServletRequest request = ActionContextUtils.getRequest();
        Cookie cookies[] = request.getCookies();
        if (cookies == null || cookieName == null){
        	return null;
        }
        String retValue = null;
        try {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    retValue = URLDecoder.decode(cookie.getValue(),encodeString);
                    break;
                }
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("Cookie Decode Error.", e);
        }
        return retValue;
    }

    /**
     * 设置Cookie的值。不设置生效时间，默认浏览器关闭即失效，无编码格式。
     * 
     * @param cookieName
     * 			Cookie名
     * @param cookieValue
     * 			Cookie的值
     */
    public static void addCookie(String cookieName, String cookieValue) {
        addCookie(cookieName, cookieValue, -1);
    }

    /**
     * 设置Cookie的值 。使其在指定时间内生效，无编码格式。
     * 
     * @param cookieName
     * 			Cookie名
     * @param cookieValue
     * 			Cookie的值
     * @param cookieMaxage
     * 			cookie生效的最大秒数
     */
    public static void addCookie(String cookieName, String cookieValue,
            int cookieMaxage) {
        addCookie(cookieName, cookieValue, cookieMaxage, false);
    }

    /**
     * 设置Cookie的值。 不设置生效时间，选择是否存在编码格式。
     * 
     * @param cookieName
     * 			Cookie名
     * @param cookieValue
     * 			Cookie的值
     * @param isEncode
     * 			是否存在编码格式的标识。
     * <ul>说明：存在编码格式时，默认的编码格式为{@code UTF-8}。</ul>
     */
    public static void addCookie(String cookieName, String cookieValue,
            boolean isEncode) {
        setCookie(cookieName, cookieValue, -1, isEncode);
    }

    /**
     * 设置Cookie的值。使其在指定时间内生效，选择是否存在编码格式。
     * 
     * @param cookieName
     * 			Cookie名
     * @param cookieValue
     * 			Cookie的值
     * @param cookieMaxage
     * 			cookie生效的最大秒数
     * @param isEncode
     * 			是否存在编码格式的标识。
     * <ul>说明：存在编码格式时，默认的编码格式为{@code UTF-8}。</ul>
     */
    public static void addCookie(String cookieName, String cookieValue,
            int cookieMaxage, boolean isEncode) {
        setCookie(cookieName, cookieValue, cookieMaxage, isEncode);
    }

    /**
     * 设置Cookie的值。使其在指定时间内生效，并指定编码格式。
     * 
     * @param cookieName
     * 			Cookie名
     * @param cookieValue
     * 			Cookie的值
     * @param cookieMaxage
     * 			cookie生效的最大秒数
     * @param encodeString
     * 			指定的编码格式
     */
    public static void addCookie(String cookieName, String cookieValue,
            int cookieMaxage, String encodeString) {
        setCookie(cookieName, cookieValue, cookieMaxage, encodeString);
    }

    /**
     * 删除Cookie。
     * 
     * @param cookieName
     * 			Cookie名
     */
    public static void removeCookie(String cookieName) {
        setCookie(cookieName, "", -1, false);
    }

    /**
     * 设置Cookie的值。使其在指定时间内生效，选择是否存在编码格式。
     * 
     * @param cookieName
     * 			Cookie名
     * @param cookieValue
     * 			Cookie的值
     * @param cookieMaxage
     * 			cookie生效的最大秒数
     * @param isEncode
     * 			是否存在编码格式的标识。
     * <ul>说明：存在编码格式时，默认的编码格式为{@code UTF-8}。</ul>
     */
    private static final void setCookie(String cookieName, String cookieValue,
            int cookieMaxAge, boolean isEncode) {
        HttpServletRequest request = ActionContextUtils.getRequest();
        HttpServletResponse response = ActionContextUtils.getResponse();
        try {
            if (cookieValue == null) {
                cookieValue = "";
            } else if (isEncode) {
                cookieValue = URLEncoder.encode(cookieValue, "utf-8");
            }
            Cookie cookie = new Cookie(cookieName, cookieValue);
            if (cookieMaxAge > 0)
                cookie.setMaxAge(cookieMaxAge);
            if (null != request)// 设置域名的cookie
               // cookie.setDomain(getDomainName(request)); FIXME IE下是bug
                
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (Exception e) {
            logger.error("Cookie Encode Error.", e);
        }
    }

    /**
     * 设置Cookie的值。使其在指定时间内生效，同时指定编码格式。
     * 
     * @param cookieName
     * 			Cookie名
     * @param cookieValue
     * 			Cookie的值
     * @param cookieMaxage
     * 			cookie生效的最大秒数
     * @param encodeString
     * 			指定的编码格式
     */
    private static final void setCookie(String cookieName, String cookieValue,
            int cookieMaxage, String encodeString) {
        HttpServletRequest request = ActionContextUtils.getRequest();
        HttpServletResponse response = ActionContextUtils.getResponse();
        try {
            if (cookieValue == null) {
                cookieValue = "";
            } else {
                cookieValue = URLEncoder.encode(cookieValue, encodeString);
            }
            Cookie cookie = new Cookie(cookieName, cookieValue);
            if (cookieMaxage > 0)
                cookie.setMaxAge(cookieMaxage);
            if (null != request)// 设置域名的cookie
              //  cookie.setDomain(getDomainName(request));FIXME IE下是bug
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (Exception e) {
            logger.error("Cookie Encode Error.", e);
        }
    }

    /**
     * 获取cookie的域名。
     * 
     * @param request
     * 			HttpServletRequest对象
     * 
     * @return cookie的域名
     */
	public static final String getDomainName(HttpServletRequest request) {
        String domainName = null;
        String serverName = request.getRequestURL().toString();
        if (StringUtils.isEmpty(serverName)) {
              domainName = "";
        } else {
            serverName = serverName.toLowerCase();//变为小写
            serverName = serverName.substring(7);//去掉http://
            final int end = serverName.indexOf("/");//获取最后一个位置
            serverName = serverName.substring(0, end);
            final String[] domains = serverName.split("\\.");
            int len = domains.length;
            if (len > 3) {
                // www.xxx.com.cn
                domainName = "." + domains[len - 3] + "." + domains[len - 2]
                        + "." + domains[len - 1];
            } else if (len <= 3 && len > 1) {
                // uisb.dong-he.cn or www.uustudy.com
                domainName = "." + domains[len - 2] + "." + domains[len - 1];
            } else {
                domainName = serverName;
            }
        }
        //去掉端口号,例如: 8080 等其他端口号
        if (domainName != null && domainName.indexOf(":") > 0) {
            String[] ary = domainName.split("\\:");
            domainName = ary[0];
        }
        return domainName;
    }
}