package com.example.main_app_2;

import com.example.main_app_2.integratedClasses.DayOfWeek;
import com.example.main_app_2.integratedClasses.Lesson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBase {
    private static int course;
    private static int group;
    public static Map<DayOfWeek, List<Lesson>>data;

    public static void setCourseAndGroupInfo(int aCourse, int aGroup){
        course = aCourse;
        group = aGroup;
    }

    public static int getCourse() {
        return course;
    }

    public static int getGroup() {
        return group;
    }
}
