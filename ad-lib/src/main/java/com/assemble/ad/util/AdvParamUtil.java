package com.assemble.ad.util;

import android.content.Context;

import com.assemble.ad.Constants;
import com.assemble.ad.bean.AdvParamBean;

public class AdvParamUtil {

    public static String ParamJson(Context mContext, String adslot, int material, int height, int width) {
        AdvParamBean bean = new AdvParamBean();

        //param--adslot
        AdvParamBean.AdslotEntity adslotEntity = new AdvParamBean.AdslotEntity();
        adslotEntity.setId(adslot);
        adslotEntity.setType(material);
        adslotEntity.setHeight(height);
        adslotEntity.setWidth(width);
        bean.setAdslot(adslotEntity);

        //param--client
        AdvParamBean.ClientEntity clientEntity = new AdvParamBean.ClientEntity();
        clientEntity.setType(3);
        clientEntity.setVersion(Constants.VERSION_NAME);
        bean.setClient(clientEntity);

        //param--media
        AdvParamBean.MediaEntity mediaEntity = new AdvParamBean.MediaEntity();
        AdvParamBean.MediaEntity.AppEntity appEntity = new AdvParamBean.MediaEntity.AppEntity();
        appEntity.setApp_version(AppUtil.getVersionInfo(mContext).versionName);
        appEntity.setPackage_name(AppUtil.getVersionInfo(mContext).packageName);
        AdvParamBean.MediaEntity.BrowserEntity browserEntity = new AdvParamBean.MediaEntity.BrowserEntity();
        browserEntity.setUser_agent(AppUtil.getUserAgent(mContext));
        mediaEntity.setBrowser(browserEntity);
        mediaEntity.setType(1);
        mediaEntity.setApp(appEntity);
        bean.setMedia(mediaEntity);

        //param--device
        AdvParamBean.DeviceEntity deviceEntity = new AdvParamBean.DeviceEntity();
        deviceEntity.setId_imei(AppUtil.getIMEI(mContext));
        deviceEntity.setWidth(AppUtil.getScreenWidth(mContext));
        deviceEntity.setHeight(AppUtil.getScreenHeight(mContext));
        deviceEntity.setModel(AppUtil.getPhoneModel());
        deviceEntity.setOs_version(AppUtil.getPhoneSystemVersion());
        deviceEntity.setBrand(AppUtil.getPhoneBrand());
        deviceEntity.setOs_type(1);
        bean.setDevice(deviceEntity);

        //param--network
        AdvParamBean.NetworkEntity networkEntity = new AdvParamBean.NetworkEntity();
        networkEntity.setIp(AppUtil.getIPAddress(mContext));
        networkEntity.setType(2);
        bean.setNetwork(networkEntity);

        String json = JsonUtil.toJson(bean).replaceAll("\\,\\}", "}");

        return json;
    }
}
