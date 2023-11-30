package ru.itis.servlets;

import ru.itis.dao.MoodJournalDao;
import ru.itis.dao.UserDao;
import ru.itis.dto.MoodJournalDto;
import ru.itis.service.MoodJournalService;
import ru.itis.service.UserService;
import ru.itis.util.exception.DBException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

@WebServlet("/addMoodJournalEntry")
public class AddMoodJournalEntryServlet extends HttpServlet {
    private MoodJournalService moodJournalService;
    private MoodJournalDao moodJournalDao;
    private UserDao userDao;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        moodJournalDao = new MoodJournalDao();
        userDao = new UserDao();
        try {
            moodJournalDao.init();
        } catch (DBException e) {
            e.printStackTrace();
        }
        this.moodJournalService = new MoodJournalService(moodJournalDao);
        userService = (UserService) getServletContext().getAttribute("userService");
        this.userDao = new UserDao();
        if (this.userService == null) {
            this.userService = new UserService(userDao);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!userService.isNonAnonymous(request, response)) {
            response.sendRedirect(request.getContextPath() + "/signin");
            return;
        }
        request.getRequestDispatcher("view/add-mood-journal-entry.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        long userId = userService.getUser(request, response).getId();
        String title = request.getParameter("title");
        String notes = request.getParameter("notes");
        int moodRating = Integer.parseInt(request.getParameter("moodRating"));
        Date dateTime = new Date();
        Timestamp timestamp = new Timestamp(dateTime.getTime());

        MoodJournalDto moodJournalDto = new MoodJournalDto();
        moodJournalDto.setUserId((int) userId);
        moodJournalDto.setTitle(title);
        moodJournalDto.setDateTime(timestamp);
        moodJournalDto.setNotes(notes);
        moodJournalDto.setMoodRating(moodRating);

        try {
            moodJournalService.addMoodJournalEntry(moodJournalDto);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect(request.getContextPath() + "/profile");
    }
}