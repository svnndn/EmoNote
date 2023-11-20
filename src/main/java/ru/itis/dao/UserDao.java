package ru.itis.dao;

import ru.itis.model.User;
import ru.itis.util.connection.DBConnection;
import ru.itis.util.exception.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    public static User getUserByEmail(String email) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM users WHERE email = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId((int) resultSet.getLong("id"));
                user.setNickname(resultSet.getString("nickname"));
                user.setEmail(resultSet.getString("email"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public User getUserData(int userId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM users WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = User.builder()
                        .id(resultSet.getInt("id"))
                        .nickname(getNotNull(resultSet.getString("nickname")))
                        .name(getNotNull(resultSet.getString("name")))
                        .surname(getNotNull(resultSet.getString("surname")))
                        .email(getNotNull(resultSet.getString("email")))
                        .dateOfBirth(resultSet.getDate("date_of_birth"))
                        .gender(getNotNull(resultSet.getString("gender")))
                        .description(getNotNull(resultSet.getString("description")))
                        .build();
            }
        } catch (SQLException | DBException e) {
            e.printStackTrace();
        }

        return user;
    }

    private String getNotNull(String value) {
        return (value != null && !value.isEmpty()) ? value : "unknown";
    }
}