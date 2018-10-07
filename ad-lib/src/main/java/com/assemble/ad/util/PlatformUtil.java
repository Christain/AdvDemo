package com.assemble.ad.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.widget.Toast;

import com.assemble.ad.bean.RatioBean;
import com.assemble.ad.core.AdvFactory;
import com.assemble.ad.http.CallBackUtil;
import com.assemble.ad.http.UrlHttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class PlatformUtil {

    private Activity mActivity;
    private SharedPreferences mSharedPreferences;
    private PlatformListener mListener;
    private String adslot;

    public PlatformUtil(Activity activity, String adslot, PlatformListener listener) {
        this.mActivity = activity;
        this.mSharedPreferences = activity.getSharedPreferences("sp_adv_data", Context.MODE_PRIVATE);
        this.mListener = listener;
        this.adslot = adslot;
        checkPlatform();
    }

    private void checkPlatform() {
        String cacheTime = mSharedPreferences.getString("TIME", "");
        String ratioData = mSharedPreferences.getString("RATIO_DATA", "");
        String key = mSharedPreferences.getString("KEY", "");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String nowTime = dateFormat.format(System.currentTimeMillis());
        if (cacheTime.equals(nowTime) && !TextUtils.isEmpty(ratioData) && !TextUtils.isEmpty(key)) {
            randomRatio(ratioData, key);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("http://union.51tui666.com" + "/api/channel/ratio");
            sb.append("?app_id=");
            String app_id = AppUtil.getAppMetaData(mActivity, "union_app_id");
            sb.append(app_id);
            UrlHttpUtil.get(sb.toString(), new CallBackUtil.CallBackString() {
                @Override
                public void onFailure(int code, String errorMessage) {
                    Toast.makeText(mActivity, errorMessage, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject object = new JSONObject(response);
                        int code = object.optInt("code");
                        if (code == 200) {
                            String key = object.optString("key");
                            mSharedPreferences.edit().putString("KEY", key).apply();
                            mSharedPreferences.edit().putString("TIME", nowTime).apply();
                            String ratioData = object.optString("data", "");
                            mSharedPreferences.edit().putString("RATIO_DATA", ratioData).apply();

                            randomRatio(ratioData, key);
                        } else {
                            Toast.makeText(mActivity, object.optString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void randomRatio(String ratioData, String key) {
        if (TextUtils.isEmpty(ratioData) || TextUtils.isEmpty(key)) {
            Toast.makeText(mActivity, "ratio data is null", Toast.LENGTH_SHORT).show();
            return;
        }
        RatioBean bean = null;
        try {
            JSONArray array = new JSONArray(ratioData);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                RatioBean ratio = new RatioBean(obj.toString());
                if (ratio.ad_place_id.equals(adslot)) {
                    bean = ratio;
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (bean == null || bean.channels == null || bean.channels.size() == 0) {
            Toast.makeText(mActivity, "channels is null", Toast.LENGTH_SHORT).show();
            return;
        }
        int random = (int) (Math.random() * 100);
        int ratio = 0;
        for (int i = 0; i < bean.channels.size(); i++) {
            ratio = ratio + bean.channels.get(i).ratio;
            if (random <= ratio) {
                switchPlatform(bean.channels.get(i).method, bean.channels.get(i).ad_place_id, bean.channels.get(i).id);
                return;
            }
        }
    }

    private void switchPlatform(String method, String plactId, String id) {
        if (mListener == null) {
            return;
        }
        switch (method) {
            case "sdk":
                mListener.platform(AdvFactory.PLATFORM_AICLK, plactId, id);
                break;
            case "api":
                mListener.platform(AdvFactory.PLATFORM_API, plactId, id);
                break;
            default:
                Toast.makeText(mActivity, "method=" + method + "is not support", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public interface PlatformListener {
        void platform(int platform, String placeId, String channelId);
    }
}
