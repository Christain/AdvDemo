package com.assemble.ad.core;

import android.content.Context;
import android.util.Log;

import com.assemble.ad.Constants;
import com.assemble.ad.http.CallBackUtil;
import com.assemble.ad.http.UrlHttpUtil;
import com.assemble.ad.ui.ApiBanner;
import com.assemble.ad.util.ParamUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class ApiAdRequest {

    private String TAG;
    private Context mContext;
    private ApiBanner banner;
    private String adslot;
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
     * @param material 物料类型(html、native)
     * @param height   广告位高度
     * @param width    广告位宽度
     */
    public void InvokeADV(String adslot, int material, int height, int width) {
        this.adslot = adslot;
        this.material = material;
        String param = ParamUtil.ParamJson(mContext, adslot, material, height, width);
        UrlHttpUtil.postJson(Constants.HOST + "/api/ad", param, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(int code, String errorMessage) {
                Log.e(TAG, "code=" + code + "   error=" + errorMessage);
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