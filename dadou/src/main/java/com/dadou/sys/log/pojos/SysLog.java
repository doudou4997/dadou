package com.dadou.sys.log.pojos;

/**
 * 日志实体类
 * 
 * @author gaofeng
 */
public class SysLog {
    /**
     * 日志主键
     */
    private String id;
    /**
     * 操作时间
     */
    private String operateDate;
    /**
     * IP来源
     */
    private String ip;
    /**
     * 用户来源
     */
    private String userName;
    /**
     * 事件内容
     */
    private String event;
    //日志类型
    private String type;
    public static final String LOG_TYPE_ERROR="0";//错误日志
    public static final String LOG_TYPE_OPERATE="1";//操作日志
    //模块名称
    private String moduleName;
    public static final  String LOG_MODULE_SYS="系统管理";
    public static final  String LOG_MODULE_IM="基础数据";
    public static final  String LOG_MODULE_PM="生产管理";
    public static final  String LOG_MODULE_PE="生产执行";
    public static final  String LOG_MODULE_MM="物料管理";
    public static final  String LOG_MODULE_EM="设备管理";
    public static final  String LOG_MODULE_QC="质量管理";
    public static final  String LOG_MODULE_PR="工艺管理";

    public SysLog() {

    }
    public SysLog(String ip, String event, String userName,String operateDate,String type,String moduleName) {
        this.ip = ip;
        this.event = event;
        this.userName = userName;
        this.operateDate = operateDate;
        this.type=type;
        this.moduleName=moduleName;
    }
    /**
     * 老方法，给以前的java调用
     * @param ip
     * @param event
     * @param userName
     * @param operateDate
     * @param type
     * @param
     * @author  liyp
     */
    public SysLog(String ip, String event, String userName,String operateDate,String type) {
        this.ip = ip;
        this.event = event;
        this.userName = userName;
        this.operateDate = operateDate;
        this.type=type;
        this.moduleName=null;
    }
    // ////////////////////////////////////////
    // /getter/setter方法
    // ///////////////////////////////////////

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(String operateDate) {
        this.operateDate = operateDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
         this.ip = ip;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
