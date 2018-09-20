package com.assemble.ad.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.assemble.ad.core.AdvFactory;
import com.iclicash.advlib.core.AdRequest;
import com.iclicash.advlib.ui.banner.ADBanner;
import com.iclicash.advlib.ui.banner.HTMLBanner;

public class NativeBanner extends LinearLayout implements CommonBanner {

    private Context mContext;
    private AttributeSet attrs;
    private int defStyleAttr;
    private AdRequest mAiclkAdRequest;
    private int platform;
    private int advType;
    private ADBanner mAiclkADBanner;
    private HTMLBanner mAiclkHtmlBanner;

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
            switch (advType) {
                case AdvFactory.CONTENT_IMAGE_AND_TEXT:
                case AdvFactory.CONTENT_IMAGE_GROUP:
                case AdvFactory.CONTENT_VIDEO:
                    if (mAiclkADBanner == null) {
                        mAiclkADBanner = new ADBanner(mContext, attrs, defStyleAttr);
                    }
                    addView(mAiclkADBanner);
                    mAiclkAdRequest.bindView(mAiclkADBanner);
                    break;
                case AdvFactory.CONTENT_PURE_IMAGE:
                    if (mAiclkHtmlBanner == null) {
                        mAiclkHtmlBanner = new HTMLBanner(mContext, attrs, defStyleAttr);
                    }
                    addView(mAiclkHtmlBanner);
                    mAiclkAdRequest.bindView(mAiclkHtmlBanner);
                    break;
            }
        } else {

        }
    }

    @Override
    public void setAiclkAdRequest(AdRequest adRequest) {
        this.mAiclkAdRequest = adRequest;

    }
}
