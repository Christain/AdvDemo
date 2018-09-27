package com.assemble.ad.ui;


import com.assemble.ad.core.ApiAdRequest;
import com.assemble.ad.core.ApiBundle;

public interface ApiBanner {

    void setAdvType(int advType);

    void setApiAdRequest(ApiAdRequest apiAdRequest);

    void ApiUpdateView(ApiBundle apiBundle);
}
