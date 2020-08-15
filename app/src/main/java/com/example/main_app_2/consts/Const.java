package com.example.main_app_2.consts;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalTime;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Const {
    public static final LocalTime MAX_WINDOW = LocalTime.of(0,35);
}
