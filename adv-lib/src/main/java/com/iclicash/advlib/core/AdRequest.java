package com.iclicash.advlib.core;

import android.content.Context;
import android.widget.Toast;

import com.iclicash.advlib.http.CallBackUtil;
import com.iclicash.advlib.http.UrlHttpUtil;
import com.iclicash.advlib.ui.banner.Banner;

public class AdRequest {

    private Context mContext;
    private ICliUtils.AdContentListener listener;
    private Banner banner;
    private String adId;
    private int adtype;

    public AdRequest(Context context) {
        this.mContext = context;
    }

    public void bindAdContentListener(ICliUtils.AdContentListener listener) {
        this.listener = listener;
    }

    public void bindView(Banner banner) {
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
