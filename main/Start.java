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
	public static int delay;

	public static boolean flag = false;
	static {
		Scanner input = new Scanner(System.in);
		System.out.println("请输入Cpdaily-Extension:");
		Data.cpdailyExtension = input.nextLine();
		System.out.println("请输入atw_tc:");
		String atwTc = input.nextLine();
		System.out.println("请输入MOD_AUTH_CAS:");
		String modAuthCas = input.nextLine();
		Data.cookie = "acw_tc=" + atwTc + "; MOD_AUTH_CAS=" + modAuthCas;
		System.out.println("请输入接收通知的邮箱：");
		Data.toMail = input.nextLine();
		System.out.println("是否加入刷赞功能？ y or n：");
		isRank = input.nextLine();
		System.out.println("请输入早上开始小时：");
		morning = input.nextInt();
		System.out.println("请输入中午开始小时：");
		noon = input.nextInt();
		System.out.println("请输入晚上开始小时：");
		evening = input.nextInt();
		System.out.println("请输入开始分钟：");
		minute = input.nextInt();
		System.out.println("请输入开始秒：");
		second = input.nextInt();
		System.out.println("请输入延迟，单位毫秒：");
		delay = input.nextInt();

		input.close();

		Cpdaily.prepData();
		System.out.println("已经预加载数据，正在运行...");
	}

	public static void beginSubmit() {
		if (flag) {
			if (delay > 0) {
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			System.out.println("==============");
			System.out.println("开始签到...");
			String date = new Date().toLocaleString();
			String result = Cpdaily.submitResult();
			if ("success".equals(result)) {
				System.out.println(date + " 签到成功");
				if ("y".equals(isRank)) {
					Cpdaily.goGoGo();
				} else {
					try {
						Thread.sleep(1000 * 30);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				String rankList = Cpdaily.getRank();
				String[] mails = new String[2];
				mails[0] = "签到成功通知";
				mails[1] = "时间：" + date + "\n地点：" + Data.poi + "\n经度：" + Data.log + "\n纬度：" + Data.lat
						+ "\n排行榜：以下展示列表\n" + rankList + "\n签到Id：" + Cpdaily.signIds;
				System.out.println(SendMail.send(mails));
				flag = false;
			} else {
				System.out.println("签到失败");
			}

			//预先加载下次数据
			if(!flag) {
				Cpdaily.prepData();
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
		// 开启关键线程
		new Thread(() -> {
			Calendar c;
			while (true) {
				c = Calendar.getInstance();
				int currentHour = c.get(Calendar.HOUR_OF_DAY);
				int currentMinute = c.get(Calendar.MINUTE);
				int currentSecond = c.get(Calendar.SECOND);
				if (currentHour == morning && currentMinute == minute && currentSecond == second) {
					flag = true;
				}
				if (currentHour == noon && currentMinute == minute && currentSecond == second) {
					flag = true;
				}
				if (currentHour == evening && currentMinute == minute && currentSecond == second) {
					flag = true;
				}
				beginSubmit();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}
}
