package edu.neu.madcourse.groupproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SettingsActivity extends AppCompatActivity {

    EditText planningSteps;
    EditText userFeet;
    EditText userInch;
    Spinner spinner_steps;
    Spinner spinner_feet;
    Spinner spinner_inch;
    Spinner spinner_pound;
    RadioButton buttonM;
    RadioButton buttonF;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences("DAY_STEPS", 0);
        int setting_steps = sharedPreferences.getInt("SETTING_STEPS", -1);
        int setting_feet = sharedPreferences.getInt("SETTING_FEET", -1);
        int setting_inch = sharedPreferences.getInt("SETTING_INCH", -1);
        int setting_pounds = sharedPreferences.getInt("SETTING_POUNDS", -1);
        int setting_gender = sharedPreferences.getInt("SETTING_GENDER", -1);

        Log.i("Steps", String.valueOf(setting_feet));
        Log.i("Steps", String.valueOf(setting_inch));
        Log.i("Steps", String.valueOf(setting_pounds));

        buttonM = findViewById(R.id.radioM);

        buttonF = findViewById(R.id.radioF);

        if(setting_gender != -1){
            if(setting_gender ==1){
                buttonM.setChecked(true);
            }else {
                buttonF.setChecked(true);
            }
        }

        spinner_steps = findViewById(R.id.steps_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.steps_array, R.layout.spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_steps.setAdapter(adapter);

        if (setting_steps != -1) {
            CharSequence chars_steps = String.valueOf(setting_steps);
            int step_position = adapter.getPosition(chars_steps);
            spinner_steps.setSelection(step_position);
        }

        spinner_feet = findViewById(R.id.feet_spinner);

        ArrayAdapter<CharSequence> adapter_feet = ArrayAdapter.createFromResource(this,
                R.array.feet_array, R.layout.spinner_item);

        adapter_feet.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_feet.setAdapter(adapter_feet);


        if (setting_feet != -1) {
            CharSequence chars_feet = String.valueOf(setting_feet);
            int feet_position = adapter_feet.getPosition(chars_feet);
            Log.i("Step", "position" + feet_position);
            spinner_feet.setSelection(feet_position);
        }


        spinner_inch = findViewById(R.id.inch_spinner);

        ArrayAdapter<CharSequence> adapter_inch = ArrayAdapter.createFromResource(this,
                R.array.inch_array, R.layout.spinner_item);

        adapter_inch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_inch.setAdapter(adapter_inch);

        if (setting_inch != -1) {
            CharSequence chars_inch = String.valueOf(setting_inch);
            int inch_position = adapter_inch.getPosition(chars_inch);
            spinner_inch.setSelection(inch_position);
        }

        spinner_pound = findViewById(R.id.weight_spinner);

        ArrayAdapter<CharSequence> adapter_pound = ArrayAdapter.createFromResource(this,
                R.array.pound_array, R.layout.spinner_item);

        adapter_pound.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_pound.setAdapter(adapter_pound);

        if (setting_pounds != -1) {
            CharSequence chars_pounds = String.valueOf(setting_pounds);
            int pound_position = adapter_pound.getPosition(chars_pounds);
            spinner_pound.setSelection(pound_position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
                finish();
                break;
            case R.id.action_Profile:
                Toast.makeText(SettingsActivity.this, "Settings clicked", Toast.LENGTH_LONG).show();
                break;
            case R.id.action_Game:
                finish();
                startActivity(new Intent(this, GameActivity.class));
                break;

        }


        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                String selectedStep = spinner_steps.getSelectedItem().toString();
                String selectedFeet = spinner_feet.getSelectedItem().toString();
                String selectedInch = spinner_inch.getSelectedItem().toString();
                String selectedLbs = spinner_pound.getSelectedItem().toString();
                if (buttonM.isChecked() || buttonF.isChecked()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("SETTING_STEPS", Integer.parseInt(selectedStep));
                    editor.putInt("SETTING_FEET", Integer.parseInt(selectedFeet));
                    editor.putInt("SETTING_INCH", Integer.parseInt(selectedInch));
                    editor.putInt("SETTING_POUNDS", Integer.parseInt(selectedLbs));
                    editor.putInt("SETTING_GENDER", buttonM.isChecked() ? 1 : 0);
                    editor.apply();
                    Toast.makeText(this, "Information Set!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "Please select the gender please", Toast.LENGTH_LONG).show();
                }

        }
    }
}
