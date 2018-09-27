package com.assemble.ad.core;

import android.graphics.Bitmap;

/**
 * 数据bundle
 *
 * SDK 内部数据流转的载体对象，只包含一系列变量，无调用方法。
 */
public class ApiBundle {

    private int DataType;           //物料类型
    private int DataContent;        //内容类型
    private String lastError;       //错误消息(null == 无错误)
    private String title;           //内容标题(仅在物料类型 == MATERIAL_NATIVE 时可用)
    private String content;         //HTML内容(仅在物料类型 == MATERIAL_DYNAMIC 时可用)
    private String content_url;     //内容引用URL(仅在内容类型 == CONTENT_VIDEO 时可用)
    private String onClickURL;      //点击跳转URL(仅在内容类型 == MATERIAL_NATIVE时可用)
    private Bitmap[] bmparr;        //存储了内容图片的数组(仅在内容类型 == CONTENT_IMAGE_GROUP || CONTENT_PURE_IMAGE || CONTENT_IMAGE_AND_TEXT 时可用，只有 SetImageAutoDownload == true 时才会自动下载)
    private String[] bmpurlarr;     //存储了内容图片URL的数组(同上，可根据此地址下载图片)
    private int img_height;         //由服务器定义的图片高
    private int img_width;          //由服务器定义的图片宽
    private int video_duration;     //由服务器定义的视频播放时长(仅在内容类型 == CONTENT_VIDEO 时可用)

    public int getDataType() {
        return DataType;
    }

    public void setDataType(int dataType) {
        this.DataType = dataType;
    }

    public int getDataContent() {
        return DataContent;
    }

    public void setDataContent(int dataContent) {
        this.DataContent = dataContent;
    }

    public String getLastError() {
        return lastError;
    }

    public void setLastError(String lastError) {
        this.lastError = lastError;
    }

    public String getTitle() {
        return (title != null) ? title : "";
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return (content != null) ? content : "";
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent_url() {
        return (content_url != null) ? content_url : "";
    }

    public void setContent_url(String content_url) {
        this.content_url = content_url;
    }

    public String getOnClickURL() {
        return (onClickURL != null) ? onClickURL : "";
    }

    public void setOnClickURL(String onClickURL) {
        this.onClickURL = onClickURL;
    }

    public Bitmap[] getBmparr() {
        return bmparr;
    }

    public void setBmparr(Bitmap[] bmparr) {
        this.bmparr = bmparr;
    }

    public String[] getBmpurlarr() {
        return bmpurlarr;
    }

    public void setBmpurlarr(String[] bmpurlarr) {
        this.bmpurlarr = bmpurlarr;
    }

    public int getImg_height() {
        return img_height;
    }

    public void setImg_height(int img_height) {
        this.img_height = img_height;
    }

    public int getImg_width() {
        return img_width;
    }

    public void setImg_width(int img_width) {
        this.img_width = img_width;
    }

    public int getVideo_duration() {
        return video_duration;
    }

    public void setVideo_duration(int video_duration) {
        this.video_duration = video_duration;
    }
}
