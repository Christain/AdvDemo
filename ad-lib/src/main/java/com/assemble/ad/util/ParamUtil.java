package com.assemble.ad.util;

import android.content.Context;

import com.assemble.ad.Constants;
import com.assemble.ad.bean.ParamBean;

public class ParamUtil {

    public static String ParamJson(Context mContext, String adslot, int material, int height, int width) {
        ParamBean bean = new ParamBean();

//        //request
//        ParamBean.RequestBean requestBean = new ParamBean.RequestBean();
//        requestBean.setApp_id("app_id");
//        requestBean.setSecurity_key("security_key");
//        requestBean.setSign("sign");
//        requestBean.setSsp("api");

        //param
        ParamBean.ParamEntity paramEntity = new ParamBean.ParamEntity();
        //param--adslot
        ParamBean.ParamEntity.AdslotEntity adslotEntity = new ParamBean.ParamEntity.AdslotEntity();
        adslotEntity.setId(adslot);
        adslotEntity.setType(material);
        adslotEntity.setHeight(height);
        adslotEntity.setWidth(width);
        paramEntity.setAdslot(adslotEntity);

        //param--client
        ParamBean.ParamEntity.ClientEntity clientEntity = new ParamBean.ParamEntity.ClientEntity();
        clientEntity.setType(1);
        clientEntity.setVersion(Constants.VERSION_NAME);
        paramEntity.setClient(clientEntity);

        //param--media
        ParamBean.ParamEntity.MediaEntity mediaEntity = new ParamBean.ParamEntity.MediaEntity();
        ParamBean.ParamEntity.MediaEntity.AppEntity appEntity = new ParamBean.ParamEntity.MediaEntity.AppEntity();
        appEntity.setApp_version(AppUtil.getVersionInfo(mContext).versionName);
        appEntity.setPackage_name(AppUtil.getVersionInfo(mContext).packageName);
        mediaEntity.setType(1);
        mediaEntity.setApp(appEntity);
        paramEntity.setMedia(mediaEntity);

//        bean.setRequest(requestBean);
        bean.setParam(paramEntity);
        String json = SimpleJson.toJson(bean).replaceAll("\\,\\}", "}");

        return json;
    }
}
