package com.iclicash.advdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.iclicash.advlib.core.AdRequest;
import com.iclicash.advlib.core.ICliFactory;
import com.iclicash.advlib.ui.banner.ADBanner;
import com.iclicash.advlib.ui.banner.HTMLBanner;

public class MainActivity extends AppCompatActivity {

    protected HTMLBanner htmlbanner;
    protected ADBanner adBanner;
    protected EditText adbanner_id_text;
    protected EditText htmlbanner_id_text;
    protected Button refresh;
    protected ICliFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        factory = new ICliFactory(this);
        factory.setImageAutoDownload(true);
        adBanner = (ADBanner) findViewById(R.id.adbanner);
        htmlbanner = (HTMLBanner) findViewById(R.id.htmlbanner);
        adbanner_id_text = (EditText) findViewById(R.id.adbanner_text);
        htmlbanner_id_text = (EditText) findViewById(R.id.htmlbanner_text);
        refresh = (Button) findViewById(R.id.refresh);
        final AdRequest adbanner_req = factory.getADRequest();
        adbanner_req.bindView(adBanner);
        final AdRequest htmlbanner_req = factory.getADRequest();
        htmlbanner_req.bindView(htmlbanner);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String adbanner_content = adbanner_id_text.getText().toString();
                String htmlbanner_content = htmlbanner_id_text.getText().toString();
                if (adbanner_content != null || adbanner_content.length() > 0)
                    adbanner_req.InvokeADV(adbanner_content, 1, 100, 200); // 使 Request 发
                if (htmlbanner_content != null || htmlbanner_content.length() > 0)
                    htmlbanner_req.InvokeADV(htmlbanner_content, 1, 100, 200); // 略起广告请求// 令 Factory 自动下载图片
            }
        });

//        AdRequest request = factory.getADRequest(new ICliUtils.AdContentListener() {
//            @Override
//            public void AdContentListener(ICliBundle bundle) {
//
//            }
//        });
//        request.bindView(adBanner);
//        request.InvokeADV("", 1, 1, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (factory != null) {
            factory.whenPermDialogReturns(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (factory != null) {
            factory.terminate();
        }
    }
}
