package com.assemble.ad.core;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.iclicash.advlib.core.ICliUtils;

public class AdvFactory {

    /**
     * DataType 物料类型引用--DYNAMIC物料(HTML)
     */
    public static final int MATERIAL_DYNAMIC = 0x101;

    /**
     * DataType 物料类型引用--NATIVE物料(裸数据)
     */
    public static final int MATERIAL_NATIVE = 0x102;

    /**
     * DataContent 内容类型引用--未知类型(通常意味着错误)
     */
    public static final int CONTENT_UNKNOWN = 0x103;

    /**
     * DataContent 内容类型引用--图文
     */
    public static final int CONTENT_IMAGE_AND_TEXT = 0x104;

    /**
     * DataContent 内容类型引用--纯图片
     */
    public static final int CONTENT_PURE_IMAGE = 0x105;

    /**
     * DataContent 内容类型引用--组图
     */
    public static final int CONTENT_IMAGE_GROUP = 0x106;

    /**
     * DataContent 内容类型引用--视频
     */
    public static final int CONTENT_VIDEO = 0x107;

    private Activity mActivity;
    private AdvNetRequest mAdRequest;
    private boolean isImageAutoDownload;//是否自动下载图片、视频等资源

    public AdvFactory(Activity activity) {
        this.mActivity = activity;
        requestPermission();
    }

    public AdvNetRequest getAdvNetRequest() {
        mAdRequest = new AdvNetRequest(mActivity);
        return mAdRequest;
    }

    public AdvNetRequest getAdvNetRequest(ICliUtils.AdContentListener listener) {
        mAdRequest = new AdvNetRequest(mActivity);
        mAdRequest.bindAdContentListener(listener);
        return mAdRequest;
    }

    public void setImageAutoDownload(boolean isImageAutoDownload) {
        this.isImageAutoDownload = isImageAutoDownload;
    }

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
        if (mAdRequest != null) {
            mAdRequest = null;
        }
    }
}
