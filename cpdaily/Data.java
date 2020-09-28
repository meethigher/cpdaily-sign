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
	 * {"appVersion":"8.1.11","systemName":"android","model":"MI
	 * 9","lon":0,"systemVersion":"10","deviceId":"设备号","userId":学号,"lat":0}的加密值
	 */
	public static String cpdailyExtension = "ooxxoxx";
	/**
	 * cookie
	 */
	public static String cookie = "ooxxoxx";
	/**
	 * 接收方邮箱
	 */
	public static String toMail = "meethigher@qq.com";
	/**
	 * 定位的地点
	 */
	public static String poi = "吉林省长春市宽城区长春工业大学（北湖西区）";
	/**
	 * 经度
	 */
	public static String log = "125.398185";
	/**
	 * 纬度
	 */
	public static String lat = "44.001709";
	
	
	/**
	 * 学校的host
	 */
	public static final String host = "https://ccut.campusphere.net";
	/**
	 * 发件方邮箱，经过多次测试，腾讯企业邮箱是最稳定的
	 * 
	 * 2020-09-12 垃圾腾讯，还是阿里牛逼
	 */
	public static final String fromMail = "meethigher@meethigher.top";

	/**
	 * 腾讯企业邮箱密码
	 */
	public static final String fromMailPw = "ooxxoxx";

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
	 */
	public static final String querySign = host + "/wec-counselor-sign-apps/stu/sign/getStuSignInfosInOneDay";

	/**
	 * 获取详细表单接口
	 */
	public static final String detailSign = host + "/wec-counselor-sign-apps/stu/sign/detailSignInstance";

	/**
	 * 提交最新签到接口
	 */
	public static final String submitSign = host + "/wec-counselor-sign-apps/stu/sign/submitSign";

	/**
	 * 获取排行榜接口
	 */
	public static final String rankUrl = host + "/wec-counselor-sign-apps/stu/sign/getSignRankList";
	
	/**
	 * 刷赞接口
	 */
	public static final String giveLike = host + "/wec-counselor-sign-apps/stu/sign/giveLikeToStu";

}
