package com.example.main_app_2;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import com.example.main_app_2.ui.main.SectionsPagerAdapter;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements Datable{
    @Override
    @TargetApi(26)
    protected void onCreate(Bundle savedInstanceState) {
        ZoneId zoneId = ZoneId.of( "Europe/Moscow" );

        Requester.zone = zoneId;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        Instant instant = Instant.now();


        if(DataBase.data == null)
            startCustomDialog();
        Requester.textView = findViewById(R.id.updateTextView);
       // TextView lastUpdate = findViewById(R.id.updateTextView);
       // lastUpdate.setText(Requester.lastDateViewText);

        ZonedDateTime zdt = ZonedDateTime.ofInstant( instant , zoneId );
        DayOfWeek dow = DayOfWeek.from( zdt );


        int res =  dow.getValue();
        if(res == 7)
            tabs.selectTab(tabs.getTabAt(0));
        else
            tabs.selectTab(tabs.getTabAt(res-1));

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
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
                DataBase.data = Requester.makeRequest(DataBase.course,DataBase.group);
                TextView lastUpdate = findViewById(R.id.updateTextView);
                lastUpdate.setText(Requester.lastDateViewText);
                break;
            }
            case 2:
            {
                Intent intent = new Intent(this,SettingsActivity.class);
                startActivity(intent);
                break;
            }
            default:
                Toast.makeText(getBaseContext(),String.valueOf(item.getItemId()),Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
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

    @Override
    public void setGroupAndCourseInfo(int group, int course) {
       DataBase.group = group;
       DataBase.course = course;
    }
}
