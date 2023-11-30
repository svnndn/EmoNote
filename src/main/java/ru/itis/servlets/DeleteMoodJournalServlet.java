package ru.itis.servlets;

import ru.itis.dao.MoodJournalDao;
import ru.itis.dao.UserDao;
import ru.itis.model.MoodJournal;
import ru.itis.model.User;
import ru.itis.service.MoodJournalService;
import ru.itis.service.UserService;
import ru.itis.util.PasswordManager;
import ru.itis.util.exception.DBException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deletenote")
public class DeleteMoodJournalServlet extends HttpServlet {
    private MoodJournalService moodJournalService;
    private MoodJournalDao moodJournalDao;
    private UserService userService;
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        super.init();
        moodJournalDao = new MoodJournalDao();
        moodJournalService = new MoodJournalService(moodJournalDao);
        userDao = new UserDao();
        userService = new UserService(userDao);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("error") != null && req.getParameter("error").equals("true")) {
            req.setAttribute("errorMessage", "Произошла ошибка при удалении. Попробуйте еще раз.");
        }
        int moodJournalId = Integer.parseInt(req.getParameter("moodJournalId"));
        MoodJournal moodJournal = null;
        try {
            moodJournal = moodJournalDao.findById(moodJournalId);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        System.out.println(moodJournal.getId());
        req.setAttribute("moodJournalDto", moodJournal);
        req.getRequestDispatcher("delete-note.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int moodJournalId = Integer.parseInt(request.getParameter("moodJournalId"));

        long userId = userService.getUser(request, response).getId();
        String oldPassword = request.getParameter("oldPassword");
        String storedPassword = userService.getUserPassword((int) userId);
        boolean isOldPasswordCorrect = PasswordManager.checkPassword(oldPassword, storedPassword);
        boolean isConfirmed = "true".equals(request.getParameter("confirmDelete"));

        if (isOldPasswordCorrect && isConfirmed) {
            moodJournalDao.deleteById(moodJournalId);

            response.sendRedirect(request.getContextPath() + "/profile");
        } else {
            response.sendRedirect(request.getContextPath() + "/deletenote?error=true");
        }
    }
}