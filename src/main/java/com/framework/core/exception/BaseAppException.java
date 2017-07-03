package com.framework.core.exception;
/**
 * checked Exception的子类，系统中所有checked类都要继承该类。
 * 
 * @author gaofeng
 */
public class BaseAppException extends Exception {

    private static final long serialVersionUID = 8343048459443313229L;
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

    ///////////////////////////////////////////////
    //   构造方法
    ///////////////////////////////////////////////
    public BaseAppException() {
        super();
    }

    /**
     * @param message
     * 			发生异常错误的信息
     * @param cause
     * 			发生异常的原因
     */
    public BaseAppException(String message, Throwable cause) {
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
    public BaseAppException(String errorCode, String message, Throwable cause) {
        this(message, cause);
        setErrorCode(errorCode);
    }

    /**
     * @param message
     * 			发生异常错误的信息
     */
    public BaseAppException(String message) {
        super(message);
    }

    /**
     * @param cause
     * 			发生异常的原因
     */
    public BaseAppException(Throwable cause) {
        super(cause);
    }

    @Override
    /**
     * 返回程序异常的简短描述。
     *
     * @return 描述程序异常的字符串
     */
    public String toString() {
        String errorCode = getErrorCode();
        String s = (errorCode != null) ? errorCode + "--" + getClass().getName() : getClass().getName();
        String message = getLocalizedMessage();
        return (message != null) ? (s + ": " + message) : s;
    }
}
