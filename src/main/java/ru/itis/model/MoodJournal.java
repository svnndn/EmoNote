package ru.itis.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoodJournal {
    private int id;
    private int userId;
    private Date dateTime;
    private String title;
    private String notes;
    private int moodRating;
}