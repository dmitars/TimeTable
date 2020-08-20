package com.example.main_app_2.integratedClasses;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

public class Lesson
	implements Serializable
{
	private final String name;
	private final LocalTime start;
	private final int lengthInMinutes;
	private final String teacher;
	private final String room;
	private final String type;

	public Lesson(String type, String name,
				  LocalTime start, int lengthInMinutes, String teacher,
				  String room)
	{
		this.name = name;
		this.start = start;
		this.lengthInMinutes = lengthInMinutes;
		this.teacher = teacher;
		this.room = room;
		this.type = type;
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

	public String getType()
	{
		return type;
	}

	@Override
	public String toString()
	{
		return String.format("Занятие: %s\nТип занятия: %s\n" +
							 "Начало занятий: %s\n" +
							 "Конец занятий: %s\nПреподаватель: %s\n" +
							 "Аудитория: %s",
							 name, type,
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
				tokens[2],
				tokens[3],
				startTime,
				Integer.parseInt(tokens[5]),
				tokens[6],
				tokens[7]
			);
	}

	@Override
	public boolean equals(Object obj)
	{
		return Integer.valueOf(this.hashCode()).equals(obj.hashCode());
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(name,
							start, lengthInMinutes,
							teacher, room, type);
	}
}
