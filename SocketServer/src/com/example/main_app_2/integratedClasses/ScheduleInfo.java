package com.example.main_app_2.integratedClasses;

import database_functional.Requester;
import database_functional.ResultSetParser;

import java.util.HashMap;
import java.util.List;

public class ScheduleInfo {
    private static ScheduleInfo instance = null;

    private HashMap<DayOfWeek, List<Lesson>> lessons;
    private Requester requester;

    private void initializeLessons(int course, int group) throws Exception {
        var resultSet = requester.findLessonsOfGroup(course, group);
        lessons = ResultSetParser.parseAsLessons(resultSet);
        requester.closeConnection();
    }

    public boolean isGroupPresent(int course, int group) throws Exception {
        boolean result = requester.isGroupPresent(course, group);
        requester.closeConnection();
        return result;
    }

    private ScheduleInfo() {
        requester = new Requester();
    }

    public static ScheduleInfo getInstance() {
        if (instance == null)
            instance = new ScheduleInfo();
        return instance;
    }

    public HashMap<DayOfWeek, List<Lesson>> getLessonsOfGroup(int course, int group) throws Exception {
        initializeLessons(course, group);
        return lessons;
    }
}
