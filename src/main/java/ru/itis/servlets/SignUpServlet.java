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

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
    private UserService userService;
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        super.init();
        userDao = new UserDao();
        try {
            userDao.init();
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        userService = (UserService) getServletContext().getAttribute("userService");
        if (this.userService == null) {
            this.userService = new UserService(userDao);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (userService.isNonAnonymous(req, resp)) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        if (req.getParameter("error") != null && req.getParameter("error").equals("true")) {
            req.setAttribute("errorMessage", "Произошла ошибка при регистрации. Попробуйте еще раз.");
        }
        req.getRequestDispatcher("sign-up.ftl").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nickname = req.getParameter("nickname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        String hashedPassword = PasswordManager.encrypt(password);

        User user = new User();
        user.setNickname(nickname);
        user.setEmail(email);
        user.setPassword(hashedPassword);

        try {
            UserService.registerUser(user);
            resp.sendRedirect(req.getContextPath() + "/signin");
        } catch (DBException e) {
            resp.sendRedirect(req.getContextPath() + "/signup?error=true");
        }
    }
}
