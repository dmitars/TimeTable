package com.example.main_app_2.integratedClasses;

import java.net.*;
import java.util.LinkedList;

public class ScheduleServer
{
	private static ServerSocket serverSocket;
	private static LinkedList<ScheduleSocketServer> clientServers;
	private static int port;

	public static LinkedList<ScheduleSocketServer> getClientServers()
	{
		return clientServers;
	}

	public static void main(String[] args)
		throws Exception
	{
		try
		{
			port = 2048;
			serverSocket = new ServerSocket(port);
			clientServers = new LinkedList<>();
			System.out.println("Server is running!");

			Socket newClient;
			while (!serverSocket.isClosed())
			{
				newClient = serverSocket.accept();
				try
				{
					clientServers.addLast(new ScheduleSocketServer(newClient));
					System.out.println("New client appeared!");
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					newClient.close();
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			serverSocket.close();
		}
	}
}
