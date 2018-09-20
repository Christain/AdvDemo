package com.assemble.ad.core;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.widget.Toast;

import com.assemble.ad.http.CallBackUtil;
import com.assemble.ad.http.UrlHttpUtil;
import com.assemble.ad.ui.CommonBanner;
import com.assemble.ad.util.TimeUtil;
import com.iclicash.advlib.core.AdRequest;
import com.iclicash.advlib.core.ICliBundle;
import com.iclicash.advlib.core.ICliFactory;
import com.iclicash.advlib.core.ICliUtils;

public class AdvNetRequest {

    private Activity mActivity;
    private CommonBanner banner;
    private int advType;
    private int material;
    private int height;
    private int width;
    private ICliFactory mICliFactory;
    private AdRequest mAiclkAdRequest;
    private SharedPreferences mSharedPreferences;

    public AdvNetRequest(Activity activity) {
        this.mActivity = activity;
        mSharedPreferences = mActivity.getSharedPreferences("sp_adv_data", Context.MODE_PRIVATE);
    }

    public void bindView(CommonBanner banner) {
        this.banner = banner;
    }

    /**
     * 广告参数
     *
     * @param advType  广告内容类型
     * @param material 物料类型(html、native)
     * @param height   广告位高度
     * @param width    广告位宽度
     */
    public void InvokeADV(int advType, int material, int height, int width) {
        if (banner == null) {
            Toast.makeText(mActivity, "此请求未绑定Banner对象", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!(advType == AdvFactory.CONTENT_IMAGE_AND_TEXT
                || advType == AdvFactory.CONTENT_IMAGE_GROUP
                || advType == AdvFactory.CONTENT_PURE_IMAGE
                || advType == AdvFactory.CONTENT_VIDEO)) {
            Toast.makeText(mActivity, "广告类型错误", Toast.LENGTH_SHORT).show();
            return;
        }
        this.advType = advType;
        this.material = material;
        this.height = height;
        this.width = width;

        String cacheTime = mSharedPreferences.getString("TIME", "");
        final String nowTime = TimeUtil.getFormatedTime("yyyy-MM-dd", System.currentTimeMillis());
        if (cacheTime.equals(nowTime)) {
            randomRoute();
        } else {
            //TODO 接口获取最新的广告占比
            UrlHttpUtil.get("https://www.baidu.com", new CallBackUtil.CallBackString() {
                @Override
                public void onFailure(int code, String errorMessage) {
                    randomRoute();
                }

                @Override
                public void onResponse(String response) {
                    mSharedPreferences.edit().putString("TIME", nowTime).apply();
                    mSharedPreferences.edit().putInt("PERCENT", 10).apply();
                    randomRoute();
                }
            });
        }
    }

    private void randomRoute() {
        int random = (int) (Math.random() * 10);
        if (mSharedPreferences.getInt("PERCENT", 10) >= random) {
            setAdvPlatform(AdvFactory.PLATFORM_AICLK);
        } else {
            setAdvPlatform(AdvFactory.PLATFORM_OTHER);
        }
    }

    private void setAdvPlatform(int platform) {
        banner.setPlatform(platform);
        banner.setAdvType(advType);
        if (platform == AdvFactory.PLATFORM_AICLK) {
            if (mICliFactory == null) {
                mICliFactory = new ICliFactory(mActivity);
                mAiclkAdRequest = mICliFactory.getADRequest(new ICliUtils.AdContentListener() {
                    @Override
                    public void onContentDelivered(ICliBundle iCliBundle) {
                        if (!TextUtils.isEmpty(iCliBundle.lastError)) {
                            return;
                        }
                        onShowedReport();
                    }
                });
                banner.setAiclkAdRequest(mAiclkAdRequest);
            }
            banner.addBannerView();
            String adslot = null;
            switch (advType) {
                case AdvFactory.CONTENT_IMAGE_AND_TEXT:
                    adslot = "7479036";
                    break;
                case AdvFactory.CONTENT_IMAGE_GROUP:
                    adslot = "7277638";
                    break;
                case AdvFactory.CONTENT_PURE_IMAGE:
                    adslot = "7668584";
                    break;
                case AdvFactory.CONTENT_VIDEO:
                    adslot = "7031293";
                    break;
            }
            mAiclkAdRequest.InvokeADV(adslot, material, height, width);
        } else {
            //TODO 其他平台广告
        }
    }

    /**
     * 曝光后调用此接口
     */
    public void onShowedReport() {
        UrlHttpUtil.get("https://www.baidu.com", new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(int code, String errorMessage) {
                Toast.makeText(mActivity, "广告显示回调失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                Toast.makeText(mActivity, "广告显示回调成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public ICliFactory getICliFactory() {
        return mICliFactory;
    }
}
