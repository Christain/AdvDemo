package com.assemble.ad.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.assemble.ad.Constants;
import com.assemble.ad.bean.RatioBean;
import com.assemble.ad.core.AdvFactory;
import com.assemble.ad.http.CallBackUtil;
import com.assemble.ad.http.UrlHttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PlatformUtil {

    private SharedPreferences mSharedPreferences;
    private PlatformListener mListener;

    public PlatformUtil(Activity activity, PlatformListener listener) {
        this.mSharedPreferences = activity.getSharedPreferences("sp_adv_data", Context.MODE_PRIVATE);
        this.mListener = listener;
        checkPlatform();
    }

    private void checkPlatform() {
        String cacheTime = mSharedPreferences.getString("TIME", "");
        final String nowTime = TimeUtil.getFormatedTime("yyyy-MM-dd", System.currentTimeMillis());
        if (cacheTime.equals(nowTime)) {
            randomRatio();
        } else {
            //TODO 接口获取最新的广告占比
            UrlHttpUtil.get(Constants.HOST + "/api/channel/ratio", new CallBackUtil.CallBackString() {
                @Override
                public void onFailure(int code, String errorMessage) {
                    Log.e("PlatformUtil", "code=" + code + "  error=" + errorMessage);
                    randomRatio();
                }

                @Override
                public void onResponse(String response) {
                    response = "{\"code\":200,\"data\":[{\"id\":1,\"name\":\"Fahey, Rosenbaum and Rutherford\",\"ratio\":78},{\"id\":2,\"name\":\"Wolff-Schmitt\",\"ratio\":22}]}";
                    try {
                        JSONObject object = new JSONObject(response);
                        int code = object.optInt("code");
                        if (code == 200) {
                            mSharedPreferences.edit().putString("TIME", nowTime).apply();
                            mSharedPreferences.edit().putString("RATIO_DATA", object.optString("data", "")).apply();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        randomRatio();
                    }
                }
            });
        }
    }

    private void randomRatio() {
        String ratioData = mSharedPreferences.getString("RATIO_DATA", "");
        int total_ratio = 0;
        ArrayList<RatioBean> list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(ratioData);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                RatioBean bean = new RatioBean();
                bean.setId(obj.optInt("id"));
                bean.setName(obj.optString("name"));
                bean.setRatio(obj.optInt("ratio"));
                list.add(bean);
                total_ratio = total_ratio + bean.getRatio();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (list.size() == 0) {
            mListener.platform(AdvFactory.PLATFORM_AICLK);
            return;
        }
        int random = (int) (Math.random() * total_ratio);
        int ratio = 0;
        for (int i = 0; i < list.size(); i++) {
            ratio = ratio + list.get(i).getRatio();
            if (random <= ratio) {
                switchPlatform(list.get(i).getId());
                return;
            }
        }
    }

    private void switchPlatform(int id) {
        if (mListener == null) {
            return;
        }
        switch (id) {
            case 1:
                mListener.platform(AdvFactory.PLATFORM_AICLK);
                break;
            case 2:
                mListener.platform(AdvFactory.PLATFORM_OTHER);
                break;
             default:
                 mListener.platform(AdvFactory.PLATFORM_AICLK);
                 break;
        }
    }

    public interface PlatformListener {
        void platform(int platform);
    }
}
