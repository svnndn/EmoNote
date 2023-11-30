package ru.itis.servlets;

import ru.itis.dao.UserDao;
import ru.itis.model.User;
import ru.itis.service.UserService;
import ru.itis.util.PasswordManager;
import ru.itis.util.exception.DBException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/edit_main")
public class EditMainInfoProfileServlet extends HttpServlet {
    private UserDao userDao;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.userDao = new UserDao();
        this.userService = new UserService(userDao);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = userService.getUser(req, resp);
        req.setAttribute("user", currentUser);
        if (req.getParameter("error") != null && req.getParameter("error").equals("true")) {
            req.setAttribute("errorMessage", "Неверные данные. Попробуйте еще раз.");
        }
        req.getRequestDispatcher("edit_profile_2.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nickname = req.getParameter("nickname");
        String email = req.getParameter("email");
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");

        User currentUser = userService.getUser(req, resp);

        if (PasswordManager.checkPassword(oldPassword, userService.getUserPassword(currentUser.getId()))) {
            currentUser.setNickname(nickname);
            currentUser.setEmail(email);
            String hashedNewPassword = PasswordManager.encrypt(newPassword);
            currentUser.setPassword(hashedNewPassword);

            try {
                userDao.updateUserProfile(currentUser);
            } catch (DBException e) {
                throw new RuntimeException(e);
            }
            resp.sendRedirect(req.getContextPath() + "/profile");
        } else {
            req.setAttribute("errorMessage", "Incorrect old password");
            resp.sendRedirect(req.getContextPath() + "/edit_main?error=true");
        }
    }
}