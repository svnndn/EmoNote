package ru.itis.servlets;

import ru.itis.dao.MoodJournalDao;
import ru.itis.dto.MoodJournalDto;
import ru.itis.model.MoodJournal;
import ru.itis.model.User;
import ru.itis.service.MoodJournalService;
import ru.itis.util.exception.DBException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

@WebServlet("/editnote")
public class EditMoodJournalServlet extends HttpServlet {
    private MoodJournalService moodJournalService;
    private MoodJournalDao moodJournalDao;

    @Override
    public void init() throws ServletException {
        super.init();
        moodJournalDao = new MoodJournalDao();
        moodJournalService = new MoodJournalService(moodJournalDao);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int moodJournalId = Integer.parseInt(request.getParameter("moodJournalId"));
        MoodJournal moodJournal = null;
        try {
            moodJournal = moodJournalDao.findById(moodJournalId);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("moodJournalDto", moodJournal);
        request.getRequestDispatcher("view/edit-mood-journal.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int moodJournalId = Integer.parseInt(request.getParameter("moodJournalId"));
        String title = request.getParameter("title");
        String notes = request.getParameter("notes");
        int moodRating = Integer.parseInt(request.getParameter("moodRating"));
        Date dateTime = new Date();
        Timestamp timestamp = new Timestamp(dateTime.getTime());
        User currentUser = (User) request.getSession().getAttribute("user");
        int userId = currentUser.getId();

        MoodJournal updatedMoodJournal = new MoodJournal();

        updatedMoodJournal.setId(moodJournalId);
        updatedMoodJournal.setUserId(userId);
        updatedMoodJournal.setDateTime(timestamp);
        updatedMoodJournal.setTitle(title);
        updatedMoodJournal.setNotes(notes);
        updatedMoodJournal.setMoodRating(moodRating);

        moodJournalDao.update(updatedMoodJournal);

        response.sendRedirect(request.getContextPath() + "/profile");
    }
}