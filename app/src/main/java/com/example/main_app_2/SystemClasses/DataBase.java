package com.example.main_app_2.SystemClasses;

import android.content.Context;

import com.example.main_app_2.Network.Requester;
import com.example.main_app_2.R;
import com.example.main_app_2.integratedClasses.DayOfWeek;
import com.example.main_app_2.data_classes.Lesson;

import java.util.List;
import java.util.Map;

public class DataBase {
    private static int course;
    private static int group;
    private static Map<DayOfWeek, List<Lesson>>data;

    public static void setCourseAndGroupInfo(int aCourse, int aGroup){
        course = aCourse;
        group = aGroup;
    }

    public static void loadCurrentData(Context context, int course, int group)throws Exception{
        Map<DayOfWeek, List<Lesson>> tempData  = Requester.makeRequest(course,group);
        if(tempData == null)
            throw new Exception(context.getString(R.string.refresh_error_message));
        data = tempData;
        setCourseAndGroupInfo(course,group);
    }

    public static void refresh(Context context) throws Exception{
        Map<DayOfWeek, List<Lesson>> tempData  =
                Requester.makeRequest(DataBase.getCourse(),DataBase.getGroup());
        if(tempData == null)
            throw new Exception(context.getString(R.string.refresh_error_message));
        else
            data = tempData;
    }

    public static List<Lesson>getLessonsOfDay(int dayIndex){
        return data.get(DayOfWeek.values()[dayIndex]);
    }

    public static boolean isNotLoaded(){
        return data == null;
    }

    public static int getCourse() {
        return course;
    }

    public static int getGroup() {
        return group;
    }
}
