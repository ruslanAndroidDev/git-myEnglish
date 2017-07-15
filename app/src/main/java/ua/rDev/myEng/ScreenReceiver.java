package ua.rDev.myEng;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by pk on 10.07.2017.
 */

public abstract class ScreenReceiver extends BroadcastReceiver {
    boolean isScreenOn = true;

    public ScreenReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("tag", "OnReceive");
        if (intent.getAction().equalsIgnoreCase("android.intent.action.SCREEN_ON")) {
            isScreenOn = true;
        } else if (intent.getAction().equalsIgnoreCase("android.intent.action.SCREEN_OFF")) {
            isScreenOn = false;
        } else if (intent.getAction().equalsIgnoreCase(Intent.ACTION_TIME_TICK)) {
            if (isScreenOn)
                timeTick();
        }
    }

    abstract void timeTick();
}

