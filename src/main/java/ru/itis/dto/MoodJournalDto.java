package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoodJournalDto {
    private int id;
    private int userId;
    private Timestamp dateTime;
    private String title;
    private String notes;
    private int moodRating;
}