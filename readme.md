## 接口说明

所有接口都需要传递`query`参数`sign`与`app_id`，`sign`为接口签名，`app_id`为账号。

所有接口都是返回`json`。接口返回状态可以通过`http`状态码或者接口数据中`code`判断。

当接口正常返回时`http`状态码为200，`code`为200.

```json
{
    "code": 200,
    "message": "ok",
    "data": [
      ....
    ]
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

当`app_id`账号不可用时，接口返回`http`状态为403，返回`json`为：

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

可通过`message`获取错误信息。

## sign方法


## 获取广告接口

`post` `{host}/api/ad?sign=xxx&app_id=xxx&ssp=aiclk`

### header

`Content-Type: application/json`

### 参数

#### query参数

`sign`接口签名

`app_id`账号ID

`channel`ssp平台

#### post参数

`post`为`json`数据:

```json
{
	"param": {                   // param为对应请求SSP API的参数，每个API都可能不相同
		"media": {
			"type": 1,
			"app": {
				"package_name": "InternetRadio.all",
				"app_version": "1.2.3"
			},
			"site":{
				"domain":"xx",
				"urls":"xx",
				"title":"xx",
				"keywords":"xx"
			},
			"browser":{
				"user_agent":"xx"
			}
		},  
		"client": {
			"type": 3,
			"version":"1.6.4"
		},
		"device": {
			"id_idfa":"ssss",
			"id_imei":"xxxx",
			"height": 1136,
			"width": 640,
			"brand": "apple",
			"model": "iPhone6",
			"os_version": "1.1.2",
			"os_type": 1
		},
		"adslot": {
			"id": "5000188",
			"type": 1,
			"height": 100,
			"width": 640
		},
		"network": {
			"type": 1,
			"ip": "218.66.169.71"
		}
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
        "ads": {
            "material_type": 0,
            "html_snippet": "<!DOCTYPE html><html lang=\"en\"\nstyle=\"height:100%;\"><head><meta charset=\"UTF-8\"><meta name=\"apple-touchfullscreen\"\ncontent=\"YES\" /><meta name=\"format-detection\" content=\"telephone=no\"\n/><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimumscale=1.0,\nmaximum-scale=1.0,user-scalable=no\"><meta name=\"apple-mobile-web-appcapable\"\ncontent=\"yes\" /><meta name=\"apple-mobile-web-app-status-bar-style\"\ncontent=\"black\" /><meta name=\"viewport\"/><title></title><style>#picBox img\n{width: 100%;}.i_gx3_img_div {-webkit-box-flex: 1;-ms-flex: 1;flex: 1;\nmargin-right: .1rem; box-sizing: border-box; -webkit-box-sizing: borderbox;padding:\n.22rem 0;margin: 0;position: relative; -webkit-tap-highlight-color:\nrgba(231,231,231,0.6);}.i_gx3_img_div {marginright:\n.2rem;}</style></head><body style=\"height:100%;margin: 0;box-sizing: borderbox;\"><div\nclass=\"single\" style=\"<!-- height:100% -->\" id=\"i-container\"><a id=\"aBox\"\nstyle=\"padding: 5px 0;text-decoration: none;height: 100%;overflow: hidden;display: -webkitbox;border-bottom:\n1px solid #f2f2f2;-webkit-tap-highlight-color: transparent;-webkit-taphighlight-color:\ntransparent;box-sizing: border-box;\"\nhref=\"http://yuan.51oneone.com?iclicashsid=84c6351be8f389c882616d90834e4b243d18d262\\\n\" target=\"_top\"><div id=\"textBox\" style=\"-webkit-box-flex: 1;height: 100%;position:\nrelative;display: -webkit-box;display: -moz-box;display: -ms-flexbox;display: -webkitflex;display:flex;align-items:\ncenter;\"><h3 id=\"title\" style=\"font-size: 16px;color: #000;lineheight:\n24px;height: 44px;overflow: hidden;font-weight: normal;margin:0;color:\n#2b2b2b;\"></h3><p style=\" height: 15px;padding-top: 4px;position: absolute;bottom:\n0;right: 0;margin:0;\"><span style=\"float: right;font-size: 11px;color: #999;\"> 广 告\n</span></p></div><div id=\"picBox\" style=\"width: 90px;margin-left: 15px;position:\nrelative;display: -webkit-box;display: -moz-box;display: -ms-flexbox;display: -webkitflex;display:flex;align-items:\ncenter;\"><img style=\"width: 100%;\"\nsrc=\"\"></div></a></div><div class=\"multi\" style=\"<!-- height:100% -->\" id=\"icontainer\"><a\nid=\"aBox\" style=\"padding: 5px 0;text-decoration: none;height:\n100%;overflow: hidden;border-bottom: 1px solid #f2f2f2;-webkit-tap-highlight-color:\ntransparent;-webkit-tap-highlight-color: transparent;box-sizing: border-box;display:\nblock;position:relative;\"\nhref=\"http://yuan.51oneone.com?iclicashsid=84c6351be8f389c882616d90834e4b243d18d262\\\n\" target=\"_top\"><div id=\"textBox\" style=\"-webkit-box-flex: 1;position: relative;display: -\n14\nwebkit-box;display: -moz-box;display: -ms-flexbox;display: -webkit-flex;display:flex;align-items:\ncenter;\"><h3 id=\"title\" style=\"font-size: 16px;color: #000;line-height: 34px;height:\n34px;overflow: hidden;font-weight: normal;margin:0;color: #2b2b2b;\"></h3></div><div\nid=\"picBox\" style=\"position: relative;display: -webkit-box;display: -moz-box;display: -msflexbox;display:\n-webkit-flex;display:flex;align-items: center;\"><figure\nclass=\"i_gx3_img_div\"><img src=\"\"></figure><figure class=\"i_gx3_img_div\"><img\nsrc=\"\"></figure><figure class=\"i_gx3_img_div\" style=\"margin-right:0;\"><img\nsrc=\"\"></figure></div><p id=\"adText\" style=\"height: 15px;padding-top:\n4px;margin:0;\"><span style=\"float: right;font-size: 11px;color: #999;\"> 广 告\n</span></p></a></div><script>(function (w) { var d =\ndocument.documentElement; var globalBody = document.body; var f = 'currentFontSize';\nvar attr = 'setAttribute'; var b = 10; var W = 320; var t = (w.Modernizr && Modernizr.touch\n=== true) || (function () { return !!(('ontouchstart' in w) || w.DocumentTouch && document\ninstanceof DocumentTouch); })(); function setFontSize() { var fs = d.clientWidth / W *\nb; fs === d[f] || (d.style.fontSize = fs + 'px', d[f] = fs); } setFontSize();\nwindow.addEventListener('resize', setFontSize);function log(url) { var img =\ndocument.createElement('img'); img[attr]('src', url); img[attr]('style', 'display:none;');\nglobalBody.appendChild(img); } function cl(bindTarget) { var focusing = false;\nbindTarget.addEventListener(t ? 'touchstart' : 'mousedown', function (event) {\nfocusing = true; }); bindTarget.addEventListener(t ? 'touchmove' : 'mousemove',\nfunction (event) { focusing = false; });\nbindTarget.addEventListener(t ? 'touchend' : 'mouseup', function (event) { if\n(!focusing) { return false; } focusing = false; if\n(cl.logs && cl.logs.length) { cl.logs.forEach(log); cl.logs =\nnull; } }); } w.regClickLog = function (logs) { cl.logs = (cl.logs\n|| []).concat(logs); }; var i = document.getElementById('i-container'); i &&\ncl(i);}(window));window.regClickLog &&\nwindow.regClickLog([\"http://clickc.admaster.com.cn/c/a89643,b1853655,c3217,i0,m10\n1,8a2,8b3,0a__1__,n__MAC__,z__ssss__,f__IP__,t__TS__,0c____,o__OPENUDID__,h\",\"http://\n180.169.79.250:8899/click?CAAQMQ.zNNTdOrlh-h-eEiVcE_CU-rSh-rJU8hXeCmSUOreFBlh8glUqmXwFmoe8ghXdW_oKBrXjddlrlsFrHgwONKUONKUOPTNuHNdrhXUONKwENKNEHNm\nrrg9NPmF_8HNnHyzPUmnmPhlKPHlrUJNlNukruTuNruPiuVEKGexaylLOH7WvRAhkJ6eEi7DC57DCB\n6hqjQMqR8DFR8hYeHLqRnMErlh-h-eEiVcE_CU-rSh-rJU8hXeCmSUOreFBlh8glUqmXwFmoe83ouNruPrusOqeqLCA7cF5ZMkeqLRjqUYH3hCRncFUsUENKfrQGD9yRLRyoc\n-HXUgguNgHumrrzPrrgNuNNFNNrNuHdzNUTuae-LvUhNErNmry-Lve-TVNSUUqck3CU8BKwO2nwEi8wOPJckBqcERRh-gSc8_8UcguONrOPrrgNuNNFNNrNdguOlXuBiyMBzATEuOwicwTdNFNN\",]);</script><img\nstyle=\"display:none\"\nsrc=\"http://v.admaster.com.cn/i/a89643,b1853655,c3217,i0,m202,8a2,8b3,0a__1__,n__MAC__,\nz__ssss__,f__IP__,t__TS__,0c____,o__OPENUDID__,h\"><img style=\"display:none\"\nsrc=\"http://180.169.79.250:8899/show?CAAQEw.9j_8qAHQ-X-X1SYhcSxRtXHl-XH0tbC1RmltAHX1BLQ-b6QtTmCyBmD1b6-CqWxDKLHCwqqQHQZBHG6yAjKtAjKtA_8jkGjqHCtAjKySjKjSGjmHH6Oj_mBxbGj5Gf9_tm5m_-\nQK_GQHt0jQjkLHm6j3kmeHm6j36jUHwbrOau2T3d2AMbrOaErbYZ97agcB3byb_KtnGW47lQczaL\neReZtS9qj3m6jx669jjmjkHj6jj0j_6_ep1XeZjBjj\"><script>var infoData = {typePos: \n15\n5, title: \" 看 看 你 的 手 机 号 ， 能 借 多 少 钱 ？ \",\nimgUrl:['http://cdn.iclicash.com/uploads/26115ec51f44d6ab6dfc345801274b69_67_225_150.jpg\n']};</script><script\nsrc=\"//cdn.iclicash.com/htmljs/bda4bfcb7979dc043b8178cb822a5bc3.js\"></script>\n</body>",
            "native_material": {
                "type": 0,
                "interaction_type": 1,
                "image_snippet": "",
                "text_icon_snippet": "",
                "video_snippet": ""
            },
            "search_id": "84c6351be8f389c882616d90834e4b243d18d262"
        }
    }
}
```

## 获取渠道流量占比

`get` `{host}/api/channel/ratio?sign=xxx&app_id=xxx`

### query参数

`sign`接口签名

`app_id`账号

### 成功返回

```json
{
    "code": 200,
    "data": [
        {
            "id": 1, // 渠道ID
            "name": "Fahey, Rosenbaum and Rutherford", // 渠道名称
            "ratio": 22, //占比
            "method": "api" //对接方式
        },
        {
            "id": 2,
            "name": "Wolff-Schmitt",
            "ratio": 34,
            "method": "material"
        },
        {
            "id": 3,
            "name": "Gutmann-Herzog",
            "ratio": 44,
            "method": "api"
        }
    ]
}
```

## 提交统计

`post` `{host}/api/statistics?sign=xxx&app_id=xxx`

### post参数

### 成功返回

```json
{
    "code": 200,
    "message": "ok",
    "data": null
}
```
