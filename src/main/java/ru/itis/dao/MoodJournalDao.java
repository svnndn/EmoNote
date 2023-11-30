package ru.itis.dao;

import ru.itis.model.MoodJournal;
import ru.itis.util.connection.DBConnection;
import ru.itis.util.exception.DBException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MoodJournalDao {
    private Connection connection;

    public void init() throws DBException {
        connection = DBConnection.getConnection();
    }
    public void save(MoodJournal moodJournal) throws DBException {
        connection = DBConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO mood_journal (user_id, date_time, title, notes, mood_rating) VALUES (?, ?, ?, ?, ?)")
        ) {
            preparedStatement.setInt(1, moodJournal.getUserId());
            preparedStatement.setTimestamp(2, new java.sql.Timestamp(moodJournal.getDateTime().getTime()));
            preparedStatement.setString(3, moodJournal.getTitle());
            preparedStatement.setString(4, moodJournal.getNotes());
            preparedStatement.setInt(5, moodJournal.getMoodRating());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public MoodJournal findById(int id) throws DBException {
        connection = DBConnection.getConnection();
        MoodJournal moodJournal = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM mood_journal WHERE id = ?")
        ) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    moodJournal = mapRow(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return moodJournal;
    }

    public List<MoodJournal> findAllByUserId(int userId) {
        List<MoodJournal> moodJournals = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM mood_journal WHERE user_id = ?")
        ) {
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    MoodJournal moodJournal = mapRow(resultSet);
                    moodJournals.add(moodJournal);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return moodJournals;
    }

    public List<MoodJournal> findAll() {
        List<MoodJournal> moodJournals = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM mood_journal")
        ) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    MoodJournal moodJournal = mapRow(resultSet);
                    moodJournals.add(moodJournal);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return moodJournals;
    }

    public void update(MoodJournal moodJournal) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE mood_journal SET user_id = ?, date_time = ?, title = ?, notes = ?, mood_rating = ? WHERE id = ?")
        ) {
            preparedStatement.setInt(1, moodJournal.getUserId());
            preparedStatement.setTimestamp(2, new java.sql.Timestamp(moodJournal.getDateTime().getTime()));
            preparedStatement.setString(3, moodJournal.getTitle());
            preparedStatement.setString(4, moodJournal.getNotes());
            preparedStatement.setInt(5, moodJournal.getMoodRating());
            preparedStatement.setInt(6, moodJournal.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteById(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM mood_journal WHERE id = ?")
        ) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAllByUserId(int userId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM mood_journal WHERE user_id = ?")
        ) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private MoodJournal mapRow(ResultSet resultSet) throws SQLException {
        return new MoodJournal(
                resultSet.getInt("id"),
                resultSet.getInt("user_id"),
                resultSet.getTimestamp("date_time"),
                resultSet.getString("title"),
                resultSet.getString("notes"),
                resultSet.getInt("mood_rating")
        );
    }
}