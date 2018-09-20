package com.assemble.ad.ui;

import com.iclicash.advlib.core.AdRequest;

public interface CommonBanner {

    void setPlatform(int platform);

    void setAdvType(int advType);

    void addBannerView();

    void setAiclkAdRequest(AdRequest adRequest);

}
