package com.example.shruti.todolist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Shruti on 06-05-2017.
 */

public class Alarm_Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("In the receiver", "");
        Intent service_intent= new Intent(context, RingtoneService.class);
        context.startService(service_intent);
    }
}
