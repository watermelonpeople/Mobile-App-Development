package edu.neu.madcourse.groupproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

import edu.neu.madcourse.groupproject.gamefragments.HelpFragment;
import edu.neu.madcourse.groupproject.gamefragments.RandomDialog;
import edu.neu.madcourse.groupproject.gamefragments.WorkFragment;
import edu.neu.madcourse.groupproject.gamefragments.StudyFragment;
import edu.neu.madcourse.groupproject.gamefragments.RestRelaxFragment;

public class GameActivity extends AppCompatActivity implements RandomDialog.RandomDialogListener {

    private static String TAG = "groupproject.GameActivity";

    public int energyCount = 0;
    public int hungerCount = 50;
    public int moneyCount = 100;
    public int moodCount = 50;


    private boolean randomMotivation;
    private static final long START_TIME_IN_MILLIS = 20000;
    private TextView mTextViewCountDown;

    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private long mEndTime;


    BottomNavigationView bottomNavViewId;
    FrameLayout frameLayoutId;

    public TextView energyTextView;
    public TextView hungerTextView;
    public TextView moneyTextView;
    public TextView moodTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        bottomNavViewId = findViewById(R.id.bottomNavViewId);
        frameLayoutId = findViewById(R.id.frameLayoutId);
        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        if (mTimerRunning == true) {
            mTextViewCountDown.setVisibility(View.VISIBLE);
        }
        else {
            mTextViewCountDown.setVisibility(View.INVISIBLE);
        }

        SharedPreferences pref = getApplicationContext().getSharedPreferences("DAY_STEPS", MODE_PRIVATE);
        energyCount = pref.getInt("energyCount", 0);
        hungerCount = pref.getInt("hungerCount", 50);
        moneyCount = pref.getInt("moneyCount", 100);
        moodCount = pref.getInt("moodCount", 50);

        energyTextView = findViewById(R.id.energyTextView);
        hungerTextView = findViewById(R.id.hungerTextView);
        moneyTextView = findViewById(R.id.moneyTextView);
        moodTextView = findViewById(R.id.moodTextView);

        energyTextView.setText(Integer.toString(energyCount));
        hungerTextView.setText(Integer.toString(hungerCount));
        moneyTextView.setText(Integer.toString(moneyCount));
        moodTextView.setText(Integer.toString(moodCount));

        randomMotivation = pref.getBoolean("randomMotivation", false);
        Log.d(TAG, "onCreate mTimeLeftInMillis: " + Long.toString(mTimeLeftInMillis));

        /*
        //Need to make sure mTimerRunning is false so we can avoid overlapping Random reward timers running
        if (randomMotivation == true && mTimerRunning == false) {
            openRandomDialog();
            randomMotivation = false;
            SharedPreferences.Editor editor = pref.edit();

            editor.putBoolean("randomMotivation", false);
            editor.apply();
        }
         */

        bottomNavViewId.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.restRelaxId:
                        setFragment(new RestRelaxFragment());
                        return true;

                    case R.id.workId:
                        setFragment(new WorkFragment());
                        return true;

                    case R.id.studyId:
                        setFragment(new StudyFragment());
                        return true;

                    case R.id.helpId:
                        setFragment(new HelpFragment());
                        return true;

