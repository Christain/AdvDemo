package com.assemble.ad.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import com.assemble.ad.core.AdvFactory;
import com.assemble.ad.core.ApiRequest;
import com.assemble.ad.http.CallBackUtil;
import com.assemble.ad.http.UrlHttpUtil;
import com.assemble.ad.util.AppUtil;
import com.assemble.ad.util.MD5Util;
import com.iclicash.advlib.core.AdRequest;
import com.iclicash.advlib.ui.banner.ADBanner;

import java.util.HashMap;

public class NativeBanner extends LinearLayout implements CommonBanner {

    private String TAG;
    private Context mContext;
    private AttributeSet attrs;
    private int defStyleAttr;
    private int platform;
    private int advType;

    private AdRequest mAiclkAdRequest;
    private ADBanner mAiclkADBanner;

    private ApiRequest mApiAdRequest;
    private ApiAdBanner mApiAdBanner;

    public NativeBanner(Context context) {
        super(context);
        initView(context, null, 0);
    }

    public NativeBanner(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0);
    }

    public NativeBanner(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this.TAG = NativeBanner.this.getClass().getSimpleName();
        this.mContext = context;
        this.attrs = attrs;
        this.defStyleAttr = defStyleAttr;
    }

    @Override
    public void setPlatform(int platform) {
        this.platform = platform;
    }

    @Override
    public void setAdvType(int advType) {
        this.advType = advType;
    }

    @Override
    public void addBannerView() {
        removeAllViews();
        if (platform == AdvFactory.PLATFORM_AICLK) {
            if (mAiclkADBanner == null) {
                mAiclkADBanner = new ADBanner(mContext, attrs, defStyleAttr);
            }
            addView(mAiclkADBanner);
            mAiclkAdRequest.bindView(mAiclkADBanner);
        } else {
            if (mApiAdBanner == null) {
                mApiAdBanner = new ApiAdBanner(mContext, attrs, defStyleAttr);
                mApiAdBanner.setApiRequest(mApiAdRequest);
            }
            mApiAdBanner.setAdvType(advType);
            addView(mApiAdBanner);
            mApiAdRequest.bindView(mApiAdBanner);
        }
    }

    @Override
    public void setAiclkAdRequest(AdRequest adRequest) {
        this.mAiclkAdRequest = adRequest;
    }

    @Override
    public void setApiAdRequest(ApiRequest apiAdRequest) {
        this.mApiAdRequest = apiAdRequest;
    }

    @Override
    public void onAiclkShowedReport(String adslot, String placeId, String channelId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("ad_place_id", adslot);
        params.put("channel_id", channelId);
        params.put("channel_ad_place_id", placeId);

        String app_id = AppUtil.getAppMetaData(mContext, "union_app_id");

        StringBuilder sb = new StringBuilder();
        sb.append("http://union.51tui666.com" + "/api/statistics/exposure");
        sb.append("?sign=");

        String sign = "ad_place_id" + adslot
                + "app_id" + app_id
                + "channel_ad_place_id" + placeId
                + "channel_id" + channelId;
        SharedPreferences sp = mContext.getSharedPreferences("sp_adv_data", Context.MODE_PRIVATE);
        String keycode = sp.getString("KEY", "");
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
                    SharedPreferences sp = mContext.getSharedPreferences("sp_adv_data", Context.MODE_PRIVATE);
                    sp.edit().putString("TIME", "").apply();
                }
                Log.e(TAG, "error=" + errorMessage);
            }

            @Override
            public void onResponse(String response) {

            }
        });
    }
}
