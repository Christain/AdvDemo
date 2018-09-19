package com.assemble.ad.core;

import android.app.Activity;
import android.widget.Toast;

import com.assemble.ad.ui.CommonBanner;
import com.assemble.ad.ui.NativeBanner;
import com.iclicash.advlib.core.AdRequest;
import com.iclicash.advlib.core.ICliBundle;
import com.iclicash.advlib.core.ICliFactory;
import com.iclicash.advlib.core.ICliUtils;
import com.iclicash.advlib.http.CallBackUtil;
import com.iclicash.advlib.http.UrlHttpUtil;

public class AdvNetRequest {

    public static final int AICLK = 1000;
    public static final int OTHER = 2000;

    private Activity mActivity;
    private ICliUtils.AdContentListener listener;
    private CommonBanner banner;
    private String adId;
    private int adtype;
    private ICliFactory mICliFactory;
    private AdRequest mAdRequest;

    public AdvNetRequest(Activity activity) {
        this.mActivity = activity;
    }

    public void bindAdContentListener(ICliUtils.AdContentListener listener) {
        this.listener = listener;
    }

    public void bindView(CommonBanner banner) {
        this.banner = banner;
    }

    /**
     * 广告参数
     *
     * @param adId   广告位ID
     * @param adtype 广告类型(信息流、横幅)
     * @param height 广告位高度
     * @param width  广告位宽度
     */
    public void InvokeADV(String adId, int adtype, int height, int width) {
        int platform = AICLK;
        if (platform == AICLK) {
            banner.setPlatform(AICLK);
            banner.addBannerView();
            if (mICliFactory == null) {
                mICliFactory = new ICliFactory(mActivity);
                mICliFactory.setImageAutoDownload(true);
                mAdRequest = mICliFactory.getADRequest();
                mAdRequest.bindView(new NativeBanner(mActivity));
            }
            mAdRequest.InvokeADV(adId, adtype, 0, 0);
        } else {

        }
        if (banner == null) {
            Toast.makeText(mContext, "此请求未绑定Banner对象", Toast.LENGTH_SHORT).show();
            return;
        }
        banner.setAdRequest(this);
        this.adId = adId;
        this.adtype = adtype;
        UrlHttpUtil.get("https://www.baidu.com", new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(int code, String errorMessage) {
                Toast.makeText(mContext, "获取广告失败，请重试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                if (listener != null) {
                    listener.AdContentListener(new ICliBundle());
                } else {
                    banner.UpdateView(new ICliBundle());
                }
            }
        });
    }

    /**
     * 曝光后调用此接口
     */
    public void onShowedReport() {
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
    public void onClickedReport() {
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
