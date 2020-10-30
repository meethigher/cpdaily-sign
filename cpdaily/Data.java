package cpdaily;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 存储数据以及接口的类，部分需根据自己情况修改
 * 
 * @author kit chen
 *
 */
public class Data {
	/**
	 * 图片链接
	 */
	public static String photoUrls = "https://meethigher.top/images/kitchen.png,https://meethigher.top/images/kitchen.jpg";
	/**
	 * 默认赞数量
	 */
	public static int likeNum = 520;
	/**
	 * {"appVersion":"8.1.11","systemName":"android","model":"MI
	 * 9","lon":0,"systemVersion":"10","deviceId":"设备号","userId":学号,"lat":0}的加密值
	 */
	public static String cpdailyExtension = "kQDkr/pNQMdvv2lfjaljgajljglajgagdhagaMiBRA/RO/v2pzirtMIWOBr9Ph5QXmLrt/ngN2gij4JaPKFJLAJGAjljgaklLJlhuuigJkhjhHlHhKhkHkpoiyojFHVa8dAAH9b5vav7LSy/AusZqXSa9YrtG1G0cLYuJyoCq8OfZyyIfHEChUnIrSCvLdw0wnuBtmLPss8I5d4t/3MF3Y1GjNvej+GW6prUV1Jbdtr8Z4qw==";
	/**
	 * cookie
	 */
	public static String cookie = "acw_tc=2f624a2216091hyoj196flajj8jg8991hagfd2d5f5d91117658eb5ab2e70f8a; MOD_AUTH_CAS=ST-iap:1018615876819107:ST:6abde32a-3hshs-47s6-faf-b2d573e28ad9:20201030093730";
	/**
	 * 接收方邮箱
	 */
	public static String toMail = "meethigher@qq.com";
	/**
	 * 定位的地点
	 */
	public static String poi = "长春工业大学北湖校区西区知远苑-5栋宿舍";
	/**
	 * 经度
	 */
	public static String log = "125.40051674459573";
	/**
	 * 纬度
	 */
	public static String lat = "44.00362121280072";

	/**
	 * 学校的host
	 */
	public static final String host = "https://ccut.campusphere.net";
	/**
	 * 发件方邮箱，经过多次测试，腾讯企业邮箱是最稳定的
	 * 
	 * 2020-09-12 垃圾腾讯，还是阿里牛逼
	 */
	public static final String fromMail = "chen@meethigher.top";

	/**
	 * 腾讯企业邮箱密码
	 */
	public static final String fromMailPw = "xxxx";

	/**
	 * Cpdaily-Extension需要根据实际情况
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
	 * Cookie需要根据实际情况
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

	// 以下内容所有学校都一样，不用修改
	/**
	 * 保持session
	 */
	public static final String keepingUrl = host + "/portal/index.html";

	/**
	 * 获取最新的签到表单
	 * 
	 *2020年10月29日前接口/wec-counselor-sign-apps/stu/sign/getStuSignInfosInOneDay
	 *2020年10月29日后接口/wec-counselor-sign-apps/stu/sign/queryDailySginTasks
	 */
	public static final String querySign = host + "/wec-counselor-sign-apps/stu/sign/queryDailySginTasks";

	/**
	 * 获取详细表单接口
	 * 2020年10月29日前接口/wec-counselor-sign-apps/stu/sign/detailSignInstance
	 * 2020年10月29日后接口/wec-counselor-sign-apps/stu/sign/detailSignTaskInst
	 */
	public static final String detailSign = host + "/wec-counselor-sign-apps/stu/sign/detailSignTaskInst";

	/**
	 * 提交最新签到接口
	 * 2020年10月29日前接口/wec-counselor-sign-apps/stu/sign/submitSign
	 * 2020年10月29日后接口/wec-counselor-sign-apps/stu/sign/completeSignIn
	 */
	public static final String submitSign = host + "/wec-counselor-sign-apps/stu/sign/completeSignIn";

	/**
	 * 获取排行榜接口
	 */
	public static final String rankUrl = host + "/wec-counselor-sign-apps/stu/sign/getSignRankList";

	/**
	 * 刷赞接口
	 */
	public static final String giveLike = host + "/wec-counselor-sign-apps/stu/sign/giveLikeToStu";

}
