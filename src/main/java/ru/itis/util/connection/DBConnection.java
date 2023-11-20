package ru.itis.util.connection;

import ru.itis.util.exception.DBException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection connection;

    public static DBConnection getConnection() throws DBException {
        if(connection == null){
            connection = new DBConnection();
        }
        return connection;
    }

    private Connection connect;

    private DBConnection() throws DBException {
        try {
            Class.forName("org.postgresql.Driver").newInstance();
            connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/EmoNote_db", "postgres", "rumure56");
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new DBException("Can't connect to DB.", e);
        }
    }

    public Connection getConnect() {
        return connect;
    }
}
