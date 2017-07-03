package com.framework.core.exception;
/**
 * 系统异常基类，所有系统unchecked Exception类都要集成该类。
 * 
 * @author gaof
 */
public class BaseAppRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -6021077900819863433L;
    /**
     * 异常错误码
     */
    private String errorCode;

    /**
     * 获取异常错误码。
     * 
     * @return 异常错误码
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * 设置异常错误码。
     * 
     * @param errorCode
     * 			异常错误码
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    ////////////////////////////////////////////////
    // 各种构造方法的处理
    ////////////////////////////////////////////////
    public BaseAppRuntimeException() {
        super();
    }

    /**
     * @param message
     * 			发生异常错误的信息
     * @param cause
     * 			发生异常的原因
     */
    public BaseAppRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param errorCode
     * 			异常错误码
     * @param message
     * 			发生异常错误的信息
     * @param cause
     * 			发生异常的原因
     */
    public BaseAppRuntimeException(String errorCode, String message, Throwable cause) {
        this(message, cause);
        setErrorCode(errorCode);
    }

    /**
     * @param message
     * 			发生异常错误的信息
     */
    public BaseAppRuntimeException(String message) {
        super(message);
    }

    /**
     * @param errorCode
     * 			异常错误码
     * @param message
     * 			发生异常错误的信息
     */
    public BaseAppRuntimeException(String errorCode,String message){
        super(message);
        setErrorCode(errorCode);
    }

    /**
     * @param cause
     * 			发生异常的原因
     */
    public BaseAppRuntimeException(Throwable cause) {
        super(cause);
    }
    
    @Override
    /**
     * 返回程序运行异常的简短描述。
     *
     * @return 描述运行异常的字符串
     */
    public String toString() {
        String errorCode = getErrorCode();
        String s = (errorCode != null) ? errorCode + "--" + getClass().getName() : getClass().getName();
        String message = getLocalizedMessage();
        return (message != null) ? (s + ": " + message) : s;
    }
}
