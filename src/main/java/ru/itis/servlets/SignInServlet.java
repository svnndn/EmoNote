package ru.itis.servlets;

import ru.itis.model.User;
import ru.itis.service.UserService;
import ru.itis.dao.UserDao;
import ru.itis.util.PasswordManager;
import ru.itis.util.connection.DBConnection;
import ru.itis.util.exception.DBException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/signin")
public class SignInServlet extends HttpServlet {
    private UserService userService;
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserService();
        this.userDao = new UserDao();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("error") != null && req.getParameter("error").equals("true")) {
            req.setAttribute("errorMessage", "Произошла ошибка при входе. Попробуйте еще раз.");
        }
        req.getRequestDispatcher("sign-in.ftl").forward(req, resp);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (isCorrectData(email, password)) {
            User user = null;
            try {
                user = UserDao.getUserByEmail(email);
            } catch (DBException e) {
                throw new RuntimeException(e);
            }
            userService.auth(user, request, response);

            response.sendRedirect(request.getContextPath() + "/profile");
        } else {
            request.setAttribute("errorMessage", "Invalid email or password");
            response.sendRedirect(request.getContextPath() + "/signin?error=true");
        }
    }

    protected boolean isCorrectData(String email, String passw) throws IOException {
        boolean isValid = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection();

            String sql = "SELECT password FROM users WHERE email = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                isValid = PasswordManager.checkPassword(passw, storedPassword);
            }

        } catch (SQLException | DBException e) {
            throw new RuntimeException(e);
        }
        return isValid;
    }
}