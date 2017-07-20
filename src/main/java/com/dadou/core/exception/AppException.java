/**
 * 
 */
package com.dadou.core.exception;

/**
 * 业务异常父类
 */
@SuppressWarnings("serial")
public class AppException extends RuntimeException {
    /**
     * 是否记录该异常,true为记录
     */
    private boolean isLog = true;
    public AppException() {
        super("出错啦！我们对此表示歉意，请告知我们此次操作的出错过程，我们会尽快改进。谢谢！");
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(Throwable cause) {
        super(cause);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public boolean isLog() {
        return isLog;
    }

    public void setLog(boolean isLog) {
        this.isLog = isLog;
    }
}
