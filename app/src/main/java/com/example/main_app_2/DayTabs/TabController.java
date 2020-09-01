package com.example.main_app_2.DayTabs;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.main_app_2.R;
import com.example.main_app_2.SystemClasses.DataBase;
import com.example.main_app_2.consts.Const;
import com.example.main_app_2.data_classes.Lesson;
import com.example.main_app_2.data_classes.RestTime;
import com.example.main_app_2.integratedClasses.DayOfWeek;
import com.example.main_app_2.integratedClasses.PartOfDayDisplay;

import java.time.LocalTime;
import java.util.List;

public class TabController {
    Fragment tab;
    View root;

    private PartOfDayDisplay partOfDayDisplay;
    private SwipeRefreshLayout swipeRefreshLayout;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public TabController(Fragment fragment, View root){
        this.tab = fragment;
        this.root = root;
        partOfDayDisplay = getDisplay();
    }

    public void setTitle(int dayId){
        TextView textView = root.findViewById(R.id.titleView);
        textView.setText(DayOfWeek.values()[dayId].name());
    }

    public void configureSwipe(SwipeRefreshLayout.OnRefreshListener listener){
        swipeRefreshLayout = root.findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(listener);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private PartOfDayDisplay getDisplay(){
        LinearLayout rightLayout = root.findViewById(R.id.rightLayout);
        LinearLayout leftLayout = root.findViewById(R.id.leftLayout);
        LayoutInflater layoutInflater = tab.getLayoutInflater();
        return new PartOfDayDisplay(layoutInflater,leftLayout,rightLayout);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void generateData(int dayId){
        List<Lesson> lessons = DataBase.getLessonsOfDay(dayId);
        if(lessons.size() == 0)
            return;
        LocalTime endTimeOfPrevious = lessons.get(0).getStartTime();
        for(Lesson lesson:lessons)
        {
            int interval = lesson.getStartTime().toSecondOfDay() - endTimeOfPrevious.toSecondOfDay();
            if(interval > Const.MAX_WINDOW.toSecondOfDay()) {
                insertRestTime(endTimeOfPrevious, interval);
            }
            partOfDayDisplay.showPartOfDay(lesson);
            endTimeOfPrevious = lesson.getEndOfPart();
        }
    }

    private void insertRestTime(LocalTime startTime, int intervalInSeconds){
        int intervalInMinutes = intervalInSeconds % 60;
        RestTime restTime = getRestWithParameters(startTime,intervalInMinutes);
        partOfDayDisplay.showPartOfDay(restTime);
    }

    private RestTime getRestWithParameters(LocalTime startTime, int intervalInMinutes){
        Context context = tab.getContext();
        return new RestTime(context.getString(R.string.rest_time_title),
                startTime,intervalInMinutes);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void refresh() throws Exception {
        Context context = tab.getContext();
        DataBase.refresh(context);
        partOfDayDisplay.clear();
        swipeRefreshLayout.setRefreshing(false);
    }
}
