package com.assemble.ad.ui;


import com.assemble.ad.bean.AdvBean;
import com.assemble.ad.core.ApiRequest;

public interface ApiBanner {

    void setAdvType(int advType);

    void setApiRequest(ApiRequest apiRequest);

    void ApiUpdateView(AdvBean advBean);
}
