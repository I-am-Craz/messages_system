package repositories;

import datasource.DataSource;
import lombok.NonNull;
import lombok.extern.log4j.Log4j;
import models.Message;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

@Log4j
public class MessageRepository {
    private static LocalDate lastDate = null;
    private static final String MESSAGES_TABLE_NAME = "messages";

    public static void getMessages(@NonNull LocalDate startDate,
                                   @NonNull LocalDate endDate,
                                   int limit,
                                   @NonNull ArrayList<Message> messages){
        messages.clear();
        Connection connection = DataSource.connect();
        Message message;
        String query;

        if(lastDate == null){
            query = String.format("SELECT * FROM %s WHERE sending_date BETWEEN '%s' AND '%s' LIMIT %d;",
                    MESSAGES_TABLE_NAME, startDate.toString(), endDate.toString(), limit);
            lastDate = endDate;
        } else{
            query = String.format("SELECT * FROM %s WHERE sending_date BETWEEN '%s' AND '%s' LIMIT %d;",
                    MESSAGES_TABLE_NAME, lastDate.plusDays(1), lastDate.plusDays(2), limit);
            lastDate = lastDate.plusDays(2);
        }

        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                message = new Message(resultSet.getString("text"),
                        resultSet.getDate("sending_date").toLocalDate(),
                        resultSet.getString("sender"),
                        resultSet.getString("recipient"));
                messages.add(message);
            }
        } catch(SQLException e){
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }
    }
}