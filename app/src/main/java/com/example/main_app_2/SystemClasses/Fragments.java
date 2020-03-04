package com.example.main_app_2.SystemClasses;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

public class Fragments {
    public static ArrayList<SwipeRefreshLayout.OnRefreshListener>fragments = new ArrayList<>();

    public static void fragmentsUpdate(){
        for(SwipeRefreshLayout.OnRefreshListener fragment:Fragments.fragments)
        {
            if(fragment!=null)
                fragment.onRefresh();
        }
    }
}
