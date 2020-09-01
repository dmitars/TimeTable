package com.example.main_app_2;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Time {
    private static final ZoneId zoneId = ZoneId.of("Europe/Moscow");

    public static String getLocalTimeAsString(){
        LocalTime localTime = getLocalTime();
        return localTime.toString();
    }

    private static LocalTime getLocalTime(){
        return LocalTime.now(zoneId).withNano(0).withSecond(0);
    }

    public static DayOfWeek getTodayDayOfWeek(Instant instant){
        ZonedDateTime zdt = ZonedDateTime.ofInstant( instant , zoneId );
        return DayOfWeek.from( zdt );
    }

}
