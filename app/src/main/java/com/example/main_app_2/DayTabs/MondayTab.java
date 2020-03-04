package com.example.main_app_2.DayTabs;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.main_app_2.R;


public class MondayTab extends Fragment {
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.monday, container, false);
        TextView textView = root.findViewById(R.id.Title);
        textView.setText("Понедельник");

        return root;
    }
}
