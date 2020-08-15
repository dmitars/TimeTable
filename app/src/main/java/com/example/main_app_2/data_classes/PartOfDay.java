package com.example.main_app_2.data_classes;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalTime;

public abstract class PartOfDay implements DataForShowing {
    final String name;
    final LocalTime startTime;
    final int lengthInMinutes;
    int backgroundResource;

    public String getName()
    {
        return name;
    }

    public LocalTime getStartTime()
    {
        return startTime;
    }

    public int getLengthInMinutes()
    {
        return lengthInMinutes;
    }

    public PartOfDay(String name,LocalTime startTime, int lengthInMinutes){
        this.name = name;
        this.startTime = startTime;
        this.lengthInMinutes = lengthInMinutes;
        setBackgroundResource();
    }

    abstract void setBackgroundResource();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalTime getEndOfPart(){
        return startTime.plusMinutes(lengthInMinutes);
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String getStart() {
        return startTime.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public String getEnd() {
        return getEndOfPart().toString();
    }
}
