package com.example.main_app_2.integratedClasses;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class ScheduleClient
{
	@SuppressWarnings("unchecked")
	public static void main(String[] args)
	{
/*		Socket socket = new Socket();
		while (!socket.isConnected())
		{
			try
			{
				socket = new Socket("127.0.0.1", 2048);
			}
			catch (Exception ignore) {}
		}

		try
		{
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

			com.example.main_app_2.integratedClasses.Message msg = new com.example.main_app_2.integratedClasses.Message(com.example.main_app_2.integratedClasses.MessageType.GET_SCHEDULE, new int[]{1, 14});
			oos.writeObject(msg);

			com.example.main_app_2.integratedClasses.Message recv = (com.example.main_app_2.integratedClasses.Message) ois.readObject();
			if (recv.getMessageType() == com.example.main_app_2.integratedClasses.MessageType.SEND_SCHEDULE)
			{
				Map<com.example.main_app_2.integratedClasses.DayOfWeek, List<com.example.main_app_2.integratedClasses.Lesson>> response =
					(Map<com.example.main_app_2.integratedClasses.DayOfWeek, List<com.example.main_app_2.integratedClasses.Lesson>>) recv.getContainer();
			}

			socket.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}*/
		try
		{
			Socket socket = new Socket("192.168.0.103", 2048);

			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

			Message msg = new Message(MessageType.GET_SCHEDULE, new int[]{1, 14});
			oos.writeObject(msg);

			Message recv = (Message) ois.readObject();
			if (recv.getMessageType() == MessageType.SEND_SCHEDULE)
			{
				Map<DayOfWeek, List<Lesson>> response =
					(Map<DayOfWeek, List<Lesson>>) recv.getContainer();
			}

			socket.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
