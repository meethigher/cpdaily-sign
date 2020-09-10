之前未开学之前，是信息表采集，开学之后，改成了循环签到，那就想着再写个脚本

<!--more-->

站在巨人的肩膀上，所以该代码全部开源，我也借鉴的大佬的东西，放在第三节致谢。有问题可以在下面进行留言讨论！

[源码](https://github.com/meethigher/cpdaily-sign)

[博客地址](https://meethigher.top/blog/2020/cpdaily-sign/)

# 一、开发流程

## 1.1 思路

1. 抓包（抓包的流程，可以参照我之前的[文章](https://meethigher.top/blog/2020/cpdaily-automation/)）
2. 每日接口获取最新表单
3. 通过上步的id获取详细表单
4. 通过上步获取该表单的选项id

如果整体的思路看不明白，可以参照下面的详细步骤

## 2.1 获取最新表单

接口

```html
/wec-counselor-sign-apps/stu/sign/getStuSignInfosInOneDay
```

参数

```json
{}
```

返回值

```json
{
	"code": "0",
	"message": "SUCCESS",
	"datas": {
		"dayInMonth": "2020-09-10",
		"codeRcvdTasks": [],
		"signedTasks": [],
		"unSignedTasks": [{
			"stuSignWid": "746210",
			"signInstanceWid": "4146", //需要
			"signWid": "514213", //需要
			"signRate": "1",
			"taskType": "4",
			"taskName": "一日三签（早签到）",
			"senderUserName": "牛逼学院(牛逼老师)",
			"signStatus": "2",
			"isMalposition": null,
			"isLeave": "0",
			"leavePcUrl": null,
			"leaveMobileUrl": null,
			"currentTime": "2020-09-10 08:15",
			"singleTaskBeginTime": null,
			"singleTaskEndTime": null,
			"rateSignDate": "2020-09-10 (周四)",
			"rateTaskBeginTime": "05:00",
			"rateTaskEndTime": "09:59"
		}, {
			"stuSignWid": "759720",
			"signInstanceWid": "4199",
			"signWid": "514234",
			"signRate": "1",
			"taskType": "4",
			"taskName": "一日三签（午签到）",
			"senderUserName": "牛逼学院(牛逼老师)",
			"signStatus": "2",
			"isMalposition": null,
			"isLeave": "0",
			"leavePcUrl": null,
			"leaveMobileUrl": null,
			"currentTime": "2020-09-10 08:15",
			"singleTaskBeginTime": null,
			"singleTaskEndTime": null,
			"rateSignDate": "2020-09-10 (周四)",
			"rateTaskBeginTime": "10:00",
			"rateTaskEndTime": "15:59"
		}, {
			"stuSignWid": "778325",
			"signInstanceWid": "4266",
			"signWid": "535128",
			"signRate": "1",
			"taskType": "4",
			"taskName": "一日三签（晚签到）",
			"senderUserName": "牛逼学院(牛逼老师)",
			"signStatus": "2",
			"isMalposition": null,
			"isLeave": "0",
			"leavePcUrl": null,
			"leaveMobileUrl": null,
			"currentTime": "2020-09-10 08:15",
			"singleTaskBeginTime": null,
			"singleTaskEndTime": null,
			"rateSignDate": "2020-09-10 (周四)",
			"rateTaskBeginTime": "16:00",
			"rateTaskEndTime": "22:00"
		}],
		"leaveTasks": []
	}
}
```

其中我们需要获取`signWid`514213和`signInstanceWid`4146

## 2.2 获取详细信息

接口

```html
/wec-counselor-sign-apps/stu/sign/detailSignInstance
```

参数

```json
{
  "signWid" : 514213,
  "signInstanceWid" : 4146
}
```

返回值

```json
{
  "message" : "SUCCESS",
  "datas" : {
    "latitude" : "",
    "singleTaskEndTime" : null,
    "catQrUrl" : "https:\/\/cat.cpdaily.com\/erweima",
    "singleTaskBeginTime" : null,
    "signInstanceWid" : "4146",
    "signPhotoUrl" : null,
    "signTime" : null,
    "senderUserName" : "牛逼学院(牛逼老师)",
    "qrCodeRcvdUsers" : [
      {
        "targetWid" : "91457111",
        "targetType" : "4",
        "targetName" : "牛逼老师\/19070302",
        "targetGrade" : "-1",
        "targetDegree" : "-1",
        "targetUserType" : "-1"
      }
    ],
    "isNeedExtra" : 1,
    "signCondition" : 0,
    "currentTime" : "2020-09-10 09:33:08",
    "taskDesc" : "https:\/\/wecres.cpdaily.com\/counselor\/1018615876819107\/content\/54e503639d0244049c3b85baabe509fc.html",
    "signType" : null,
    "changeActorName" : "牛逼老师",
    "isMalposition" : 0,
    "longitude" : "",
    "signedStuInfo" : {
      "cls" : "20200521",
      "major" : "改变世界专业-牛逼",
      "dept" : "牛逼学院",
      "sex" : "男",
      "mobile" : null,
      "nation" : "-",
      "userWid" : "1017790009",
      "userId" : "20200521",
      "userName" : "雷军",
      "malposition" : null,
      "extraFieldItemVos" : [
        {
          "fieldIndex" : 0,
          "extraDesc" : "",
          "extraFieldItemWid" : "",
          "extraTitle" : "体温",
          "extraFieldItem" : null,
          "isExtraFieldOtherItem" : "0",
          "isAbnormal" : "0"
        },
        {
          "fieldIndex" : 0,
          "extraDesc" : "",
          "extraFieldItemWid" : "",
          "extraTitle" : "身体是否异常",
          "extraFieldItem" : null,
          "isExtraFieldOtherItem" : "0",
          "isAbnormal" : "0"
        }
      ],
      "schoolStatus" : null,
      "stuDormitoryVo" : {
        "sex" : "",
        "area" : "",
        "building" : "",
        "unit" : "",
        "room" : ""
      },
      "grade" : "2019级"
    },
    "extraFieldItemVos" : [

    ],
    "changeTime" : null,
    "taskType" : "4",
    "photograph" : [

    ],
    "signRate" : "1",
    "isPhoto" : 0,
    "signAddress" : null,
    "rateTaskEndTime" : "09:59",
    "taskName" : "一日三签（早签到）",
    "leaveAppUrl" : "\/wec-counselor-leave-apps\/leave\/home\/index.html",
    "isAllowUpdate" : false,
    "signMode" : 0,
    "rateTaskBeginTime" : "05:00",
    "signStatus" : "2",
    "signPlaceSelected" : [
      {
        "address" : "只有野鸡学校才用这个流氓app（北界王西区）",
        "radius" : 520,
        "longitude" : "125.398185",
        "placeWid" : null,
        "creatorUserWid" : null,
        "creatorName" : null,
        "wid" : null,
        "latitude" : "44.001709",
        "currentStatus" : null,
        "isShare" : null,
        "creatorUserId" : null
      },
      {
        "address" : "只有野鸡学校才用这个流氓app（北界王东区）",
        "radius" : 500,
        "longitude" : "125.40993457956229",
        "placeWid" : null,
        "creatorUserWid" : null,
        "creatorName" : null,
        "wid" : null,
        "latitude" : "44.00262969203334",
        "currentStatus" : null,
        "isShare" : null,
        "creatorUserId" : null
      },
      {
        "address" : "只有野鸡学校才用这个流氓app（南界王校区）",
        "radius" : 400,
        "longitude" : "125.296568",
        "placeWid" : null,
        "creatorUserWid" : null,
        "creatorName" : null,
        "wid" : null,
        "latitude" : "43.859918",
        "currentStatus" : null,
        "isShare" : null,
        "creatorUserId" : null
      }
    ],
    "rateSignDate" : "2020-09-10 (周四)",
    "extraField" : [
      {
        "hasOtherItems" : 0,
        "title" : "体温",
        "extraFieldItems" : [
          {
            "isSelected" : null,
            "value" : null,
            "content" : "37.3度以下",
            "isOtherItems" : 0,
            "wid" : 499882,//获取
            "isAbnormal" : false
          },
          {
            "isSelected" : null,
            "value" : null,
            "content" : "37.3度及以上",
            "isOtherItems" : 0,
            "wid" : 499883,
            "isAbnormal" : true
          }
        ],
        "description" : "",
        "wid" : 211814
      },
      {
        "hasOtherItems" : 1,
        "title" : "身体是否异常",
        "extraFieldItems" : [
          {
            "isSelected" : null,
            "value" : null,
            "content" : "健康",
            "isOtherItems" : 0,
            "wid" : 499884,//获取
            "isAbnormal" : false
          },
          {
            "isSelected" : null,
            "value" : null,
            "content" : "感冒",
            "isOtherItems" : 0,
            "wid" : 499885,
            "isAbnormal" : false
          },
          {
            "isSelected" : null,
            "value" : null,
            "content" : "发烧",
            "isOtherItems" : 0,
            "wid" : 499886,
            "isAbnormal" : false
          },
          {
            "isSelected" : null,
            "value" : null,
            "content" : "咳嗽",
            "isOtherItems" : 0,
            "wid" : 499887,
            "isAbnormal" : false
          },
          {
            "isSelected" : null,
            "value" : null,
            "content" : "其它",
            "isOtherItems" : 1,
            "wid" : 499888,
            "isAbnormal" : false
          }
        ],
        "description" : "",
        "wid" : 211815
      }
    ],
    "downloadUrl" : "https:\/\/img.cpdaily.com\/ldy\/index.html"
  },
  "code" : "0"
}
```

获取我需要的两个参数，`体温选项`499882和`身体是否异常选项`499884

## 2.3 模拟提交

最后通过上面步骤获取到的4146,499882,499884以及自定义的地址，进行签到

接口

```html
/wec-counselor-sign-apps/stu/sign/submitSign
```

参数

```json
{
	"abnormalReason": "",
	"position": "你的地址",
	"longitude": 你的经度,
	"isNeedExtra": 1,
	"latitude": 你的纬度,
	"isMalposition": 0,
	"extraFieldItems": [{
			"extraFieldItemWid": 499882,
			"extraFieldItemValue": "37.3度以下"
		},
		{
			"extraFieldItemWid": 499884,
			"extraFieldItemValue": "健康"
		}
	],
	"signInstanceWid": "4146",
	"signPhotoUrl": ""
}
```

返回值

```json
{
  "message" : "SUCCESS",
  "datas" : {
    "signedStuInfo" : null,
    "signInstanceWid" : "4146"
  },
  "code" : "0"
}
```

# 二、运行结果

![](https://meethigher.top/blog/2020/cpdaily-sign/1.png)

# 三、致谢

1. [ZimoLoveShuang](https://github.com/ZimoLoveShuang/auto-sign/)

