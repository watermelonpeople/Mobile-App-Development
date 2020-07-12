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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private String TAG = "StepCounter";

    private static final int REQUEST_ACTIVITY_RECOGNITION = 1;
    private static final int UPDATE_STEP = 2;
    private static final String INTENT_FILTER = "edu.neu.madcourse.groupproject.IntentFilter";

    private int hour = 0; //for debugging, pretend this is the hour
    private int stepCount = 0; //associated with stepCounterTextView
    private List<Integer> listStepCount = new ArrayList<>(); //associated with listTextView

    private TextView stepCounterTextView;
    private BroadcastReceiver updateReceiver;
    private TextView listStepCounterTextView;
    private BarChart barChart;

    private List<BarEntry> dataList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        barChart = findViewById(R.id.mp_BarChart);

        stepCounterTextView = findViewById(R.id.stepCounterTextView);
        stepCounterTextView.setText(Integer.toString(stepCount));

        listStepCounterTextView = findViewById(R.id.listStepCounterTextView);
        listStepCounterTextView.setText(listStepCount.toString());

        //private method to set up our step detector
        setStepDetectorSensor();

        //Next few lines make barchart look like a normal barchart
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis left = barChart.getAxisLeft();
        left.setAxisMaximum(1000f);
        left.setAxisMinimum(0f);
        barChart.getAxisRight().setEnabled(false);

        dataList = new ArrayList<>();
        BarDataSet barDataSet1 = new BarDataSet(dataList, "Dataset 1");

        BarData barData = new BarData();
        barData.addDataSet(barDataSet1);

        barChart.setData(barData);
        barChart.invalidate();
    }
/*
    private ArrayList<BarEntry> dataValues() {
        ArrayList<BarEntry> dataVals = new ArrayList<>();
        dataVals.add(new BarEntry(0, 3));
        dataVals.add(new BarEntry(1, 4));
        dataVals.add(new BarEntry(3, 6));
        dataVals.add(new BarEntry(4, 11));
        dataVals.add(new BarEntry(5, 11));
        dataVals.add(new BarEntry(6, 11));
        dataVals.add(new BarEntry(7, 11));
        dataVals.add(new BarEntry(8, 11));
        dataVals.add(new BarEntry(9, 11));
        return dataVals;
    }

 */

    private void setStepDetectorSensor() {
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
        unregisterReceiver(updateReceiver);
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepCount", stepCount);
        editor.apply();
        //unregisterReceiver(updateReceiver);
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

        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 7);
        calendar.set(Calendar.SECOND, 0);

        //Change the third parameter to see changes sooner
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60, pendingIntent);

        updateReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context, "Group Project Toast message", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Updating listStepCount");


                //CCHART DATA
                dataList.add(new BarEntry(hour, stepCount));
                BarDataSet barDataSet1 = new BarDataSet(dataList, "Dataset 1");

                BarData barData = new BarData(barDataSet1);

                barChart.setData(barData);
                barChart.invalidate();
                hour++;



                listStepCount.add(stepCount);
                listStepCounterTextView.setText(listStepCount.toString());
                //stepCount = 0;
                stepCounterTextView.setText(Integer.toString(stepCount));

            }
        };

        IntentFilter updateIntentFilter = new IntentFilter(INTENT_FILTER);
        registerReceiver(updateReceiver, updateIntentFilter);
    }

}
