package com.example.main_app_2.DayTabs;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.main_app_2.R;
import com.example.main_app_2.SystemClasses.DataBase;
import com.example.main_app_2.SystemClasses.Fragments;
import com.example.main_app_2.integratedClasses.PartOfDayDisplay;

@TargetApi(26)
public class WednesdayTab extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
                                                                                    TabConfigure{
    int dayId;
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
        setTitle(root,dayId);
        LayoutInflater ltInflater = getLayoutInflater();
        partOfDayDisplay = getDisplay(ltInflater,root);

        swipeRefreshLayout = root.findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        if(!DataBase.isNotLoaded())
            generateData(partOfDayDisplay,getContext(),dayId);
        Fragments.fragments.add(this);
        return root;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(() -> {
            try{
                refresh(partOfDayDisplay,getContext(),dayId);
                swipeRefreshLayout.setRefreshing(false);
            } catch (Exception e) {
                Toast.makeText(getContext(), e.getLocalizedMessage(),
                        Toast.LENGTH_LONG).show();
            }
        },400);
    }
}
