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

@TargetApi(26)
public class WednesdayTab extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    int dayId;
    Context mainContext;

    private TabController tabController;

    public WednesdayTab(Context context, int position){
        dayId = position;
        mainContext = context;
    }

    @TargetApi(26)
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.wednesday, container, false);
        tabController = new TabController(this,root);
        tabController.setTitle(dayId);
        tabController.configureSwipe(this);

        if(!DataBase.isNotLoaded())
            tabController.generateData(dayId);
        Fragments.fragments.add(this);
        return root;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(() -> {
            try{
                tabController.refresh();
                tabController.generateData(dayId);
            } catch (Exception e) {
                Toast.makeText(getContext(), e.getLocalizedMessage(),
                        Toast.LENGTH_LONG).show();
            }
        },400);
    }
}
