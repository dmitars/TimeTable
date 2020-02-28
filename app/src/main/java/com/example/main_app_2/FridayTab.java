package com.example.main_app_2;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

public class FridayTab extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    TextView textView;
    SwipeRefreshLayout swipeRefreshLayout;
    ImageView imageView;
    private static final int REQUEST_WEIGHT = 1;
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.friday, container, false);
        textView = root.findViewById(R.id.Title);
        textView.setText("Пятница");
        swipeRefreshLayout=root.findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
       // imageView.setEnabled(false);
        return root;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                textView.setText("хуй вам, а не пятница\n завтра на тп");
                //imageView.setEnabled(true);
            }
        },400);

    }

}
