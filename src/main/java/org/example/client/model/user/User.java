package org.example.client.model.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.client.model.user.exception.InvalidPasswordException;

@Setter
@Getter
public class User {
    private Long id;
    private String userId;
    private String password;
    private String name;

    @Builder
    public User(String userId, String password, String name) {
        this.userId = userId;
        this.password = password;
        this.name = name;
    }

    public void passwordValidation(String password) {
        if (!this.password.equals(password)) {
            throw new InvalidPasswordException("Invalid password");
        }
    }
}
