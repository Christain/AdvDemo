package com.assemble.ad.bean;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RatioBean {

    public String ad_place_id;
    public ArrayList<ChannelsBean> channels;

    public RatioBean(String json) {
        try {
            JSONObject object = new JSONObject(json);
            this.ad_place_id = object.optString("ad_place_id", "");
            this.channels = new ArrayList<>();
            JSONArray array = object.getJSONArray("channels");
            for (int i = 0; i < array.length(); i++) {
                ChannelsBean channelsBean = new ChannelsBean(array.getJSONObject(i).toString());
                channels.add(channelsBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static class ChannelsBean {

        public String id;
        public String name;
        public int ratio;
        public String method;
        public String ad_place_id;

        public ChannelsBean(String json) {
            try {
                JSONObject object = new JSONObject(json);
                this.id = object.optString("id", "");
                this.name = object.optString("name", "");
                this.ratio = object.optInt("ratio");
                this.method = object.optString("method", "");
                this.ad_place_id = object.optString("ad_place_id", "");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
