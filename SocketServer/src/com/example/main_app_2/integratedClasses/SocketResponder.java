package com.example.main_app_2.integratedClasses;

import java.io.*;

public interface SocketResponder
{
	void respond(Message sentMessage, ObjectOutputStream oos)
		throws IOException;
}
