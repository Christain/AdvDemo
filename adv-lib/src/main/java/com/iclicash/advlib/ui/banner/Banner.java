package com.iclicash.advlib.ui.banner;

import com.iclicash.advlib.core.AdRequest;
import com.iclicash.advlib.core.ICliBundle;

/**
 * Banner包的接口类
 */
public interface Banner {

    /**
     * 用于更新一个实例化了的Banner界面
     */
    void UpdateView(ICliBundle bundle);

    /**
     * 将Request绑定到Banner对象上
     */
    void setAdRequest(AdRequest adRequest);
}
