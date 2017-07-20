package com.dadou.weixin.qrcode.service;

import com.dadou.core.config.ConfigHelper;
import com.dadou.core.utils.NetUtil;
import com.dadou.weixin.qrcode.pojos.AccessToken;
import com.framework.core.utils.ExceptionUtils;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 负责处理AccessToken Session过期的问题
 */
public class AccessTokenSession{
    private static Logger logger = Logger.getLogger(AccessTokenSession.class);
    /**
     * 对象
     */
    private static final Object LOCK_OBJECT = new Object();
    /**
     * 可以维护多个微信公众号和服务器的绑定
     */
    private static ConcurrentHashMap<String, AccessToken> ACCESS_TOKEN_CACHE = new ConcurrentHashMap<String, AccessToken>();
    /**
     * 单例模式
     */
    private static AccessTokenSession accessTokenSession;
    /**
     * 超时限制 30分钟
     */
    private static final int SESSION_TIMEOUT = 30*60*1000;
    
    private static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    /**
     * 写锁
     */
    private static Lock writeLock = readWriteLock.writeLock();

    public AccessTokenSession() {}
   
    /**
     * 返回单例模式的对象
     * 
     * @return
     * @throws NamingException
     */
    public static AccessTokenSession getInstance() {
        if (accessTokenSession == null) {
            synchronized (LOCK_OBJECT) {
                if (accessTokenSession == null) {
                	accessTokenSession = new AccessTokenSession();
                }
            }
        }
        return accessTokenSession;
    }

    /**
     * 缓存中增加一个MAC信息 key:mac value:DeviceModel
     */
    public static void put(String key, AccessToken accessToken) {
        try {
            writeLock.lock();
            try {
            	//多于10个公众号
                if (ACCESS_TOKEN_CACHE.size() >= 10) {
                    return;
                }
                // 放到登录缓存表中
                AccessTokenSession.ACCESS_TOKEN_CACHE.put(key, accessToken);
            } finally {
                // 释放锁
                writeLock.unlock();
            }

        } catch (Exception e) {
            logger.error("缓存中增加一个MAC信息异常：" + ExceptionUtils.formatStackTrace(e));
        }
    }
    /**
     * 根据key值获取AccessToken对象
     * @param
     * @return
     */
    public static AccessToken getAccessToken(){
    	String appId = ConfigHelper.getValue("appId");
		String secret = ConfigHelper.getValue("secret");
    	AccessToken accessToken =  AccessTokenSession.ACCESS_TOKEN_CACHE.get(appId);
    	if(accessToken == null){
			String accessTokenUrl  = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appId+"&secret="+secret;
			String json = NetUtil.executeGet(accessTokenUrl);
			accessToken = new AccessToken();
			accessToken.setJson(json);
			accessToken.setSave_time(new Date());
			AccessTokenSession.put(appId,accessToken);
	    }
    	return accessToken;
    }
    /**
     * 
     */
    public void doCleanAccessSession(){
        Set<String> keySet = ACCESS_TOKEN_CACHE.keySet();
        Iterator<String> it = keySet.iterator();
        while (it.hasNext()) {
            String key = it.next();
            AccessToken accessToken = ACCESS_TOKEN_CACHE.get(key);
            Date lastAccsessTime = accessToken.getSave_time();
            // 如果最近访问时间已经超过了30分钟
            if ((new Date().getTime() - lastAccsessTime.getTime()) > SESSION_TIMEOUT) {
                it.remove();
            }
        }
    }
}
