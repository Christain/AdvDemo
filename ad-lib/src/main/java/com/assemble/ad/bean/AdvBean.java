package com.assemble.ad.bean;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdvBean {

    public int material_type;
    public String html_snippet;
    public NativeMaterialBean native_material;

    public AdvBean(String json) {
        try {
            JSONObject object = new JSONObject(json);
            this.material_type = object.optInt("material_type");
            this.html_snippet = object.optString("html_snippet", "");
            this.native_material = new NativeMaterialBean(object.optString("native_material"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static class NativeMaterialBean {

        public int type;
        public int interaction_type;
        public SnippetBean image_snippet;
        public SnippetBean text_icon_snippet;
        public String video_snippet;
        public String ext;

        public NativeMaterialBean(String json) {
            try {
                JSONObject object = new JSONObject(json);
                this.type = object.optInt("type");
                this.interaction_type = object.optInt("interaction_type");
                if (type == 2 || type == 6 || type == 7) {
                    this.image_snippet = new SnippetBean(object.optString("image_snippet", ""));
                }
                if (type == 1 || type == 3 || type == 5) {
                    this.text_icon_snippet = new SnippetBean(object.optString("text_icon_snippet", ""));
                }
                if (type == 4) {
                    this.video_snippet = object.optString("video_snippet", "");
                }
                this.ext = object.optString("ext", "");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static class SnippetBean {

        public String url;
        public String c_url;
        public int width;
        public int height;
        public ArrayList<String> imp;
        public ArrayList<String> clk;
        public String title;
        public String desc;

        public ArrayList<String> ext_urls;

        public SnippetBean(String json) {
            try {
                JSONObject object = new JSONObject(json);
                this.url = object.optString("url", "");
                this.c_url = object.optString("c_url", "");
                this.width = object.optInt("width");
                this.height = object.optInt("height");
                this.title = object.optString("title", "");
                this.desc = object.optString("desc", "");
                this.imp = new ArrayList<>();
                JSONArray impArray = object.getJSONArray("imp");
                for (int i = 0; i < impArray.length(); i++) {
                    this.imp.add(impArray.optString(i));
                }
                this.clk = new ArrayList<>();
                JSONArray clkArray = object.getJSONArray("clk");
                for (int i = 0; i < clkArray.length(); i++) {
                    this.clk.add(clkArray.optString(i));
                }
                this.ext_urls = new ArrayList<>();
                if (object.has("ext_urls")
                        && !TextUtils.isEmpty(object.optString("ext_urls")) &&
                        !object.optString("ext_urls").equals("null")) {
                    JSONArray extArray = object.getJSONArray("ext_urls");
                    for (int i = 0; i < extArray.length(); i++) {
                        this.ext_urls.add(extArray.optString(i));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