                    default:
                        return false;
                }

            }
        });

        RestRelaxFragment restRelaxFragment = new RestRelaxFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayoutId, restRelaxFragment).commit();

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutId, fragment);
        fragmentTransaction.commit();
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
        switch (id){
            case R.id.action_Walk:
                finish();
                break;
            case R.id.action_Profile:
                finish();
                Intent intent = new Intent(this,SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.action_Game:
                Toast.makeText(GameActivity.this, "Game clicked", Toast.LENGTH_LONG).show();
                break;

        }


        return super.onOptionsItemSelected(item);
    }

    /*
    public int getEnergyCount() {
        return energyCount;
    }

    public int getHungerCount() {
        return hungerCount;
    }

    public int getMoneyCount() {
        return moneyCount;
    }

    public int getMoodCount() {
        return moodCount;
    }
    */

    public void setEnergyText(int value){
        energyTextView = findViewById(R.id.energyTextView);
        energyTextView.setText(Integer.toString(value));
    }

    public void setHungerText(int value){
        hungerTextView = findViewById(R.id.hungerTextView);
        if (value > 100) {
            value = 100;
        }
        hungerTextView.setText(Integer.toString(value));
    }

    public void setMoneyText(int value){
        moneyTextView = findViewById(R.id.moneyTextView);
        moneyTextView.setText(Integer.toString(value));
    }

    public void setMoodText(int value){
        moodTextView = findViewById(R.id.moodTextView);
        if (value > 100) {
            value = 100;
        }
        moodTextView.setText(Integer.toString(value));
    }

    public void openRandomDialog() {
        RandomDialog randomDialog = new RandomDialog();
        randomDialog.show(getSupportFragmentManager(), "randomDialog");
    }

    //This allows communication with RandomDialog.
    @Override
    public void chosenReward(int selection) {
        if (selection == 1) {
            startTimer("full");
            Toast.makeText(this, "1 selected", Toast.LENGTH_SHORT).show();
        }
        else {
            energyCount += 1000;
            setEnergyText(energyCount);
            SharedPreferences prefs = getSharedPreferences("DAY_STEPS", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("energyCount", energyCount);
            editor.apply();
            Toast.makeText(this, "Gained 1000 energy", Toast.LENGTH_SHORT).show();
        }
    }

    private void startTimer(String status) {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        Log.d(TAG, "startTimer mTimeLeftInMillis: " + mTimeLeftInMillis);
        mTextViewCountDown.setVisibility(View.VISIBLE);

        //We only want to change our startEnergyCount if we are starting a full timer, (ie not resuming).
        if (status.equals("full")) {
            SharedPreferences prefs = getSharedPreferences("DAY_STEPS", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            int startEnergyCount = energyCount;
            editor.putInt("startEnergyCount", startEnergyCount);
            editor.apply();
        }
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("DAY_STEPS", 0);
                int endEnergyCount = pref.getInt("energyCount", 0);
                int startEnergyCount = pref.getInt("startEnergyCount", 0);
                int diffEnergyCount = endEnergyCount - startEnergyCount;
                Log.d(TAG, "endEnergyCount: " + endEnergyCount + "\nstartEnergyCount: " + startEnergyCount);
                energyCount += diffEnergyCount * 100;
                setEnergyText(energyCount);

                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("energyCount", energyCount);
                editor.apply();

                Toast.makeText(GameActivity.this, "You gained  " + diffEnergyCount * 100 + " energyCount thanks to your x100 boost!", Toast.LENGTH_SHORT).show();
                mTimerRunning = false;
                mTimeLeftInMillis = START_TIME_IN_MILLIS;
                mTextViewCountDown.setVisibility(View.INVISIBLE);
            }
        }.start();
        mTimerRunning = true;
    }

    private void updateCountDownText() {
        Log.d(TAG, "updateCountDownText called");
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop called");
        Log.d(TAG, "onStop mTimeLeftInMillis: " + mTimeLeftInMillis);
        Log.d(TAG, "onStop energyCount: " + energyCount);
        SharedPreferences prefs = getSharedPreferences("DAY_STEPS", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("energyCount", energyCount);
        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);

        editor.apply();

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = getSharedPreferences("DAY_STEPS", MODE_PRIVATE);
        mTimeLeftInMillis = prefs.getLong("millisLeft", START_TIME_IN_MILLIS);
        mTimerRunning = prefs.getBoolean("timerRunning", false);
        updateCountDownText();

        Log.d(TAG, "onStart energyCount: " + energyCount);

        //Need to make sure mTimerRunning is false so we can avoid overlapping Random reward timers running
        if (randomMotivation == true && mTimerRunning == false) {
            openRandomDialog();
            randomMotivation = false;
            SharedPreferences.Editor editor = prefs.edit();

            editor.putBoolean("randomMotivation", false);
            editor.apply();
        }

        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();

            if (mTimeLeftInMillis < 0) {
                //Same code as in onFinish(); perhaps move into separate method?
                SharedPreferences pref = getApplicationContext().getSharedPreferences("DAY_STEPS", 0);
                int endEnergyCount = pref.getInt("energyCount", 0);
                int startEnergyCount = pref.getInt("startEnergyCount", 0);
                int diffEnergyCount = endEnergyCount - startEnergyCount;
                Log.d(TAG, "onStart endEnergyCount: " + endEnergyCount + "\nonStart startEnergyCount: " + startEnergyCount);
                energyCount += diffEnergyCount * 100;
                setEnergyText(energyCount);

                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("energyCount", energyCount);
                editor.apply();

                Toast.makeText(GameActivity.this, "You gained  " + diffEnergyCount * 100 + " energyCount thanks to your x100 boost!", Toast.LENGTH_SHORT).show();
                mTimeLeftInMillis = START_TIME_IN_MILLIS;
                mTimerRunning = false;
                mTextViewCountDown.setVisibility(View.INVISIBLE);

                updateCountDownText();
            }
            else if (mTimeLeftInMillis > 0 && mTimeLeftInMillis < START_TIME_IN_MILLIS) {
                startTimer("resume");
            }
        }

    }

}
