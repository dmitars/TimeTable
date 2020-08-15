package com.example.main_app_2.DayTabs;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.main_app_2.R;
import com.example.main_app_2.SystemClasses.DataBase;
import com.example.main_app_2.consts.Const;
import com.example.main_app_2.data_classes.Lesson;
import com.example.main_app_2.data_classes.RestTime;
import com.example.main_app_2.integratedClasses.DayOfWeek;
import com.example.main_app_2.integratedClasses.PartOfDayDisplay;

import java.time.LocalTime;
import java.util.List;

public interface TabConfigure {

    @RequiresApi(api = Build.VERSION_CODES.N)
    default PartOfDayDisplay getDisplay(LayoutInflater layoutInflater, View root){
        LinearLayout rightLayout = root.findViewById(R.id.rightLayout);
        LinearLayout leftLayout = root.findViewById(R.id.leftLayout);
        return new PartOfDayDisplay(layoutInflater,leftLayout,rightLayout);
    }

    default void setTitle(View root, int dayId) {
        TextView textView = root.findViewById(R.id.titleView);
        textView.setText(DayOfWeek.values()[dayId].name());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    default void generateData(PartOfDayDisplay partOfDayDisplay, Context context, int dayId){
        List<Lesson>lessons = DataBase.getLessonsOfDay(dayId);
        if(lessons.size() == 0)
            return;
        LocalTime endTimeOfPrevious = lessons.get(0).getStartTime();
        for(Lesson lesson:lessons)
        {
            int interval = lesson.getStartTime().toSecondOfDay() - endTimeOfPrevious.toSecondOfDay();
            if(interval > Const.MAX_WINDOW.toSecondOfDay()) {
                RestTime restTime = getRestWithParameters(endTimeOfPrevious, interval,context);
                partOfDayDisplay.showPartOfDay(restTime);
            }
            partOfDayDisplay.showPartOfDay(lesson);
            endTimeOfPrevious = lesson.getEndOfPart();
        }
    }

    default RestTime getRestWithParameters(LocalTime startTime, int intervalInSeconds, Context context){
        int intervalInMinutes = intervalInSeconds % 60;
        return new RestTime(context.getString(R.string.rest_time_title),
                startTime,intervalInMinutes);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    default void refresh(PartOfDayDisplay partOfDayDisplay, Context context, int dayId) throws Exception {
        DataBase.refresh(context);
        partOfDayDisplay.clear();
        generateData(partOfDayDisplay, context, dayId);
    }
}
