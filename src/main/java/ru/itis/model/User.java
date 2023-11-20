package ru.itis.model;

import java.util.Date;
import lombok.Data;

@Data
public class User {
    private int id;
    private String nickname;
    private String name;
    private String surname;
    private String email;
    private String password;
    private Date dateOfBirth;
    private String gender;
    private String description;

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);

        return result;
    }
}