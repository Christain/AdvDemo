## 接口说明

所有接口都需要传递`query`参数`app_id`与`sign`(占比接口只传`app_id`)，`sign`为接口签名，`app_id`为该SDK的`app_id`。

所有接口都是返回`json`。接口返回状态可以通过`http`状态码或者接口数据中`code`判断。

当接口正常返回时`http`状态码为200，`code`为200。

```json
{
    "code": 200,
    "message": "ok",
    "data": {
        "key": "value"
    }
}
```

当`sign`验证失败时，接口返回`http`状态为401，返回`json`为：

```json
{
    "code": 401,
    "message": "Unauthorized",
    "data": null
}
```

当`app_id`不可用时，接口返回`http`状态为403，返回`json`为：

```json
{
    "code": 403,
    "message": "forbidden",
    "data": null
}
```

当传递的参数信息有误时，接口返回`http`状态为422，返回`json`为：

```json
{
    "code": 422,
    "message": "param is required",
    "data": null
}
```

当请求渠道API发生错误，或者渠道API返回了错误时。接口返回`http`状态为200，返回`json`为：

```json
{
    "code": 400,
    "message": "当前广告位没上线",
    "data": null
}
```

可通过`message`获取错误信息。

## sign方法

将除开`sign`外的`query`与`post`参数，放入字典中，对字典做一个字典排序，依次把所有key value连接成一个字符串，在加上签名的干扰字符串，对整个字符串做一次`hash`。

如请求的url为`{host}/api/ad?sign=xx&app_id=afgasgas`, `post`有一个为`param`的参数，当post参数的值为一个json时，把json当成一个字符串处理,如：

```json
param = {"media":{"app":{"package_name":"InternetRadio.all","app_version":"1.2.3"},"type":1,"site":{"domain":"xx","urls":"xx","title":"xx","keywords":"xx"},"browser":{"user_agent":"xx"}},"client":{"type":3,"version":"1.6.4"},"device":{"id_idfa":"ssss","id_imei":"xxxx","height":1136,"width":640,"brand":"apple","model":"iPhone6","os_version":"1.1.2","os_type":1},"adslot":{"id":"5000188","type":1,"height":100,"width":640},"network":{"type":1,"ip":"218.66.169.71"}}
```

把参数转换成字典，同时把`query`参数`app_id`放进去。转换后的字典为：

```
dict = [
    'param' : '{"media": {"type": 1,"app": {"package_name": "InternetRadio.all","app_version": "1.2.3"},"site":{"domain":"xx","urls":"xx","title":"xx","keywords":"xx"},"browser":{"user_agent":"xx"}}',
    'app_id' : 'afgasgas'
];
```

依次对字典做字典排序，结果如下：

```
dict = [
    'app_id' : 'afgasgas',
    'param' : '{"media": {"type": 1,"app": {"package_name": "InternetRadio.all","app_version": "1.2.3"},"site":{"domain":"xx","urls":"xx","title":"xx","keywords":"xx"},"browser":{"user_agent":"xx"}}',
];
```

依次把`key`,`value`连接起来：

```
app_idafgasgasparam{"media": {"type": 1,"app": {"package_name": "InternetRadio.all","app_version": "1.2.3"},"site":{"domain":"xx","urls":"xx","title":"xx","keywords":"xx"},"browser":{"user_agent":"xx"}}
```

处理干扰字符串`8a0e6abaf53399569cdb2e78a6b8b365`：

做hash：

`f885b3450b08441c50feab28b79e39bd`

翻转：

`db93e97b82baef05c14480b0543b588f`

取前16位放上面字符串的开头，后16位放末尾：

`db93e97b82baef05{"media": {"type": 1,"app": {"package_name": "InternetRadio.all","app_version": "1.2.3"},"site":{"domain":"xx","urls":"xx","title":"xx","keywords":"xx"},"browser":{"user_agent":"xx"}}c14480b0543b588f`

对整个字符串做hash就为sign：`02f40a56a9b388fbc4a551f304abc131`

最后的url为：

`{host}/api/ad?sign=02f40a56a9b388fbc4a551f304abc131&app_id=afgasgas`

## 获取广告接口

`post` `{host}/api/ad?sign=xxx&app_id=xxx`

### 参数

#### header

`Content-Type : application/x-www-form-urlencoded`

#### query参数

`sign` 接口签名

`app_id` 账号ID

#### post参数

`ad_place_id` 媒体广告位ID 

