package com.iclicash.advlib.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;

/**
 * 获取app各种信息工具类
 */
public class AppUtil {

    /**
     * 获取application中指定的meta-data
     *
     * @return 如果没有获取成功(没有对应值, 或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context ctx, String key) {
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return resultData;
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHigh(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getApplicationContext().getResources().getDimensionPixelSize(x);
            return sbar;
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据手机分辨率从dp转成px
     *
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        if (context == null || context.getApplicationContext() == null) {
            return 0;
        }
        final float scale = context.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 隐藏输入法键盘
     *
     * @param activity
     */
    public static void hideKeyboard(Activity activity) {
        try {
            View view = activity.getWindow().peekDecorView();
            if (view != null) {
                InputMethodManager inputmanger = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
