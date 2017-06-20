package com.example.shruti.todolist;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Shruti on 06-05-2017.
 */

public class RingtoneService extends Service{


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.i("Localservice", "Received start id " + startId + ":" + intent);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy()
    {
        Toast.makeText(this, "on Destroy called", Toast.LENGTH_SHORT).show();
    }
}
