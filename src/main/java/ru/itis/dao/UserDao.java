package ru.itis.dao;

import ru.itis.model.MoodJournal;
import ru.itis.model.User;
import ru.itis.util.connection.DBConnection;
import ru.itis.util.exception.DBException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private Connection connection;
    private MoodJournalDao moodJournalDao;

    public void init() throws DBException {
        connection = DBConnection.getConnection();
        moodJournalDao = new MoodJournalDao();
        moodJournalDao.init();
    }

    public User getUserByEmail(String email, Connection connection) throws DBException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
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

    public User getUserData(int userId){
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            String sql = "SELECT * FROM users WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                java.sql.Date dateOfBirth = resultSet.getDate("date_of_birth");
                user = User.builder()
                        .id(resultSet.getInt("id"))
                        .nickname(getNotNull(resultSet.getString("nickname")))
                        .name(getNotNull(resultSet.getString("name")))
                        .surname(getNotNull(resultSet.getString("surname")))
                        .email(getNotNull(resultSet.getString("email")))
                        .dateOfBirth(dateOfBirth != null ? dateOfBirth : java.sql.Date.valueOf("1970-01-01"))
                        .gender(getNotNull(resultSet.getString("gender")))
                        .description(getNotNull(resultSet.getString("description")))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public List<User> findAll() {
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        List<User> users = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM users")
        ) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    User user = mapRow(resultSet);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    private User mapRow(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt("id"),
                resultSet.getString("nickname"),
                resultSet.getString("name"),
                resultSet.getString("surname"),
                resultSet.getString("email"),
                resultSet.getString("password"),
                resultSet.getDate("date_of_birth"),
                resultSet.getString("gender"),
                resultSet.getString("description")
        );
    }

    private String getNotNull(String value) {
        return (value != null && !value.isEmpty()) ? value : "unknown";
    }

    public void updateUserProfile(User currentUser) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBConnection.getConnection();
            String sql = "UPDATE users SET nickname = ?, email = ?, password = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, currentUser.getNickname());
            preparedStatement.setString(2, currentUser.getEmail());
            preparedStatement.setString(3, currentUser.getPassword());
            preparedStatement.setInt(4, currentUser.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Error occurred while updating user profile", e);
        }
    }

    public void deleteUserProfile(int userId) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            moodJournalDao.deleteAllByUserId(userId);
            connection = DBConnection.getConnection();
            String sql = "DELETE FROM users WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Error occurred while deleting user profile", e);
        }
    }
}