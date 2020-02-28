package com.example.main_app_2.integratedClasses;
import java.io.Serializable;

public class Message
	implements Serializable
{
	private MessageType messageType;
	private Object container;

	public Message(MessageType messageType,
				   Object container)
	{
		this.messageType = messageType;
		this.container = container;
		if (!(container instanceof Serializable))
			throw new RuntimeException("Can't serialize the container!");
	}

	public MessageType getMessageType()
	{
		return messageType;
	}

	public Object getContainer()
	{
		return container;
	}
}