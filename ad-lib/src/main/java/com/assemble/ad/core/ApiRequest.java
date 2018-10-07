package com.assemble.ad.core;

import com.assemble.ad.ui.ApiBanner;

public interface ApiRequest {

    void bindView(ApiBanner banner);

    void InvokeADV(String adslot, String placeId, String channelId, int material, int height, int width);

    void onApiShowedReport();

    void onApiClickedReport();
}
