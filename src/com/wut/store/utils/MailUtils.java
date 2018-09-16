package com.wut.store.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 鍙戦�侀偖浠剁殑宸ュ叿绫�:
 * @author admin
 *
 */
public class MailUtils {

	public static void sendMail(String to,String code){
		
		try {
			// 鑾峰緱杩炴帴:
			Properties props = new Properties();
			Session session = Session.getInstance(props, new Authenticator() {

				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("service@store.com", "111");
				}
				
			});
			// 鏋勫缓閭欢:
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("service@store.com"));
			// 璁剧疆鏀朵欢浜�:
			// TO:鏀朵欢浜�   CC:鎶勯��   BCC:鏆楅��,瀵嗛��.
			message.addRecipient(RecipientType.TO, new InternetAddress(to));
			// 涓婚:
			message.setSubject("激活邮件!");
			// 姝ｆ枃:
			message.setContent("<h1>官方激活邮件：请点击下面链接激活!</h1><h3><a href='http://localhost:8080/store_v2.0/UserServlet?method=active&code="+code+"'>http://localhost:8080/store_v2.0/UserServlet?method=active&code="+code+"</a></h3>", "text/html;charset=UTF-8");
		
			// 鍙戦�侀偖浠�:
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		MailUtils.sendMail("aaa@store.com", "123sdfjklsdkljrsiduoi1123");
	}
}