`channel_id` 渠道ID

`channel_ad_place_id` 渠道广告位ID

`param` 请求对应渠道API的参数，为`json`数据。

请求示例参数：

```
ad_place_id = 1，
channel_id =1，
param = {
  "media": {
    "app": {
      "package_name":"InternetRadio.all",
      "app_version":"1.2.3"
    },
    "type":1,
    "site": {
      "domain":"xx",
      "urls":"xx",
      "title":"xx",
      "keywords":"xx"
    },
  "browser": {
    "user_agent":"Mozilla/5.0 (Linux; Android 4.4.2; Nexus 4 Build/KOT49H) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.114 Mobile Safari/537.36"
    }
   },
  "client": {
    "type":3,
    "version":"1.6.4"
  },
  "device": {
    "id_idfa":"ssss",
    "id_imei":"xxxx",
    "height":1136,
    "width":640,
    "brand":"apple",
    "model":"iPhone6",
    "os_version":"1.1.2",
    "os_type": 1
  },
  "adslot": {
    "id":"7112926",
    "type":1,
    "height":100,
    "width":640
  },
  "network": {
    "type":1,
    "ip":"218.66.169.71"
  }
}
```

#### 成功返回

`http code` 为200

返回body:
```json
{
    "code": 200,
    "message": "ok",
    "data": {
        "material_type": 1,
        "html_snippet": "",
        "native_material": {
            "type": 2,
            "interaction_type": 1,
            "image_snippet": "{\"url\":\"http://cdn.aiclk.com//uploads/28a7994bcec1dea054752f399ec03657_1539387_690_360.gif\",\"c_url\":\"http://testrcv.aiclk.com/click?CAAQMg.EOOteWSTZWIo1vdz1a74ZN6zZ537Z57oZN6UZNP9SUS7SaPfPaZ9Sadm1NnP4pepfj6nzqeeonoBlOLI05OAZ5OUZadtOXL3tv9o1hKC1h3tPv3iMu9DMWA_XbrzZNI9ZaPAOaLOjnLIyOdjWDaLOkO5tD4ICsiFd0kp1XagBW7L4G0XONnEjO2QOWE6ucOoQOStXjnXdIrEPicXEiAXQJ3or5LGlUS7Mk9bMv2uFEmVrUrCMTxoluqDMWzbQh3HruczSNZ9ZbnflbHTPN7bSU17Pv2DParo1vSa05Ou05S7PNnmSbIUSTPmlUHC1Wx4lTDoMvAsraou0NjUKTHaMWHaPhSLrUH7wvSTZWIo1vdz1a74ZN6zZ537Z57oZN6UZNP9SUS7SaPfPaZ9Sadm1NnTMTxfFhSHraozrnoIO3IOenrfZNdu0NIU8nXB5TSoraKVMU3HM5ozlhPu8D2aFyKVMU3HM5ozlvSUrHqU0hLO8nzarWSD1W9mMNLzSNXBEWFbrW3a0a6A8nDDrfKDMTgBShLSMTqkMf3oMUmiMTFD1JLlPyS7FhXmrUxu0aKBdTSGQv9bQh3HwNZAS5ZzlNd40JLZPfXVMvHCPTH70a7U8D3VQv9aFJKbFJIq0N6AZJAuSNOAZJLZrf39MWxaMUHC0aIA8DXaMUHCFWHV1vFDr5LbSaOA8DSf1vzTPhKHPv3AMfSiFWHGMaLu8n9H8JXaFJK-rWqf1hIBZfL0PU2gQvKuPh3iMU4qMU9BdvSorTmG1WxgwNdVFaIVFvH7nndOknJ1OjnXdnnIXXOdWOInOXLeEOdtXJD48JnPONeKO6mG8THgMWdGSt4AIEDZQv9m85gnjv97rTqi1EOolajCZagnNTx4FhZnSEXEFvHg1EqlNmjo06niId2ArWzHxUxktUHolb6bSu4bSkOLtoD6N6AgIWziQU6n3UxaQUciIdSLrTqV1tcbSE4Alad4S5rCZNdoIdmGPTHg1tXNPv1DrT7GSNZflaZU0OKEXJD48JDeI5nmZb6zSTIA1aX7SvIASTdb1aSTSaj4ZWZfSbdmZTdb6IO2vwOIPKZ6QOWtOjAIOzIIEOdjXDndIOEkOjzarWZVrUxo1hSoZ5WnXnEuXnnjOEOO0OXOOMLWdjLSZad4laPUladU0t4fZ3OX\",\"width\":690,\"height\":360,\"imp\":[\"http://testrcv.aiclk.com/show?CAAQHw.0vfEndQURdjzM_fkM-8qRhKkR1i8R18zRhKORharQOQ8Q-aCa-RrQ-fHMhxaqGnGCpKxksnnzxzT4vPju1vZR1vOR-fEvyPiE_rzMXbtMXiEa_iA6JrF6dZYyNlkRhjrR-aZv-PvpxxjWvfpdF-Pv8Pl04mTXi1PmUpaeuFAnu4Nxpfqv8vy_xyxoRmvQ3j30vfpFZbJ1pxyfxvYyNlkRhjrR-MTvLPuaCiJRUHmMdV5Shfw2-bTf_QOlUHmMdV5ShfwaCMJXCarcxyT1dQZaO386WVwu-fHRLPjMCQZMdRTQhyT0d3NlU3tBNPHcxHt6Obm2Lim6_otMO38cxwIlOiHlLVNMXjTRWPEaOsA6WQA2dKsRNvzRNfwRhxqcxk-ldHwB_rIB_pTuhMT3dHA6UQzlWQzl-zrQhvZg1jHR1vZcxkN2Lo5M_QmB_qTR-yTfdQmB_rzB_HoMO3Zu-ROR1yTfC2o6dMFlUVFMLymlOozB_stu-bT1UVqldQzlosZ6C2ol-PNcxr-a_kAaWbF2dom6-Hm6WPiaCiJ6_s8M_ZsREHORIHHB_Envipjvijj0vppyyx0jvvqv8jfcLFqcnvdvQvdvv\"],\"clk\":[],\"title\":\"在家用微信赚钱，日赚千元，好羡慕！！！\",\"desc\":\"\"}",
            "text_icon_snippet": "",
            "video_snippet": "",
            "motivate_snippet": "",
            "ext": "{\"sex\":0,\"coin\":0}"
        },
        "bottoming_snippet": ""
    }
}
```

