package ru.itis.servlets;

import ru.itis.dao.MoodJournalDao;
import ru.itis.dao.UserDao;
import ru.itis.dto.MoodJournalDto;
import ru.itis.dto.UserDto;
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

@WebServlet("/notes")
public class AllNotesServlet extends HttpServlet {
    private MoodJournalService moodJournalService;
    private UserService userService;
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        super.init();
        UserDao userDao = new UserDao();
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<MoodJournalDto> allNotes = moodJournalService.getAllMoodJournals();
        List<UserDto> users = userService.getAllUsers();

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("notes", allNotes);
        dataModel.put("users", users);

        request.setAttribute("dataModel", dataModel);
        request.getRequestDispatcher("/common-mood-journal.ftl").forward(request, response);
    }
}