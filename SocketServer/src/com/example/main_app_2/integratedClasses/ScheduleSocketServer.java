package com.example.main_app_2.integratedClasses;

import java.io.*;
import java.net.*;
import java.util.*;

public class ScheduleSocketServer
	extends Thread
{
	private Socket socket;
	private InputStream in;
	private OutputStream out;

	public ScheduleSocketServer(Socket socket)
	{
		try
		{
			this.socket = socket;
			in = socket.getInputStream();
			out = socket.getOutputStream();
			start();
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}

	private void errorResponder(ObjectOutputStream oos,
								String errorDescription)
		throws IOException
	{
		Message errorMsg = new Message(MessageType.ERROR, errorDescription);
		oos.writeObject(errorMsg);
	}

	private void scheduleResponder(Message sentMsg,
								   ObjectOutputStream oos)
		throws IOException
	{
		if (!(sentMsg.getContainer() instanceof int[]))
		{
			errorResponder(oos, "You sent an incorrect container: " +
								"must be a 2-element int array!");
			return;
		}
		int[] courseGroup = (int[]) sentMsg.getContainer();
		if (courseGroup.length != 2)
		{
			errorResponder(oos, "You sent an incorrect array: " +
								"must contain 2 elements!");
			return;
		}
		int course = courseGroup[0];
		int group = courseGroup[1];
		if (course < 1 || course > ScheduleInfo.getInstance().getGroupsInCourse().length)
		{
			errorResponder(oos, "You sent an incorrect course!");
			return;
		}
		if (group < 1 || group > ScheduleInfo.getInstance().getGroupsInCourse()[course - 1])
		{
			errorResponder(oos, "You sent an incorrect group!");
			return;
		}

		Map<DayOfWeek, List<Lesson>> lessons = new HashMap<>();
		ScheduleInfo.getInstance().getAllLessons()
			.stream()
			.filter(lesson -> lesson.getCourse() == course &&
							  lesson.getGroup() == group)
			.forEach(lesson ->
					 {
						 lessons.putIfAbsent(lesson.getDayOfWeek(), new ArrayList<>());
						 lessons.get(lesson.getDayOfWeek()).add(lesson);
					 });
		Message response = new Message(MessageType.SEND_SCHEDULE, lessons);
		oos.writeObject(response);
	}

	@Override
	public void run()
	{
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;

		try
		{
			oos = new ObjectOutputStream(out);
			ois = new ObjectInputStream(in);

			while (true)
			{
				Message msg = (Message) ois.readObject();
				switch (msg.getMessageType())
				{
					case GET_SCHEDULE:
						scheduleResponder(msg, oos);
						break;
					default:
						errorResponder(oos, "This query isn't supported!");
						break;
				}
			}
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		catch (Exception ex)
		{
			if (socket.isConnected())
			{
				try
				{
					errorResponder(oos, ex.getMessage() == null ? "Everything fucked up!"
																: ex.getMessage());
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		finally
		{
			try
			{
				socket.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
