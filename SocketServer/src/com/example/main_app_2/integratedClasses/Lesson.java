package com.example.main_app_2.integratedClasses;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

public class Lesson
	implements Serializable
{
	private final int course;
	private final int group;
	private final String name;
	private final LocalTime start;
	private final int lengthInMinutes;
	private final String teacher;
	private final String room;
	private final String type;
	private final DayOfWeek dayOfWeek;

	public Lesson(int course, int group, String type, String name,
				  LocalTime start, int lengthInMinutes, String teacher,
				  String room, DayOfWeek dayOfWeek)
	{
		this.course = course;
		this.group = group;
		this.name = name;
		this.start = start;
		this.lengthInMinutes = lengthInMinutes;
		this.teacher = teacher;
		this.room = room;
		this.type = type;
		this.dayOfWeek = dayOfWeek;
	}

	public int getCourse()
	{
		return course;
	}

	public int getGroup()
	{
		return group;
	}

	public String getName()
	{
		return name;
	}

	public LocalTime getStart()
	{
		return start;
	}

	public int getLengthInMinutes()
	{
		return lengthInMinutes;
	}

	public String getTeacher()
	{
		return teacher;
	}

	public String getRoom()
	{
		return room;
	}

	public DayOfWeek getDayOfWeek()
	{
		return dayOfWeek;
	}

	public String getType()
	{
		return type;
	}

	@Override
	public String toString()
	{
		return String.format("Курс: %d\nГруппа: %d\n" +
							 "Занятие: %s\nТип занятия: %s\n" +
							 "День недели: %s\nНачало занятий: %s\n" +
							 "Конец занятий: %s\nПреподаватель: %s\n" +
							 "Аудитория: %s",
							 course, group, name, type,
							 dayOfWeek.toString(),
							 start.toString(),
							 start.plusMinutes(lengthInMinutes).toString(),
							 teacher, room);
	}

	public static Lesson parseLesson(String str)
	{
		String[] tokens = str.split(",\\s*");
		LocalTime startTime = LocalTime.parse(tokens[4]);
		return new Lesson
			(
				Integer.parseInt(tokens[0]),
				Integer.parseInt(tokens[1]),
				tokens[2],
				tokens[3],
				startTime,
				Integer.parseInt(tokens[5]),
				tokens[6],
				tokens[7],
				DayOfWeek.parse(tokens[8])
			);
	}

	@Override
	public boolean equals(Object obj)
	{
		return Objects.deepEquals(this, obj);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(course, group, name,
							start, lengthInMinutes,
							teacher, room, type,
							dayOfWeek);
	}
}
