package com.example.main_app_2.integratedClasses;

import java.time.LocalTime;
import java.util.HashSet;

public class ScheduleInfo
{
	private static ScheduleInfo instance = null;
	
	private HashSet<Lesson> allLessons;
	private int[] groupsInCourse =
		new int[] {14, 13, 13, 13, 5, 5};

	private void initializeAllLessons()
	{
		allLessons = new HashSet<>();
		allLessons.add(new Lesson(2, 13, "Лекция", "АиСД",
								  LocalTime.of(8, 15), 80,
								  "Соболевская Е.П.", "522", DayOfWeek.WEDNESDAY));
		allLessons.add(new Lesson(2, 13, "Практика", "АиСД",
								  LocalTime.of(9, 45), 80,
								  "Соболевская Е.П.", "508", DayOfWeek.WEDNESDAY));
		allLessons.add(new Lesson(2, 13, "Лекция", "ОС",
								  LocalTime.of(11, 15), 80,
								  "Побегайло А.П.", "522", DayOfWeek.WEDNESDAY));
		allLessons.add(new Lesson(2, 13, "Практика", "ОС",
								  LocalTime.of(13, 00), 80,
								  "Тылецкий П.С.", "505", DayOfWeek.WEDNESDAY));
		allLessons.add(new Lesson(1, 14, "Лекция", "АиТЧ",
								  LocalTime.of(8, 15), 80,
								  "Самый понятный лектор", "522", DayOfWeek.FRIDAY));
		allLessons.add(new Lesson(1, 14, "Практика", "Физическая культура",
								  LocalTime.of(9, 45), 80,
								  "Веремейчик В.М.", "Спортзал", DayOfWeek.FRIDAY));
		allLessons.add(new Lesson(1, 14, "Практика", "АиТЧ",
								  LocalTime.of(11, 15), 80,
								  "Комраков Б.Б.", "340", DayOfWeek.FRIDAY));
	}

	private ScheduleInfo()
	{
		initializeAllLessons();
	}

	public static ScheduleInfo getInstance()
	{
		if (instance == null)
			instance = new ScheduleInfo();
		return instance;
	}

	public HashSet<Lesson> getAllLessons()
	{
		return allLessons;
	}

	public int[] getGroupsInCourse()
	{
		return groupsInCourse;
	}
}
