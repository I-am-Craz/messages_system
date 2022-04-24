package datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;

import java.sql.*;

@Log4j
public class DataSource {
    private static final HikariConfig HIKARI_CONFIG = new HikariConfig();
    private static HikariDataSource dataSource;

    private static final String DATABASE_NAME = "my_database";
    private static final String URL = String.format("jdbc:postgresql://localhost:5432/%s", DATABASE_NAME);
    private static final String USER = System.getenv("DATABASE_USER");
    private static final String PASSWORD = System.getenv("DATABASE_PASSWORD");

    static {
        HIKARI_CONFIG.setJdbcUrl(URL);
        HIKARI_CONFIG.setUsername(USER);
        HIKARI_CONFIG.setPassword(PASSWORD);
        HIKARI_CONFIG.addDataSourceProperty("cashPrepStmts", "true");
        HIKARI_CONFIG.addDataSourceProperty("prepStmtCashSize", "250");
        HIKARI_CONFIG.addDataSourceProperty("prepStmtsCashSqlLimit", "2048");
        dataSource = new HikariDataSource(HIKARI_CONFIG);
    }

    public static Connection connect(){
        Connection connection = null;
        try{
            connection = dataSource.getConnection();
        }
        catch (SQLException e){
            log.error("Something went wrong with database connection:");
            log.error(e.getMessage());
            System.exit(0);
        }
        return connection;
    }
}
