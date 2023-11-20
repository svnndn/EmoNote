package ru.itis.model;

import java.util.Date;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return Objects.equals(nickname, user.nickname) && Objects.equals(email, user.email);
    }
}