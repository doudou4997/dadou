package com.framework.core.cache;

import com.framework.core.utils.StringUtils;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Cache实用类，用于在缓存中对对象进行增、删、改、查等操作。
 * 
 * @author gaofeng
 * @since 2011-09-11
 */
public final class CacheUtils {
    /** 定义日志类 */
    private static final Log logger = LogFactory.getLog(CacheUtils.class);
    /** 获取CacheManager实例 */
    private static CacheManager manager = CacheManager.getInstance();
    /**手工定义默认缓存**/

    public static final String DEFAULT_CACHE = "defaultCache";
    
    private CacheUtils() {

    }
    /**
     * 向指定的缓存区域(cacheName)内添加元素（对象）。
     * 
     * @param key
     * 			对象的键值
     * @param value
     * 			对象的数据值
     * @param cacheName
     * 			缓存区域名称
     */
    public static void add(Object key, Object value,String... cacheName) {
		if (logger.isDebugEnabled()) {
			logger.debug("CacheUtils-add(Object key, Object value)--in");
		}
		String tmpCacheName = "";
		if (cacheName != null && cacheName.length > 0) {
			tmpCacheName = cacheName[0];
		}

		if (StringUtils.isEmpty(tmpCacheName)) {
			tmpCacheName = DEFAULT_CACHE;
		}
		if (!manager.cacheExists(tmpCacheName)) {
			if (logger.isDebugEnabled()) {
				logger.debug("名字为" + tmpCacheName + "的Cache对象不存在，请创建!");
			}
			throw new RuntimeException("名字为" + tmpCacheName
					+ "的Cache对象不存在，请创建!");
		}
		// 获取Cache对象
		Cache cache = manager.getCache(tmpCacheName);
		if (cache != null) {
			// 创建Element对象
			Element element = new Element(key, value);
			cache.put(element);
			if (logger.isDebugEnabled()) {
				logger.debug("添加键为" + key + "的元素！");
				logger.debug("CacheUtils-add(Object key, Object value)--out");
			}
		}
    }

    /**
     * 从指定的缓存区域（cacheName）中获取指定元素的数据值。
     * 
     * @param key
     * 			对象的键值
     * @param cacheName
     * 			缓存区域名称
     * 
     * @return 对象数据
     */
    public static Object get(Object key, String... cacheName) {
        if (logger.isDebugEnabled()) {
            logger.debug("CacheUtils-get(Object key,String cacheName)--in");
        }
		String tmpCacheName = "";
		if (cacheName != null && cacheName.length > 0) {
			tmpCacheName = cacheName[0];
		}

		if (StringUtils.isEmpty(tmpCacheName)) {
			tmpCacheName = DEFAULT_CACHE;
		}
		if (!manager.cacheExists(tmpCacheName)) {
			if (logger.isDebugEnabled()) {
				logger.debug("名字为" + tmpCacheName + "的Cache对象不存在，请创建!");
			}
			throw new RuntimeException("名字为" + tmpCacheName
					+ "的Cache对象不存在，请创建!");
		}
        // 获取Cache对象
        Cache cache = manager.getCache(tmpCacheName);
        Element element = null;
        // 创建Element对象
        if (cache != null) {
            element = cache.get(key);
            if (logger.isDebugEnabled()) {
                logger.debug("CacheUtils-get(Object key,String cacheName)--out");
            }
        }
        if (element != null) {
            return element.getObjectValue();
        } 
        else {
            return null;
        }
    }

    /**
     * 从指定的缓存区域（cacheName）中删除指定的元素（对象）。
     * 
     * @param key
     * 			对象的键值
     * @param cacheName
     * 			缓存区域名称
     */
    public static void remove(Object key, String... cacheName) {
        if (logger.isDebugEnabled()) {
            logger.debug("CacheUtils-remove(Object key,String cacheName)--in");
        }
		String tmpCacheName = "";
		if (cacheName != null && cacheName.length > 0) {
			tmpCacheName = cacheName[0];
		}

		if (StringUtils.isEmpty(tmpCacheName)) {
			tmpCacheName = DEFAULT_CACHE;
		}
        if (!manager.cacheExists(tmpCacheName)) {
            if (logger.isDebugEnabled()) {
                logger.debug("名字为" + tmpCacheName + "的Cache对象不存在，请创建!");
            }
            throw new RuntimeException("名字为" + tmpCacheName + "的Cache对象不存在，请创建!");
        }
        // 获取Cache对象
        Cache cache = manager.getCache(tmpCacheName);
        if (cache != null) {
            // 创建Element对象
            cache.remove(key);
            if (logger.isDebugEnabled()) {
                logger.debug("删除键为" + key + "的元素！");
                logger.debug("CacheUtils-remove(Object key,String cacheName)--out");
            }
        }
    }
    /**
     * 清空指定的缓存区域中所有数据
     * @param cacheName
     */
    public static void clean(String cacheName){
        if (!manager.cacheExists(cacheName)) {
            if (logger.isDebugEnabled()) {
                logger.debug("名字为" + cacheName + "的Cache对象不存在，请创建!");
            }
            throw new RuntimeException("名字为" + cacheName + "的Cache对象不存在，请创建!");
        }
        // 获取Cache对象
        Cache cache = manager.getCache(cacheName);
        if (cache != null) {
            // 清空所有对象
        	cache.removeAll();
        }
        
        
    }

    /**
     * 移除指定的缓存区域（cacheName）。
     * 
     * @param cacheName
     * 			缓存区域名称
     */
    public static void remove(String cacheName) {
        if (logger.isDebugEnabled()) {
            logger.debug("CacheUtils-remove(String cacheName)--in");
        }
        // 移除cacheName
        manager.removeCache(cacheName);
        if (logger.isDebugEnabled()) {
            logger.debug("CacheUtils-remove(String cacheName)--out");
        }
    }
    
    

    /**
     * 移除所有的缓存区域。
     */
    public static void removeAll() {
        if (logger.isDebugEnabled()) {
            logger.debug("CacheUtils-removeAll()--in");
        }
        // 移除所有的Cache
        manager.removalAll();

        if (logger.isDebugEnabled()) {
            logger.debug("移除所有的Cache对象！");
            logger.debug("CacheUtils-removeAll()--out");
        }
    }

    /**
     * 根据名称获取具体的Cache对象。
     * 
     * @param cacheName
     * 			缓存区域名称
     * 
     * @return 缓存对象
     */
    public static Cache getCache(String cacheName) {
        Cache cache = manager.getCache(cacheName);
        return cache;
    }
    public static void main(String[] args) {
    	CacheManager manager = CacheManager.getInstance();
    	
    	String[] caches = manager.getCacheNames();
    	for(String cache : caches){
    		System.out.println(cache);
    	}
	}

}
