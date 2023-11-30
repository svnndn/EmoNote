package ru.itis.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

import ru.itis.dao.UserDao;
import ru.itis.service.UserService;
import ru.itis.util.exception.DBException;

@WebServlet("/signout")
public class SignOutServlet extends HttpServlet {
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
        userService = new UserService(userDao);
        this.userDao = new UserDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getOut(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getOut(req, resp);
    }

    private void getOut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (userService.isNonAnonymous(req, resp)) {
//            Cookie[] cookies = req.getCookies();
//
//            if (cookies != null) {
//                for (Cookie cookie : cookies) {
//                    cookie.setMaxAge(0);
//                    resp.addCookie(cookie);
//                }
//            }

            HttpSession session = req.getSession(false);

            if (session != null) {
                session.invalidate();
            }

            resp.sendRedirect(req.getContextPath() + "/");
        }
        else {
            resp.sendRedirect(req.getContextPath() + "/");
        }
    }
}