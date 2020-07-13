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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private String TAG = "StepCounter";

    private static final int REQUEST_ACTIVITY_RECOGNITION = 1;
    private static final int UPDATE_STEP_DAY_ID = 2;
    private static final int UPDATE_STEP_MINUTE_ID = 3;
    private static final String INTENT_FILTER_DAY = "edu.neu.madcourse.groupproject.IntentFilterDay";
    private static final String INTENT_FILTER_MINUTE = "edu.neu.madcourse.groupproject.IntentFilterMinute";

    private int day; //for debugging, pretend this is the day
    private int stepCount; //associated with stepCounterTextView
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

        //Set up stepCounter
        stepCounterTextView = findViewById(R.id.stepCounterTextView);
        stepCount = 0;
        stepCounterTextView.setText(Integer.toString(stepCount));

        //Set up listStepCounter
        listStepCounterTextView = findViewById(R.id.listStepCounterTextView);
        listStepCount.add(0);
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

        //Set up bar graph
        dataList = new ArrayList<>();
        dataList.add(new BarEntry(day, stepCount));
        BarDataSet barDataSet1 = new BarDataSet(dataList, "Dataset 1");

        BarData barData = new BarData();
        barData.addDataSet(barDataSet1);

        barChart.setData(barData);
        barChart.invalidate();
    }

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

                //Testing hopefully doesnt break the phone

                dataList.remove(dataList.size() - 1);
                dataList.add(new BarEntry(day, stepCount));

                BarDataSet barDataSet1 = new BarDataSet(dataList, "Dataset 1");

                BarData barData = new BarData(barDataSet1);

                barChart.setData(barData);
                barChart.invalidate();

                listStepCount.remove(listStepCount.size() - 1);
                listStepCount.add(stepCount);

                listStepCounterTextView.setText(listStepCount.toString());

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
        stepCounterTextView.setText(Integer.toString(stepCount));

        //Move this into onCreate for actual app. This method only in onResume for testing.
        restartStatsDaily();
    }

    private void restartStatsDaily() {
        //THE INTENT IS HERE
        Intent intentDay = new Intent(INTENT_FILTER_DAY);

        PendingIntent pendingIntentDay = PendingIntent.getBroadcast(getApplicationContext(), UPDATE_STEP_DAY_ID, intentDay, 0);

        AlarmManager alarmManagerDay = (AlarmManager) getSystemService(ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 16);
        calendar.set(Calendar.SECOND, 0);

        //Change the third parameter to see changes sooner
        //Updates every 12 minutes
        alarmManagerDay.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 3, pendingIntentDay);


        updateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //temp flipped intent_filter_minute.
                //why won't intent_filter_minute get procced?
                if (intent.getAction() != null) {
                    Log.d(TAG, intent.getAction());
                }
                if (INTENT_FILTER_DAY.equals(intent.getAction())) {
                    Toast.makeText(context, "Group Project Toast Day", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Updating today");

                    //reset stepCount since we are starting a new day
                    stepCount = 0;
                    stepCounterTextView.setText(Integer.toString(stepCount));

                    //CHART DATA
                    day++;
                    dataList.add(new BarEntry(day, stepCount));

                    //Removing Old Weekly Data
                    if (dataList.size() > 7) {
                        dataList.remove(0);
                    }

                    BarDataSet barDataSet1 = new BarDataSet(dataList, "Dataset 1");

                    BarData barData = new BarData(barDataSet1);

                    barChart.setData(barData);
                    barChart.invalidate();


                    listStepCount.add(stepCount);
                    if (listStepCount.size() > 7) {
                        listStepCount.remove(0);
                    }
                    listStepCounterTextView.setText(listStepCount.toString());
                    //stepCount = 0;
                    //stepCounterTextView.setText(Integer.toString(stepCount));
                }
            }
        };

        IntentFilter updateIntentFilter = new IntentFilter(INTENT_FILTER_DAY);
        registerReceiver(updateReceiver, updateIntentFilter);

    }

}
