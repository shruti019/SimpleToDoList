package com.example.shruti.todolist;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class AlarmActivity extends AppCompatActivity {

    AlarmManager alarm_Manager;
    TimePicker alarm_timepicker;
    TextView update_text;
    Context context;
    PendingIntent pending_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        this.context= this;

        alarm_Manager= (AlarmManager)getSystemService(ALARM_SERVICE);
        alarm_timepicker= (TimePicker)findViewById(R.id.timePicker);
        update_text= (TextView)findViewById(R.id.update_text);
        final Calendar calendar= Calendar.getInstance();
        final Intent my_intent= new Intent(this.context, Alarm_Receiver.class);
        final Button alarm_on= (Button)findViewById(R.id.button4);
        alarm_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.HOUR_OF_DAY, alarm_timepicker.getHour());
                calendar.set(Calendar.MINUTE, alarm_timepicker.getMinute());

                int hour= alarm_timepicker.getHour();
                int minute= alarm_timepicker.getMinute();
                String hour_string= String.valueOf(hour);
                String minute_string= String.valueOf(minute);

                if (hour > 12){
                    hour_string= String.valueOf(hour - 12);
                }

                if (minute < 12)
                {
                    minute_string = "0" + String.valueOf(minute);
                }

                set_alarm_text("Alarm set to " + hour_string + ":" + minute_string);

                pending_intent = PendingIntent.getBroadcast(AlarmActivity.this, 0, my_intent, PendingIntent.FLAG_UPDATE_CURRENT);

                alarm_Manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending_intent);
            }


        });

        Button alarm_off= (Button)findViewById(R.id.button5);
        alarm_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_alarm_text("Alarm Off!");

                alarm_Manager.cancel(pending_intent);
            }
        });

    }

    private void set_alarm_text(String s) {
        update_text.setText(s);
    }
}
