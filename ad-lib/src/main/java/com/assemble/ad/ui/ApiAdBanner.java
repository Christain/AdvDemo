package com.assemble.ad.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.assemble.ad.bean.AdvBean;
import com.assemble.ad.core.ApiRequest;
import com.assemble.ad.imageLoader.EasyImageLoader;
import com.assemble.ad.util.AppUtil;
import com.iclicash.advlib.ui.front.ADBrowser;

public class ApiAdBanner extends LinearLayout implements ApiBanner {

    private Context mContext;
    private int advType;
    private ApiRequest mApiAdRequest;
    private AdvBean mAdvBean;

    public ApiAdBanner(Context context) {
        super(context);
        initView(context);
    }

    public ApiAdBanner(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ApiAdBanner(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
    }

    @Override
    public void setAdvType(int advType) {
        this.advType = advType;
    }

    @Override
    public void setApiRequest(ApiRequest apiRequest) {
        this.mApiAdRequest = apiRequest;
    }

    @Override
    public void ApiUpdateView(AdvBean advBean) {
        this.mAdvBean = advBean;
        if (mAdvBean == null || mAdvBean.native_material == null) {
            Toast.makeText(mContext, "ad data is null", Toast.LENGTH_SHORT).show();
            return;
        }
        addADView();
    }

    private void addADView() {
        removeAllViews();
        setVisibility(GONE);
        switch (mAdvBean.native_material.type) {
            case 1:
                if (mAdvBean.native_material.text_icon_snippet != null) {
                    imageAndText();
                }
                break;
            case 2:
                if (mAdvBean.native_material.image_snippet != null) {
                    bigImage();
                }
                break;
            case 3:
                if (mAdvBean.native_material.text_icon_snippet != null) {
                    imageGroup();
                }
                break;
        }
    }

    private void imageAndText() {
        LinearLayout layout = new LinearLayout(mContext);
        layout.setOrientation(HORIZONTAL);
        LinearLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, AppUtil.dip2px(mContext, 72));
        layout.setLayoutParams(layoutParams);

        LinearLayout verticalLayout = new LinearLayout(mContext);
        verticalLayout.setOrientation(VERTICAL);
        LinearLayout.LayoutParams verticalParam = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 2f);
        verticalLayout.setLayoutParams(verticalParam);

