package ua.rDev.myEng;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

import ua.rDev.myEng.data.MyDataBase;
import ua.rDev.myEng.data.MyDataBaseHelper;
import ua.rDev.myEng.model.Word;

/**
 * Created by pk on 10.07.2017.
 */

public class LearningWordService extends Service {
    ArrayList<Word> data;
    ScreenReceiver receiver = new ScreenReceiver() {
        @Override
        void timeTick() {
            Log.d("tag", "timeTick");
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("tag", "onStartCommand");
        MyDataBaseHelper.loadWordwithDb(getApplicationContext(), new MyDataBaseHelper.DataLoadListener() {
            @Override
            public void onLoad(ArrayList<Word> words) {
                data = words;
                sortArray(data);
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.SCREEN_ON");
                intentFilter.addAction("android.intent.action.SCREEN_OFF");
                intentFilter.addAction(Intent.ACTION_TIME_TICK);

                registerReceiver(receiver, intentFilter);
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    private void sortArray(ArrayList<Word> words) {
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).getStatus() == MyDataBase.STATUS_STUDING)
                words.remove(i);
        }
        if (words.size() > 0) {
            //TODO Show notification
        } else {
            Log.d("tag", "stopSelf");
            stopSelf();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("tag", "OnDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
