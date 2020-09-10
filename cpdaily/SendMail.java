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
 * SendMail�������û�����֪ͨ
 * 
 * @author kit chen
 *
 */
public class SendMail {
	public static String send(String[] mail) {
		Properties properties = new Properties();
		properties.put("mail.transport.protocol", "smtp");// ����Э��
		properties.put("mail.smtp.host", "smtp.exmail.qq.com");// ������
		properties.put("mail.smtp.port", "587");// �˿ں�
		properties.put("mail.smtp.auth", "true");// ����smtp�Ƿ���Ҫ��֤
		properties.put("mail.smtp.ssl.enable", "true");// �����Ƿ�ʹ��ssl��ȫ���� ---һ�㶼ʹ��
		properties.put("mail.debug", "false");// �����Ƿ���ʾdebug��Ϣ true ���ڿ���̨��ʾ�����Ϣ
		try {
			Session session = Session.getInstance(properties);
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(Data.fromMail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(Data.toMail));
			message.setSubject(mail[0]);
			message.setText(mail[1]);
			message.setSentDate(new Date());
			Transport transport = session.getTransport();
			transport.connect(Data.fromMail, Data.fromMailPw);// ��¼�����˺�
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			return "�ʼ����ͳɹ�!";
		} catch (Exception e) {
			return "�ʼ�����ʧ��!";
		}
	}
}
