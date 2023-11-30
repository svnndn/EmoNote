package ru.itis.service;

import ru.itis.dao.MoodJournalDao;
import ru.itis.dto.MoodJournalDto;
import ru.itis.model.MoodJournal;
import ru.itis.util.exception.DBException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MoodJournalService {

    private final MoodJournalDao moodJournalDao;

    public MoodJournalService(MoodJournalDao moodJournalDao) {
        this.moodJournalDao = moodJournalDao;
    }

    public void addMoodJournalEntry(MoodJournalDto moodJournalDto) throws DBException {
        MoodJournal moodJournal = MoodJournal.builder()
                .userId(moodJournalDto.getUserId())
                .dateTime(moodJournalDto.getDateTime())
                .title(moodJournalDto.getTitle())
                .notes(moodJournalDto.getNotes())
                .moodRating(moodJournalDto.getMoodRating())
                .build();

        moodJournalDao.save(moodJournal);
    }

    public List<MoodJournalDto> getAllMoodJournalsByUserId(int userId) {
        return moodJournalDao.findAllByUserId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<MoodJournalDto> getAllMoodJournals() {
        List<MoodJournal> moodJournals = moodJournalDao.findAll();

        List<MoodJournalDto> moodJournalDtos = new ArrayList<>();
        for (MoodJournal moodJournal : moodJournals) {
            MoodJournalDto moodJournalDto = convertToDto(moodJournal);
            moodJournalDtos.add(moodJournalDto);
        }
        return moodJournalDtos;
    }
    private MoodJournalDto convertToDto(MoodJournal moodJournal) {
        return MoodJournalDto.builder()
                .id(moodJournal.getId())
                .userId(moodJournal.getUserId())
                .dateTime(new Timestamp(moodJournal.getDateTime().getTime()))
                .title(moodJournal.getTitle())
                .notes(moodJournal.getNotes())
                .moodRating(moodJournal.getMoodRating())
                .build();
    }
}