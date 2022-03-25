package database;

import org.apache.log4j.Logger;

import java.sql.*;

public class Database {
    static Logger logger = Logger.getLogger(Database.class.getName());
    private static final String DATABASE_NAME = "my_database";
    private static String url = String.format("jdbc:postgresql://localhost:5432/%s", DATABASE_NAME);
    private static String user = System.getenv("DATABASE_USER");
    private static String password = System.getenv("DATABASE_PASSWORD");;
    public static Connection connect(){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(url, user, password);
        }
        catch (SQLException e){
            logger.error("Something went wrong with database connection :");
            logger.error(e.getMessage());
            System.exit(0);
        }
        return connection;
    }
}
