package com.iclicash.advlib.ui.front;

import android.content.Intent;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iclicash.advlib.R;
import com.iclicash.advlib.util.AppUtil;
import com.iclicash.advlib.util.BtnClickUtil;
import com.iclicash.advlib.util.StatusBarUtil;
import com.iclicash.advlib.widget.BackView;

/**
 * 包装的WebView浏览器
 */
public class ADBrowser extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout mRootView;
    private View mStatusBarView;
    private BackView mBackView;
    private TextView mTvClose, mTvTitle;
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.icli_activity_ad_browser);
        if (savedInstanceState != null) {
            this.url = savedInstanceState.getString("URL", "");
        } else {
            Intent intent = getIntent();
            if (intent == null || TextUtils.isEmpty(intent.getAction())) {
                finish();
                return;
            }
            this.url = intent.getAction();
        }
        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        mRootView = (RelativeLayout) findViewById(R.id.root_view);
        mStatusBarView = (View) findViewById(R.id.status_bar_view);
        mBackView = (BackView) findViewById(R.id.back_view);
        mTvClose = (TextView) findViewById(R.id.tv_close);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mWebView = (WebView) findViewById(R.id.webview);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        //状态栏
        if (StatusBarUtil.StatusBarLightMode(this) != 0) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mStatusBarView.getLayoutParams();
            params.height = AppUtil.getStatusBarHigh(this);
            mStatusBarView.setLayoutParams(params);
            mStatusBarView.setVisibility(View.VISIBLE);
        } else {
            mStatusBarView.setVisibility(View.GONE);
        }
        setCustomStyle();

        mWebView.setWebViewClient(mWebViewClient);
        mWebView.setWebChromeClient(mWebChromeClient);
        initWebViewSettings();

        mBackView.setOnClickListener(this);
        mTvClose.setOnClickListener(this);

        mWebView.loadUrl(url);
    }

    /**
     * 自定义颜色样式
     */
    private void setCustomStyle() {
        String titleColor = AppUtil.getAppMetaData(this, "aiclk_override_adbrowser_tvtitle_text_color");
        if (mTvTitle != null && !TextUtils.isEmpty(titleColor)) {
            mTvTitle.setTextColor(titleColor.startsWith("#") ? Color.parseColor(titleColor) : Color.parseColor("#" + titleColor));
        }
        String closeColor = AppUtil.getAppMetaData(this, "aiclk_override_adbrowser_tvclose_text_color");
        if (mTvClose != null && !TextUtils.isEmpty(closeColor)) {
            mTvClose.setTextColor(closeColor.startsWith("#") ? Color.parseColor(closeColor) : Color.parseColor("#" + closeColor));
        }
        String backColor = AppUtil.getAppMetaData(this, "aiclk_override_adbrowser_tvback_text_color");
        if (mBackView != null && !TextUtils.isEmpty(backColor)) {
            mBackView.setColor(backColor.startsWith("#") ? Color.parseColor(backColor) : Color.parseColor("#" + backColor));
        }
    }

    /**
     * WebView配置
     */
    private void initWebViewSettings() {
        WebSettings webSetting = mWebView.getSettings();
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
     * 处理Url拦截事项
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
            if (mTvTitle != null && webView != null && webView.getTitle() != null) {
                mTvTitle.setText(webView.getTitle());
            }
        }

        @Override
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            sslErrorHandler.proceed();
        }
    };

    /**
     * 设置WebChromeClient
     * 处理进度条和标题显示
     */
    private WebChromeClient mWebChromeClient = new WebChromeClient() {

        @Override
        public void onProgressChanged(WebView webView, int newProgress) {
            if (mProgressBar == null) {
                return;
            }
            if (newProgress == 100) {
                mProgressBar.setProgress(100);
                mProgressBar.setVisibility(View.GONE);
            } else {
                if (!mProgressBar.isShown()) {
                    mProgressBar.setVisibility(View.VISIBLE);
                }
                mProgressBar.setProgress(newProgress);
            }
            super.onProgressChanged(webView, newProgress);
        }
    };

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (BtnClickUtil.isFastDoubleClick(id)) return;
        if (id == R.id.back_view) {
            onBackPressed();
        } else if (id == R.id.tv_close) {
            finish();
        }
    }

    /**
     * 物理返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }

    @Override
    public void finish() {
        AppUtil.hideKeyboard(this);
        super.finish();
    }

    /**
     * 销毁网页
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRootView != null) {
            mRootView.removeView(mWebView);
            mRootView.removeAllViews();
        }
        if (mWebView != null) {
            mWebView.destroy();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("URL", url);
        super.onSaveInstanceState(outState);
    }
}
