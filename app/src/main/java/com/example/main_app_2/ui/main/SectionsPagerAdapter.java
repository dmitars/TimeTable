package com.example.main_app_2.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.main_app_2.DayTabs.FridayTab;
import com.example.main_app_2.DayTabs.MondayTab;
import com.example.main_app_2.DayTabs.SaturdayTab;
import com.example.main_app_2.DayTabs.ThursdayTab;
import com.example.main_app_2.DayTabs.TuesdayTab;
import com.example.main_app_2.DayTabs.WednesdayTab;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
            {
                return new MondayTab();
            }
            case 1:
            {

                return new TuesdayTab();
            }
            case 2:
            {
                return new WednesdayTab(mContext,position);
            }
            case 3:
                return new ThursdayTab();
            case 4:
                return new FridayTab();
            case 5:
                return new SaturdayTab();
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position)
        {
            case 0:
                return "Пн";
            case 1:
                return "Вт";
            case 2:
                return "Ср";
            case 3:
                return "Чт";
            case 4:
                return "Пт";
            case 5:
                return "Сб";
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 6;
    }
}