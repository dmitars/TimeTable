package com.example.main_app_2.DayTabs;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.main_app_2.R;
import com.example.main_app_2.SystemClasses.DataBase;
import com.example.main_app_2.SystemClasses.Fragments;
import com.example.main_app_2.consts.Const;
import com.example.main_app_2.data_classes.Lesson;
import com.example.main_app_2.data_classes.PartOfDay;
import com.example.main_app_2.data_classes.RestTime;
import com.example.main_app_2.integratedClasses.DayOfWeek;
import com.example.main_app_2.integratedClasses.PartOfDayDisplay;

import java.time.LocalTime;
import java.util.List;

@TargetApi(26)
public class WednesdayTab extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    int dayId;
    LayoutInflater ltInflater;
    LinearLayout rightLayout;
    LinearLayout leftLayout;
    PartOfDayDisplay partOfDayDisplay;

    SwipeRefreshLayout swipeRefreshLayout;
    Context mainContext;

    public WednesdayTab(Context context, int position){
        dayId = position;
        mainContext = context;
    }

    @TargetApi(26)
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.wednesday, container, false);
        TextView textView = root.findViewById(R.id.titleView);
        textView.setText(DayOfWeek.values()[dayId].name());
        rightLayout = root.findViewById(R.id.rightLayout);
        leftLayout = root.findViewById(R.id.leftLayout);

        swipeRefreshLayout = root.findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        ltInflater = getLayoutInflater();
        partOfDayDisplay
                = new PartOfDayDisplay(ltInflater,leftLayout,rightLayout);

        if(!DataBase.isNotLoaded())
            generateData();
        Fragments.fragments.add(this);
        return root;
    }

    private void generateData(){
        List<Lesson>lessons = DataBase.getLessonsOfDay(dayId);
        if(lessons.size() == 0)
            return;
        LocalTime endTimeOfPrevious = lessons.get(0).getStartTime();
        for(Lesson lesson:lessons)
        {
            int interval = lesson.getStartTime().toSecondOfDay() - endTimeOfPrevious.toSecondOfDay();
            if(interval > Const.MAX_WINDOW.toSecondOfDay())
                insertRest(endTimeOfPrevious,interval);
            partOfDayDisplay.showPartOfDay(lesson);
            endTimeOfPrevious = lesson.getEndOfPart();
        }
    }

    private void insertRest(LocalTime startTime, int intervalInSeconds){
        int intervalInMinutes = intervalInSeconds % 60;
        PartOfDay rest = new RestTime(getString(R.string.rest_time_title),
                startTime,intervalInMinutes);
        partOfDayDisplay.showPartOfDay(rest);
    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try{
                    DataBase.refresh(getContext());
                    leftLayout.removeAllViews();
                    rightLayout.removeAllViews();
                    generateData();
                    swipeRefreshLayout.setRefreshing(false);
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getLocalizedMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        },400);
    }
}
