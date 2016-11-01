package com.example.pk.myapplication.model;

import android.graphics.Bitmap;

/**
 * Created by pk on 08.10.2016.
 */
public class VkPosts {
    String vktext;
    String hightphotoUrl;

    public VkPosts(String vktext, String hightphotoUrl) {
        this.vktext = vktext;
        this.hightphotoUrl = hightphotoUrl;
    }

    public String getVktext() {
        return vktext;
    }
    public String getHightphotoUrl() {
        return hightphotoUrl;
    }
}