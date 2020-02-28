package com.example.main_app_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class TuesdayTab extends Fragment{
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        inflater.getContext().setTheme(R.style.Theme_Design_Light);
        //inflater.context.setTheme(R.style.yourCustomTheme)
        View root = inflater.inflate(R.layout.tuesday, container, false);
        TextView textView = root.findViewById(R.id.Title);
        textView.setText("Вторник");
        return root;
    }
}
