package com.framework.core.utils;

import com.framework.core.exception.BaseAppRuntimeException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密类。<br>
 * <ul>
 *  <li>可进行 MD5 加密和 SHA 加密。</li>
 * </ul>
 * <br/>
 * 
 * <b>使用方法:</b><br/>
 * <pre>
 *  String orgStr = Encrypt.getMD5String("test");<br/>
 *  或者<br/>
 *  String orgStr = Encrypt.getSHAString("test");<br/>
 * </pre>
 * 
 * <br/>
 * @version 1.0
 * @since 1.0
 */
public class Encrypt {

    /** MD5 对象 */
    private static MessageDigest md5;

    /** SHA 对象 */
    private static MessageDigest sha;

    /**
     * 将传入的字符串进行 MD5 加密,返回32位长的字符串。<BR>
     * 
     * @param   toEncrypt       要加密的字符串
     * @return 加密后的字符串
     */
    public static String getMD5String(String toEncrypt) {
        try {
            // 判断用于 MD5 加密的 MessageDigest 对象是否为 null
            if (null == md5) {
                // 初始化
                md5 = MessageDigest.getInstance("MD5");
            }
        } catch (NoSuchAlgorithmException e) {
            // 打印错误
             throw new BaseAppRuntimeException(e);
        }

        // 将要传入的字符串进行 SHA 加密，并返回一个 byte[]
        md5.update(toEncrypt.getBytes());
        // 将加密后的 byte[] 数组转换为字符串返回
        return toHexString(md5.digest());
    }

    /**
     * 将传入的字符串进行 SHA 加密,返回40位长的字符串。<BR>
     * 
     * @param   toEncrypt       要加密的字符串
     * @return 加密后的字符串
     */
    public static String getSHAString(String toEncrypt) {

        try {
            // 判断用于 SHA 加密的 MessageDigest 对象是否为 null
            if (null == sha) {
                // 初始化
                sha = MessageDigest.getInstance("SHA");
            }
        } catch (NoSuchAlgorithmException e) {
            // 打印错误
            throw new BaseAppRuntimeException(e);
 
        }

        // 将要传入的字符串进行 SHA 加密，并返回一个 byte[]
        sha.update(toEncrypt.getBytes());
        // 将加密后的 byte[] 数组转换为字符串返回
        return toHexString(sha.digest());
    }

    /**
     * 将 byte[] 数组转换为大写的十六进制字符串,长度是 byte 数组的2倍。<BR>
     * 
     * @param    byteArray      要转换的 byte 数组。
     * @return  转换后的字符串。
     */
    private static String toHexString(byte[] byteArray) {

        // 声明一个 StringBuffer 对象
        StringBuffer hexStr = new StringBuffer();

        // 循环遍历 byte[] 数组中的每一个元素，并转换为十六进制
        // 数字字符串，添加到 StringBuffer 中。
        for (int i = 0; i < byteArray.length; i++) {
            hexStr.append(byteToHex(byteArray[i]));
        }
        // 将 StringBuffer 转换为 String 并返回
        return hexStr.toString();
    }

    /**
     * 将单个 byte 的高4位和低4位分别转换成大写十六进制数字字符,
     * 然后组合成一个字符串返回。<BR>
     * 
     * @param   ibyte       要转换的 byte
     * @return 转换后的 String
     */
    private static String byteToHex(byte ibyte) {

        // 参照
        char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F' };

        // 将 byte 转换为与其对应的 hexChars 中的字符
        char[] hexChar = new char[2];
        hexChar[0] = hexChars[(ibyte >>> 4) & 0X0F];
        hexChar[1] = hexChars[ibyte & 0X0F];

        // 将 hexChar 数组转为一个 String 对象
        String hexStr = new String(hexChar);
        // 返回结果
        return hexStr;
    }
}
