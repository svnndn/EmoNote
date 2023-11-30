package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private int id;
    private String nickname;
    private String name;
    private String surname;
    private String email;
    private Date dateOfBirth;
    private String gender;
    private String description;
}