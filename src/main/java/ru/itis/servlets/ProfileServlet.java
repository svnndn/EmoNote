package ru.itis.servlets;

import ru.itis.dao.MoodJournalDao;
import ru.itis.dto.MoodJournalDto;
import ru.itis.model.User;
import ru.itis.dao.UserDao;
import ru.itis.service.MoodJournalService;
import ru.itis.service.UserService;
import ru.itis.util.exception.DBException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/profile/*")
public class ProfileServlet extends HttpServlet {
    private UserDao userDao;
    private UserService userService;
    private MoodJournalService moodJournalService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.userDao = new UserDao();
        userService = new UserService(userDao);
        moodJournalService = (MoodJournalService) getServletContext().getAttribute("moodJournalService");
        if (moodJournalService == null) {
            MoodJournalDao moodJournalDao = new MoodJournalDao();
            try {
                moodJournalDao.init();
            } catch (DBException e) {
                throw new ServletException(e);
            }
            moodJournalService = new MoodJournalService(moodJournalDao);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            User currentUser = (User) req.getSession().getAttribute("user");
            if (currentUser != null) {
                int userId = currentUser.getId();
                User user = userDao.getUserData(userId);
                List<MoodJournalDto> userNotes = moodJournalService.getAllMoodJournalsByUserId(userId);

                Map<String, Object> dataModel = new HashMap<>();
                dataModel.put("notes", userNotes);
                dataModel.put("user", user);
                dataModel.put("currentUser", currentUser);

                req.setAttribute("dataModel", dataModel);
                req.getRequestDispatcher("/profile.ftl").forward(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath() + "/signin");
            }
            return;
        }

        String[] pathParts = pathInfo.split("/");
        if (pathParts.length != 2) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        int userId;
        try {
            userId = Integer.parseInt(pathParts[1]);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        User user = userDao.getUserData(userId);
        if (user == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        List<MoodJournalDto> userNotes = moodJournalService.getAllMoodJournalsByUserId(userId);
        User currentUser = (User) req.getSession().getAttribute("user");
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("notes", userNotes);
        dataModel.put("user", user);
        dataModel.put("currentUser", currentUser);

        req.setAttribute("dataModel", dataModel);
        req.getRequestDispatcher("/profile.ftl").forward(req, resp);
    }
}