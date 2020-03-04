package com.example.main_app_2;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.example.main_app_2.integratedClasses.DayOfWeek;
import com.example.main_app_2.integratedClasses.Lesson;

import java.util.List;
import java.util.Map;

@TargetApi(26)
public class SettingsActivity extends AppCompatActivity {

    public static int tempCourse;
    public static int tempGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Настройки");
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }



    @TargetApi(26)
    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            try {
                ListPreference listPreference = findPreference("prefCourse");
                listPreference.setValue(String.valueOf(DataBase.getCourse()));
                listPreference.setOnPreferenceChangeListener(listener);
                listPreference = findPreference("prefGroup");
                listPreference.setValue(String.valueOf(DataBase.getGroup()));
                listPreference.setOnPreferenceChangeListener(listener);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.menu_save_button:
                if(tempCourse!=DataBase.getCourse() || tempGroup!=DataBase.getGroup())
                {
                    if(tempGroup == 0)
                        tempGroup = DataBase.getGroup();
                    if(tempCourse == 0)
                        tempCourse = DataBase.getCourse();
                    Map<DayOfWeek, List<Lesson>>data = Requester.makeRequest(tempCourse,tempGroup);
                    if(data!=null)
                    {
                        DataBase.data = data;
                        DataBase.setCourseAndGroupInfo(tempCourse,tempGroup);
                        Fragments.fragmentsUpdate();
                        Toast.makeText(getApplicationContext(),"Данные изменены",
                                Toast.LENGTH_SHORT).show();
                    }
                }


                /*Toast.makeText(getApplicationContext(),"menu save button was pressed",Toast.LENGTH_LONG).show();*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private static Preference.OnPreferenceChangeListener listener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {

            String value = newValue.toString();
            if(preference instanceof ListPreference)
            {
                ListPreference listPreference = (ListPreference)preference;

                if(listPreference.getKey().equals("prefCourse")){
                    tempCourse = Integer.parseInt(value);
                }
                if(listPreference.getKey().equals("prefGroup")){
                    tempGroup = Integer.parseInt(value);
                }
            }
            if(preference instanceof SwitchPreferenceCompat)
            {

            }
            return true;
        }
    };
}