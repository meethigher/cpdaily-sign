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
		System.out.println("请输入Cpdaily-Extension:");
		Data.cpdailyExtension=input.nextLine();
		System.out.println("请输入atw_tc:");
		String atwTc=input.nextLine();
		System.out.println("请输入MOD_AUTH_CAS:");
		String modAuthCas=input.nextLine();
		Data.cookie="acw_tc="+atwTc+"; MOD_AUTH_CAS="+modAuthCas;
		System.out.println("请输入接收通知的邮箱：");
		Data.toMail=input.nextLine();
		System.out.println("请输入早上开始小时：");
		morning=input.nextInt();
		System.out.println("请输入中午开始小时：");
		noon=input.nextInt();
		System.out.println("请输入晚上开始小时：");
		evening=input.nextInt();
		
		input.close();
	}
	public static void beginSubmit() {
		if(flag) {
			System.out.println("开始签到...");
			String result = Cpdaily.submitResult();
			if("success".equals(result)) {
				System.out.println("签到成功");
				String[] mails=new String[2];
				mails[0]="签到成功通知";
				mails[1]="时间："+new Date().toLocaleString()+"\n"+"地点："+Data.poi+"\n"+"经度："+Data.log+"\n"+"纬度："+Data.lat;
				System.out.println(SendMail.send(mails));
				flag=false;
			}else if("submited".equals(result)) {
				System.out.println("已经签到过了，不用再次签到");
				flag=false;
			}else {
				System.out.println("签到失败");
			}
		}
	}
	public static void main(String[] args) {
		// 开启保持会话线程
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
		//开启关键线程
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
