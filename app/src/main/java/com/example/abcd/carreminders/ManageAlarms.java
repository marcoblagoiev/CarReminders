package com.example.abcd.carreminders;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ManageAlarms {
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    static int i = 0;


    public void setAlarm(Context context, String date, String type, String licence) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        i = sharedpreferences.getInt("lastId", 0);
        Log.d("DebugAlarm", "Getting lastId " + i);
        android.app.AlarmManager alarmManager = (android.app.AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        alarmIntent.putExtra("type", type);
        alarmIntent.putExtra("licence", licence);
        //the second param was 0
        PendingIntent pendingIntent;

        Log.d("DebugAlarm", "date is " + date);

        //converting the string to a calendar
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            cal.setTime(sdf.parse(date));// all done
        } catch (Exception e) {
            Log.d("DebugAlarm", "!!!!!!!!!!!!!In exception ");
        }
        Log.d("DebugAlarm", "cal is " + cal.toString());

        cal.set(Calendar.HOUR_OF_DAY, 10);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 0);

        Calendar calTest = Calendar.getInstance();
        //Log.d("DebugAlarm", "calTest" + calTest.toString());

        Calendar cal2 = (Calendar) cal.clone();
        cal2.add(Calendar.MONTH, -1);
        Calendar cal3 = (Calendar) cal.clone();
        cal3.add(Calendar.DAY_OF_MONTH, -7);
        Calendar cal4 = (Calendar) cal.clone();
        cal4.add(Calendar.DAY_OF_MONTH, -1);

        Log.d("DebugAlarm", "cal is " + cal.toString());
        Log.d("DebugAlarm", "cal2 is " + cal2.toString());
        Log.d("DebugAlarm", "cal3 is " + cal3.toString());
        Log.d("DebugAlarm", "cal4 is " + cal4.toString());
        Log.d("DebugAlarm", "calTest is " + calTest.toString());

        //Random r=new Random();

        if (sharedpreferences.getBoolean("month", true) && cal2.after(calTest)) {
            //i=r.nextInt(100000);
            alarmIntent.putExtra("period", "month");
            pendingIntent = PendingIntent.getBroadcast(context, i++, alarmIntent, 0);
            alarmManager.set(android.app.AlarmManager.RTC, cal2.getTimeInMillis(), pendingIntent);
            Log.d("DebugAlarm", "Added alarm for a month");
            Log.d("DebugAlarm", "i is " + i);
        }

        if (sharedpreferences.getBoolean("week", true) && cal3.after(calTest)) {
            //i=r.nextInt(100000);
            alarmIntent.putExtra("period", "week");
            pendingIntent = PendingIntent.getBroadcast(context, i++, alarmIntent, 0);
            alarmManager.set(android.app.AlarmManager.RTC, cal3.getTimeInMillis(), pendingIntent);
            Log.d("DebugAlarm", "Added alarm for a week");
            Log.d("DebugAlarm", "i is " + i);
        }

        if (sharedpreferences.getBoolean("day", true) && cal4.after(calTest)) {
            //i=r.nextInt(100000);
            alarmIntent.putExtra("period", "day");
            pendingIntent = PendingIntent.getBroadcast(context, i++, alarmIntent, 0);
            alarmManager.set(android.app.AlarmManager.RTC, cal4.getTimeInMillis(), pendingIntent);
            Log.d("DebugAlarm", "Added alarm for a day");
            Log.d("DebugAlarm", "i is " + i);
        }
        SharedPreferences.Editor editor = sharedpreferences.edit();
        Log.d("DebugAlarm", "Setting lastId to " + i);
        editor.putInt("lastId", i);
        editor.commit();
        Log.d("DebugAlarm", "Exiting setAlarm");
    }

    public void deleteAlarms(Context context) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        int j = 0;
        int i = sharedpreferences.getInt("lastId", 1);
        android.app.AlarmManager alarmManager = (android.app.AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        //the second param was 0
        PendingIntent pendingIntent;

        while (j <= i) {
            pendingIntent = PendingIntent.getBroadcast(context, j, alarmIntent, 0);
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
            Log.d("DebugAlarm", "Canceling the event with the id " + j);
            j++;
        }
        i = 0;
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("lastId", i);
        editor.commit();
        setAlarmForAllCars(context);
    }

    public void setAlarmForAllCars(Context context) {
        JCGSQLiteHelper db = new JCGSQLiteHelper(context);
        List<Car> list;
        // get all the cars
        list = db.getAllCars();


        List<String> stringsList = new ArrayList<String>(list.size());
        for (Car car : list) {
            stringsList.add(car.toString());
        }

        Log.d("DebugAlarm", "---------------------------------------------------------------");

        Log.d("DebugAlarm", "The list is: " + list.toString());
        Log.d("DebugAlarm", "The list is: " + stringsList.toString());
        //Log.d("DebugAlarm", "The id is: " + list.get(0).toString());
        Log.d("DebugAlarm", "The size of the list is: " + list.size());
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String type="", date="";

        for (int i = 0; i < list.size(); i++) {
            Log.d("DebugAlarm", "THE LIST SIZE IS " + list.size());
            Log.d("DebugAlarm", "The id inside the 'for' is: " + Integer.parseInt(list.get(i).toString()));
            Log.d("DebugAlarm", "The id inside the 'for' is: " + list.get(i).toString());
            Car car = db.findCar(Integer.parseInt(list.get(i).toString()));
            String licence = car.getLicence();
            //TODO change this is we add more fields
            for (int j=0; j< 14; j++){
                if (j==0){
                    type = "insurance";
                    date = car.getInsurance();
                } else if (j==1){
                    type = "inspection";
                    date = car.getInspection();
                } else if (j==2){
                    type = "tax";
                    date = car.getTax();
                } else if (j==3){
                    type = "fire";
                    date = car.getFire();
                } else if (j==4){
                    type = "medical";
                    date = car.getMedical();
                } else if (j==5){
                    type = "rate";
                    date = car.getRate();
                } else if (j==6){
                    type = "oil";
                    date = car.getRate();
                } else if (j==7){
                    type = "parking";
                    date = car.getRate();
                } else if (j==8){
                    type = "eairfilter";
                    date = car.getRate();
                } else if (j==9){
                    type = "cairfilter";
                    date = car.getRate();
                } else if (j==10){
                    type = "bettery";
                    date = car.getRate();
                } else if (j==11){
                    type = "antifreeze";
                    date = car.getRate();
                } else if (j==12){
                    type = "tire";
                    date = car.getRate();
                } else if (j==13){
                    type = "wiper";
                    date = car.getRate();
                }
                i =  sharedpreferences.getInt("lastId", 0);
                Log.d("DebugAlarm", "Getting lastId " + i);
                android.app.AlarmManager alarmManager = (android.app.AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                Intent alarmIntent = new Intent(context, AlarmReceiver.class);
                alarmIntent.putExtra("type", type);
                alarmIntent.putExtra("licence", licence);
                //the second param was 0
                PendingIntent pendingIntent;

                Log.d("DebugAlarm", "date is " + date);

                //converting the string to a calendar
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    cal.setTime(sdf.parse(date));// all done
                } catch (Exception e) {
                    Log.d("DebugAlarm", "!!!!!!!!!!!!!In exception ");
                }
                Log.d("DebugAlarm", "cal is " + cal.toString());

                Log.d("DebugAlarm", ".................................................");

                cal.set(Calendar.HOUR_OF_DAY, 10);
                cal.set(Calendar.MINUTE, 00);
                cal.set(Calendar.SECOND, 0);

                Calendar calTest = Calendar.getInstance();
                //Log.d("DebugAlarm", "calTest" + calTest.toString());

                Calendar cal2 = (Calendar) cal.clone();
                cal2.add(Calendar.MONTH, -1);
                Calendar cal3 = (Calendar) cal.clone();
                cal3.add(Calendar.DAY_OF_MONTH, -7);
                Calendar cal4 = (Calendar) cal.clone();
                cal4.add(Calendar.DAY_OF_MONTH, -1);

                Log.d("DebugAlarm", "cal is " + cal.toString());
                Log.d("DebugAlarm", "cal2 is " + cal2.toString());
                Log.d("DebugAlarm", "cal3 is " + cal3.toString());
                Log.d("DebugAlarm", "cal4 is " + cal4.toString());
                Log.d("DebugAlarm", "calTest is " + calTest.toString());

                //Random r=new Random();

                if (sharedpreferences.getBoolean("month", true) && cal2.after(calTest)) {
                    //i=r.nextInt(100000);
                    alarmIntent.putExtra("period", "month");
                    pendingIntent = PendingIntent.getBroadcast(context, i++, alarmIntent, 0);
                    alarmManager.set(android.app.AlarmManager.RTC, cal2.getTimeInMillis(), pendingIntent);
                    Log.d("DebugAlarm", "Added alarm for a month");
                    Log.d("DebugAlarm", "i is " + i);
                }

                if (sharedpreferences.getBoolean("week", true) && cal3.after(calTest)) {
                    //i=r.nextInt(100000);
                    alarmIntent.putExtra("period", "week");
                    pendingIntent = PendingIntent.getBroadcast(context, i++, alarmIntent, 0);
                    alarmManager.set(android.app.AlarmManager.RTC, cal3.getTimeInMillis(), pendingIntent);
                    Log.d("DebugAlarm", "Added alarm for a week");
                    Log.d("DebugAlarm", "i is " + i);
                }

                if (sharedpreferences.getBoolean("day", true) && cal4.after(calTest)) {
                    //i=r.nextInt(100000);
                    alarmIntent.putExtra("period", "day");
                    pendingIntent = PendingIntent.getBroadcast(context, i++, alarmIntent, 0);
                    alarmManager.set(android.app.AlarmManager.RTC, cal4.getTimeInMillis(), pendingIntent);
                    Log.d("DebugAlarm", "Added alarm for a day");
                    Log.d("DebugAlarm", "i is " + i);

                }

            }

            }
        }



}