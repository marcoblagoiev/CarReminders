package com.example.abcd.carreminders;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


//needed so we can restart the alarms after phone reboot
public class BootBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context pContext, Intent intent) {
        Log.d("DebugAlarm", "ON BOOTBraodcastReceiver");
        // Do your work related to alarm manager
        ManageAlarms ma = new ManageAlarms();
        ma.setAlarmForAllCars(pContext);
    }
}