#### 失败返回

`http code` 200

```json
{
    "code": 400,
    "message": "当前广告位没上线",
    "data": null
}
```

## 获取渠道流量占比

`get` `{host}/api/channel/ratio?app_id=xxx`

### query参数

`app_id`账号

### 成功返回

```json
{
    "code": 200,
    "key": "fc2cc3afaf58fb9fc9131f2e385b1784",
    "data": [
        {
            "ad_place_id": 1,                 // 该媒体在在我们平台的广告位ID，
            "channels": [                     // 该媒体对应的多个渠道，但只对应一个渠道的一个广告位
                {
                    "id": 9,                  // 渠道ID
                    "name": "Altenwerth Inc", // 渠道名称
                    "ratio": 33,              // 我们的广告位在该渠道下的占比
                    "method": "api",          // 对接方式
                    "ad_place_id": "170043"   // 该渠道下对应的广告位
                },
                {
                    "id": 3,
                    "name": "Crona-Rogahn",
                    "ratio": 67,
                    "method": "api",
                    "ad_place_id": "183746"
                }
            ]
        },
        {
            "ad_place_id": 2,
            "channels": [
                {
                    "id": 9,
                    "name": "Altenwerth Inc",
                    "ratio": 33,
                    "method": "api",
                    "ad_place_id": "146154"
                },
                {
                    "id": 3,
                    "name": "Crona-Rogahn",
                    "ratio": 23,
                    "method": "api",
                    "ad_place_id": "183746"
                },
                {
                    "id": 4,
                    "name": "Dooley, Franecki and Mosciski",
                    "ratio": 44,
                    "method": "api",
                    "ad_place_id": "135364"
                }
            ]
        }
    ]
}
```

## 提交曝光统计

`post` `{host}/api/statistics/exposure?sign=xxx&app_id=xxx`

### post参数

`ad_place_id` 广告位ID

`channel_id` 渠道ID

`channel_ad_place_id` 渠道广告位ID

### 成功返回

```json
{
    "code": 200,
    "message": "ok",
    "data": null
}
```


## 提交点击统计

`post` `{host}/api/statistics/click?sign=xxx&app_id=xxx`

### post参数

`ad_place_id` 广告位ID

`channel_id` 渠道ID

`channel_ad_place_id` 渠道广告位ID

### 成功返回

```json
{
    "code": 200,
    "message": "ok",
    "data": null
}
```