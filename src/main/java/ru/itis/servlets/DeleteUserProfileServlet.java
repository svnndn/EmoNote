package ru.itis.servlets;

import ru.itis.model.User;
import ru.itis.service.UserService;
import ru.itis.util.exception.DBException;
import ru.itis.dao.UserDao;
import ru.itis.util.PasswordManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteprofile")
public class DeleteUserProfileServlet extends HttpServlet {

    private UserDao userDao;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userDao = new UserDao();
        userService = new UserService(userDao);
        try {
            userDao.init();
        } catch (DBException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = userService.getUser(req, resp);
        if (req.getParameter("error") != null && req.getParameter("error").equals("true")) {
            req.setAttribute("errorMessage", "Произошла ошибка при удалении профиля. Попробуйте еще раз.");
        }
        req.getRequestDispatcher("delete_profile.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long userId = userService.getUser(request, response).getId();

        String oldPassword = request.getParameter("oldPassword");

        String storedPassword = userService.getUserPassword((int) userId);

        boolean isOldPasswordCorrect = PasswordManager.checkPassword(oldPassword, storedPassword);

        boolean isConfirmed = "true".equals(request.getParameter("confirmDelete"));

        if (isOldPasswordCorrect && isConfirmed) {
            try {
                userDao.deleteUserProfile((int) userId);
            } catch (DBException e) {
                throw new ServletException("Error deleting user profile", e);
            }

            response.sendRedirect(request.getContextPath() + "/signout");
        } else {
            response.sendRedirect(request.getContextPath() + "/deleteprofile?error=true");
        }
    }
}
