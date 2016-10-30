package com.example.pk.myapplication.model;

import android.graphics.Bitmap;

/**
 * Created by pk on 08.10.2016.
 */
public class VkPosts {
    String vktext;
    String lowphotoUrl;
    String hightphotoUrl;

    public VkPosts(String vktext, String lowphotoUrl, String hightphotoUrl) {
        this.vktext = vktext;
        this.lowphotoUrl = lowphotoUrl;
        this.hightphotoUrl = hightphotoUrl;
    }

    public String getVktext() {
        return vktext;
    }

    public String getLowphotoUrl() {
        return lowphotoUrl;
    }

    public String getHightphotoUrl() {
        return hightphotoUrl;
    }
}