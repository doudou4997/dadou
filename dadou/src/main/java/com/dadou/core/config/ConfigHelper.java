package com.dadou.core.config;

import com.framework.core.utils.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigHelper {
    /**
     * 逻辑路径、物理路径、临时路径
     */
    public static final String RES_URL = "resourse_url";
    public static final String RES_ABSOLUTE_PATH = "resource_pic_path";
    public static final String TMP_PATH = "tmp_path";
    /**
     * ERP接口地址
     */
    public static final String INTERFACE_ERP_IP = "INTERFACE_ERP_IP";
    private static Log logger = LogFactory.getLog(ConfigHelper.class);
    /**
     * 负责装载所有地址
     */
    private static final Properties URL_PROPERTIES = new Properties();
    static {
        // 加载属性集
        loadProperties();
    }

    /**
     * 获取String值.
     * 
     * @param propertyName
     *            属性文件的key
     */
    public static String getValue(String propertyName) {
        if (URL_PROPERTIES.isEmpty()) {
            // 如果为空,重新加载
            loadProperties();
        }
        String value = URL_PROPERTIES.getProperty(propertyName);
        if (value == null) {
            return null;
        }
        value = value.trim();
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return value;
    }
    public static String getValue(String propertyName,String defaultValue){
        if (URL_PROPERTIES.isEmpty()) {
            // 如果为空,重新加载
            loadProperties();
        }
        String value = URL_PROPERTIES.getProperty(propertyName);
        if (value == null) {
            return defaultValue;
        }
        value = value.trim();
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }
        return value;
    }
    public static int getIntValue(String propertyName){
        return NumberUtils.toInt(getValue(propertyName),-1);
    }
    public static int getIntValue(String propertyName,int defalutValue){
        return NumberUtils.toInt(getValue(propertyName),defalutValue);
    }
    private static void loadProperties() {
        // 加载属性文件

        try {
            InputStream stream = ConfigHelper
                    .getResourceAsStream("config.properties");
            try {
                URL_PROPERTIES.load(stream);
            } catch (Exception e) {
                logger.error("problem loading properties from url.properties");
            } finally {
                try {
                    stream.close();
                } catch (IOException ioe) {
                    logger.error("could not close stream on url.properties",
                            ioe);
                }
            }
        } catch (Exception he) {
            logger.info("url.properties not found");
        }

    }

    /**
     * 获取属性文件对应的流对象
     * 
     * @param resource
     * @return
     */
    private static InputStream getResourceAsStream(String resource) {
        String stripped = resource.startsWith("/") ? resource.substring(1)
                : resource;

        InputStream stream = null;
        ClassLoader classLoader = Thread.currentThread()
                .getContextClassLoader();
        if (classLoader != null) {
            stream = classLoader.getResourceAsStream(stripped);
        }
        if (stream == null) {
            stream = ConfigHelper.class.getResourceAsStream(resource);
        }
        if (stream == null) {
            stream = ConfigHelper.class.getClassLoader().getResourceAsStream(
                    stripped);
        }
        if (stream == null) {
            throw new RuntimeException(resource + " not found");
        }
        return stream;
    }
    /**
     * 上机题上传逻辑路径
     */
    public static String getShangjiUrl(){
        return getValue("shangji_url");
    }
    /**
     * 获取上机题的物理路径
     */
    public static String getShangjiPath(){
        return ConfigHelper.getValue(RES_ABSOLUTE_PATH)+"/"+getShangjiUrl();
    }
    /**
     * 获取Web路径
     */
    public static String getWebUrl(){
        return getValue(RES_URL);
    }
    /**
     * 获取资源物理路径
     */
    public static String getAbsoluteUrl(){
        return getValue(RES_ABSOLUTE_PATH);
    }
    /**
     * 获取临时路径
     */
    public static String getTmpPath(){
        return getValue(TMP_PATH);
    }
    /**
     * 获取ERP_IP
     */
    public static String getInterfaceErpIp(){
    	return getValue(INTERFACE_ERP_IP);
    }
}
