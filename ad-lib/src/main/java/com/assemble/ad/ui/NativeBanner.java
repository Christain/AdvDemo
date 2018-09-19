package com.assemble.ad.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.assemble.ad.core.AdvNetRequest;
import com.iclicash.advlib.ui.banner.ADBanner;

public class NativeBanner extends LinearLayout implements CommonBanner {

    private Context mContext;
    private AttributeSet attrs;
    private int defStyleAttr;
    private AdvNetRequest mAdvNetRequest;
    private int platform;
    private ADBanner mADBanner;

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
    public void addBannerView() {
        if (platform == AdvNetRequest.AICLK) {
            if (mADBanner == null) {
                mADBanner = new ADBanner(mContext, attrs, defStyleAttr);
                addView(mADBanner);
            }
        } else {

        }
    }

    @Override
    public void updateView() {

    }
}
