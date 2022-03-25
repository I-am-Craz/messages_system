package repoitories;

import database.Database;
import models.User;
import org.apache.log4j.Logger;

import java.sql.*;

public class UserRepository {
    static Logger logger = Logger.getLogger(UserRepository.class.getName());
    private static final String USERS_TABLE_NAME = "users";

    public static void createUser(String firstname, String lastname, String username, boolean is_chief, boolean is_subordinate, boolean is_client){
        Connection connection = Database.connect();
        String query = String.format("INSERT INTO %s (FIRSTNAME, LASTNAME, USERNAME, IS_CHIEF, IS_SUBORDINATE, IS_CLIENT)" +
                " VALUES(?, ?, ?, ?, ?, ?)", USERS_TABLE_NAME);
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, firstname);
            preparedStatement.setString(2, lastname);
            preparedStatement.setString(3, username);
            preparedStatement.setBoolean(4, is_chief);
            preparedStatement.setBoolean(5, is_subordinate);
            preparedStatement.setBoolean(6, is_client);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        }
        catch (SQLException e){
            logger.error(e.getMessage());
        }
    }

    public static User getUserByUserName(String username){
        Connection connection = Database.connect();
        String query = String.format("SELECT * FROM %s WHERE username = ?", USERS_TABLE_NAME);
        User user = null;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                user = new User(resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getString("username"),
                        resultSet.getBoolean("is_chief"),
                        resultSet.getBoolean("is_subordinate"),
                        resultSet.getBoolean("is_client"));
            }
            preparedStatement.close();
            connection.close();
        }
        catch (SQLException e){
            System.err.println(e.getMessage());
        }
        finally {
            return user;
        }
    }
}
