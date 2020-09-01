package com.example.main_app_2.integratedClasses;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleInfoTest {

    @org.junit.jupiter.api.Test
    void isGroupPresent() throws Exception {
        assertTrue(ScheduleInfo.getInstance().isGroupPresent(1,1));
        assertTrue(ScheduleInfo.getInstance().isGroupPresent(2,1));
        assertTrue(ScheduleInfo.getInstance().isGroupPresent(2,5));
        assertTrue(ScheduleInfo.getInstance().isGroupPresent(1,10));
        assertFalse(ScheduleInfo.getInstance().isGroupPresent(3,10));
        assertFalse(ScheduleInfo.getInstance().isGroupPresent(4,1));
        assertFalse(ScheduleInfo.getInstance().isGroupPresent(1,15));
    }

    @org.junit.jupiter.api.Test
    void getLessonsOfGroup() throws Exception {
        HashMap<DayOfWeek, List<Lesson>>lessons = new HashMap<>();
        for(var day:DayOfWeek.values())
            lessons.put(day,new ArrayList<>());
        List<Lesson> lessonsList= new ArrayList<Lesson>();
        Lesson lesson = new Lesson("Лекция","Программирование",
                LocalTime.of(8,15),120,"Кашкевич С.И.","522");
        lessonsList.add(lesson);
        lesson = new Lesson("Лекция","Математический анализ",
                LocalTime.of(9,45),120,"Новичкова Д.А.","517");
        lessonsList.add(lesson);
        lesson = new Lesson("Практика","Математический анализ",
                LocalTime.of(11,15),120,"Новичкова Д.А.","517");
        lessonsList.add(lesson);
        lesson = new Lesson("Практика","Программирование",
                LocalTime.of(13,0),120,"Кашкевич С.И.","513");
        lessonsList.add(lesson);
        lessons.put(DayOfWeek.WEDNESDAY,lessonsList);
        assertEquals(lessons,ScheduleInfo.getInstance().getLessonsOfGroup(1,1));
    }
}