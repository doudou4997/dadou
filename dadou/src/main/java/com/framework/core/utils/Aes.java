package com.framework.core.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * AES.
 * 
 * @author uu
 * @version 2010-4-20
 */
public class Aes {

    private static final String ALGORITHM      = "AES";
    private static final String TRANSFORMATION = "AES";
    private static final int    AES_KEY_SIZE   = 128;

    /**
     * 加密
     * 
     * @param source
     *            要加密的明文
     * @param key
     *            密钥
     * @return 加密后的密文
     * @throws Exception
     */
    public static byte[] encrypt(byte[] source, byte[] key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(source);
    }

    /**
     * 加密
     * 
     * @param source
     *            要加密的明文
     * @param key
     *            密钥
     * @return 加密后的密文
     * @throws Exception
     */
    public static byte[] encrypt(byte[] source, String key) throws Exception {
        SecretKey secretKey = generateKey(key);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(source);
    }

    /**
     * 加密
     * 
     * @param source
     *            要加密的明文
     * @param key
     *            密钥
     * @return 加密后的密文
     * @throws Exception
     */
    public static String encrypt(String source, String key) throws Exception {
        byte[] resultByte = encrypt(source.getBytes(), key);
        return Utils.byteArray2String(resultByte);
    }

    /**
     * 解密
     * 
     * @param source
     *            加密后的密文
     * @param key
     *            密钥
     * @return 解密后的明文
     * @throws Exception
     */
    public static byte[] decrypt(byte[] source, byte[] key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(source);
    }

    /**
     * 解密
     * 
     * @param source
     *            加密后的密文
     * @param key
     *            密钥
     * @return 解密后的明文
     * @throws Exception
     */
    public static byte[] decrypt(byte[] source, String key) throws Exception {
        SecretKey secretKey = generateKey(key);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(source);
    }

    /**
     * 解密
     * 
     * @param source
     *            加密后的密文
     * @param key
     *            密钥
     * @return 解密后的明文
     * @throws Exception
     */
    public static String decrypt(String source, String key) throws Exception {
        byte[] resultByte = decrypt(Utils.string2ByteArray(source), key);
        return new String(resultByte);
    }
    /*
     * 生成密钥
     * 
     * @param keySeed
     *            种子.如果为空则生成随机密钥.
     * @return 密钥
     * @throws NoSuchAlgorithmException
     */
    private static SecretKey generateKey(String keySeed) throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed(keySeed.getBytes());
        KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);
        kg.init(AES_KEY_SIZE, sr);
        return kg.generateKey();
    }

    static class Utils {
        static String byteArray2String(byte[] bytes) {
            StringBuilder result = new StringBuilder();
            String s = "";
            for (byte b : bytes) {
                s = Integer.toHexString(b & 0XFF);
                if (s.length() == 1)
                    result.append("0").append(s);
                else
                    result.append(s);
            }
            return result.toString();
        }

        static byte[] string2ByteArray(String source) {
            byte[] bytes = source.getBytes();
            byte[] b2 = new byte[bytes.length / 2];
            String s = "";
            for (int n = 0; n < bytes.length; n += 2) {
                s = new String(bytes, n, 2);
                b2[n / 2] = (byte) Integer.parseInt(s, 16);
            }
            return b2;
        }
    }

    public static void main(String[] args) throws Exception {
        // 原文
        String source = "明月出天山，苍茫云海间。\n长风几万里，吹度玉门关。\n汉下白登道，胡窥青海湾。\n由来征战地，不见有人还。\n戍客望边邑，思归多苦颜。\n高楼当此夜，叹息未应闲。";
        // 密钥
        String key = "秦时明月汉时关，万里长征人未还，但使龙城飞将在，不教胡马度阴山。";

        String cipher = Aes.encrypt(source, key); // 加密
        String source2 = Aes.decrypt(cipher, key); // 解密

        //
        System.out.println("----------------AES----------------");
        System.out.println("原文:--------------------------------");
        System.out.println(source);
        System.out.println("密钥:--------------------------------");
        System.out.println(key);
        System.out.println("密文:--------------------------------");
        System.out.println(cipher);
        System.out.println("解密后的原文:--------------------------------");
        System.out.println(source2);
    }

}