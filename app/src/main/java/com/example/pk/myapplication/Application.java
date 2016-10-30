package com.example.pk.myapplication;

import android.content.Intent;

import com.example.pk.myapplication.view.MainActivity;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

/**
 * Created by pk on 03.10.2016.
 */
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        vkAccessTokenTracker.startTracking();
        VKSdk.initialize(this);
    }

    VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {
                Intent intent = new Intent(Application.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    };
}
