package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBConnectionWrapper {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "Rodanciuc123!";
    private static final int TIMEOUT = 5;

    private static final String ALLOW_MULTI_QUERIES = "?allowMultiQueries=true";

    private Connection connection;

    public JDBConnectionWrapper (String schema){
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL + schema + ALLOW_MULTI_QUERIES,USER,PASSWORD);

        }catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean testConnection() throws SQLException {
        return connection.isValid(TIMEOUT);
    }

    public Connection getConnection()
    {
        return connection;
    }



}
