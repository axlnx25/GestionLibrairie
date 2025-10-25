package app_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDataBase {
    private static final String URL = "jdbc:mysql://localhost:3306/GestionLibrairie?serverTimezone=UTC";
    private static final String USER = "admin";
    private static final String PASSWORD ="123456";

    private static Connection connection;

    public static Connection getConnection () throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                throw new SQLException("connexion bdd echec",e);
            }
        }
        return connection;
    }
}
