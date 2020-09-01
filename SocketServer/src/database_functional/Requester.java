package database_functional;

import consts.Constants;

import java.sql.*;

public class Requester {
    private Connection connection = null;
    private Statement statement = null;

    private void performConnection() throws Exception {
        Class.forName(Constants.driver);
        connection = DriverManager.getConnection(Constants.databasePath,
                Constants.userName,
                Constants.password);
        statement = connection.createStatement();
    }

    public boolean isGroupPresent(int course, int group) throws Exception {
        String query = String.format("SELECT id" +
                " FROM groups" +
                " WHERE course_number = %d AND group_number = %d",
                course, group);
        var resultSet = executeResultQuery(query);
        return ResultSetParser.isEmpty(resultSet);
    }

    public ResultSet findLessonsOfGroup(int course, int group) throws Exception {
        String query = String.format("SELECT lecturers.name as lecturer_name," +
                        " disciplines.name as discipline_name," +
                        " start_hour,start_minute, length_in_minutes," +
                        " day, room," +
                        " type" +
                        " FROM groups" +
                        " INNER JOIN lessons ON groups.id = group_id" +
                        " INNER JOIN lecturers ON lecturers.id = lecturer_id" +
                        " INNER JOIN time ON time.id = time_id" +
                        " INNER JOIN disciplines ON disciplines.id = discipline_id" +
                        " INNER JOIN lesson_types ON lesson_types.id = type_id" +
                        " WHERE course_number = %d AND group_number = %d",
                course, group);
        var resultSet = executeResultQuery(query);
        return resultSet;
    }

    private ResultSet executeResultQuery(String query) throws Exception {
        ResultSet resultSet;
        performConnection();
        resultSet = statement.executeQuery(query);
        return resultSet;
    }

    public void closeConnection() throws SQLException {
        if(connection!=null)
            connection.close();
    }
}
