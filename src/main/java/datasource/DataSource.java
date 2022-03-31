package datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.log4j.Logger;

import java.sql.*;

public class DataSource {
    private static Logger logger = Logger.getLogger(DataSource.class.getName());
    private static HikariConfig hikariConfig = new HikariConfig();
    private static HikariDataSource dataSource;

    private static final String DATABASE_NAME = "my_database";
    private static String url = String.format("jdbc:postgresql://localhost:5432/%s", DATABASE_NAME);
    private static String user = System.getenv("DATABASE_USER");
    private static String password = System.getenv("DATABASE_PASSWORD");

    static {
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(user);
        hikariConfig.setPassword(password);
        hikariConfig.addDataSourceProperty("cashPrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCashSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtsCashSqlLimit", "2048");
        dataSource = new HikariDataSource(hikariConfig);
    }

    public static Connection connect(){
        Connection connection = null;
        try{
            connection = dataSource.getConnection();
        }
        catch (SQLException e){
            logger.error("Something went wrong with database connection:");
            logger.error(e.getMessage());
            System.exit(0);
        }
        return connection;
    }
}
