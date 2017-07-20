package com.dadou.weixin.scan.service;

import com.dadou.core.utils.MessageUtil;
import com.dadou.shop.user.dao.UserDao;
import com.dadou.weixin.scan.dao.UserBindDao;
import com.dadou.weixin.scan.message.MessageConst;
import com.dadou.weixin.scan.message.TextMessage;
import com.dadou.weixin.scan.message.TransferMessage;
import com.dadou.weixin.scan.pojos.UserBind;
import com.framework.core.utils.ExceptionUtils;
import com.framework.core.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信Service接口
 */
public  class WeixinService {
	private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	private static Log logger = LogFactory.getLog(WeixinService.class);
	/**用户绑定Dao**/
	protected UserBindDao userBindDao;
	/**用户Dao**/
	protected UserDao userDao;
	/**
	 * 处理微信发来的请求
	 * 
	 * @param
	 * @return
	 */
	public  String doProcessRequest(Map<String, String> requestMap){
		String respMessage = "请求处理异常，请稍候尝试！";
		// 获取消息类型
		String msgType = requestMap.get("MsgType");
		if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
			// 事件类型
			String eventType = requestMap.get("Event");
			if (MessageUtil.EVENT_TYPE_SUBSCRIBE.equals(eventType)) {
				// 订阅
				respMessage = 	subscribe(requestMap);
			} else if (MessageUtil.EVENT_TYPE_SCAN.equals(eventType)) {
				// 扫描,当用户进行扫描后，将要发生两个事件，首先:调用scan()，如果用户点击关注的话,会调用subscribe()
				respMessage = scan(requestMap);
			} else if (MessageUtil.EVENT_TYPE_UNSUBSCRIBE.equals(eventType)) {
				// 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				//respMessage = unsubscribe(requestMap);
			} else if(MessageUtil.EVENT_TYPE_CLICK.equals(eventType)){
				
			} else if(MessageUtil.EVENT_TYPE_VIEW.equals(eventType)){
				String eventKey = requestMap.get("EventKey");  
				respMessage = eventKey;
				//TODO 添加点击记录
			}
		}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)){//接收用户发出文本信息
			//调用客服系统
			respMessage = transferReply(requestMap);
		}
		//其他事件
		return respMessage;
	
	}

	/**
	 * 用户订阅公众号
	 * 如果第一次关注，则保存对象
	 * @param params
	 * @return
	 */
	public  String subscribe(Map<String, String> params){
		String result = "谢谢关注";
		try {
			// 获取用户Id
			String fromUserName = params.get(MessageConst.FROM_USER_NAME);
			//获取事件类型
			String eventKey = params.get(MessageConst.EVENT_KEY);
			//不是扫码进入,而是通过查找关注进入
			if(StringUtils.isEmpty(eventKey)||!eventKey.contains("qrscene") ){
		        List<UserBind> userBindlist = userBindDao.findList("findByOpenId", fromUserName);
				if(userBindlist!=null && !userBindlist.isEmpty()){
					//已有绑定设备
					UserBind ub = userBindlist.get(0);
					StringBuffer msg = new StringBuffer();
					msg.append("-您当前已绑定的设备MAC地址为:\n"+ub.getMac())
		               .append("-查看【最新影片】、【热门影片】，并推送到您的设备.");
					result = getTextMessage(params, msg.toString());
				}else{
					//还没有绑定设备
					result = getTextMessage(params, "谢谢关注,您还没有绑定任何设备,您可以：\n1.扫描二维码关注设备.\n2.点击【帮助信息】-【设备列表】进行编辑设备.");
				}
				return result;
			}
			//扫描方式进入,通常带场景值
			String sceneId = eventKey.substring("qrscene_".length());
			if (logger.isDebugEnabled()) {
				logger.debug("sceneId = " + sceneId);
			}
			//查询该场景值对应的设备
			/**
			User user = userDao.findById(NumberUtils.toInt(sceneId, 0));
			if (user != null) {
				Map<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("userId", user.getId());
				queryMap.put("openId", fromUserName);
				// openId和userId唯一决定一条绑定记录
				List<UserBind> list = userBindDao.findList(queryMap);
				if (list == null ||  list.isEmpty()) {
					UserBind userBind = new UserBind();
					userBind.setOpenId(fromUserName);
					// 绑定时间
					String bindTime = DateUtils.formatDate(YYYY_MM_DD_HH_MM_SS);
					userBind.setBindTime(bindTime);
					userBind.setUserId(user.getId());
					//userBind.setMac(user.getMac());
					//表示当前设备
					userBind.setStatus(1);
					//userBind.setMac(user.getMac());
					userBindDao.save(userBind);
				}
				//把当前设备设置为绑定状态,其他的该微信号绑定的设备设置为未绑定状态
				userBindDao.update("updateBatch",queryMap);
				//result = getImageMessage(params,user.getMac(),3);
			}
			**/
			//获取用户信息更新到数据库中
			
		} catch (Exception ex) {
			String event = ExceptionUtils.formatStackTrace(ex);
			logger.equals(event);
			result = getTextMessage(params, "绑定失败,请稍后重试!");
		}
		return result;
	}
	/**
	 * 用于已经关注,然后进行扫描
	 * @param params
	 * @return
	 */
	public  String scan(Map<String, String> params){
		String result = "谢谢重新绑定";
		try {
			// 获取用户Id
			String fromUserName = params.get(MessageConst.FROM_USER_NAME);
			//与关注时不同
			String sceneId = params.get(MessageConst.EVENT_KEY);
			if (logger.isDebugEnabled()) {
				logger.debug("sceneId = " + sceneId);
			}

//			User user = userDao.findById(NumberUtils.toInt(sceneId, 0));
//			if (user != null) {
//				//查询条件
//				Map<String, Object> queryMap = new HashMap<String, Object>();
//				queryMap.put("userId", user.getId());
//				queryMap.put("openId", fromUserName);
//				// openId和userId唯一决定一条绑定记录
//				List<UserBind> list = userBindDao.findList(queryMap);
//				if (list == null || list.isEmpty()) {
//					// 绑定对象
//					UserBind userBind = new UserBind();
//					userBind.setOpenId(fromUserName);
//					// 绑定时间
//					String bindTime = DateUtils
//							.formatDate(YYYY_MM_DD_HH_MM_SS);
//					userBind.setBindTime(bindTime);
//					//默认客厅设备
//					userBind.setBindName("冰箱");
//					// 已经注册过
//					userBind.setUserId(user.getId());
//					//userBind.setMac(user.getMac());
//					// 设置当前绑定
//					userBind.setStatus(1);
//					userBindDao.save(userBind);
//				}
//				userBindDao.update("updateBatch",queryMap);
//				StringBuffer msg = new StringBuffer();
//				msg.append("-恭喜您,已成功绑定设备,MAC地址为:\n"/*+user.getMac()*/)
//						.append("-查看【最新影片】、【热门影片】，并推送到您的设备.");
//				result = getTextMessage(params, msg.toString());
//			}

		} catch (Exception ex) {
			String event = ExceptionUtils.formatStackTrace(ex);
			logger.equals(event);
			result = getTextMessage(params, "绑定失败,请稍后重试!");
		}
		return result;
	}
	/****
	 * 客服信息转发20150303
	 * @return
	 */
	 private String transferReply(Map<String, String> params){
		    // 回复文本消息
			TransferMessage transferMessage = new TransferMessage();
			transferMessage.setToUserName(params.get(MessageConst.FROM_USER_NAME));
			transferMessage.setFromUserName(params.get(MessageConst.TO_USER_NAME));
			transferMessage.setCreateTime(new Date().getTime());
			transferMessage.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_TRANSFER);
		    return  MessageUtil.transferMessageToXml(transferMessage);
     } 
	/**
	 * 获取文本回复
	 * @param params
	 * @param respContent
	 * @return
	 */
	public String getTextMessage(Map<String, String> params,String respContent){
		// 回复文本消息
		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName(params.get(MessageConst.FROM_USER_NAME));
		textMessage.setFromUserName(params.get(MessageConst.TO_USER_NAME));
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		textMessage.setContent(respContent);
		return MessageUtil.textMessageToXml(textMessage);
	}
	
	//////////////////////////
	//////一般业务方法
	/////////////////////////
	public List<UserBind> findUserBinds(String openId) {
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("openId", openId);
		List<UserBind> userBindlist = userBindDao.findList(params);
		return userBindlist;
	}
	//设置默认设备,同时获取设置后的列表
	public void doSetDefault(Integer id) {
		UserBind ub = userBindDao.findById(id);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", ub.getUserId());
		params.put("openId", ub.getOpenId());
		//设置绑定状态
		userBindDao.update("updateBatch",params);
	}
	//更新设备名称
	public void updateUserBind(Integer id, String bindName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("bindName", bindName);
		userBindDao.update(params);
	}
	public void doDelUserBind(Integer id) {
		userBindDao.removeById(id);
		
	}
	public UserBind findUserBindById(Integer id) {
		return userBindDao.findById(id);
	}
	public UserBindDao getUserBindDao() {
		return userBindDao;
	}

	public void setUserBindDao(UserBindDao userBindDao) {
		this.userBindDao = userBindDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}