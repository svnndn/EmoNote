package ru.itis.servlets;

import ru.itis.dao.UserDao;
import ru.itis.model.User;
import ru.itis.service.UserService;
import ru.itis.util.exception.DBException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet("/edit")
public class EditProfileServlet extends HttpServlet {
    private UserService userService;
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = (UserService) getServletContext().getAttribute("userService");
        this.userDao = new UserDao();
        if (this.userService == null) {
            this.userService = new UserService(userDao);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!userService.isNonAnonymous(req, resp)) {
            resp.sendRedirect(req.getContextPath() + "/signin");
            return;
        }
        if (req.getParameter("error") != null && req.getParameter("error").equals("true")) {
            req.setAttribute("errorMessage", "Произошла ошибка при обновлении профиля. Попробуйте еще раз.");
        }
        long userId = userService.getUser(req, resp).getId();
        User currentUser = userDao.getUserData((int) userId);
        req.setAttribute("user", currentUser);
        req.getRequestDispatcher("edit-profile.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!userService.isNonAnonymous(req, resp)) {
            resp.sendRedirect(req.getContextPath() + "/signin");
            return;
        }

        User currentUser = userService.getUser(req, resp);

        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String dateOfBirthString = req.getParameter("dateOfBirth");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parsedDate = LocalDate.parse(dateOfBirthString, formatter);
        Date dateOfBirth = Date.valueOf(parsedDate);
        String gender = req.getParameter("gender");
        String description = req.getParameter("description");

        currentUser.setName(name);
        currentUser.setSurname(surname);
        currentUser.setDateOfBirth(dateOfBirth);
        currentUser.setGender(gender);
        currentUser.setDescription(description);

        try {
            userService.updateUserProfile(currentUser);
            resp.sendRedirect(req.getContextPath() + "/profile");
        } catch (DBException e) {
            resp.sendRedirect(req.getContextPath() + "/edit?error=true");
        }
    }
}