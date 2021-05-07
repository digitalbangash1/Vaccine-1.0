package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    private static final String HOST     = "localhost";
    private static final int    PORT     = 3306;
    private static final String DATABASE = "Test";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "zakaria123";

    private static Connection connection;

    private Connector(){}

    private static void createConnector() {
        String url = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?serverTimezone=UTC";
        try {
            connection = DriverManager.getConnection(url, USERNAME, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public static Connection getConnection() {
        if(connection == null){
            createConnector();
        }
        return connection;
    }
}
