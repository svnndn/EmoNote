package ru.itis.servlets;

import ru.itis.model.User;
import ru.itis.service.UserService;
import ru.itis.dao.UserDao;
import ru.itis.util.PasswordManager;
import ru.itis.util.connection.DBConnection;
import ru.itis.util.exception.DBException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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
        userDao = new UserDao();
        try {
            userDao.init();
            userService = new UserService(userDao);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (userService.isNonAnonymous(req, resp)) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        if (req.getParameter("error") != null && req.getParameter("error").equals("true")) {
            req.setAttribute("errorMessage", "Произошла ошибка при входе. Попробуйте еще раз.");
        }

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie: cookies) {
                if (cookie.getName().equals("user_id")) {
                    resp.sendRedirect(req.getContextPath() + "/profile");
                    return;
                }
            }
        }
        req.getRequestDispatcher("sign-in.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("remember_me");

        if (isCorrectData(email, password)) {
            User user = null;
            try {
                Connection connection = DBConnection.getConnection();
                user = userDao.getUserByEmail(email, connection);
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