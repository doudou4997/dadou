package com.dadou.core.utils;

import com.dadou.core.config.ConfigConst;
import com.framework.core.utils.JsonUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 菜单创建工具类
 */
public final class MenuUtils {
	public static void main(String[] args) {
		createMenu();
	}

	/**
	 * 创建Menu
	 * 
	 * @Title: createMenu
	 * @Description: 创建Menu
	 * @param @return
	 * @param @throws IOException 设定文件
	 * @return int 返回类型
	 * @throws
	 */
    public static void createMenu() {
		InputStream inputStream = null;
		BufferedReader bufferedReader = null;
		try {
			inputStream = MenuUtils.class.getResourceAsStream("menu.txt");
			bufferedReader = new BufferedReader(new InputStreamReader(
					inputStream));
			StringBuffer buffer = new StringBuffer();
			String tmp = "";
			while ((tmp = bufferedReader.readLine()) != null) {
				buffer.append(tmp + "\n");
			}
			System.out.println(buffer.toString());
			inputStream.close();
			bufferedReader.close();
			//
	        String access_token= getAccessToken(); 
	        String action = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+access_token;
	        NetUtil.executePost(action,buffer.toString());

		} catch (Exception ex) {
            ex.printStackTrace();
		}
   }
	/**
	 * 获得ACCESS_TOKEN
	 *
	 * @Title: getAccess_token
	 * @Description: 获得ACCESS_TOKEN
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	private static String getAccessToken() {

		String APPID = ConfigConst.appId;
		String APPSECRET = ConfigConst.secret;
		String URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
				+ APPID + "&secret=" + APPSECRET;
		String accessToken = "";
		String responseBody = NetUtil.executeGet(URL);
		JsonElement jsonElement = JsonUtils.parse(responseBody);
		JsonObject jsonObject  = jsonElement.getAsJsonObject();
		if(jsonObject.has("access_token")){
			accessToken  = jsonObject.get("access_token").getAsString();
		}
		return accessToken;
	}
}
