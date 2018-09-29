package com.assemble.ad.core;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.assemble.ad.http.CallBackUtil;
import com.assemble.ad.http.UrlHttpUtil;
import com.assemble.ad.ui.CommonBanner;
import com.assemble.ad.util.PlatformUtil;
import com.iclicash.advlib.core.AdRequest;
import com.iclicash.advlib.core.ICliBundle;
import com.iclicash.advlib.core.ICliFactory;
import com.iclicash.advlib.core.ICliUtils;

public class AdvNetRequest {

    private String TAG;
    private Activity mActivity;
    private CommonBanner banner;
    private int advType;
    private int material;
    private int height;
    private int width;
    private ICliFactory mICliFactory;
    private AdRequest mAiclkAdRequest;
    private ApiAdRequest mApiAdRequest;

    public AdvNetRequest(Activity activity) {
        this.mActivity = activity;
        TAG = AdvNetRequest.this.getClass().getSimpleName();
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
            Log.e(TAG, "the request is not bindview banner");
            return;
        }
        if (!(advType == AdvFactory.CONTENT_IMAGE_AND_TEXT
                || advType == AdvFactory.CONTENT_IMAGE_GROUP
                || advType == AdvFactory.CONTENT_PURE_IMAGE)) {
            Log.e(TAG, "advType is error");
            return;
        }
        this.advType = advType;
        this.material = material;
        this.height = height;
        this.width = width;

        new PlatformUtil(mActivity, new PlatformUtil.PlatformListener() {
            @Override
            public void platform(int platform, String name) {
                setAdvPlatform(platform, name);
            }
        });
    }

    private void setAdvPlatform(int platform, String name) {
        banner.setPlatform(platform);
        banner.setAdvType(advType);
        String adslot = null;
        switch (advType) {
            case AdvFactory.CONTENT_IMAGE_AND_TEXT:
                adslot = "7479036";
                break;
            case AdvFactory.CONTENT_IMAGE_GROUP:
                adslot = "7277638";
                break;
            case AdvFactory.CONTENT_PURE_IMAGE:
                adslot = "7112926";
                break;
        }
        if (platform == AdvFactory.PLATFORM_AICLK) {
            //TODO SDK广告
            if (mICliFactory == null) {
                mICliFactory = new ICliFactory(mActivity);
                mAiclkAdRequest = mICliFactory.getADRequest(new ICliUtils.AdContentListener() {
                    @Override
                    public void onContentDelivered(ICliBundle iCliBundle) {
                        if (!TextUtils.isEmpty(iCliBundle.lastError)) {
                            return;
                        }
                        onAiclkShowedReport();
                    }
                });
                banner.setAiclkAdRequest(mAiclkAdRequest);
            }
            banner.addBannerView();
            mAiclkAdRequest.InvokeADV(adslot, material, height, width);
        } else {
            //TODO Api广告
            if (mApiAdRequest == null) {
                mApiAdRequest = new ApiAdRequest(mActivity);
                banner.setApiAdRequest(mApiAdRequest);
            }
            banner.addBannerView();
            mApiAdRequest.InvokeADV(adslot, name, material, height, width);
        }
    }

    /**
     * 曝光后调用此接口
     */
    public void onAiclkShowedReport() {
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
