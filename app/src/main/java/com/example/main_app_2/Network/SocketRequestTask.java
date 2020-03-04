package com.example.main_app_2.Network;

import android.os.AsyncTask;

import com.example.main_app_2.integratedClasses.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

public class SocketRequestTask
    extends AsyncTask<Integer, Void, Map<DayOfWeek, List<Lesson>>> {
    private Exception exception;

    @Override
    protected Map<DayOfWeek, List<Lesson>> doInBackground(Integer... ints){
        Map<DayOfWeek, List<Lesson>> answ = null;
        try
        {
            Socket socket = new Socket("192.168.0.106", 2048);

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            Message msg = new Message(MessageType.GET_SCHEDULE, new int[]{ints[0], ints[1]});
            oos.writeObject(msg);

            Message recv = (Message) ois.readObject();
            if (recv.getMessageType() == MessageType.SEND_SCHEDULE)
            {
                answ = (Map<DayOfWeek, List<Lesson>>) recv.getContainer();
            }

            socket.close();
        }
        catch (Exception ex)
        {

            exception = ex;
            //throw new RuntimeException("incorrect inet connection");
            return null;
        }
        return answ;
    }
}
