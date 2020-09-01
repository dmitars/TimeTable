package com.example.main_app_2;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.widget.Toast;

import com.example.main_app_2.Network.Requester;
import com.example.main_app_2.SystemClasses.DataBase;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import com.example.main_app_2.ui.main.SectionsPagerAdapter;

import java.time.DayOfWeek;
import java.time.Instant;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity{
    @Override
    @TargetApi(26)
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        Instant instant = Instant.now();

        if(DataBase.isNotLoaded())
            startCustomDialog();

        Requester.updateTimeTextView = findViewById(R.id.updateTextView);
        DayOfWeek dow = Time.getTodayDayOfWeek(instant);
        setTabOfDay(tabs,dow);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setTabOfDay(TabLayout tabs, DayOfWeek dow){
        int dayOfWeekIndex =  dow.getValue();
        if(dayOfWeekIndex == 7)
            tabs.selectTab(tabs.getTabAt(0));
        else
            tabs.selectTab(tabs.getTabAt(dayOfWeekIndex-1));
    }

    @TargetApi(23)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.getItem(0).setIcon(R.drawable.menu_item);
        menu.getItem(1).setIcon(R.drawable.menu_item);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       // Toast.makeText(getApplicationContext(), item.getTitle() + "has been pushed", Toast.LENGTH_LONG).show();
        switch(item.getOrder())
        {
            case 1:
            {
                try {
                    DataBase.refresh(getBaseContext());
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),
                            Toast.LENGTH_LONG).show();
                }
                break;
            }
            case 2:
            {
                startSettings();
                break;
            }
            default:
                Toast.makeText(getBaseContext(),String.valueOf(item.getItemId()),Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void startSettings(){
        Intent intent = new Intent(this,SettingsActivity.class);
        startActivity(intent);
    }


    void startCustomDialog() {

        CustomDialogFragment newFragment = new CustomDialogFragment();

        Bundle bundle = new Bundle();
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(0, 10);
        arrayList.add(1, 13);
        arrayList.add(2, 11);
        arrayList.add(3, 13);


        bundle.putIntegerArrayList("list", arrayList);
        newFragment.setArguments(bundle);

        newFragment.show(getSupportFragmentManager(), "tag");

    }

}
