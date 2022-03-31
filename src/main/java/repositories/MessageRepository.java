package repositories;

import datasource.DataSource;
import models.Message;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class MessageRepository {
    private static Logger logger = Logger.getLogger(MessageRepository.class.getName());
    private static LocalDate lastDate = null;
    private static final String MESSAGES_TABLE_NAME = "messages";

    public static void getMessages(LocalDate startDate, LocalDate endDate, int limit, ArrayList<Message> messages){
        Connection connection = DataSource.connect();
        Message message = null;
        if(lastDate != null){
            startDate = lastDate;
        }
        lastDate = endDate;
        String query = String.format("SELECT * FROM %s WHERE sending_date BETWEEN '%s' AND '%s' LIMIT %d;",
                MESSAGES_TABLE_NAME, startDate.toString(), endDate.toString(), limit);
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                message = new Message(resultSet.getString("text"),
                        resultSet.getDate("sending_date"),
                        resultSet.getString("sender"),
                        resultSet.getString("recipient"));
                messages.add(message);
            }
            statement.close();
            connection.close();
        } catch(SQLException e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }
}