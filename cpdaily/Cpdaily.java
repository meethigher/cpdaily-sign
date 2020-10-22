package cpdaily;

import java.util.Random;
import java.util.function.Consumer;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Cpdaily {
	public static String signIds;
	public static String stuSignWid;
	
	//���ٴ���
	public static String[] items;
	public static String signInstanceWid;
	
	//���ٴ���
	public static void prepData() {
		items=getItemId();
		signInstanceWid = JSONObject.fromObject(getSignId()).get("signInstanceWid").toString();
		System.out.println("�Ѿ�Ԥ��������");
	}

	/**
	 * �ύǩ��
	 * 
	 * @return json�ַ���
	 */
	public static String submitSign() {
		//���ʹ��Ԥ���أ����Բ������������д���
//		String[] items = getItemId();
//		String signInstanceWid = JSONObject.fromObject(getSignId()).get("signInstanceWid").toString();
		String param = "{\"abnormalReason\":\"\",\"position\":\""+Data.poi+"\""
				+ ",\"longitude\":"+Data.log+",\""
				+ "isNeedExtra\":1,\"latitude\":"+Data.lat+","
				+ "\"isMalposition\":0,\"extraFieldItems\":[{\"extraFieldItemWid\":"+items[0]+""
						+ ",\"extraFieldItemValue\":\"37.3������\"},{\"extraFieldItemWid\":"+items[1]+""
								+ ",\"extraFieldItemValue\":\"37.3������\"},{\"extraFieldItemWid\":"+items[2]+""
								+ ",\"extraFieldItemValue\":\"37.3������\"},{\"extraFieldItemWid\":"+items[3]+",\"extraFieldItemValue\":\"����\"}],\"signInstanceWid\":\""+signInstanceWid+"\",\"signPhotoUrl\":\""+randomPhoto()+"\"}";
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
		signIds=getSignId();
		return HttpUtil.sendPost(Data.detailSign, signIds, Data.getHeaders());
	}

	/**
	 * ��ȡ��id
	 * 
	 * @return json�ַ���
	 */
	public static String getSignId() {
		JSONObject jsonObject = JSONObject.fromObject(getNewSign()).getJSONObject("datas").getJSONArray("unSignedTasks")
				.getJSONObject(0);
		
		//��һ������stuSignWid��Ϊ���²�ˢ����
		stuSignWid=jsonObject.getString("stuSignWid");
		
		return "{\"signWid\":" + jsonObject.getInt("signWid") + ",\"signInstanceWid\":"
				+ jsonObject.getInt("signInstanceWid") + "}";
	}
	
	/**
	 * ���������Ƭ
	 * 
	 * @return
	 */
	public static String randomPhoto() {
		String[] photos = Data.photoUrls.split(",");
		int pieces = photos.length;
		int index = new Random().nextInt(pieces);
		return photos[index];
	}

	/**
	 * ��ȡ����ѡ���id
	 * 
	 * @return ����
	 * �±�0 ������ 37����
	 * �±�1 ������ 37����
	 * �±�2 ������ 37����
	 * �±�3 ���彡��״�� ����
	 */
	public static String[] getItemId() {
		JSONArray jsonArray = JSONObject.fromObject(getDetailSign()).getJSONObject("datas").getJSONArray("extraField");
		String[] items = new String[4];
		items[0] = jsonArray.getJSONObject(0).getJSONArray("extraFieldItems").getJSONObject(0).getString("wid");
		items[1] = jsonArray.getJSONObject(1).getJSONArray("extraFieldItems").getJSONObject(0).getString("wid");
		items[2] = jsonArray.getJSONObject(2).getJSONArray("extraFieldItems").getJSONObject(0).getString("wid");
		items[3] = jsonArray.getJSONObject(3).getJSONArray("extraFieldItems").getJSONObject(0).getString("wid");
		return items;
	}
	
	/**
	 * ����ǩ�����
	 * @return
	 * success �ɹ�
	 * submited ���ύ
	 * noform û�����±��
	 */
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
	
	/**
	 * �������а�����ݡ��жϽ��Ϊsuccess��ʱ�򣬲Ż���Ч
	 * @return
	 */
	public static String getRank() {
		String rankJson = HttpUtil.sendPost(Data.rankUrl, Cpdaily.signIds, Data.getHeaders());
//		String rankJson="{\"code\":\"0\",\"message\":\"SUCCESS\",\"datas\":{\"serverDate\":\"2020-09-12\",\"signStartTime\":\"2020-09-09 16:00\",\"self\":{\"stuSignWid\":\"736437\",\"studentId\":\"20194583\",\"likedNum\":0,\"headPicUrl\":null,\"nickName\":null,\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:20\",\"hasLiked\":\"0\"},\"signs\":[{\"stuSignWid\":\"726393\",\"studentId\":\"20183922\",\"likedNum\":4,\"headPicUrl\":null,\"nickName\":\"��ͬ��\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"726411\",\"studentId\":\"20183948\",\"likedNum\":1,\"headPicUrl\":null,\"nickName\":\"������\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"732625\",\"studentId\":\"20194583\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"�´���\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"732643\",\"studentId\":\"20195883\",\"likedNum\":4,\"headPicUrl\":null,\"nickName\":\"��ѩ\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"732467\",\"studentId\":\"20195912\",\"likedNum\":6,\"headPicUrl\":null,\"nickName\":\"κ����\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"729754\",\"studentId\":\"20183685\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"����\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"732601\",\"studentId\":\"20195887\",\"likedNum\":8,\"headPicUrl\":null,\"nickName\":\"�ѱ���\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"729231\",\"studentId\":\"20170187\",\"likedNum\":3,\"headPicUrl\":null,\"nickName\":\"�羰��\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"732642\",\"studentId\":\"20195882\",\"likedNum\":6,\"headPicUrl\":null,\"nickName\":\"������\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"729875\",\"studentId\":\"20172345\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"������\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"730018\",\"studentId\":\"20190364\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"�׿���\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"726484\",\"studentId\":\"20183888\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"�ܺ���\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"732414\",\"studentId\":\"20195894\",\"likedNum\":6,\"headPicUrl\":null,\"nickName\":\"������\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"726444\",\"studentId\":\"20183972\",\"likedNum\":3,\"headPicUrl\":null,\"nickName\":\"����\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"729134\",\"studentId\":\"20170272\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"������\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"729829\",\"studentId\":\"20183683\",\"likedNum\":3,\"headPicUrl\":null,\"nickName\":\"������\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"726543\",\"studentId\":\"20191793\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"����\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"733283\",\"studentId\":\"20190112\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"��Ԫ��\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"733743\",\"studentId\":\"20191955\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"����׿\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"731123\",\"studentId\":\"20191461\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"���\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"728997\",\"studentId\":\"20173795\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"����\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"733094\",\"studentId\":\"20194389\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"������\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"726853\",\"studentId\":\"20181884\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"������\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"729641\",\"studentId\":\"20193242\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"��ǧһ\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"729928\",\"studentId\":\"20190463\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"����ʤ\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"728073\",\"studentId\":\"20171809\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"�ϱ���\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"727690\",\"studentId\":\"20194061\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"�ܽ���\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"727038\",\"studentId\":\"20182896\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"��Բ\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"733960\",\"studentId\":\"20191921\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"����\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"733656\",\"studentId\":\"20191907\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"�γ�\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"}]}}";
		JSONArray jsonArray = JSONObject.fromObject(rankJson).getJSONObject("datas").getJSONArray("signs");
		
		//�����洢���а�����
		StringBuilder rankList = new StringBuilder();
		jsonArray.forEach((name)->{
			JSONObject object = JSONObject.fromObject(name);
			rankList.append(object.get("studentId")+"->"+object.get("nickName")+"->"+object.get("likedNum")+"��\n");
		});
		
		return rankList.toString();
	}
	
	
	/**
	 * ˢ��
	 * 
	 * @return
	 */
	public static void goGoGo() {
		int i = Data.likeNum;
		String param="{\"stuSignWid\":\""+stuSignWid+"\",\"hasLiked\":0}";
		System.out.println("��ʼˢ��...");
		while (i > 0) {
			i--;
			HttpUtil.sendPost(Data.giveLike, param, Data.getHeaders());
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("ˢ�޽���");
	}
}
