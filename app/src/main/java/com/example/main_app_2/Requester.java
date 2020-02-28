package com.example.main_app_2;
import com.example.main_app_2.integratedClasses.*;

import android.annotation.TargetApi;
import android.widget.TextView;


import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@TargetApi(26)
public class Requester {
    public static TextView textView;
    private static List<String>teachers = Arrays.asList("Кашкевич С.И.",
            "Соболевская Е.П.",
            "Давидовская М.И.",
            "Орешко И.Г.");
    private static List<String>pairs = Arrays.asList("Программирование",
            "АиСД",
            "Технологии программирования",
            "Учебная практика");
    private static List<String>types = Arrays.asList("Лекция",
            "Практика",
            "Практика",
            "Лаб. Практика");
    private static List<String>rooms = Arrays.asList("522",
            "508",
            "505",
            "314");
    private static int[] hours = {8,9,11,14};
    private static int[] minutes = {15,45,15,30};


    public static String lastDateViewText = "";
    static ZoneId zone;
    public static Map<DayOfWeek, List<Lesson>> makeRequest(){

        Map<DayOfWeek, List<Lesson>> answ = new HashMap<DayOfWeek, List<Lesson>>(); //= null;
        /*try {
            answ = new SocketRequestTask().execute(DataBase.course, DataBase.group).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        List<Lesson>lessons = new ArrayList<>();
        for(int i = 0;i<4;i++)
            lessons.add(new Lesson(0,0,types.get(i),pairs.get(i),
                    LocalTime.of(hours[i],minutes[i]),80,
                    teachers.get(i),rooms.get(i),null));


        for(int i = 0;i<7;i++)
            answ.put(DayOfWeek.values()[i],lessons);
        lastDateViewText = ("Обновлено: "+ LocalTime.now(zone).withNano(0).withSecond(0).toString());
        textView.setText(lastDateViewText);
        return answ;
    }
}
