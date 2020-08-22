package edu.neu.madcourse.groupproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

public class ChartActivity extends AppCompatActivity {

    private static final String TAG = "ChartActivity";
    private String currentDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        int day = getIntent().getIntExtra("DAY", 0);
        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        Date currentTime = new Date(System.currentTimeMillis() - (day * DAY_IN_MS));
        currentDate = String.valueOf(currentTime);
        currentDate = currentDate.replaceAll("\\s+", "");
        currentDate = currentDate.substring(0, 8);

        SharedPreferences sharedpreferences = getSharedPreferences("DAY_STEPS", 0);
        int step = sharedpreferences.getInt(currentDate, 0);
        int height_feet = sharedpreferences.getInt("SETTING_FEET", -1);
        int height_inch = sharedpreferences.getInt("SETTING_INCH", -1);
        int weight = sharedpreferences.getInt("SETTING_POUNDS", -1);


        TextView totalStep = findViewById(R.id.total_step);
        TextView totalLength = findViewById(R.id.total_length);
        TextView totalCal = findViewById(R.id.total_cal);

        if (height_feet == -1 || height_inch == -1 || weight == -1) {
            totalLength.setText("Distance: " + String.valueOf((int) (step * 0.7)) + "m");
        } else {
            switch (height_feet) {
                case 5:
                    totalLength.setText("Distance: " + String.valueOf((int) (step * 0.7)) + "m");
                    break;
                case 6:
                    totalLength.setText("Distance: " + String.valueOf((int) (step * 0.77)) + "m");
                    break;
                case 7:
                    totalLength.setText("Distance: " + String.valueOf((int) (step * 0.8)) + "m");
                    break;
                case 8:
                    totalLength.setText("Distance: " + String.valueOf((int) (step * 0.86)) + "m");
                    break;
                default:
                    totalLength.setText("Distance: " + String.valueOf((int) (step * 0.7)) + "m");
                    break;
            }

        }
        totalStep.setText("Steps: " + String.valueOf(step));
        totalCal.setText("Calories: " + String.valueOf((int) (step * 0.04)));
        LineChart lineChart = findViewById(R.id.line_chart);
        lineChart.getAxisRight().setAxisMinimum(0f);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getAxisLeft().setAxisMinimum(0f);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


        LineDataSet lineDataSet = new LineDataSet(setDateSet(), "Hourly Steps");

        lineDataSet.setColor(getResources().getColor(R.color.colorRoundCounter));
        lineDataSet.setCircleColor(getResources().getColor(R.color.colorRoundCounter));

        lineDataSet.setDrawValues(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);

        LineData lineData = new LineData(dataSets);
        lineChart.setData(lineData);
        lineChart.getDescription().setEnabled(false);
        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.invalidate();


        TextView textView = findViewById(R.id.details_date);


    }

    private ArrayList<Entry> setDateSet() {
        SharedPreferences prefs = getSharedPreferences("HOUR_STEPS", Context.MODE_PRIVATE);
        int[] ints = new int[24];
        for (int i = 0; i < 24; i++) {
            ints[i] = prefs.getInt(currentDate + helper(i), 0);
            Log.i("StepChart", "Getting " + currentDate + helper(i));
        }
        for (int i = 1; i < 24; i++) {
            if (ints[i] == 0) ints[i] = ints[i - 1];
        }
        for (int i = 0; i < 24; i++) {
            Log.i("StepChart", String.valueOf(i) + String.valueOf(ints[i]));
        }
        ArrayList<Entry> arrayList = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            arrayList.add(new Entry(i, ints[i]));
        }
//        arrayList.add(new Entry(0, 50));
//        arrayList.add(new Entry(1, 90));
//        arrayList.add(new Entry(2, 150));
//        arrayList.add(new Entry(3, 200));
//        arrayList.add(new Entry(4, 266));
//        arrayList.add(new Entry(5, 1800));
//        arrayList.add(new Entry(6, 1800));
//        arrayList.add(new Entry(7, 1900));
//        arrayList.add(new Entry(8, 2000));
//        arrayList.add(new Entry(9, 2100));
//        arrayList.add(new Entry(10, 2100));
//        arrayList.add(new Entry(11, 2100));
//        arrayList.add(new Entry(12, 2100));
//        arrayList.add(new Entry(13, 2100));
//        arrayList.add(new Entry(14, 2100));
//        arrayList.add(new Entry(15, 2100));
//        arrayList.add(new Entry(16, 2100));
//        arrayList.add(new Entry(17, 2100));
//        arrayList.add(new Entry(18, 2100));
//        arrayList.add(new Entry(19, 2100));
//        arrayList.add(new Entry(20, 2100));
//        arrayList.add(new Entry(21, 2100));
//        arrayList.add(new Entry(22, 2100));
//        arrayList.add(new Entry(23, 2100));
//        arrayList.add(new Entry(24, 2100));

        return arrayList;


    }

    private String helper(int a) {
        if (a < 10) return "0" + String.valueOf(a);
        return String.valueOf(a);
    }
}

