package edu.neu.madcourse.groupproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import edu.neu.madcourse.groupproject.gamefragments.RandomDialog;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final int REQUEST_ACTIVITY_RECOGNITION = 1;
    private String TAG = "StepCounter";
    private int stepCount;
    private StepService stepService;
    private boolean mBound = false;
    Sensor stepDetectorSensor;
    TextView stepCounterTextView;
    SensorManager sensorManager;
    private String[] dayOfWeek;
    private int settingSteps;
    private static final String CHANNEL_ID = "channel";
    private static final String CHANNEL_NAME = "Channel Name";
    private static final String CHANNEL_DESCRIPTION = "This channel is for stickers messaging";

    private ServiceConnection connection = new ServiceConnection() {


        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            StepService.LocalBinder binder = (StepService.LocalBinder) service;
            stepService = binder.getService();
            mBound = true;
            Log.i("StepCounter", "AAAAAAAAAAAA");
            sensorManager.registerListener(stepService, stepDetectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    TextView day1;
    TextView day2;
    TextView day3;
    TextView day4;
    TextView day5;
    TextView day6;
    TextView day7;
    TextView toGo;

    ProgressBar mainBar;
    ProgressBar bar1;
    ProgressBar bar2;
    ProgressBar bar3;
    ProgressBar bar4;
    ProgressBar bar5;
    ProgressBar bar6;
    ProgressBar bar7;

    Switch mySwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        createNotificationChannel();

        stepCounterTextView = findViewById(R.id.showSteps);

        day1 = findViewById(R.id.TX_1);
        day2 = findViewById(R.id.TX_2);
        day3 = findViewById(R.id.TX_3);
        day4 = findViewById(R.id.TX_4);
        day5 = findViewById(R.id.TX_5);
        day6 = findViewById(R.id.TX_6);
        day7 = findViewById(R.id.TX_7);
        toGo = findViewById(R.id.toGo);

        mainBar = findViewById(R.id.today_step);
        bar1 = findViewById(R.id.progressBar_1);
        bar2 = findViewById(R.id.progressBar_2);
        bar3 = findViewById(R.id.progressBar_3);
        bar4 = findViewById(R.id.progressBar_4);
        bar5 = findViewById(R.id.progressBar_5);
        bar6 = findViewById(R.id.progressBar_6);
        bar7 = findViewById(R.id.progressBar_7);

        mySwitch = findViewById(R.id.switch1);

        final SharedPreferences myPrefs = getSharedPreferences("DAY_STEPS", 0);
        final SharedPreferences.Editor myEditor = myPrefs.edit();

        mySwitch.setChecked(myPrefs.getBoolean("COUNTER", true)); //false default

        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //commit prefs on change
                myEditor.putBoolean("COUNTER", isChecked);
                myEditor.apply();

                if (!isChecked) {
                    Intent intent = new Intent(getApplicationContext(), StepService.class);
                    unbindService(connection);
                    stopService(intent);
                    sensorManager.unregisterListener(stepService);
                    mBound = false;
                    Log.i(TAG, "Stopped");
                }

                if (isChecked) {
                    Intent intent = new Intent(getApplicationContext(), StepService.class);
                    startForegroundService(intent);
                    mBound = bindService(intent, connection, Context.BIND_AUTO_CREATE);
                }
            }
        });

        setStepDetectorSensor();
    }

    private void setStepDetectorSensor() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) == null) {
            Toast.makeText(this, "Null Sensor", Toast.LENGTH_LONG).show();
            Log.d(TAG, "Null Sensor");
            //the step detector sensor exists
        } else {
            stepDetectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
            if(mySwitch.isChecked()) {
                if (!isMyServiceRunning(StepService.class)) {
                    Intent intent = new Intent(this, StepService.class);
                    startForegroundService(intent);
                    Log.i(TAG, "Main Activity starting servcie");
                }
            }
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, REQUEST_ACTIVITY_RECOGNITION);
        }
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                startActivity(new Intent(this, BarChartActivity.class));
                break;
            case R.id.relativeLayout_today:
            case R.id.progressBar_7:
                Intent intent = new Intent(this, ChartActivity.class);
                intent.putExtra("DAY", 0);
                startActivity(intent);
                break;
            case R.id.progressBar_6:
                Intent intent2 = new Intent(this, ChartActivity.class);
                intent2.putExtra("DAY", 1);
                startActivity(intent2);
                break;
            case R.id.progressBar_5:
                Intent intent3 = new Intent(this, ChartActivity.class);
                intent3.putExtra("DAY", 2);
                startActivity(intent3);
                break;
            case R.id.progressBar_4:
                Intent intent4 = new Intent(this, ChartActivity.class);
                intent4.putExtra("DAY", 3);
                startActivity(intent4);
                break;
            case R.id.progressBar_3:
                Intent intent5 = new Intent(this, ChartActivity.class);
                intent5.putExtra("DAY", 4);
                startActivity(intent5);
                break;
            case R.id.progressBar_2:
                Intent intent6 = new Intent(this, ChartActivity.class);
                intent6.putExtra("DAY", 5);
                startActivity(intent6);
                break;
            case R.id.progressBar_1:
                Intent intent7 = new Intent(this, ChartActivity.class);
                intent7.putExtra("DAY", 6);
                startActivity(intent7);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_favorite) {
//            Toast.makeText(MainActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
//            return true;
//        }
        switch (id) {
            case R.id.action_Walk:
                Toast.makeText(MainActivity.this, "Walk is running", Toast.LENGTH_LONG).show();
                break;
            case R.id.action_Profile:
                Intent intent1 = new Intent(this, SettingsActivity.class);
                startActivity(intent1);
                break;
            case R.id.action_Game:
                Intent intent2 = new Intent(this, GameActivity.class);
                startActivity(intent2);
                break;

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, stepDetectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
        Intent intent = new Intent(this, StepService.class);

//        if (isMyServiceRunning(StepService.class)) {
//            startService(intent);
//        }
        if (mySwitch.isChecked()) {
            mBound = bindService(intent, connection, Context.BIND_AUTO_CREATE);
            Log.i(TAG, "onResume()");
        }

        Date currentTime = Calendar.getInstance().getTime();
        Log.i(TAG, String.valueOf(currentTime));
        String time = String.valueOf(currentTime);
        time = time.replaceAll("\\s+", "");
        time = time.substring(0, 8);
        Log.i(TAG, time);
        SharedPreferences sharedpreferences = getSharedPreferences("DAY_STEPS", 0);
        stepCount = sharedpreferences.getInt(time, 0);
        stepCounterTextView.setText(Integer.toString(stepCount));
        settingSteps = sharedpreferences.getInt("SETTING_STEPS", 2000);

        if (stepCount <= settingSteps) {
            toGo.setText("Still " + String.valueOf(settingSteps - stepCount) + " steps to go !");
        } else {
            toGo.setText("Congratulations ! You have completed the plan !");
        }

        mainBar.setProgress(stepCount * 100 / settingSteps);
        TextView[] textViews = new TextView[]{day1, day2, day3, day4, day5, day6, day7};
        dayOfWeek = calculateWeek();
        for (int i = 0; i < 7; i++) {
            textViews[i].setText(dayOfWeek[i]);
        }

        ProgressBar[] progressBars = new ProgressBar[]{bar1, bar2, bar3, bar4, bar5, bar6, bar7};
        for (int i = 6; i >= 0; i--) {
            long DAY_IN_MS = 1000 * 60 * 60 * 24;
            Date date = new Date(System.currentTimeMillis() - ((6 - i) * DAY_IN_MS));

            String barTime = String.valueOf(date);
            barTime = barTime.replaceAll("\\s+", "");
            barTime = barTime.substring(0, 8);
            progressBars[i].setProgress(sharedpreferences.getInt(barTime, 0) == 0 ? 0 : sharedpreferences.getInt(barTime, 0) * 100 / settingSteps);
        }

    }

    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
        sensorManager.unregisterListener(this);
        if (mBound) {
            unbindService(connection);
            mBound = false;
        }
    }

    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        if (mBound) {
            if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
                Log.d(TAG, "onSensorChanged called");
                stepCount = stepService.getStep();
                if (stepCount <= settingSteps) {
                    toGo.setText("Still " + String.valueOf(settingSteps - stepCount) + " steps to go !");
                } else {
                    toGo.setText("Congratulations ! You have completed the plan !");
                }
                stepCounterTextView.setText(String.valueOf(stepCount));
                mainBar.setProgress(stepCount * 100 / settingSteps);
                bar7.setProgress(stepCount * 100 / settingSteps);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private String[] calculateWeek() {
        String[] daysOfWeekArray;
        LocalDate localDate = LocalDate.now();

        // Find the day from the local date
        DayOfWeek dayOfWeek = DayOfWeek.from(localDate);

        int val = dayOfWeek.getValue();

        Log.d(TAG, "Int Value of " + dayOfWeek.name() + " - " + val);


        switch (val) {

            case 1:
                daysOfWeekArray = new String[]{"Tue.", "Wed.", "Thu.", "Fri.", "Sat.", "Sun.", "Mon."};
                return daysOfWeekArray;
            case 2:
                daysOfWeekArray = new String[]{"Wed.", "Thu.", "Fri.", "Sat.", "Sun.", "Mon.", "Tue."};
                return daysOfWeekArray;
            case 3:
                daysOfWeekArray = new String[]{"Thu.", "Fri.", "Sat.", "Sun.", "Mon.", "Tue.", "Wed."};
                return daysOfWeekArray;
            case 4:
                daysOfWeekArray = new String[]{"Fri.", "Sat.", "Sun.", "Mon.", "Tue.", "Wed.", "Thu."};
                return daysOfWeekArray;
            case 5:
                daysOfWeekArray = new String[]{"Sat.", "Sun.", "Mon.", "Tue.", "Wed.", "Thu.", "Fri."};
                return daysOfWeekArray;
            case 6:
                daysOfWeekArray = new String[]{"Sun.", "Mon.", "Tue.", "Wed.", "Thu.", "Fri.", "Sat."};
                return daysOfWeekArray;
            case 7:
                daysOfWeekArray = new String[]{"Mon.", "Tue.", "Wed.", "Thu.", "Fri.", "Sat.", "Sun."};
                return daysOfWeekArray;
        }
        return daysOfWeekArray = new String[]{"Mon.", "Tue.", "Wed.", "Thu.", "Fri.", "Sat.", "Sun."};
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        Log.i(TAG, "AAA " + "called");
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i(TAG, "AAA " + service.service.getClassName());
                return true;
            }

        }
        return false;
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW);
            channel.setDescription(CHANNEL_DESCRIPTION);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void openRandomDialog() {
        RandomDialog randomDialog = new RandomDialog();
        randomDialog.show(getSupportFragmentManager(), "randomDialog");
    }
}