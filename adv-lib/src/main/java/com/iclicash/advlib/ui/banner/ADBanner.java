package com.iclicash.advlib.ui.banner;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.iclicash.advlib.R;
import com.iclicash.advlib.core.AdRequest;
import com.iclicash.advlib.core.ICliBundle;
import com.iclicash.advlib.imageLoader.EasyImageLoader;
import com.iclicash.advlib.widget.ultraviewpager.UltraViewPager;

import java.util.ArrayList;

public class ADBanner extends UltraViewPager implements Banner {

    private Context mContext;
    private AdRequest mAdRequest;

    public ADBanner(Context context) {
        super(context);
        initView(context);
    }

    public ADBanner(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ADBanner(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        this.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
//        mBanner.setRatio(2.65f);
        this.disableIndicator();
        this.setMultiScreen(1.0f);
    }

    @Override
    public void UpdateView(ICliBundle bundle) {
        //TODO 更新数据到Banner
//        if (bundle == null || bundle.getBmpurlarr() == null) {
//            return;
//        }
//        if (bundle.getBmpurlarr().length == 1) {
//            this.disableAutoScroll();
//            this.setInfiniteLoop(false);
//            this.setCanScroll(false);
//        } else {
//            this.setAutoScroll(3000);
//            this.setInfiniteLoop(true);
//            this.setCanScroll(true);
//        }
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < 4; i++) {
            list.add("" + i);
        }
        BannerAdapter adapter = new BannerAdapter(mContext);
        adapter.setBannerData(list);
        this.setAdapter(adapter);

        //广告显示的回调
        if (mAdRequest != null) {
            mAdRequest.onShowedReport();
        }
    }

    @Override
    public void setAdRequest(AdRequest adRequest) {
        this.mAdRequest = adRequest;
    }

    public class BannerAdapter extends PagerAdapter {

        private Context mContext;
        private ArrayList<String> list;

        public BannerAdapter(Context context) {
            this.mContext = context;
            this.list = new ArrayList<>();
        }

        public void setBannerData(ArrayList<String> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(final ViewGroup container, final int position) {
            ImageView imageView = (ImageView) LayoutInflater.from(mContext).inflate(R.layout.icli_item_adbanner, null);
            if (mContext == null || imageView == null) {
                return null;
            }
            if (mContext instanceof Activity && ((Activity) mContext).isFinishing()) {
                return null;
            }
            if (position % 2 == 0) {
                imageView.setBackgroundColor(0xFF4e4e4e);
                EasyImageLoader.getInstance(mContext).bindBitmap
                        ("http://img03.tooopen.com/images/20120731/sy_201207312302132850.jpg", imageView);
            } else {
                imageView.setBackgroundColor(0xFF4FEE68);
                EasyImageLoader.getInstance(mContext).bindBitmap
                        ("http://pic27.photophoto.cn/20130526/0036036820588350_b.jpg", imageView);
            }
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list != null && position <= list.size() - 1) {
                        Toast.makeText(mContext, "跳转", Toast.LENGTH_SHORT).show();

                        //广告点击的回调
                        if (mAdRequest != null) {
                            mAdRequest.onClickedReport();
                        }
                    }
                }
            });
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ImageView view = (ImageView) object;
            container.removeView(view);
        }
    }
}
