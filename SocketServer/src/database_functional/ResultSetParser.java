package database_functional;

import com.example.main_app_2.integratedClasses.DayOfWeek;
import com.example.main_app_2.integratedClasses.Lesson;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResultSetParser {
    public static HashMap<DayOfWeek, List<Lesson>> parseAsLessons(ResultSet resultSet) throws Exception {
        HashMap<DayOfWeek, List<Lesson>> allLessons = new HashMap<>();
        for (var day : DayOfWeek.values()) {
            allLessons.put(day, new ArrayList<>());
        }
        while (resultSet.next()) {
            String disciplineName = resultSet.getString("discipline_name");
            String lecturer_name = resultSet.getString("lecturer_name");
            String type = resultSet.getString("type");
            int startHour = resultSet.getInt("start_hour");
            int startMinute = resultSet.getInt("start_minute");
            LocalTime startTime = LocalTime.of(startHour, startMinute);
            int lengthInMinutes = resultSet.getInt("length_in_minutes");
            int day = resultSet.getInt("day");
            DayOfWeek dayOfWeek = DayOfWeek.values()[day];
            String room = resultSet.getString("room");
            Lesson lesson = new Lesson(type, disciplineName, startTime, lengthInMinutes, lecturer_name, room);
            allLessons.get(dayOfWeek).add(lesson);
        }
        return allLessons;
    }

    public static boolean isEmpty(ResultSet resultSet){
        try{
            return resultSet.next();
        } catch (SQLException throwables) {
            return false;
        }
    }
}
