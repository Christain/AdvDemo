package com.assemble.ad.core;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.Toast;

import com.assemble.ad.ui.CommonBanner;
import com.assemble.ad.util.AppUtil;
import com.assemble.ad.util.PlatformUtil;
import com.iclicash.advlib.core.AdRequest;
import com.iclicash.advlib.core.ICliBundle;
import com.iclicash.advlib.core.ICliFactory;
import com.iclicash.advlib.core.ICliUtils;

public class AdvNetRequest {

    private Activity mActivity;
    private CommonBanner banner;
    private String adslot;
    private int material;
    private int height;
    private int width;
    private ICliFactory mICliFactory;
    private AdRequest mAiclkAdRequest;
    private ApiRequest mApiAdRequest;

    public AdvNetRequest(Activity activity) {
        this.mActivity = activity;
    }

    public void bindView(CommonBanner banner) {
        this.banner = banner;
    }

    public void InvokeADV(String adslot, int height, int width) {
        if (banner == null) {
            Toast.makeText(mActivity, "the request is not bindview banner", Toast.LENGTH_SHORT).show();
            return;
        }
        String app_id = AppUtil.getAppMetaData(mActivity, "union_app_id");
        if (TextUtils.isEmpty(app_id)) {
            Toast.makeText(mActivity, "Plaese add app_id int AndroidManifest.xml", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(adslot)) {
            Toast.makeText(mActivity, "adslot is null", Toast.LENGTH_SHORT).show();
            return;
        }
        this.adslot = adslot;
        this.material = 1;
        this.height = height;
        this.width = width;

        new PlatformUtil(mActivity, adslot, new PlatformUtil.PlatformListener() {
            @Override
            public void platform(int platform, String placeId, String channelId) {
                setAdvPlatform(platform, placeId, channelId);
            }
        });
    }

    private void setAdvPlatform(int platform, final String placeId, final String channelId) {
        banner.setPlatform(platform);
        if (platform == AdvFactory.PLATFORM_AICLK) {
            if (mICliFactory == null) {
                mICliFactory = new ICliFactory(mActivity);
                mAiclkAdRequest = mICliFactory.getADRequest(new ICliUtils.AdContentListener() {
                    @Override
                    public void onContentDelivered(ICliBundle iCliBundle) {
                        if (!TextUtils.isEmpty(iCliBundle.lastError)) {
                            return;
                        }
                        banner.onAiclkShowedReport(adslot, placeId, channelId);
                    }
                });
                banner.setAiclkAdRequest(mAiclkAdRequest);
            }
            banner.addBannerView();
            mAiclkAdRequest.InvokeADV(placeId, material, height, width);
        } else {
            if (mApiAdRequest == null) {
                mApiAdRequest = new ApiAdRequest(mActivity);
                banner.setApiAdRequest(mApiAdRequest);
            }
            banner.addBannerView();
            mApiAdRequest.InvokeADV(adslot, placeId, channelId, material, height, width);
        }
    }

    public ICliFactory getICliFactory() {
        return mICliFactory;
    }
}
