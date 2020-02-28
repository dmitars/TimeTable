package com.example.main_app_2.integratedClasses;
import java.io.Serializable;

public enum DayOfWeek
	implements Serializable
{
	MONDAY("Понедельник"),
	TUESDAY("Вторник"),
	WEDNESDAY("Среда"),
	THURSDAY("Четверг"),
	FRIDAY("Пятница"),
	SATURDAY("Суббота"),
	SUNDAY("Воскресенье");

	private String name;

	DayOfWeek(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return name;
	}

	public static DayOfWeek parse(String str)
	{
		for (int i = 0; i < DayOfWeek.values().length; ++i)
			if (DayOfWeek.values()[i].name.equalsIgnoreCase(str))
				return DayOfWeek.values()[i];
		throw new RuntimeException();
	}
}