        TextView title = new TextView(mContext);
        LinearLayout.LayoutParams titleParam = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f);
        title.setLayoutParams(titleParam);
        title.setPadding(0, 0, AppUtil.dip2px(mContext, 15), 0);
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18f);
        title.setText(mAdvBean.native_material.text_icon_snippet.title);
        title.setMaxLines(2);
        title.setEllipsize(TextUtils.TruncateAt.END);
        title.setTextColor(0xFF2E3230);

        TextView adTag = new TextView(mContext);
        LinearLayout.LayoutParams tagParam = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        adTag.setLayoutParams(tagParam);
        adTag.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14f);
        adTag.setText("广告");
        adTag.setTextColor(0xFF737373);

        ImageView image = new ImageView(mContext);
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        LinearLayout.LayoutParams imageParam = new LayoutParams(AppUtil.dip2px(mContext, 113), ViewGroup.LayoutParams.MATCH_PARENT);
        image.setLayoutParams(imageParam);
        EasyImageLoader.getInstance(mContext).bindBitmap(mAdvBean.native_material.text_icon_snippet.url, image);

        verticalLayout.addView(title);
        verticalLayout.addView(adTag);
        layout.addView(verticalLayout);
        layout.addView(image);
        addView(layout);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ADBrowser.class);
                intent.setAction(mAdvBean.native_material.text_icon_snippet.c_url);
                mContext.startActivity(intent);
                if (mApiAdRequest != null) {
                    mApiAdRequest.onApiClickedReport();
                }
            }
        });

        if (mApiAdRequest != null) {
            mApiAdRequest.onApiShowedReport();
        }
        setVisibility(VISIBLE);
    }

    private void imageGroup() {
        LinearLayout layout = new LinearLayout(mContext);
        layout.setOrientation(VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(layoutParams);

        TextView title = new TextView(mContext);
        LinearLayout.LayoutParams titleParam = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        title.setLayoutParams(titleParam);
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18f);
        title.setText(mAdvBean.native_material.text_icon_snippet.title);
        title.setMaxLines(2);
        title.setEllipsize(TextUtils.TruncateAt.END);
        title.setTextColor(0xFF2E3230);

        LinearLayout imagelayout = new LinearLayout(mContext);
        imagelayout.setOrientation(HORIZONTAL);
        LinearLayout.LayoutParams horizontalParam = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, AppUtil.dip2px(mContext, 72));
        horizontalParam.topMargin = AppUtil.dip2px(mContext, 4);
        horizontalParam.bottomMargin = AppUtil.dip2px(mContext, 6);
        imagelayout.setLayoutParams(horizontalParam);

        ImageView imageOne = new ImageView(mContext);
        imageOne.setScaleType(ImageView.ScaleType.CENTER_CROP);
        LinearLayout.LayoutParams imageParam = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f);
        imageOne.setLayoutParams(imageParam);
        EasyImageLoader.getInstance(mContext).bindBitmap(mAdvBean.native_material.text_icon_snippet.url, imageOne);

        ImageView imageTwo = new ImageView(mContext);
        imageTwo.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageTwo.setLayoutParams(imageParam);
        if (!mAdvBean.native_material.text_icon_snippet.ext_urls.isEmpty()) {
            EasyImageLoader.getInstance(mContext).bindBitmap(mAdvBean.native_material.text_icon_snippet.ext_urls.get(0), imageTwo);
        }

        ImageView imageThree = new ImageView(mContext);
        imageThree.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageThree.setLayoutParams(imageParam);
        if (!mAdvBean.native_material.text_icon_snippet.ext_urls.isEmpty() && mAdvBean.native_material.text_icon_snippet.ext_urls.size() > 1) {
            EasyImageLoader.getInstance(mContext).bindBitmap(mAdvBean.native_material.text_icon_snippet.ext_urls.get(1), imageThree);
        }

        View dividerOne = new View(mContext);
        LinearLayout.LayoutParams diverParam = new LayoutParams(AppUtil.dip2px(mContext, 3), ViewGroup.LayoutParams.MATCH_PARENT);
        dividerOne.setLayoutParams(diverParam);
        View dividerTwo = new View(mContext);
        dividerTwo.setLayoutParams(diverParam);

        TextView adTag = new TextView(mContext);
        LinearLayout.LayoutParams tagParam = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        adTag.setLayoutParams(tagParam);
        adTag.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14f);
        adTag.setText("广告");
        adTag.setTextColor(0xFF737373);

        imagelayout.addView(imageOne);
        imagelayout.addView(dividerOne);
        imagelayout.addView(imageTwo);
        imagelayout.addView(dividerTwo);
        imagelayout.addView(imageThree);
        layout.addView(title);
        layout.addView(imagelayout);
        layout.addView(adTag);
        addView(layout);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ADBrowser.class);
                intent.setAction(mAdvBean.native_material.text_icon_snippet.c_url);
                mContext.startActivity(intent);
                if (mApiAdRequest != null) {
                    mApiAdRequest.onApiClickedReport();
                }
            }
        });

        if (mApiAdRequest != null) {
            mApiAdRequest.onApiShowedReport();
        }
        setVisibility(VISIBLE);
    }

    private void bigImage() {
        LinearLayout layout = new LinearLayout(mContext);
        layout.setOrientation(VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(layoutParams);

        TextView title = new TextView(mContext);
        LinearLayout.LayoutParams titleParam = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        title.setLayoutParams(titleParam);
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18f);
        title.setText(mAdvBean.native_material.image_snippet.title);
        title.setMaxLines(2);
        title.setEllipsize(TextUtils.TruncateAt.END);
        title.setTextColor(0xFF2E3230);

        ImageView image = new ImageView(mContext);
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        LinearLayout.LayoutParams imageParam = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, AppUtil.dip2px(mContext, 170));
        imageParam.topMargin = AppUtil.dip2px(mContext, 4);
        imageParam.bottomMargin = AppUtil.dip2px(mContext, 6);
        image.setLayoutParams(imageParam);
        EasyImageLoader.getInstance(mContext).bindBitmap(mAdvBean.native_material.image_snippet.url, image);

        TextView adTag = new TextView(mContext);
        LinearLayout.LayoutParams tagParam = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        adTag.setLayoutParams(tagParam);
        adTag.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14f);
        adTag.setText("广告");
        adTag.setTextColor(0xFF737373);

        layout.addView(title);
        layout.addView(image);
        layout.addView(adTag);
        addView(layout);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ADBrowser.class);
                intent.setAction(mAdvBean.native_material.image_snippet.c_url);
                mContext.startActivity(intent);
                if (mApiAdRequest != null) {
                    mApiAdRequest.onApiClickedReport();
                }
            }
        });

        if (mApiAdRequest != null) {
            mApiAdRequest.onApiShowedReport();
        }
        setVisibility(VISIBLE);
    }
}
