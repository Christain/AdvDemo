package com.assemble.ad.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.assemble.ad.bean.AdvBean;
import com.assemble.ad.http.CallBackUtil;
import com.assemble.ad.http.UrlHttpUtil;
import com.assemble.ad.ui.ApiBanner;
import com.assemble.ad.util.AdvParamUtil;
import com.assemble.ad.util.AppUtil;
import com.assemble.ad.util.MD5Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ApiAdRequest implements ApiRequest {

    private String TAG;
    private Context mContext;
    private ApiBanner banner;
    private String adslot;
    private String placeId;
    private String channelId;
    private int material;
    private SharedPreferences mSharedPreferences;

    public ApiAdRequest(Context context) {
        this.mContext = context;
        this.TAG = ApiAdRequest.this.getClass().getSimpleName();
        this.mSharedPreferences = mContext.getSharedPreferences("sp_adv_data", Context.MODE_PRIVATE);
    }

    @Override
    public void bindView(ApiBanner banner) {
        this.banner = banner;
    }

    @Override
    public void InvokeADV(String adslot, String placeId, String channelId, int material, int height, int width) {
        this.adslot = adslot;
        this.placeId = placeId;
        this.channelId = channelId;
        this.material = material;
        String app_id = AppUtil.getAppMetaData(mContext, "union_app_id");
        String jsonParam = AdvParamUtil.ParamJson(mContext, placeId, material, height, width);
        HashMap<String, String> params = new HashMap<>();
        params.put("param", jsonParam);
        params.put("ad_place_id", adslot);
        params.put("channel_id", channelId);
        params.put("channel_ad_place_id", placeId);

        StringBuilder sb = new StringBuilder();
        sb.append("http://union.51tui666.com" + "/api/ad");
        sb.append("?sign=");

        String sign = "ad_place_id" + adslot
                + "app_id" + app_id
                + "channel_ad_place_id" + placeId
                + "channel_id" + channelId
                + "param" + jsonParam;
        String keycode = mSharedPreferences.getString("KEY", "");
        keycode = MD5Util.getInstance().getMD5String(keycode);
        keycode = new StringBuilder(keycode).reverse().toString();
        sign = keycode.substring(0, 16) + sign + keycode.substring(keycode.length() - 16, keycode.length());
        sign = MD5Util.getInstance().getMD5String(sign);

        sb.append(sign);
        sb.append("&app_id=");
        sb.append(app_id);
        UrlHttpUtil.post(sb.toString(), params, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(int code, String errorMessage) {
                if (code == 401) {
                    mSharedPreferences.edit().putString("TIME", "").apply();
                }
                Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    int code = object.optInt("code");
                    if (code == 200) {
                        AdvBean bean = new AdvBean(object.optString("data"));
                        banner.ApiUpdateView(bean);
                    } else {
                        if (code == 401) {
                            mSharedPreferences.edit().putString("TIME", "").apply();
                        }
                        String message = object.optString("message", "");
                        Toast.makeText(mContext, "code=" + code + "   error=" + message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onApiShowedReport() {
        advStatistics("/api/statistics/exposure", 0);
    }

    @Override
    public void onApiClickedReport() {
        advStatistics("/api/statistics/click", 1);
    }

    private void advStatistics(String url, final int type) {
        HashMap<String, String> params = new HashMap<>();
        params.put("ad_place_id", adslot);
        params.put("channel_id", channelId);
        params.put("channel_ad_place_id", placeId);

        String app_id = AppUtil.getAppMetaData(mContext, "union_app_id");

        StringBuilder sb = new StringBuilder();
        sb.append("http://union.51tui666.com" + url);
        sb.append("?sign=");

        String sign = "ad_place_id" + adslot
                + "app_id" + app_id
                + "channel_ad_place_id" + placeId
                + "channel_id" + channelId;
        String keycode = mSharedPreferences.getString("KEY", "");
        keycode = MD5Util.getInstance().getMD5String(keycode);
        keycode = new StringBuilder(keycode).reverse().toString();
        sign = keycode.substring(0, 16) + sign + keycode.substring(keycode.length() - 16, keycode.length());
        sign = MD5Util.getInstance().getMD5String(sign);

        sb.append(sign);
        sb.append("&app_id=");
        sb.append(app_id);
        UrlHttpUtil.post(sb.toString(), params, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(int code, String errorMessage) {
                if (code == 401) {
                    mSharedPreferences.edit().putString("TIME", "").apply();
                }
                Log.e(TAG, errorMessage);
            }

            @Override
            public void onResponse(String response) {

            }
        });
    }
}
