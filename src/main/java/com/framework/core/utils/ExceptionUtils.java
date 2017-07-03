package com.framework.core.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 异常处理类。
 */
public final class ExceptionUtils {
    /**
     * 把异常栈的信息转换成字符串信息。
     * 
     * @param t
     * 			异常类对象
     * 
     * @return 由异常栈的信息转换成的字符串
     */
    public static String formatStackTrace(Throwable t) {
        // 存储异常信息
        StringWriter sw = new StringWriter();
        try {
            PrintWriter p = new PrintWriter(sw);
            t.printStackTrace(p);
        } catch (Exception e) {
            // do nothing
        }
        return sw.toString();
    }

    /**
     * 主要处理外键异常。当删除主表时，如果子表中含有记录，则提示通过直接抛出异常效果更好。<br>
     * 例如：Teacher表和Student表，设置外键为：FK_teacher_student。<br>
     * <b>注：</b>外键的设置格式。 最后提示给用户的信息为：“不能删除teacher表，以为其子表student中还有记录”。
     * 
     * @param t
     * 			异常类对象
     * 
     * @return 两个表的表名
     */
    public static String[] foreignKeyException(Throwable t) {
        // 异常字符串
        String sw = formatStackTrace(t);
        // 根据正则表达式得到父表和子表
        String[] result = null;
        Pattern pattern = Pattern.compile("FK_[a-zA-Z]*_[a-zA-Z]*",
                Pattern.CASE_INSENSITIVE);
        Matcher m = pattern.matcher(sw);
        String message = null;
        if (m.find()) {
            int i = m.groupCount();
            if (i == 0) {
                message = m.group(0);
            }
            if (i > 0) {
                message = m.group(0);
            }
        }
        if (message != null) {
            String[] results = message.split("_");
            result = new String[2];
            result[0] = results[1].toLowerCase();
            result[1] = results[2].toLowerCase();
        }
        return result;
    }

    /**
     * 打印日志函数。<br/>
     * <ul>
     * <li>打印debug级的日志。</li>
     * </ul>
     * 
     * @param e
     * 			异常类对象
     * 
     * @return 表示异常轨迹的字符串
     */
    public static final String printDebug(Exception e) {
        // 得到异常轨迹并把轨迹放到一个对象中。
        StackTraceElement[] stt = e.getStackTrace();
        // 定义盛放轨迹的StringBuffer。
        StringBuffer infors = new StringBuffer("\n\t" + e.getClass().toString());
        infors.append("\n\t" + e.getMessage() + "\n\t");
        // 用于把栈轨迹串起来，这样只打印一次，提高性能。
        for (int i = 0; i < stt.length; i++) {
            infors.append(stt[i]);
            infors.append("\n\t");
        }
        return infors.toString();
    }

    /**
     * 打印日志函数。<br/>
     * <ul>
     * <li>打印error级日志，try...catch中的异常为错误异常。</li>
     * </ul>
     * 
     * @param e
     * 			异常类对象
     * 
     * @return 表示异常轨迹的字符串
     */
    public static final String printError(Exception e) {
        // 得到异常轨迹并把轨迹放到一个对象中。
        StackTraceElement[] stt = e.getStackTrace();
        // 定义盛放轨迹的StringBuffer。
        StringBuffer infors = new StringBuffer("\n\t" + e.getClass().toString());
        infors.append("\n\t" + e.getMessage() + "\n\t");
        // 用于把栈轨迹串起来，这样只打印一次，提高性能．
        for (int i = 0; i < stt.length; i++) {
            infors.append(stt[i]);
            infors.append("\n\t");
        }
        // 打印异常轨迹。
        return infors.toString();
    }
}