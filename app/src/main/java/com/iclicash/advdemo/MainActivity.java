package com.iclicash.advdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.assemble.ad.core.AdvFactory;
import com.assemble.ad.core.AdvNetRequest;
import com.assemble.ad.ui.ApiBanner;
import com.assemble.ad.ui.NativeBanner;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected NativeBanner adBanner;
    protected ApiBanner mApiBanner;
    protected Button ImageText, ImageGroup, ImageOnly, Video;
    protected AdvFactory factory;
    private AdvNetRequest mAdvNetRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adBanner = (NativeBanner) findViewById(R.id.adbanner);
        mApiBanner = (ApiBanner) findViewById(R.id.apibanner);
        ImageText = (Button) findViewById(R.id.image_text);
        ImageGroup = (Button) findViewById(R.id.image_group);
        ImageOnly = (Button) findViewById(R.id.image_only);
        Video = (Button) findViewById(R.id.video);
        ImageText.setOnClickListener(this);
        ImageGroup.setOnClickListener(this);
        ImageOnly.setOnClickListener(this);
        Video.setOnClickListener(this);

        factory = new AdvFactory(this);
        mAdvNetRequest = factory.getAdvNetRequest();
        mAdvNetRequest.bindView(adBanner);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_text:
                mAdvNetRequest.InvokeADV("3", 100, 200);
                break;
            case R.id.image_group:
                mAdvNetRequest.InvokeADV("2", 100, 200);
                break;
            case R.id.image_only:
                mAdvNetRequest.InvokeADV("1", 100, 200);
                break;
        }
    }
}
