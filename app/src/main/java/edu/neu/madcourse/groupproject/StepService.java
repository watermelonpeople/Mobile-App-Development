package edu.neu.madcourse.groupproject;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.LocusId;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class StepService extends Service implements SensorEventListener {
    private static final String TAG = "StepService";
    private static final String CHANNELID = "channel";
    private final IBinder mBinder = new LocalBinder();
    private int step;
    private int energyCount;
    private String time;
    private boolean randomMotivation;


    public class LocalBinder extends Binder {
        StepService getService() {
            // Return this instance of LocalService so clients can call public methods
            return StepService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("StepService", "Service Started");
        Date currentTime = Calendar.getInstance().getTime();
        time = String.valueOf(currentTime);
        time = time.replaceAll("\\s+", "");
        time = time.substring(0, 8);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("DAY_STEPS", 0);

        step = pref.getInt(time, 0);
        energyCount = pref.getInt("energyCount", 0);

        Intent intent1 = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent1, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNELID)
                .setContentTitle("Good Work, keep going !")
                .setContentText("Check your steps today ！")
                .setSmallIcon(R.mipmap.ic_icon)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
        return START_NOT_STICKY;


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (time != null) {
            Date currentTime = Calendar.getInstance().getTime();
//        Calendar calendar = (Calendar)Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY,2);
//        Log.i("StepService", String.valueOf(calendar.getTime()));
            String newTime = String.valueOf(currentTime);
            newTime = newTime.replaceAll("\\s+", "");
            Log.i("StepService", newTime);
            String hour = newTime.substring(8, 10);
            newTime = newTime.substring(0, 8);
            storeHourlyData(newTime, hour);

            if (!newTime.equals(time)) {
                time = newTime;
                step = 0;
            }
            SharedPreferences preferences = getApplicationContext().getSharedPreferences("DAY_STEPS", 0);

            Log.i("StepService", "Walked " + time);
            if( preferences.getBoolean("COUNTER",true)) {
                step += event.values[0];
                SharedPreferences prefs = getSharedPreferences("DAY_STEPS", MODE_PRIVATE);
                energyCount = prefs.getInt("energyCount", 0);
                energyCount += event.values[0];
            }
            Log.i("StepService", String.valueOf(step));

            SharedPreferences pref = getApplicationContext().getSharedPreferences("DAY_STEPS", 0);
            SharedPreferences.Editor editor = pref.edit();

            editor.putInt(time, step);
            editor.apply();

            editor.putInt("energyCount", energyCount);
            editor.apply();

            //Setting up random motivation
            Random random = new Random();
            int randomNumber = random.nextInt(5);
            //lucky number
            if (randomNumber == 1) {
                randomMotivation = true;
                editor.putBoolean("randomMotivation", true);
                editor.apply();
                Log.d(TAG, "randomMotivation is true");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("StepService", "Service Destroyed");
    }

    public int getStep() {

        return this.step;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void storeHourlyData(String date, String hour) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("HOUR_STEPS", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(date + hour, step);
        Log.i("StepService", date + hour + " Stored");
        editor.apply();
    }

}