package com.dadou.core.cache;

import com.dadou.sys.dic.service.DictionaryService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;


/**
 * 用于加载系统中用到的所有缓存对象
 * 加载某些不常变的数据到缓存中,避免每次请求时查询数据库或其它数据源，以提高性能(准备连接，建立连接，关闭连接，减少数据读取的IO数)
 * 
 * @author gaof
 *
 */
@Component
public class CacheBeanPostProcessor  implements BeanPostProcessor {
    /**
     * Commons Logging日志实例
     */
    public static Log logger = LogFactory.getLog(CacheBeanPostProcessor.class);
   /**
    * Spring在成功完成嵌入初始化以后调用该方法。
    */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		if("im_dictionaryService".equals(beanName)){
			// 字典Service，单独处理
			DictionaryService dictionaryService =(DictionaryService)bean;
			//初始化缓存
			dictionaryService.doInitOrRefreshCache();
		}
		return bean;
	}
    /**
     * 在Spring调用任何bean的初始化钩子（例如InitializingBean.afterPropertiesSet或者init方法）之前被调用。 
     */
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

}
