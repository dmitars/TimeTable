package com.example.main_app_2.data_classes;

import android.graphics.Color;

import java.time.LocalTime;

public class RestTime extends PartOfDay {
    public RestTime(String name, LocalTime startTime, int lengthInMinutes) {
        super(name, startTime, lengthInMinutes);
    }

    @Override
    void setBackgroundResource() {
        backgroundResource = Color.parseColor("#808080");
    }

    @Override
    public String getFirstDetailedData() {
        return "";
    }

    @Override
    public String getSecondDetailedData() {
        return "";
    }
}
