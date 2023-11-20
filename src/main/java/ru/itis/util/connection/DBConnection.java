package ru.itis.util.connection;

import ru.itis.util.exception.DBException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection;

    public static Connection getConnection() throws DBException {
        if (connection == null) {
            initializeConnection();
        }
        return connection;
    }

    private static void initializeConnection() throws DBException {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/EmoNote_db", "postgres", "rumure56");
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException("Can't connect to DB.", e);
        }
    }
}