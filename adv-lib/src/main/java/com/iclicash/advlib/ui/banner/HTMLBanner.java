package com.iclicash.advlib.ui.banner;

import android.content.Context;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.iclicash.advlib.core.AdRequest;
import com.iclicash.advlib.core.ICliBundle;

public class HTMLBanner extends WebView implements Banner {

    private Context mContext;
    private AdRequest mAdRequest;

    public HTMLBanner(Context context) {
        super(context);
        initView(context);
    }

    public HTMLBanner(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public HTMLBanner(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        this.setWebViewClient(mWebViewClient);
        initWebViewSettings();

        //禁止滑动
        this.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });
    }

    /**
     * WebView配置
     */
    private void initWebViewSettings() {
        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(false);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setSavePassword(false);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSetting.setLoadWithOverviewMode(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    /**
     * 设置WebViewClient
     */
    private WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView webView, String s) {
            super.onPageFinished(webView, s);
            //广告显示的回调
            if (mAdRequest != null) {
                mAdRequest.onShowedReport();
            }
        }

        @Override
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            sslErrorHandler.proceed();
        }
    };


    @Override
    public void UpdateView(ICliBundle bundle) {
//        if (bundle == null || TextUtils.isEmpty(bundle.getContent())) {
//            return;
//        }
        this.loadUrl("https://www.baidu.com");
    }

    @Override
    public void setAdRequest(AdRequest adRequest) {
        this.mAdRequest = adRequest;
    }
}
