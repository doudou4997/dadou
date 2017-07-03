package com.framework.core.utils;

import java.net.InetAddress;

/**
 * 主键生成器，采用Hibernate的UUID的方式。
 * 用于生成UUID
 */
public class UUIDGenerator {
    private static final int IP;
    static {
        int ipadd;
        try {
            ipadd = IptoInt( InetAddress.getLocalHost().getAddress() );
        }
        catch (Exception e) {
            ipadd = 0;
        }
        IP = ipadd;
    }
    private static short counter = (short) 0;
    private static final int JVM = (int) ( System.currentTimeMillis() >>> 8 );

    public UUIDGenerator() {
    	
    }

    /**
     * 将IP地址转换为int型数据。
     * 
     * @return 由IP地址转换成的int型数据
     * 
     */
    public static int IptoInt( byte[] bytes ) {
        int result = 0;
        for (int i=0; i<4; i++) {
            result = ( result << 8 ) - Byte.MIN_VALUE + (int) bytes[i];
        }
        return result;
    }
    /**
     * Unique across JVMs on this machine (unless they load this class
     * in the same quater second - very unlikely)
     * 
     * @return JVM
     * 
     */
    protected static int getJVM() {
        return JVM;
    }

    /**
     * Unique in a millisecond for this JVM instance (unless there
     * are > Short.MAX_VALUE instances created in a millisecond)
     * 
     * @return counter
     * 
     */
    protected  static short getCount() {
        synchronized(UUIDGenerator.class) {
            if (counter<0){
            	counter=0;
            }
            return counter++;
        }
    }

    /**
     * Unique in a local network
     * 
     * @return IP
     * 
     */
    protected static int getIP() {
        return IP;
    }

    /**
     * Unique down to millisecond
     */
    protected static short getHiTime() {
        return (short) ( System.currentTimeMillis() >>> 32 );
    }
    
    protected static int getLoTime() {
        return (int) System.currentTimeMillis();
    }
    
    protected static String format(int intval) {
        String formatted = Integer.toHexString(intval);
        StringBuffer buf = new StringBuffer("00000000");
        buf.replace( 8-formatted.length(), 8, formatted );
        return buf.toString();
    }

    protected static String format(short shortval) {
        String formatted = Integer.toHexString(shortval);
        StringBuffer buf = new StringBuffer("0000");
        buf.replace( 4-formatted.length(), 4, formatted );
        return buf.toString();
    }

    /**
     * 生成ID序列。
     * 
     * @return 生成的ID序列
     */ 
    public static String  randomId() {   
        return new StringBuffer(36)   
         .append( format( getIP() ) )
         .append( format( getJVM() ) )
         .append( format( getHiTime() ) )
         .append( format( getLoTime() ) )
         .append( format( getCount() ) )   
         .toString();   
    }
}
