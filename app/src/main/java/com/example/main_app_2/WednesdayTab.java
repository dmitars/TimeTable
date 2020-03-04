package com.example.main_app_2;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.main_app_2.integratedClasses.DayOfWeek;
import com.example.main_app_2.integratedClasses.Lesson;


import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;

@TargetApi(26)
public class WednesdayTab extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    int size;
    int dayId;
    LayoutInflater ltInflater;
    LinearLayout rightLayout;
    LinearLayout leftLayout;
    LocalTime localTime;
    LocalTime maxWindow = LocalTime.of(0,35);

    SwipeRefreshLayout swipeRefreshLayout;

    Context mainContext;
    public WednesdayTab(Context context, int position)
    {
        dayId = position;
        mainContext = context;
    }

    @TargetApi(26)
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.wednesday, container, false);
        TextView textView = root.findViewById(R.id.Title);
        textView.setText("Среда");
        rightLayout = root.findViewById(R.id.rightLayout);
        leftLayout = root.findViewById(R.id.leftLayout);

        swipeRefreshLayout=root.findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        ltInflater = getLayoutInflater();

        if(DataBase.data!=null)
            generateData();
        Fragments.fragments.add(this);
        return root;
    }


    @TargetApi(26)
    View getLeftItem(boolean isWindow, Lesson lesson){
        View item = ltInflater.inflate(R.layout.left_item, leftLayout, false);
        TextView textFirst =  item.findViewById(R.id.timeBegin);
        TextView textSecond = item.findViewById(R.id.timeEnd);

        if(!isWindow)
        {
            textFirst.setText(lesson.getStart().toString());
            textSecond.setText(lesson.getStart()
                    .plusMinutes(lesson.getLengthInMinutes()).toString());
        }
        else
        {
            textFirst.setText(localTime.toString());
            textSecond.setText(lesson.getStart().toString());
        }
        item.setBackgroundResource(R.drawable.back_left);
        return item;
    }

    @TargetApi(26)
    View getRightItem(Optional<Lesson> lesson){
        View item = ltInflater.inflate(R.layout.item, rightLayout, false);
        TextView textView1 =  item.findViewById(R.id.tvName);
        TextView tvPosition =  item.findViewById(R.id.tvPosition);
        TextView tvSalary =  item.findViewById(R.id.tvSalary);
        if(lesson.isPresent())
        {
            textView1.setText(lesson.get().getName());
            tvPosition.setText("К. "+lesson.get().getRoom());
            tvSalary.setText(lesson.get().getTeacher());
            item.setBackgroundResource(R.drawable.back);
        }
        else
        {
            textView1.setText("Перерыв");
            tvPosition.setText("");
            tvSalary.setText("");
            item.setBackgroundColor(Color.parseColor("#808080"));
        }
        return item;
    }

    private void generateData(){
        List<Lesson>lessons = DataBase.data.get(DayOfWeek.values()[dayId]);
        size = lessons.size();
        localTime = lessons.get(0).getStart();
        for(int i = 0;i<size;i++)
        {
            if(lessons.get(i).getStart().toSecondOfDay() - localTime.toSecondOfDay()
                    > maxWindow.toSecondOfDay())
            {
                View item = getRightItem(Optional.<Lesson>empty());
                rightLayout.addView(item);

                item = getLeftItem(true,lessons.get(i));
                leftLayout.addView(item);
            }
            {
                View item = getRightItem(Optional.of(lessons.get(i)));
                rightLayout.addView(item);
            }
            {
                View item = getLeftItem(false,lessons.get(i));
                leftLayout.addView(item);
            }
            localTime = lessons.get(i).getStart().plusMinutes(lessons.get(i).getLengthInMinutes());
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Map<DayOfWeek, List<Lesson>> data = Requester.makeRequest(DataBase.group,DataBase.course);
                if(data == null)
                    Toast.makeText(getContext(), "Ошибка соединения; попробуйте позже", Toast.LENGTH_LONG).show();
                else {
                    leftLayout.removeAllViews();
                    rightLayout.removeAllViews();
                    DataBase.data = data;
                    generateData();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        },400);
    }
}
