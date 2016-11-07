package com.centfor.system.smack;

import java.io.IOException;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XMPPServer {
	public static Logger logger = LoggerFactory.getLogger(XMPPServer.class);
	//openfire服务器地址
	public static  final String  ip="182.254.137.36";
	//http服务的管理端口

	
	public static final  String httpuserservice="http://"+ip+":9090/plugins/userService/userservice?secret=782qwevsdfsdaf785";
	
	
	//openfire 端口
	private static final int port=5222;
	//登陆账号
	private static final String adminName="admin";
	//登陆密码
	private static final String adminPassword="zywx@openfire";
	//注册的域
	private static final String domain="@openfireserver";
	//客户端的名称
	private static final String clientName="mzywx";
	
	// 新建连接配置对象，设置服务器IP和监听端口
	private static ConnectionConfiguration config = new ConnectionConfiguration(ip, port); 

	static {
		
		//禁用安全模式
		config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
		// 允许自动连接
		config.setReconnectionAllowed(true);
		// 允许登陆成功后更新在线状态
		config.setSendPresence(true);
		// 收到好友邀请后manual表示需要经过同意,accept_all表示不经同意自动为好友
		Roster.setDefaultSubscriptionMode(Roster.SubscriptionMode.manual);
	}

	
	
	
	/**
	 * 获取 XMPPConnection
	 * @return XMPPConnection
	 */
	public static XMPPConnection getXMPPConnection() {
		XMPPConnection connection = null;

		try {
			connection = new XMPPTCPConnection(config);
			connection.connect();
			// 连接服务器
			connection.login(adminName, adminPassword,clientName); // 利用用户名和密码登录
		} catch (SmackException e) {
			logger.error(e.getLocalizedMessage());
		} catch (IOException e) {
			logger.error(e.getLocalizedMessage());
		} catch (XMPPException e) {
			logger.error(e.getLocalizedMessage());
		}

		return connection;

	}
	/**
	 * 关闭XMPPConnection
	 * @param connection
	 */
	public static void closeXMPPConnection(XMPPConnection connection){
		try {
			if(connection!=null){
				connection.disconnect();
			}
		
		} catch (NotConnectedException e) {
			logger.error(e.getLocalizedMessage());
		}	
		
	}
	
	/**
	 * 使用系统账号发送消息
	 * @param connection
	 * @param to
	 * @param message
	 */
	public static void sendSystemMessage(XMPPConnection connection,String to,String message,String messageId){
		sendMessage(connection, adminName, to, message,messageId);
	}
	/**
	 * 发送消息
	 * @param connection
	 * @param from
	 * @param to
	 * @param message
	 */
	public static void sendMessage(XMPPConnection connection,String from ,String to,String message,String messageId){
		try {
			ChatManager cm = ChatManager.getInstanceFor(connection); 	//取得聊天管理器
			Chat chat = cm.createChat(to+domain, null);	//得到与另一个帐号的连接，这里是一对一,@后面是你安装openfire时注册的域
			if(messageId==null){
				//发送消息
				chat.sendMessage(message);
			}else{
				Message m=new Message();
				m.setType(Message.Type.chat);
				m.setPacketID(messageId);
				m.setBody(message);
				chat.sendMessage(m);
			}
			
		} catch (NotConnectedException e) {
			logger.error(e.getLocalizedMessage());
		} catch (XMPPException e) {
			logger.error(e.getLocalizedMessage());
		}		
	}
	
	/**
	 * 针对一个用户发送系统消息
	 * @param to
	 * @param message
	 */
	public static void sendSystemMessage(String to,String message,String messageId){
		XMPPConnection connection = getXMPPConnection();
		sendMessage(connection, adminName, to, message,messageId);
		closeXMPPConnection(connection);
	}
	
	

}
