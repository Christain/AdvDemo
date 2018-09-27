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

import com.assemble.ad.core.AdvFactory;
import com.assemble.ad.core.ApiAdRequest;
import com.assemble.ad.core.ApiBundle;
import com.assemble.ad.util.AppUtil;
import com.iclicash.advlib.ui.front.ADBrowser;

public class ApiAdBanner extends LinearLayout implements ApiBanner {

    private Context mContext;
    private int advType;
    private ApiAdRequest mApiAdRequest;
    private ApiBundle mApiBundle;

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
    public void setApiAdRequest(ApiAdRequest apiAdRequest) {
        this.mApiAdRequest = apiAdRequest;
    }

    @Override
    public void ApiUpdateView(ApiBundle apiBundle) {
        this.mApiBundle = apiBundle;
        addADView();
    }

    private void addADView() {
        removeAllViews();
        setVisibility(GONE);
        switch (advType) {
            case AdvFactory.CONTENT_IMAGE_AND_TEXT:
                imageAndText();
                break;
            case AdvFactory.CONTENT_IMAGE_GROUP:
                imageGroup();
                break;
            case AdvFactory.CONTENT_VIDEO:
                video();
                break;
            case AdvFactory.CONTENT_PURE_IMAGE:

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

        //Title
        TextView title = new TextView(mContext);
        LinearLayout.LayoutParams titleParam = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f);
        title.setLayoutParams(titleParam);
        title.setPadding(0, 0, AppUtil.dip2px(mContext, 15), 0);
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18f);
        title.setText("你有一台全新的iPhone待领取！");
        title.setMaxLines(2);
        title.setEllipsize(TextUtils.TruncateAt.END);
        title.setTextColor(0xFF2E3230);

        //”广告“文字
        TextView adTag = new TextView(mContext);
        LinearLayout.LayoutParams tagParam = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        adTag.setLayoutParams(tagParam);
        adTag.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14f);
        adTag.setText("广告");
        adTag.setTextColor(0xFF737373);

        //图片
        ImageView image = new ImageView(mContext);
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        image.setBackgroundColor(0xFF4e4e4e);
        LinearLayout.LayoutParams imageParam = new LayoutParams(AppUtil.dip2px(mContext, 113), ViewGroup.LayoutParams.MATCH_PARENT);
        image.setLayoutParams(imageParam);

        verticalLayout.addView(title);
        verticalLayout.addView(adTag);
        layout.addView(verticalLayout);
        layout.addView(image);
        addView(layout);

        //点击
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mApiAdRequest != null) {
                    mApiAdRequest.onApiClickedReport();
                }
            }
        });
        //已显示
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

        //Title
        TextView title = new TextView(mContext);
        LinearLayout.LayoutParams titleParam = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        title.setLayoutParams(titleParam);
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18f);
        title.setText("免费大礼等你来拿，速领！");
        title.setMaxLines(2);
        title.setEllipsize(TextUtils.TruncateAt.END);
        title.setTextColor(0xFF2E3230);

        LinearLayout imagelayout = new LinearLayout(mContext);
        imagelayout.setOrientation(HORIZONTAL);
        LinearLayout.LayoutParams horizontalParam = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, AppUtil.dip2px(mContext, 72));
        horizontalParam.topMargin = AppUtil.dip2px(mContext, 4);
        horizontalParam.bottomMargin = AppUtil.dip2px(mContext, 6);
        imagelayout.setLayoutParams(horizontalParam);

        //图片1
        ImageView imageOne = new ImageView(mContext);
        imageOne.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageOne.setBackgroundColor(0xFF4e4e4e);
        LinearLayout.LayoutParams imageParam = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f);
        imageOne.setLayoutParams(imageParam);

        //图片2
        ImageView imageTwo = new ImageView(mContext);
        imageTwo.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageTwo.setBackgroundColor(0xFF4e4e4e);
        imageTwo.setLayoutParams(imageParam);

        //图片3
        ImageView imageThree = new ImageView(mContext);
        imageThree.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageThree.setBackgroundColor(0xFF4e4e4e);
        imageThree.setLayoutParams(imageParam);

        //divider
        View dividerOne = new View(mContext);
        LinearLayout.LayoutParams diverParam = new LayoutParams(AppUtil.dip2px(mContext, 3), ViewGroup.LayoutParams.MATCH_PARENT);
        dividerOne.setLayoutParams(diverParam);
        View dividerTwo = new View(mContext);
        dividerTwo.setLayoutParams(diverParam);

        //”广告“文字
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

        //点击
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ADBrowser.class);
                intent.setAction("http://www.baidu.com");
                mContext.startActivity(intent);
                if (mApiAdRequest != null) {
                    mApiAdRequest.onApiClickedReport();
                }
            }
        });
        //已显示
        if (mApiAdRequest != null) {
            mApiAdRequest.onApiShowedReport();
        }
        setVisibility(VISIBLE);
    }

    private void video() {

    }
}
