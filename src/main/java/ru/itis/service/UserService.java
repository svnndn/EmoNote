package ru.itis.service;

import ru.itis.dao.UserDao;
import ru.itis.dto.UserDto;
import ru.itis.model.User;
import ru.itis.util.PasswordManager;
import ru.itis.util.connection.DBConnection;
import ru.itis.util.exception.DBException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }
    public static void registerUser(User user) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBConnection.getConnection();
            String sql = "INSERT INTO users (nickname, email, password) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getNickname());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Error occurred while registering user", e);
        }
    }

    public void updateUserProfile(User user) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBConnection.getConnection();
            String sql = "UPDATE users SET name = ?, surname = ?, date_of_birth = ?, gender = ?, description = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setDate(3, (Date) user.getDateOfBirth());
            preparedStatement.setString(4, user.getGender());
            preparedStatement.setString(5, user.getDescription());
            preparedStatement.setInt(6, user.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Error occurred while updating user profile", e);
        }
    }

    public void auth(User user, HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        session.setAttribute("user_id", user.getId());
        session.setAttribute("nickname", user.getNickname());
        session.setMaxInactiveInterval(-1);
        session.setAttribute("user", user);
    }

    public boolean isNonAnonymous(HttpServletRequest req, HttpServletResponse resp) {
        return req.getSession().getAttribute("user") != null;
    }

    public User getUser(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getSession().getAttribute("user") != null) {
            return (User) req.getSession().getAttribute("user");
        } else {
            throw new IllegalStateException("User attribute does not exist in the session.");
        }
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userDao.findAll();

        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            UserDto userDto = convertToDto(user);
            userDtos.add(userDto);
        }
        return userDtos;
    }

    public String getUserPassword(int userId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String password = null;

        try {
            connection = DBConnection.getConnection();

            String sql = "SELECT password FROM users WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                password = resultSet.getString("password");
            }
        } catch (SQLException | DBException e) {
            throw new RuntimeException(e);
        }
        return password;
    }

    private UserDto convertToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .name(user.getName())
                .surname(user.getSurname())
                .dateOfBirth(user.getDateOfBirth())
                .gender(user.getGender())
                .description(user.getDescription())
                .build();
    }
}