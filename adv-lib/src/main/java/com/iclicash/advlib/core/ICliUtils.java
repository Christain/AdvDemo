package com.iclicash.advlib.core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 工具类
 */
public class ICliUtils {

    /**
     * 下载图片，并返回Bitmap对象
     *
     * @param url
     * @return
     */
    public static Bitmap DownloadImage(String url) {
        Bitmap bitmap = BitmapFactory.decodeFile("");
        return bitmap;
    }

    /**
     * 下载ICliBundle中bmpurlarr中的所有图片
     *
     * @param bundle
     * @return
     */
    public static ICliBundle DownloadAllImage(ICliBundle bundle) {

        return new ICliBundle();
    }

    /**
     * 将输入流复制到输出流，直到输入流EOF
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     * @return
     */
    public static long CopyIToO(InputStream inputStream, OutputStream outputStream) {

        return 0;
    }

    /**
     * 下载文件到指定地址，返回保存文件全路径
     *
     * @param downloadUrl  下载地址
     * @param downloadPath 下载路径
     * @param fileName     保存文件名
     * @return 保存文件全路径
     */
    public static String Wget(String downloadUrl, String downloadPath, String fileName) {

        return "";
    }

    /**
     * 广告事件监听器
     * <p>
     * 将监听器注册到 AdRequest 请求类中。在请求得到数据回复后，对应的回调函数将被调用。
     * 注意:调用者可能来自工厂的线程。请酌情使用runOnUiThread()函数来保证UI的更改来自UI线程。
     */
    public interface AdContentListener {

        /**
         * 在请求得到回复后，将回调该函数
         */
        void AdContentListener(ICliBundle bundle);
    }
}
