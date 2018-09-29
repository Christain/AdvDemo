package com.assemble.ad.core;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.assemble.ad.Constants;
import com.assemble.ad.http.CallBackUtil;
import com.assemble.ad.http.UrlHttpUtil;
import com.assemble.ad.ui.ApiBanner;
import com.assemble.ad.util.AppUtil;
import com.assemble.ad.util.ParamUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ApiAdRequest {

    private String TAG;
    private Context mContext;
    private ApiBanner banner;
    private String adslot;
    private String name;
    private int material;

    public ApiAdRequest(Context context) {
        this.mContext = context;
        this.TAG = ApiAdRequest.this.getClass().getSimpleName();
    }

    public void bindView(ApiBanner banner) {
        this.banner = banner;
    }

    /**
     * 广告参数
     *
     * @param adslot   广告位ID
     * @param name     广告渠道名称
     * @param material 物料类型(html、native)
     * @param height   广告位高度
     * @param width    广告位宽度
     */
    public void InvokeADV(String adslot, String name, int material, int height, int width) {
        this.adslot = adslot;
        this.name = name;
        this.material = material;
        String app_id = AppUtil.getAppMetaData(mContext, "union_app_id");
        if (TextUtils.isEmpty(app_id)) {
            Toast.makeText(mContext, "Plaese add app_id int AndroidManifest.xml", Toast.LENGTH_SHORT).show();
            return;
        }
        String jsonParam = ParamUtil.ParamJson(mContext, adslot, material, height, width);
        HashMap<String, String> params = new HashMap<>();
        params.put("json", jsonParam);

        StringBuilder sb = new StringBuilder();
        sb.append(Constants.HOST + "/api/ad");
        sb.append("?sign=");
        sb.append("sign");
        sb.append("&app_id=");
        sb.append(app_id);
        sb.append("&ssp=");
        sb.append(name);
        UrlHttpUtil.post(sb.toString(), params, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(int code, String errorMessage) {
                Log.e(TAG, "error=" + errorMessage);
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    int code = object.optInt("code");
                    if (code == 200) {
                        banner.ApiUpdateView(new ApiBundle());
                    } else {
                        String message = object.optString("message", "");
                        Log.e(TAG, "code=" + code + "   error=" + message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 曝光后调用此接口
     */
    public void onApiShowedReport() {
        UrlHttpUtil.get("https://www.baidu.com", new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(int code, String errorMessage) {

            }

            @Override
            public void onResponse(String response) {

            }
        });
    }

    /**
     * 点击后调用此接口
     */
    public void onApiClickedReport() {
        UrlHttpUtil.get("https://www.baidu.com", new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(int code, String errorMessage) {

            }

            @Override
            public void onResponse(String response) {

            }
        });
    }
}
