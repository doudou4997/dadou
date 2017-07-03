package com.dadou.sys;

/**
 * 后台管理系统常量文件
 */
public final class CmsConst {
	public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 根节点的值为1，用于所有菜单情况
	 */
	public static final String MENU_ROOT_ID = "1";
	/**
	 * 用户存储在Session中的key
	 */
	public static final String SESSION_USER = "session_user";
	/**
	 * 分割符
	 */
	public static final String SPLITCHAR = ";";
	/**
	 * 分页常量
	 */
	public static final String PAGE_NUMBER = "com.framework.core.taglib.page.currPage";
	public static final String PAGE_NUMBER_E = "page";
	/**
	 * 分页大小
	 */
	public static final int PAGE_SIZE = 40;
	/**
	 * 传递的集合名称
	 */
	public static final String MENU_LIST_NAME = "menuItemList";
}
