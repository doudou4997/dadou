package com.framework.core.utils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 负责对URL的转义处理
 *
 */
public final class UrlUtils {

	public static final String ALLOWED_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.!~*'()";

	/**
	 * 进行编码
	 * 
	 * @param input
	 * @return
	 */
	public final static String encodeURI(String input) {
		if (input == null || "".equals(input)) {
			return input;
		}
		int l = input.length();
		StringBuilder o = new StringBuilder(l * 3);
		try {
			for (int i = 0; i < l; i++) {
				String e = input.substring(i, i + 1);
				if (ALLOWED_CHARS.indexOf(e) == -1) {
					byte[] b = e.getBytes("utf-8");
					o.append(getHex(b));
					continue;
				}
				o.append(e);
			}
			return o.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return input;
	}

	private final static String getHex(byte buf[]) {
		StringBuilder o = new StringBuilder(buf.length * 3);
		for (int i = 0; i < buf.length; i++) {
			int n = (int) buf[i] & 0xff;
			o.append("%");
			if (n < 0x10) {
				o.append("0");
			}
			o.append(Long.toString(n, 16).toUpperCase());
		}
		return o.toString();
	}

	/**
	 * 进行解码
	 */
	public final static String decodeURI(String result) {
		String encodedURI = result;
//		try {
//			encodedURI = java.net.URLDecoder.decode(result, "utf-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
		char actualChar;
		StringBuffer buffer = new StringBuffer();
		int bytePattern, sumb = 0;
		for (int i = 0, more = -1; i < encodedURI.length(); i++) {
			actualChar = encodedURI.charAt(i);
			switch (actualChar) {
			case '%': {
				actualChar = encodedURI.charAt(++i);
				int hb = (Character.isDigit(actualChar) ? actualChar - '0'
						: 10 + Character.toLowerCase(actualChar) - 'a') & 0xF;
				actualChar = encodedURI.charAt(++i);
				int lb = (Character.isDigit(actualChar) ? actualChar - '0'
						: 10 + Character.toLowerCase(actualChar) - 'a') & 0xF;
				bytePattern = (hb << 4) | lb;
				break;
			}
			case '+': {
				bytePattern = ' ';
				break;
			}
			default: {
				bytePattern = actualChar;
			}
			}
			if ((bytePattern & 0xc0) == 0x80) { // 10xxxxxx
				sumb = (sumb << 6) | (bytePattern & 0x3f);
				if (--more == 0)
					buffer.append((char) sumb);
			} else if ((bytePattern & 0x80) == 0x00) { // 0xxxxxxx
				buffer.append((char) bytePattern);
			} else if ((bytePattern & 0xe0) == 0xc0) { // 110xxxxx
				sumb = bytePattern & 0x1f;
				more = 1;
			} else if ((bytePattern & 0xf0) == 0xe0) { // 1110xxxx
				sumb = bytePattern & 0x0f;
				more = 2;
			} else if ((bytePattern & 0xf8) == 0xf0) { // 11110xxx
				sumb = bytePattern & 0x07;
				more = 3;
			} else if ((bytePattern & 0xfc) == 0xf8) { // 111110xx
				sumb = bytePattern & 0x03;
				more = 4;
			} else { // 1111110x
				sumb = bytePattern & 0x01;
				more = 5;
			}
		}
		return buffer.toString();
	}
	
	/**
	 * 根据传入的前端的查询表单进行分析并返回Map
	 * @param formData
	 * @return
	 */
	public static  Map<String,Object> getQueryMap(String formData){
		Map<String, Object> queryMap = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(formData)){
			String[] values = formData.split("\\&");
	         for(int i = 0;i<values.length;i++){
	        	 String value = values[i];
	        	 String[] vs = value.split("\\=");
	        	 if(vs.length == 2){
	        		 queryMap.put(vs[0], vs[1]);
	        	 }
	         }
		}
		return queryMap;
	}
	
	
	

	public static void main(String[] arges) {

//		System.out
//				.println(decodeURI("%E6%92%92%E6%97%A6%E6%B3%95"));
//
//		System.out.println(encodeURI("撒旦法"));
//
//		System.out.println("%E4%BD%A0%E5%A5%BD%20%E7%9C%9F%E7%9A%84");
//
//         String str  = "APEC召开时不让点柴火做饭";
//         System.out.println(StringEscapeUtils.unescapeEcmaScript("APEC\u53EC\u5F00\u65F6\u4E0D\u8BA9\u70B9\u67F4\u706B\u505A\u996D"));
//         System.out.println(StringEscapeUtils.escapeXml(str));
//         Map<String, String> map = getQueryMap("siteId%3D%26areaId%3D%26lineId%3D%26groupCode%3D%26groupName%3D&page=1&rows=20");
//         System.out.println(map);
         
         
	}

}
