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
		System.out.println("������Cpdaily-Extension:");
		Data.cpdailyExtension = input.nextLine();
		System.out.println("������atw_tc:");
		String atwTc = input.nextLine();
		System.out.println("������MOD_AUTH_CAS:");
		String modAuthCas = input.nextLine();
		Data.cookie = "acw_tc=" + atwTc + "; MOD_AUTH_CAS=" + modAuthCas;
		System.out.println("���������֪ͨ�����䣺");
		Data.toMail = input.nextLine();
		System.out.println("�Ƿ����ˢ�޹��ܣ� y or n��");
		isRank = input.nextLine();
		System.out.println("���������Ͽ�ʼСʱ��");
		morning = input.nextInt();
		System.out.println("���������翪ʼСʱ��");
		noon = input.nextInt();
		System.out.println("���������Ͽ�ʼСʱ��");
		evening = input.nextInt();
		System.out.println("�����뿪ʼ���ӣ�");
		minute = input.nextInt();
		System.out.println("�����뿪ʼ�룺");
		second = input.nextInt();
		System.out.println("�������ӳ٣���λ���룺");
		delay = input.nextInt();

		input.close();

		Cpdaily.prepData();
		System.out.println("�Ѿ�Ԥ�������ݣ���������...");
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
			System.out.println("��ʼǩ��...");
			String date = new Date().toLocaleString();
			String result = Cpdaily.submitResult();
			if ("success".equals(result)) {
				System.out.println(date + " ǩ���ɹ�");
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
				mails[0] = "ǩ���ɹ�֪ͨ";
				mails[1] = "ʱ�䣺" + date + "\n�ص㣺" + Data.poi + "\n���ȣ�" + Data.log + "\nγ�ȣ�" + Data.lat
						+ "\n���а�����չʾ�б�\n" + rankList + "\nǩ��Id��" + Cpdaily.signIds;
				System.out.println(SendMail.send(mails));
				flag = false;
			} else {
				System.out.println("ǩ��ʧ��");
			}

			//Ԥ�ȼ����´�����
			if(!flag) {
				Cpdaily.prepData();
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
		// �����ؼ��߳�
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
