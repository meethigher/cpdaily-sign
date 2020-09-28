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
	public static int minute;
	public static int second;
	public static String isRank;
	
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
		System.out.println("�Ƿ�������а��ܣ� y or n��");
		isRank=input.nextLine();
		System.out.println("���������Ͽ�ʼСʱ��");
		morning=input.nextInt();
		System.out.println("���������翪ʼСʱ��");
		noon=input.nextInt();
		System.out.println("���������Ͽ�ʼСʱ��");
		evening=input.nextInt();
		System.out.println("�����뿪ʼ���ӣ�");
		minute=input.nextInt();
		System.out.println("�����뿪ʼ�룺");
		second=input.nextInt();
		
		input.close();
	}
	public static void beginSubmit() {
		if(flag) {
			System.out.println("==============");
			System.out.println("��ʼǩ��...");
			String date=new Date().toLocaleString();
			String result = Cpdaily.submitResult();
			if("success".equals(result)) {
				String rankList="δ�����ù���";
				System.out.println(date+" ǩ���ɹ�");
				if("y".equals(isRank)) {
					rankList=Cpdaily.getRank();
				}
				String[] mails=new String[2];
				mails[0]="ǩ���ɹ�֪ͨ";
				mails[1]="ʱ�䣺"+date+"\n�ص㣺"+Data.poi+"\n���ȣ�"+Data.log+"\nγ�ȣ�"+Data.lat+"\n���а�����չʾ�б�\n"+rankList+"\nǩ��Id��"+Cpdaily.signIds;
				System.out.println(SendMail.send(mails));
				flag=false;
			}else if("submited".equals(result)) {
				System.out.println(date+" �Ѿ�ǩ�����ˣ������ٴ�ǩ��");
				flag=false;
			}else {
				System.out.println(date+" ǩ��ʧ��");
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
				if(currentHour==morning&&currentMinute==minute&&currentSecond==second) {
					flag=true;
				}
				if(currentHour==noon&&currentMinute==minute&&currentSecond==second) {
					flag=true;
				}
				if(currentHour==evening&&currentMinute==minute&&currentSecond==second) {
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
