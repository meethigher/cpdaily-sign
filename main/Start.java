package main;

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import cpdaily.Cpdaily;
import cpdaily.Data;
import cpdaily.HttpUtil;
import cpdaily.SendMail;

public class Start {
	public static int morning;
	public static int noon;
	public static int evening;
	public static boolean flag=false;
	static {
		Scanner input=new Scanner(System.in);
		System.out.println("������Cpdaily-Extension:");
		Data.cpdailyExtension=input.nextLine();
		System.out.println("������atw_tc:");
		String atwTc=input.nextLine();
		System.out.println("������MOD_AUTH_CAS:");
		String modAuthCas=input.nextLine();
		Data.cookie="acw_tc="+atwTc+"; MOD_AUTH_CAS="+modAuthCas;
		System.out.println("���������֪ͨ�����䣺");
		Data.toMail=input.nextLine();
		System.out.println("���������Ͽ�ʼСʱ��");
		morning=input.nextInt();
		System.out.println("���������翪ʼСʱ��");
		noon=input.nextInt();
		System.out.println("���������Ͽ�ʼСʱ��");
		evening=input.nextInt();
		
		input.close();
	}
	public static void beginSubmit() {
		if(flag) {
			System.out.println("��ʼǩ��...");
			String result = Cpdaily.submitResult();
			if("success".equals(result)) {
				System.out.println("ǩ���ɹ�");
				String[] mails=new String[2];
				mails[0]="ǩ���ɹ�֪ͨ";
				mails[1]="ʱ�䣺"+new Date().toLocaleString()+"\n"+"�ص㣺"+Data.poi+"\n"+"���ȣ�"+Data.log+"\n"+"γ�ȣ�"+Data.lat;
				System.out.println(SendMail.send(mails));
				flag=false;
			}else if("submited".equals(result)) {
				System.out.println("�Ѿ�ǩ�����ˣ������ٴ�ǩ��");
				flag=false;
			}else {
				System.out.println("ǩ��ʧ��");
			}
		}
	}
	public static void main(String[] args) {
		// �������ֻỰ�߳�
		new Thread(() -> {
			while (true) {
				HttpUtil.sendGet(Data.keepingUrl, Data.getHeaders());
				try {
					Thread.sleep(1000 * 60 * 10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		//�����ؼ��߳�
		new Thread(()->{
			Calendar c;
			while(true) {
				c=Calendar.getInstance();
				int currentHour = c.get(Calendar.HOUR_OF_DAY);
				int currentMinute=c.get(Calendar.MINUTE);
				int currentSecond=c.get(Calendar.SECOND);
				if(currentHour==morning&&currentMinute==0&&currentSecond==0) {
					flag=true;
				}
				if(currentHour==noon&&currentMinute==0&&currentSecond==0) {
					flag=true;
				}
				if(currentHour==evening&&currentMinute==0&&currentSecond==0) {
					flag=true;
				}
				beginSubmit();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}
}
