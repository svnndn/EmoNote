package ru.itis.dao;

import ru.itis.model.Comment;
import ru.itis.util.connection.DBConnection;
import ru.itis.util.exception.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDao {
    private Connection connection;

    public void init() throws DBException {
        connection = DBConnection.getConnection();
    }
    public void save(Comment comment) throws DBException {
        connection = DBConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO comment (user_id, mood_journal_id, date_time, notes) VALUES (?, ?, ?, ?)")
        ) {
            preparedStatement.setInt(1, comment.getUserId());
            preparedStatement.setInt(2, comment.getMoodJournalId());
            preparedStatement.setTimestamp(3, new java.sql.Timestamp(comment.getDateTime().getTime()));
            preparedStatement.setString(4, comment.getNotes());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Comment findById(int id) throws DBException {
        connection = DBConnection.getConnection();
        Comment comment = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM comment WHERE id = ?")
        ) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    comment = mapRow(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return comment;
    }

    public List<Comment> findAllByUserId(int userId) throws DBException {
        connection = DBConnection.getConnection();
        List<Comment> comments = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM comment WHERE user_id = ?")
        ) {
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Comment comment = mapRow(resultSet);
                    comments.add(comment);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return comments;
    }

    public List<Comment> findAllByMoodJournalId(int moodJournalId) throws DBException {
        connection = DBConnection.getConnection();
        List<Comment> comments = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM comment WHERE mood_journal_id = ?")
        ) {
            preparedStatement.setInt(1, moodJournalId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Comment comment = mapRow(resultSet);
                    comments.add(comment);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return comments;
    }

    public List<Comment> findAll() {
        List<Comment> comments = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM comment")
        ) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Comment comment = mapRow(resultSet);
                    comments.add(comment);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return comments;
    }

    public void update(Comment comment) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE comment SET user_id = ?, mood_journal_id = ?, date_time = ?, notes = ? WHERE id = ?")
        ) {
            preparedStatement.setInt(1, comment.getUserId());
            preparedStatement.setInt(2, comment.getMoodJournalId());
            preparedStatement.setTimestamp(3, new java.sql.Timestamp(comment.getDateTime().getTime()));
            preparedStatement.setString(4, comment.getNotes());
            preparedStatement.setInt(5, comment.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteById(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM comment WHERE id = ?")
        ) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAllByUserId(int userId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM comment WHERE user_id = ?")
        ) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAllByMoodJournalId(int moodJournalId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM comment WHERE mood_journal_id = ?")
        ) {
            preparedStatement.setInt(1, moodJournalId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Comment mapRow(ResultSet resultSet) throws SQLException {
        return new Comment(
                resultSet.getInt("id"),
                resultSet.getInt("user_id"),
                resultSet.getInt("mood_journal_id"),
                resultSet.getTimestamp("date_time"),
                resultSet.getString("notes")
        );
    }
}