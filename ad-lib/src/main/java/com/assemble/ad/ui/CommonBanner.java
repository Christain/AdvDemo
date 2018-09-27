package com.assemble.ad.ui;

import com.assemble.ad.core.ApiAdRequest;
import com.iclicash.advlib.core.AdRequest;

public interface CommonBanner {

    void setPlatform(int platform);

    void setAdvType(int advType);

    void addBannerView();

    void setAiclkAdRequest(AdRequest adRequest);

    void setApiAdRequest(ApiAdRequest apiAdRequest);

}
