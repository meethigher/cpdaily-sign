package cpdaily;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Cpdaily {

	/**
	 * 提交签到
	 * 
	 * 这个不同学校不一样，需要根据实际情况进行修改
	 * @return json字符串
	 */
	public static String submitSign() {
		String[] items = getItemId();
		String signInstanceWid = JSONObject.fromObject(getSignId()).get("signInstanceWid").toString();
		String param = "{\"abnormalReason\":\"\",\"position\":\"" + Data.poi + "\",\"longitude\":" + Data.log
				+ ",\"isNeedExtra\":1,\"latitude\":" + Data.lat
				+ ",\"isMalposition\":0,\"extraFieldItems\":[{\"extraFieldItemWid\":" + items[0]
				+ ",\"extraFieldItemValue\":\"37.3度以下\"},{\"extraFieldItemWid\":" + items[1]
				+ ",\"extraFieldItemValue\":\"健康\"}],\"signInstanceWid\":\"" + signInstanceWid
				+ "\",\"signPhotoUrl\":\"\"}";
		return HttpUtil.sendPost(Data.submitSign, param, Data.getSubHeaders());
	}

	/**
	 * 获取每日最新的表单
	 * 
	 * @return json字符串
	 */
	public static String getNewSign() {
		String param = "{}";
		return HttpUtil.sendPost(Data.querySign, param, Data.getHeaders());
	}

	/**
	 * 获取详细表单内容
	 * 
	 * @return json字符串
	 */
	public static String getDetailSign() {
		String param = getSignId();
		return HttpUtil.sendPost(Data.detailSign, param, Data.getHeaders());
	}

	/**
	 * 获取表单id
	 * 
	 * @return json字符串
	 */
	public static String getSignId() {
		JSONObject jsonObject = JSONObject.fromObject(getNewSign()).getJSONObject("datas").getJSONArray("unSignedTasks")
				.getJSONObject(0);
		return "{\"signWid\":" + jsonObject.getInt("signWid") + ",\"signInstanceWid\":"
				+ jsonObject.getInt("signInstanceWid") + "}";
	}

	/**
	 * 获取表单中选项的id
	 * 
	 * @return 数组 0为体温37度以下id，1为身体健康id
	 */
	public static String[] getItemId() {
		JSONArray jsonArray = JSONObject.fromObject(getDetailSign()).getJSONObject("datas").getJSONArray("extraField");
		String[] items = new String[2];
		items[0] = jsonArray.getJSONObject(0).getJSONArray("extraFieldItems").getJSONObject(0).getString("wid");
		items[1] = jsonArray.getJSONObject(1).getJSONArray("extraFieldItems").getJSONObject(0).getString("wid");
		return items;
	}

	public static String submitResult() {
		String submitSign = submitSign();
		if (submitSign.indexOf("SUCCESS") > 0) {
			return "success";
		} else if (submitSign.indexOf("任务未开始，扫码签到无效") > 0) {
			return "submited";
		} else {
			return "noform";
		}
	}

	public static void main(String[] args) {
		System.out.println(submitResult());
	}
}
