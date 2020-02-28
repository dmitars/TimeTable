package com.example.main_app_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class ThursdayTab extends Fragment{

    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.thursday, container, false);
        TextView textView = root.findViewById(R.id.Title);
        textView.setText("Четверг");
        return root;
    }
}
