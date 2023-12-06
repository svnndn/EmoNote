package ru.itis.service;

import ru.itis.dao.CommentDao;
import ru.itis.dto.CommentDto;
import ru.itis.model.Comment;
import ru.itis.util.exception.DBException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommentService {

    private final CommentDao commentDao;

    public CommentService(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    public void addCommentEntry(CommentDto commentDto) throws DBException {
        Comment comment = Comment.builder()
                .userId(commentDto.getUserId())
                .moodJournalId(commentDto.getMoodJournalId())
                .dateTime(commentDto.getDateTime())
                .notes(commentDto.getNotes())
                .build();

        commentDao.save(comment);
    }

    public List<CommentDto> getAllCommentsByUserId(int userId) throws DBException {
        return commentDao.findAllByUserId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<CommentDto> getAllCommentsByMoodJournalId(int moodJournalId) throws DBException {
        return commentDao.findAllByMoodJournalId(moodJournalId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<CommentDto> getAllComments() {
        List<Comment> comments = commentDao.findAll();

        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments) {
            CommentDto commentDto = convertToDto(comment);
            commentDtos.add(commentDto);
        }
        return commentDtos;
    }
    private CommentDto convertToDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .userId(comment.getUserId())
                .moodJournalId(comment.getMoodJournalId())
                .dateTime(new Timestamp(comment.getDateTime().getTime()))
                .notes(comment.getNotes())
                .build();
    }
}