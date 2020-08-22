package edu.neu.madcourse.groupproject;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;

public class MyCustomFormatter implements IAxisValueFormatter {
    private String[] mValues;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.hh");

    public MyCustomFormatter(String[] values) {
        this.mValues = values;
    }


    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
        return mValues[(int) value];
    }
}