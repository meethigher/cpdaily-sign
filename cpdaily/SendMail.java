package cpdaily;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 
 * SendMail用来给用户发送通知
 * 
 * @author kit chen
 *
 */
public class SendMail {
	public static String send(String[] mail) {
		Properties properties = new Properties();
		properties.put("mail.transport.protocol", "smtp");// 连接协议
		
		//腾讯企业邮箱，不好使
//		properties.put("mail.smtp.host", "smtp.exmail.qq.com");// 主机名
//		properties.put("mail.smtp.port", "587");// 端口号
		
		//阿里企业邮箱，https://developer.aliyun.com/ask/6542?scm=20140722.184.2.173
		//25端口服务器不能用，465同理，阿里可以用80
		properties.put("mail.smtp.host", "smtp.mxhichina.com");// 主机名
		properties.put("mail.smtp.port", "80");// 端口号
		
		properties.put("mail.smtp.auth", "true");// 设置smtp是否需要认证
		properties.put("mail.smtp.ssl.enable", "true");// 设置是否使用ssl安全连接 ---一般都使用
		properties.put("mail.debug", "false");// 设置是否显示debug信息 true 会在控制台显示相关信息
		try {
			Session session = Session.getInstance(properties);
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(Data.fromMail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(Data.toMail));
			message.setSubject(mail[0]);
			message.setText(mail[1]);
			message.setSentDate(new Date());
			Transport transport = session.getTransport();
			transport.connect(Data.fromMail, Data.fromMailPw);// 登录发信账号
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			return "邮件发送成功!";
		} catch (Exception e) {
			return "邮件发送失败!";
		}
	}
}
