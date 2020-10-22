package cpdaily;

import java.util.Random;
import java.util.function.Consumer;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Cpdaily {
	public static String signIds;
	public static String stuSignWid;
	
	//加速代码
	public static String[] items;
	public static String signInstanceWid;
	
	//加速代码
	public static void prepData() {
		items=getItemId();
		signInstanceWid = JSONObject.fromObject(getSignId()).get("signInstanceWid").toString();
		System.out.println("已经预加载数据");
	}

	/**
	 * 提交签到
	 * 
	 * @return json字符串
	 */
	public static String submitSign() {
		//如果使用预加载，可以不用下面这两行代码
//		String[] items = getItemId();
//		String signInstanceWid = JSONObject.fromObject(getSignId()).get("signInstanceWid").toString();
		String param = "{\"abnormalReason\":\"\",\"position\":\""+Data.poi+"\""
				+ ",\"longitude\":"+Data.log+",\""
				+ "isNeedExtra\":1,\"latitude\":"+Data.lat+","
				+ "\"isMalposition\":0,\"extraFieldItems\":[{\"extraFieldItemWid\":"+items[0]+""
						+ ",\"extraFieldItemValue\":\"37.3度以下\"},{\"extraFieldItemWid\":"+items[1]+""
								+ ",\"extraFieldItemValue\":\"37.3度以下\"},{\"extraFieldItemWid\":"+items[2]+""
								+ ",\"extraFieldItemValue\":\"37.3度以下\"},{\"extraFieldItemWid\":"+items[3]+",\"extraFieldItemValue\":\"健康\"}],\"signInstanceWid\":\""+signInstanceWid+"\",\"signPhotoUrl\":\""+randomPhoto()+"\"}";
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
		signIds=getSignId();
		return HttpUtil.sendPost(Data.detailSign, signIds, Data.getHeaders());
	}

	/**
	 * 获取表单id
	 * 
	 * @return json字符串
	 */
	public static String getSignId() {
		JSONObject jsonObject = JSONObject.fromObject(getNewSign()).getJSONObject("datas").getJSONArray("unSignedTasks")
				.getJSONObject(0);
		
		//这一步保存stuSignWid是为了下步刷赞用
		stuSignWid=jsonObject.getString("stuSignWid");
		
		return "{\"signWid\":" + jsonObject.getInt("signWid") + ",\"signInstanceWid\":"
				+ jsonObject.getInt("signInstanceWid") + "}";
	}
	
	/**
	 * 返回随机照片
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
	 * 获取表单中选项的id
	 * 
	 * @return 数组
	 * 下标0 早体温 37度下
	 * 下标1 午体温 37度下
	 * 下标2 晚体温 37度下
	 * 下标3 身体健康状况 健康
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
	 * 返回签到结果
	 * @return
	 * success 成功
	 * submited 已提交
	 * noform 没有最新表格
	 */
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
	
	/**
	 * 返回排行榜的内容。判断结果为success的时候，才会有效
	 * @return
	 */
	public static String getRank() {
		String rankJson = HttpUtil.sendPost(Data.rankUrl, Cpdaily.signIds, Data.getHeaders());
//		String rankJson="{\"code\":\"0\",\"message\":\"SUCCESS\",\"datas\":{\"serverDate\":\"2020-09-12\",\"signStartTime\":\"2020-09-09 16:00\",\"self\":{\"stuSignWid\":\"736437\",\"studentId\":\"20194583\",\"likedNum\":0,\"headPicUrl\":null,\"nickName\":null,\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:20\",\"hasLiked\":\"0\"},\"signs\":[{\"stuSignWid\":\"726393\",\"studentId\":\"20183922\",\"likedNum\":4,\"headPicUrl\":null,\"nickName\":\"李同辉\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"726411\",\"studentId\":\"20183948\",\"likedNum\":1,\"headPicUrl\":null,\"nickName\":\"邵燕妮\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"732625\",\"studentId\":\"20194583\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"陈传诚\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"732643\",\"studentId\":\"20195883\",\"likedNum\":4,\"headPicUrl\":null,\"nickName\":\"陈雪\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"732467\",\"studentId\":\"20195912\",\"likedNum\":6,\"headPicUrl\":null,\"nickName\":\"魏欣雨\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"729754\",\"studentId\":\"20183685\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"文乐\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"732601\",\"studentId\":\"20195887\",\"likedNum\":8,\"headPicUrl\":null,\"nickName\":\"费冰心\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"729231\",\"studentId\":\"20170187\",\"likedNum\":3,\"headPicUrl\":null,\"nickName\":\"苗景浩\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"732642\",\"studentId\":\"20195882\",\"likedNum\":6,\"headPicUrl\":null,\"nickName\":\"陈相宇\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"729875\",\"studentId\":\"20172345\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"孟子轩\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"730018\",\"studentId\":\"20190364\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"白俊仁\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"726484\",\"studentId\":\"20183888\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"周宏宇\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"732414\",\"studentId\":\"20195894\",\"likedNum\":6,\"headPicUrl\":null,\"nickName\":\"李卫芳\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"726444\",\"studentId\":\"20183972\",\"likedNum\":3,\"headPicUrl\":null,\"nickName\":\"王琨\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"729134\",\"studentId\":\"20170272\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"王中正\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"729829\",\"studentId\":\"20183683\",\"likedNum\":3,\"headPicUrl\":null,\"nickName\":\"王子卿\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"726543\",\"studentId\":\"20191793\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"操瑞\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"733283\",\"studentId\":\"20190112\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"许元赫\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"733743\",\"studentId\":\"20191955\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"王海卓\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"731123\",\"studentId\":\"20191461\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"李东泽\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"728997\",\"studentId\":\"20173795\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"王娇\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"733094\",\"studentId\":\"20194389\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"王宇麒\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"726853\",\"studentId\":\"20181884\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"张子烨\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"729641\",\"studentId\":\"20193242\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"周千一\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"729928\",\"studentId\":\"20190463\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"裴玉胜\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"728073\",\"studentId\":\"20171809\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"邢宝文\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"727690\",\"studentId\":\"20194061\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"曹健飞\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"727038\",\"studentId\":\"20182896\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"金芳圆\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"733960\",\"studentId\":\"20191921\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"于岩\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"},{\"stuSignWid\":\"733656\",\"studentId\":\"20191907\",\"likedNum\":2,\"headPicUrl\":null,\"nickName\":\"宋常\",\"signDate\":\"2020-09-09\",\"signTime\":\"2020-09-09 16:00\",\"hasLiked\":\"0\"}]}}";
		JSONArray jsonArray = JSONObject.fromObject(rankJson).getJSONObject("datas").getJSONArray("signs");
		
		//用来存储排行榜数据
		StringBuilder rankList = new StringBuilder();
		jsonArray.forEach((name)->{
			JSONObject object = JSONObject.fromObject(name);
			rankList.append(object.get("studentId")+"->"+object.get("nickName")+"->"+object.get("likedNum")+"赞\n");
		});
		
		return rankList.toString();
	}
	
	
	/**
	 * 刷赞
	 * 
	 * @return
	 */
	public static void goGoGo() {
		int i = Data.likeNum;
		String param="{\"stuSignWid\":\""+stuSignWid+"\",\"hasLiked\":0}";
		System.out.println("开始刷赞...");
		while (i > 0) {
			i--;
			HttpUtil.sendPost(Data.giveLike, param, Data.getHeaders());
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("刷赞结束");
	}
}
