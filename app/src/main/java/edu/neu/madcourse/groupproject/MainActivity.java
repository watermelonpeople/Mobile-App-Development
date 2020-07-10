package edu.neu.madcourse.groupproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private String TAG = "StepCounter";

    private static final int REQUEST_ACTIVITY_RECOGNITION = 1;
    private static final int UPDATE_STEP = 2;
    private static final String INTENT_FILTER = "edu.neu.madcourse.groupproject.IntentFilter";

    private int stepCount;
    private TextView stepCounterTextView;
    private BroadcastReceiver updateReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stepCounterTextView = findViewById(R.id.textView2);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) == null) {
            Toast.makeText(this, "Null Sensor", Toast.LENGTH_LONG).show();
            Log.d(TAG, "Null Sensor");
        } else {
            Sensor stepDetectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
            sensorManager.registerListener(this, stepDetectorSensor, SensorManager.SENSOR_DELAY_FASTEST);
            Log.d(TAG, "Registered");
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, REQUEST_ACTIVITY_RECOGNITION);
        }

    }

    //SensorEventListener required method
    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d(TAG, "Sensor Changed");
        //Get sensor values
        switch (event.sensor.getType()) {
            case (Sensor.TYPE_STEP_DETECTOR):
                stepCount += event.values[0];
                Log.d(TAG, Integer.toString(stepCount));
                stepCounterTextView.setText(Integer.toString(stepCount));
                break;
        }
    }

    //SensorEventListener required method
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Empty for now
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepCount", stepCount);
        editor.apply();

    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepCount", stepCount);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        stepCount = sharedPreferences.getInt("stepCount", 0);

        //Move this into onCreate for actual app. This method only in onResume for testing.
        restartStatsDaily();
    }

    private void restartStatsDaily() {
        //THE INTENT IS HERE
        Intent intent = new Intent(INTENT_FILTER);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), UPDATE_STEP, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 50);

        //Change the third parameter to see changes sooner
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        updateReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context, "Group Project Toast message", Toast.LENGTH_SHORT).show();
                stepCount = 0;
                stepCounterTextView.setText(Integer.toString(stepCount));
            }
        };

        IntentFilter updateIntentFilter = new IntentFilter(INTENT_FILTER);
        registerReceiver(updateReceiver, updateIntentFilter);
    }

}
