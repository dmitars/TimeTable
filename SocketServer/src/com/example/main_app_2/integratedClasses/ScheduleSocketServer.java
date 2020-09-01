package com.example.main_app_2.integratedClasses;

import java.io.*;
import java.net.*;
import java.util.*;

public class ScheduleSocketServer
        extends Thread {
    private Socket socket;
    private InputStream in;
    private OutputStream out;

    public ScheduleSocketServer(Socket socket) {
        try {
            this.socket = socket;
            in = socket.getInputStream();
            out = socket.getOutputStream();
            start();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void errorResponder(ObjectOutputStream oos,
                                String errorDescription)
            throws IOException {
        System.out.println(errorDescription);
        Message errorMsg = new Message(MessageType.ERROR, errorDescription);
        oos.writeObject(errorMsg);
    }

    private void scheduleResponder(Message sentMsg,
                                   ObjectOutputStream oos)
            throws IOException {
        try {
            int[] courseGroup = parseSentMessage(sentMsg);
            Map<DayOfWeek, List<Lesson>> lessons = getLessonsWithCourseAndGroup(courseGroup);
            String jsonLessons = getJson(lessons);
            Message response = new Message(MessageType.SEND_SCHEDULE, jsonLessons);
            oos.writeObject(response);
        } catch (Exception e) {
            errorResponder(oos, e.getMessage());
        }
    }

    private Map<DayOfWeek, List<Lesson>> getLessonsWithCourseAndGroup(int[]courseGroup)throws Exception{
        int course = courseGroup[0];
        int group = courseGroup[1];
        checkGroup(course, group);
        return ScheduleInfo.getInstance().getLessonsOfGroup(course, group);
    }

    private int[] parseSentMessage(Message sentMsg) throws Exception {
        if (!(sentMsg.getContainer() instanceof int[])) {
            throw new Exception("You sent an incorrect container: " +
                    "must be a 2-element int array!");
        }
        int[] courseGroup = (int[]) sentMsg.getContainer();
        if (courseGroup.length != 2) {
            throw new Exception("You sent an incorrect array: " +
                    "must contain 2 elements!");
        }
        return courseGroup;
    }

    private void checkGroup(int course, int group) throws Exception {
        if (!ScheduleInfo.getInstance().isGroupPresent(course, group)) {
            throw new Exception("You sent an incorrect group!");
        }
    }

    private String getJson(Object object) throws IOException {
        StringWriter writer = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(writer,object);
        return writer.toString();
    }

    @Override
    public void run() {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        try {
            oos = new ObjectOutputStream(out);
            ois = new ObjectInputStream(in);

            while (true) {
                Message msg = (Message) ois.readObject();
                switch (msg.getMessageType()) {
                    case GET_SCHEDULE:
                        scheduleResponder(msg, oos);
                        break;
                    default:
                        errorResponder(oos, "This query isn't supported!");
                        break;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            if (socket.isConnected()) {
                try {
                    errorResponder(oos, ex.getMessage() == null ? "Everything fucked up!"
                            : ex.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}