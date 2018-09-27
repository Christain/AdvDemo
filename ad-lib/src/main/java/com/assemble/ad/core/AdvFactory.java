package com.assemble.ad.core;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

public class AdvFactory {

    public static final int PLATFORM_AICLK = 1000;
    public static final int PLATFORM_OTHER = 2000;
    public static final int MATERIAL_DYNAMIC = 0x101;
    public static final int MATERIAL_NATIVE = 0x102;
    public static final int CONTENT_UNKNOWN = 0x103;
    public static final int CONTENT_IMAGE_AND_TEXT = 0x104;
    public static final int CONTENT_PURE_IMAGE = 0x105;
    public static final int CONTENT_IMAGE_GROUP = 0x106;
    public static final int CONTENT_VIDEO = 0x107;

    private Activity mActivity;
    private AdvNetRequest mAdvNetRequest;

    public AdvFactory(Activity activity) {
        this.mActivity = activity;
        requestPermission();
    }

    public AdvNetRequest getAdvNetRequest() {
        mAdvNetRequest = new AdvNetRequest(mActivity);
        return mAdvNetRequest;
    }

//    public AdvNetRequest getAdvNetRequest(ICliUtils.AdContentListener listener) {
//        mAdRequest = new AdvNetRequest(mActivity);
//        mAdRequest.bindAdContentListener(listener);
//        return mAdRequest;
//    }

    /**
     * 申请权限
     */
    private void requestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            return;
        }
        ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);

    }

    /**
     * 6.0权限回调处理
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void whenPermDialogReturns(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //授权成功

            } else {
                //授权失败提示
                Toast.makeText(mActivity, "获取授权失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 界面销毁时，释放工厂对象
     */
    public void terminate() {
        if (mAdvNetRequest != null && mAdvNetRequest.getICliFactory() != null) {
            mAdvNetRequest.getICliFactory().terminate();
        }
    }
}
