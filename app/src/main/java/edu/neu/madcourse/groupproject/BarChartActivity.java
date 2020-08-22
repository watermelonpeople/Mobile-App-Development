package edu.neu.madcourse.groupproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class BarChartActivity extends AppCompatActivity {

    BarChart barChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);


        barChart = findViewById(R.id.mp_BarChart);
        barChart.getDescription().setEnabled(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setLabelCount(7, true);
        //xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(false);
        xAxis.setDrawGridLines(false);

        String[] week = calculateWeek();
        xAxis.setValueFormatter(new MyCustomFormatter(week));


        YAxis left = barChart.getAxisLeft();
//        left.setAxisMaximum(1000f);
        left.setAxisMinimum(0f);
        left.setDrawGridLines(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getAxisRight().setAxisMinimum(0f);

        //Set up bar graph
        int day = 6;
        SharedPreferences sharedpreferences = getSharedPreferences("DAY_STEPS", 0);
        ArrayList<BarEntry> dataList = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            long DAY_IN_MS = 1000 * 60 * 60 * 24;
            Date date = new Date(System.currentTimeMillis() - (i * DAY_IN_MS));
            String barTime = String.valueOf(date);
            barTime = barTime.replaceAll("\\s+", "");
            barTime = barTime.substring(0, 8);
            dataList.add(new BarEntry(day-i,sharedpreferences.getInt(barTime, 0)));


        }


        BarDataSet barDataSet1 = new BarDataSet(dataList, "Daily Steps");
        barDataSet1.setColor(getResources().getColor(R.color.colorRoundCounter));
        barDataSet1.setDrawValues(false);
        BarData barData = new BarData();
        barData.addDataSet(barDataSet1);

        barChart.setVisibleXRangeMaximum(7);//no more than 5 values on the x-axis can be viewed at once without scrolling
        barChart.setVisibleXRangeMinimum(7);//it is not possible to zoom in further than 5 values
        barChart.setData(barData);
        barChart.invalidate();

    }


    private String[] calculateWeek() {
        String[] daysOfWeekArray;
        LocalDate localDate = LocalDate.now();

        // Find the day from the local date
        DayOfWeek dayOfWeek = DayOfWeek.from(localDate);

        int val = dayOfWeek.getValue();

//        Log.d(TAG, "Int Value of " + dayOfWeek.name() + " - " + val);


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
}
