package cpdaily;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Cpdaily {

	/**
	 * �ύǩ��
	 * 
	 * �����ͬѧУ��һ������Ҫ����ʵ����������޸�
	 * @return json�ַ���
	 */
	public static String submitSign() {
		String[] items = getItemId();
		String signInstanceWid = JSONObject.fromObject(getSignId()).get("signInstanceWid").toString();
		String param = "{\"abnormalReason\":\"\",\"position\":\"" + Data.poi + "\",\"longitude\":" + Data.log
				+ ",\"isNeedExtra\":1,\"latitude\":" + Data.lat
				+ ",\"isMalposition\":0,\"extraFieldItems\":[{\"extraFieldItemWid\":" + items[0]
				+ ",\"extraFieldItemValue\":\"37.3������\"},{\"extraFieldItemWid\":" + items[1]
				+ ",\"extraFieldItemValue\":\"����\"}],\"signInstanceWid\":\"" + signInstanceWid
				+ "\",\"signPhotoUrl\":\"\"}";
		return HttpUtil.sendPost(Data.submitSign, param, Data.getSubHeaders());
	}

	/**
	 * ��ȡÿ�����µı�
	 * 
	 * @return json�ַ���
	 */
	public static String getNewSign() {
		String param = "{}";
		return HttpUtil.sendPost(Data.querySign, param, Data.getHeaders());
	}

	/**
	 * ��ȡ��ϸ������
	 * 
	 * @return json�ַ���
	 */
	public static String getDetailSign() {
		String param = getSignId();
		return HttpUtil.sendPost(Data.detailSign, param, Data.getHeaders());
	}

	/**
	 * ��ȡ��id
	 * 
	 * @return json�ַ���
	 */
	public static String getSignId() {
		JSONObject jsonObject = JSONObject.fromObject(getNewSign()).getJSONObject("datas").getJSONArray("unSignedTasks")
				.getJSONObject(0);
		return "{\"signWid\":" + jsonObject.getInt("signWid") + ",\"signInstanceWid\":"
				+ jsonObject.getInt("signInstanceWid") + "}";
	}

	/**
	 * ��ȡ����ѡ���id
	 * 
	 * @return ���� 0Ϊ����37������id��1Ϊ���彡��id
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
		} else if (submitSign.indexOf("����δ��ʼ��ɨ��ǩ����Ч") > 0) {
			return "submited";
		} else {
			return "noform";
		}
	}

	public static void main(String[] args) {
		System.out.println(submitResult());
	}
}
