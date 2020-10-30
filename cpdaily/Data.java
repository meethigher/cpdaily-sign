package cpdaily;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * �洢�����Լ��ӿڵ��࣬����������Լ�����޸�
 * 
 * @author kit chen
 *
 */
public class Data {
	/**
	 * ͼƬ����
	 */
	public static String photoUrls = "https://meethigher.top/images/kitchen.png,https://meethigher.top/images/kitchen.jpg";
	/**
	 * Ĭ��������
	 */
	public static int likeNum = 520;
	/**
	 * {"appVersion":"8.1.11","systemName":"android","model":"MI
	 * 9","lon":0,"systemVersion":"10","deviceId":"�豸��","userId":ѧ��,"lat":0}�ļ���ֵ
	 */
	public static String cpdailyExtension = "kQDkr/pNQMdvv2lfjaljgajljglajgagdhagaMiBRA/RO/v2pzirtMIWOBr9Ph5QXmLrt/ngN2gij4JaPKFJLAJGAjljgaklLJlhuuigJkhjhHlHhKhkHkpoiyojFHVa8dAAH9b5vav7LSy/AusZqXSa9YrtG1G0cLYuJyoCq8OfZyyIfHEChUnIrSCvLdw0wnuBtmLPss8I5d4t/3MF3Y1GjNvej+GW6prUV1Jbdtr8Z4qw==";
	/**
	 * cookie
	 */
	public static String cookie = "acw_tc=2f624a2216091hyoj196flajj8jg8991hagfd2d5f5d91117658eb5ab2e70f8a; MOD_AUTH_CAS=ST-iap:1018615876819107:ST:6abde32a-3hshs-47s6-faf-b2d573e28ad9:20201030093730";
	/**
	 * ���շ�����
	 */
	public static String toMail = "meethigher@qq.com";
	/**
	 * ��λ�ĵص�
	 */
	public static String poi = "������ҵ��ѧ����У������֪ԶԷ-5������";
	/**
	 * ����
	 */
	public static String log = "125.40051674459573";
	/**
	 * γ��
	 */
	public static String lat = "44.00362121280072";

	/**
	 * ѧУ��host
	 */
	public static final String host = "https://ccut.campusphere.net";
	/**
	 * ���������䣬������β��ԣ���Ѷ��ҵ���������ȶ���
	 * 
	 * 2020-09-12 ������Ѷ�����ǰ���ţ��
	 */
	public static final String fromMail = "chen@meethigher.top";

	/**
	 * ��Ѷ��ҵ��������
	 */
	public static final String fromMailPw = "xxxx";

	/**
	 * Cpdaily-Extension��Ҫ����ʵ�����
	 * 
	 * @return
	 */
	public static Map<String, String> getSubHeaders() {
		Map<String, String> map = getHeaders();
		map.put("CpdailyStandAlone", "0");
		map.put("extension", "1");
		map.put("Cpdaily-Extension", cpdailyExtension);
		return map;
	}

	/**
	 * Cookie��Ҫ����ʵ�����
	 * 
	 * @return
	 */
	public static Map<String, String> getHeaders() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("tenantId", "ccut");
		map.put("User-Agent",
				"Mozilla/5.0 (Linux; Android 10; MI 9 Build/QKQ1.190825.002; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/33.0.0.0 Mobile Safari/537.36 okhttp/3.8.1");
		map.put("Content-Type", "application/json;charset=utf-8");
		map.put("Host", "ccut.campusphere.net");
		map.put("Connection", "Keep-Alive");
		map.put("Accept-Encoding", "gzip");
		map.put("Cookie", cookie);
		return map;
	}

	// ������������ѧУ��һ���������޸�
	/**
	 * ����session
	 */
	public static final String keepingUrl = host + "/portal/index.html";

	/**
	 * ��ȡ���µ�ǩ����
	 * 
	 *2020��10��29��ǰ�ӿ�/wec-counselor-sign-apps/stu/sign/getStuSignInfosInOneDay
	 *2020��10��29�պ�ӿ�/wec-counselor-sign-apps/stu/sign/queryDailySginTasks
	 */
	public static final String querySign = host + "/wec-counselor-sign-apps/stu/sign/queryDailySginTasks";

	/**
	 * ��ȡ��ϸ���ӿ�
	 * 2020��10��29��ǰ�ӿ�/wec-counselor-sign-apps/stu/sign/detailSignInstance
	 * 2020��10��29�պ�ӿ�/wec-counselor-sign-apps/stu/sign/detailSignTaskInst
	 */
	public static final String detailSign = host + "/wec-counselor-sign-apps/stu/sign/detailSignTaskInst";

	/**
	 * �ύ����ǩ���ӿ�
	 * 2020��10��29��ǰ�ӿ�/wec-counselor-sign-apps/stu/sign/submitSign
	 * 2020��10��29�պ�ӿ�/wec-counselor-sign-apps/stu/sign/completeSignIn
	 */
	public static final String submitSign = host + "/wec-counselor-sign-apps/stu/sign/completeSignIn";

	/**
	 * ��ȡ���а�ӿ�
	 */
	public static final String rankUrl = host + "/wec-counselor-sign-apps/stu/sign/getSignRankList";

	/**
	 * ˢ�޽ӿ�
	 */
	public static final String giveLike = host + "/wec-counselor-sign-apps/stu/sign/giveLikeToStu";

}
