package ru.itis.servlets;

import ru.itis.dao.CommentDao;
import ru.itis.dao.MoodJournalDao;
import ru.itis.dao.UserDao;
import ru.itis.dto.CommentDto;
import ru.itis.dto.UserDto;
import ru.itis.model.Comment;
import ru.itis.model.MoodJournal;
import ru.itis.model.User;
import ru.itis.service.CommentService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/comments")
public class CommentsServlet extends HttpServlet {
    private MoodJournalDao moodJournalDao;
    private MoodJournalService moodJournalService;
    private CommentDao commentDao;
    private CommentService commentService;
    private UserDao userDao;
    private UserService userService;


    @Override
    public void init() throws ServletException {
        super.init();
        moodJournalDao = new MoodJournalDao();
        moodJournalService = new MoodJournalService(moodJournalDao);
        commentDao = new CommentDao();
        commentService = new CommentService(commentDao);
        userDao = new UserDao();
        userService = new UserService(userDao);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int moodJournalId = Integer.parseInt(req.getParameter("moodJournalId"));
        MoodJournal moodJournal = null;
        List<CommentDto> commentDtos = null;
        List<UserDto> users = userService.getAllUsers();
        try {
            commentDtos = commentService.getAllCommentsByMoodJournalId(moodJournalId);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        User currentUser = (User) req.getSession().getAttribute("user");
        try {
            moodJournal = moodJournalDao.findById(moodJournalId);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("note", moodJournal);
        dataModel.put("comments", commentDtos);
        dataModel.put("currentUser", currentUser);
        dataModel.put("users", users);
        req.setAttribute("dataModel", dataModel);
        req.getRequestDispatcher("comments.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int moodJournalId = Integer.parseInt(request.getParameter("moodJournalId"));
        String commentText = request.getParameter("comment");
        User currentUser = (User) request.getSession().getAttribute("user");
        Date dateTime = new Date();
        Timestamp timestamp = new Timestamp(dateTime.getTime());

        Comment newComment = new Comment();
        newComment.setMoodJournalId(moodJournalId);
        newComment.setUserId(currentUser.getId());
        newComment.setNotes(commentText);
        newComment.setDateTime(timestamp);

        try {
            commentDao.save(newComment);
        } catch (DBException e) {
            throw new ServletException("Unable to save comment", e);
        }

        response.sendRedirect(request.getContextPath() + "/comments?moodJournalId=" + moodJournalId);
    }
}