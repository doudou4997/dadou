package com.dadou.sys.login;

import com.dadou.sys.emp.pojos.Employee;
import com.framework.core.utils.ActionContextUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 处理登录相关.
 *
 * 由原来的LogonManage、LogonAdmins、LogonListener三个类合并而来。
 */
public class LoginManager {

	private static final Log LOGGER = LogFactory.getLog(LoginManager.class);
	private static final String LOGIN_FLAG = LoginManager.class.getSimpleName();

	// 两个Map存储已登录用户及其对应的HttpSession. 键都是用户ID.
	private static final ConcurrentHashMap<String, Employee> USERS
			= new ConcurrentHashMap<String, Employee>();
	private static final ConcurrentHashMap<String, HttpSession> SESSIONS
			= new ConcurrentHashMap<String, HttpSession>();

	/**
	 * 登录.记录用户及其对应的HttpSession
	 *
	 * @param user
	 *            登录用户
	 */
	public static boolean login(Employee user) {
		logout(user.getId());
		HttpSession session = currentSession();
		UserSessionBindingListener listener = new UserSessionBindingListener();
		listener.user = user;
		session.setAttribute(LOGIN_FLAG, listener);
		return true;
	}

	/**
	 * 当前会话中的用户退出
	 */
	public static void logout() {
		logout(currentSession());
	}
	/**
	 * 获取在线用户的数量
	 *
	 * @return
	 */
	public static int getOnlineNum() {
		return USERS.size();
	}
	/**
	 * 判断用户是否在线
	 * @return
	 */
	public static boolean isOnline(){
		return (currentUser() != null);
	}

	/**
	 * 判断用户是否在线
	 *
	 * @param userName
	 *            用户名
	 * @return
	 */
	public static boolean isOnline(String userName) {
		boolean b = false;
		// 获取在线用户集合
		Collection<Employee> set = USERS.values();
		for (Employee admin : set) {
			if (admin.getUserName().equals(userName)) {
				b = true;
				break;
			}
		}
		return b;
	}
	/**
	 * 特定ID的用户退出
	 *
	 * @param userId
	 *            用户ID
	 */
	public static void logout(String userId) {
		logout(SESSIONS.get(userId));
	}

	/**
	 * 特定会话的用户退出
	 *
	 * @param session
	 *            会话
	 */
	public static void logout(HttpSession session) {
		if (session == null)
			return;
		session.removeAttribute(LOGIN_FLAG);
		session.invalidate();
	}

	/**
	 * 获取当前会话的已登录用户。
	 *
	 * JSP中可通过 ${LoginManager.currentUser}获取。
	 *
	 * @return 已登录用户
	 */
	public static Employee currentUser() {
		return getLoggedinUser(currentSession());
	}
	/**
	 * 获取当前会话的已登录用户。
	 *
	 * JSP中可通过 ${LoginManager.currentUser}获取。
	 *
	 * @return 已登录用户
	 */
	public static String getCurrentUserId() {
		return currentUser().getId();
	}
	/**
	 * 获取特定会话的已登录用户。
	 *
	 * @return 已登录用户
	 */
	public static Employee currentUser(HttpSession session) {
		return getLoggedinUser(session);
	}

	/**
	 * 获取所有已登录用户
	 *
	 * @return 已登录用户
	 */
	public static Collection<Employee> getLoggedinUsers() {
		return Collections.unmodifiableCollection(USERS.values());
	}

	/**
	 * 获取所有已登录用户的ID
	 *
	 * @return 已登录用户ID
	 */
	public static Set<String> getLoggedinUserIds() {
		return Collections.unmodifiableSet(USERS.keySet());
	}

	/**
	 * 获取特定ID的已登录用户
	 *
	 * @param userId
	 *            用户ID
	 * @return 已登录用户
	 */
	public static Employee getLoggedinUser(Integer userId) {
		return USERS.get(userId);
	}

	/**
	 * 获取特定会话中的已登录用户
	 *
	 * @param session
	 *            会话
	 * @return 已登录用户
	 */
	public static Employee getLoggedinUser(HttpSession session) {
		if (session == null)
			return null;
		UserSessionBindingListener listener
				= (UserSessionBindingListener) session.getAttribute(LOGIN_FLAG);
		if (listener == null)
			return null;
		return listener.user;
	}
	/**
	 * 从Session中删除key对应的value
	 *
	 * @param key
	 */
	public static void removeAttribute(String key) {
		HttpSession session = currentSession();
		session.removeAttribute(key);
	}
	/**
	 * 根据key把对象存放到Session中
	 * @param key
	 * @param value
	 */
	public static void setAttribute(String key,Object value){
		HttpSession session = currentSession();
		session.setAttribute(key, value);
	}
	/**
	 * 从Session中获得key对应的vlaue
	 *
	 * @param key
	 * @return
	 */
	public static Object getAttribute(String key) {
		HttpSession session = currentSession();
		return session.getAttribute(key);
	}
	// 当前会话
	private static HttpSession currentSession() {
		return ActionContextUtils.getRequest().getSession();
	}

	public static class UserSessionBindingListener implements HttpSessionBindingListener {

		Employee user;

		public Employee getCurrentUser() {
			return user;
		}

		public void valueBound(HttpSessionBindingEvent e) {
			HttpSession session = e.getSession();
			USERS.put(user.getId(), user);
			SESSIONS.put(user.getId(), session);
			LOGGER.debug("USER " + user.getUserName() + " LOGIN");
		}

		public void valueUnbound(HttpSessionBindingEvent e) {
			USERS.remove(user.getId());
			SESSIONS.remove(user.getId());
			LOGGER.debug("USER " + user.getUserName() + " LOGOUT");
		}
	}
}
