package ru.itis.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/signin")
public class SignInServlet extends HttpServlet {
    protected boolean correctData(String email, String passw) throws IOException {
        boolean isValid = false;
        try{
            Connection connection;
            PreparedStatement statement;
            ResultSet resultSet;
            Class.forName("org.postgresql.Driver");

            String url = "jdbc:postgresql://localhost:5432/EmoNote";
            String username = "postgres";
            String password = "rumure56";

            connection = DriverManager.getConnection(url, username, password);

            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, passw);

            resultSet = statement.executeQuery();

            isValid = resultSet.next();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return isValid;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("sign-in.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if(correctData(email,password)) {
            request.getSession(true).setAttribute("User", email);
            response.sendRedirect("profile");
        } else {
            response.sendRedirect("sign-up");
        }
    }
}
